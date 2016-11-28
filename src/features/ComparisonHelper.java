package features;

import java.text.DecimalFormat;
import java.text.NumberFormat;

class ComparisonHelper {

	private final double usageAmount;
	private final double avgAmount;
	private final double minAmount;
	private final double maxAmount;

	private String comparisonType;
	private double comparisonValue;

	private double absoluteDiff;
	private double percentDiff;


	private final String resourceName;
	private final double rate;
	private final String usageUnit;
	private final String inputUnit;

	// ================================================================================
	// Constructors
	// ================================================================================

	// Average usage constructor
	ComparisonHelper(String resourceName,
	                 double rate,
	                 String usageUnit,
	                 String inputUnit,
	                 double usageAmount,
	                 double avgAmount) {
		this.usageAmount = usageAmount;
		this.avgAmount = avgAmount;
		this.minAmount = 0.0;
		this.maxAmount = 0.0;

		this.resourceName = resourceName;
		this.rate = rate;
		this.usageUnit = usageUnit;
		this.inputUnit = inputUnit;
	}

	// Historical usage constructor
	ComparisonHelper(String resourceName,
	                 double rate,
	                 String usageUnit,
	                 String inputUnit,
	                 double usageAmount,
	                 double avgAmount,
	                 double minAmount,
	                 double maxAmount) {
		this.usageAmount = usageAmount;
		this.avgAmount = avgAmount;
		this.minAmount = minAmount;
		this.maxAmount = maxAmount;

		this.resourceName = resourceName;
		this.rate = rate;
		this.usageUnit = usageUnit;
		this.inputUnit = inputUnit;
	}


	// ================================================================================
	// Return comparisons
	// ================================================================================

	String compareAvg() {
		String comparison = returnBaseComparison();
		if (isGreaterAvg()) {
			comparison += followupHowMuchToAvg();
		} else {
			comparison += followupGoodJob();
		}
		return comparison;
	}

	String compareHistorical() {
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
		if (minAmount == 0.0 && maxAmount == 0.0) {
			followup += "the average usage.";
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


	// ================================================================================
	// Determine comparison type/values
	// ================================================================================

	private void determineComparisonType() {
		// check if historical or average
		if (minAmount == 0.0 && maxAmount == 0.0) {
			this.comparisonType = "the average use";
		}
		// if historical, determine comp type
		else if (isGreaterMax()) {
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
