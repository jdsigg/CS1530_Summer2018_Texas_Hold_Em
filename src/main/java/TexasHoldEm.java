import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

class TexasHoldEm
{
	public static void main(String []args)
	{
		Deck deck = new Deck();
		PlayerContainer[] players = new PlayerContainer[8];
		Dealer dealer = new Dealer(deck);
		
		dealer.shuffle();
		
		GameBoard runMe = new GameBoard("Test GUI");
		
		Game game = new Game(runMe, dealer);
		game.setPlayerContainers(players);
		
		runMe.assignSharedVariables(dealer, players);
		
		InitialPlayerDialog dialog = new InitialPlayerDialog(runMe, game, "Test Dialog");
	}
}