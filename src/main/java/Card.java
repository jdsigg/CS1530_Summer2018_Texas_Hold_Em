import javax.swing.*;
import java.awt.*;

public class Card
{
	public static enum Suit {Spades, Clubs, Diamonds, Hearts}
	public static enum Rank {Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King, Ace}
	
	private Suit suit;
	private Rank rank;
	private ImageIcon img;
	private static final String IMAGE_LOC = "..\\..\\..\\img\\png\\";
	
	public Card(Suit s, Rank r)
	{
		this.suit = s;
		this.rank = r;
		this.img = getImage(s, r);
	}
	
	public ImageIcon getImage(Suit s, Rank r) 
	{
		return new ImageIcon(IMAGE_LOC + r.name() + s.name() + ".png");
	}
	
	public String toString()
	{
		return (this.rank + " of " + this.suit);
	}
	
	public int compareTo(Card card2)
	{
		return this.rank.compareTo(card2.rank); //returns 0 if equal, pos if c1 > c2, neg if c1 < c2
	}
        
        public ImageIcon getImage()
        {
            return img;
        }
        
        public String getSuit()
        {
            return this.suit.toString();
        }
        
        public String getRank()
        {
            return this.rank.toString();
        }
	
}

