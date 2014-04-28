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
	
	private JLabel electionIDLabel;
	private JTextField electionID;
	private JTextArea checkInfo;
	private JButton register;
	
	private String ServerResponse;
	private String[] fields;

	
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
		checkInfo = new JTextArea();
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
		} //show register panel and buttons			//works
		else if(e.getActionCommand()=="Vote"){			//Vote Radio Button Clicked
			errorLabel.setVisible(true);
			electionIDLabel.setVisible(false);
			electionID.setVisible(false);
			checkInfo.setVisible(false);
			register.setVisible(false);
		}//show voter panel and buttons 
		else if(e.getActionCommand()=="Upload"){			//Upload Radio Button Clicked
			errorLabel.setVisible(true);
			electionIDLabel.setVisible(false);
			electionID.setVisible(false);
			checkInfo.setVisible(false);
			register.setVisible(false);
		}//show upload panel and buttons
		else if(e.getActionCommand()=="Info"){			//Info Radio Button Clicked
			errorLabel.setVisible(false);
			welcomeLabel.setVisible(true);
			electionIDLabel.setVisible(false);
			electionID.setVisible(false);
			checkInfo.setVisible(false);
			register.setVisible(false);
		} //show info panel and buttons
		
		else if(e.getActionCommand()=="registernow"){		//Register JButton Clicked

			File SystemID = new File("SystemID.txt");		//can start filename with . to hide in unix environments (will make it hard to clean up though)
			String writtensystemid = "";
      		try{ 
      			SystemID.createNewFile();
				BufferedReader br = new BufferedReader(new FileReader("SystemID.txt")); 
				String content = br.readLine(); 
				br.close();
				
				//Getting SystemID into variable writtensystemid   
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
	  					Random rand = new Random();
	  					//Getting a unique System ID	The server writes it to the table if it is unique
						do{
    						potentialsystemid = (long)(rand.nextDouble()*(4294967295L));
			  				serverCommand = "3, "+potentialsystemid+", "+contactinfo;
	
			  				outToServer.writeBytes(serverCommand + '\n');
						  	ServerResponse = inFromServer.readLine();
						}while(!ServerResponse.equals("0"));
		  			
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

			try{
			  	BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));
  				Socket clientSocket = new Socket(SERVERIP, SERVERPORT);		
  				DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
	  			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	  

			  	String serverCommand = "2, "+fields[0]+", "+writtensystemid;
	
			  	outToServer.writeBytes(serverCommand + '\n');

				//Recieving Account Details
			  	ServerResponse = inFromServer.readLine();
		  			
		 		inFromUser.close();
		 		outToServer.close();
		 		inFromServer.close();
				clientSocket.close();
			}catch( IOException a){checkInfo.setText("Can't connect to database. \nCheck that your internet connection is up.");}



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
				
				ArrayList<String> RegisteredElections = this.returnWordNumberArray("Elections.txt",1);
				Iterator iterator = RegisteredElections.iterator();
				boolean registered = false;
				while(iterator.hasNext()){
				if(iterator.next().equals(fields[0])) 
					registered = true;
				}
			
				if(!registered) 
					output.append(ServerResponse+"\n");
				
				output.flush();
				output.close();
//				Elections.setWritable(false);
				JOptionPane.showMessageDialog(null,"Registered.");
			}catch(IOException h){
				h.printStackTrace();
			}
			infoButton.doClick();

			} //saves election info and adds system id(creates system id) to election table		//partially works
		
		else if(e.getActionCommand()=="Lookup"){			//ElectionID JTextField Entered
//connect to database and save info to variables      For security, info shouldn't be displayed yet if access code is needed, so that should be handled here
			if(electionID.getText()!="" && !electionID.getText().isEmpty() ){
			
				//Variables 
	  			String serverCommand;
	  			//String ServerResponse;
	  			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date datenow = new Date();
  
				//Setting up connection  
				try{
		  			BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));
		  			Socket clientSocket = new Socket(SERVERIP, SERVERPORT);	
		  			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		  			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	  

		  			serverCommand = "1, "+electionID.getText();
				
					electionID.setText("");

		  			outToServer.writeBytes(serverCommand + '\n');

				//Recieving Account Details
		  			ServerResponse = inFromServer.readLine();
		  			fields = ServerResponse.split("[;]+");		//
		  			checkInfo.setText(fields[2]);
		  			
		  			boolean electionopen = false;
		  			try{   if(	datenow.compareTo(dateFormat.parse(fields[4])) < 0	){  electionopen = true; System.out.println("Election is open");} else {  electionopen = false; System.out.println("Election is closed"); } }catch(ParseException g){g.printStackTrace();}

		  			if(!electionopen){   
		  				checkInfo.setText("Sorry, election has timed out.");
		  			}else if(!fields[2].equals("null")){
		  				String input =  JOptionPane.showInputDialog(this ,"An access code must be provided to view poll details:");
						if(input.equals(fields[2])){
							checkInfo.setText("Election Name: \n  "+fields[1]+ "\n\nElection Description: \n  "+fields[3] + "\n\nPoll Close Time: \n  "+fields[4] + "\n\nCandidates: ");
							for(int i=0; i<fields.length-6;i++)
								checkInfo.setText(checkInfo.getText()+"\n   "+fields[i+6]);	
							register.setVisible(true);
						}
						else{
							checkInfo.setText("Sorry, you did not enter the correct access code. Perhaps your capitalization was incorrect.");
						}
		  			}else{	
						checkInfo.setText("Election Name: "+fields[1]+ "\n\nElection Description: "+fields[3] + "\n\nPoll Close Time: "+fields[4] + "\n\nCandidates: ");
						for(int i=0; i<fields.length-6;i++)
							checkInfo.setText(checkInfo.getText()+"\n   "+fields[i+6]);	
						register.setVisible(true);
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

		VoterGUI labelFrame = new VoterGUI();
		labelFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		labelFrame.setSize(750, 500);
		labelFrame.setLocationRelativeTo(null);
		labelFrame.setVisible(true);
		
	}	
	
	
}
	
