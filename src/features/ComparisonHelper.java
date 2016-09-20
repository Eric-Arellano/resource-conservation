package features;


class ComparisonHelper {

	private final double usageAmount;
	private final double avgAmount;
	private final double minAmount;
	private final double maxAmount;

	private String comparisonType;
	private double comparisonValue;

	private double absoluteDiff;
	private double percentDiff;

	private final String unit;
	private final String name;
	private final double rate;

	// ================================================================================
	// Constructor
	// ================================================================================

	// Average usage constructor
	ComparisonHelper (double usageAmount,
	                  double avgAmount,
	                  String unit,
	                  String resourceName,
	                  double rate) {
		this.usageAmount = usageAmount;
		this.avgAmount = avgAmount;
		this.minAmount = 0.0;
		this.maxAmount = 0.0;
		this.unit = unit;
		this.name = resourceName;
		this.rate = rate;
	}

	// Historical usage constructor
	ComparisonHelper (double usageAmount,
	                  double avgAmount,
	                  double minAmount,
	                  double maxAmount,
	                  String unit,
	                  String resourceName,
	                  double rate) {
		this.usageAmount = usageAmount;
		this.avgAmount = avgAmount;
		this.minAmount = minAmount;
		this.maxAmount = maxAmount;
		this.unit = unit;
		this.name = resourceName;
		this.rate = rate;
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
		calcAbsoluteDiff();
		calcPercentDiff();
		String moreOrLess = determineMoreOrLess();
		// return result
		return "You used " + absoluteDiff + moreOrLess + unit + "than your " + comparisonType +
				"of" + comparisonValue + unit + "! That's" + percentDiff + moreOrLess + "than your " + comparisonType;
	}


	private String followupGoodJob() {
		return "\nKeep it up!";
	}

	private String followupHowMuchToAvg() {
		return "\nYou would need to use the " + name + calcFollowupChangeAmount(absoluteDiff) + " fewer " + unit + "to " +
				"get to your average usage.";
	}

	private String followupHowMuchToMin() {
		return "You would need to use the " + name + calcFollowupChangeAmount(absoluteDiff) + " fewer " + unit + "to beat your lowest record.";
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
		if (unit.equalsIgnoreCase("seconds")) {
			changeAmount = base.UsageDuration.convertToSec(changeAmount);
		}
		return changeAmount;
	}

}