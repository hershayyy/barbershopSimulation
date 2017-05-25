/**
*  Joshua Yurche
*  CS2
*  5/7/2015
*
*  The Barber class will be used in the BarbershopSimulation class.  This
*  class will take the user inputs from the BarbershopSimulation class, to find
*  out if there is a Barber who is available to service a customer who is new
*  or who is next in line to be serviced.
**/

import java.util.*;
public class Barber
{
	private int minutesForService; // minutes for a customer to be serviced
	private int serviceTimeLeft;  // minutes until this Barber is no longer busy with a customer
	private int barberAmount;  // how many Barbers will be working during the simulated time
	private int minServiceTime;  // minimum time (in minutes) it takes a Barber to service a customer
	private int maxServiceTime;  // maximum time (in minutes) it takes a Barber to service a customer
	Random rand = new Random();  // random number generator
	
	
	public Barber(int min, int max, int b)
	{
		minServiceTime = min;
		maxServiceTime = max;
		barberAmount = b;
		
		minutesForService = rand.nextInt(max - min + 1) + min; 
		
		serviceTimeLeft = 0;
	}
	
	//isBusy
	/**Determines if a Barber is available to service a customer
	*  @return true if serviceTimeLeft is greater than 0
	**/
	public boolean isBusy()
	{
		return (serviceTimeLeft > 0);
	}
	//reduceRemainingTime
	/**reduces the remaining service time by one minute, for each busy
	*  Barber, for each simulated minute
	**/
	public void reduceRemainingTime()
	{
		for (int i = 1; i <= barberAmount; i++)
		{
			if (serviceTimeLeft > 0)
			serviceTimeLeft --;
		}
	}
	//startServicing
	/**If there is an available Barber, the Barber will start servicing 
	*  the next customer in line.
	*  @Exception IllegalStateException throw exception to indicate that the current
	*  Barber being checked, during the current simulated minute, is busy 
	**/
	public void startServicing()
	{
		//check each Barber, until one is available this simulated minute, 
		for ( int i = 1; i <= barberAmount; i++)
		{
			if (serviceTimeLeft > 0)
			{
				throw new IllegalStateException("Barber is already busy.");
			}
			serviceTimeLeft = minutesForService;
			break;
		}
	}
}
