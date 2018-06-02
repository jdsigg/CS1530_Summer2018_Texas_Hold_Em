import java.util.ArrayList;

public class TexasHoldEm
{
	public static void main(String[] args)
	{
		//Initialize the gameboard, deck, and dealer
		GameBoard table = new GameBoard();
		Deck dealerDeck = new Deck();
		Dealer bob = new Dealer(dealerDeck);
		
		//Get needed info from GUI class to set up the gameboard
		int numOfOpponents = table.getNumberOfComputers;
		String humanName = new String(table.getHumanName);
		
		//Initialize an ArrayList with the number of opponents
		ArrayList<Player> opponents = new ArrayList<>(numOfOpponents);
	}
}
