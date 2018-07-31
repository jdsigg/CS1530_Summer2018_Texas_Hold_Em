import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.io.*;
import java.util.Random;
import javax.swing.Timer;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JOptionPane;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import java.awt.Dimension;

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
		//players[0].updateMoney(25); // Testing purposes

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
		String[] buttons = {"Yes, choose from defaults", "Yes, take my own (webcam)", "No, I am boring"};

		int returnValue = JOptionPane.showOptionDialog(null, "Would you like to choose an avatar?", "Avatar Option",
				JOptionPane.WARNING_MESSAGE, 0, null, buttons, null);

		if(returnValue == 0) //picked yes, display file chooser
		{
			JFileChooser popUp = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "png", "jpeg");

			popUp.setFileFilter(filter);

			popUp.setCurrentDirectory(new File("./src/main/resources/img/avatars/"));

			int returnVal = popUp.showOpenDialog(null);

			if(returnVal == JFileChooser.APPROVE_OPTION)
			{
				File pic =  popUp.getSelectedFile();

				BufferedImage img = null;
				try
				{
					img = ImageIO.read(pic);
				}
				catch (IOException e)
				{
					System.err.println("Issue opening photo");
				}

				playerContainers[0].setAvatar(new ImageIcon(img), 1);
			}

		}
		else if(returnValue == 1)
		{
			//Display webcam here

			Webcam webcam = Webcam.getDefault();

			//webcam.setViewSize(new Dimension(176, 144));

			webcam.setViewSize(WebcamResolution.VGA.getSize());

			webcam.open();

			WebcamPanel panel = new WebcamPanel(webcam);
			panel.setFPSDisplayed(true);
			panel.setDisplayDebugInfo(true);
			panel.setImageSizeDisplayed(true);
			panel.setMirrored(true);

			JFrame window = new JFrame("Webcam");
			window.add(panel);
			window.setResizable(true);
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.pack();
			window.setVisible(true);

			try
			{
				String[] takePhotoBtn = {"Take Photo!"};

				int returnValue2 = JOptionPane.showOptionDialog(null, "Take the avatar photo when you are ready!", "Avatar Capture Option",
						JOptionPane.WARNING_MESSAGE, 0, null, takePhotoBtn, null);
				ImageIO.write(webcam.getImage(), "PNG", new File(".//src//main//resources//img//avatars//avatarCapture.png"));

				//Assign image to player avatar here
				ImageIcon imageIcon = new ImageIcon(".//src//main//resources//img//avatars//avatarCapture.png");

				playerContainers[0].setAvatar(imageIcon, 0);
				window.setVisible(false);
				webcam.close();
			}
			catch(IOException e)
			{
				System.out.println("Error taking photo");
			}
		}

		String extension = "./src/main/resources/img/avatars/";

		ArrayList<String> avatars = new ArrayList<>(Arrays.asList("Bart_Simpson.jpg",
		"Farnan.jpg", "Laboon.jpg", "Misurda.jpg", "Patrick_Star.jpg", "Ramirez.jpg",
		"spongebob.png", "ninja.png", "Alan.jpg", "Bee.jpeg", "Bob_Ross.jpeg", "Brian_Griffin.jpeg", "Dan_Budny.jpg", "Doug_Hangover.jpg", "Homer_Simpson.jpg", "Leslie_Chow.jpg", "Lois_Griffin.jpg", "Lord_F.jpg", "Luigi.png", "Marge_Simpson.png", "Mario.png", "Meg_Griffin.png", "Peter_Griffin.jpg", "Phil_Hangover.jpg", "Stu_Hangover.jpg", "Waluigi.png", "Wario.jpg"));

		Collections.shuffle(avatars);

		for(int i = 1; i < playerContainers.length; i++)
			playerContainers[i].setAvatar(new ImageIcon(extension + avatars.get(i)), 1);
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
