/*
  Unit Tests For Texas Hold 'Em
*/
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import java.io.*;

public class TestTexasHoldEm
{
    /*
    Tests for Player class.
    */

    //Tests player initalization gives player $1000.
    @Test
    public void testPlayerGivenMoney()
    {
      Player p = new Player();
      assertTrue(p.getMoney() == 1000.00);
    }

    //tests player initalization sets player status to 0 (In game)
    @Test
    public void testPlayerInitalStatus()
    {
        Player p = new Player();
        assertTrue(p.getStatus() == 0);
    }

    //tests player initalization sets player type to 0 (Human)
    @Test
    public void testPlayerInitalType()
    {
        Player p = new Player();
        assertTrue(p.getPlayerType() == 0);
    }


    /*
    Tests for Card class.
    */

    //Test card class initialization.
    @Test
    public void testCardClassInitlaization()
    {
        Card card = new Card(Card.Suit.Spades,Card.Rank.Four);
        assertThat(card, instanceOf(Card.class));
    }

    //Test card class string name return.
    @Test
    public void testCardClassToString()
    {
        Card card = new Card(Card.Suit.Spades,Card.Rank.Four);
        assertTrue(card.toString().equals("Four of Spades"));
    }

    //Test card class rank return.
    @Test
    public void testCardClassRank()
    {
        Card card = new Card(Card.Suit.Spades,Card.Rank.Four);
        assertTrue(card.getRank().equals("Four"));
    }

    //Test card class suit return.
    @Test
    public void testCardClassSuit()
    {
        Card card = new Card(Card.Suit.Spades,Card.Rank.Four);
        assertTrue(card.getSuit().equals("Spades"));
    }

    //Test card class compare method.
    @Test
    public void testCardClassCompare()
    {
        Card card1 = new Card(Card.Suit.Spades,Card.Rank.Four);
        Card card2 = new Card(Card.Suit.Hearts,Card.Rank.Four);
        assertTrue(card1.compareTo(card2) == 0);
    }

    /*
    Tests for Deck class.
    */

    //Test deck class initialization.
    @Test
    public void testDeckClassInitlaization()
    {
        Deck deck = new Deck();
        assertThat(deck, instanceOf(Deck.class));
    }

    //Test removeCard() method from deck class.
    @Test
    public void testRemoveDeckClassMethod()
    {
        Deck deck = new Deck();
        assertThat(deck.removeCard(), instanceOf(Card.class));
    }


    /*
    Tests for dealer class.
    */

    //Tests dealer class initalization sets pot to zero.
    @Test
    public void testDealerInitalizedPotZero()
    {
        Deck deck = new Deck();
        Dealer dealer = new Dealer(deck);
        assertTrue(dealer.getPot() == 0.00);
    }

    //Tests dealer class initalization creates common cards array,
    //of size 5.
    @Test
    public void testDealerInitalizedCommonCards()
    {
        Deck deck = new Deck();
        Dealer dealer = new Dealer(deck);
        assertTrue(dealer.getCommCards().length == 5);
    }



    /*
    Tests for Hands class.
    */

    //Test for royal flush hand.
    @Test
    public void testForRoyalFlush()
    {
        Card [] hand = new Card[5];
        hand[0] = new Card(Card.Suit.Hearts,Card.Rank.Ten);
        hand[1] = new Card(Card.Suit.Hearts,Card.Rank.Jack);
        hand[2] = new Card(Card.Suit.Hearts,Card.Rank.Queen);
        hand[3] = new Card(Card.Suit.Hearts,Card.Rank.King);
        hand[4] = new Card(Card.Suit.Hearts,Card.Rank.Ace);
        assert(Hands.royalFlush(hand));
    }

    //Test for straight flush hand.
    @Test
    public void testForStraightFlush()
    {
        Card [] hand = new Card[5];
        hand[0] = new Card(Card.Suit.Hearts,Card.Rank.Ten);
        hand[1] = new Card(Card.Suit.Hearts,Card.Rank.Eight);
        hand[2] = new Card(Card.Suit.Hearts,Card.Rank.Seven);
        hand[3] = new Card(Card.Suit.Hearts,Card.Rank.Nine);
        hand[4] = new Card(Card.Suit.Hearts,Card.Rank.Six);
        assert(Hands.straightFlush(hand));
    }

    //Test for four of a kind hand.
    @Test
    public void testFourOfKindHand()
    {
        Card [] hand = new Card[5];
        hand[0] = new Card(Card.Suit.Hearts,Card.Rank.Ten);
        hand[1] = new Card(Card.Suit.Spades,Card.Rank.Ten);
        hand[2] = new Card(Card.Suit.Clubs,Card.Rank.Ten);
        hand[3] = new Card(Card.Suit.Hearts,Card.Rank.Nine);
        hand[4] = new Card(Card.Suit.Diamonds,Card.Rank.Ten);
        assert(Hands.fourOfAKind(hand));
    }

    //Test for full house hand.
    @Test
    public void testFullHouseHand()
    {
        Card [] hand = new Card[5];
        hand[0] = new Card(Card.Suit.Hearts,Card.Rank.Ten);
        hand[1] = new Card(Card.Suit.Spades,Card.Rank.Ten);
        hand[2] = new Card(Card.Suit.Clubs,Card.Rank.Ten);
        hand[3] = new Card(Card.Suit.Hearts,Card.Rank.Nine);
        hand[4] = new Card(Card.Suit.Diamonds,Card.Rank.Nine);
        assert(Hands.fullHouse(hand));
    }

    //Test for flush hand.
    @Test
    public void testFlushHand()
    {
        Card [] hand = new Card[5];
        hand[0] = new Card(Card.Suit.Diamonds,Card.Rank.Two);
        hand[1] = new Card(Card.Suit.Diamonds,Card.Rank.Ten);
        hand[2] = new Card(Card.Suit.Diamonds,Card.Rank.Four);
        hand[3] = new Card(Card.Suit.Diamonds,Card.Rank.Ace);
        hand[4] = new Card(Card.Suit.Diamonds,Card.Rank.King);
        assert(Hands.flush(hand));
    }

    //Test for straight hand.
    @Test
    public void testStraightHand()
    {
        Card [] hand = new Card[5];
        hand[0] = new Card(Card.Suit.Hearts,Card.Rank.Two);
        hand[1] = new Card(Card.Suit.Spades,Card.Rank.Four);
        hand[2] = new Card(Card.Suit.Clubs,Card.Rank.Six);
        hand[3] = new Card(Card.Suit.Hearts,Card.Rank.Five);
        hand[4] = new Card(Card.Suit.Diamonds,Card.Rank.Three);
        assert(Hands.straight(hand));
    }

    //Test for three of a kind hand.
    @Test
    public void testThreeOfKindHand()
    {
        Card [] hand = new Card[5];
        hand[0] = new Card(Card.Suit.Hearts,Card.Rank.Two);
        hand[1] = new Card(Card.Suit.Spades,Card.Rank.Four);
        hand[2] = new Card(Card.Suit.Clubs,Card.Rank.Two);
        hand[3] = new Card(Card.Suit.Hearts,Card.Rank.Five);
        hand[4] = new Card(Card.Suit.Diamonds,Card.Rank.Two);
        assert(Hands.threeOfAKind(hand));
    }

    //Test for two pair hand.
    @Test
    public void testTwoPairHand()
    {
        Card [] hand = new Card[5];
        hand[0] = new Card(Card.Suit.Hearts,Card.Rank.Five);
        hand[1] = new Card(Card.Suit.Spades,Card.Rank.Four);
        hand[2] = new Card(Card.Suit.Clubs,Card.Rank.Two);
        hand[3] = new Card(Card.Suit.Hearts,Card.Rank.Five);
        hand[4] = new Card(Card.Suit.Diamonds,Card.Rank.Two);
        assert(Hands.twoPair(hand));
    }

    //Test for one pair hand.
    @Test
    public void testOnePairHand()
    {
        Card [] hand = new Card[5];
        hand[0] = new Card(Card.Suit.Hearts,Card.Rank.Five);
        hand[1] = new Card(Card.Suit.Spades,Card.Rank.Four);
        hand[2] = new Card(Card.Suit.Clubs,Card.Rank.Six);
        hand[3] = new Card(Card.Suit.Hearts,Card.Rank.Five);
        hand[4] = new Card(Card.Suit.Diamonds,Card.Rank.Seven);
        assert(Hands.onePair(hand));
    }

    /*
    Tests for Logger class.
    */

    //Tests instance on Logger class is created.
    @Test
    public void testInstanceOfLoggerCreated()
    {
        Player [] players = new Player[3];
        players[0] = new Player("Player 1");
        players[1] = new Player("Player 2");
        players[2] = new Player("Player 3");
        Logger logger = null;
        try
        {
            logger = new Logger("test_output.txt",players);
        }
        catch(Exception e){}
        assertThat(logger, instanceOf(Logger.class));
    }

    //Tests log method in logger class.
    @Test
    public void testLogMethod()
    {
        Player [] players = new Player[3];
        players[0] = new Player("Player 1");
        players[1] = new Player("Player 2");
        players[2] = new Player("Player 3");
        Logger logger = null;
        try
        {
            logger = new Logger("test_output.txt",players);
        }
        catch(Exception e){}
        assertTrue(logger.log("This is a test."));
    }

    //Tests that logger closes the log file.
    @Test
    public void testLoggerFileCloses()
    {
        Player [] players = new Player[3];
        players[0] = new Player("Player 1");
        players[1] = new Player("Player 2");
        players[2] = new Player("Player 3");
        Logger logger = null;
        try
        {
            logger = new Logger("test_output.txt",players);
            logger.log("This is a test.");
        }
        catch(Exception e){}
        assertTrue(logger.closeLogger());
    }

}
