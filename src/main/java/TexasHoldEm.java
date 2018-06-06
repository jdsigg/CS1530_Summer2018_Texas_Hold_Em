import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;

public class TexasHoldEm
{
	public static void main(String[] args)
	{
		//Initialize the gameboard, deck, and dealer
		GameBoard table = new GameBoard();
		Deck dealerDeck = new Deck();
		Dealer Zhlata = new Dealer(dealerDeck);
		
		ArrayList<String> names = new ArrayList<>(Arrays.asList("Jacques S.", 
		"Seymour B.", "Mike R.", "Ollie T.", "Al C.", 
		"Oliver C.", "Libby D.", "Sarah N.", "Robin B.", "Hugh J."));
		
		//Get needed info from GUI class to set up the gameboard
		int numOfOpponents = table.getNumberOfComputers();
		String humanName = table.getHumanName();
		
		//Initialize an ArrayList with the number of opponents
		ArrayList<Player> opponents = new ArrayList<>(numOfOpponents);
		
		//Shuffle the names of opponents to keep things fresh
		Collections.shuffle(names);
		
		//Give opponents names
		for (int i=0; i<numOfOpponents; i++)
		{
			Player x = new Player(names.get(i));
			opponents.add(x);
		}
                
                dealerDeck.shuffle();    
                table.getDealerContainer().setDealer(Zhlata);
                table.getDealerContainer().setPot();
                
                PlayerContainer[] players = table.getPlayers();
                
                players[0].setPlayer(new Player(humanName));
                for(int i = 1; i < numOfOpponents+1; i++)
                    players[i].setPlayer(opponents.get(i-1));
                
                for(int i = 0; i < numOfOpponents+1; i++)
                {
                    players[i].updateName();
                    players[i].updateMoney();
                    players[i].getPlayer().addCard(dealerDeck.removeCard());
                    players[i].getPlayer().addCard(dealerDeck.removeCard());
                    players[i].setCardOne();
                    players[i].setCardTwo();
                }
		
		/*Test to make sure names are being added to the opponents ArrayList
		
		for (Player x: opponents){
			System.out.println(x.getName());
		}
		*/

	}
}
