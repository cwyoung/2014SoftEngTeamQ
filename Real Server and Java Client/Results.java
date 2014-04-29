import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;

public class Results extends JFrame implements ActionListener{
	int SERVERPORT = 1795;
	String SERVERIP = "130.184.98.10"; 
	private String ServerResponse;
	private String[] fields;
	
//Create Components
	private JRadioButton syncButton;
	private JRadioButton textButton;
	private JRadioButton tableButton;
	
	private JLabel errorLabel;
	private JLabel electionIDLabel;
	
	private JTextArea resultsInfo;
	
	private JTextField electionID;
	
	private JTable resultsTable;
	
	public Results(){
		super("Election Results");
		//setLayout(new GroupLayout(panel));
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

//Initialize and Group Radio Buttons	
		syncButton = new JRadioButton("Sync");
		syncButton.setMnemonic(KeyEvent.VK_I);
		syncButton.setActionCommand("Sync");
		syncButton.setSelected(true);
		
		textButton = new JRadioButton("Text");
		textButton.setMnemonic(KeyEvent.VK_I);
		textButton.setActionCommand("Text");
		
		tableButton = new JRadioButton("Table");
		tableButton.setMnemonic(KeyEvent.VK_R);
		tableButton.setActionCommand("Table");
		
		ButtonGroup mainbuttons = new ButtonGroup();
		mainbuttons.add(syncButton);
		mainbuttons.add(textButton);
		mainbuttons.add(tableButton);
		
//Set Labels
		errorLabel = new JLabel("");
		electionIDLabel = new JLabel("Enter Election ID:");
		
//Set TextFields
		electionID = new JTextField("Please enter the ID of the election you wish to register for and press ENTER");
		electionID.setActionCommand("Lookup");	
		resultsInfo = new JTextArea();
		resultsInfo.setEditable(false);
		
//Create a listener for any necessary components
		electionID.addActionListener(this);
		syncButton.addActionListener(this);
		textButton.addActionListener(this);
		
		
		
//Add various components to Group Layout Scheme
		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup()
				.addComponent(errorLabel)
				.addGroup(layout.createSequentialGroup()
					.addComponent(syncButton)
					.addComponent(textButton)
					.addComponent(tableButton)
				)			
				.addGroup(layout.createSequentialGroup()
					.addComponent(electionIDLabel)
					.addComponent(electionID)
				)
				.addComponent(resultsInfo)
			)
		);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup()
				.addComponent(syncButton)
				.addComponent(textButton)
				.addComponent(tableButton)	
			)	
			.addComponent(errorLabel)
			.addGroup(layout.createParallelGroup()
				.addComponent(electionIDLabel)
				.addComponent(electionID)
			)
			.addComponent(resultsInfo)
		);	
		
		layout.linkSize(SwingConstants.VERTICAL, electionID, electionIDLabel);
		
		//Initially Hide Components
		errorLabel.setVisible(false);
		electionIDLabel.setVisible(true);
		electionID.setVisible(true);
		resultsInfo.setVisible(false);
		tableButton.setVisible(false);
	}

	public void actionPerformed(ActionEvent e){
		String serverCommand;
		if(e.getActionCommand()=="Sync"){
			errorLabel.setVisible(false);
			electionIDLabel.setVisible(true);
			electionID.setVisible(true);
			resultsInfo.setVisible(false);
			resultsInfo.setText("");
			electionID.setText("Please enter the ID of the election you wish to register for and press ENTER");
		}
		
		if(e.getActionCommand()=="Text"){
			errorLabel.setVisible(false);
			electionIDLabel.setVisible(false);
			electionID.setVisible(false);
			resultsInfo.setVisible(true);
		}
		
		if(e.getActionCommand()=="Lookup"){
			if(electionID.getText()!="" && !electionID.getText().isEmpty() ){
				try{
					textButton.doClick();
		  			BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));
		  			Socket clientSocket = new Socket(SERVERIP, SERVERPORT);	
		  			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		  			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		  			
		  			serverCommand = "4, "+electionID.getText();
		  			electionID.setText("");
		  			outToServer.writeBytes(serverCommand + '\n');
		  			ServerResponse = inFromServer.readLine();
		  			
//		  			if(ServerResponse.equals("Nope")){
//						JOptionPane.showMessageDialog(null,"Either your Election ID was incorrectly entered, or it has no record associated with it.");
//						syncButton.doClick();
//		  			}
		  			System.out.println(ServerResponse);
		  			fields = ServerResponse.split("[;]+");
		  			
		  			ArrayList<String> RegisteredElections = this.returnWordNumberArray("Elections.txt",1);
					Iterator iterator = RegisteredElections.iterator();
					
					
		  			 
	  				if(!fields[2].equals("null")){
	  					String input =  JOptionPane.showInputDialog(this ,"An access code must be provided to view poll details:");
						if(input.equals(fields[2])){
							resultsInfo.setText("Election Name: \n  "+fields[1]+ "\n\nElection Description: \n  "+fields[3] + "\n\nPoll Close Time: \n  "+fields[4] + "\n\nCandidates: ");
							for(int i=0; i<fields.length-14;i++)
								resultsInfo.setText(resultsInfo.getText()+"\n   "+fields[i+6] + ": " + fields[i+14]);
						}
						else{
							resultsInfo.setText("Sorry, you did not enter the correct access code. Perhaps your capitalization was incorrect.");
						}
	  				}else{	
	  					resultsInfo.setText("Election Name: "+fields[1]+ "\n\nElection Description: "+fields[3] + "\n\nPoll Close Time: "+fields[4] + "\n\nCandidates: ");
						for(int i=0; i<fields.length-14;i++)
							resultsInfo.setText(resultsInfo.getText()+"\n   "+fields[i+6] + ": " + fields[i+14]);
						
	  				}
		  			
	 				//checkInfo.setText("FROM ACCOUNT SERVER: " + ServerResponse+"\n");
	 				inFromUser.close();
	 				outToServer.close();
	 				inFromServer.close();
					clientSocket.close();
				}catch( IOException a){resultsInfo.setText("Can't connect to database. \nCheck that your internet connection is up.");}
			}
			else{resultsInfo.setText("No ElectionID was given");}
		}
		else if(e.getActionCommand()=="Text"){
			
		}
		
		else if(e.getActionCommand()=="Table"){
			//Ran out of time to implement
		}
	}
	
	ArrayList<String> returnWordNumberArray(String filename, int word){
		
		File currentfile = new File(filename);
		try{ currentfile.createNewFile();} catch(Exception e){e.printStackTrace();}
	
		ArrayList<String> ResultArray = new ArrayList<String>();
		String[] allWords;
		String delims = "[;]+";
		String directoryline;				
		try{
			BufferedReader br = new BufferedReader(new FileReader(currentfile));
			while ((directoryline = br.readLine()) != null) {
				allWords = directoryline.split(delims);
				ResultArray.add(allWords[word-1]);
			}
			br.close();  
		} catch (IOException e) {System.out.println("File I/O error!");}
	
		return ResultArray;
	}
	

	public static void main (String[] args)  {
		//Create variable instances of files, NOT the files themselves	
		//.createNewFile doesn't do anything if file exists. Returns true if it created something
		//Do something to hide file
		Results labelFrame = new Results();
		labelFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		labelFrame.setSize(750, 600);
		labelFrame.setLocationRelativeTo(null);
		labelFrame.setVisible(true);
	}
}