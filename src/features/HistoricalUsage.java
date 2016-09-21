package features;

import java.util.ArrayList;

public class HistoricalUsage {

	private ArrayList<Double> histUsage;
	private int count = 0;
	private double avg = 0;
	private int minIndex = 0;
	private int maxIndex = 0;

	public HistoricalUsage() {
		histUsage = new ArrayList<>();
	}


	// ================================================================================
	// Public Interface
	// ================================================================================

	public String displayHistorical() {
		String histUsageResult = "";
		for (Double element : histUsage) {
			histUsageResult += element.toString();
		}
		return histUsageResult;
	}

	public String compareHistorical(double usage, String usageUnit, String name, double rate) {
		ComparisonHelper comparer = new ComparisonHelper(usage, getAvg(), getMinVal(), getMaxVal(),
				usageUnit, name, rate);
		String comparison = comparer.compareHistorical();
		updateValues();
		return comparison;
	}


	// ================================================================================
	// Add data to Array
	// ================================================================================

	public void addHistorical(double usage) {
		histUsage.add(usage);
		count++;
		// if enough values, update min/max/avg
		if (count <= 2)
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


	// ================================================================================
	// Update values
	// ================================================================================

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
		for (Double element : histUsage)
			sum += element;
		this.avg = sum / count;
	}

	private void updateMin() {
		for (int index = minIndex + 1; index < count; index++) {
			// because array stays in chronological order, don't have to re-evaluate elements before minIndex
			if (histUsage.get(index) < histUsage.get(minIndex))
				minIndex = index;
		}
	}

	private void updateMax() {
		for (int index = maxIndex + 1; index < count; index++) {
			// because array stays in chronological order, don't have to re-evaluate elements before minIndex
			if (histUsage.get(index) > histUsage.get(maxIndex))
				maxIndex = index;
		}
	}


	// ================================================================================
	// Internal Accessor Methods
	// ================================================================================

	private double getMinVal() {
		return this.histUsage.get(maxIndex);
	}

	private double getMaxVal() {
		return this.histUsage.get(maxIndex);
	}

	private double getAvg() {
		return this.avg;
	}

}
