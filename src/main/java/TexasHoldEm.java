public class TexasHoldEm
{
	public static void main(String[] args)
	{
		//Initialize the gameboard and deck
		GameBoard table = new GameBoard();
		//Deck dealerDeck = new Deck();
		
		//Get needed info from GUI class to set up the gameboard
		int numOfOpponents = table.getNumberOfComputers;
		String humanName = table.getHumanName;
		
		//Initialize an ArrayList with the number of opponents
		ArrayList<AiPlayer> opponents = new ArrayList<>(numOfOpponents);
	}
}
