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

}
