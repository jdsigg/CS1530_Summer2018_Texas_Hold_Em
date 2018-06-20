class Player
{

	/*
    Initialize Variables
	*/
	private String name;
	private double money;
	private Card [] currentHand = new Card[2];
	private int currentHandIndex;
	private int bet;

	public Player()
	{
		//Init Constructor for Player
		this.money = 1000.00;
		currentHandIndex = 0;
		this.bet = 0;
	}

	public Player(String name)
	{
		//Constructor for Player
		this.name = name;
		this.money = 1000.00;
		currentHandIndex = 0;
		this.bet = 0;
	}

	/*
	Method to get player's stack (Money)
	*/
	public double getMoney()
	{
		return this.money;
	}

	/*
    Method to get player's name
	*/
	public String getName()
	{
		return this.name;
	}

	/*
    Method to update player's stack (Money)
	*/
	public void updateMoney(double money)
	{
		this.money = money;
	}

	/*
    Method to set player's name
    NOTE: you can set the player's name in the constructor
	*/
	public void setName(String name)
	{
		this.name = name;
	}

	/*
    Method to deal player a Card
    You can add ONE card at a time
	*/
	public void addCard(Card card)
	{
		assert(currentHandIndex < 2); //Make sure player only has two cards
		this.currentHand[currentHandIndex] = card;
		currentHandIndex++;
	}

	/*
    Method to get player's current hand
    returns a array of type Card
	*/
	public Card[] getCurrentHand()
	{
		return this.currentHand;
	}
	
	public void wipeHand()
	{
		currentHandIndex = 0;
		this.currentHand = new Card[2];
	}
	
	/*
	Method to set the players bet
	*/
	public void setBet(int newBet)
	{
		this.bet = newBet;
		this.updateMoney(this.getMoney()-newBet);
	}
	
	/*
	Method to get the players bet
	*/
	public int getBet()
	{
		return bet;
	}

}
