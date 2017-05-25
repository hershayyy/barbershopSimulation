/**
*  Joshua Yurche
*  CS2
*  5/7/2015
*
* The BarbershopSimulation class uses the Averager class, the Barber class,
*    the ArrivalRate class, and the ArrayQueue class.  BarbershopSimulation
*    takes user input and uses probablility to simulate the following things
*    in a Barber shops work day:
*    -Number of customers serviced
*    -Average customer wait time
*    -The longest wait time a customer experiences
*    -How many customers are still waiting to be serviced by the time the 
*    Barber shop closes.
**/

import java.lang.*;
import java.util.*;

public class BarbershopSimulation
{
	public static Scanner input = new Scanner(System.in);
	
	//main Method
	/** Explain to the user what this program does and asks the user to input
	*   specific values which will be used to run the simulation.  Activate
	*   barbershopSimulation method.
	**/
	public static void main(String[] args)
	{
		System.out.println();
		System.out.println();
		System.out.println("\n**********  Welcome to the Barber Shop Simulation  **********");
		System.out.println("*************************************************************");
		System.out.println("\nThis is a simulation that will use probability to calculate\n"
				+ "the number of customers served, the average waiting time,\n"
				+ "the largest waiting time, and how many customers will still\n"
				+ "be waiting at the end of the day, based on your inputs.\n");
		System.out.println("*************************************************************");
		System.out.println("\nEnter the amount of time (in hours between 0-24) your Barber Shop will be open.");
		
		Double operationTime = new Double(input.nextDouble());
		
		while (operationTime < 0.0 || operationTime >= 24.0)
		{
			System.out.println("\nYou have made an invalid entry.  Please re-enter the amount"
					+ " of time (in hours between 0-24) your Barber Shop will be open.\n");
			operationTime = new Double(input.nextDouble());
		}
		Double timeInMinutes = operationTime * 60;
		int totalTime = timeInMinutes.intValue();
		
		
		//System.out.println("\nYou entered " + operationTime + ", or " + totalTime + " minutes");
		
		System.out.println("\nEnter the number of Barbers that will be working.\n");
		
		int barberAmount = input.nextInt();
		while (barberAmount < 0)
		{
			System.out.println("\nYou have made an invalid entry.  Please re-enter\n"
					+ " the number of Barbers that will be working.\n");
			barberAmount = input.nextInt();
		}
		
		//System.out.println("\nYou have entered " + barberAmount + " barbers.");
		
		System.out.println("\nEnter the probability that a new customer will enter your Barber Shop \n"
				+ "each minute.  Enter a value between 0 and 1.  For example: if the probability \n"
				+ "that a new customer will enter your shop each minute is 5%, enter .05\n");
		double arrivalProb = input.nextDouble();
		while (arrivalProb < 0 || arrivalProb >= 1)
		{
			System.out.println("\nYou have made an invalid entry.  Please enter a probability value between"
					+ " 0 and 1");
			arrivalProb = input.nextDouble();
		}
		
		System.out.println("\nEnter the minimum amount of time it will take to service a customer\n"
				 + " in whole minutes please.");
		int minServiceTime = input.nextInt();
		while(minServiceTime <= 0)
		{
			System.out.println("\nYou have made an invalid entry. The minimum service time must be \n"
					+ "greater than 0.  Re-enter the minimum amount of time it will take to \n"
					+ "service a customer");
		}	
		
		System.out.println("\nEnter the maximum amount of time it will take to service a customer \n"
				+ " in whole minutes please");
			
		int maxServiceTime = input.nextInt();
		while(maxServiceTime < minServiceTime)
		{
			System.out.println("\nYou have made an invalid entry.  The maximum amount of \n"
					+ "time it will take the barber to service the customer must be \n"
					+ "greater than the minimum amount of servicing time.");
			maxServiceTime = input.nextInt();
		}	
				
		System.out.println("\n********************   Run simulation   ********************");
		System.out.println("************************************************************");
		
		barbershopSimulation(barberAmount, minServiceTime, maxServiceTime, arrivalProb, totalTime);			
		
	}

	//barbershopSimulation Method
	/** Runs simulation
	*  @param barberAmount the amount of Barbers entered as working during the simulated work day.
	*  @param minServiceTime the minimum time it takes for a barber to service a customer.
	*  @param maxServiceTime the maximum time it takes for a barber to service a customer.
	*  @param arrivalProb the probability that a new client will enter the barbershop each simulated minute.
	*  @param totalTime the total time in minutes to be simulated
	*  @return a summary of the simulation information.
	**/
	
	
	public static void barbershopSimulation(int barberAmount, int minServiceTime, int maxServiceTime, double arrivalProb, int totalTime)
	{
		ArrayQueue<Integer> arrivalTimes = new ArrayQueue<Integer>();
		ArrayQueue<Integer> longestWait = new ArrayQueue<Integer>();
		int next;
		ArrivalRate arrival = new ArrivalRate(arrivalProb);
		Barber barber = new Barber(minServiceTime, maxServiceTime, barberAmount);
		Averager waitTimes = new Averager();
		int currentMinute;
		int count = 1;
		int longestWaitTime = 0;
		int theLongestWaitTime;
		
		System.out.print("\nProbability of customer arrival during a minute: ");
		System.out.println(arrivalProb * 100.00 + "%");
		System.out.println("\nTotal simulation minutes; " + totalTime);
		System.out.println("\nMinimum service time : " + minServiceTime);
		System.out.println("\nMaximum service time : " + maxServiceTime);
		
		for (currentMinute = 0; currentMinute < totalTime; currentMinute++)
		{
			//Simulate the passage of one minute of time.
			// check whether a new customer has arrived.
			if (arrival.query())
				arrivalTimes.add(currentMinute);
			
			// Check whether we can start servicing a new customer.
			if ((!barber.isBusy()) && (!arrivalTimes.isEmpty()))
			{	
				next = arrivalTimes.remove();
				int theWait = currentMinute - next;
				
				//find the longest wait time a customer experienced
				if (theWait > longestWaitTime)
				{
					longestWaitTime = theWait;
				}
				//add the longest wait time to the front of an array queue
				//to be accessed in the simulation summary.
				if (longestWait.isEmpty())
				{
					longestWait.add(longestWaitTime);
				}
				else
				{
					longestWait.remove();
					longestWait.add(longestWaitTime);
				}
				
				waitTimes.addNumber(currentMinute - next);
				barber.startServicing();
			}
			
			// Subtract one minute from the remaining service time of each busy barber.
			barber.reduceRemainingTime();
		}
		
		// Write the summary information about the simulation.
		System.out.println("\nCustomers serviced " + waitTimes.howManyNumbers());
		if (waitTimes.howManyNumbers() > 0)
			System.out.println("\nAverage wait: " + waitTimes.average() + " minutes");
		
		theLongestWaitTime = longestWait.remove();
		System.out.println("\nThe longest wait time is " + theLongestWaitTime + " minutes.");
		
		
		System.out.println("\nThe number of customers still waiting to be serviced at \n"
				+ "the time of closing is " + arrivalTimes.size());
	}
	
	
}
