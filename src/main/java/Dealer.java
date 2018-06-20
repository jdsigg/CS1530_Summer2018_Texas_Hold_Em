import java.util.Collections;
import java.util.ArrayList;

class Dealer
{
	/*
	Initialize Variables
	*/
	private double pot;
	private Deck dealerDeck;
	private Card [] commCards = new Card[5];
	
	/*
	Initializing constructor
	*/
	public Dealer(Deck deck)
	{
		this.pot = 0.00;
		this.dealerDeck = deck;
	}
	
	/*
	Method to get pot
	*/
	public double getPot()
	{
		return this.pot;
	}
	
	/*
	Method to update pot
	*/
	public void updatePot(double pot)
	{
		this.pot = pot;
	}
	
	/*
	Method to deal an individual card to a given player
	*/
	public void dealCard(Player p)
	{
		p.addCard(dealerDeck.removeCard());
	}
	
	/*
	Method to deal the cards to the community "hand"
	*/
	public void dealCommCards()
	{
		for (int i=0; i<commCards.length; i++)
		{
			commCards[i] = dealerDeck.removeCard();
		}
	}
	
	/*
	Method to return the community cards
	*/
	public Card[] getCommCards()
	{
		return commCards;
	}
		
	public void shuffle()
	{
		dealerDeck.shuffle();
	}
	
	public void returnCard(Card card)
	{
		dealerDeck.addCard(card);
	}
	
	public void returnCommCards()
	{
		for(int i =0; i<commCards.length; i++)
		{
			dealerDeck.addCard(commCards[i]);
		}
	}
	
	public void wipeCommCards()
	{
		commCards = new Card[5];
	}
	
	
}