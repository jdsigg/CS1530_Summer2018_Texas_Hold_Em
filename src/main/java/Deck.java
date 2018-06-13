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
        Card toSend = deck.remove(0);    
		return toSend;
	}
	
	public ArrayList<Card> getDeck()
	{
		return deck;
	}
	
}