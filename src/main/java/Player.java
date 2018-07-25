import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import java.awt.Container;
import javax.swing.JLabel;
import javax.swing.JDialog;
import java.awt.FlowLayout;
import java.awt.Color;

import java.util.concurrent.atomic.AtomicInteger;

class Player
{

	
    // Initialize Variables
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

	private boolean timerMode;

	private JDialog timerDialog;
	private AtomicInteger timeRemaining;
	private Timer timer;

	public Player()
	{
		// Init Constructor for Player
		this.money = 1000;
		currentHandIndex = 0;
		this.bet = 0;
		this.status = 0;
		this.playerType = 0;
	}

	public Player(String name)
	{
		// Constructor for Player
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

	public void resetBet()
	{
		this.bet = 0;
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

	public int bet(int previousBet, int numberOfRaises, int state, int playersStillInGame)
	{
		int betPercent = 1;
		int actualBet = 0;
		int buttonBet = 0;
		
		switch(this.playerType)
		{
			case 0: // Human player

				Thread timerThread = new Thread();
				
				if(timerMode)
				{
					// Spawn a new thread that shows some GUI count down
					timerThread = new Thread(() -> {
						startTimer();
					});

					timerThread.start();
				}


				String[] buttons = null;
				if(this.getMoney() <= 0)
				{
					this.money = 0;
					break;
				}

				if(previousBet == 0 && numberOfRaises < 3)
				{
					buttons = new String[3];
					buttons[0] = "Fold";
					buttons[1] = "Check";
					buttons[2] = "Raise $20";
				}

				if(previousBet > 0 && numberOfRaises < 3)
				{
					buttonBet = previousBet - this.bet;
					buttons = new String[3];
					buttons[0] = "Fold";
					buttons[1] = "Call $" + buttonBet;
					buttons[2] = "Raise $20";
				}

				if(numberOfRaises == 3)
				{
					buttonBet = previousBet - this.bet;
					buttons = new String[2];
					buttons[0] = "Fold";
					buttons[1] = "Call $" + buttonBet;
				}


				int returnValue = JOptionPane.showOptionDialog(null, "It is your turn to bet!", "Player Turn",
				JOptionPane.WARNING_MESSAGE, 0, null, buttons, null);


				if(timerMode)
				{
					try
					{
						timerThread.join();
					}
					catch(InterruptedException ex)
					{

					}

				}



				// If timer is at or less than 0, fold
				if(timerMode && timeRemaining.get() <= 0)
				{
					actualBet = -1;
					timerDialog.setVisible(false);
					timer.stop();
					timerThread.interrupt();
				}
				else // Otherwise, proceed normally
				{
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
					if(timerMode)
					{
						timerDialog.setVisible(false);
						timer.stop();
						timerThread.interrupt();
					}
				}

				break;
			case 1: // AI player


				int foldOrBet = gen.nextInt(100);
				if (this.money <= 0)
				{
					actualBet = 0;
				}
				else if(foldOrBet <= 50 && numberOfRaises < 3 && state == 2 && playersStillInGame > 2)
				{
					actualBet = -1;
				}
				else
				{
					if(numberOfRaises < 3)
					{
						betPercent = gen.nextInt(100) + 1; // Forces computer to bet between 1 and 100 percent of their pot
						actualBet = (this.money * betPercent) / 1000; // Calculates bet off of betPercent
					}
					else
						actualBet = previousBet;
				}

				try
				{
					TimeUnit.MILLISECONDS.sleep(1000);
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

	public void setTimerMode(boolean mode)
	{
		this.timerMode = mode;
	}

	public void startTimer()
	{
		timerDialog = new JDialog();
		timerDialog.setLocationRelativeTo(null);
		timerDialog.setSize(340, 200);
		timerDialog.setLayout(new FlowLayout());

		Container pane = timerDialog.getContentPane();

		timeRemaining = new AtomicInteger(10); // 10 seconds for each timer

		JLabel timerTextLabel = new JLabel("Time Remaining: ");
		timerTextLabel.setFont(timerTextLabel.getFont().deriveFont(32.0f));
		pane.add(timerTextLabel);

		JLabel timerLabel = new JLabel(Integer.toString(timeRemaining.get()));
		timerLabel.setFont(timerLabel.getFont().deriveFont(32.0f));
		timerLabel.setForeground(Color.RED);

		pane.add(timerLabel);

		timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent evt)
			{
				if(timeRemaining.get() > 0)
					timeRemaining.getAndDecrement();

				if(timeRemaining.get() == 0)
				{
					timerLabel.setText("FOLD");
				}
				else
				{
					timerLabel.setText(Integer.toString(timeRemaining.get()));
				}
			}
		});
		timer.start();

		timerDialog.setVisible(true);
	}
}
