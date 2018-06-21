import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

public class Hands {
	
	/*
	Check if cards passed in yield a royal flush
	10 through Ace rank, all same suit
	*/
	public static boolean royalFlush(Card[] cards) {
		
		Card[] tempHand = new Card[7];
		boolean ten = false;
		boolean jack = false;
		boolean queen = false;
		boolean king = false;
		boolean ace = false;
		
		int count = 0;
		int index = 0;
		
		for( int j = 0; j < cards.length; j++ ) 
		{
			if(cards[j].getRank() == "Ten")
			{
				tempHand[index] = cards[j];
				ten = true;
				index++;
			}
			if(cards[j].getRank() == "Jack")
			{
				tempHand[index] = cards[j];
				jack = true;
				index++;
			}
			if(cards[j].getRank() == "Queen")
			{
				tempHand[index] = cards[j];
				queen = true;
				index++;
			}
			if(cards[j].getRank() == "King")
			{
				tempHand[index] = cards[j];
				king = true;
				index++;
			}
			if(cards[j].getRank() == "Ace")
			{
				tempHand[index] = cards[j];
				ace = true;
				index++;
			}
		}
		
		if(ten && jack && queen && king && ace) 
		{
			if(flush(tempHand))
			{
				return true;
			}
		}
		return false;
	}
	
	/*
	Check if cards passed in yield a straight flush
	Any five consecutive card ranks, all same suit
	*/
	public static boolean straightFlush(Card[] cards) 
	{
		int count = 0;
		int suitCount = 0;
		int index = 0;
		String suit = "";
		
		Card[] tempCards = new Card[7];
		
		for( int j = 0; j < cards.length; j++ ) 
		{
			count = 0;
			
			for( int i = j+1; i < cards.length; i++ ) 
			{
				if( cards[j].getSuit() == cards[i].getSuit() )
				{
					suitCount++;
				}
				
				if(suitCount == 5)
				{
					suit = cards[j].getSuit();
				}
			}
		}
		
		for(int i = 0; i < cards.length; i++) 
		{
			if(suit == cards[i].getSuit())
			{
				tempCards[index] = cards[i];
				index++;
			}
		}
		
		if(straight(tempCards))
		{
			return true;
		}
		return false;
	}
	
	/*
	Check if cards passed in yield a four of a kind
	Four cards of same rank, suit does not matter
	*/
	public static boolean fourOfAKind(Card[] cards) {
		
		int count = 0;
		
		for( int j = 0; j < cards.length; j++ )
		{
			count = 1;
			
			for( int i = j+1; i < cards.length; i++ )
			{
				
				if(cards[j].getRank() == cards[i].getRank())
				{
					count++;						
				}	

				if(count == 4)
				{
					return true;				
				}	
			}
		}
		return false;
	}
	
	/*
		Check if cards passed in yield a full house
		One pair and one three of a kind in the same hand
	*/
	public static boolean fullHouse(Card[] cards) 
	{
		HashMap<String, Integer> tallies = new HashMap<String, Integer>();
		int count = 0;
		boolean foundPair = false;
		boolean foundTrio = false;
		
		tallies.put("Two", 0);
		tallies.put("Three", 0);
		tallies.put("Four", 0);
		tallies.put("Five", 0);
		tallies.put("Six", 0);
		tallies.put("Seven", 0);
		tallies.put("Eight", 0);
		tallies.put("Nine", 0);
		tallies.put("Ten", 0);
		tallies.put("Jack", 0);
		tallies.put("Queen", 0);
		tallies.put("King", 0);
		tallies.put("Ace", 0);
		
		for(int j = 0; j < cards.length; j++) 
		{
			String cardRank = cards[j].getRank();
			tallies.put( cardRank, (tallies.get(cardRank)+1) );
		}
		
		for(Map.Entry<String, Integer> entry : tallies.entrySet()) 
		{
			if(entry.getValue() == 2) 
			{
				foundPair = true;
			}
			if(entry.getValue() == 3) 
			{
				foundTrio = true;
			}
		}
		
		if(foundPair && foundTrio) 
		{
			return true;
		}
		
		return false;
	}
	
	/*
	Check if cards passed in yield a flush
	Five cards with the same suit, rank irrelevant
	*/
	public static boolean flush(Card[] cards) 
	{
		int count = 0;
		
		for(int j = 0; j < cards.length; j++) 
		{
			count = 1;
			
			for(int i = j+1; i < cards.length; i++) 
			{
				if(cards[j] == null || cards[i] == null) 
				{
					break;
				}
				
				if( cards[j].getSuit() == cards[i].getSuit() ) 
				{
					count++;						
				}	

				if(count == 5) 
				{
					return true;				
				}
			}
		}
		return false;
	}
	
	/*
	Check if cards passed in yield a straight
	Five consectuve ranks, suit irrelevant
	*/
	public static boolean straight(Card[] cards) {
		
		HashMap<String, Integer> possCards = new HashMap<String,Integer>();
		possCards.put("Two", 2);
		possCards.put("Three", 3);
		possCards.put("Four", 4);
		possCards.put("Five", 5);
		possCards.put("Six", 6);
		possCards.put("Seven", 7);
		possCards.put("Eight", 8);
		possCards.put("Nine", 9);
		possCards.put("Ten", 10);
		possCards.put("Jack", 11);
		possCards.put("Queen", 12);
		possCards.put("King", 13);
		possCards.put("Ace", 14);
		
		int[] cardVals = new int[7];
		int count = 0;
		int index = 0;
		
		for(int i = 0; i < cards.length; i++) 
		{
			if(cards[i] != null)
			{
				cardVals[index] = possCards.get(cards[i].getRank());
				index++;
			}
		}
		
		Arrays.sort(cardVals);
		
		for(int i = 1; i <= 3; i++) 
		{
			count = 1;
			
			for(int j = (cardVals.length - i); j > 0 ; j--) 
			{
				if( (cardVals[j] - cardVals[j-1]) == 1 )
				{
					count++;
				}
				else
				{
					break;
				}
			}

			if(count == 5)
			{
				return true;
			}
		}
		return false;
	}
	
	/*
	Check if cards passed in yield a three of a kind
	Three cards of same rank, suit irrelevant
	*/
	public static boolean threeOfAKind(Card[] cards) 
	{
		
		Card holder = new Card(Card.Suit.Clubs, Card.Rank.Two);
		boolean foundOne = false;
		int count = 0;
		
		for(int j = 0; j < cards.length; j++) 
		{
			count = 1;
			
			for(int i = j+1; i < cards.length; i++) 
			{
				if(cards[j].getRank() == cards[i].getRank()) 
				{
					count++;						
				}	

				if(count == 3) 
				{
					//FOR CASES WHERE THERE ARE TWO AND A HIGHER RANK NEEDED
					/*if(!foundOne) 
					{
						holder = cards[j];	
						foundOne = true;
					}
					else 
					{
						if(holder.compareTo(cards[j]) > 0) 
						{
							
						}
						else if(holder.compareTo(cards[j]) < 0) 
						{
							
						}
					}*/
					
					//But for now...
					return true;				
				}
			}
		}
		return false;
	}
	
	/*
	Check if cards passed in yield two pair
	Two pairs, essentially two independent one pairs
	*/
	public static boolean twoPair(Card[] cards) {
		
		HashMap<String, Integer> tallies = new HashMap<String, Integer>();
		int count = 0;
		
		tallies.put("Two", 0);
		tallies.put("Three", 0);
		tallies.put("Four", 0);
		tallies.put("Five", 0);
		tallies.put("Six", 0);
		tallies.put("Seven", 0);
		tallies.put("Eight", 0);
		tallies.put("Nine", 0);
		tallies.put("Ten", 0);
		tallies.put("Jack", 0);
		tallies.put("Queen", 0);
		tallies.put("King", 0);
		tallies.put("Ace", 0);
		
		for(int j = 0; j < cards.length; j++) 
		{
			String cardRank = cards[j].getRank();
			tallies.put(cardRank,(tallies.get(cardRank)+1));
		}
		
		for(Map.Entry<String, Integer> entry : tallies.entrySet()) 
		{
			if(entry.getValue() == 2) 
			{
				count++;
			}
			
			if(count == 2) 
			{
				return true;
			}	
		}
		return false;
	}
	
	/*
	Check if cards passed in yield a pair
	Two cards that are equal in rank
	*/
	public static boolean onePair(Card[] cards) {
		
		Card holder = new Card(Card.Suit.Clubs, Card.Rank.Two);
		boolean foundOne = false;
		int count = 0;
		
		for(int j = 0; j < cards.length; j++) 
		{
			count = 1;
			
			for(int i = j+1; i < cards.length; i++) 
			{
				if(cards[j].getRank() == cards[i].getRank()) 
				{
					count++;						
				}	

				if(count == 2) 
				{
					//FOR CASES WHERE THERE ARE TWO AND A HIGHER RANK NEEDED
					/*if(!foundOne) 
					{
						holder = cards[j];	
						foundOne = true;
					}
					else 
					{
						if(holder.compareTo(cards[j]) > 0) 
						{
							
						}
						else if(holder.compareTo(cards[j]) < 0) 
						{
							
						}
					}*/
					//But for now...
					return true;				
				}
			}
		}
		return false;
	}
	
	/*
	Check if cards passed in yield a high card
	Highest card rank in all of the cards
	*/
	public static boolean highCard(Card[] cards) {
		//Identify highest rank like in pair, kinds, etc
		return true;
	}
	
}