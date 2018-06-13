import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.FlowLayout;

import java.awt.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

class GameBoard extends JFrame
{
	private PlayerContainer[] players;
	private int numberOfOpponents;
	private ArrayList<Player> opponents;
	private Dealer dealer;
	
	public GameBoard(String title)
	{
		super(title);
		
		this.setLayout(new FlowLayout());
		
		this.setSize(1300, 900);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void createPlayerFrames(String playerName, int numberOfOpponents)
	{
		this.numberOfOpponents = numberOfOpponents;
		players = new PlayerContainer[numberOfOpponents+1];
		
		//create player containers to display
		for(int i = 0; i < numberOfOpponents+1; i++)
		{
			PlayerContainer temp = new PlayerContainer();
			players[i] = temp;
			this.add(temp);
		}
		
		//create dealer container to display
		DealerContainer dealerBox = new DealerContainer();
		
		this.add(dealerBox);
		this.setVisible(true);
		
		//call function to assign players to each container
		assignPlayers(playerName);
	}
	
	public void assignSharedVariables(Dealer dealer, PlayerContainer[] players)
	{
		this.players = players;
		this.dealer = dealer;
	}
	
	private void assignPlayers(String playerName)
	{
		ArrayList<String> names = new ArrayList<>(Arrays.asList("Jacques S.", 
		"Seymour B.", "Mike R.", "Ollie T.", "Al C.", 
		"Oliver C.", "Libby D.", "Sarah N.", "Robin B.", "Hugh J."));
		
		Collections.shuffle(names);
		
		//create ArrayList of opponents
		opponents = new ArrayList(numberOfOpponents);
		
		//set 0th player to be human
		players[0].setPlayer(new Player(playerName));
		
		//set remaining players to be AI
		for(int i = 1; i < numberOfOpponents+1; i++)
		{
			Player temp = new Player(names.get(i));
			players[i].setPlayer(temp);
			opponents.add(temp);
		}
		
		//iterate over players, showing their respective information
		for(int i = 0; i < players.length; i++)
		{
			PlayerContainer container = players[i];
			Player player = container.getPlayer();
			
			container.updateName();
			container.updateMoney();
			
			dealer.dealCard(player);
			dealer.dealCard(player);
			
			container.setCardOne();
			container.setCardTwo();
			
		}
	}
}

