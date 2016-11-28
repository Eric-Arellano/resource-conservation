package features;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

public class HistoricalUsage {

	private ArrayList<Double> histUsage;
	private int count = 0;
	private double avg = 0;
	private int minIndex = 0;
	private int maxIndex = 0;

	String resourceName;
	double rate;
	String usageUnit;
	String inputUnit;

	private final DecimalFormat decimals = new DecimalFormat("0.##");

	/**
	 * Constructor with no prior historical usage.
	 */
	public HistoricalUsage(String resourceName,
	                       double rate,
	                       String usageUnit,
	                       String inputUnit) {
		histUsage = new ArrayList<>();
		this.resourceName = resourceName;
		this.rate = rate;
		this.usageUnit = usageUnit;
		this.inputUnit = inputUnit;
	}

	/**
	 * Constructor with variable prior historical amounts (in input units).
	 */
	public HistoricalUsage(String resourceName,
	                       double rate,
	                       String usageUnit,
	                       String inputUnit,
	                       double... preExistingInputAmounts) {
		histUsage = new ArrayList<>();
		this.resourceName = resourceName;
		this.rate = rate;
		this.usageUnit = usageUnit;
		this.inputUnit = inputUnit;
		preFillData(preExistingInputAmounts);

	}

	private void preFillData(double... input) {
		for (double inputAmount : input) {
			double usageAmount = inputAmount * rate;
			addHistorical(usageAmount);
		}
		updateValues();
	}

	// ================================================================================
	// Public Interface
	// ================================================================================

	public String displayHistorical(String usageUnit) {
		String histUsageResult = "Historical usage:\t";
		Iterator<Double> iterator = histUsage.iterator();
		if (iterator.hasNext()) {
			String amount = decimals.format(iterator.next());
			histUsageResult += amount + " " + usageUnit;
		}
		while (iterator.hasNext()) {
			String amount = decimals.format(iterator.next());
			histUsageResult += ", " + amount + " " + usageUnit;
		}
		return histUsageResult;
	}

	public String compareHistorical(double usageAmt) {
		ComparisonHelper comparer = new ComparisonHelper(resourceName, rate, usageUnit, inputUnit, usageAmt,
				getAvg(), getMinVal(), getMaxVal());
		String comparison = comparer.compareHistorical();
		return comparison;
	}


	// ================================================================================
	// Add data to Array
	// ================================================================================

	public void addHistorical(double usageAmt) {
		histUsage.add(usageAmt);
		count++;
		// if enough values, update min/max/avg
		if (count <= 2)
			updateValues();
	}


	// ================================================================================
	// Update values
	// ================================================================================

	/**
	 * Updates min, max, and avg values.
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
		// because array is chronological, just check new values
		for (int index = minIndex + 1; index < count; index++) {
			if (histUsage.get(index) < histUsage.get(minIndex))
				minIndex = index;
		}
	}

	private void updateMax() {
		/// because array is chronological, just check new values
		for (int index = maxIndex + 1; index < count; index++) {
			if (histUsage.get(index) > histUsage.get(maxIndex))
				maxIndex = index;
		}
	}


	// ================================================================================
	// Internal Accessor Methods
	// ================================================================================

	private double getMinVal() {
		return this.histUsage.get(minIndex);
	}

	private double getMaxVal() {
		return this.histUsage.get(maxIndex);
	}

	private double getAvg() {
		return this.avg;
	}

}
