import java.io.*;
import java.net.*;
import java.sql.*;

class Server
{
   public static void main(String argv[]) throws Exception
      {
         String clientSentence;
         String ServerOutput;
         int port = Integer.parseInt(argv[0]);
         ServerSocket welcomeSocket = new ServerSocket(port);
         
         
         int balance = 10000;
         int command;
         int amount;
         String balancemessage = "";

    while(true){
         balancemessage = "";
         Socket connectionSocket = welcomeSocket.accept();    
              
//accepting connection and recieving string

            BufferedReader inFromClient =
               new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

            clientSentence = inFromClient.readLine();
            System.out.println("Received: " + clientSentence);
            
//parsing with string and changing to integers

            String[] args = clientSentence.trim().split("\\s*,\\s*");       //WORKS!!!!!!!!!   splits into args[0] and args[1]   spaces are not an issue
            command = Integer.parseInt(args[0]);
            
//cases and dealings with

	        if(command == 1){
	        	amount = Integer.parseInt(args[1]);
	        	if(amount > balance){balancemessage = "You do not have that much to withdraw. No transaction will occur.";}
	        	else if(amount < 0){balancemessage = "You cannot withdraw a negative amount. No transaction will occur.";}
	        	else{balance = balance - amount;}
	        }
	        if(command == 2){
	        	amount = Integer.parseInt(args[1]);
	        	if(amount < 0){balancemessage = "You cannot deposit a negative amount. No transaction will occur.";}
	        	else{balance = balance + amount;}
            }
	        if(command == 3){
	        	try{
			        Class.forName("com.mysql.jdbc.Driver").newInstance();
		        	String host = "jdbc:mysql://localhost:3306/teamq";
		        	String uname = "teamq";
		        	String pword = "dah4ieW1";
		        	Connection con = DriverManager.getConnection(host, uname, pword); 
				}catch(SQLException err){System.out.println(err.getMessage());}

//	        	amount = Integer.parseInt(args[1]);
//	        	if(amount < 0){balancemessage = "You cannot deposit a negative amount. No transaction will occur.";}
//	        	else{balance = balance + amount;}
            }

//Server Output

            ServerOutput = balancemessage + "The balance is "+ balance+"\n"; 
            outToClient.writeBytes(ServerOutput);
			outToClient.flush();

        }
    }
}
