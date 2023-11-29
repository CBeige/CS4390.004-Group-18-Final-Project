import java.io.*;
import java.net.*;

public class TCPClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		String lineIn = null;
		String response = null;
		BufferedReader inputBuff = null;
		BufferedReader inputStream = null;
		PrintWriter outStream = null;

		//create new socket
	    Socket clientSocket = new Socket("127.0.0.1", 4321);
		try {
        	inputBuff = new BufferedReader(new InputStreamReader(System.in));
        	inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        	outStream = new PrintWriter(clientSocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("IO Error: " + e);
		}
	    //get argument from cmd line
	    System.out.println("Enter equation (Type STOP to end):");
	        
		try	{
			lineIn = inputBuff.readLine(); 
			while(lineIn.compareTo("STOP")!=0){
					outStream.println(lineIn);
					outStream.flush();
					response=inputStream.readLine();
					System.out.println("Server Response : " + response);
					lineIn=inputBuff.readLine();
				}
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Socket Read Error: " + e);
		} finally {
			inputStream.close();
			outStream.close();
			inputBuff.close();
			clientSocket.close();
			System.out.println("Connection closed");
		}
	}
}