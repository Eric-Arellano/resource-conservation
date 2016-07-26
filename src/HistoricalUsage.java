
public class HistoricalUsage 
{
	// ================================================================================
	// Instance variables
	// ================================================================================
	private double[] histUsage; // = new double[10];
	private int count = 0;
	private double avg = 0;
	private int minIndex = 0;
	private int maxIndex = 0;
	
	// ================================================================================
	// Constructor
	// ================================================================================
	public HistoricalUsage(int size)
	{
		histUsage = new double[size];
	}
	
	// ================================================================================
	// Methods to add to Array
	// ================================================================================
	/**
	 * This method adds a usage to the instance array histUsage. If the array is filled, it
	 * 	removes the first element and shifts all elements one to the left.
	 * @param usage
	 */
	public void addHistorical(double usage)
	{
		if (count < histUsage.length)
		{
			histUsage[count] = usage;
			count++;
		}
		
		else // remove first element and shift all elements one to the left
		{
			for (int i = 1; i < histUsage.length; i++)
				histUsage[i - 1] = histUsage[i];
			histUsage[histUsage.length - 1] = usage;
		}
		
		if (count <= 2) // to prevent comparing with empty instance variables
				// <= 2 because you need at least 2 values to have a max and min
			updateValues();
	}
	
	/**
	 * This method allows the programmer to fill the array with previous values without any user input
	 * @param rate - should be same rate as in constructor of object ResourceUsage
	 * @param input - variable argument, can supply as many as wanted
	 */
	public void preFill(double rate, double... input) // uses VarArg
	{
		for (int i = 0; i < input.length; i++)
		{
			double usage = calcUsage(rate, input[i]);
			addHistorical(usage);
		}
		
		updateValues();
	}
	
	/**
	 * This private method is used for the sake of preFill, so that the programmer can input with
	 * 		the more accessible input units (e.g. minutes) than the usage units (e.g. gallons)
	 * @param rate - should be same rate as in constructor of object ResourceUsage
	 * @param inputAmt - should match the unit used in rate; e.g. if gpm, input in minutes
	 * @return - usage amount
	 */
	private double calcUsage(double rate, double inputAmt)
	{
		return inputAmt * rate;
	}
	
	/**
	 * This method updates the average, min, and max instance variables. It should be called after
	 * 	ResourceUsage.compareHistUsage() is called so that the array shows updated values.
	 */
	public void updateValues()
	{
		updateAvg();
		updateMin();
		updateMax();
	}
	
	private void updateAvg()
	{
		double sum = 0;
		for (int i = 0; i < count; i++)
			sum += histUsage[i];
		avg = sum / count;
	}
	
	private void updateMin()
	{
		for (int i = minIndex + 1; i < count; i++) 
			// because array stays in chronological order, don't have to re-evaluate elements before minIndex
		{
			if (histUsage[i] < histUsage[minIndex])
				minIndex = i;
		}
	}
	
	private void updateMax()
	{
		for (int i = maxIndex + 1; i < count; i++) 
			// because array stays in chronological order, don't have to re-evaluate elements before minIndex
		{
			if (histUsage[i] > histUsage[maxIndex])
				maxIndex = i;
		}
	}
	
	// ================================================================================
	// Accesor methods
	// ================================================================================
	
	public double getMinVal() { return this.histUsage[minIndex]; }
	public double getMaxVal() { return this.histUsage[maxIndex]; }
	public double getAvg() { return this.avg; }
	public double[] getUsage() { return this.histUsage; }
	public int getCount() { return this.count; }

	// ================================================================================
	// Methods to evaluate comparisons
	// ================================================================================
	/**
	 * Compares usage amount with historical average 
	 * @param usage 
	 * @return true if usage is greater than average, false if lower than average
	 */
	public boolean isGreaterAvg(double usage)
	{
		if (usage > avg) { return true; }
		else { return false; }
	}
	
	/**
	 * Compares usage amount with historical max
	 * @param usage 
	 * @return true if usage is greater than max, false if lower than min
	 */
	public boolean isGreaterMax(double usage)
	{
		if (usage > histUsage[maxIndex]) { return true; }
		else { return false; }
	}
	
	/**
	 * Compares usage amount with historical min
	 * @param usage 
	 * @return true if usage is less than min, false if greater than min
	 */
	public boolean isLessMin(double usage)
	{
		if (usage < histUsage[minIndex]) { return true; }
		else { return false; }
	}
	
	/**
	 * Provides percent difference between user amount and historical amount. Returns in absolute value.
	 * @param usage - current usage
	 * @param historical - value being compared to, such as historical max or historical avg
	 * @return double in decimal format (not percentage)
	 */
	public static double percentDiff(double usage, double historical)
	{
		double percent = (usage - historical) / historical;
		return Math.abs(percent);
	}
}
