package LoadBalancer;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadBalancer {
	// ***************
	// Static Variables
	// ***************
	
	/**
	 * MIN_PORT
	 *
	 * Static variable defining the minimum start port value accepted.
	 */
	private static final int MIN_PORT = 1024;
	
	/**
	 * MAX_PORT
	 *
	 * Static variable defining the maximum start port value accepted.
	 */
	private static final int MAX_PORT = 65535;
	
	/**
	 * NB_PARAMS
	 *
	 * Static variable defining the number of parameters expected at runtime.
	 */
	private static final short NB_PARAMS = 2;

	/**
	 * ALGO
	 * 
	 * Static variable defining the load balancing algorithm to utilise.
	 * Default value of RANDOM
	 */
	private static AlgorithmTypes ALGO = AlgorithmTypes.NONE;

	/**
	 * MAX_ALGORITHMS
	 * 
	 * Static variable defining the maximum number of algorithms available
	 */
	private static final int MAX_ALGORITHMS = AlgorithmTypes.values().length;

	/**
	 * PORT
	 * 
	 * Static variable defining the port where the load balancer is located.
	 * Default value of 9090
	 */
	private static int PORT = 9090;
	
	/**
	 * TIMEOUT
	 *
	 * Static variable defining the timeout for the receive function in miliseconds.
	 */
	private static final short TIMEOUT = 100;

	// ***************
	// Static Methods
	// ***************
	
	/**
	 * Main
	 * 
	 * Runs the Load Balancer. Parses the arguments and instances the proxy.
	 * 
	 * @param	args	the args at runtime:
	 * 					[ALGO]
	 * 					[PORT]
	 */
	public static void main(String args[])
	{
		parse_params(args);

		LoadBalancer proxy = new LoadBalancer(ALGO, PORT);

		setShutdownHook(proxy);
		proxy.start();
	}

	/**
	 * Parse_params
	 *
	 * Parses the input args and checks if the values are valid. If the arguments
	 * passed are incorrect, use the default values.
	 *
	 * @param	args		the args at runtime:
	 * 						[ALGO]
	 * 						[PORT]
	 */
	private static void parse_params(String[] args)
	{
		int tmp = 0;

		// Check argument length
		// If too long, print usage
		if(args.length > NB_PARAMS)
			usage();
		// If no args, use defaults
		if (args.length == 0)
			return;

		// Get ALGO value
		try
		{
			// Check if value ins a number and valid
			tmp = Integer.parseInt(args[0]);
			if (tmp < 0 || tmp > MAX_ALGORITHMS)
				throw new NumberFormatException();
			// Get ENUM value
			ALGO = AlgorithmTypes.getAlgorithm(tmp);
		}
		// If an error has occured, print the info and use default value
		catch (NumberFormatException ex)
		{
			if(args[0].equals("help"))
			{
				usage();
			}
			System.err.println("ERROR: ALGO value invalid.");
			System.err.println(" - Expected Number: 0 <= ALGO < " + AlgorithmTypes.values().length);
			System.err.println(" - Received " + args[0]);
		}

		// Get PORT value
		try
		{
			// Check if value is a number and valid
			tmp = Integer.parseInt(args[1]);
			if (tmp < MIN_PORT || tmp > MAX_PORT)
				throw new NumberFormatException();
			PORT = tmp;
		}
		// Saftey net if only 1 argument has been provided
		catch (ArrayIndexOutOfBoundsException ex)
		{
			return;
		}
		// If an error has occured, print the info and use default value
		catch (NumberFormatException ex)
		{
			System.err.println("ERROR: PORT value invalid.");
			System.err.println(" - Expected Number: " + MIN_PORT + " <= PORT <= " + MAX_PORT);
			System.err.println(" - Received " + args[1]);
		}
	}
	
	/**
	 * SetShutdownHook
	 *
	 * Creates the shutdown hook to capture the signals sent to the JVM. Calls
	 * the {@code stop} function allowing to stop and shut the system down
	 * properly.
	 *
	 * @param	loadbalancer	loadbalancer instance to call stop method.
	 */
	private static void setShutdownHook(LoadBalancer loadbalancer)
	{
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			@Override
			public void run()
			{
				loadbalancer.stop();
			}
		});
	}
	
	/**
	 * Usage
	 *
	 * Prints the usage information to STDOUT. Informs the users of the correct
	 * syntax and arguments.
	 */
	private static void usage ()
	{
		System.out.println("Usage: java LoadBalancer [ALGO] [PORT]");
		System.out.println("\t[ALGO]\t: Algorithm to utilise (default 0 - None)");
		System.out.print(AlgorithmTypes.printAlgorithms());
		System.out.println("\t[PORT]\t: Port number to run on (default 9090)");
		System.exit(0);
	}

	// ***************
	// Class Variables
	// ***************

	/**
	 * Running
	 *
	 * Boolean representing of the load balancer is running, used to stop the 
	 * system properly.
	 */
	private boolean running = true;

	/**
	 * Worker
	 * 
	 * Worker thread for the load balancer.
	 */
	private ReplyWorker worker;

	/**
	 * ServerSocket
	 * 
	 * Datagram socket to contact the different servers
	 */
	private DatagramSocket serverSocket;

	/**
	 * ClientSocket
	 * 
	 * Datagram socket to contact the clients
	 */
	private DatagramSocket clientSocket;

	/**
	 * ClientInfos
	 * 
	 * Map associating message IDs with their client, to allow multi-client
	 * communications
	 */
	private Map<Integer, NetInfo> clientInfos;

	/**
	 * ServerInfo
	 * 
	 * List of all servers the load balancer must deal with
	 */
	private List<NetInfo> serverInfos;

	/**
	 * Port
	 * 
	 * Port number for the load balancer.
	 */
	private int port;

	/**
	 * Algo
	 * 
	 * Algorithm to be used in the load balancer
	 */
	private AlgorithmTypes algo;
	
	/**
	 * Lock
	 *
	 * Final object for used as a multithreading lock for inter-threat
	 * synchronisation
	 */
	private final Object lock = new Object();

	/** 
	 * Error
	 * 
	 * Indicates if an error has occured
	 */
	private boolean error = false;

	// ***************
	// Class Methods
	// ***************

	/**
	 * LoadBalancer
	 *
	 * Constructor of LoadBalancer, sets the algorithm to be used as well as the
	 * port on which the load balancer is to be found.
	 *
	 * @param	algo		Enum value representing the algorithm
	 * @param	port		the load balancers port number {@literal >}= 1024 and
	 * 						{@literal <}= 65535
	 */
	public LoadBalancer(AlgorithmTypes algo, int port)
	{
		// Set class variables
		this.algo = algo;
		this.port = port;

		// Initialise worker class
		worker = new ReplyWorker(this);

		// Initialise the client and server structures
		clientInfos = new HashMap<>();
		serverInfos = new ArrayList<>();

		// Set the list of servers for this load balancer
		setServers();

		// Initialise the balancing algorithm with this load balancer instance
		algo.init(this);
	}

	/**
	 * SetServers
	 * 
	 * Function containing the list of configured IP/hostnames and ports of the
	 * servers
	 */
	public void setServers()
	{
		// TODO: Add all servers that are running here
		addServer("localhost", 8080);
		addServer("localhost", 8081);
	}

	/**
	 * addServer
	 * 
	 * Function adding a server to the list of servers. If the information is
	 * invalid, then an error is printed and the server is not added
	 * 
	 * @param	ip		IP address of the server
	 * @param	port	Port number of the server
	 */
	public void addServer(String ip, int port)
	{
		try
		{
			// Check if provided information is correct
			if(port < MIN_PORT || port > MAX_PORT)
				throw new UnknownHostException("Invalid port number");
			serverInfos.add(new NetInfo(InetAddress.getByName(ip), port));
		}
		// If info invalid, print error message
		catch(UnknownHostException ex)
		{
			System.err.println("! ERROR: The server '" + ip + ":" + port + "' is not known");
			System.err.println("! - " + ex.getMessage());
		}
	}

	/**
	 * NbServers
	 * 
	 * Return the number of servers assocaited with this load balancer
	 * 
	 * @return	The number of servers
	 */
	public int nbServers()
	{
		return serverInfos.size();
	}

	/**
	 * GetServer
	 * 
	 * Return the requested server from the list of available servers
	 * 
	 * @return	The server information
	 */
	public NetInfo getServer(int nb)
	{
		return serverInfos.get(nb);
	}

	/**
	 * ReceiveServer
	 * 
	 * Polls the server socket for a datagram packet. If none is available and
	 * the timeout expires, throws timeout exception. Otherwise, the data is
	 * put in the provided packet.
	 * 
	 * @param	p	Datagram Packet to receive information
	 * 
	 * @throws	SocketTimeoutException	Socket timeout has expired with no data
	 * @throws	IOException				Internal I/O error has occured
	 */
	public void receiveServer(DatagramPacket p)
		throws SocketTimeoutException, IOException
	{
		serverSocket.receive(p);
	}

	/**
	 * SendClient
	 * 
	 * Sends a datagram packet through the client socket.
	 * 
	 * @param	p	Datagram packet to send
	 * 
	 * @throws IOException	Internal I/O error has occured
	 */
	public void sendClient(DatagramPacket p)
		throws IOException
	{
		clientSocket.send(p);
	}

	/**
	 * GetClientInfo
	 * 
	 * Get the client information associated with the message ID. Also removes
	 * the corresponding entry from the list of clients.
	 * 
	 * @param	id	The ID of the message to send
	 * @return		The NetInfo if exists, null otherwise
	 */
	public NetInfo getClientInfo(int id)
	{
		return clientInfos.remove(id);
	}

	/**
	 * GetServerID
	 * 
	 * Return the ID of the server in the serverInfos list with the provided
	 * ip and port 
	 * 
	 * @param	ip		Server IP address
	 * @param	port	Server port number
	 * 
	 * @return			The server's ID if exists, else -1
	 */
	public int getServerID(InetAddress ip, int port)
	{
		for(int i = 0; i < nbServers(); i++)
			if(getServer(i).match(ip, port))
				return i;
		return -1;
	}

	/**
	 * Start
	 *
	 * Creates two new datagram sockets, one for the clients on the specified
	 * port and another for the servers on a random port, then initialises the
	 * worker thread to deal with replies. Waits and receives all incoming 
	 * messages until either all messages have been received, or the system quits.
	 * Once a shutdown has been requested, the load balancer stops listening and
	 * accepting new datagrams on the client socket and waits on the synchronised
	 * lock object for the sending worker thread to terminate. 
	 */
	public void start()
	{
		// Check if servers exist ...
		if(nbServers() == 0)
		{
			System.err.println("! [ERROR] No servers have been added, cannot continue");
			return;
		}

		int msgID = 0;
		DatagramPacket p;
		NetInfo serverInfo;
		System.out.println("> Starting load balancer ...");

		try
		{
			// Initialise client socket
			clientSocket = new DatagramSocket(port);
			System.out.println("> Client socket running on port " + port);

			// Initialise server socket
			serverSocket = new DatagramSocket();
			System.out.println("> Server socket running on port " + serverSocket.getLocalPort());
			
			// Set a timeout on the ports so to not block the program
			clientSocket.setSoTimeout(TIMEOUT);
			serverSocket.setSoTimeout(TIMEOUT);

			// Start the sender thread
			worker.start();

			System.out.println("> Load balancer running with algorithm - " + algo);
			
			// While the system is running, wait for incoming messages from
			// clients.
			// The try-catch responds to the timeout, allowing the method to be
			// non blocking
			while (running)
			{
				p = new DatagramPacket(new byte[1024], 1024);
				try
				{
					clientSocket.receive(p);
					// Get the message ID to identify the source client
					msgID = ByteBuffer.wrap(p.getData()).getInt(5);
					clientInfos.put(msgID, new NetInfo(p.getAddress(), p.getPort()));
					// Run the algorithm and get the server to whom send the message
					serverInfo = algo.run();
					p.setAddress(serverInfo.getIP());
					p.setPort(serverInfo.getPort());
					serverSocket.send(p);
					System.out.println("> Forwarded message to server " + serverInfo.getIP() + ":" + serverInfo.getPort());
				}
				catch (SocketTimeoutException ex)
				{}
			}
		}
		// If a UDP error occurs
		catch (SocketException ex)
		{
			System.err.println("! ERROR: A UDP error occurred");
			System.err.println("! - " + ex.getMessage());
			error = true;
		}
		// If a general previously uncaught I/O exception occurs with a connection
		catch (IOException ex)
		{
			System.err.println("! ERROR: An input/output error occurred with the socket");
			System.err.println("! - " + ex.getMessage());
			error = true;
		}
		finally
		{
			// Wait for the lock to be released if a client shutdown has been
			// requested
			// In place to close the client socket ONLY once the sender thread
			// has terminated
			if(!error)
			{
				synchronized(lock)
				{
					try
					{
						lock.wait();
					}
					catch(InterruptedException ex)
					{}
				}
			}
			// Cleanup the program and close the sockets, if they have been opened
			System.out.println("> Closing client socket ...");
			if(clientSocket != null)
			{
				clientSocket.close();
				System.out.println("> Client socket closed");
			}
			System.out.println("> Closing server socket ...");
			if(serverSocket != null)
			{
				serverSocket.close();
				System.out.println("> Server socket closed");
			}
			synchronized(lock)
			{
					lock.notify();
			}
		}
	}

	/**
	 * Stop
	 *
	 * Begins the server shutdown. Sets the running variable to {@code false}
	 * allowing the server to be stopped. Informs the worker thread of the
	 * shutdown then waits for it to exit before sleeping for 500ms to allow
	 * main thread to catch up. Once the worker has been terminated, notify the
	 * main thread it is OK to close all datagram sockets. Once the sockets are
	 * closed the main thread notifies this function and the system is terminted.
	 */
	public void stop()
	{
		// If a system shutdown has been requested.
		if(!error)
		{
			// Log the shutdown request and set running to false to stop the
			// load balancer from dealing with new datagrams
			System.out.println("\n> Shutting down Server ...");
			running = false;
			
			// Inform the worker thread to terminate and await its death then
			// wait 500ms for the server to receive any remaining datagrams
			try
			{
				worker.interrupt();
				worker.join();
				Thread.sleep(500);
			}
			catch (InterruptedException ex)
			{}
			
			// When worker is dead, notify the main thread to close datagram
			// sockets and wait for confirmation.
			synchronized(lock)
			{
				lock.notify();
				try
				{
					lock.wait();
				}
				catch(InterruptedException ex)
				{}
			}
		}
		
		// Print successful server shutdown message
		System.out.println("> Load balancer shut down");
	}

	// =========================================================================
	// Internal Class NetInfo
	// =========================================================================

	/**
	 * NetInfo
	 * 
	 * Class used to store network information of clients and servers
	 */
	public class NetInfo {
		/**
		 * IP
		 * 
		 * The IP address of the target
		 */
		private final InetAddress ip;

		/**
		 * Port
		 * 
		 * The port number of the target
		 */
		public final int port;

		/**
		 * NetInfo
		 * 
		 * Initialise and set the IP and port number
		 * 
		 * @param	ip		IP address
		 * @param	port	Port number
		 */
		public NetInfo(InetAddress ip, int port)
		{
			this.ip = ip;
			this.port = port;
		}

		/**
		 * Match
		 * 
		 * Comparaison function to check if the provided IP address and port
		 * number are the same as this entry.
		 * 
		 * @param	ip		IP address to compare
		 * @param 	port	Port number to compare
		 * 
		 * @return			True if the same, false otherwise
		 */
		public boolean match(InetAddress ip, int port)
		{
			return this.ip.equals(ip) && this.port == port;
		}

		/**
		 * getIP
		 * 
		 * Return the IP address
		 * 
		 * @return	IP address
		 */
		public InetAddress getIP()
		{
			return ip;
		}

		/**
		 * GetPort
		 * 
		 * Return the port number
		 * 
		 * @return	port number
		 */
		public int getPort()
		{
			return port;
		}
	}

	// =========================================================================
	// Internal Class ReplyWorker
	// =========================================================================

	/**
	 * ReplyWorker
	 * 
	 * Private sub-class of Load Balancer whose only goal is to deal with replys
	 * from the servers to the various clients, updating information in the
	 * load balancer if and when needed.
	 */
	private class ReplyWorker extends Thread
	{
		/**
		 * LoadBalancer
		 * 
		 * Reference to the load balancer module to update synchronised variables
		 */
		private LoadBalancer loadBalancer;

		/**
		 * ReplyWorker
		 * 
		 * Constructor of ReplyWorker, sets a reference to the load balancer
		 * 
		 * @param	loadBalancer	Reference to the load balancer itself
		 */
		public ReplyWorker(LoadBalancer loadBalancer)
		{
			this.loadBalancer = loadBalancer;
		}

		/**
		 * Run
		 * 
		 * Thread-based function for all workers to listen to the server socket
		 * and pass any and all datagrams to the client socket. Also calls an 
		 * update function in the load balancer for each packet.
		 */
		public void run()
		{
			DatagramPacket p = new DatagramPacket(new byte[1024], 1024);
			NetInfo clientInfo;
			int msgID;

			System.out.println("> [Worker] Running ...");
			try
			{
				// While the thread is alive and hasn't been interrupted, listen
				// and receive all data on the server socket and send it to the
				// corresponding client
				while (!Thread.interrupted())
				{
					try
					{
						loadBalancer.receiveServer(p);
						algo.callback(loadBalancer.getServerID(p.getAddress(), p.getPort()));
						// Get the message ID to identify the original source client
						msgID = ByteBuffer.wrap(p.getData()).getInt(5);
						clientInfo = loadBalancer.getClientInfo(msgID);
						p.setAddress(clientInfo.getIP());
						p.setPort(clientInfo.getPort());
						loadBalancer.sendClient(p);
						System.out.println("> [Worker] Forwarded message to client " + clientInfo.getIP() + ":" + clientInfo.getPort());
					}
					catch (SocketTimeoutException ex)
					{}
				}

			}
			// If a UDP error occurs
			catch (SocketException ex)
			{
				System.err.println("! [Worker] ERROR: A UDP error occurred");
				System.err.println("! - " + ex.getMessage());
				error = true;
			}
			// If a general previously uncaught I/O exception occurs with a connection
			catch (IOException ex)
			{
				System.err.println("! [Worker] ERROR: An input/output error occurred with the socket");
				System.err.println("! - " + ex.getMessage());
				error = true;
			}
			finally
			{
				System.out.println("> [Worker] Shutdown");
			}
		}
	}
}
