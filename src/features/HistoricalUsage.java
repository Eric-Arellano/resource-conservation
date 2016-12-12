package features;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringJoiner;

public class HistoricalUsage {

	private final ArrayList<Double> historicalUsages;
	private final ComparisonHelper comparisonHelper;

	private final double rate;
	private final String usageUnit;

	private final DecimalFormat decimals = new DecimalFormat("0.##");

	// ================================================================================
	// Constructors
	// ================================================================================

	/**
	 * Constructor with no prior historical usage.
	 */
	public HistoricalUsage(String resourceName,
	                       double rate,
	                       String inputUnit,
	                       String usageUnit) {
		historicalUsages = new ArrayList<>();
		this.rate = rate;
		this.usageUnit = usageUnit;
		this.comparisonHelper = new ComparisonHelper(resourceName, rate, inputUnit, usageUnit);
	}

	/**
	 * Constructor with variable prior historical amounts (in input units).
	 */
	public HistoricalUsage(String resourceName,
	                       double rate,
	                       String inputUnit,
	                       String usageUnit,
	                       double... preExistingInputAmounts) {
		historicalUsages = new ArrayList<>();
		this.rate = rate;
		this.usageUnit = usageUnit;
		this.comparisonHelper = new ComparisonHelper(resourceName, rate, inputUnit, usageUnit);
		preFillData(preExistingInputAmounts);

	}

	private void preFillData(double... inputAmounts) {
		for (double input : inputAmounts) {
			double usageAmount = input * rate;
			addHistorical(usageAmount);
		}
	}


	// ================================================================================
	// Public Interface
	// ================================================================================

	public void addHistorical(double usageAmount) {
		historicalUsages.add(usageAmount);
	}

	public String displayHistorical() {
		StringJoiner result = new StringJoiner(", ", "Historical usage:\t", "");
		for (double value : historicalUsages) {
			final String usageAmount = decimals.format(value);
			result.add(usageAmount + " " + usageUnit);
		}
		return result.toString();
	}

	public String compareHistorical(double usageAmount) {
		if (historicalUsages.isEmpty()) {
			return "Oops! Looks like there's no historical data to compare to.";
		} else {
			return comparisonHelper.compareHistorical(usageAmount,
					getAverage(),
					getMinVal(),
					getMaxVal());
		}
	}


	// ================================================================================
	// Accessor Methods
	// ================================================================================

	private double getMinVal() {
		return Collections.min(historicalUsages);
	}

	private double getMaxVal() {
		return Collections.max(historicalUsages);
	}

	private double getAverage() {
		return historicalUsages
				.stream()
				.mapToDouble(Double::doubleValue)
				.average()
				.getAsDouble();
	}

}
