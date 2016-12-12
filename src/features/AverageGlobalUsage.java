package features;

public class AverageGlobalUsage {

	private final double globalAverage;
	private final ComparisonHelper comparisonHelper;

	public AverageGlobalUsage(String resourceName,
	                          double rate,
	                          String usageUnit,
	                          String inputUnit,
	                          double globalAverageInUsageUnit) {
		this.globalAverage = globalAverageInUsageUnit;
		this.comparisonHelper = new ComparisonHelper(resourceName, rate, usageUnit, inputUnit);
	}

	public String compareGlobalAverage(double usageAmount) {
		return comparisonHelper.compareGlobalAverage(usageAmount, getGlobalAverage());
	}

	private double getGlobalAverage() {
		return this.globalAverage;
	}

}
