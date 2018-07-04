import java.util.HashMap;
import java.io.IOException;

class Game
{
	private int numberOfPlayers;
	private Player[] activePlayers;
	private Player[] realPlayers;
	private int state;
	private GameBoard gameBoard;
	private Dealer dealer;
	private Logger logger;

    /*
	Constructor for Game Class
    */
    public Game(GameBoard gameBoard, Logger logger, Player[] players, Dealer dealer)
    {
		this.numberOfPlayers = players.length;
		this.state = 1;
		this.gameBoard = gameBoard;
		this.realPlayers = players;
		this.logger = logger;
		this.dealer = dealer;
    }

    /*
	Method to deal the players

	Did not use in this implementation. Leaving for future.
    */
    private void dealToPlayer()
    {

    }

    /*
    Method to deal five commcards[5]

	Did not use in this implementation. Leaving for future.
    */
    private void dealCommCards()
    {

    }

    /*
	Method to call when a player, human or AI is
	going to fold. Method must accept a player. The
	player passed in is the player that is going to
	fold.

	Did not use in this implementation. Leaving for future.
    */
    private void playerFold(Player player)
    {

    }

	/*
	Called when the player bets. In the future, other players will bet instead
	*/
	public void foldEveryone()
	{
		activePlayers = new Player[1];
		activePlayers[0] = realPlayers[0];

		dealer.updatePot(activePlayers[0].getBet());

		checkWinner(activePlayers);
		resetGame();
	}

	/*
	State machine controller. Calling moves game from one hand to the next.
	Betting immediately ends the hand.
	*/
	public void nextState()
	{
		if(state != 1 && state!= 0)
		{
			for(int i = 0; i < realPlayers.length; i++)
			{
				Player temp = realPlayers[i];
				logString(temp.getName()+" calls.");
			}
		}

		if(state==1)
		{
			startNewHand();
		}
		gameBoard.displayCommCard(state);

		if (state == 5)
		{
			activePlayers = new Player[numberOfPlayers];

			for (int i=0; i<activePlayers.length; i++)
			{
				activePlayers[i] = realPlayers[i];
			}
			checkWinner(activePlayers);
			resetGame();
		}
		else
		{
			state++;
		}
	}

	/*
	Resets all necessary data structures for a new hand
	*/
	public void resetGame()
	{
		for (int i=0; i<realPlayers.length; i++)
		{
			Player temp = realPlayers[i];
			Card[] tempCards = temp.getCurrentHand();
			dealer.returnCard(tempCards[0]);
			dealer.returnCard(tempCards[1]);
			temp.wipeHand();
			temp.setBet(0);
		}
		dealer.returnCommCards();
		dealer.wipeCommCards();
		state = 0;
	}

	/*
	Process of shuffling and dealing to players and community
	*/
	public void startNewHand()
	{
		logString("Shuffling deck...");
		dealer.shuffle();

		logString("Dealing...");


		for (int i=0; i<realPlayers.length; i++)
		{
			Player temp = realPlayers[i];
			dealer.dealCard(temp);
			dealer.dealCard(temp);

			logString(temp.getName()+" dealt: "+temp.getCurrentHand()[0].toString()+" and "
														+temp.getCurrentHand()[1].toString());

			if(i==0){
				gameBoard.displayPlayerHand(i);
			}
		}
		dealer.dealCommCards();
	}

    /*
	Method to call when a player, human or AI is
	going to check. Method must accept a player.
	The player passed in is the player that is
	going to check.

	Did not use in this implementation. Will leave for further design
    */
    private void playerCheck(Player player)
    {

    }

    /*
	Method to check, after the round is over, who won.
	It accepts an array of all of the players who are still
	in at the end of the round.
    */
    private void checkWinner(Player[] players)
    {

		Card[] currHand = new Card[7];
		HashMap<String, Integer> scores = new HashMap<String, Integer>();
		int score = 0;

		for (int i =0; i<5; i++)
		{
			currHand[i] = dealer.getCommCards()[i];
		}

		for(int i=0; i<players.length; i++)
		{

			currHand[5] = players[i].getCurrentHand()[0];
			currHand[6] = players[i].getCurrentHand()[1];
			score = 0;

			String name = players[i].getName();

			logString("Checking "+name+"'s hand...");;

			//Systematically check hand, starting at best result to worst
			//If the hand passes the test, assign score to player

			if(Hands.royalFlush(currHand)) {
				logString("->Player somehow has a royal flush");
				scores.put(players[i].getName(), 10); 	//Give player a score
			}
			else if(Hands.straightFlush(currHand)) {
				logString("->Player has straight flush");
				scores.put(players[i].getName(), 9); 	//Give player a score
			}
			else if(Hands.fourOfAKind(currHand)) {
				logString("->Player has four of a kind");
				scores.put(players[i].getName(), 8); 	//Give player a score
			}
			else if(Hands.fullHouse(currHand)) {
				logString("->Player has full house");
				scores.put(players[i].getName(), 7); 	//Give player a score
			}
			else if(Hands.flush(currHand)) {
				logString("->Player has a flush");
				scores.put(players[i].getName(), 6); 	//Give player a score
			}
			else if(Hands.straight(currHand)) {
				logString("->Player has straight");
				scores.put(players[i].getName(), 5); 	//Give player a score
			}
			else if(Hands.threeOfAKind(currHand)) {
				 logString("->Player has three of a kind");
				 scores.put(players[i].getName(), 4); 	//Give player a score
			}
			else if(Hands.twoPair(currHand)) {
				logString("->Player has two pair");
				scores.put(players[i].getName(), 3); 	//Give player a score
			}
			else if(Hands.onePair(currHand)) {
				logString("->Player has a pair");
				scores.put(players[i].getName(), 2); 	//Give player a score
			}
			else if(Hands.highCard(currHand)) {
				logString("->Player only has high card");
				scores.put(players[i].getName(), 1); 	//Give player a score
			}
		}

		int max = 0;
		String winner = "";
		Player winningPlayer = null;

		//Best score wins
		//Future deliverable will require a tie breaking method for tied hands
		for(int i = 0; i < players.length; i++)
		{
			if(scores.get(players[i].getName()) > max)
			{
				max = scores.get(players[i].getName());
				winner = players[i].getName();
				winningPlayer = players[i];
			}
		}
		//Show the pot
		gameBoard.updatePot();

		//Show the min bet
		gameBoard.updateBet();

		//Log who won and the money they receive
		logString(winner + " won the hand!");
		logString(winner + " gets money from pot: $"+dealer.getPot());

		//Add that money to their pot
		winningPlayer.updateMoney(dealer.getPot() + winningPlayer.getMoney());

		logString(winner+ "'s money is now: $"+winningPlayer.getMoney());

		//Clear the pot
		dealer.updatePot(0);

		//Clear winner's bet (for now, only human player can change their bet. This should be replaced with a clear all player bets)
		winningPlayer.setBet(0);
	}

	/*
	Used for testing. Leaving for future testing of state machine
	*/
	public int getState()
	{
		return state;
	}

	/*
	Given a message, attempt to log it
	*/
	public void logString(String message)
	{
		try
		{
			logger.log(message);
		}
		catch(Exception e)
		{
			System.err.println("Failed logging message: "+message);
			System.err.println("Error: "+e.toString());
		}
	}
}
