import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.FlowLayout;

import java.awt.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import java.io.IOException;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class GameBoard extends JFrame
{
	private PlayerContainer[] players;
	private int numberOfOpponents;
	private ArrayList<Player> opponents;
	private Dealer dealer;
	private DealerContainer dealerBox;
	private Game game;
	private boolean isFirstHand = true;
	
	public GameBoard(String title)
	{
		super(title);
		
		this.setLayout(new FlowLayout());
		
		this.setSize(1300, 900);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				exitGame();
			}
		});
	}
	
	public void createPlayerFrames(String playerName, int numberOfOpponents, Game game)
	{
		this.game = game;
		this.numberOfOpponents = numberOfOpponents;
		players = new PlayerContainer[numberOfOpponents+1];
		
		//Create player containers to display
		for(int i = 0; i < numberOfOpponents+1; i++)
		{
			PlayerContainer temp = new PlayerContainer(game);
			players[i] = temp;
			if(i != 0)
				temp.hideButtons();
			this.add(temp);
		}
		game.setPlayerContainers(players);
		this.players = players;
		
		//Create dealer container to display
		dealerBox = new DealerContainer(dealer);
		
		this.add(dealerBox);
		this.setVisible(true);
		
		//Call function to assign players to each container
		assignPlayers(playerName);
	}
	
	public void assignSharedVariables(Dealer dealer)
	{
		this.dealer = dealer;
	}
	
	private void assignPlayers(String playerName)
	{
		ArrayList<String> names = new ArrayList<>(Arrays.asList("Jacques S.", 
		"Seymour B.", "Mike R.", "Ollie T.", "Al C.", 
		"Oliver C.", "Libby D.", "Sarah N.", "Robin B.", "Hugh J."));
		
		Collections.shuffle(names);
		
		//Create ArrayList of opponents
		opponents = new ArrayList(numberOfOpponents);
		
		//Set 0th player to be human
		players[0].setPlayer(new Player(playerName));
		
		//Set remaining players to be AI
		for(int i = 1; i < numberOfOpponents+1; i++)
		{
			Player temp = new Player(names.get(i));
			players[i].setPlayer(temp);
			opponents.add(temp);
		}
		
		//Iterate over players, showing their respective information
		for(int i = 0; i < players.length; i++)
		{
			PlayerContainer container = players[i];
			Player player = container.getPlayer();
			
			container.updateName();
			container.updateMoney();
		}
		game.startLogger();
	}
	
	/*
	Function that displays cards based on order of state machine
	*/
	public void displayCommCard(int state, Logger logger)
	{
		switch(state)
		{
			case 0:
				for (int i =0; i<players.length; i++)
				{
					displayBlanks(i);
				}
				displayDealerBlanks();
				updateBet();
				updatePot();
				break;
			case 1:
				players[0].setBetButton(true);
				break;
			case 2:
				logString("Flop: "+dealer.getCommCards()[0].toString()+", "+dealer.getCommCards()[1].toString()+", "
										+dealer.getCommCards()[2].toString(), logger);
				dealerBox.setCardOne();
				dealerBox.setCardTwo();
				dealerBox.setCardThree();
				break;
			case 3:
				logString("Turn: "+dealer.getCommCards()[3].toString(), logger);
				dealerBox.setCardFour();
				break;
			case 4:
				logString("River: "+dealer.getCommCards()[4].toString(), logger);
				dealerBox.setCardFive();
				break;
			case 5:
				for(int i = 1; i < players.length; i++)
					displayPlayerHand(i);
				players[0].setBetButton(false);
				break;
			default:
				break;
		}
	}
	
	/*
	Method to display the player's hand
	*/
	public void displayPlayerHand(int player)
	{
		players[player].setCardOne();
		players[player].setCardTwo();
	}
	
	/*
	Method to display blanks for the AI opponents.
	This will be done each hand after the reveal.
	*/
	public void displayBlanks(int player)
	{
		players[player].setBlanks();
	}
	
	/*
	Method to display blanks for the community cards
	*/
	public void displayDealerBlanks()
	{
		dealerBox.setBlanks();
	}
	
	/*
	Method to update the pot on the GUI
	*/
	public void updatePot()
	{
		dealerBox.setPot(Double.toString(dealerBox.getDealer().getPot()));
	}
	
	/*
	Method to update the current bet on the GUI
	*/
	public void updateBet()
	{
		//For now, bet can only be $20, and is always same as the pot
		dealerBox.setMinBet(Double.toString(dealerBox.getDealer().getPot()));
	}
	
	/*
	Method to send a string to the logger
	*/
	public void logString(String message, Logger logger)
	{
		try
		{
			logger.log(message);
		}
		catch(IOException e)
		{
			System.out.println("Failed logging message: "+message);
		}
	}
	
	/*
	Method to exit the game
	*/
	public void exitGame()
	{
		game.exit();
	}
}

