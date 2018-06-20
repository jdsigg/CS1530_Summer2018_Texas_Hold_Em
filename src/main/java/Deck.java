import java.util.ArrayList;
import java.util.Collections;

public class Deck 
{
	private ArrayList<Card> deck = new ArrayList<>();

	/*
	Fill the ArrayList with cards
	*/
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
	
	/*
	Shuffle the deck
	*/
	public void shuffle() 
	{
		Collections.shuffle(deck);
	}
	
	/*
	Remove a card from the deck. Since it is always shuffled
	before a remove is called, just remove the top card
	*/
	public Card removeCard() 
	{
        Card toSend = deck.remove(0);    
		return toSend;
	}
	
	/*
	Return the deck as an ArrayList to caller
	*/
	public ArrayList<Card> getDeck()
	{
		return deck;
	}
	
	/*
	Place a card within the deck
	*/
	public void addCard(Card card)
	{
		deck.add(card);
	}
}