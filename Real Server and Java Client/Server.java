import java.io.*;
import java.net.*;
import java.sql.*;
//import java.util.*;
import java.text.*;

class Server{
   public static void main(String argv[]) throws Exception{
        String clientSentence;
        String ServerOutput;
        int port = Integer.parseInt(argv[0]);
        ServerSocket welcomeSocket = new ServerSocket(port);

	 	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        int command;

    	while(true){
	 		ServerOutput = "";
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

	        if(command == 1){//Send Info		args[1] is ElectionID searched
                try{
                	Class.forName("com.mysql.jdbc.Driver").newInstance();
                    String host = "jdbc:mysql://localhost:3306/teamq";
                    String uname = "teamq";
                    String pword = "dah4ieW1";
                    Connection con = DriverManager.getConnection(host, uname, pword); 
					Statement stmt = con.createStatement();
					
    				ResultSet rs = stmt.executeQuery("SELECT * FROM elections WHERE ElectionID = "+args[1]);

    				while (rs.next()) {
						int id =rs.getInt("ElectionID");
  	      				String name = rs.getString("ElectionName");
						String code = rs.getString("accessCodeElection");
        				String description = rs.getString("descriptionElection");
						String date = rs.getString("timeOutDate");
						String systemids = rs.getString("idSystems");
						String[] candidates = new String[8];
						int[] votes = new int[8];
						candidates[0] = rs.getString("Candidate1");
                        candidates[1] = rs.getString("Candidate2");
                        candidates[2] = rs.getString("Candidate3");
                        candidates[3] = rs.getString("Candidate4");
                        candidates[4] = rs.getString("Candidate5");
                        candidates[5] = rs.getString("Candidate6");
                        candidates[6] = rs.getString("Candidate7");
                        candidates[7] = rs.getString("Candidate8");
                        
						System.out.println(name+"\n"+description);
						ServerOutput = id+";"+name+";"+code+";"+description+";"+date+";"+systemids+";";
						int a = 0;
						//while (candidates[a] != null && a < 8){
						while (a < 8){
							ServerOutput = ServerOutput + candidates[a]+";";
							a++;
						}
    				}
                }catch(SQLException err){System.out.println(err.getMessage());}
           	}//Grab Info(works)				args[1] is ElectionID searched

	        
	        if(command == 2){//Update SystemIDs    	args[1] is ElectionID used 			args[2] is SystemID added
				int whichelection = Integer.parseInt(args[1]);
				String newid = args[2];
				
                try{
                	Class.forName("com.mysql.jdbc.Driver").newInstance();
                    String host = "jdbc:mysql://localhost:3306/teamq";
                    String uname = "teamq";
                    String pword = "dah4ieW1";
                    Connection con = DriverManager.getConnection(host, uname, pword); 
					Statement stmt = con.createStatement();
					
    				ResultSet rs = stmt.executeQuery("SELECT * FROM elections WHERE ElectionID = "+whichelection);

					String oldidstring = "";
					
    				if (rs.next()) {
    					oldidstring = rs.getString("idSystems");
    				}
    				
					String newstring = "";
    				
					if(oldidstring == null){
						newstring =newid;
					}else{
						newstring = oldidstring+"."+newid;
					}
					
					
    				stmt.executeUpdate("UPDATE elections SET idSystems = " + newstring + " WHERE ElectionID = "+whichelection);

                }catch(SQLException err){System.out.println(err.getMessage());}
           	}//Update SystemIDs(works)    	args[1] is ElectionID used 			args[2] is SystemID added
	        
	        if(command == 3){//Check for uniqueness of a new SystemID  			args[1] is SystemID to check
			  	int rowCount = -1;
                try{
                	Class.forName("com.mysql.jdbc.Driver").newInstance();
                    String host = "jdbc:mysql://localhost:3306/teamq";
                    String uname = "teamq";
                    String pword = "dah4ieW1";
                    Connection con = DriverManager.getConnection(host, uname, pword); 
					Statement stmt = con.createStatement();
					

				    ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Systems WHERE systemID ="+args[1]);
      				rs.next();
      				rowCount = rs.getInt(1);
      				
      				if(rowCount == 0){
						PreparedStatement statement = con.prepareStatement("insert into Systems (systemID, ContactInfo) values (?, ?)");
						statement.setString(1, args[1]);
						statement.setString(2, args[2]);
						statement.executeUpdate();
      				
      					//stmt.executeUpdate("INSERT INTO Systems VALUES ("+args[1]+", "+args[2]+")");getConnection();
      				}
      				
      				ServerOutput = rowCount+"";

                }catch(SQLException err){System.out.println(err.getMessage());}
           	}//Check for uniqueness of a new SystemID  			args[1] is SystemID to check
	        if(command == 4){//Send Info		args[1] is ElectionID searched
                try{
                	Class.forName("com.mysql.jdbc.Driver").newInstance();
                    String host = "jdbc:mysql://localhost:3306/teamq";
                    String uname = "teamq";
                    String pword = "dah4ieW1";
                    Connection con = DriverManager.getConnection(host, uname, pword); 
					Statement stmt = con.createStatement();
					
    				ResultSet rs = stmt.executeQuery("SELECT * FROM elections WHERE ElectionID = "+args[1]);

    				while (rs.next()) {
						int id =rs.getInt("ElectionID");
  	      				String name = rs.getString("ElectionName");
						String code = rs.getString("accessCodeElection");
        				String description = rs.getString("descriptionElection");
						String date = rs.getString("timeOutDate");
						String systemids = rs.getString("idSystems");
						String[] candidates = new String[8];
						int[] votes = new int[8];
						candidates[0] = rs.getString("Candidate1");
                        candidates[1] = rs.getString("Candidate2");
                        candidates[2] = rs.getString("Candidate3");
                        candidates[3] = rs.getString("Candidate4");
                        candidates[4] = rs.getString("Candidate5");
                        candidates[5] = rs.getString("Candidate6");
                        candidates[6] = rs.getString("Candidate7");
                        candidates[7] = rs.getString("Candidate8");
                        votes[0] = rs.getInt("votesCand1");
                        votes[1] = rs.getInt("votesCand2");
                        votes[2] = rs.getInt("votesCand3");
                        votes[3] = rs.getInt("votesCand4");
                        votes[4] = rs.getInt("votesCand5");
                        votes[5] = rs.getInt("votesCand6");
                        votes[6] = rs.getInt("votesCand7");
                        votes[7] = rs.getInt("votesCand8");
                        
						System.out.println(name+"\n"+description);
						ServerOutput = id+";"+name+";"+code+";"+description+";"+date+";"+systemids+";";
						int a = 0;
						//while (candidates[a] != null && a < 8){
						while (a < 8){
							ServerOutput = ServerOutput + candidates[a]+";";
							a++;
						}
						a = 0;
						while (a < 8){
							ServerOutput = ServerOutput + votes[a]+";";
							a++;
						}
    				}
                }catch(SQLException err){System.out.println(err.getMessage());}
           	}


//Server Output    
            outToClient.writeBytes(ServerOutput);
			outToClient.flush();
			outToClient.close();
        }
    }
}
