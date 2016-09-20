package features;


class ComparisonHelper {

	private final double usageAmount;
	private final double avgAmount;
	private final double minAmount;
	private final double maxAmount;

	private String comparisonType;
	private double comparisonValue;

	private final String unit;
	private final String name;

	// ================================================================================
	// Constructor
	// ================================================================================

	// Average usage constructor
	ComparisonHelper (double usageAmount,
	                  double avgAmount,
	                  String unit,
	                  String resourceName) {
		this.usageAmount = usageAmount;
		this.avgAmount = avgAmount;
		this.minAmount = 0.0;
		this.maxAmount = 0.0;
		this.unit = unit;
		this.name = resourceName;
	}

	// Historical usage constructor
	ComparisonHelper (double usageAmount,
	                  double avgAmount,
	                  double minAmount,
	                  double maxAmount,
	                  String unit,
	                  String resourceName) {
		this.usageAmount = usageAmount;
		this.avgAmount = avgAmount;
		this.minAmount = minAmount;
		this.maxAmount = maxAmount;
		this.unit = unit;
		this.name = resourceName;
	}


	// ================================================================================
	// Return comparisons
	// ================================================================================
	// TODO: figure out how to format numbers

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
		// calc values
		determineComparisonType();
		determineComparisonAmount();
		double absoluteDiff = calcAbsoluteDiff();
		double percentDiff = calcPercentDiff();
		String moreOrLess = determineMoreOrLess();
		// return result
		return "You used " + absoluteDiff + moreOrLess + unit + "than your " + comparisonType +
				"of" + comparisonValue + unit + "! That's" + percentDiff + moreOrLess + "than your " + comparisonType;
	}


	private String followupGoodJob() {
		return "\nKeep it up!";
	}

	private String followupHowMuchToAvg() {
		return "";
//				"\nYou would need to use the " + name + calcInputChange(absoluteDiff) + " fewer " + unit + "to " +
//				"get to your average usage.";
//		TODO: figure out calcInputChange
	}

	private String followupHowMuchToMin() {
		return "";
//		return "You would need to use the " + name + calcInputChange(absoluteDiff) + " fewer " + unit + "to beat your lowest record.";
//		TODO: figure out CalcInputChange
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
		if (minAmount == 0.0 && maxAmount == 0.0) {
			this.comparisonType = "average user";
		}
		// if historical, determine comp type
		else if (isGreaterMax()) {
			this.comparisonType = "your max";
		}
		else if (isLessMin()) {
			this.comparisonType = "your min";
		}
		else {
			this.comparisonType = "your average";
		}
	}

	private void determineComparisonAmount() {
		if (comparisonType.equals("your max")) {
			this.comparisonValue = maxAmount;
		}
		if (comparisonType.equals("your min")) {
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
