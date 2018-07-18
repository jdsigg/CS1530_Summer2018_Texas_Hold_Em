import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.io.*;
import java.util.Random;
import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
