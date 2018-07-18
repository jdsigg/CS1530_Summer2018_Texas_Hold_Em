import java.util.HashMap;
import java.io.IOException;
import java.util.stream.Stream;
import java.util.Random;
import javax.swing.JOptionPane;
import java.util.Arrays;

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

	private boolean sidePotInPlay;
	private boolean checkWinnerCalledAgain;
	private Player playerWhoMadeSidePot;
	private int playerWhoMadeSidePotIndex;

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
	State machine controller. Calling moves game from one hand to the next.
	Betting immediately ends the hand.
	*/
	public void nextState()
	{
		if(state==1)
		{
			startNewHand();
		}
		gameBoard.displayCommCard(state);

		if (state == 5)
		{
			checkWinner(realPlayers);
			resetGame();
			moveBlinds();
		}
		else
		{
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
			if(realPlayers[i].isIn())
			{
				Player temp = realPlayers[i];
				Card[] tempCards = temp.getCurrentHand();
				dealer.returnCard(tempCards[0]);
				dealer.returnCard(tempCards[1]);
				temp.wipeHand();
				temp.setBet(0);

				gameBoard.wipePlayer(i);
			}
			else
			{
				Card[] tempCards = realPlayers[i].getCurrentHand();
				
				if(tempCards[0] != null && tempCards[1] != null)
				{
					Player temp = realPlayers[i];
					dealer.returnCard(tempCards[0]);
					dealer.returnCard(tempCards[1]);
					temp.wipeHand();
					gameBoard.wipePlayer(i);
				}
			}
		}
		dealer.returnCommCards();
		dealer.wipeCommCards();
		gameBoard.wipeDealer();
		state = 1;
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
			if(realPlayers[i].isIn())
			{
				Player temp = realPlayers[i];
				dealer.dealCard(temp);
				dealer.dealCard(temp);

				logString(temp.getName()+" dealt: "+temp.getCurrentHand()[0].toString()+" and "
															+temp.getCurrentHand()[1].toString());

				if(i==0)
				{
					gameBoard.displayPlayerHand(i);
				}
				else
				{
					gameBoard.displayCardBacks(i);
				}
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
    private void checkWinner(Player[] players)
    {
		//players[0].setStatus(0);
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
		int playerIndex[] = new int[8];
		Arrays.fill(playerIndex, -1);
		int maxSpot = 0;

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

		if(!sidePotInPlay && playerWhoMadeSidePotIndex != -1)
		{
			players[playerWhoMadeSidePotIndex].setStatus(0);
		}
		if(sidePotInPlay)
		{
			players[playerWhoMadeSidePotIndex].setStatus(0);
		}
		if(!sidePotInPlay && checkWinnerCalledAgain)
		{
			players[playerWhoMadeSidePotIndex].setStatus(1);
		}

		for (int i =0; i<5; i++)
		{
			currHand[i] = dealer.getCommCards()[i];
		}

		for(int i=0; i<players.length; i++)
		{
			if(players[i].isIn())
			{
				currHand[5] = players[i].getCurrentHand()[0];
				currHand[6] = players[i].getCurrentHand()[1];
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
		}

		int max = 0;
		String winner = "";
		Player winningPlayer = null;

		String maxPlayer = "";
		int[] maxHand = new int[6];

		String challengerPlayer = "";
		int[] challengerHand = new int[6];

		//DETERMINING WINNER AND ANY TIES...

		int winningIndex = -1;

		for(int i = 0; i < players.length; i++)
		{
			if(players[i].isIn())
			{
				if(scores.get(players[i].getName())[0] > max)
				{
					Arrays.fill(playerIndex, -1);
					maxSpot = 0;
					max = scores.get(players[i].getName())[0];
					winner = players[i].getName();
					winningPlayer = players[i];
					winningIndex = i;
					playerIndex[maxSpot] = i;
					maxSpot++;
				}
				else if(scores.get(players[i].getName())[0] == max)
				{
					//loop through players to find who owns current max
					//we need their hand to break the tie with the current challenger
					for(int x = 0; x < players.length; x++)
					{
						if(players[x].isIn())
						{
							if(max == scores.get(players[x].getName())[0])
							{
								maxPlayer = players[x].getName();
								maxHand = scores.get(players[x].getName());
								break;
							}
						}
					}

					challengerPlayer = players[i].getName();
					challengerHand = scores.get(players[i].getName());
					boolean addToTies = true;
					int maxIndex = 0;

					//loop not all the way through (best five card hand??)
					//potential bug
					for(int y = 1; y < challengerHand.length; y++)
					{
						if(maxHand[y] < challengerHand[y])
						{
							max = scores.get(players[i].getName())[0];
							winner = players[i].getName();
							winningPlayer = players[i];
							winningIndex = i;
							maxIndex = i;
							addToTies = false;
							break;
						}
						else if(maxHand[y] > challengerHand[y])
						{
							//max determined prior won against the challenger
							//no changes needed, max stays. No ties here
							addToTies = false;
						}
						winningIndex = i;
					}

					//If we have a true tie, add them to ties hashmap to print later
					//If anyone else ties, they get added here as well
					//Max and winner info for future comparison need no updating
					if(addToTies)
					{
						ties.put(maxPlayer, maxHand[0]);
						ties.put(challengerPlayer, challengerHand[0]);
						playerIndex[maxSpot] = i;
						maxSpot++;
					}

				}
			}
		}

		gameBoard.updatePot();
		gameBoard.updateSidePot();

		if(!ties.containsValue(max))
		{
			//we found a max greater than the previously found tie (if applicable), so max wins
			//highCard = cardScores.get(scores.get(winner)[1]);
			//logString();

			String[] buttons = {"Continue"};
			if(!checkWinnerCalledAgain)
			{
				JOptionPane.showOptionDialog(null, winner + " wins with a " + handScores.get(max) + "!", "Winner!",
					JOptionPane.WARNING_MESSAGE, 0, null, buttons, null);
				logString(winner + " wins with a " + handScores.get(max) + "!");
			}
			else
			{
				JOptionPane.showOptionDialog(null, winner + " wins the side pot with a " + handScores.get(max) + "!", "Side Pot Winner!",
					JOptionPane.WARNING_MESSAGE, 0, null, buttons, null);
				logString(winner + " wins side pot with a " + handScores.get(max) + "!");
			}

			int pot = 0;

			System.out.println("winningIndex = " + winningIndex + " playerWhoMadeSidePotIndex = " + playerWhoMadeSidePotIndex);
			if(sidePotInPlay && winningIndex == playerWhoMadeSidePotIndex)
			{
				pot = dealer.getPot();
				winningPlayer.updateMoney(winningPlayer.getMoney() + pot);
				gameBoard.changePlayerPot(winningIndex);
				System.out.println("Winning players pot should now be: -------> ");

				//Now call checkWinner again without side pot player to give "Second" place the side pot.
				dealer.updatePot(0);
				players[playerWhoMadeSidePotIndex].setStatus(1);//set them out temperarly for the round
				sidePotInPlay = false;
				checkWinnerCalledAgain = true;
				int oldSidePotIndex = playerWhoMadeSidePotIndex;
				checkWinner(players);
				dealer.updateSidePot(0);
				checkWinnerCalledAgain = false;
				players[oldSidePotIndex].setStatus(0);
				realPlayers[oldSidePotIndex].setStatus(0);

			}
			else
			{
				//System.out.println("Winning players pot should now be: " + (dealer.getPot() + dealer.getSidePot() + winningPlayer.getMoney()));
				pot = dealer.getPot() + dealer.getSidePot();
				winningPlayer.updateMoney(winningPlayer.getMoney() + pot);
				gameBoard.changePlayerPot(winningIndex);
				dealer.updatePot(0);
				dealer.updateSidePot(0);
			}

			//gameBoard.changePlayerPot(winningIndex);
		}
		else //playerIndex is an int array filled with indexes of winners
		{
			//the max hasnt been beat since this tie was found, so the ties split the pot
			logString("We have a tie! Players that tied: ");
			String winningPlayers = "";

			Object[] winningPlayersArray = ties.keySet().toArray();

			for(int i = 0; i < winningPlayersArray.length; i++)
			{
				if(i == winningPlayersArray.length-1)
				{
					winningPlayers += " and ";
					winningPlayers += winningPlayersArray[i].toString();
				}
				else
				{
					winningPlayers += winningPlayersArray[i].toString();
					winningPlayers += ", ";
				}
			}

			String[] buttons = {"Continue"};
			JOptionPane.showOptionDialog(null, "We have a tie! Players that tied: "+winningPlayers+
					"\n\nThey won with a " + handScores.get(max) + " and will split the pot.", "Winner!",
				JOptionPane.WARNING_MESSAGE, 0, null, buttons, null);

			logString(winningPlayers+
					"\n\nThey won with a " + handScores.get(max) + " and will split the pot.");

			int winnerCount = 0;

			for(int i = 0; i < playerIndex.length; i++)
			{
				if(playerIndex[i] == -1)
					break;
				winnerCount++;
			}

			int pot = dealer.getPot();
			int sidePot = dealer.getSidePot();
			int moneyPerPlayer = pot/winnerCount;
			int moneyBackToPot = (pot + sidePot)%winnerCount;
			int moneyBackToSidePotPlayers = pot/winnerCount;
			int moneyBackToPlayersWhoNotSidePotPlayers = (sidePot/winnerCount-1) + moneyBackToSidePotPlayers;

			for(int i = 0; i < playerIndex.length; i++)
			{
				if(playerIndex[i] != -1 && !sidePotInPlay)
				{
					realPlayers[playerIndex[i]].updateMoney(realPlayers[i].getMoney() + moneyPerPlayer);
					gameBoard.changePlayerPot(playerIndex[i]);
				}
				else if(playerIndex[i] != -1 && sidePotInPlay)
				{
					if(playerWhoMadeSidePotIndex != playerIndex[i]) //Should be playerIndex[i]??? - issue #68 and issue #65
					{
						realPlayers[playerIndex[i]].updateMoney(realPlayers[i].getMoney() + moneyBackToPlayersWhoNotSidePotPlayers);
						gameBoard.changePlayerPot(playerIndex[i]);
					}
					else
					{
						realPlayers[playerIndex[i]].updateMoney(realPlayers[i].getMoney() + moneyBackToSidePotPlayers);
						players[playerWhoMadeSidePotIndex].setStatus(0);
						realPlayers[playerWhoMadeSidePotIndex].setStatus(0);
						gameBoard.changePlayerPot(playerIndex[i]);
					}
				}
			}

			dealer.updatePot(moneyBackToPot);
		}


		//clear all player bets in player classes
		for(int i = 0; i < realPlayers.length; i++)
			realPlayers[i].resetBet();

		for(int i = 0; i < realPlayers.length; i++)
		{
			if(realPlayers[i].getStatus() == 1 && realPlayers[i].getMoney() > 0)
			{
				gameBoard.clearCurrentBetter(i);
				realPlayers[i].setStatus(0);
				playersStillInGame++;
			}
		}


		if(playerWhoMadeSidePotIndex >= 0 && sidePotInPlay)
		{
			if(realPlayers[playerWhoMadeSidePotIndex].getMoney() == 0)
				realPlayers[playerWhoMadeSidePotIndex].setStatus(2);
		}

		playerWhoMadeSidePotIndex = -1;
		sidePotInPlay = false;
		playerWhoMadeSidePot = null;
		dealer.updateSidePot(0);
		gameBoard.resetSidePot();

		//check for one player left
		int numberOfPlayersRemaining = 0;
		int winningPlayerIndex = 0;
		for(int i = 0; i < realPlayers.length; i++)
		{
			if(realPlayers[i].getMoney() > 0)
			{
				numberOfPlayersRemaining++;
				winningPlayerIndex = i;
				if(numberOfPlayersRemaining == 2)
				{
					break;
				}
			}
			else if(realPlayers[i].getMoney() <= 0) //Set them out of the game for good
			{
				realPlayers[i].setStatus(2);
			}
		}

		if(numberOfPlayersRemaining == 1) //display game winner and exit
		{
			String[] buttons = {"Exit"};

			JOptionPane.showOptionDialog(null, realPlayers[winningPlayerIndex].getName()+" won the game!", "Game Over", JOptionPane.WARNING_MESSAGE, 0, null, buttons, null);

			exit();
		}
	}

	public void betRound()
	{
		logString("Dealer is: "+realPlayers[dealerChip].getName());
		logString("Small Blind is: "+realPlayers[smallBlind].getName());
		logString("Big Blind is : "+realPlayers[bigBlind].getName());

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
			//logString("Current Player is: "+currentPlayer);
			//logString("Current Lead is: "+currentLead);
			if(realPlayers[currentPlayer].isIn())
			{
				gameBoard.highlightCurrentBetter(currentPlayer);

				toThePot = realPlayers[currentPlayer].bet(previousBet, numberOfRaises,state,playersStillInGame);

				if(toThePot == -1)
				{
					realPlayers[currentPlayer].setStatus(1);
					playersStillInGame--;
					gameBoard.cancelCurrentBetter(currentPlayer);
					logString(realPlayers[currentPlayer].getName() + " folded.");
				}
				else
				{
					if(realPlayers[currentPlayer].getMoney() > minRoundBet - realPlayers[currentPlayer].getBet())
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

						if(!sidePotInPlay)
						{
							dealer.updatePot(dealer.getPot() + toThePot);
						}
						else
						{
							dealer.updateSidePot(dealer.getSidePot() + toThePot);
							gameBoard.updateSidePot();
						}

						logString(realPlayers[currentPlayer].getName() + " bet "+(realPlayers[currentPlayer].getBet()));

						gameBoard.changePlayerPot(currentPlayer);
						gameBoard.updatePot();
						gameBoard.updateMinBet(minRoundBet);
					}
					else
					{
						//Set up side pot here
						if(!sidePotInPlay)
						{
							/*int tempSidePot = realPlayers[currentPlayer].getMoney();
							tempSidePot = tempSidePot * numberOfRaises + tempSidePot;

							dealer.updatePot(dealer.getPot() - tempSidePot + realPlayers[currentPlayer].getMoney());
							dealer.updateSidePot(tempSidePot);
							realPlayers[currentPlayer].updateMoney(0);*/
							if(toThePot < minRoundBet)
							{
								int difference = (minRoundBet-realPlayers[currentPlayer].getBet()
												- realPlayers[currentPlayer].getMoney()) * (realPlayers.length - 1);

								dealer.updateSidePot(dealer.getSidePot() + difference);
								dealer.updatePot(dealer.getPot() - difference + realPlayers[currentPlayer].getMoney());
								if(dealer.getPot() < 0)
								{
									dealer.updatePot(dealer.getPot() * -1);
								}
								realPlayers[currentPlayer].updateMoney(0);
								realPlayers[currentPlayer].setStatus(1);
							}
							else if(toThePot <= minRoundBet)
							{
								int difference = (minRoundBet - realPlayers[currentPlayer].getMoney()) * (realPlayers.length - 1);

								dealer.updateSidePot(dealer.getSidePot() + difference);
								dealer.updatePot(dealer.getPot() - difference + realPlayers[currentPlayer].getMoney());
								if(dealer.getPot() < 0)
								{
									dealer.updatePot(dealer.getPot() * -1);
								}
								realPlayers[currentPlayer].updateMoney(0);
								realPlayers[currentPlayer].setStatus(1);
							}

							//dealer.updateSidePot(tempSidePot);
							/*for(int i = 0; i < realPlayers.length; i++)
							{
								if(realPlayers[i].getStatus() == 0 && i != currentPlayer) //still in
								{
									//update
									realPlayers[i].updateMoney(realPlayers[i].getMoney() - realPlayers[currentPlayer].getMoney());
								}
							}*/

							sidePotInPlay = true;
							playerWhoMadeSidePot = realPlayers[currentPlayer];
							playerWhoMadeSidePotIndex = currentPlayer;
							gameBoard.changePlayerPot(currentPlayer);
							gameBoard.updatePot();
							gameBoard.updateSidePot();
						}
						else
						{
							//Side pot is in play
							gameBoard.clearCurrentBetter(currentPlayer);

							toThePot += minRoundBet - realPlayers[currentPlayer].getBet() - toThePot;

							previousBet = realPlayers[currentPlayer].getBet() + toThePot;

							realPlayers[currentPlayer].setBet(toThePot); //update the current player's bet

							//Update player's own pot.
							if(realPlayers[currentPlayer].getMoney() >= toThePot)
							{
								realPlayers[currentPlayer].subtractFromMoney(toThePot);
							}
							else
							{
								realPlayers[currentPlayer].subtractFromMoney(realPlayers[currentPlayer].getMoney());
							}

							if(!sidePotInPlay)
							{
								dealer.updatePot(dealer.getPot() + toThePot);
							}
							else
							{
								dealer.updateSidePot(dealer.getSidePot() + toThePot);
								gameBoard.updateSidePot();
							}

							logString(realPlayers[currentPlayer].getName() + " bet "+(realPlayers[currentPlayer].getBet()));

							gameBoard.changePlayerPot(currentPlayer);
							gameBoard.updatePot();
							gameBoard.updateMinBet(minRoundBet);

						}
					}
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

					toThePot = realPlayers[currentPlayer].bet(previousBet, numberOfRaises,state, playersStillInGame);

					if(toThePot == -1) //give players chance to fold
					{
						realPlayers[currentPlayer].setStatus(1);
						playersStillInGame--;
						gameBoard.cancelCurrentBetter(currentPlayer);
						logString(realPlayers[currentPlayer].getName() + " folded.");
					}
					else //if they don't fold, force them to call
					{
						if(realPlayers[currentPlayer].getMoney() > minRoundBet - realPlayers[currentPlayer].getBet())
						{
							gameBoard.clearCurrentBetter(currentPlayer);

							toThePot += minRoundBet - realPlayers[currentPlayer].getBet() - toThePot;

							previousBet = realPlayers[currentPlayer].getBet() + toThePot;

							realPlayers[currentPlayer].setBet(toThePot); //update the current player's bet
							realPlayers[currentPlayer].subtractFromMoney(toThePot);

							if(!sidePotInPlay)
							{
								dealer.updatePot(dealer.getPot() + toThePot);
							}
							else
							{
								dealer.updateSidePot(dealer.getSidePot() + toThePot);
								gameBoard.updateSidePot();
							}

							logString(realPlayers[currentPlayer].getName() + " bet "+(realPlayers[currentPlayer].getBet()));

							gameBoard.changePlayerPot(currentPlayer);
							gameBoard.updatePot();
							gameBoard.updateMinBet(minRoundBet);
						}
						else
						{
							//Set up side pot here
							if(!sidePotInPlay)
							{
								/*int tempSidePot = realPlayers[currentPlayer].getMoney();
								tempSidePot = tempSidePot * numberOfRaises + tempSidePot;

								dealer.updatePot(dealer.getPot() - tempSidePot + realPlayers[currentPlayer].getMoney());
								dealer.updateSidePot(tempSidePot);
								realPlayers[currentPlayer].updateMoney(0);*/

								if(toThePot < minRoundBet)
								{
									int difference = (minRoundBet-realPlayers[currentPlayer].getBet()
													- realPlayers[currentPlayer].getMoney()) * (realPlayers.length - 1);

									dealer.updateSidePot(dealer.getSidePot() + difference);
									dealer.updatePot(dealer.getPot() - difference + realPlayers[currentPlayer].getMoney());
									if(dealer.getPot() < 0)
									{
										dealer.updatePot(dealer.getPot() * -1);
									}
									realPlayers[currentPlayer].updateMoney(0);
									realPlayers[currentPlayer].setStatus(1);
								}
								else
								{
									int difference = (minRoundBet - realPlayers[currentPlayer].getMoney()) * (realPlayers.length - 1);

									dealer.updateSidePot(dealer.getSidePot() + difference);
									dealer.updatePot(dealer.getPot() - difference + realPlayers[currentPlayer].getMoney());
									if(dealer.getPot() < 0)
									{
										dealer.updatePot(dealer.getPot() * -1);
									}
									realPlayers[currentPlayer].updateMoney(0);
									realPlayers[currentPlayer].setStatus(1);
								}

								//dealer.updateSidePot(tempSidePot);
								/*for(int i = 0; i < realPlayers.length; i++)
								{
									if(realPlayers[i].getStatus() == 0 && i != currentPlayer) //still in
									{
										//update
										realPlayers[i].updateMoney(realPlayers[i].getMoney() - realPlayers[currentPlayer].getMoney());
									}
								}*/

								sidePotInPlay = true;
								playerWhoMadeSidePot = realPlayers[currentPlayer];
								playerWhoMadeSidePotIndex = currentPlayer;
								gameBoard.changePlayerPot(currentPlayer);
								gameBoard.updatePot();
								gameBoard.updateSidePot();
							}
							else
							{
								//Do nothing
								gameBoard.clearCurrentBetter(currentPlayer);

								toThePot += minRoundBet - realPlayers[currentPlayer].getBet() - toThePot;

								previousBet = realPlayers[currentPlayer].getBet() + toThePot;

								realPlayers[currentPlayer].setBet(toThePot); //update the current player's bet

								//Update player's own pot.
								if(realPlayers[currentPlayer].getMoney() >= toThePot)
								{
									realPlayers[currentPlayer].subtractFromMoney(toThePot);
								}
								else
								{
									realPlayers[currentPlayer].subtractFromMoney(realPlayers[currentPlayer].getMoney());
								}

								if(!sidePotInPlay)
								{
									dealer.updatePot(dealer.getPot() + toThePot);
								}
								else
								{
									dealer.updateSidePot(dealer.getSidePot() + toThePot);
									gameBoard.updateSidePot();
								}

								logString(realPlayers[currentPlayer].getName() + " bet "+(realPlayers[currentPlayer].getBet()));

								gameBoard.changePlayerPot(currentPlayer);
								gameBoard.updatePot();
								gameBoard.updateMinBet(minRoundBet);
							}
						}
					}
				}
				currentPlayer = (currentPlayer+1) % numberOfPlayers;
			}
		}

		for(int i = 0; i < realPlayers.length; i++)
		{
			realPlayers[i].resetBet();
			if(sidePotInPlay && i == playerWhoMadeSidePotIndex)
			{
				realPlayers[i].setStatus(1);
			}
			
			/*
			if(realPlayers[i].getStatus() == 2)
			{
				gameBoard.displayOutForGood(i);
			}
			*/
		}
		

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
		catch(Exception e)
		{
			System.err.println("Failed logging message: "+message);
			System.err.println("Error: "+e.toString());
		}
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
