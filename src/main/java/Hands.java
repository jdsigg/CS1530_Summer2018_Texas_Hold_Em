import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

public class Hands {
	
	private static HashMap<String, Integer> possCards = new HashMap<String,Integer>();
	
	/*
	Check if cards passed in yield a royal flush
	10 through Ace rank, all same suit
	*/
	public static int[] royalFlush(Card[] cards) 
	{
		int winningHand[] = new int[6];
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
			if(flush(tempHand)[0] == 6)
			{
				winningHand[0] = 10;
				return winningHand;
			}
		}
		return winningHand;
	}
	
	/*
	Check if cards passed in yield a straight flush
	Any five consecutive card ranks, all same suit
	*/
	public static int[] straightFlush(Card[] cards) 
	{
		int winningHand[] = new int[6];
		int count = 0;
		int suitCount = 0;
		int index = 0;
		String suit = "";
		
		Card[] tempCards = new Card[7];
		
		for( int j = 0; j < cards.length; j++ ) 
		{
			suitCount = 1;
			
			for( int i = j+1; i < cards.length; i++ ) 
			{
				if( cards[j].getSuit() == cards[i].getSuit() )
				{
					suitCount++;
				}
				
				if(suitCount == 5)
				{
					suit = cards[j].getSuit();
					break;
				}
			}
		}
		
		for(int i = 0; i < cards.length; i++) 
		{
			if(suit.equals(cards[i].getSuit()))
			{
				tempCards[index] = cards[i];
				index++;
			}
		}
		
		if(Hands.straight(tempCards)[0] == 5)
		{
			for(int i = 1; i < winningHand.length; i++)
			{
				winningHand[i] = Hands.straight(tempCards)[i];
			}
			winningHand[0] = 9;
			return winningHand;
		}
		return winningHand;
	}
	
	/*
	Check if cards passed in yield a four of a kind
	Four cards of same rank, suit does not matter
	*/
	public static int[] fourOfAKind(Card[] cards) 
	{
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
		
		int winningHand[] = new int[6];
		int temp[] = new int[7];
		int count = 0;
		
		//sort all cards
		for(int x = 0; x < cards.length; x++)
		{
			temp[x] = possCards.get(cards[x].getRank());
		}
		
		Arrays.sort(temp);
		
		for( int j = 0; j < cards.length; j++ )
		{
			count = 1;
			winningHand[count+1] = possCards.get(cards[j].getRank());
			
			for( int i = j+1; i < cards.length; i++ )
			{
				
				if(cards[j].getRank() == cards[i].getRank())
				{
					count++;					
				}	
				
				if(count == 4)
				{
					winningHand[1] = possCards.get(cards[j].getRank());	
					for(int x = 0; x < cards.length; x++)
					{
						if(temp[x] != winningHand[2])
						{
							winningHand[2] = temp[x];
							break;
						}
					}
		
					winningHand[0] = 8;
					return winningHand;				
				}	
			}
		}
		return winningHand;
	}
	
	/*
		Check if cards passed in yield a full house
		One pair and one three of a kind in the same hand
	*/
	public static int[] fullHouse(Card[] cards) 
	{
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
		
		HashMap<String, Integer> tallies = new HashMap<String, Integer>();
		int winningHand[] = new int[6];
		int count = 0;
		boolean foundPair = false;
		boolean foundTrio = false;
		int tieValue = 0;
		
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
			if(tallies.get(cardRank) == 3)
			{
				tieValue = possCards.get(cardRank);
			}
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
			winningHand[0] = 7;
			winningHand[1] = tieValue;
			return winningHand;
		}
		
		return winningHand;
	}
	
	/*
	Check if cards passed in yield a flush
	Five cards with the same suit, rank irrelevant
	*/
	public static int[] flush(Card[] cards) 
	{
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
		
		int winningHand[] = new int[6];
		int[] sameSuit = new int[7];
		int count = 0;
		
		for(int j = 0; j < cards.length; j++) 
		{
			count = 1;
			sameSuit[count-1] = possCards.get(cards[j].getRank());
			
			for(int i = j+1; i < cards.length; i++) 
			{
				if(cards[j] == null || cards[i] == null) 
				{
					break;
				}
				
				if( cards[j].getSuit() == cards[i].getSuit() ) 
				{
					count++;	
					sameSuit[count-1] = possCards.get(cards[i].getRank());
				}	
			}
			
			Arrays.sort(sameSuit);
			if(count >= 5) 
			{
				winningHand[1] = sameSuit[sameSuit.length - 1];
				winningHand[2] = sameSuit[sameSuit.length - 2];
				winningHand[3] = sameSuit[sameSuit.length - 3];
				winningHand[4] = sameSuit[sameSuit.length - 4];
				winningHand[5] = sameSuit[sameSuit.length - 5];
				winningHand[0] = 6;
				return winningHand;				
			}
			
		}
		return winningHand;
	}
	
	/*
	Check if cards passed in yield a straight
	Five consectuve ranks, suit irrelevant
	*/
	public static int[] straight(Card[] cards) 
	{
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
		
		int winningHand[] = new int[6];
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
			index = 1;
			
			for(int j = (cardVals.length - i); j > 0 ; j--) 
			{
				if( (cardVals[j] - cardVals[j-1]) == 1 )
				{
					winningHand[index] = cardVals[j];

					if(count == 4)
					{
						winningHand[index + 1] = cardVals[j-1];
					}
					index++;
					count++;
				}
				else
				{
					break;
				}
			}

			if(count == 5)
			{
				winningHand[0] = 5;
				return winningHand;
			}
		}
		return winningHand;
	}
	
	/*
	Check if cards passed in yield a three of a kind
	Three cards of same rank, suit irrelevant
	*/
	public static int[] threeOfAKind(Card[] cards) 
	{
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
		
		int winningHand[] = new int[6];
		Card holder = new Card(Card.Suit.Clubs, Card.Rank.Two);
		int temp[] = new int[7];
		boolean foundOne = false;
		int count = 0;
		
		//sort all cards
		for(int x = 0; x < cards.length; x++)
		{
			temp[x] = possCards.get(cards[x].getRank());
		}
		Arrays.sort(temp);
		
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
					int spot = 2;
					for(int x = 0; x < 5; x++)
					{
						if(temp[x] != winningHand[2])
						{
							winningHand[spot] = temp[x];
							spot++;
							if (spot == 4)
							{
								break;
							}
						}
					}
					
					//But for now...
					winningHand[1] = possCards.get(cards[j].getRank());
					winningHand[0] = 4;
					return winningHand;				
				}
			}
		}
		return winningHand;
	}
	
	/*
	Check if cards passed in yield two pair
	Two pairs, essentially two independent one pairs
	*/
	public static int[] twoPair(Card[] cards) 
	{
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
		
		int winningHand[] = new int[6];
		int pairValues[] = new int[2];
		int spot = 1;
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
			if(tallies.get(cardRank) == 2)
			{
				winningHand[spot] = possCards.get(cardRank);
				spot++;
			}
		}
		
		for(Map.Entry<String, Integer> entry : tallies.entrySet()) 
		{
			if(entry.getValue() == 2) 
			{
				count++;
			}
			
			if(count == 2) 
			{
				if(winningHand[1] < winningHand[2])
				{
					//get the greater of the pairs first
					int t = winningHand[1];
					winningHand[1] = winningHand[2];
					winningHand[2] = t;
				}
				
				int temp[] = new int[7];
		
				//sort all cards
				for(int x = 0; x < cards.length; x++)
				{
					temp[x] = possCards.get(cards[x].getRank());
				}
				Arrays.sort(temp);
				
				for(int x = 1; x < 7; x++)
				{
					if(temp[temp.length - x] != winningHand[1] && temp[temp.length - x] != winningHand[2])
					{
						winningHand[3] = temp[temp.length-x];
						System.out.println(winningHand[3]);
						break;
					}
				}
					
				winningHand[0] = 3;
				return winningHand;
			}	
		}
		return winningHand;
	}
	
	/*
	Check if cards passed in yield a pair
	Two cards that are equal in rank
	*/
	public static int[] onePair(Card[] cards) 
	{
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
		
		int winningHand[] = new int[6];
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
					winningHand[1] = possCards.get(cards[j].getRank());
					
					int temp[] = new int[7];
		
					//sort all cards
					for(int x = 0; x < cards.length; x++)
					{
						temp[x] = possCards.get(cards[x].getRank());
					}
					Arrays.sort(temp);
					
					int index = 2;
					for(int x = 0; x < 7; x++)
					{
						if(temp[x] != winningHand[1])
						{
							winningHand[index] = temp[x];
							index++;
							if(index == 5)
							{
								break;
							}
						}
					}
					
					winningHand[0] = 2;
					return winningHand;				
				}
			}
		}
		return winningHand;
	}
	
	/*
	Check if cards passed in yield a high card
	Highest card rank in all of the cards
	*/
	public static int[] highCard(Card[] cards) 
	{
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
		
		int temp[] = new int[7];
		
		//sort all cards
		for(int x = 0; x < cards.length; x++)
		{
			temp[x] = possCards.get(cards[x].getRank());
		}

		Arrays.sort(temp);
		
		int winningHand[] = new int[6];
		
		for(int x = 1; x < 6; x++)
		{
			winningHand[x] = temp[temp.length - x];
		}
		
		winningHand[0] = 1;
		return winningHand;
	}
	
}