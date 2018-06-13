import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import java.awt.Dimension;

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
	
	public DealerContainer()
	{
		this.setLayout(new FlowLayout());
		this.setPreferredSize(new Dimension(650, 300));
		
		cardOnePanel = new JPanel();
		cardOnePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		cardOnePanel.setPreferredSize(new Dimension (120, 160));
		this.add(cardOnePanel);
		
		cardTwoPanel = new JPanel();
		cardTwoPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		cardTwoPanel.setPreferredSize(new Dimension (120, 160));
		this.add(cardTwoPanel);
		
		cardThreePanel = new JPanel();
		cardThreePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		cardThreePanel.setPreferredSize(new Dimension (120, 160));
		this.add(cardThreePanel);
		
		cardFourPanel = new JPanel();
		cardFourPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		cardFourPanel.setPreferredSize(new Dimension (120, 160));
		this.add(cardFourPanel);
		
		cardFivePanel = new JPanel();
		cardFivePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		cardFivePanel.setPreferredSize(new Dimension (120, 160));
		this.add(cardFivePanel);
		
		minBetLabel = new JLabel("Min Bet:");
		this.add(minBetLabel);
		
		minBet = new JLabel("No bet.");
		this.add(minBet);
		
		potLabel = new JLabel("Pot:");
		this.add(potLabel);
		
		pot = new JLabel("Not pot.");
		this.add(pot);
	}
	
	public void setPot(String newPot)
	{
		pot.setText(newPot);
	}
	
	public void setMinBet(String newBet)
	{
		minBet.setText(newBet);
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