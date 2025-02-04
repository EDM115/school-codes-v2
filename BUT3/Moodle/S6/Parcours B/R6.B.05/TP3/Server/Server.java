package Server;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;

public class Server
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
	private static final short NB_PARAMS = 3;

	/**
	 * INTERVAL
	 * 
	 * Static variable defining the interval between message replys, in miliseconds
	 * Default value of 1000 -> 1 second
	 */
	private static int INTERVAL = 1000;
	
	/**
	 * QUEUE
	 * 
	 * Static variable defining the size of the message queue. If size is 0, no
	 * messages are allowed in the queue and must be treated immediately.
	 * Default value 0
	 */
	private static int QUEUE = 0;

	/**
	 * PORT
	 * 
	 * Static variable defining the port where the server is located. If value
	 * is left as default, the next available port is selected.
	 * Default value of 8080
	 */
	private static int PORT = 8080;
	
	/**
	 * TIMEOUT
	 *
	 * Static variable defining the timeout for the receive function in miliseconds.
	 */
	private static final short TIMEOUT = 100;

	/**
	 * INTERVAL REDUCTION
	 * 
	 * Static variable defining the interval reduction for the worker thread to
	 * account of other processing time.
	 */
	private static final int INTERVAL_REDUCTION = 10;

	// ***************
	// Static Methods
	// ***************
	
	/**
	 * Main
	 * 
	 * Runs the Server. Parses the arguments and instances the server.
	 * 
	 * @param	args	the args at runtime:
	 * 					[INTERVAL]
	 * 					[QUEUE]
	 * 					[PORT]
	 */
	public static void main(String args[])
	{
		parse_params(args);

		Server server = new Server(INTERVAL, QUEUE, PORT);

		setShutdownHook(server);
		server.start();
	}

	/**
	 * Parse_params
	 *
	 * Parses the input args and checks if the values are valid. If the arguments
	 * passed are incorrect, use the default values.
	 *
	 * @param	args		the args at runtime:
	 * 						[INTERVAL]
	 * 						[QUEUE]
	 * 						[PORT]
	 */
	private static void parse_params(String[] args)
	{
		int tmp = 0;
		if(args.length > NB_PARAMS)
			usage();
		if (args.length == 0)
			return;

		// INTERVAL
		try
		{
			tmp = Integer.parseInt(args[0]);
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
			if(args[0].equals("help"))
			{
				usage();
			}
			System.err.println("ERROR: INTERVAL value invalid.");
			System.err.println(" - Expected Number: 0 < INTERVAL");
			System.err.println(" - Received " + args[0]);
		}

		// INTERVAL
		try
		{
			tmp = Integer.parseInt(args[1]);
			if (tmp <= 0)
				throw new NumberFormatException();
			QUEUE = tmp;
		}
		catch (ArrayIndexOutOfBoundsException ex)
		{
			return;
		}
		catch (NumberFormatException ex)
		{
			System.err.println("ERROR: QUEUE value invalid.");
			System.err.println(" - Expected Number: 0 < INTERVAL");
			System.err.println(" - Received " + args[1]);
		}

		// PORT
		try
		{
			tmp = Integer.parseInt(args[2]);
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
			System.err.println(" - Received " + args[2]);
		}
	}
	
	/**
	 * SetShutdownHook
	 *
	 * Creates the shutdown hook to capture the signals sent to the JVM. Calls
	 * the {@code stop} function allowing the stop and shut the client down
	 * properly.
	 *
	 * @param	server	chat instance to call stop method.
	 */
	private static void setShutdownHook(Server server)
	{
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			@Override
			public void run()
			{
				server.stop();
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
		System.out.println("Usage: java Server [INTERVAL] [QUEUE] [PORT]");
		System.out.println("Usage: java -jar Server.jar [INTERVAL] [QUEUE] [PORT]");
		System.out.println("\t[INTERVAL]\t: Miliseconds between message replies (default 1000)");
		System.out.println("\t[QUEUE]\t\t: Message queue buffer size (default 0)");
		System.out.println("\t[PORT]\t\t: Port number to run on (default 8080+)");
		System.exit(0);
	}

	// ***************
	// Class Variables
	// ***************

	/**
	 * Running
	 *
	 * Boolean representing of the server is running, used to stop the server
	 * properly.
	 */
	private boolean running = true;

	/**
	 * Worker
	 * 
	 * Worker thread for the server.
	 */
	private MessageWorker worker;

	/**
	 * Queue
	 * 
	 * Message queue containing received datagram packets
	 */
	private Queue<DatagramPacket> queue;

	/**
	 * Queue Length
	 * 
	 * Max size of the message queue, plus one corresponding to the current
	 * message being analysed
	 */
	private int queue_length;

	/**
	 * Port
	 * 
	 * Port number for the server. If the port is the default value PORT,
	 * increment until a free port is found. Otherwise, return error.
	 */
	private int port;
	
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
	 * Server
	 * 
	 * Datagram socket to send and receive UDP datagrames
	 */
	private DatagramSocket server;

	// ***************
	// Class Methods
	// ***************

	/**
	 * Server
	 *
	 * Constructor of Server, sets the duration for message analysis between
	 * replys as well as the message queue length and port number.
	 *
	 * @param	interval	the duration for message analysis {@literal >} 0
	 * @param	queue		the message queue length
	 * @param	port		the servers port number {@literal >}= 1024 and
	 * 						{@literal <}= 65535
	 */
	public Server(int interval, int queue, int port)
	{
		worker = new MessageWorker(this, interval);
		this.queue = new LinkedList<>();
		this.queue_length = queue + 1;
		this.port = port;
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
		int nb = 0;
		int accepted = 0;
		int dropped = 0;
		DatagramPacket p;
		boolean default_port = this.port == PORT;
		System.out.println("> Starting server ...");
		try
		{
			do
			{
				try
				{
					// Create the client and print status message
					server = new DatagramSocket(this.port);
					break;
				}
				catch(SocketException ex)
				{
					if(default_port)
					{
						this.port++;
						if(this.port >= MAX_PORT)
							throw ex;
						continue;
					}
					throw ex;
				}
			} while(default_port);

			System.out.println("> Server running on port " + port);
			
			// Set a timeout on the port so to not block the program
			server.setSoTimeout(TIMEOUT);

			// Start the sender thread
			worker.start();

			System.out.println("> Server ready with queue size of " + queue_length);
			
			// While the client is running, wait for potentiel replys
			// The try-catch responds to the timeout, allowing the method to be
			// non blocking
			while (running)
			{
				// Thread.yield();
				p = new DatagramPacket(new byte[1024], 1024);
				try
				{
					server.receive(p);
					System.out.print("> Message n' " + ++nb + " received (" + queue.size() + " / " + queue_length + ") - ");
					if(queue.size() < queue_length)
					{
						System.out.println("accepted");
						accepted++;
						queue.add(p);
					}
					else
					{
						System.out.println("dropped");
						dropped++;
						server.send(p);
					}
				}
				catch (SocketTimeoutException ex)
				{
					// Thread.yield();
				}
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
			// Cleanup the program and close the socket, if one has been opened
			System.out.println("> Closing socket ...");
			if(server != null)
			{
				server.close();
				System.out.println("> Socket closed");
			}
			System.out.println("> " + nb + " Received | " + accepted + " Accepted | " + dropped + " Dropped");
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
	 * shutdown then waits for thread to exit before sleeping for 500ms to allow
	 * main thread to catch up. Once the worker has been terminated, notify the
	 * main thread it is OK to close the datagram socket. Once the socket is
	 * closed the main thread notifies this function and the client is terminted.
	 */
	public void stop()
	{
		// If a client shutdown has been requested.
		if(!error)
		{
			// Log the shutdown request and set running to false to stop the
			// server from receiving new datagrams
			System.out.println("\n> Shutting down Server ...");
			running = false;
			
			// Inform the worker thread to terminate and await its death
			// then wait 500ms for the server to receive any remaining datagrams
			try
			{
				worker.interrupt();
				worker.join();
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
		
		// Print successful server shutdown message
		System.out.println("> Server shut down");
	}

	/**
	 * HasMessage
	 * 
	 * Thread-safe function to determine if there are messages available in the
	 * message queue
	 * 
	 * @return	true if there is at least one message available, false otherwise
	 */
	public synchronized boolean hasMessage()
	{
		return queue.size() > 0;
	}

	/**
	 * PeekMessage
	 * 
	 * Thread-safe function to get the first packet in the queue, without
	 * removing it from the queue itself.
	 * 
	 * @return	The packet at the head of the queue
	 */
	public synchronized DatagramPacket peekMessage()
	{
		return queue.peek();
	}

	/**
	 * GetMessage
	 * 
	 * Thread-safe function to get the first packet in the queue, and remove it
	 * at the same time.
	 * 
	 * @return	The packet at the head of the queue
	 */
	public synchronized DatagramPacket getMessage()
	{
		return queue.poll();
	}

	/**
	 * SendDatagram
	 * 
	 * Function to send a datagram packet
	 * 
	 * @param	p	Packet to send
	 * 
	 * @throws 	IOException	Exception if a network error occurs
	 */
	public void sendDatagram(DatagramPacket p)
		throws IOException
	{
		server.send(p);
	}

	/**
	 * MessageWorker
	 * 
	 * Private Message worker class to simulate work performed on a received
	 * packet over a certain time interval. Once complete, the packet is returned
	 * to the sender.
	 */
	private class MessageWorker extends Thread
	{
		/**
		 * Interval
		 * 
		 * Integer representing the time between message transmissions in seconds
		 */
		private int interval;

		/**
		 * Server
		 * 
		 * Reference to the server module to update synchronised variables
		 */
		private Server server;

		/**
		 * MessageWorker
		 * 
		 * Constructor of the Message Worker class, setting the time interval
		 * for simulating work being performed on received messages, as well as
		 * a reference to the server main thread for shared variable manipulation
		 * and data transmission
		 * 
		 * @param	server		Reference to the main server class
		 * @param	interval	Time interval for work simulation
		 */
		public MessageWorker(Server server, int interval)
		{
			this.server = server;
			this.interval = interval;
		}

		/**
		 * Run
		 * 
		 * Thread based function to simulate work being performed on received
		 * packets for a certain interval, and them reply back to the client.
		 */
		public void run()
		{
			DatagramPacket p;
			ByteBuffer payload;
			int msg_id = 0;
			boolean running = true;

			System.out.println("> [Worker] Running with work interval of " + (float) interval / 1000 + "s");
			try
			{
				while (running && !Thread.interrupted())
				{
					while(!server.hasMessage())
					{
						try
						{
							Thread.sleep(1);
						}
						catch(InterruptedException ex)
						{
							running = false;
							break;
						}
					}

					if(!running)
						break;

					p = server.peekMessage();
					payload = ByteBuffer.wrap(p.getData());
					payload.put(0, (byte) 1);
					msg_id = payload.getInt(1);
					p.setData(payload.array());
					System.out.println("> [Worker] Beginning work on message '" + msg_id + "'");
					try
					{
						Thread.sleep(interval - INTERVAL_REDUCTION);
					}
					catch(InterruptedException ex)
					{
						break;
					}
					
					System.out.println("> [Worker] Repling to message '" + msg_id + "'");
					server.getMessage();
					server.sendDatagram(p);
				}
			}
			//If the server is unknown
			catch (UnknownHostException ex)
			{
				System.err.println("! [Worker] ERROR: A server address unknown");
				System.err.println("! - " + ex.getMessage());
				error = true;
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
