import java.util.ArrayList;
import java.util.Collections;

public class Deck 
{
	private ArrayList<Card> deck = new ArrayList<>();

	public Deck()
	{
		for (Card.Suit suit : Card.Suit.values())
		{
			for(Card.Rank rank : Card.Rank.values()) 
			{
				deck.add(new Card(suit, rank));
			}
		}
	}
	
	public void shuffle() 
	{
		Collections.shuffle(deck);
	}
	
	public Card removeCard() 
	{
		return deck.remove(0);
	}
	
}