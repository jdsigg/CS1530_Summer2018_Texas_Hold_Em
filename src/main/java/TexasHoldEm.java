public class TexasHoldEm
{
	public static void main(String[] args)
	{
		
<<<<<<< Updated upstream
=======
		ArrayList<String> names = new ArrayList<>(Arrays.asList("Jacques Strap", 
		"Seymour Butz", "Mike Rotch", "Ollie Tabooger", "Al Coholic", 
		"Oliver Clothesoff", "Libby Doe", "Sarah Nader", "Robin Banks", "Hugh Jeffencock"));
		
		//Get needed info from GUI class to set up the gameboard
		int numOfOpponents = table.getNumberOfComputers;
		String humanName = new String(table.getHumanName);
		
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
		
		/*Test to make sure names are being added to the opponents ArrayList
		
		for (Player x: opponents){
			System.out.println(x.getName());
		}
		*/
		
		
		
>>>>>>> Stashed changes
	}
}