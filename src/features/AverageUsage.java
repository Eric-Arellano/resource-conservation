package features;

public class AverageUsage 
{
	// ================================================================================
	// Instance Variable
	// ================================================================================
	private double avg; // should be in usage unit, e.g. gallons

	// ================================================================================
	// Constructor
	// ================================================================================
	public AverageUsage(double avg) { this.avg = avg; }
	
	public AverageUsage(double rate, double inputAmt)
	{
		this.avg = calcAvg(rate, inputAmt);
	}
	
	// ================================================================================
	// Mutator method
	// ================================================================================
	/**
	 * This method calculates a usage amount based off of the supplied rate and input amount.
	 * @param rate
	 * @param inputAmt
	 * @return double - usage amount
	 */
	private double calcAvg(double rate, double inputAmt)
	{
		return inputAmt * rate;
	}
	
	public void setAvg(double avg) { this.avg = avg; }
	
	// ================================================================================
	// Unique methods
	// ================================================================================
	/**
	 * Compares usage amount with average amount
	 * @param usage 
	 * @return true if usage is greater than average, false if lower than average
	 */
	public boolean isGreater(double usage)
	{
		if (usage > avg) { return true; }
		else { return false; }
	}
	
	/**
	 * Provides percent difference between user amount and average amount. Returns in absolute value.
	 * @param usage
	 * @return double in decimal format (not percentage)
	 */
	public double percentDiff(double usage)
	{
		double percent = (usage - avg) / avg;
		return Math.abs(percent);
	}
	
	public double getAvg() { return this.avg; }
	

}
