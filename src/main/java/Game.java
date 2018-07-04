import java.util.HashMap;
import java.io.IOException;
import java.util.Random;

class Game
{
	private int numberOfPlayers;
	private Player[] activePlayers;
	private Player[] realPlayers;
	private int state;
	private GameBoard gameBoard;
	private Dealer dealer;
	private Logger logger;
	private int playersStillInGame;
	
	private int dealerChip;
	private int smallBlind;
	private int bigBlind;
	
	private Random gen;
	
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
		this.playersStillInGame = this.numberOfPlayers;
		
		gen = new Random();
    }
	
	public void initializeBlinds()
	{
		//if number of players == 2 --> assign only small and big blind, dealer also being the small blind
		
		if(playersStillInGame == 2)
		{
			this.dealerChip = gen.nextInt(numberOfPlayers);
			this.smallBlind = this.dealerChip;
			this.bigBlind = (this.dealerChip + 1) % this.numberOfPlayers;
		}
		else
		{
			this.dealerChip = gen.nextInt(numberOfPlayers); //set the dealer
			this.smallBlind = (this.dealerChip + 1) % this.numberOfPlayers; //put small blind left of dealer
			this.bigBlind = (this.dealerChip + 2) % this.numberOfPlayers; //put small blind two left of dealer
		}
		
		realPlayers[dealerChip].setDealerStatus(true);
		realPlayers[smallBlind].setSmallBlindStatus(true);
		realPlayers[bigBlind].setBigBlindStatus(true);
		
		gameBoard.displayDealerStatus(dealerChip);
		gameBoard.displaySmallBlindStatus(smallBlind);
		gameBoard.displayBigBlindStatus(bigBlind);
	}
	
	public void moveBlinds()
	{
		int count = 0;
		
		realPlayers[dealerChip].setDealerStatus(false);
		gameBoard.displayDealerStatus(dealerChip);
		
		dealerChip = (dealerChip+1) % numberOfPlayers;
		
		while(count < numberOfPlayers) //go over every player, assigning new dealer and breaking when found
		{
			if(realPlayers[dealerChip].isIn())
			{
				realPlayers[dealerChip].setDealerStatus(true);
				break;
			}
			dealerChip = (dealerChip + 1) % numberOfPlayers;
			count++;
		}
		
		if(playersStillInGame == 2)
		{
			realPlayers[smallBlind].setSmallBlindStatus(false);
			gameBoard.displaySmallBlindStatus(smallBlind);
			smallBlind = dealerChip;
			
			realPlayers[smallBlind].setSmallBlindStatus(true);
			realPlayers[bigBlind].setBigBlindStatus(false);
			gameBoard.displayBigBlindStatus(bigBlind);
			
			bigBlind = (smallBlind + 1) % numberOfPlayers;
			realPlayers[bigBlind].setBigBlindStatus(true);
		}
		else
		{
			count = 0;
			realPlayers[smallBlind].setSmallBlindStatus(false);
			gameBoard.displaySmallBlindStatus(smallBlind);
			smallBlind = (dealerChip+1) % numberOfPlayers;
			
			while(count < numberOfPlayers)
			{
				if(realPlayers[smallBlind].isIn())
				{
					realPlayers[smallBlind].setSmallBlindStatus(true);
					break;
				}
				smallBlind = (smallBlind + 1) % numberOfPlayers;
				count++;
			}
			
			count = 0;
			realPlayers[bigBlind].setBigBlindStatus(false);
			gameBoard.displayBigBlindStatus(bigBlind);
			bigBlind = (smallBlind + 1) % numberOfPlayers;
			
			while(count < numberOfPlayers)
			{
				if(realPlayers[bigBlind].isIn())
				{
					realPlayers[bigBlind].setBigBlindStatus(true);
					break;
				}
				bigBlind = (bigBlind + 1) % numberOfPlayers;
				count++;
			}
		}
		
		gameBoard.displayDealerStatus(dealerChip);
		gameBoard.displaySmallBlindStatus(smallBlind);
		gameBoard.displayBigBlindStatus(bigBlind);
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
			betRound();
			
			int numberIn = 0;
			for(int i = 0; i < realPlayers.length; i++)
			{
				if(realPlayers[i].isIn())
					numberIn++;
			}
			
			activePlayers = new Player[numberIn];
			
			int j = 0;
			for (int i=0; i<realPlayers.length; i++)
			{
				if(realPlayers[i].isIn())
				{
					activePlayers[j] = realPlayers[i];
					j++;
				}
			}
			checkWinner(activePlayers);
			resetGame();
			moveBlinds();
		}
		else
		{
			System.out.println(state);
			betRound();
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
		//gameBoard.updateBet();
		
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
		
		
		//clear all player bets in player classes
		//split the pot amongst winners
		//clear the pot
		//show the winner
		//shift pointers and re-assign new dealer chip, small blind, and big blind
		//update gui to show new values
	}
	
	public void betRound()
	{
		System.out.println("Dealer is: "+dealerChip);
		System.out.println("Small Blind is: "+smallBlind);
		System.out.println("Big Blind is : "+bigBlind);
		
		int numberOfRaises = 0;
		int minRoundBet = 20;
		int currentLead = bigBlind;
		int nextPlayer = (bigBlind + 1) % numberOfPlayers;
		int currentPlayer = nextPlayer;
		int previousBet = 0;
		int toThePot = 0;
		
		if(state == 1)
		{
			realPlayers[smallBlind].setBet(10);
			realPlayers[smallBlind].subtractFromMoney(10);
			realPlayers[bigBlind].setBet(20);
			realPlayers[bigBlind].subtractFromMoney(20);
			dealer.updatePot(dealer.getPot() + 30);
			
			gameBoard.changePlayerPot(smallBlind);
			gameBoard.changePlayerPot(bigBlind);
			gameBoard.updatePot();
		}
		
		gameBoard.updateMinBet(minRoundBet);
		
		while(currentPlayer != currentLead)
		{
			if(numberOfRaises == 3)
			{
				break;
			}
			System.out.println("Current Player is: "+currentPlayer);
			System.out.println("Current Lead is: "+currentLead);
			if(realPlayers[currentPlayer].isIn())
			{
				gameBoard.highlightCurrentBetter(currentPlayer);
				
				toThePot = realPlayers[currentPlayer].bet(previousBet, numberOfRaises);
				
				if(toThePot == -1)
				{
					realPlayers[currentPlayer].setStatus(1);
					gameBoard.cancelCurrentBetter(currentPlayer);
				}
				else
				{
					gameBoard.clearCurrentBetter(currentPlayer);
					
					if(realPlayers[currentPlayer].getBet() + toThePot <= minRoundBet)
					{
						toThePot += minRoundBet - realPlayers[currentPlayer].getBet() - toThePot;
					}
					else
					{
						numberOfRaises++;
						currentLead = currentPlayer;
						
						minRoundBet = realPlayers[currentPlayer].getBet() + toThePot;
					}
					
					previousBet = realPlayers[currentPlayer].getBet() + toThePot;
					
					realPlayers[currentPlayer].setBet(toThePot); //update the current player's bet
					realPlayers[currentPlayer].subtractFromMoney(toThePot);
					
					dealer.updatePot(dealer.getPot() + toThePot);
					
					System.out.println("Current Player bet: "+(realPlayers[currentPlayer].getBet()));
					
					gameBoard.changePlayerPot(currentPlayer);
					gameBoard.updatePot();
					gameBoard.updateMinBet(minRoundBet);
				}
			}
			nextPlayer = (nextPlayer + 1) % numberOfPlayers;
			currentPlayer = nextPlayer;
		}
		
		if(numberOfRaises == 3) //if we have three raises left, finish betting for the round
		{
			while(currentPlayer != currentLead)
			{
				if(realPlayers[currentPlayer].isIn())
				{
					gameBoard.highlightCurrentBetter(currentPlayer);
				
					toThePot = realPlayers[currentPlayer].bet(previousBet, numberOfRaises);
				
					if(toThePot == -1) //give players chance to fold
					{
						realPlayers[currentPlayer].setStatus(1);
						gameBoard.cancelCurrentBetter(currentPlayer);
					}
					else //if they don't fold, force them to call
					{
						gameBoard.clearCurrentBetter(currentPlayer);
						
						toThePot += minRoundBet - realPlayers[currentPlayer].getBet() - toThePot;
						
						previousBet = realPlayers[currentPlayer].getBet() + toThePot;
					
						realPlayers[currentPlayer].setBet(toThePot); //update the current player's bet
						realPlayers[currentPlayer].subtractFromMoney(toThePot);
						
						dealer.updatePot(dealer.getPot() + toThePot);
						
						System.out.println("Current Player bet: "+(realPlayers[currentPlayer].getBet()));
						
						gameBoard.changePlayerPot(currentPlayer);
						gameBoard.updatePot();
						gameBoard.updateMinBet(minRoundBet);
					}
				}
				currentPlayer = (currentPlayer+1) % numberOfPlayers;
			}
		}
		
		for(int i = 0; i < realPlayers.length; i++)
			realPlayers[i].setBet(0);
	}
	
	public void runMe()
	{
		while(true)
		{
			nextState();
		}
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
		catch(IOException | NullPointerException e)
		{
			System.err.println("Failed logging message: "+message);
			System.err.println("Error: "+e.toString());
		}
	}
}
