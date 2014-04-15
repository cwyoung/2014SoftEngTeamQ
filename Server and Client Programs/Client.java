//use command java TCPCLient <hostname(ip or "localhost")> <port#>
import java.io.*;
import java.net.*;

class Client
{
 public static void main(String argv[]) throws Exception
 {
//User instructions


//Variables 
  String sentence;
  String ServerResponse;
  //int port = Integer.parseInt(argv[1]);
  
//Setting up connection  
  BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));
  Socket clientSocket = new Socket("130.184.98.10", 1795);
  DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
  BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
  
//Getting what to write 
												//while(true){

  System.out.println("\n\nFor your transaction, input choice (1 for withdrawal, 2 for deposit, 3 for account check).\n\nFor withdrawal and deposit, follow with a comma and the amount desired.\n \n  INPUT:");

  sentence = inFromUser.readLine();
  
												 // if(Integer.parseInt(sentence)==4)
												 // 	clientSocket.close();
  
  outToServer.writeBytes(sentence + '\n');

//Recieving Account Details
  		ServerResponse = inFromServer.readLine();
 		System.out.println("FROM ACCOUNT SERVER: " + ServerResponse);
												  //}
  		clientSocket.close();
 }
}