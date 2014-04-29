import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;


public class VoterGUI extends JFrame implements ActionListener{

int SERVERPORT = 1795;
String SERVERIP = "130.184.98.10";
//Create Components	
	
	private JLabel welcomeLabel;
	private JLabel errorLabel;
	private JRadioButton infoButton;
	private JRadioButton registerButton;
	private JRadioButton voteButton;
	private JRadioButton uploadButton;
	private JRadioButton resultsButton;
	
	private JLabel electionIDLabel;
	private JTextField electionID;
	private JTextArea checkInfo;
	private JButton register;
	private JLabel alreadyRegisteredLabel;
	private JLabel resultsLabel;
	
	private String ServerResponse;
	private String[] fields;

//Voting Stuff
	private JLabel UAIDLabel;
	private JTextField UAID;
	private JComboBox votableElections;
	private JComboBox votableCandidates;
	private JLabel DropDown1Instructions;
	private JLabel DropDown2Instructions;
	private String electionPicked;
	
	
	public VoterGUI(){
		super("Team Q's Offline Voting System");
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
		
		resultsButton = new JRadioButton("Results");
		resultsButton.setMnemonic(KeyEvent.VK_U);
		resultsButton.setActionCommand("Results");
		
		ButtonGroup mainbuttons = new ButtonGroup();
		mainbuttons.add(infoButton);
		mainbuttons.add(registerButton);
		mainbuttons.add(voteButton);
		mainbuttons.add(uploadButton);
		mainbuttons.add(resultsButton);
		}
		
//Set Labels
		welcomeLabel = new JLabel("Welcome to the Register Prototype");
		errorLabel = new JLabel("This is room for another part's implementation");
		electionIDLabel = new JLabel("Enter Election ID:");
		alreadyRegisteredLabel = new JLabel("You are already registered for this election");
		UAIDLabel = new JLabel("Enter Your UofA ID# to begin voting: ");
		DropDown1Instructions = new JLabel("Pick the election you want to vote for from the dropdown menu: ");
		DropDown2Instructions = new JLabel("Pick the candidate you want to vote for from the dropdown menu: ");
		resultsLabel = new JLabel("Results windows launched.");

//Set Buttons
		register = new JButton("Register for Election");
		register.setActionCommand("registernow");

//Set TextFields
		electionID = new JTextField("Please enter the ID of the election you wish to register for and press ENTER");
		electionID.setActionCommand("Lookup");
		checkInfo = new JTextArea();
		checkInfo.setEditable(false);
		UAID = new JTextField();
		UAID.setActionCommand("IDEntered");
		
//Set Dropdown menu		
		ArrayList<String> list = this.returnVotableElectionsNameArray();
		votableElections = new JComboBox(list.toArray(new String[list.size()]));
		votableCandidates = new JComboBox();
		
		votableElections.setActionCommand("ElectionPicked");
		votableCandidates.setActionCommand("CandidatePicked");
			
//Create a listener for any necessary components
		{
		infoButton.addActionListener(this);
		registerButton.addActionListener(this);
		voteButton.addActionListener(this);
		uploadButton.addActionListener(this);
		resultsButton.addActionListener(this);
		
		register.addActionListener(this);
		
		electionID.addActionListener(this);
		
		UAID.addActionListener(this);
		
		votableElections.addActionListener(this);
//		votableCandidates.addActionListener(this);
		
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
					.addComponent(resultsButton)
				.addComponent(resultsLabel)
				)			
				.addGroup(layout.createSequentialGroup()
					.addComponent(electionIDLabel)
					.addComponent(electionID)
				)
				.addComponent(checkInfo)
				.addComponent(register)
				.addComponent(alreadyRegisteredLabel)
				.addComponent(DropDown1Instructions)
				.addComponent(DropDown2Instructions)
				.addGroup(layout.createSequentialGroup()
					.addComponent(UAIDLabel)
					.addComponent(UAID)
				)
				.addComponent(votableElections)
				.addComponent(votableCandidates)
			)
		);	
		
		layout.setVerticalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup()
				.addComponent(infoButton)
				.addComponent(registerButton)
				.addComponent(voteButton)
				.addComponent(uploadButton)	
				.addComponent(resultsButton)
			)	
			.addComponent(welcomeLabel)
			.addComponent(errorLabel)
			.addGroup(layout.createParallelGroup()
				.addComponent(electionIDLabel)
				.addComponent(electionID)
			.addComponent(resultsLabel)
			)
			.addComponent(checkInfo)
			.addComponent(register)
			.addComponent(alreadyRegisteredLabel)
			.addGroup(layout.createParallelGroup()
				.addComponent(UAIDLabel)
				.addComponent(UAID)
			)
			.addComponent(DropDown1Instructions)
			.addComponent(votableElections)
			.addComponent(DropDown2Instructions)
			.addComponent(votableCandidates)
		);	
		
		layout.linkSize(SwingConstants.VERTICAL, electionID, electionIDLabel, UAID, UAIDLabel);
		}

//Initially Hide Components
	errorLabel.setVisible(false);
	electionIDLabel.setVisible(false);
	electionID.setVisible(false);
	checkInfo.setVisible(false);
	register.setVisible(false);
	alreadyRegisteredLabel.setVisible(false);
	UAID.setVisible(false);
	UAIDLabel.setVisible(false);
	votableElections.setVisible(false);
	votableCandidates.setVisible(false);
	DropDown1Instructions.setVisible(false);
	DropDown2Instructions.setVisible(false);
	resultsLabel.setVisible(false);
	
					
	}
				
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
			register.setVisible(false);
			alreadyRegisteredLabel.setVisible(false);
			UAIDLabel.setVisible(false);
			UAID.setVisible(false);
			votableElections.setVisible(false);
			votableCandidates.setVisible(false);
			DropDown1Instructions.setVisible(false);
			DropDown2Instructions.setVisible(false);
			resultsLabel.setVisible(false);
		} //show register panel and buttons			//works
		else if(e.getActionCommand()=="Vote"){			//Vote Radio Button Clicked
			errorLabel.setVisible(false);
			electionIDLabel.setVisible(false);
			electionID.setVisible(false);
			checkInfo.setVisible(false);
			register.setVisible(false);
			alreadyRegisteredLabel.setVisible(false);
			UAIDLabel.setVisible(true);
			UAID.setVisible(true);
			votableElections.setVisible(false);
			votableCandidates.setVisible(false);
			DropDown1Instructions.setVisible(false);
			DropDown2Instructions.setVisible(false);
			resultsLabel.setVisible(false);
		}//show voter panel and buttons 
		else if(e.getActionCommand()=="Upload"){			//Upload Radio Button Clicked
			errorLabel.setVisible(true);
			electionIDLabel.setVisible(false);
			electionID.setVisible(false);
			checkInfo.setVisible(false);
			register.setVisible(false);
			alreadyRegisteredLabel.setVisible(false);
			UAIDLabel.setVisible(false);
			UAID.setVisible(false);
			votableElections.setVisible(false);
			votableCandidates.setVisible(false);
			DropDown1Instructions.setVisible(false);
			DropDown2Instructions.setVisible(false);
			resultsLabel.setVisible(false);
		}//show upload panel and buttons
		else if(e.getActionCommand()=="Info"){			//Info Radio Button Clicked
			errorLabel.setVisible(false);
			welcomeLabel.setVisible(true);
			electionIDLabel.setVisible(false);
			electionID.setVisible(false);
			checkInfo.setVisible(false);
			register.setVisible(false);
			alreadyRegisteredLabel.setVisible(false);
			UAIDLabel.setVisible(false);
			UAID.setVisible(false);
			votableElections.setVisible(false);
			votableCandidates.setVisible(false);
			DropDown1Instructions.setVisible(false);
			DropDown2Instructions.setVisible(false);
			resultsLabel.setVisible(false);
		} //show info panel and buttons
		
		else if(e.getActionCommand()=="Results"){			//Info Radio Button Clicked
			errorLabel.setVisible(false);
			welcomeLabel.setVisible(false);
			electionIDLabel.setVisible(false);
			electionID.setVisible(false);
			checkInfo.setVisible(false);
			register.setVisible(false);
			alreadyRegisteredLabel.setVisible(false);
			UAIDLabel.setVisible(false);
			UAID.setVisible(false);
			votableElections.setVisible(false);
			votableCandidates.setVisible(false);
			DropDown1Instructions.setVisible(false);
			DropDown2Instructions.setVisible(false);
			resultsLabel.setVisible(true);
			//Results.main(null);
		} //show info panel and buttons
		
		else if(e.getActionCommand()=="registernow"){		//Register JButton Clicked

//Getting SystemID into variable writtensystemid 
			File SystemID = new File("SystemID.txt");		//can start filename with . to hide in unix environments (will make it hard to clean up though)
			String writtensystemid = "";
      		try{ 
      			SystemID.createNewFile();
				BufferedReader br = new BufferedReader(new FileReader("SystemID.txt")); 
				String content = br.readLine(); 
				br.close();
				
  
				if (content == null) {
	  				String contactinfo = JOptionPane.showInputDialog(this ,"You are registering this system with our voting database for the first time.\n\nPlease provide some type of contact information in case you ever need a reminder to upload polling results in the future:");
					//Getting new SystemID and writing to local file
					try{
					  	BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));
  						Socket clientSocket = new Socket(SERVERIP, SERVERPORT);		
  						DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
	  					BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	  
	  					String serverCommand;
	  					long potentialsystemid;
	  					String serverResponseAboutID;
	  					Random rand = new Random();
	  					//Getting a unique System ID	The server writes it to the table if it is unique
						do{
    						potentialsystemid = (long)(rand.nextDouble()*(999999999L));
			  				serverCommand = "3, "+potentialsystemid+", "+contactinfo;
	
			  				outToServer.writeBytes(serverCommand + '\n');
						  	serverResponseAboutID = inFromServer.readLine();
						}while(!serverResponseAboutID.equals("0"));
		  			
		 				inFromUser.close();
		 				outToServer.close();
		 				inFromServer.close();
						clientSocket.close();
						
						writtensystemid = potentialsystemid + "";
					}catch( IOException a){checkInfo.setText("Can't connect to database. \nCheck that your internet connection is up.");} 
							
					BufferedWriter bw = new BufferedWriter(new FileWriter("SystemID.txt"));
					bw.write(writtensystemid);
					bw.close();
					SystemID.setReadable(true);
					SystemID.setWritable(false);
					SystemID.setExecutable(false);
				}
				else{
					writtensystemid = content;
				}
				
			
			} catch(Exception h){h.printStackTrace();}

//Petitioning the Server to Update System ID field(no response is needed)
			try{
			  	BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));
  				Socket clientSocket = new Socket(SERVERIP, SERVERPORT);		
  				DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
	  			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	  

			  	String serverCommand = "2, "+fields[0]+", "+writtensystemid;
	
			  	outToServer.writeBytes(serverCommand + '\n');

			  	//ServerResponse = inFromServer.readLine();						//Server Response isn't needed here
		  			
		 		inFromUser.close();
		 		outToServer.close();
		 		inFromServer.close();
				clientSocket.close();
			}catch( IOException a){checkInfo.setText("Can't connect to database. \nCheck that your internet connection is up.");}


//Adding info to Elections.txt
			File Elections = new File("Elections.txt");		//can start filename with . to hide in unix environments (will make it hard to clean up though)
      		try{ 
      			Elections.createNewFile();
      		} catch(Exception h){h.printStackTrace();}
			Elections.setReadable(true);
			Elections.setWritable(false);
			Elections.setExecutable(false);
			
			Writer output;
			try{
				Elections.setWritable(true);
				output = new BufferedWriter(new FileWriter(Elections,true));
				
				output.append(ServerResponse+"\n");
				
				output.flush();
				output.close();
//				Elections.setWritable(false);
				JOptionPane.showMessageDialog(null,"Registered.");
			}catch(IOException h){
				h.printStackTrace();
			}
			infoButton.doClick();

			} //saves election info and adds system id(creates system id) to election table		// works
		
		else if(e.getActionCommand()=="Lookup"){			//ElectionID JTextField Entered
//connect to database and save info to variables      For security, info shouldn't be displayed yet if access code is needed, so that should be handled here
			if(electionID.getText()!="" && !electionID.getText().isEmpty() ){
			
				//Variables 
	  			String serverCommand;
	  			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date datenow = new Date();
  
//Pulling info and Parsing 
				try{
		  			BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));
		  			Socket clientSocket = new Socket(SERVERIP, SERVERPORT);	
		  			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		  			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	  

		  			serverCommand = "1, "+electionID.getText();
				
					electionID.setText("");

		  			outToServer.writeBytes(serverCommand + '\n');

		  			ServerResponse = inFromServer.readLine();
		  			
		  			if(ServerResponse.equals("Nope")){
		  				
						JOptionPane.showMessageDialog(null,"Either your Election ID was incorrectly entered, or it has no record associated with it.");
		  				infoButton.doClick();
		  			}
		  			fields = ServerResponse.split("[;]+");		
		  			//checkInfo.setText(fields[2]);				//Displays accesscode for easier testing
		  			
//Determining boolean of election open		  			
		  			boolean electionopen = false;
		  			try{   
		  				if(	datenow.compareTo(dateFormat.parse(fields[4])) < 0	){  
		  					electionopen = true; 
		  					//System.out.println("Election is open");
		  				}else{  
		  					electionopen = false; 
		  					//System.out.println("Election is closed"); 
		  				} 
		  			}catch(ParseException g){g.printStackTrace();}

//Determining boolean of already registered
					boolean registered = false;
					
					ArrayList<String> RegisteredElections = this.returnWordNumberArray("Elections.txt",1);
					Iterator iterator = RegisteredElections.iterator();
					while(iterator.hasNext()){
						if(iterator.next().equals(fields[0])) 
							registered = true;
					}


//Setting what to display based on timeout date, access code, and current registration status
		  			if(!electionopen){   																			
		  				checkInfo.setText("Sorry, election has timed out.");
		  			}
		  			else{ 
		  				if(!fields[2].equals("null")){
		  					String input =  JOptionPane.showInputDialog(this ,"An access code must be provided to view poll details:");
							if(input.equals(fields[2])){
								checkInfo.setText("Election Name: \n  "+fields[1]+ "\n\nElection Description: \n  "+fields[3] + "\n\nPoll Close Time: \n  "+fields[4] + "\n\nCandidates: ");
								for(int i=0; i<fields.length-6;i++)
									checkInfo.setText(checkInfo.getText()+"\n   "+fields[i+6]);
								if(registered){
									alreadyRegisteredLabel.setVisible(true);
								}else{		
									register.setVisible(true);
								}
							}
							else{
								checkInfo.setText("Sorry, you did not enter the correct access code. Perhaps your capitalization was incorrect.");
							}
		  				}else{	
							checkInfo.setText("Election Name: "+fields[1]+ "\n\nElection Description: "+fields[3] + "\n\nPoll Close Time: "+fields[4] + "\n\nCandidates: ");
							for(int i=0; i<fields.length-6;i++)
								checkInfo.setText(checkInfo.getText()+"\n   "+fields[i+6]);	
							if(registered){
								alreadyRegisteredLabel.setVisible(true);		
							}else{
								register.setVisible(true);
							}
		  				}
		  			}
	 				//checkInfo.setText("FROM ACCOUNT SERVER: " + ServerResponse+"\n");
	 				inFromUser.close();
	 				outToServer.close();
	 				inFromServer.close();
					clientSocket.close();
				}catch( IOException a){checkInfo.setText("Can't connect to database. \nCheck that your internet connection is up.");}
			}
			else{checkInfo.setText("No ElectionID was given");}	
		}	//gets and shows election info (deals with access code)	//works
		
		else if(e.getActionCommand()=="IDEntered"){
			DropDown1Instructions.setVisible(true);
			votableElections.setVisible(true);
		}	
			
		else if(e.getActionCommand()=="ElectionPicked"){
			JComboBox cb = (JComboBox)e.getSource();
			String raw = (String)cb.getSelectedItem();
        	String[] ElectionIDSelected = raw.split("[ ]+");
        	
        	electionPicked = ElectionIDSelected[0];
        
        	String accesscode = this.getAccessCode(ElectionIDSelected[0]);
        	
        	if(!accesscode.equals("null")){
        		if(!accesscode.equals(JOptionPane.showInputDialog(this ,"Enter the access code needed for this election:"))){
        			JOptionPane.showMessageDialog(null,"Your access code was incorrectly entered.");
        			UAID.setText("");
        			infoButton.doClick();
        		}else{
   	     			//initialize candidates
        			ArrayList<String> Candidates = this.returnCandidatesNamesArray(ElectionIDSelected[0]);
					Iterator iterator = Candidates.iterator();
					while(iterator.hasNext()){
						votableCandidates.addItem(iterator.next());
					}
					votableCandidates.addActionListener(this);
					DropDown2Instructions.setVisible(true);
					votableCandidates.setVisible(true);
        		}
        	}else{
   	     		//initialize candidates
        		ArrayList<String> Candidates = this.returnCandidatesNamesArray(ElectionIDSelected[0]);
				Iterator iterator = Candidates.iterator();
				while(iterator.hasNext()){
					votableCandidates.addItem(iterator.next());
				}
				votableCandidates.addActionListener(this);
				DropDown2Instructions.setVisible(true);
				votableCandidates.setVisible(true);
        	}
		}
		
		else if(e.getActionCommand()=="CandidatePicked"){
			JComboBox cb = (JComboBox)e.getSource();
			String CandidateSelected = (String)cb.getSelectedItem();
			
			int confirm = JOptionPane.showConfirmDialog(this, "Do you confirm your vote for "+CandidateSelected+"?", "Candidate Vote Confirmation", JOptionPane.YES_NO_OPTION);
                   
            if (confirm == JOptionPane.YES_OPTION) {
            	File LocalPoll = new File(electionPicked+".txt");		//can start filename with . to hide in unix environments (will make it hard to clean up though)
      			try{ 
      				LocalPoll.createNewFile();
      			} catch(Exception h){h.printStackTrace();}
				LocalPoll.setReadable(true);
				LocalPoll.setWritable(false);
				LocalPoll.setExecutable(false);
			
				Writer output;
				try{
					LocalPoll.setWritable(true);
					output = new BufferedWriter(new FileWriter(LocalPoll,true));
				
					output.append(UAID.getText()+";"+CandidateSelected+";\n");
				
					output.flush();
					output.close();
//					Elections.setWritable(false);
					JOptionPane.showMessageDialog(null,"Registered.");
				}catch(IOException h){
					h.printStackTrace();
				}
				
                JOptionPane.showMessageDialog(null,"You have successfully cast your vote. Remember that voting multiple times will not help, as only one vote by any voter will count.");
        		UAID.setText("");
            	infoButton.doClick();
        	} else {
        		JOptionPane.showMessageDialog(null,"This voting session with terminate with no vote cast. Do come back to vote sometime.");
        		UAID.setText("");
            	infoButton.doClick();
            }
			
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
		
	ArrayList<String> returnVotableElectionsNameArray(){
		
			File currentfile = new File("Elections.txt");
			try{ currentfile.createNewFile();} catch(Exception e){e.printStackTrace();}
		
			ArrayList<String> ResultArray = new ArrayList<String>();
			String[] allWords;
			String delims = "[;]+";
			String directoryline;		
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date datenow = new Date();		
			
			try{
				BufferedReader br = new BufferedReader(new FileReader(currentfile));
				while ((directoryline = br.readLine()) != null) {
					allWords = directoryline.split(delims);
					if(datenow.compareTo(dateFormat.parse(allWords[4])) < 0)
					ResultArray.add(allWords[0]+" "+allWords[1]);
				}
				br.close();  
			} catch (IOException e) {System.out.println("File I/O error!");}catch (ParseException e) {System.out.println("Parse error!");}
		
			return ResultArray;
		}
	
	ArrayList<String> returnCandidatesNamesArray(String election){
		
			File currentfile = new File("Elections.txt");
			try{ currentfile.createNewFile();} catch(Exception e){e.printStackTrace();}
		
			ArrayList<String> ResultArray = new ArrayList<String>();
			String[] allWords;
			String delims = "[;]+";
			String directoryline;				
			try{
				BufferedReader br = new BufferedReader(new FileReader(currentfile));
				while ((directoryline = br.readLine()) != null) {
					allWords = directoryline.split(delims);
					if(allWords[0].equals(election)){
						for(int x=6;x<allWords.length;x++){								//may need to subtract one
							if(allWords[x] != null){
								ResultArray.add(allWords[x]);
							}
						}					
					}
				}
				br.close();  
			} catch (IOException e) {System.out.println("File I/O error!");}
		
			return ResultArray;
		}
	
		String getAccessCode(String election){
		
			File currentfile = new File("Elections.txt");
			try{ currentfile.createNewFile();} catch(Exception e){e.printStackTrace();}
		
			String Result = "";
			String[] allWords;
			String delims = "[;]+";
			String directoryline;				
			try{
				BufferedReader br = new BufferedReader(new FileReader(currentfile));
				while ((directoryline = br.readLine()) != null) {
					allWords = directoryline.split(delims);
					if(allWords[0].equals(election)){
						Result = allWords[2];
					}
				}
				br.close();  
			} catch (IOException e) {System.out.println("File I/O error!");}
		
			return Result;
		}
		
	public static void main (String[] args)  {

		VoterGUI labelFrame = new VoterGUI();
		labelFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		labelFrame.setSize(750, 500);
		labelFrame.setLocationRelativeTo(null);
		labelFrame.setVisible(true);
		
	}	
	
	
}
	
