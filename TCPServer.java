import java.io.*; 
import java.net.*;
import java.util.ArrayList;
import java.time.format.*;
import java.time.LocalDateTime;

public class TCPServer {
	private static ArrayList<TCPThread> connections = new ArrayList<TCPThread>();
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = null;
		Socket socket = null;
		int numCon = 0;

		try {
			serverSocket = new ServerSocket(4321);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// accepts and tracks all client connections
		// individual client threads created here
		
		System.out.println("Waiting for connections...");
		while(true) {
			try {
				socket = serverSocket.accept();
				connections.add(new TCPThread(socket));
				connections.get(numCon).start();
				numCon++;
			} catch (IOException e) {
				System.out.println("I/O error: " + e);
			}
			new TCPThread(socket).start();
		}
	} // end main()
}

// TCPThread class handles server functionality:
// parsing requests, logging, calculation, and response to client
class TCPThread extends Thread {
		protected Socket socket;

		public TCPThread(Socket clientSocket) {
			this.socket = clientSocket;
		}

		// current date/time formatting helper method for logging
		public String dtf(){
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");  
			LocalDateTime now = LocalDateTime.now();
			return (dtf.format(now));
		}

		// file I/O logging method
		public void log(Socket socket, String msg) {
			String addr = socket.getInetAddress().toString();
			String datetime = dtf();
			String entry = msg;
			String request_type = null;

			if (msg == "STOP") {
				request_type = "User Connection Closed";
			} else if (!Character.isDigit(msg.charAt(0))) {
				request_type = "New User Connection Opened";
			} else {
				request_type = "Math Calculation";
			}

			// Log Entry Format:
			// Line 1: client address
			// Line 2: date and time
			// Line 4: client request type
			// Line 5: client input
			try {
				BufferedWriter fout = new BufferedWriter(new FileWriter("log.txt", true));
				fout.write(addr + '\n');
				fout.write(datetime + '\n');
				fout.write(request_type + '\n');
				fout.write(entry + '\n' + '\n');
				fout.close();
			} catch (IOException e){
				e.printStackTrace();
			}
		}

		// calculation method:
		//		parses a formatted input string
		//		calculates accordingly and returns result as a string
        public static String calculate(String input) {
            String[] input_part = input.split("\\s+"); //This allows for input format: "a + b"
            double result = 0;

            //check if input string has enough 2 operands and operator
            if (input_part.length !=3) {
                return "null";
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
                    return "null";
                } else {
                result = operand_1 / operand_2;
                break;
                } // end if 
            } // end switch
            
            return Double.toString(result);
            
        } // end calculate()

		// Server functionality method:
		// Logging, request handling, and responses handled here
		// Each thread encapsulates an input buffer and I/O streams
		public void run() {
			InputStream inputRaw = null;
			BufferedReader inputBuff = null;
			DataOutputStream out = null;
			try {
				inputRaw = socket.getInputStream();
				inputBuff = new BufferedReader(new InputStreamReader(inputRaw));
				out = new DataOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				return;
			}
			String lineIn;
			// log and response loop
			while(true) {
				try {
					lineIn = inputBuff.readLine();
					
					// check for "STOP" first to close connection
					if ((lineIn == null) || lineIn.equals("STOP")) {
						log(socket, "STOP");

						System.out.println("User left, logged...");
						socket.close();
						return;

					// if the input string doesn't start with a number
					// assumed that the input is a username for connection
					} else if (!Character.isDigit(lineIn.charAt(0))) {
						out.writeBytes("Welcome, " + lineIn + '\n');
						out.flush();
						System.out.println("User greeted...");

					// math calculation
					} else {
						out.writeBytes(calculate(lineIn) + '\n');
						out.flush();
						System.out.println("Math response sent...");
					}
					log(socket, lineIn);
					System.out.println("Request Logged...");
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
			}
		}
	}
