import java.io.*; 
import java.net.*; 

public class TCPServer {
	public static double calculate(String input) {
		String[] input_part = input.split("\\s+"); //This allows for input format: "a + b"
		double result = 0;
		
		//check if input string has enough 2 operands and operator 
		if (input_part.length !=3) {
			return 1;
		} // end if 
		
		//convert string to double value for calculation
		double operand_1 = Double.parseDouble(input_part[0]);
		double operand_2 = Double.parseDouble(input_part[2]);
		
		//switch condition
		switch (input_part[1]) {
		case "+":
			result = operand_1 + operand_2;
			break;
			
		case "-":
			result = operand_1 - operand_2;
			break;
			
		case "*":
			result = operand_1 * operand_2;
			break;
			
		case "/":
			if (operand_2 == 0) {
				return 1;
			} else {
			result = operand_1 / operand_2;
			break;
			} // end if 
		} // end switch
		
		return result;
		
	} // end calculate()
	
	public static void main(String[] args) throws IOException {
		 String client_Sentence; 

	     ServerSocket welcomeSocket = new ServerSocket(7777); 
	 	 System.out.println("Server is running");
	   while(true) {
	           Socket connectionSocket = welcomeSocket.accept();
	           System.out.println("Client connection accepted");
	           // create input stream (read from client)
	           BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream())); 

	           // create output stream (send to client) 
	           DataOutputStream  outToClient = new DataOutputStream(connectionSocket.getOutputStream()); 

	           //get input from client
	           client_Sentence = inFromClient.readLine(); 

	           // send input string to calculate()
	           double result = calculate(client_Sentence);
	           
	           //return a string in format: "a + b = c"
	           String result_Sentence = client_Sentence + " = " + result;

	           //send the return string to client
	           outToClient.writeBytes(result_Sentence); 
connectionSocket.close();
	   } //end true 
	} // end main()
	
	

}
