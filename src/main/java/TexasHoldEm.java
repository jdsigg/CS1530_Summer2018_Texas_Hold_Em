import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.io.*;

class TexasHoldEm
{
	public static void main(String []args) throws IOException
	{
		Deck deck = new Deck();
		PlayerContainer[] players = new PlayerContainer[8];
		Dealer dealer = new Dealer(deck);

		dealer.shuffle();

		GameBoard runMe = new GameBoard("Test GUI");

		Game game = new Game(runMe, dealer);

		runMe.assignSharedVariables(dealer);

		InitialPlayerDialog dialog = new InitialPlayerDialog(runMe, game, "Test Dialog");
	}
}
