package LoadBalancer.Algorithms;
import LoadBalancer.LoadBalancer.NetInfo;

/**
 * NoAlgorithm
 * 
 * Basic balancing algorithm where no choice is made and the first listed server
 * is returned every time.
 * 
 * This class is used as a tester and example for the Load Balancer algorithms
 */
public class NoAlgorithm extends BalancingAlgorithm
{
	/**
	 * NoAlgorithm
	 * 
	 * Constructor, nothing needed
	 */
	public NoAlgorithm()
	{}

	/**
	 * Run
	 * 
	 * Runs the algorithm. Here, return the first server listed in the balancer
	 * 
	 * @return	The selected server information 
	 */
	public NetInfo run()
	{
		return balancer.getServer(0);
	}
}
