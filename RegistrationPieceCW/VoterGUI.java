import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class VoterGUI extends JFrame implements ActionListener{

/*	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
*/
//Create Components	
	
	private JLabel welcomeLabel;
	private JLabel errorLabel;
	private JRadioButton infoButton;
	private JRadioButton registerButton;
	private JRadioButton voteButton;
	private JRadioButton uploadButton;
	
	private JLabel electionIDLabel;
	private JTextField electionID;
	private JTextField checkInfo;
	private JButton register;

	
	public VoterGUI(){
		super("Register Prototype Window");
		//setLayout(new GroupLayout(panel));                           //Probably will edit
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		
//Initialize and Group Radio Buttons		
		{
		infoButton = new JRadioButton("Info");
		infoButton.setMnemonic(KeyEvent.VK_I);
		infoButton.setActionCommand("Info");
		infoButton.setSelected(true);

		registerButton = new JRadioButton("Register");
		registerButton.setMnemonic(KeyEvent.VK_R);
		registerButton.setActionCommand("Register");
		
		voteButton = new JRadioButton("Vote");
		voteButton.setMnemonic(KeyEvent.VK_V);
		voteButton.setActionCommand("Vote");

		uploadButton = new JRadioButton("Upload");
		uploadButton.setMnemonic(KeyEvent.VK_U);
		uploadButton.setActionCommand("Upload");
		
		ButtonGroup mainbuttons = new ButtonGroup();
		mainbuttons.add(infoButton);
		mainbuttons.add(registerButton);
		mainbuttons.add(voteButton);
		mainbuttons.add(uploadButton);
		}
		
//Set Labels
		welcomeLabel = new JLabel("Welcome to the Register Prototype");
		errorLabel = new JLabel("This is room for another part's implementation");
		electionIDLabel = new JLabel("Enter Election ID:");

//Set Buttons
		register = new JButton("Register for Election");
		register.setActionCommand("registernow");

//Set TextFields
		electionID = new JTextField("Please enter the ID of the election you wish to register for and press ENTER");
		electionID.setActionCommand("Lookup");
		checkInfo = new JTextField();
		checkInfo.setEditable(false);
		
//Create a listener for any necessary components
		{
		infoButton.addActionListener(this);
		registerButton.addActionListener(this);
		voteButton.addActionListener(this);
		uploadButton.addActionListener(this);
		
		register.addActionListener(this);
		
		electionID.addActionListener(this);
		}
		
//Add various components to Group Layout Scheme
		{
		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup()
				.addComponent(welcomeLabel)
				.addComponent(errorLabel)
				.addGroup(layout.createSequentialGroup()
					.addComponent(infoButton)
					.addComponent(registerButton)
					.addComponent(voteButton)
					.addComponent(uploadButton)
				)			
				.addGroup(layout.createSequentialGroup()
					.addComponent(electionIDLabel)
					.addComponent(electionID)
				)
				.addComponent(checkInfo)
				.addComponent(register)
			)
		);	
		
		layout.setVerticalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup()
				.addComponent(infoButton)
				.addComponent(registerButton)
				.addComponent(voteButton)
				.addComponent(uploadButton)		
			)	
			.addComponent(welcomeLabel)
			.addComponent(errorLabel)
			.addGroup(layout.createParallelGroup()
				.addComponent(electionIDLabel)
				.addComponent(electionID)
			)
			.addComponent(checkInfo)
			.addComponent(register)
		);	
		
		layout.linkSize(SwingConstants.VERTICAL, electionID, electionIDLabel);
		}

//Initially Hide Components
	errorLabel.setVisible(false);
	electionIDLabel.setVisible(false);
	electionID.setVisible(false);
	checkInfo.setVisible(false);
	register.setVisible(false);
	
					
				
	}

//Example for layouts
/*

layout.setHorizontalGroup(layout.createSequentialGroup()
    .addComponent(label)
    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addComponent(textField)
        .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(caseCheckBox)
                .addComponent(wholeCheckBox))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(wrapCheckBox)
                .addComponent(backCheckBox))))
    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addComponent(findButton)
        .addComponent(cancelButton))
);
layout.linkSize(SwingConstants.HORIZONTAL, findButton, cancelButton);

layout.setVerticalGroup(layout.createSequentialGroup()
    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
        .addComponent(label)
        .addComponent(textField)
        .addComponent(findButton))
    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(caseCheckBox)
                .addComponent(wrapCheckBox))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(wholeCheckBox)
                .addComponent(backCheckBox)))
        .addComponent(cancelButton))
);

		*/		
				
	public void actionPerformed(ActionEvent e){
		welcomeLabel.setVisible(false);
		if(e.getActionCommand()=="Register"){				//Register Radio Button Clicked
			errorLabel.setVisible(false);
			electionIDLabel.setVisible(true);
			electionID.setVisible(true);
			electionID.setText("Please enter the ID of the election you wish to register for and press ENTER");
			electionID.requestFocus();
			electionID.selectAll();
			checkInfo.setVisible(true);
			checkInfo.setText("");
			register.setVisible(true);
		} 
		else if(e.getActionCommand()=="Vote"){			//Vote Radio Button Clicked
			errorLabel.setVisible(true);
			electionIDLabel.setVisible(false);
			electionID.setVisible(false);
			checkInfo.setVisible(false);
			register.setVisible(false);
		} 
		else if(e.getActionCommand()=="Upload"){			//Upload Radio Button Clicked
			errorLabel.setVisible(true);
			electionIDLabel.setVisible(false);
			electionID.setVisible(false);
			checkInfo.setVisible(false);
			register.setVisible(false);
		} 
		else if(e.getActionCommand()=="Info"){			//Info Radio Button Clicked
			errorLabel.setVisible(false);
			welcomeLabel.setVisible(true);
			electionIDLabel.setVisible(false);
			electionID.setVisible(false);
			checkInfo.setVisible(false);
			register.setVisible(false);
		} 
		
		else if(e.getActionCommand()=="registernow"){		//Register JButton Clicked
//Handle Possible Access Code ----EDIT: that should be handled in lookup, not here

//save info locally to apps files systems. Elections file should be updated, and a ballot file should be created
		//Pretend
		
//If successful		
			JOptionPane.showMessageDialog(null,"Registered. Continue operations or close.");
			infoButton.doClick();
//If not successful, error message

		} 
		
		else if(e.getActionCommand()=="Lookup"){			//ElectionID JTextField Entered
//connect to database and save info to variables      For security, info shouldn't be displayed yet if access code is needed, so that should be handled here
			JOptionPane.showMessageDialog(null,"Looking Up. Please Wait.");
			/*
public void connectToAndQueryDatabase(String username, String password) {

    Connection con = DriverManager.getConnection(
                         "jdbc:myDriver:myDatabase",
                         username,
                         password);

    Statement stmt = con.createStatement();
    ResultSet rs = stmt.executeQuery("SELECT a, b, c FROM Table1");

    while (rs.next()) {
        int x = rs.getInt("a");
        String s = rs.getString("b");
        float f = rs.getFloat("c");
    }
}
*/ 
//save info to variables

//display lookedup info in checkInfo box
			checkInfo.setText("DB LookUp Info Should Be Here");
			
		}
	}
	
	public static void main (String[] args) {
	
		//Create variable instances of files, NOT the files themselves	
		//.createNewFile doesn't do anything if file exists. Returns true if it created something
		File Elections = new File("filetest.txt");		//can start filename with . to hide in unix environments (will make it hard to clean up though)
      	try{ Elections.createNewFile();} catch(Exception e){e.printStackTrace();}
		Elections.setReadable(false);
		Elections.setWritable(false);
		Elections.setExecutable(false);
		//Do something to hide file

		VoterGUI labelFrame = new VoterGUI();
		labelFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		labelFrame.setSize(750, 500);
		labelFrame.setLocationRelativeTo(null);
		labelFrame.setVisible(true);
		
		
 
		String domainName = "google.com";
 
		//in mac oxs
		String command = "ping -c 3 " + domainName;
 
		//in windows
		//String command = "ping -n 3 " + domainName;
 
		String output = executeCommand(command);
 
		System.out.println(output);
		
	}	
	
	
 
		public String executeCommand(String command) {
 
			StringBuffer output = new StringBuffer();
 
			Process p;
			try {
				p = Runtime.getRuntime().exec(command);
				p.waitFor();
				BufferedReader reader = 
	                            new BufferedReader(new InputStreamReader(p.getInputStream()));
 
                  	      String line = "";			
				while ((line = reader.readLine())!= null) {
					output.append(line + "\n");
				}
 
			} catch (Exception e) {
				e.printStackTrace();
			}
 
			return output.toString();
		}
 

}
	


/*			
		label1 = new JLabel("Label with text");
		label1.setToolTipText("This is label1");
		add(label1);
		
		Icon bug = new ImageIcon("bug1.png");
		label2 = new JLabel("Label with text and icon", bug, SwingConstants.LEFT);
		label2.setToolTipText("This is label2");
		add(label2);
		
		label3 = new JLabel();
		label3.setText("Label with icon and text at bottom");
		label3.setIcon(bug);
		label3.setHorizontalTextPosition(SwingConstants.CENTER);
		label3.setVerticalTextPosition(SwingConstants.BOTTOM);
		label3.setToolTipText("This is label3");
		add(label3);
*/