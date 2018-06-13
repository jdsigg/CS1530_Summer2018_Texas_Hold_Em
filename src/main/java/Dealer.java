import java.util.Collections;
import java.util.ArrayList;

class Dealer
{
	/*
	Initialize Variables
	*/
	private double pot;
	private Deck dealerDeck;
	
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
		
	public void shuffle()
	{
		Collections.shuffle(dealerDeck.getDeck());
	}
        
	/*
	Method to deal the community cards
	public void dealCommunityCards()
	{
		
	}
	*/
	
}