import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.io.*;

class TexasHoldEm
{
	public static void main(String []args) throws IOException
	{
		/*Player [] p = new Player[3];
        for(int x = 0;x<p.length;x++)
        {
            p[x] = new Player("Antonino " + x);
        }
        Logger l = new Logger("test.txt",p);
		l.logGamePlay("GAme PLay 1");
		l.logGamePlay("GAme PLay 2");
        l.closeLogger();*/

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
