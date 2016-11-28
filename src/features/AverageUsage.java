package features;

public class AverageUsage {

	private final double avg; // in usage unit (not input unit), e.g. gallons
	private final ComparisonHelper comparisonHelper;

	public AverageUsage(String resourceName,
	                    double rate,
	                    String usageUnit,
	                    String inputUnit,
	                    double avgInUsageAmt) {
		this.avg = avgInUsageAmt;
		this.comparisonHelper = new ComparisonHelper(resourceName, rate, usageUnit, inputUnit);
	}

	public String compareAverage(double usageAmount) {
		return comparisonHelper.compareGlobalAvg(usageAmount, getAvg());
	}

	private double getAvg() {
		return this.avg;
	}

}
