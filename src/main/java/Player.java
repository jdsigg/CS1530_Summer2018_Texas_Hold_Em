class Player
{

  /*
    Initilize Variables
  */
  String name;
  double money;
  Card [] currentHand = new Card[2];
  private int currentHandIndex;

  public Player()
	{
    //Init Constructor for Player
    this.money = 1000.00;
    currentHandIndex = 0;
	}

	public Player(String name)
	{
    //Constructor for Player
    this.name = name;
    this.money = 1000.00;
    currentHandIndex = 0;
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
  public Card [] getCurrentHand()
  {
    return this.currentHand;
  }

}
