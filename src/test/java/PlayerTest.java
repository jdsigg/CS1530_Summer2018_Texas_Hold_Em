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

}
