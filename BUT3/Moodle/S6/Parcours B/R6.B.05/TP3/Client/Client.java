package Client;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Random;

public class Client
{
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
	private static final short NB_PARAMS = 4;

	/**
	 * MSG
	 * 
	 * Static variable defining the number of messages to be sent
	 * Default value of 10
	 */
	private static int MSG = 10;

	/**
	 * INTERVAL
	 * 
	 * Static variable defining the interval between transmissions, in miliseconds
	 * Default value of 1000 -> 1 second
	 */
	private static int INTERVAL = 1000;

	/**
	 * IP
	 * 
	 * Static variable defining the IP/URL of the server to contact
	 * Default value of localhost
	 */
	private static String IP = "localhost"; // server on localhost

	/**
	 * PORT
	 * 
	 * Static variable defining the port where the server is located
	 * Default value of 8080
	 */
	private static int PORT = 8080; // server on 8080 default port
	
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
	 * Runs the client. Parses the arguments and instances the client.
	 * 
	 * @param	args	the args at runtime:
	 * 					[MSG]
	 * 					[INTERVAL]
	 * 					[IP]
	 * 					[PORT]
	 */
	public static void main(String args[])
	{
		parse_params(args);

		Client client = new Client(MSG, INTERVAL, IP, PORT);

		setShutdownHook(client);
		client.start();
	}

	/**
	 * Parse_params
	 *
	 * Parses the input args and checks if the values are valid. If the arguments
	 * passed are incorrect, use the default values.
	 *
	 * @param	args		the args at runtime:
	 * 						[MSG]
	 * 						[INTERVAL]
	 * 						[IP]
	 * 						[PORT]
	 */
	private static void parse_params(String[] args)
	{
		int tmp = 0;
		if(args.length > NB_PARAMS)
			usage();
		if (args.length == 0)
			return;

		// MSG
		try
		{
			tmp = Integer.parseInt(args[0]);
			if (tmp <= 0)
				throw new NumberFormatException();
			MSG = tmp;
		}
		catch (ArrayIndexOutOfBoundsException ex)
		{
			return;
		}
		catch (NumberFormatException ex)
		{
			if(args[0].equals("help"))
			{
				usage();
			}
			System.err.println("ERROR: MSG value invalid.");
			System.err.println(" - Expected Number: " + 0 + " < MSG");
			System.err.println(" - Received " + args[0]);
		}

		// INTERVAL
		try
		{
			tmp = Integer.parseInt(args[1]);
			if (tmp <= 0)
				throw new NumberFormatException();
			INTERVAL = tmp;
		}
		catch (ArrayIndexOutOfBoundsException ex)
		{
			return;
		}
		catch (NumberFormatException ex)
		{
			System.err.println("ERROR: INTERVAL value invalid.");
			System.err.println(" - Expected Number: " + 0 + " < INTERVAL");
			System.err.println(" - Received " + args[1]);
		}

		// IP
		if (args.length == 2)
			return;

		IP = args[2];

		// PORT
		try
		{
			tmp = Integer.parseInt(args[3]);
			if (tmp < MIN_PORT || tmp > MAX_PORT)
				throw new NumberFormatException();
			PORT = tmp;
		}
		catch (ArrayIndexOutOfBoundsException ex)
		{
			return;
		}
		catch (NumberFormatException ex)
		{
			System.err.println("ERROR: PORT value invalid.");
			System.err.println(" - Expected Number: " + MIN_PORT + " <= PORT <= " + MAX_PORT);
			System.err.println(" - Received " + args[3]);
		}
	}
	
	/**
	 * SetShutdownHook
	 *
	 * Creates the shutdown hook to capture the signals sent to the JVM. Calls
	 * the {@code stop} function allowing the stop and shut the client down
	 * properly.
	 *
	 * @param	client		chat instance to call stop method.
	 */
	private static void setShutdownHook(Client client)
	{
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			@Override
			public void run()
			{
				client.stop();
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
		System.out.println("Usage: java Client [MSG] [INTERVAL] [IP] [PORT]");
		System.out.println("Usage: java -jar Client.jar [MSG] [INTERVAL] [IP] [PORT]");
		System.out.println("\t[MSG]\t\t: Number of messages to send (default 10)");
		System.out.println("\t[INTERVAL]\t: Miliseconds between message transmissions (default 1000)");
		System.out.println("\t[IP]\t\t: IP/Domain name of server (default localhost)");
		System.out.println("\t[PORT]\t\t: Port number of server (default 8080)");
		System.exit(0);
	}

	// ***************
	// Class Variables
	// ***************

	/**
	 * Running
	 *
	 * Boolean representing of the client is running, used to stop the client
	 * properly.
	 */
	private boolean running = true;

	/**
	 * Total Msg
	 * 
	 * Integer representing the total number of messages to be sent
	 */
	private int total_msg;

	/**
	 * Sent
	 * 
	 * Integer representing the number of messages sent
	 */
	private int sent;

	/**
	 * Replys
	 * 
	 * Integer representing the number of replies received
	 */
	private int replies;

	/**
	 * Dropped
	 * 
	 * Integer representing the number of dropped messages
	 */
	private int dropped;

	/**
	 * Sender
	 * 
	 * Sender thread for the client.
	 */
	private ClientSender sender;
	
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

	/**
	 * Client
	 * 
	 * Datagram socket to send and receive UDP datagrames
	 */
	private DatagramSocket client;

	// ***************
	// Class Methods
	// ***************

	/**
	 * Client
	 *
	 * Constructor of Client, sets the number of messages to be sent, the intervel
	 * between transmissions as well as the IP/Domain name and port number for
	 * the server.
	 *
	 * @param	msg			the number of messages to be sent {@literal >} 0
	 * @param	interval	the interval in seconds between subsequent message
	 * 						transmissions {@literal >} 0
	 * @param	ip			the servers IP/URL
	 * @param	port		the servers port number {@literal >}= 1024 and
	 * 						{@literal <}= 65535
	 */
	public Client(int msg, int interval, String ip, int port)
	{
		sender = new ClientSender(this, msg, interval, ip, port);

		total_msg = msg;
		sent = 0;
		replies = 0;
		dropped = 0;
		client = null;
	}

	/**
	 * ParseDatagram
	 * 
	 * Parse a received datagram to get the ID as well as if it has been dropped
	 * or not
	 * 
	 * @param	p	datagram to parse
	 */
	public void parseDatagram(DatagramPacket p)
	{
		ByteBuffer payload = ByteBuffer.wrap(p.getData());
		boolean success = payload.get() == (byte) 1 ? true : false;
		int id = payload.getInt();

		if(success)
		{
			replies ++;
			System.out.println("> Message " + id + " Received");
		}
		else
		{
			dropped ++;
			System.out.println("> Message " + id + " Dropped");
		}
	}

	/**
	 * Start
	 *
	 * Creates a new datagram socket on a random port then initialises the sending
	 * thread with the provided sender parameters. Waits and receives all incomming
	 * messages until either all messages have been received, or the client quits.
	 * Once a client shutdown has been requested, the client stops listening and
	 * accepting new datagrams on the socket and waits on the synchronised
	 * lock object for the sending thread to terminate. 
	 */
	public void start()
	{
		DatagramPacket p = new DatagramPacket(new byte[1024], 1024);
		try
		{
			// Create the client and print status message
			client = new DatagramSocket();
			System.out.println("> Starting client on port " + client.getLocalPort());
			
			// Set a timeout on the port so to not block the program
			client.setSoTimeout(TIMEOUT);

			// Start the sender thread
			sender.start();
			
			// While the client is running, wait for potentiel replys
			// The try-catch responds to the timeout, allowing the method to be
			// non blocking
			while (running && replies + dropped < total_msg)
			{
				// System.out.println("Running -> " + running + " / replies + dropped < total_msg -> " + (replies + dropped < total_msg));
				// System.out.println("Replies -> " + replies + " / dropped -> " + dropped + " / total_msg -> " + total_msg);
				try
				{
					client.receive(p);
					parseDatagram(p);
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
			if(!error && !running)
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
			else if(!error)
			{
				System.out.println("> Finished, shutting down ...");
				running = false;
				
				// Wait 500ms for the client to receive any remaining datagrams
				// then inform the sender thread to terminate and close its socket.
				try
				{
					sender.interrupt();
					sender.join();
				}
				catch (InterruptedException ex)
				{}
			}
			// Cleanup the program and close the socket, if one has been opened
			System.out.println("> Closing socket ...");
			if(client != null)
			{
				client.close();
				System.out.println("> Socket closed");
			}
			System.out.println("> " + sent + " Sent | " + replies + " Received | " + dropped + " Dropped | " + (sent - replies - dropped) + " Lost");
			synchronized(lock)
			{
					lock.notify();
			}
		}
	}

	/**
	 * Stop
	 *
	 * Begins the client shutdown. Sets the running variable to {@code false}
	 * allowing the client to be stopped. Sleeps for 500ms allowing the program
	 * time to stop accepting new datagrams before informing the sending thread
	 * of the shutdown. Once the sender has been terminated, notify the main
	 * thread it is OK to close the datagram socket. Once the socket is closed
	 * the main thread notifies this function and the client is terminted.
	 */
	public void stop()
	{
		// If a client shutdown has been requested.
		if(!error && running)
		{
			// Log the shutdown request and set running to false to stop the
			// server from receiving new datagrams
			System.out.println("\n>> Shutting down Client ...");
			running = false;
			
			// Wait 500ms for the client to receive any remaining datagrams
			// then inform the sender thread to terminate and close its socket.
			try
			{
				sender.interrupt();
				sender.join();
				Thread.sleep(500);
			}
			catch (InterruptedException ex)
			{}
			
			// When sockets are closed, notify the main thread to close datagram
			// socket and wait for confirmation.
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
		
		// Print successful client shutdown message
		System.out.println("> Client shut down");
	}

	/**
	 * Increment Counter
	 * 
	 * Synchronised function to update the sent variable from the sender thread
	 */
	public synchronized void incrementCounter()
	{
		sent ++;
	}

	public void sendDatagram(DatagramPacket p)
		throws IOException
	{
		if(client != null)
			client.send(p);
	}

	/**
	 * ClientSender
	 * 
	 * Private Client Sender class to create and send messages to the server at
	 * the requested interval
	 */
	private class ClientSender extends Thread
	{
		/**
		 * Msg
		 * 
		 * Integer representing the number of messages to send to the server
		 */
		private int msg;

		/**
		 * Interval
		 * 
		 * Integer representing the time between message transmissions in seconds
		 */
		private int interval;
		
		/**
		 * IP
		 *
		 * String representing the server IP/name.
		 */
		private String ip;
		
		/**
		 * Port
		 *
		 * Integer representing the port number on which the server is located.
		 */
		private int port;

		/**
		 * Client
		 * 
		 * Reference to the client module to update synchronised variables
		 */
		private Client client;

		/**
		 * ClientSender
		 * 
		 * Constructor for the client sender class. Initialises the number of
		 * messages to send, the interval between subsequent transmissions as
		 * well as the IP/domain name and port of the server. It also takes a
		 * reference to the Client main thread to access the available sockets.
		 * 
		 * @param	client		Reference to main client thread
		 * @param	msg			The number of messages to send
		 * @param	interval	The interval in milliseconds between transmissions
		 * @param	ip			The IP/Domain name of the server
		 * @param	port		The port number of the server
		 */
		public ClientSender(Client client, int msg, int interval, String ip, int port)
		{
			this.client = client;
			this.msg = msg;
			this.interval = interval;
			this.ip = ip;
			this.port = port;
		}

		/**
		 * Run
		 * 
		 * Thread based function to create and transmit messages to the server
		 * at specific intervals.
		 */
		public void run()
		{
			Random rand = new Random();
			int nb = 0;
			ByteBuffer data = ByteBuffer.allocate(9);
			data.put(0, (byte) 0);
			try
			{
				DatagramPacket p = new DatagramPacket(new byte[9], 9, InetAddress.getByName(ip), port);

				try
				{
					Thread.sleep(500);
				}
				catch(InterruptedException ex)
				{}

				System.out.println("> [Sender] Beginning transmission of " + msg + " messages at " + (float) interval / 1000 + "s intervals to " + p.getAddress() + ":" + p.getPort());
				while(nb < msg)
				{
					if (nb++ > 0)
						try
						{
							Thread.sleep(interval);
						}
						catch(InterruptedException ex)
						{
							break;
						}
					System.out.println("> [Sender] Sending message " + nb);
					data.putInt(1, nb);
					data.putInt(5, rand.nextInt());
					client.incrementCounter();
					p.setData(data.array());
					client.sendDatagram(p);
				}
			}
			// If the server is unknown
			catch (UnknownHostException ex)
			{
				System.err.println("! [Sender] ERROR: A server address unknown");
				System.err.println("! - " + ex.getMessage());
				error = true;
			}
			// If a UDP error occurs
			catch (SocketException ex)
			{
				System.err.println("! [Sender] ERROR: A UDP error occurred");
				System.err.println("! - " + ex.getMessage());
				error = true;
			}
			// If a general previously uncaught I/O exception occurs with a connection
			catch (IOException ex)
			{
				System.err.println("! [Sender] ERROR: An input/output error occurred with the socket");
				System.err.println("! - " + ex.getMessage());
				error = true;
			}
			finally
			{
				System.out.println("> [Sender] " + nb + " messages sent");
			}
		}
	}
}