import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.ImageIcon;

//import javax.swing.JFrame;

class DealerContainer extends JPanel
{
	private JPanel cardOnePanel;
	private JPanel cardTwoPanel;
	private JPanel cardThreePanel;
	private JPanel cardFourPanel;
	private JPanel cardFivePanel;
	
	private JLabel minBetLabel;
	private JLabel minBet;
	private JLabel potLabel;
	private JLabel pot;
	
	private Dealer dealer;
	
	public DealerContainer(Dealer dealer)
	{
		this.setLayout(new FlowLayout());
		this.setPreferredSize(new Dimension(650, 300));
		
		cardOnePanel = new JPanel();
		cardOnePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		cardOnePanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		cardOnePanel.setPreferredSize(new Dimension (120, 160));
		this.add(cardOnePanel);
		
		cardTwoPanel = new JPanel();
		cardTwoPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		cardTwoPanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		cardTwoPanel.setPreferredSize(new Dimension (120, 160));
		this.add(cardTwoPanel);
		
		cardThreePanel = new JPanel();
		cardThreePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		cardThreePanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		cardThreePanel.setPreferredSize(new Dimension (120, 160));
		this.add(cardThreePanel);
		
		cardFourPanel = new JPanel();
		cardFourPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		cardFourPanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		cardFourPanel.setPreferredSize(new Dimension (120, 160));
		this.add(cardFourPanel);
		
		cardFivePanel = new JPanel();
		cardFivePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		cardFivePanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		cardFivePanel.setPreferredSize(new Dimension (120, 160));
		this.add(cardFivePanel);
		
		minBetLabel = new JLabel("Min Bet:");
		this.add(minBetLabel);
		
		minBet = new JLabel("No bet.");
		this.add(minBet);
		
		potLabel = new JLabel("Pot:");
		this.add(potLabel);
		
		pot = new JLabel("No pot.");
		this.add(pot);
		
		this.dealer = dealer;
	}
	
	/*
	Update the pot display
	*/
	public void setPot(String newPot)
	{
		pot.setText(newPot);
	}
	
	/*
	Update the min bet display
	*/
	public void setMinBet(String newBet)
	{
		minBet.setText(newBet);
	}
	
	/*
	Display card one
	*/
	public void setCardOne()
	{
		cardOnePanel.removeAll();
		
		Image cardOne = dealer.getCommCards()[0].getImage().getImage();
		Image scaledCardOne = cardOne.getScaledInstance(120, 150, Image.SCALE_SMOOTH);
		
		cardOnePanel.add(new JLabel(new ImageIcon(scaledCardOne)));
		
		cardOnePanel.repaint();
		cardOnePanel.revalidate();
	}
	
	/*
	Display card two
	*/
	public void setCardTwo()
	{
		cardTwoPanel.removeAll();
		
		Image cardTwo = dealer.getCommCards()[1].getImage().getImage();
		Image scaledCardTwo = cardTwo.getScaledInstance(120, 150, Image.SCALE_SMOOTH);
		
		cardTwoPanel.add(new JLabel(new ImageIcon(scaledCardTwo)));
		
		cardTwoPanel.repaint();
		cardTwoPanel.revalidate();
	}
	
	/*
	Display card three
	*/
	public void setCardThree()
	{
		cardThreePanel.removeAll();
		
		Image cardThree = dealer.getCommCards()[2].getImage().getImage();
		Image scaledCardThree = cardThree.getScaledInstance(120, 150, Image.SCALE_SMOOTH);
		
		cardThreePanel.add(new JLabel(new ImageIcon(scaledCardThree)));
		
		cardThreePanel.repaint();
		cardThreePanel.revalidate();
	}
	
	/*
	Display card four
	*/
	public void setCardFour()
	{
		cardFourPanel.removeAll();
		
		Image cardFour = dealer.getCommCards()[3].getImage().getImage();
		Image scaledCardFour = cardFour.getScaledInstance(120, 150, Image.SCALE_SMOOTH);
		
		cardFourPanel.add(new JLabel(new ImageIcon(scaledCardFour)));
		
		cardFourPanel.repaint();
		cardFourPanel.revalidate();
	}
	
	/*
	Display card five
	*/
	public void setCardFive()
	{
		cardFivePanel.removeAll();
		
		Image cardFive = dealer.getCommCards()[4].getImage().getImage();
		Image scaledCardFive = cardFive.getScaledInstance(120, 150, Image.SCALE_SMOOTH);
		
		cardFivePanel.add(new JLabel(new ImageIcon(scaledCardFive)));
		
		cardFivePanel.repaint();
		cardFivePanel.revalidate();
	}
	
	/*
	Display all cards as blank
	*/
	public void setBlanks()
	{
		cardOnePanel.removeAll();
		cardTwoPanel.removeAll();
		cardThreePanel.removeAll();
		cardFourPanel.removeAll();
		cardFivePanel.removeAll();
		
		cardOnePanel.add(new JLabel());
		cardTwoPanel.add(new JLabel());
		cardThreePanel.add(new JLabel());
		cardFourPanel.add(new JLabel());
		cardFivePanel.add(new JLabel());
		
		this.repaint();
		this.revalidate();
	}
	
	/*
	Return the dealer contained within the container
	*/
	public Dealer getDealer()
	{
		return dealer;
	}
	
	/*
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700, 300);
		
		frame.add(new DealerContainer());
		
		frame.setVisible(true);
	}
	*/
}