import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Logger
{
    private String fileName;
    private FileWriter fw;
    private PrintWriter printWriter;

    /*
	Constructor for Logger Class
    */
    public Logger(String fileName, Player [] players)
    {
        try
        {
            File file =new File(fileName);
        	if(!file.exists())
            {
        	 	file.createNewFile();
        	}
    		this.fileName =  fileName;
            this.fw = new FileWriter(file,true);

        	BufferedWriter bw = new BufferedWriter(fw);

            this.printWriter = new PrintWriter(bw);

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    	    LocalDateTime now = LocalDateTime.now();

            this.printWriter.append("Game started - " + dtf.format(now));
            this.printWriter.append("\n");
            this.printWriter.append("Player: " + players[0].getName());
            this.printWriter.append("\n");
            this.printAIPlayers(players);
            this.printWriter.append("\n");
        }
        catch(Exception e)
        {
            System.err.println("Caught IOException: " + e.getMessage());
        }
    }

    /*
    Method that prints out AI players names
    */
    private void printAIPlayers(Player [] players)
    {
        try
        {
            this.printWriter.append("\n");
            this.printWriter.append("AI Players: ");
            this.printWriter.append("\n");
            for(int x = 1; x<players.length;x++)
            {
                this.printWriter.append(players[x].getName());
                this.printWriter.append("\n");
            }
        }
        catch(Exception e)
        {
            System.err.println("Caught IOException: " + e.getMessage());
        }
    }

    /*
	Method to log game play to text file
    Returns true if action is completed,
    false if it fails.
    */
    public boolean log(String text)
    {
        try
        {
            this.printWriter.append(text);
            this.printWriter.append("\n");
            return true;
        }catch(Exception e)
        {
            System.err.println("Caught IOException: " + e.getMessage());
            return false;
        }
        //return false;
    }

    /*
    Method that closes the file, once all game logging is finished
    Returns true if action is completed,
    false if it fails.
    */
    public boolean closeLogger()
    {
        try
        {
            this.printWriter.append("\n");
            this.printWriter.close();
            return true;
        }catch(Exception e)
        {
            System.err.println("Caught IOException: " + e.getMessage());
            return false;
        }
    }
}
