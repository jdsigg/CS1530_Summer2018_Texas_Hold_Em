import javax.swing.JDialog;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JFrame;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JComboBox;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Checkbox;

class InitialPlayerDialog extends JDialog
{
	private JTextField nameField;
	private JComboBox<Integer> opponents;
	private TexasHoldEm caller;
	private Checkbox timerModeCheckBox;
	private Checkbox hecklingModeCheckBox;
	
	public InitialPlayerDialog(TexasHoldEm caller, String title)
	{
		super(new JDialog(), title);
		this.caller = caller;
		
		setSize(340, 200);
		setLayout(new FlowLayout());
		Container pane = this.getContentPane();
		
		JLabel nameLabel = new JLabel("Player name:");
		nameField = new JTextField();
		nameField.setPreferredSize(new Dimension(150, 30));
		
		pane.add(nameLabel);
		pane.add(nameField);
		
		JLabel opponentsLabel = new JLabel("Choose number of opponents:");
		
		Integer[] opponentsArray = new Integer[7];
		for(int i = 0; i < opponentsArray.length; i++)
			opponentsArray[i] = new Integer(i+1);
		
		opponents = new JComboBox(opponentsArray);
		opponents.setPreferredSize(new Dimension(90, 30));
		
		pane.add(opponentsLabel);
		pane.add(opponents);
		
		///////
		
		timerModeCheckBox = new Checkbox("Timer Mode");
		hecklingModeCheckBox = new Checkbox("Heckling Mode");
		
		pane.add(timerModeCheckBox);
		pane.add(hecklingModeCheckBox);
		
		///////
		
		JButton startButton = new JButton("Start");
		JButton cancelButton = new JButton("Cancel");
		
		startButton.addActionListener(e -> startButtonActionPerformed());
		cancelButton.addActionListener(e -> cancelButtonActionPerformed());
		
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosed(WindowEvent e)
			{
				System.exit(0);
			}
		});
		
		pane.add(startButton);
		pane.add(cancelButton);
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	private void startButtonActionPerformed()
	{
		//Grab number of opponents
		Integer numberOfOpponents = (Integer)opponents.getSelectedItem();
		int opponentNumber = numberOfOpponents.intValue();
		
		//Grab players name
		String playerName = nameField.getText();
		
		this.setVisible(false);
		caller.gameSetup(playerName, opponentNumber, timerModeCheckBox.getState(), hecklingModeCheckBox.getState());		
	}
	
	private void cancelButtonActionPerformed()
	{
		System.exit(0);
	}
}