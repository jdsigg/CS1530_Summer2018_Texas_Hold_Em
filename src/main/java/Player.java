import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

class Player
{

	/*
    Initialize Variables
	*/
	private String name;
	private int money;
	private Card [] currentHand = new Card[2];
	private int currentHandIndex;
	private int bet;
	private int status;
	private int playerType;
	private Random gen;
	private boolean hasBet;
	
	private boolean dealer;
	private boolean smallBlind;
	private boolean bigBlind;

	public Player()
	{
		//Init Constructor for Player
		this.money = 1000;
		currentHandIndex = 0;
		this.bet = 0;
		this.status = 0;
		this.playerType = 0;
	}

	public Player(String name)
	{
		//Constructor for Player
		this.name = name;
		this.money = 1000;
		currentHandIndex = 0;
		this.bet = 0;
		this.status = 0;
		this.playerType = 0;
		gen = new Random();
	}

	/*
	Method to get player's stack (Money)
	*/
	public int getMoney()
	{
		return this.money;
	}

	/*
    Method to get player's name
	*/
	public String getName()
	{
		return this.name;
	}

	/*
    Method to update player's stack (Money)
	*/
	public void updateMoney(int money)
	{
		this.money = money;
	}

	/*
    Method to set player's name
    NOTE: you can set the player's name in the constructor
	*/
	public void setName(String name)
	{
		this.name = name;
	}

	/*
    Method to deal player a Card
    You can add ONE card at a time
	*/
	public void addCard(Card card)
	{
		assert(currentHandIndex < 2); //Make sure player only has two cards
		this.currentHand[currentHandIndex] = card;
		currentHandIndex++;
	}

	/*
    Method to get player's current hand
    returns a array of type Card
	*/
	public Card[] getCurrentHand()
	{
		return this.currentHand;
	}

	/*
	Method to return the players cards to the deck
	*/
	public void wipeHand()
	{
		currentHandIndex = 0;
		this.currentHand = new Card[2];
	}

	/*
	Method to set the players bet
	*/
	public void setBet(int newBet)
	{
		this.bet += newBet;
	}

	/*
	Method to get the players bet
	*/
	public int getBet()
	{
		return bet;
	}

	/*
	Method that sets that players current status.
	0 = In the game.
	1 = Out for the round.
	2 = Out of the game for good.
	*/
	public void setStatus(int newStatus)
	{
		this.status = newStatus;
	}

	/*
	Method that gets the player's current status.
	*/
	public int getStatus()
	{
		return this.status;
	}

	/*
	Method to set the player type, human or AI.
	Human = 0
	AI = 1
	*/
	public void setPlayerType(int newPlayerType)
	{
		this.playerType = newPlayerType;
	}

	/*
	Method to get player type.
	*/
	public int getPlayerType()
	{
		return this.playerType;
	}
	
	/*
	Returns true if player is in for the round
	*/
	public boolean isIn()
	{
		return (this.status == 0);
	}
	
	public int bet(int previousBet, int numberOfRaises)
	{
		int betPercent = 1;
		int actualBet = 0;
		
		switch(this.playerType)
		{
			case 0: //human player
				String[] buttons = null;
				
				if(previousBet == 0 && numberOfRaises < 3) //check case
				{
					buttons = new String[3];
					buttons[0] = "Fold";
					buttons[1] = "Check";
					buttons[2] = "Raise $20";
				}
				
				if(previousBet > 0 && numberOfRaises < 3) //check case
				{
					buttons = new String[3];
					buttons[0] = "Fold";
					buttons[1] = "Call";
					buttons[2] = "Raise $20";
				}
				
				if(numberOfRaises == 3)
				{
					buttons = new String[2];
					buttons[0] = "Fold";
					buttons[1] = "Call";
				}
				
				
				int returnValue = JOptionPane.showOptionDialog(null, "It is your turn to bet!", "Player Turn",
				JOptionPane.WARNING_MESSAGE, 0, null, buttons, null);
				
				
				if(returnValue == 0)
				{
					actualBet = -1;
				}
				else if(returnValue == 1)
				{
					actualBet = previousBet - this.getBet();
				}
				else if(returnValue == 2)
				{
					actualBet = previousBet - this.getBet();
					actualBet += 20;
				}
					
				break;
			case 1: //AI player
			
				/*
				int foldOrBet = gen.nextInt(2);
				if(foldOrBet == 0)
				{
					actualBet = -1;
				}
				else
				{*/
					if(numberOfRaises < 3)
					{
						betPercent = gen.nextInt(100) + 1; //forces computer to bet between 1 and 100 percent of their pot
						actualBet = (this.money * betPercent) / 1000; //calculates bet off of betPercent
						//actualBet = 20;
					}
					else
						actualBet = previousBet;	
				//}
				
				try
				{
					TimeUnit.SECONDS.sleep(2);
				}
				catch (InterruptedException ex)
				{
					
				}
				break;
		}
		
		return actualBet;
	}
	
	public void setHasBet(boolean value)
	{
		this.hasBet = value;
	}
	
	public void subtractFromMoney(int toThePot)
	{
		this.money -= toThePot;
	}
	
	public void setDealerStatus(boolean status)
	{
		this.dealer = status;
	}
	
	public void setSmallBlindStatus(boolean status)
	{
		this.smallBlind = status;
	}
	
	public void setBigBlindStatus(boolean status)
	{
		this.bigBlind = status;
	}
	
	public boolean isDealer()
	{
		return this.dealer;
	}
	
	public boolean isSmallBlind()
	{
		return this.smallBlind;
	}
	
	public boolean isBigBlind()
	{
		return this.bigBlind;
	}	
}
