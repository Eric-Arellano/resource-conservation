package features;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringJoiner;

final public class HistoricalUsage {

	private final ArrayList<Double> historicalUsages;
	private final ComparisonHelper comparisonHelper;

	private final double rate;
	private final String usageUnit;

	private final DecimalFormat decimals = new DecimalFormat("0.##");

	public HistoricalUsage(String resourceName,
	                       double rate_UsagePerInput,
	                       String inputUnit,
	                       String usageUnit) {
		historicalUsages = new ArrayList<>();
		this.rate = rate_UsagePerInput;
		this.usageUnit = usageUnit;
		this.comparisonHelper = new ComparisonHelper(resourceName,
				rate_UsagePerInput,
				inputUnit,
				usageUnit);
	}

	// allows pre-existing inputs
	public HistoricalUsage(String resourceName,
	                       double rate_UsagePerInput,
	                       String inputUnit,
	                       String usageUnit,
	                       double... preExistingInputAmounts) {
		this(resourceName, rate_UsagePerInput, inputUnit, usageUnit);
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
	// Get Aggregate Values
	// ================================================================================

	public double getMinVal() {
		return Collections.min(historicalUsages);
	}

	public double getMaxVal() {
		return Collections.max(historicalUsages);
	}

	public double getAverage() {
		return historicalUsages
				.stream()
				.mapToDouble(Double::doubleValue)
				.average()
				.getAsDouble();
	}

}
