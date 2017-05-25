/**
*  Joshua Yurche
*  CS2
*  5/7/2015
*
*  The ArrivalRate class will be used in the BarbershopSimulation class.  It
*  will take the probability value entered by the user in the BarbershopSimulation 
*  class, and by generating random numbers between 0 and 1, it will decide if a 
*  new customer has entered the barber shop.  If the random number generator
*  generates a number that is less than the value entered by the customer
*  there is a new customer that minute
**/

public class ArrivalRate 
{
	private double probability; // the probability of query() returning true
	
	public ArrivalRate(double p)
	{
		if (( p < 0) || ( 1 < p))
			throw new IllegalArgumentException("Illegal p: " + p);
		probability = p;
	}
	
	public boolean query()
	{
		return (Math.random() < probability);
	}
}
