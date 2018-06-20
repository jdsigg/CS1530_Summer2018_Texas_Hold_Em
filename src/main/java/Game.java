public class Game
{
    /*
        Constructor for Game Class
    */
    public Game()
    {

    }

    /*
        method to deal the players
    */
    private void dealToPlayer()
    {

    }

    /*
        method to deal five commcards[5]
    */
    private void dealCommCards()
    {

    }

    /*
        method to call when a player, human or AI is
        going to fold. Method must accept a player. The
        player passed in is the player that is going to
        fold.
    */
    private void playerFold(Player player)
    {

    }

    /*
        method to call when a player, human or AI is
        going to check. Method must accept a player.
        The player passed in is the player that is
        going to check.
    */
    private void playerCheck(Player player)
    {

    }

    /*
        method to display flop, turn, and river
        Accepts an integer 3, 4, or 5 that is the
        type of deal turn. 3 = Flop, 4 = Turn,
        5 = River
    */
    private void dealFlops(int type)
    {
        if(type == 3)
        {
            //Display Flop
        }
        else if(type == 4)
        {
            //Display Turn
        }
        else
        {
            //Display River
        }
    }

    /*
        Method to check, after the round is over, who won.
        It accepts an array of all of the players.
    */
    public static void checkWinner(Player[] players)
    {
		Card[] hand1 = new Card[2];
		Card[] hand2 = new Card[2];
		hand1 = players[0].getCurrentHand();
		hand2 = players[1].getCurrentHand();
		
		Card c1 = new Card(Card.Suit.Hearts, Card.Rank.Two);
		Card c2 = new Card(Card.Suit.Hearts, Card.Rank.Three);
		Card c3 = new Card(Card.Suit.Hearts, Card.Rank.Jack);
		Card c4 = new Card(Card.Suit.Hearts, Card.Rank.Six);
		Card c5 = new Card(Card.Suit.Clubs, Card.Rank.Three);
		
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
	
		//for(int i=0; i<players.length; i++) {
			
			//System.out.println(players[i].getName());
			
			//loop to make seven cards here into currHand
			
			if(Hands.royalFlush(currHand)) {
				System.out.println("Player somehow has a royal flush");
				//give player a score
			}
			else if(Hands.straightFlush(currHand)) {
				System.out.println("Player has straight flush");
				//give player a score
			}
			else if(Hands.fourOfAKind(currHand)) {
				System.out.println("Player has four of a kind");
				//give player a score
			}
			else if(Hands.fullHouse(currHand)) {
				System.out.println("Player has full house");
				//give player a score
			}
			else if(Hands.flush(currHand)) {
				System.out.println("Player has a flush");
				//give player a score
			}
			else if(Hands.straight(currHand)) {
				System.out.println("Player has straight");
				//give player a score
			}
			else if(Hands.threeOfAKind(currHand)) {
				System.out.println("Player has three of a kind");
				//give player a score
			}
			else if(Hands.twoPair(currHand)) {
				System.out.println("Player has two pair");
				//give player a score
			}
			else if(Hands.onePair(currHand)) {
				System.out.println("Player has a pair");
				//give player a score
			}
			else if(Hands.highCard(currHand)) {
				System.out.println("Player only has high card");
				//give player a score
			}
			
			
		//}
    }
	
	/*public static void main(String[] args) {
		Card c1 = new Card(Card.Suit.Hearts, Card.Rank.Five);
		Card c2 = new Card(Card.Suit.Hearts, Card.Rank.Four);
		Player p1 = new Player("justin");
		Player p2 = new Player("anderson");
		
		p1.addCard(c1);
		p1.addCard(c2);
		p2.addCard(c2);
		p2.addCard(c1);
		
		Player[] players = new Player[2];
		players[0] = p1;
		players[1] = p2;
		
		checkWinner(players);
		
		
	}*/
	
	
	
	
}
