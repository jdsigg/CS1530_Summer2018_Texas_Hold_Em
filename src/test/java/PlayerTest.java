/*
  Tests Player, HumanPlayer, and AiPlayer classes
*/
import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerTest
{
  @Test
  public void testPlayerGivenMoney()
  {
      Player p = new Player();
      assertTrue(p.getMoney() == 1000.00);
  }

  /*@Test
  public void testLoggerClass()
  {
        Player [] p = new Player[3];
        for(int x = 0;x<p.length;x++)
        {
            p[x] = new Player("Antonino " + x);
        }
        Logger l = new Logger("test.txt",p);
        l.closeLogger();
        //assertTrue(p.getMoney() == 1000.00);
  }*/

  /*
  @Test
  public void testPlayerCardHand()
  {
    Player p = new Player();
    Card c = new Card();
    p.addCard(c);
    p.addCard(c);
    Card [] arrayC = {c,c};
    assertEquals(p.getCurrentHand(),arrayC);
  }
  */

}
