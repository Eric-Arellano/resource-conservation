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
	private final String inputUnit;
	private final String usageUnit;

	// ================================================================================
	// Constructor
	// ================================================================================

	ComparisonHelper(String resourceName,
	                 double rate,
	                 String inputUnit,
	                 String usageUnit) {
		this.resourceName = resourceName;
		this.rate = rate;
		this.inputUnit = inputUnit;
		this.usageUnit = usageUnit;
	}


	// ================================================================================
	// Compare methods
	// ================================================================================

	String compareGlobalAverage(double usageAmount, double averageAmount) {
		final double NO_MIN_MAX = 0;
		setComparisonValues(usageAmount, averageAmount, NO_MIN_MAX, NO_MIN_MAX);
		return returnBaseComparison() + addGlobalAverageFollowup();
	}

	String compareHistorical(double usageAmount, double averageAmount, double minAmount, double
			maxAmount) {
		setComparisonValues(usageAmount, averageAmount, minAmount, maxAmount);
		return returnBaseComparison() + addHistoricalFollowup();
	}

	// ================================================================================
	// Set comparison values
	// ================================================================================

	private void setComparisonValues(double usageAmount, double averageAmount, double minAmount,
	                                 double maxAmount) {
		this.usageAmount = usageAmount;
		this.averageAmount = averageAmount;
		this.minAmount = minAmount;
		this.maxAmount = maxAmount;
		setComparisonType();
		setComparisonAmount();
		setAbsoluteDifference();
		setPercentDifference();
	}

	private void setComparisonType() {
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

	private void setComparisonAmount() {
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

	private void setAbsoluteDifference() {
		absoluteDifference = Math.abs(usageAmount - comparisonValue);
	}

	private void setPercentDifference() {
		percentDifference = Math.abs((usageAmount - comparisonValue) / comparisonValue);
	}

	private double returnFollowupChangeAmount(double absoluteDifference) {
		double changeAmount = absoluteDifference / rate;
		if (usageUnit.equals("seconds")) {
			changeAmount = convertToSeconds(changeAmount);
		}
		return changeAmount;
	}


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

	private String isMoreOrLess() {
		return usageAmount >= comparisonValue ? "more" : "less";
	}


	// ================================================================================
	// Return comparisons
	// ================================================================================

	private final NumberFormat percents = NumberFormat.getPercentInstance();
	private final DecimalFormat decimals = new DecimalFormat("0.##");

	private String returnBaseComparison() {
		String moreOrLess = isMoreOrLess();
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
		String changeAmount = decimals.format(returnFollowupChangeAmount(absoluteDifference));
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
		String changeAmount = decimals.format(returnFollowupChangeAmount(absoluteDifference));
		return "\nYou would need to use the " + resourceName + " " + changeAmount + " fewer " +
				inputUnit + " to beat your lowest record.";
	}

}
