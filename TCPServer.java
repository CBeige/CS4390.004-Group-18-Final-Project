import java.io.*; 
import java.net.*;
import java.util.ArrayList;

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

class TCPThread extends Thread {
		protected Socket socket;

		public TCPThread(Socket clientSocket) {
			this.socket = clientSocket;
		}

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
			while(true) {
				try {
					lineIn = inputBuff.readLine();
					if ((lineIn == null) || lineIn.equals("STOP")) {
						socket.close();
						return;
					} else {
						out.writeBytes(calculate(lineIn) + '\n');
						out.flush();
					}
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
			}
		}
	}
