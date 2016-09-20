package features;


class ComparisonHelper {

	private double usageAmount;
	private double avgAmount;
	private double minAmount;
	private double maxAmount;

	private String comparisonType;
	private double comparisonValue;

	private String unit;
	private String name;

	// ================================================================================
	// Constructor and destructor
	// ================================================================================


	// ================================================================================
	// Return comparisons
	// ================================================================================

	public String compareAvg() {
		String comparison = returnBaseComparison();
		if (isGreaterAvg()) {
			comparison += followupHowMuchToAvg();
		}
		else {
			followupGoodJob();
		}
		return comparison;
	}

	public String compareHistorical() {
		String comparison = returnBaseComparison();
		if (isGreaterAvg()) {
			comparison += followupHowMuchToAvg();
		}
		else if (isLessMin()) {
			comparison += followupGoodJob();
		}
		else {
			comparison += followupHowMuchToMin();
		}
		return comparison;
	}

	private String returnBaseComparison() {
		double absoluteDiff = calcAbsoluteDiff();
		double percentDiff = calcPercentDiff();
		String moreOrLess = determineMoreOrLess();
		return "You used " + absoluteDiff + moreOrLess + unit + "than your " + comparisonType +
				"of" + comparisonValue + unit + "! That's" + percentDiff + moreOrLess + "than your " + comparisonType;
	}


	private String followupGoodJob() {
		return "\nKeep it up!";
	}

	private String followupHowMuchToAvg() {
		return "\nYou would need to use the " + name + calcInputChange(absoluteDiff) + " fewer " + unit + "to " +
				"get to your average usage.";
	}

	private String followupHowMuchToMin() {
		return "You would need to use the " + name + calcInputChange(absoluteDiff) + " fewer " + unit + "to beat your lowest record.";
	}


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
		if (minAmount == 0 && maxAmount == 0) {
			this.comparisonType = "average";
		}
		// if historical, determine comp type
		else if (isGreaterAvg()) {
			this.comparisonType = "max";
		}
		else if (isLessMin()) {
			this.comparisonType = "min";
		}
		else {
			this.comparisonType = "average";
		}
	}

	private void determineComparisonAmount() {
		if (comparisonType.equals("max")) {
			this.comparisonValue = maxAmount;
		}
		if (comparisonType.equals("min")) {
			this.comparisonValue = minAmount;
		}
		else {
			this.comparisonValue = avgAmount;
		}
	}

	private String determineMoreOrLess() {
		return usageAmount > comparisonValue ? "more" : "less";
	}


	// ================================================================================
	// Calculate differences
	// ================================================================================

	private double calcAbsoluteDiff() {
		return Math.abs(usageAmount - comparisonValue);
	}

	private double calcPercentDiff() {
		return Math.abs((usageAmount - comparisonValue) / comparisonValue);
	}


	// ================================================================================
	// Required input change
	// ================================================================================

	// TODO: come up with a design! Problem is handling differences between UsageDuration and UsageNumTimes.


}
