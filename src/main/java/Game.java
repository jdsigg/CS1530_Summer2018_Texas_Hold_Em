import java.util.HashMap;

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
			checkWinner(activePlayers);
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
    private void checkWinner(Player[] players)
    {
		Card[] currHand = new Card[7];
		HashMap<String, Integer> scores = new HashMap<String, Integer>();
		int score = 0;
		
		for (int i =0; i<5; i++)
		{
			currHand[i] = dealer.getCommCards()[i];
		}

		for(int i=0; i<players.length; i++) {
			
			currHand[5] = players[i].getCurrentHand()[0];
			currHand[6] = players[i].getCurrentHand()[1];
			score = 0;
			
			System.out.println(players[i].getName());
			
			//loop to make seven cards here into currHand
			
			if(Hands.royalFlush(currHand)) {
				System.out.println("Player somehow has a royal flush");
				scores.put(players[i].getName(), 10); //give player a score
			}
			else if(Hands.straightFlush(currHand)) {
				System.out.println("Player has straight flush");
				scores.put(players[i].getName(), 9); //give player a score
			}
			else if(Hands.fourOfAKind(currHand)) {
				System.out.println("Player has four of a kind");
				scores.put(players[i].getName(), 8); //give player a score
			}
			else if(Hands.fullHouse(currHand)) {
				System.out.println("Player has full house");
				scores.put(players[i].getName(), 7); //give player a score
			}
			else if(Hands.flush(currHand)) {
				System.out.println("Player has a flush");
				scores.put(players[i].getName(), 6); //give player a score
			}
			else if(Hands.straight(currHand)) {
				System.out.println("Player has straight");
				scores.put(players[i].getName(), 5); //give player a score
			}
			else if(Hands.threeOfAKind(currHand)) {
				System.out.println("Player has three of a kind");
				 scores.put(players[i].getName(), 4); //give player a score
			}
			else if(Hands.twoPair(currHand)) {
				System.out.println("Player has two pair");
				scores.put(players[i].getName(), 3); //give player a score
			}
			else if(Hands.onePair(currHand)) {
				System.out.println("Player has a pair");
				scores.put(players[i].getName(), 2); //give player a score
			}
			else if(Hands.highCard(currHand)) {
				System.out.println("Player only has high card");
				scores.put(players[i].getName(), 1); //give player a score
			}
		}
		
		int max = 0;
		String winner = "";
		
		for(int i = 0; i < players.length; i++)
		{
			if(scores.get(players[i].getName()) > max)
			{
				max = scores.get(players[i].getName());
				winner = players[i].getName();
			}	
		}
		
		System.out.println(winner + " won the hand!");
		
	}
	
	public int getState()
	{
		return state;
	}
}
