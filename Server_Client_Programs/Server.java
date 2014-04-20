import java.io.*;
import java.net.*;
import java.sql.*;
//import java.util.*;
import java.text.*;

class Server
{
   public static void main(String argv[]) throws Exception
      {
         String clientSentence;
         String ServerOutput;
         int port = Integer.parseInt(argv[0]);
         ServerSocket welcomeSocket = new ServerSocket(port);

         DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         //Date date;

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
                if(command == 1){//Send Info            args[1] is ElectionID searched
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
                                        while (candidates[a] != null && a < 8){
                                                ServerOutput = ServerOutput + candidates[a]+";";
                                                a++;
                                        }
                                }
                         }catch(SQLException err){System.out.println(err.getMessage());}
                }

                
                if(command == 2){//Update SystemIDs           args[1] is electionid      args[2] is systemid
                        int whichelection = Integer.parseInt(args[1]);
                        String newid = args[2];
                }
                if(command == 3){//Just Connect
                        try{
                                Class.forName("com.mysql.jdbc.Driver").newInstance();
                                String host = "jdbc:mysql://localhost:3306/teamq";
                                String uname = "teamq";
                                String pword = "dah4ieW1";
                                Connection con = DriverManager.getConnection(host, uname, pword); 
                                }catch(SQLException err){System.out.println(err.getMessage());}

//                      amount = Integer.parseInt(args[1]);
//                      if(amount < 0){balancemessage = "You cannot deposit a negative amount. No transaction will occur.";}
//                      else{balance = balance + amount;}
            }

//Server Output

            
            outToClient.writeBytes(ServerOutput);
                        outToClient.flush();
                        outToClient.close();
        }
    }
}
