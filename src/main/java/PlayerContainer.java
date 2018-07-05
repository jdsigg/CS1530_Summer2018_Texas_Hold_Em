import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;

class PlayerContainer extends JPanel
{
	private JPanel mainPanel;
	private JPanel cardOnePanel;
	private JPanel cardTwoPanel;
	private JLabel playerName;
	private JLabel playerMoney;

	private JLabel dealerLabel;
	private JLabel smallBlindLabel;
	private JLabel bigBlindLabel;

	private Player player;
	private Game game;

	public PlayerContainer(Player player, Game game)
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

		dealerLabel = new JLabel("");
		dealerLabel.setForeground(Color.DARK_GRAY);
		this.add(dealerLabel);

		smallBlindLabel = new JLabel("");
		smallBlindLabel.setForeground(Color.RED);
		this.add(smallBlindLabel);

		bigBlindLabel = new JLabel("");
		bigBlindLabel.setForeground(Color.BLUE);
		this.add(bigBlindLabel);

		this.player = player;
		this.game = game;
	}

	public void updateName()
	{
		playerName.setText(player.getName());
	}

	public void updateMoney()
	{
		playerMoney.setText("$"+Integer.toString(player.getMoney()));
	}

	/*
	Method to update the first card in the players hand
	*/
	public void setCardOne()
	{
		cardOnePanel.removeAll();

		Image cardOne = player.getCurrentHand()[0].getImage().getImage();
		Image scaledCardOne = cardOne.getScaledInstance(120, 150, Image.SCALE_SMOOTH);

		cardOnePanel.add(new JLabel(new ImageIcon(scaledCardOne)));
		cardOnePanel.repaint();
		cardOnePanel.revalidate();
	}

	/*
	Method to update the second card in the players hand
	*/
	public void setCardTwo()
	{
		cardTwoPanel.removeAll();

		Image cardTwo = player.getCurrentHand()[1].getImage().getImage();
		Image scaledCardTwo = cardTwo.getScaledInstance(120, 150, Image.SCALE_SMOOTH);

		cardTwoPanel.add(new JLabel(new ImageIcon(scaledCardTwo)));
		cardTwoPanel.repaint();
		cardTwoPanel.revalidate();
	}

	/*
	Method to show blanks for a new hand
	*/
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

	public void blackBorder()
	{
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.repaint();
		this.revalidate();
	}

	public void redBorder()
	{
		this.setBorder(BorderFactory.createLineBorder(Color.red));
		this.repaint();
		this.revalidate();
	}

	public void clearBorder()
	{
		this.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		this.repaint();
		this.revalidate();
	}

	public void displayDealer()
	{
		boolean status = player.isDealer();

		if(status)
		{
			dealerLabel.setText("Dealer");
		}
		else
		{
			dealerLabel.setText("");
		}
	}

	public void displaySmallBlind()
	{
		boolean status = player.isSmallBlind();

		if(status)
		{
			smallBlindLabel.setText("Small Blind");
		}
		else
		{
			smallBlindLabel.setText("");
		}
	}

	public void displayBigBlind()
	{
		boolean status = player.isBigBlind();

		if(status)
		{
			bigBlindLabel.setText("Big Blind");
		}
		else
		{
			bigBlindLabel.setText("");
		}
	}
}
