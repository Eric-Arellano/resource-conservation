package features;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import static base.UsageTimeDuration.convertToSeconds;

class ComparisonHelper {

	private double usageAmount;
	private double averageAmount;
	private double minAmount;
	private double maxAmount;

	private String comparisonType;
	private double comparisonValue;

	private double absoluteDifference;
	private double percentDifference;

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

	String compareGlobalAverage(double usageAmount, double averageAmount) {
		this.usageAmount = usageAmount;
		this.averageAmount = averageAmount;
		this.minAmount = 0.0;
		this.maxAmount = 0.0;
		return returnBaseComparison() + addGlobalAverageFollowup();
	}

	String compareHistorical(double usageAmount,
	                         double averageAmount,
	                         double minAmount,
	                         double maxAmount) {
		this.usageAmount = usageAmount;
		this.averageAmount = averageAmount;
		this.minAmount = minAmount;
		this.maxAmount = maxAmount;
		return returnBaseComparison() + addHistoricalFollowup();
	}


	// ================================================================================
	// Return comparisons
	// ================================================================================

	private String returnBaseComparison() {
		// calc values
		determineComparisonType();
		determineComparisonAmount();
		calcAbsoluteDifference();
		calcPercentDifference();
		String moreOrLess = determineMoreOrLess();
		// return result
		return "You used " + decimals.format(absoluteDifference) + " " + moreOrLess + " " + usageUnit + " than " +
				comparisonType + " of " + decimals.format(comparisonValue) + " " + usageUnit + "! That's " +
				percents.format(percentDifference) + " " + moreOrLess + " than " + comparisonType + ".";
	}

	private String addGlobalAverageFollowup() {
		if (isGreaterThanAverage()) {
			return followupHowMuchToAverage();
		} else {
			return followupGoodJob();
		}
	}

	private String addHistoricalFollowup() {
		if (isGreaterThanAverage()) {
			return followupHowMuchToAverage();
		} else if (isLessThanMin()) {
			return followupGoodJob();
		} else {
			return followupHowMuchToMin();
		}
	}

	private String followupGoodJob() {
		return "\nKeep it up!";
	}

	private String followupHowMuchToAverage() {
		String changeAmount = decimals.format(calcFollowupChangeAmount(absoluteDifference));
		String followup = "\nYou would need to use the " + resourceName + " " + changeAmount + " fewer " +
				inputUnit + " to get to ";
		if (isGlobalAverage()) {
			followup += "the average usage.";
		} else if (isSingleHistoricalDatum()) {
			followup += "your prior usage.";
		} else {
			followup += "your average usage.";
		}
		return followup;
	}

	private String followupHowMuchToMin() {
		String changeAmount = decimals.format(calcFollowupChangeAmount(absoluteDifference));
		return "\nYou would need to use the " + resourceName + " " + changeAmount + " fewer " +
				inputUnit + " to beat your lowest record.";
	}

	private final NumberFormat percents = NumberFormat.getPercentInstance();
	private final DecimalFormat decimals = new DecimalFormat("0.##");


	// ================================================================================
	// Comparison tests
	// ================================================================================

	private boolean isGlobalAverage() {
		return minAmount == 0.0 && maxAmount == 0.0 && averageAmount != 0.0;
	}

	private boolean isSingleHistoricalDatum() {
		return minAmount == maxAmount && minAmount == averageAmount && maxAmount == averageAmount;
	}

	private boolean isGreaterThanAverage() {
		return usageAmount > averageAmount;
	}

	private boolean isGreaterThanMax() {
		return usageAmount > maxAmount;
	}

	private boolean isLessThanMin() {
		return usageAmount < minAmount;
	}

	// ================================================================================
	// Determine comparison type/values
	// ================================================================================

	private void determineComparisonType() {
		if (isGlobalAverage()) {
			this.comparisonType = "the average use";
		} else if (isSingleHistoricalDatum()) {
			this.comparisonType = "your prior usage";
		} else if (isGreaterThanMax()) {
			this.comparisonType = "your max";
		} else if (isLessThanMin()) {
			this.comparisonType = "your min";
		} else {
			this.comparisonType = "your average";
		}
	}

	private void determineComparisonAmount() {
		switch (comparisonType) {
			case "your max":
				this.comparisonValue = maxAmount;
				break;
			case "your min":
				this.comparisonValue = minAmount;
				break;
			default:
				this.comparisonValue = averageAmount;
				break;
		}
	}

	private String determineMoreOrLess() {
		return usageAmount >= comparisonValue ? "more" : "less";
	}


	// ================================================================================
	// Calculate differences
	// ================================================================================

	private void calcAbsoluteDifference() {
		absoluteDifference = Math.abs(usageAmount - comparisonValue);
	}

	private void calcPercentDifference() {
		percentDifference = Math.abs((usageAmount - comparisonValue) / comparisonValue);
	}


	// ================================================================================
	// Calculate followup's required change amount
	// ================================================================================

	private double calcFollowupChangeAmount(double absoluteDifference) {
		double changeAmount = absoluteDifference / rate;
		if (usageUnit.equalsIgnoreCase("seconds")) {
			changeAmount = convertToSeconds(changeAmount);
		}
		return changeAmount;
	}

}
