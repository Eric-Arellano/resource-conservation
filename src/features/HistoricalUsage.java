package features;


import base.ResourceUsage;

import static com.sun.tools.doclint.Entity._int;
import static com.sun.tools.doclint.Entity.or;


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
	 * Provides percent difference between user amount and historical amount. Returns in absolute value.
	 *
	 * @param usage      - current usage
	 * @param historical - value being compared to, such as historical max or historical avg
	 * @return double in decimal format (not percentage)
	 */
	public static double percentDiff(double usage, double historical) {
		double percent = (usage - historical) / historical;
		return Math.abs(percent);
	}

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
		for (int i = 0; i < input.length; i++) {
			double usage = calcUsage(rate, input[i]);
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
	// Display Historical Usage
	// ================================================================================

	public String displayHistorical() {
		String histUsageResult = "";
		for (int i = 0; i < this.count; i++) {
			histUsageResult =+ histUsage[i] + "";
		}
		return histUsageResult;
	}

	private void updateMax() {
		for (int i = maxIndex + 1; i < count; i++)
		// because array stays in chronological order, don't have to re-evaluate elements before minIndex
		{
			if (histUsage[i] > histUsage[maxIndex])
				maxIndex = i;
		}
	}

	public double getMinVal() {
		return this.histUsage[minIndex];
	}

	public double getMaxVal() {
		return this.histUsage[maxIndex];
	}

	public double getAvg() {
		return this.avg;
	}

	public double[] getUsage() {
		return this.histUsage;
	}

	public int getCount() {
		return this.count;
	}

	// ================================================================================
	// Compare historical amounts
	// ================================================================================

	public String compareHistorical(double usage,
	                                String unit,
	                                String name) {

		// calc values
		String comparisonType = decideComparisonType(usage);
		double comparisonValue = getComparisonValue(comparisonType);
		String moreOrLess = decideMoreOrLess(usage, comparisonValue);
		double absoluteDiff = calcAbsoluteDiff(usage, comparisonValue);
		double percentDiff = calcPercentDiff(usage, comparisonValue);

		// base result
		String result = "You used " + absoluteDiff + moreOrLess + unit + "than your previous " + comparisonType + "of" +
				comparisonValue + unit + "! That's" + percentDiff + moreOrLess + "than your previous" + comparisonType;

		// append additional info
		if (isLessMin(usage)){
			result += "\nKeep it up!";
		}
		else if (isGreaterMax(usage) || isGreaterAvg(usage)) {
			result += howMuchToAvg();
		}
		else {
			result += howMuchToMin();
		}
		return result;
	}

	private String decideComparisonType(double usage) {
		String result = "";
		if (isGreaterAvg(usage)) {
			result = "max";
		}
		else if (isLessMin(usage)) {
			result = "min";
		}
		else {
			result = "average";
		}
		return result;
	}

	private double getComparisonValue(String comparisonType) {
		double comparisonValue;
		if (comparisonType.equals("max")) {
			comparisonValue = getMaxVal();
		}
		if (comparisonType.equals("min")) {
			comparisonValue = getMinVal();
		}
		else {
			comparisonValue = getAvg();
		}
		return comparisonValue;
	}

	private String decideMoreOrLess(double usage, double comparisonValue) {
		String result = usage > comparisonValue ? "more" : "less";
		return result;
	}

	private double calcAbsoluteDiff(double usage, double comparisonValue) {
		return Math.abs(usage - comparisonValue);
	}

	private double calcPercentDiff(double usage, double comparisonValue) {
		return Math.abs((usage - comparisonValue) / comparisonValue);
	}

	private String howMuchToAvg() {
		String result = "You would need to use the " + name + calcInputChange(absoluteDiff) + " fewer " + unit + "to " +
				"get to your average usage.";
		return result;
	}

	private String howMuchToMin(String name) {
		String result = "You would need to use the " + name + calcInputChange(absoluteDiff) + " fewer " + unit + "to beat your lowest record.";
		return result;
	}

	private boolean isGreaterAvg(double usage) {
		return usage > avg;
	}

	private boolean isGreaterMax(double usage) {
		return usage > histUsage[maxIndex];
	}

	private boolean isLessMin(double usage) {
		return usage < histUsage[minIndex];
	}
}
