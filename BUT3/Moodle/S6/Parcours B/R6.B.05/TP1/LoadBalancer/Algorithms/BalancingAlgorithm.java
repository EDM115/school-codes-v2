package LoadBalancer.Algorithms;

import LoadBalancer.LoadBalancer;
import LoadBalancer.LoadBalancer.NetInfo;

/**
 * BalancingAlgorithm
 * 
 * Interface of all load balancing algorithms. All implementations must extend
 * this class to be used in the code.
 */
public abstract class BalancingAlgorithm
{
	/**
	 * Balancer
	 * 
	 * Reference to the LoadBalancer itself, for access to server information
	 */
	protected LoadBalancer balancer;

	/**
	 * BalancingAlgorithm
	 * 
	 * Constructor for abstact class, nothing needed
	 */
	public BalancingAlgorithm()
	{}

	/**
	 * Init
	 * 
	 * Initialiser function called by the load balancer to pass a reference of
	 * itself to all algorithms.
	 * Can be overriden, although must be called by the overider
	 * 
	 * @param	balancer	Reference to the load balancer
	 */
	public void init(LoadBalancer balancer)
	{
		this.balancer = balancer;
	}

	/**
	 * Callback
	 * 
	 * Callback function called by the load balancer worker when a reply from a
	 * server has been received
	 * Can be overriden
	 * 
	 * @param	id	The server ID
	 */
	public void callback(int id)
	{
		System.out.println("> [BalancingAlgorithm] Received reply from server " + id);
	}

	/**
	 * Run
	 * 
	 * Main method called by the load balancer to get server choice
	 * 
	 * @return	The selected server information
	 */
	public abstract NetInfo run();
}
