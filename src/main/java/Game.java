class Game
{
	private int numberOfOpponents;
	private PlayerContainer[] players;
	private Player[] activePlayers;
	private int state;
	private GameBoard gameBoard;
	private Dealer dealer;
    /*
	Constructor for Game Class
    */
    public Game(GameBoard gameBoard, Dealer dealer)
    {
		this.numberOfOpponents = 0;
		this.state = 1;
		this.gameBoard = gameBoard;
		this.dealer = dealer;
    }

    /*
	method to deal the players
    */
    private void dealToPlayer()
    {

    }

    /*
    method to deal five commcards[5]
    */
    private void dealCommCards()
    {
		
    }

    /*
	method to call when a player, human or AI is
	going to fold. Method must accept a player. The
	player passed in is the player that is going to
	fold.
    */
    private void playerFold(Player player)
    {
	
    }
	
	public void foldEveryone()
	{
		activePlayers = new Player[1];
		activePlayers[0] = players[0].getPlayer();
		checkWinner(activePlayers);
		resetGame();
	}
	
	public void nextState()
	{
		if(state==1)
		{
			startNewHand();
		}
		gameBoard.displayCommCard(state);
		if (state == 5)
		{
			activePlayers = new Player[numberOfOpponents+1];
			for (int i=0; i<activePlayers.length; i++)
			{
				activePlayers[i] = players[i].getPlayer();
			}
			//checkWinner(activePlayers);
			//TODO: Give winner pot
			resetGame();
		}
		else
		{
			state++;
		}
	}
	
	public void resetGame()
	{
		for (int i=0; i<players.length; i++)
		{
			Player temp = players[i].getPlayer();
			Card[] tempCards = temp.getCurrentHand();
			dealer.returnCard(tempCards[0]);
			dealer.returnCard(tempCards[1]);
			temp.wipeHand();
			temp.setBet(0);
		}
		
		dealer.returnCommCards();
		dealer.wipeCommCards();
		state = 0;
	}
	
	public void startNewHand()
	{
		dealer.shuffle();
		for (int i=0; i<players.length; i++)
		{
			Player temp = players[i].getPlayer();
			dealer.dealCard(temp);
			dealer.dealCard(temp);
			if(i==0){
				gameBoard.displayPlayerHand(i);
			}
		}
		dealer.dealCommCards();
	}
	
	public void setNumberOfOpponents(int num)
	{
		this.numberOfOpponents = num;
	}
	
	public void setPlayerContainers(PlayerContainer[] players)
	{
		this.players = players;
	}

    /*
	method to call when a player, human or AI is
	going to check. Method must accept a player.
	The player passed in is the player that is
	going to check.
    */
    private void playerCheck(Player player)
    {

    }

    /*
	Method to check, after the round is over, who won.
	It accepts an array of all of the players.
    */
    private void checkWinner(Player [] players)
    {
		
    }
	
	public int getState()
	{
		return state;
	}
}
