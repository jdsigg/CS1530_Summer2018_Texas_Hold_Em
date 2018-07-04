import java.util.HashMap;
import java.io.IOException;
import java.util.stream.Stream;

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
	in at the end of the round. Block comments were left
	for future individual testing purposes
    */
    private static void checkWinner(Player[] players)
    {
		/*Card[] hand = new Card[2];
		
		//hand2 = players[1].getCurrentHand();
		
		Card c1 = new Card(Card.Suit.Clubs, Card.Rank.Jack);
		Card c2 = new Card(Card.Suit.Clubs, Card.Rank.Queen);
		Card c3 = new Card(Card.Suit.Clubs, Card.Rank.Queen);
		Card c4 = new Card(Card.Suit.Spades, Card.Rank.Queen);
		Card c5 = new Card(Card.Suit.Clubs, Card.Rank.Nine);
		
		//add comm cards too - loop to do this 
		//loop for every new player
		Card[] currHand = new Card[7];*/
		
		
		
		//------------------------------------------------------------------------------

		Card[] currHand = new Card[7];
		HashMap<String, int[]> scores = new HashMap<String, int[]>();
		HashMap<String, Integer> ties = new HashMap<String, Integer>();
		HashMap<Integer, String> handScores = new HashMap<Integer, String>();
		HashMap<Integer, String> cardScores = new HashMap<Integer, String>();
		
		int score = 0;
		
		handScores.put(1, "High Card");
		handScores.put(2, "Pair");
		handScores.put(3, "Two Pair");
		handScores.put(4, "Three of a Kind");
		handScores.put(5, "Straight");
		handScores.put(6, "Flush");
		handScores.put(7, "Full House");
		handScores.put(8, "Four of a Kind");
		handScores.put(9, "Straight Flush");
		handScores.put(10, "Royal Flush");
		
		cardScores.put(2, "Two");
		cardScores.put(3, "Three");
		cardScores.put(4, "Four");
		cardScores.put(5, "Five");
		cardScores.put(6, "Six");
		cardScores.put(7, "Seven");
		cardScores.put(8, "Eight");
		cardScores.put(9, "Nine");
		cardScores.put(10, "Ten");
		cardScores.put(11, "Jack");
		cardScores.put(12, "Queen");
		cardScores.put(13, "King");
		cardScores.put(14, "Ace");
		
		for (int i =0; i<5; i++)
		{
			currHand[i] = dealer.getCommCards()[i];
		}
		
		for(int i=0; i<players.length; i++)
		{
			/*hand = players[i].getCurrentHand();
			currHand[0] = hand[0];
			currHand[1] = hand[1];
			currHand[2] = c1;
			currHand[3] = c2;
			currHand[4] = c3;
			currHand[5] = c4;
			currHand[6] = c5;*/
			
			score = 0;
			String name = players[i].getName();
			
			logString("Checking "+name+"'s hand...");;
			
			//Systematically check hand, starting at best result to worst
			//If the hand passes the test, assign their name and winning 5 cards to Map
			//Five cards + their score are held in an array that will then be used
			//Score is tested against max, and winning five cards determine ties
			
			if(Hands.royalFlush(currHand)[0] == 10) {
				logString("->Player somehow has a royal flush");
				scores.put(players[i].getName(), Hands.royalFlush(currHand)); 	//Give player a score
			}
			else if(Hands.straightFlush(currHand)[0] == 9) {
				logString("->Player has straight flush");
				scores.put(players[i].getName(), Hands.straightFlush(currHand)); 	//Give player a score
			}
			else if(Hands.fourOfAKind(currHand)[0] == 8) {
				logString("->Player has four of a kind");
				scores.put(players[i].getName(), Hands.fourOfAKind(currHand)); 	//Give player a score
			}
			else if(Hands.fullHouse(currHand)[0] == 7) {
				logString("->Player has full house");
				scores.put(players[i].getName(), Hands.fullHouse(currHand)); 	//Give player a score
			}
			else if(Hands.flush(currHand)[0] == 6) {
				logString("->Player has a flush");
				scores.put(players[i].getName(), Hands.flush(currHand)); 	//Give player a score
			}
			else if(Hands.straight(currHand)[0] == 5) {
				logString("->Player has straight");
				scores.put(players[i].getName(), Hands.straight(currHand)); 	//Give player a score
			}
			else if(Hands.threeOfAKind(currHand)[0] == 4) {
				 logString("->Player has three of a kind");
				 scores.put(players[i].getName(), Hands.threeOfAKind(currHand)); 	//Give player a score
			}
			else if(Hands.twoPair(currHand)[0] == 3) {
				logString("->Player has two pair");
				scores.put(players[i].getName(), Hands.twoPair(currHand)); 	//Give player a score
			}
			else if(Hands.onePair(currHand)[0] == 2) {
				logString("->Player has a pair");
				scores.put(players[i].getName(), Hands.onePair(currHand)); 	//Give player a score
			}
			else if(Hands.highCard(currHand)[0] == 1) {
				logString("->Player only has high card");
				scores.put(players[i].getName(), Hands.highCard(currHand)); 	//Give player a score
			}
		}
		
		int max = 0;
		String winner = "";
		Player winningPlayer = null;
		
		String maxPlayer = "";
		int[] maxHand = new int[6];
		
		String challengerPlayer = "";
		int[] challengerHand = new int[6];
		
		//DETERMINING WINNER AND ANY TIES...
		
		for(int i = 0; i < players.length; i++)
		{
			if(scores.get(players[i].getName())[0] > max)
			{			
				max = scores.get(players[i].getName())[0];
				winner = players[i].getName();
				winningPlayer = players[i];
			}
			else if(scores.get(players[i].getName())[0] == max)
			{
				//loop through players to find who owns current max
				//we need their hand to break the tie with the current challenger
				for(int x = 0; x < players.length; x++)
				{
					if(max == scores.get(players[x].getName())[0])
					{
						maxPlayer = players[x].getName();
						maxHand = scores.get(players[x].getName());
						break;
					}
				}
				
				challengerPlayer = players[i].getName();
				challengerHand = scores.get(players[i].getName());
				boolean addToTies = true;
				
				//loop not all the way through (best five card hand??)
				//potential bug
				for(int y = 1; y < challengerHand.length; y++)
				{
					if(maxHand[y] < challengerHand[y])
					{
						max = scores.get(players[i].getName())[0];
						winner = players[i].getName();
						winningPlayer = players[i];
						addToTies = false;
						break;
					}
					else if(maxHand[y] > challengerHand[y])
					{
						//max determined prior won against the challenger
						//no changes needed, max stays. No ties here
						addToTies = false;
					}
				}
				
				//If we have a true tie, add them to ties hashmap to print later
				//If anyone else ties, they get added here as well
				//Max and winner info for future comparison need no updating
				if(addToTies)
				{
					ties.put(maxPlayer, maxHand[0]);
					ties.put(challengerPlayer, challengerHand[0]);
				}
				
			}
		}
		
		if(!ties.containsValue(max))
		{
			//we found a max greater than the previously found tie (if applicable), so max wins
			//highCard = cardScores.get(scores.get(winner)[1]);
			System.out.println(winner + " wins with a " + handScores.get(max) + "!");
		}
		else 
		{
			//the max hasnt been beat since this tie was found, so the ties split the pot
			System.out.println("We have a tie! Players that tied:");
			Stream.of(ties.keySet().toArray())
                .forEach(System.out::println);	
			System.out.println("They both won with a " + handScores.get(max) + " and will split the pot.");
		}
		
		//Show the pot
		//gameBoard.updatePot();
		
		//Show the min bet
		//gameBoard.updateBet();
		
		//Log who won and the money they receive
		//logString(winner + " won the hand!");
		//logString(winner + " gets money from pot: $"+dealer.getPot());
		
		//Add that money to their pot
		//winningPlayer.updateMoney(dealer.getPot() + winningPlayer.getMoney());
		
		//logString(winner+ "'s money is now: $"+winningPlayer.getMoney());
		
		//Clear the pot
		//dealer.updatePot(0);
		
		//Clear winner's bet (for now, only human player can change their bet. This should be replaced with a clear all player bets)
		//winningPlayer.setBet(0);
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
	public static void logString(String message)
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
	
	/*public static void main(String[] args) {
		Card c1 = new Card(Card.Suit.Diamonds, Card.Rank.Jack);
		Card c2 = new Card(Card.Suit.Spades, Card.Rank.Ten);
		Card c3 = new Card(Card.Suit.Hearts, Card.Rank.Jack);
		Card c4 = new Card(Card.Suit.Hearts, Card.Rank.Nine);
		Player p1 = new Player("justin");
		Player p2 = new Player("anderson");
		
		p1.addCard(c1);
		p1.addCard(c3);
		p2.addCard(c2);
		p2.addCard(c4);
		
		Player[] players = new Player[2];
		players[0] = p1;
		players[1] = p2;
		
		checkWinner(players);
		
	}*/
	
	
}
