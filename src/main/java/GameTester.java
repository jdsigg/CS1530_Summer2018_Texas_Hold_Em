    public static void main(String[] args) {
		Card c1 = new Card(Card.Suit.Clubs, Card.Rank.Five);
		Card c2 = new Card(Card.Suit.Hearts, Card.Rank.Ten);
		Card c3 = new Card(Card.Suit.Hearts, Card.Rank.Ace);
		Player p1 = new Player("justin");
		Player p2 = new Player("anderson");
		
		p1.addCard(c1);
		p1.addCard(c3);
		p2.addCard(c2);
		p2.addCard(c3);
		
		Player[] players = new Player[2];
		players[0] = p1;
		players[1] = p2;
		
		checkWinner(players);
		
	}
	
	public void checkWinner(Player[] players)
    {
		Card[] hand1 = new Card[2];
		Card[] hand2 = new Card[2];
		hand1 = players[0].getCurrentHand();
		hand2 = players[1].getCurrentHand();
		
		Card c1 = new Card(Card.Suit.Hearts, Card.Rank.Six);
		Card c2 = new Card(Card.Suit.Hearts, Card.Rank.Eight);
		Card c3 = new Card(Card.Suit.Hearts, Card.Rank.Seven);
		Card c4 = new Card(Card.Suit.Hearts, Card.Rank.King);
		Card c5 = new Card(Card.Suit.Clubs, Card.Rank.Nine);
		
		//add comm cards too - loop to do this 
		//loop for every new player
		Card[] currHand = new Card[7];
		
		currHand[0] = hand1[0];
		currHand[1] = hand1[1];
		currHand[2] = c1;
		currHand[3] = c2;
		currHand[4] = c3;
		currHand[5] = c4;
		currHand[6] = c5;
		
		//------------------------------------------------------------------------------

		//Card[] currHand = new Card[7];
		HashMap<String, Integer> scores = new HashMap<String, Integer>();
		int score = 0;
		
		//for (int i =0; i<5; i++)
		//{
		//	currHand[i] = dealer.getCommCards()[i];
		//}
		
		for(int i=0; i<players.length; i++)
		{
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
				//scores.put(players[i].getName(), 5); 	//Give player a score
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
	