package features;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringJoiner;

public class HistoricalUsage {

	private final ArrayList<Double> histUsage;
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
	                       String usageUnit,
	                       String inputUnit) {
		histUsage = new ArrayList<>();
		this.rate = rate;
		this.usageUnit = usageUnit;
		this.comparisonHelper = new ComparisonHelper(resourceName, rate, usageUnit, inputUnit);
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
		this.rate = rate;
		this.usageUnit = usageUnit;
		this.comparisonHelper = new ComparisonHelper(resourceName, rate, usageUnit, inputUnit);
		preFillData(preExistingInputAmounts);

	}

	private void preFillData(double... input) {
		for (double inputAmount : input) {
			double usageAmount = inputAmount * rate;
			addHistorical(usageAmount);
		}
	}


	// ================================================================================
	// Public Interface
	// ================================================================================

	public void addHistorical(double usageAmt) {
		histUsage.add(usageAmt);
	}

	public String displayHistorical() {
		StringJoiner result = new StringJoiner(", ", "Historical usage:\t", "");
		for (double value : histUsage) {
			final String usageAmount = decimals.format(value);
			result.add(usageAmount + " " + usageUnit);
		}
		return result.toString();
	}

	public String compareHistorical(double usageAmt) {
		if (histUsage.isEmpty()) {
			return "Oops! Looks like there's no historical data to compare to.";
		} else {
			return comparisonHelper.compareHistorical(usageAmt, getAvg(), getMinVal(), getMaxVal());
		}
	}


	// ================================================================================
	// Accessor Methods
	// ================================================================================

	public double getMinVal() {
		return Collections.min(histUsage);
	}

	public double getMaxVal() {
		return Collections.max(histUsage);
	}

	public double getAvg() {
		return histUsage
				.stream()
				.mapToDouble(Double::doubleValue)
				.average()
				.getAsDouble();
	}

}
