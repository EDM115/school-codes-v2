package LoadBalancer;
import LoadBalancer.Algorithms.*;
import LoadBalancer.LoadBalancer.NetInfo;

/**
 * Algorithms
 * 
 * Enum class listing and dealing with all possible algorithms 
 */
public enum AlgorithmTypes
{
	/**
	 * Load Balancing algorithms
	 * 
	 * TODO: Add algorithm references here
	 */
	NONE(0, "None", "No server selection, always send to first server", new NoAlgorithm());

	// ***************
	// Static Methods
	// ***************

	/**
	 * GetAlgorithm
	 * 
	 * Static function which returns the Enum value which corresponds to the
	 * numeric code provided
	 * 
	 * @param	code	int value to find
	 * @return			the Enum value found, else null
	 */
	public static AlgorithmTypes getAlgorithm(int code)
	{
		// Foreach algorithm, return if the code is the same
		for(final AlgorithmTypes algo : AlgorithmTypes.values())
			if(algo.code == code)
				return algo;

		// Else return null, algorithm not recognised
		return null;
	}

	/**
	 * PrintAlgorithms
	 * 
	 * Static function which returns a string representation of all algorithms,
	 * with their numeric code, name and description.
	 * 
	 * @return	String of algorithm information
	 */
	public static String printAlgorithms()
	{
		// Parse all values and print the name and code
		String algos = "";
		for (final AlgorithmTypes algo : AlgorithmTypes.values())
			algos += "\t\t" + algo.code + " - " + algo.name + ": " + algo.description + "\n";
		return algos;
	}

	// ***************
	// Class Variables
	// ***************
	
	/**
	 * Code
	 *
	 * Int representation of the Enum algorithm code
	 */
	private final int code;
	
	/**
	 * Name
	 *
	 * String representation of the Emum algorithm name
	 */
	private final String name;
	
	/**
	 * Description
	 *
	 * String representation of the Enum algorithm description
	 */
	private final String description;

	/**
	 * Impl
	 * 
	 * Instance of the algorithm to run
	 */
	private final BalancingAlgorithm impl;
	
	// ***************
	// Class Methods
	// ***************
	
	/**
	 * Algorithms
	 *
	 * Enum constructor, initialises the Enum values with the respective
	 * parameters
	 *
	 * @param	code			The int code
	 * @param	name			The string name associated with the code
	 * @param	description		The string description
	 * @param	impl			Instance of the algorithm's code
	 */
	private AlgorithmTypes(int code, String name, String description, BalancingAlgorithm impl)
	{
		this.code = code;
		this.name = name;
		this.description = description;
		this.impl = impl;
	}
	
	/**
	 * ToString
	 *
	 * Redefines {@code toString()} method to print the Enum value in the
	 * following format :
	 *			"*name*"
	*/
	@Override
	public String toString()
	{
		return name;
	}

	/**
	 * Run
	 * 
	 * Executes the implementation's server selection algorithm
	 * 
	 * @return	The selected server
	 */
	public NetInfo run()
	{
		return impl.run();
	}

	/**
	 * Init
	 * 
	 * Initialise the algorithm with a reference to the load balancer
	 * 
	 * @param	balancer	Reference to the balancer itself
	 */
	public void init(LoadBalancer balancer)
	{
		impl.init(balancer);
	}

	/**
	 * Callback
	 * 
	 * Callback function when a reply is returning to the client
	 * 
	 * 
	 * @param	nb	The server number who sent the reply
	 */
	public void callback(int nb)
	{
		impl.callback(nb);
	}
}