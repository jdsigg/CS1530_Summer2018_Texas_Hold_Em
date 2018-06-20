import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionEvent;


//import javax.swing.JFrame;

class PlayerContainer extends JPanel
{
	private JPanel mainPanel;
	private JPanel cardOnePanel;
	private JPanel cardTwoPanel;
	private JLabel playerName;
	private JLabel playerMoney;
	private JButton betButton;
	private JButton callButton;
	
	private Player player;
	private Game game;
	
	public PlayerContainer(Game game)
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
		
		betButton = new JButton("Bet $20");
		betButton.addActionListener(e -> betButtonActionPerformed(e));
		this.add(betButton);
		
		callButton = new JButton("Call");
		callButton.addActionListener(e -> callButtonActionPerformed(e));
		this.add(callButton);
		this.game = game;
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
		cardOnePanel.removeAll();
		
		Image cardOne = player.getCurrentHand()[0].getImage().getImage();
		Image scaledCardOne = cardOne.getScaledInstance(120, 150, Image.SCALE_SMOOTH);
		
		cardOnePanel.add(new JLabel(new ImageIcon(scaledCardOne)));
		cardOnePanel.repaint();
		cardOnePanel.revalidate();
	}
	
	public void setCardTwo()
	{
		cardTwoPanel.removeAll();
		
		Image cardTwo = player.getCurrentHand()[1].getImage().getImage();
		Image scaledCardTwo = cardTwo.getScaledInstance(120, 150, Image.SCALE_SMOOTH);
		cardTwoPanel.add(new JLabel(new ImageIcon(scaledCardTwo)));
		cardTwoPanel.repaint();
		cardTwoPanel.revalidate();
	}
	
	public void setBlanks()
	{
		cardOnePanel.removeAll();
		cardTwoPanel.removeAll();
		cardOnePanel.add(new JLabel());
		cardTwoPanel.add(new JLabel());
		cardOnePanel.repaint();
		cardOnePanel.revalidate();
		cardTwoPanel.repaint();
		cardTwoPanel.revalidate();
	}
	
	public void betButtonActionPerformed(ActionEvent e)
	{
		System.out.println("State: "+ game.getState());
		int defaultBet = 20;
		player.setBet(defaultBet);
		
		//call a method that folds everybody else
			
		game.foldEveryone();
	}
	
	public void callButtonActionPerformed(ActionEvent e)
	{
		//call a method that calls everybody else
			
		game.nextState();
	}
	
	public void hideButtons()
	{
		this.betButton.setVisible(false);
		this.callButton.setVisible(false);		
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