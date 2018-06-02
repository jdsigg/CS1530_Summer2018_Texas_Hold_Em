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
	Method to shuffle the deck
	*/
	public void shuffleDeck()
	{
		dealerDeck
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
	
	
	
}