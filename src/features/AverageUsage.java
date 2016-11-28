package features;

public class AverageUsage {

	private double avg; // in usage unit (not input unit), e.g. gallons

	String resourceName;
	double rate;
	String usageUnit;
	String inputUnit;

	// ================================================================================
	// Constructors
	// ================================================================================

	public AverageUsage(String resourceName,
	                    double rate,
	                    String usageUnit,
	                    String inputUnit,
	                    double avgInUsageAmt) {
		this.avg = avgInUsageAmt;
		this.resourceName = resourceName;
		this.rate = rate;
		this.usageUnit = usageUnit;
		this.inputUnit = inputUnit;
	}

	// ================================================================================
	// Public Interface
	// ================================================================================

	public String compareAverage(double usageAmount) {
		ComparisonHelper comparer = new ComparisonHelper(resourceName, rate, usageUnit, inputUnit,
				usageAmount, getAvg());
		return comparer.compareAvg();
	}

	private double getAvg() {
		return this.avg;
	}

}
