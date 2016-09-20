package features;


import features.ComparisonHelper;


public class HistoricalUsage {

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

	public HistoricalUsage(int size) {
		histUsage = new double[size];
	}


	// ================================================================================
	// Methods to add to Array
	// ================================================================================

	/**
	 * This method adds a usage to the instance array histUsage. If the array is filled, it
	 * removes the first element and shifts all elements one to the left.
	 *
	 * @param usage
	 */
	public void addHistorical(double usage) {
		if (count < histUsage.length) {
			histUsage[count] = usage;
			count++;
		} else // remove first element and shift all elements one to the left
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
	 *
	 * @param rate  - should be same rate as in constructor of object ResourceUsage
	 * @param input - variable argument, can supply as many as wanted
	 */
	public void preFill(double rate, double... input) // uses VarArg
	{
		for (double value : input) {
			double usage = calcUsage(rate, value);
			addHistorical(usage);
		}

		updateValues();
	}

	/**
	 * This private method is used for the sake of preFill, so that the programmer can input with
	 * the more accessible input units (e.g. minutes) than the usage units (e.g. gallons)
	 *
	 * @param rate     - should be same rate as in constructor of object ResourceUsage
	 * @param inputAmt - should match the unit used in rate; e.g. if gpm, input in minutes
	 * @return - usage amount
	 */
	private double calcUsage(double rate, double inputAmt) {
		return inputAmt * rate;
	}

	/**
	 * This method updates the average, min, and max instance variables. It should be called after
	 * ResourceUsage.compareHistUsage() is called so that the array shows updated values.
	 */
	private void updateValues() {
		updateAvg();
		updateMin();
		updateMax();
	}

	private void updateAvg() {
		double sum = 0;
		for (int i = 0; i < count; i++)
			sum += histUsage[i];
		avg = sum / count;
	}

	private void updateMin() {
		for (int i = minIndex + 1; i < count; i++)
		// because array stays in chronological order, don't have to re-evaluate elements before minIndex
		{
			if (histUsage[i] < histUsage[minIndex])
				minIndex = i;
		}
	}

	// ================================================================================
	// Public Interface
	// ================================================================================

	public String displayHistorical() {
		String histUsageResult = "";
		for (int i = 0; i < this.count; i++) {
			histUsageResult =+ histUsage[i] + "";
		}
		return histUsageResult;
	}

	public String compareHistorical(double usage, String unit, String name, double rate) {
		ComparisonHelper comparer = new ComparisonHelper(usage, getAvg(), getMinVal(), getMaxVal(), unit, name, rate);
		String comparison = comparer.compareHistorical();
		updateValues();
		return comparison;
	}


	// ================================================================================
	// Internal Accessor Methods
	// ================================================================================

	private void updateMax() {
		for (int i = maxIndex + 1; i < count; i++)
		// because array stays in chronological order, don't have to re-evaluate elements before minIndex
		{
			if (histUsage[i] > histUsage[maxIndex])
				maxIndex = i;
		}
	}

	private double getMinVal() {
		return this.histUsage[minIndex];
	}

	private double getMaxVal() {
		return this.histUsage[maxIndex];
	}

	private double getAvg() {
		return this.avg;
	}

	public double[] getUsage() {
		return this.histUsage;
	}

	public int getCount() {
		return this.count;
	}

}
