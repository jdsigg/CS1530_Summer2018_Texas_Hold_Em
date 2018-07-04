import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.io.*;

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

	public void gameSetup(String playerName, int numberOfOpponents)
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
		//set identifiers

		for(int i = 1; i < numberOfPlayers; i++)
		{
			players[i] = new Player(names.get(i));
			players[i].setPlayerType(1);
		}
		//set identifiers

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
			playerContainers[i] = new PlayerContainer(players[i], game);
		}
		
		gameBoard.showPlayers();
		game.initializeBlinds();
		
		Thread gameThread = new Thread(() -> {
			game.runMe();
		});
		gameThread.start();
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
