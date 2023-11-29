import java.io.*;
import java.net.*;

public class TCPClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		
			String sentence; 
	        String modified_Sentence; 
	
	        BufferedReader user_input = new BufferedReader(new InputStreamReader(System.in)); 
	
	        //create new socket
	        Socket clientSocket = new Socket("127.0.0.1", 7777); 
	
	        //create output stream (send to server)
	        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream()); 
	        
	        //create input stream (receive from server)
	        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); 
	
	        //get argument from cmd line
	        sentence = args[0];  
	        
	        // send to server
	        outToServer.writeBytes(sentence + '\n'); 
	
	        // read from server 
	        modified_Sentence = inFromServer.readLine(); 
	
	        System.out.println("FROM SERVER: " + modified_Sentence); 
	
	        clientSocket.close(); 
		
	}

}
