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
	private PlayerContainer[] playerContainers;
	private DealerContainer dealerContainer;
	private Game game;
	private TexasHoldEm caller;

	public GameBoard(String title, PlayerContainer[] playerContainers, DealerContainer dealerContainer, TexasHoldEm caller)
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

		this.playerContainers = playerContainers;
		this.dealerContainer = dealerContainer;
		this.caller = caller;
		this.setVisible(true);
	}

	/*
	Function that displays cards based on order of state machine
	*/
	public void displayCommCard(int state)
	{
		switch(state)
		{
			case 0:
				for (int i =0; i<playerContainers.length; i++)
				{
					displayBlanks(i);
				}
				displayDealerBlanks();
				//updateBet();
				updatePot();
				break;
			case 1:
				break;
			case 2:
			/*
				logString("Flop: "+dealer.getCommCards()[0].toString()+", "+dealer.getCommCards()[1].toString()+", "
										+dealer.getCommCards()[2].toString(), logger);
										*/
				dealerContainer.setCardOne();
				dealerContainer.setCardTwo();
				dealerContainer.setCardThree();
				break;
			case 3:
				//logString("Turn: "+dealer.getCommCards()[3].toString(), logger);
				dealerContainer.setCardFour();
				break;
			case 4:
				//logString("River: "+dealer.getCommCards()[4].toString(), logger);
				dealerContainer.setCardFive();
				break;
			case 5:
				for(int i = 1; i < playerContainers.length; i++)
					displayPlayerHand(i);
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
		playerContainers[player].setCardOne();
		playerContainers[player].setCardTwo();
	}
	
	public void displayCardBacks(int player)
	{
		playerContainers[player].setCardBacks();
	}
	
	public void displayOutForGood(int player)
	{
		playerContainers[player].displayOutForGood();
	}

	/*
	Method to display blanks for the AI opponents.
	This will be done each hand after the reveal.
	*/
	public void displayBlanks(int player)
	{
		playerContainers[player].setBlanks();
	}

	/*
	Method to display blanks for the community cards
	*/
	public void displayDealerBlanks()
	{
		dealerContainer.setBlanks();
	}

	/*
	Method to update the pot on the GUI
	*/
	public void updatePot()
	{
		dealerContainer.setPot();
	}

	/*
	Method to update the side pot on the GUI
	*/
	public void updateSidePot()
	{
		dealerContainer.setSidePot();
	}

	/*
	Method to reset the side pot on the GUI
	*/
	public void resetSidePot()
	{
		dealerContainer.resetSidePot();
	}

	/*
	Method to update the current bet on the GUI
	*/
	public void updateBet(int minBet)
	{
		//For now, bet can only be $20, and is always same as the pot
		dealerContainer.setMinBet(minBet);
	}

	/*
	Method to exit the game
	*/
	public void exitGame()
	{
		caller.exit();
	}

	public void showPlayers()
	{
		//Iterate over players, showing their respective information
		for(int i = 0; i < playerContainers.length; i++)
		{
			PlayerContainer container = playerContainers[i];
			this.add(container);

			container.updateName();
			container.updateMoney();
			container.setVisible(true);
		}

		this.add(dealerContainer);
		dealerContainer.setVisible(true);
		this.repaint();
		this.revalidate();
	}

	public void highlightCurrentBetter(int currentBetter)
	{
		playerContainers[currentBetter].blackBorder();
	}

	public void cancelCurrentBetter(int currentBetter)
	{
		playerContainers[currentBetter].redBorder();
	}

	public void clearCurrentBetter(int currentBetter)
	{
		playerContainers[currentBetter].clearBorder();
	}

	public void changePlayerPot(int player)
	{
		playerContainers[player].updateMoney();
	}

	public void updateMinBet(int minBet)
	{
		dealerContainer.setMinBet(minBet);
	}

	public void displayDealerStatus(int dealer)
	{
		playerContainers[dealer].displayDealer();
	}

	public void displaySmallBlindStatus(int smallBlind)
	{
		playerContainers[smallBlind].displaySmallBlind();
	}

	public void displayBigBlindStatus(int bigBlind)
	{
		playerContainers[bigBlind].displayBigBlind();
	}

	public void wipePlayer(int toWipe)
	{
		playerContainers[toWipe].setBlanks();
	}

	public void wipeDealer()
	{
		dealerContainer.setBlanks();
	}
}
