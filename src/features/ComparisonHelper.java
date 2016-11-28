package features;

import java.text.DecimalFormat;
import java.text.NumberFormat;

class ComparisonHelper {

	private double usageAmount;
	private double avgAmount;
	private double minAmount;
	private double maxAmount;

	private String comparisonType;
	private double comparisonValue;

	private double absoluteDiff;
	private double percentDiff;

	private final String resourceName;
	private final double rate;
	private final String usageUnit;
	private final String inputUnit;

	// ================================================================================
	// Constructor
	// ================================================================================

	ComparisonHelper(String resourceName,
	                 double rate,
	                 String usageUnit,
	                 String inputUnit) {
		this.resourceName = resourceName;
		this.rate = rate;
		this.usageUnit = usageUnit;
		this.inputUnit = inputUnit;
	}


	// ================================================================================
	// Compare methods
	// ================================================================================

	String compareGlobalAvg(double usageAmount, double avgAmount) {
		this.usageAmount = usageAmount;
		this.avgAmount = avgAmount;
		this.minAmount = 0.0;
		this.maxAmount = 0.0;

		String comparison = returnBaseComparison();
		if (isGreaterAvg()) {
			comparison += followupHowMuchToAvg();
		} else {
			comparison += followupGoodJob();
		}
		return comparison;
	}

	String compareHistorical(double usageAmount, double avgAmount, double minAmount, double maxAmount) {
		this.usageAmount = usageAmount;
		this.avgAmount = avgAmount;
		this.minAmount = minAmount;
		this.maxAmount = maxAmount;

		String comparison = returnBaseComparison();
		if (isGreaterAvg()) {
			comparison += followupHowMuchToAvg();
		} else if (isLessMin()) {
			comparison += followupGoodJob();
		} else {
			comparison += followupHowMuchToMin();
		}
		return comparison;
	}

	// ================================================================================
	// Return comparisons
	// ================================================================================

	// TODO: fix logic, for historical max actually comparing to avg even though saying max
	private String returnBaseComparison() {
		// calc values
		determineComparisonType();
		determineComparisonAmount();
		calcAbsoluteDiff();
		calcPercentDiff();
		String moreOrLess = determineMoreOrLess();
		// return result
		return "You used " + decimals.format(absoluteDiff) + " " + moreOrLess + " " + usageUnit + " than " +
				comparisonType + " of " + decimals.format(comparisonValue) + " " + usageUnit + "! That's " +
				percents.format(percentDiff) + " " + moreOrLess + " than " + comparisonType + ".";
	}


	private String followupGoodJob() {
		return "\nKeep it up!";
	}

	private String followupHowMuchToAvg() {
		String changeAmount = decimals.format(calcFollowupChangeAmount(absoluteDiff));
		String followup = "\nYou would need to use the " + resourceName + " " + changeAmount + " fewer " +
				inputUnit + " to get to ";
		if (checkGlobalAverage()) {
			followup += "the average usage.";
		} else if (checkSingleHistoricalDatum()) {
			followup += "your prior usage.";
		} else {
			followup += "your average usage.";
		}
		return followup;
	}

	private String followupHowMuchToMin() {
		String changeAmount = decimals.format(calcFollowupChangeAmount(absoluteDiff));
		return "\nYou would need to use the " + resourceName + " " + changeAmount + " fewer " +
				inputUnit + " to beat your lowest record.";
	}

	private final NumberFormat percents = NumberFormat.getPercentInstance();
	private final DecimalFormat decimals = new DecimalFormat("0.##");


	// ================================================================================
	// Comparison tests
	// ================================================================================

	private boolean isGreaterAvg() {
		return usageAmount > avgAmount;
	}

	private boolean isGreaterMax() {
		return usageAmount > maxAmount;
	}

	private boolean isLessMin() {
		return usageAmount < minAmount;
	}

	private boolean checkGlobalAverage() {
		return minAmount == 0.0 && maxAmount == 0.0 && avgAmount != 0.0;
	}

	private boolean checkSingleHistoricalDatum() {
		return minAmount == maxAmount && minAmount == avgAmount && maxAmount == avgAmount;
	}


	// ================================================================================
	// Determine comparison type/values
	// ================================================================================

	private void determineComparisonType() {
		// check if historical or average
		if (checkGlobalAverage()) {
			this.comparisonType = "the average use";
		}
		// if historical, determine comp type
		else if (checkSingleHistoricalDatum()) {
			this.comparisonType = "your prior usage";
		} else if (isGreaterMax()) {
			this.comparisonType = "your max";
		} else if (isLessMin()) {
			this.comparisonType = "your min";
		} else {
			this.comparisonType = "your average";
		}
	}

	private void determineComparisonAmount() {
		if (comparisonType.equals("your max")) {
			this.comparisonValue = maxAmount;
		}
		if (comparisonType.equals("your min")) {
			this.comparisonValue = minAmount;
		} else {
			this.comparisonValue = avgAmount;
		}
	}

	private String determineMoreOrLess() {
		return usageAmount >= comparisonValue ? "more" : "less";
	}


	// ================================================================================
	// Calculate differences
	// ================================================================================

	private void calcAbsoluteDiff() {
		absoluteDiff = Math.abs(usageAmount - comparisonValue);
	}

	private void calcPercentDiff() {
		percentDiff = Math.abs((usageAmount - comparisonValue) / comparisonValue);
	}


	// ================================================================================
	// Calculate followup's required change amount
	// ================================================================================

	private double calcFollowupChangeAmount(double absoluteDiff) {
		double changeAmount = absoluteDiff / rate;
		if (usageUnit.equalsIgnoreCase("seconds")) {
			changeAmount = base.UsageDuration.convertToSec(changeAmount);
		}
		return changeAmount;
	}

}
