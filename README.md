# CS4390.004 - Group 18 – Computer Networks Project Report 
 
### A. Names and NetIDs for the group members: 
- Jacob Maxwell – jam190009 
- Nghiem Nguyen - ndn210002 
- Chris Talley - clt190005

### B. Protocol design: 
TCP: 
– Transmission Control Protocol 

Client: Sends 3 possible requests 
- Initial connection to server request 
- Requests for a simple 2 integer or double arithmetic operation
(using the string format “[operand1] [operator] [operand2]”, separated by whitespaces) 
- End connection request using “STOP” (no response required)
  
Server: Handles client request, logging each client request, and responding accordingly 
- Server adds client requests to an input buffer 
- Each request arrives as a single string 
- Math strings are parsed for calculation and result is sent as response 
- Every request is logged to a text file when a response is sent back to the client 
- Each individual client is implemented as an array of separate threads 

### C. The programming environment you used: 
Both client and server can execute through a terminal, as such, each member was free to use 	
any IDE of choice.

- Chris 
  - Development was done in VSCode, running the system on a Windows terminal.
- Nghiem
  - Development was done in Eclipse ver. 2022-06, both server and client compile and run on two separated Window terminals (cmd).
- Jacob
  - Development was done in VSCode and executing the server and client applications was done from windows command prompt terminal.

### D. How to compile and execute your programs: 
- Compilation: 
  - A makefile was created and the java files were also able to be compiled separately 	
using the “javac” command. 

- Execution:
  - From the terminal, “java TCPClient” and “java TCPServer” launch client and server 	
respectively. One terminal window per client and only one server active at a time.

### E. Parameters needed during execution (i.e., IP, port, may be name) 
Server and clients communicate using a specific socket: (IP: 127.0.0.1, port: 4321) 

### F. Good use of comments throughout your files and code 
Comments were added to any new code beyond the provided helper codes. 

### G. If your application is not complete, specify what works and what doesn't. 
The application is complete. It can differentiate requests from different originating addresses, and only uses usernames for secondary identification.
	
#### Server:  (+) Works   (-) Doesn’t Work
- (+) The server keeps track of all users \– track who, when, how long the user is attached to the server.
- (+) The server should wait for the client’s request. Upon attachment, the server should log the details about the client.
- (+) The server can have connections simultaneously with multiple clients.
- (+) The server should be able to accept the string request for basic math calculations and show who has sent what request.
- (+) The server should respond to clients in order of requests it gets from different clients. (+) The server should close the connection with the client upon request from the client and log it.
- (-) The server should tie every response to a specific username (mainly uses addresses)

#### Client:  (+) Works   (-) Doesn’t Work
- (+) The client gives name during initial attachment to the server and waits till it gets acknowledgement from the server for a successful connection.
- (+) After a successful connection, the client can send basic math calculation requests (at least 3) to the server at random times.
- (+) Another client can join during this time and start sending its requests.
- (+) The client should send a close connection request to the server and the application terminates.

### H. Challenges faced 
- Chris
  - Challenges faced during development were two-fold. At first, group communication and organization was poor. As development started our group overcame these issues using a version control system (Github). Furthermore, there were a few bugs that needed troubleshooting. Each member had to debug and fix bugs introduced in their portions of the code.
- Jacob
  - I’m not particularly experienced with Java so the entire process of learning the language, Socket and Thread libraries, and general syntax were all a challenge in and of themselves. I also began my part of development after multithreading was implemented, and I struggled with debugging the behavior of our client threads. For this reason, the program is unfortunately incomplete as mentioned above.
- Nghiem
  - It has been a while since the last time I had a project in Java. So, for this project, I struggled a bit trying to remind myself how to code in Java. Network programming does give me some trouble at first, but the real challenge in this project for me is thread programming, an area that has never been one of my strengths.


### I. What you have learned doing project 
- Chris
  - This project has provided an opportunity for increased collaborative experience and a deeper look into the function of a TCP server. Namely, serving multiple clients and buffering requests is integral to the purpose of a TCP server, and our team has taken full advantage of the chance to learn.
- Jacob
  - I re-familiarized myself with Java programming, having only written one Java program prior. I learned a lot about java sockets, threads, and I/O streams in context of both socket to socket communication and file I/O. I discovered that java threads have some unique scope issues in our specific implementation and the order of operations had to be very deliberate around the closing connection functionality.
- Nghiem
  - This project mainly serves as a learning opportunity for me to understand how to do network programming in Java. It teaches me how to establish a TCP server and client, how two hosts communicate with each other, and what lines of code are necessary for the 2 hosts to read and send information to others.

