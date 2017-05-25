/**
*  Joshua Yurche
*  CS2
*  5/7/2015
*
*  The Averager class will be used in the BarberShopSimulation by calculating two things.
*    1. The Averager will keep track of, and return the number of customers that were serviced
*       in the duration of the simulated time.
*    2. The Averager will calculate the average time that the customers had to wait , starting
*       from their arrival time, to the time they are serviced.
**/


public class Averager
{
	private int count; // How many numbers have been given to this averager
	private double sum; // the sum of all the numbers given to this averager
	
	public Averager()
	{
		count = 0;
		sum = 0;
	}
	
	public void addNumber(double value)
	{
		if (count == Integer.MAX_VALUE)
			throw new IllegalStateException("Too many numbers.");
		count++;
		sum += value;
	}
	
	public double average()
	{
		if (count == 0)
			return Double.NaN;
		else
			return sum/count;
	}
	
	public int howManyNumbers()
	{
		return count;
	}
	
	
}
