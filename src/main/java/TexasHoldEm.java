import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.io.*;
import java.util.Random;
import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JOptionPane;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

class TexasHoldEm
{
	public static Logger logger = null;

	public static void main(String []args)
	{
		new TexasHoldEm();
	}

	public TexasHoldEm()
	{
		InitialPlayerDialog dialog = new InitialPlayerDialog(this, "Game Set-Up");
	}

	public void gameSetup(String playerName, int numberOfOpponents, boolean timerMode, boolean hecklingMode)
	{
		int numberOfPlayers = numberOfOpponents+1;

		PlayerContainer[] playerContainers = new PlayerContainer[numberOfPlayers];
		Player[] players = new Player[numberOfPlayers];

		Deck deck = new Deck();
		Dealer zhlata = new Dealer(deck);
		DealerContainer dealerContainer = new DealerContainer(zhlata);

		ArrayList<String> names = new ArrayList<>(Arrays.asList("Jacques S.",
		"Seymour B.", "Mike R.", "Ollie T.", "Al C.",
		"Oliver C.", "Libby D.", "Sarah N.", "Robin B.", "Hugh J."));

		Collections.shuffle(names);

		players[0] = new Player(playerName);
		players[0].setPlayerType(0);
		players[0].setTimerMode(timerMode); // If the check box is clicked in the initial dialog, set the human player to be in timer mode
		players[0].updateMoney(21); // Testing purposes
		
		for(int i = 1; i < numberOfPlayers; i++)
		{
			players[i] = new Player(names.get(i));
			players[i].setPlayerType(1);
		}

		try
		{
			logger = new Logger("output.txt", players);
		}
		catch(Exception e)
		{
			System.err.println("Failed starting logger.");
		}

		GameBoard gameBoard = new GameBoard("Texas Hold 'Em", playerContainers, dealerContainer, this);

		Game game = new Game(gameBoard, logger, players, zhlata);

		for(int i = 0; i < numberOfPlayers; i++)
		{
			if(i == 0)
				playerContainers[i] = new PlayerContainer(players[i], game, false);
			else
				playerContainers[i] = new PlayerContainer(players[i], game, hecklingMode);
		}
		
		assignAvatars(playerContainers);

		gameBoard.showPlayers();
		game.initializeBlinds();

		Thread gameThread = new Thread(() -> {
			game.runMe();
		});
		gameThread.start();
		
		if(hecklingMode)
		{
			Thread heckleThread = new Thread(() -> {
				heckle(playerContainers);
			});
			
			heckleThread.start();
		}
	}
	
	public void heckle(PlayerContainer[] containers)
	{
		BufferedReader inReader = null;
		
		try
		{
			inReader = new BufferedReader(new FileReader(new File("src/main/resources/heckles.txt")));
		}
		catch(IOException e)
		{
			
		}
		
		ArrayList<String> heckles = new ArrayList<>();
		
		try
		{
			while(inReader.ready())
			{
				heckles.add(inReader.readLine());
			}
		}
		catch(IOException e)
		{
			
		}
		
		Random gen = new Random();
				
		Timer timer = new Timer(10000, new ActionListener() {
			public void actionPerformed(ActionEvent evt)
			{
				int playerIndex = 0;
				for(int i = 1; i < containers.length; i++)
					containers[i].setHeckle("");
				
				if(containers.length > 2)
				{
					playerIndex = gen.nextInt(containers.length - 2) + 1;
				}
				else
				{
					playerIndex = gen.nextInt(containers.length - 1) + 1;
				}
				Collections.shuffle(heckles);
				containers[playerIndex].setHeckle(heckles.get(0));
			}
		});
		
		timer.start();		
	}
	
	public void assignAvatars(PlayerContainer[] playerContainers)
	{
		String[] buttons = {"Yes", "No"};
		
		int returnValue = JOptionPane.showOptionDialog(null, "Would you like to choose an avatar?", "Avatar Option",
				JOptionPane.WARNING_MESSAGE, 0, null, buttons, null);
				
		if(returnValue == 0) //picked yes, display file chooser
		{
			JFileChooser popUp = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG, PNG, & GIF Images", "jpg", "png", "gif");
		
			popUp.setFileFilter(filter);
		
			popUp.setCurrentDirectory(new File("./src/main/resources/img/avatars/"));
		
			int returnVal = popUp.showOpenDialog(null);
			
			if(returnVal == JFileChooser.APPROVE_OPTION)
			{
				File pic =  popUp.getSelectedFile();
				
				if(pic.getName().endsWith("gif"))
				{
					URL url = TexasHoldEm.class.getResource(pic.getName());
					playerContainers[0].setAvatar(new ImageIcon(url));
				}
				else
				{
					BufferedImage img = null;
					try
					{
						img = ImageIO.read(pic);
					}
					catch (IOException e)
					{
				
					}
					playerContainers[0].setAvatar(new ImageIcon(img));
				}
				
			}
			
		}
		//add in file chooser
		//get photo from file chooser
		//place it on player
		
		
		String extension = "./src/main/resources/img/avatars/";
		
		ArrayList<String> avatars = new ArrayList<>(Arrays.asList("Bart_Simpson.png",
		"Farnan.jpg", "Laboon.jpg", "Misurda.jpg", "Patrick_Star.png", "Ramirez.jpg", 
		"spongebob.png", "ninja.png"));
		
		Collections.shuffle(avatars);
		
		for(int i = 1; i < playerContainers.length; i++)
			playerContainers[i].setAvatar(new ImageIcon(extension + avatars.get(i)));
	}

	public void exit()
	{
		try
		{
			logger.closeLogger();
		}
		catch(Exception e)
		{
			System.out.println("Failed closing logger.");
		}
		System.exit(0);
	}
}
