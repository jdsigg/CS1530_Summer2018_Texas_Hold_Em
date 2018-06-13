import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.ImageIcon;

//import javax.swing.JFrame;

class PlayerContainer extends JPanel
{
	private JPanel mainPanel;
	private JPanel cardOnePanel;
	private JPanel cardTwoPanel;
	private JLabel playerName;
	private JLabel playerMoney;
	
	private Player player;
	
	public PlayerContainer()
	{
		this.setLayout(new FlowLayout());
		this.setPreferredSize(new Dimension(260,300));
		
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
		
		playerName = new JLabel("Default Name");
		this.add(playerName);
		
		playerMoney = new JLabel("Default Money");
		this.add(playerMoney);
	}
	
	public void setPlayer(Player player)
	{
		this.player = player;
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	public void updateName()
	{
		playerName.setText(player.getName());
	}
	
	public void updateMoney()
	{
		playerMoney.setText(Double.toString(player.getMoney()));
	}
	
	public void setCardOne()
	{
		Image cardOne = player.getCurrentHand()[0].getImage().getImage();
		Image scaledCardOne = cardOne.getScaledInstance(120, 150, Image.SCALE_SMOOTH);
		
		cardOnePanel.add(new JLabel(new ImageIcon(scaledCardOne)));
	}
	
	public void setCardTwo()
	{
		Image cardTwo = player.getCurrentHand()[1].getImage().getImage();
		Image scaledCardTwo = cardTwo.getScaledInstance(120, 150, Image.SCALE_SMOOTH);
		cardTwoPanel.add(new JLabel(new ImageIcon(scaledCardTwo)));
	}
	
	/*
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		
		frame.add(new PlayerContainer());
		
		frame.setVisible(true);
	}
	*/
}