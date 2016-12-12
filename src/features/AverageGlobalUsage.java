package features;

public class AverageGlobalUsage {

	private final double globalAverage;
	private final ComparisonHelper comparisonHelper;

	public AverageGlobalUsage(String resourceName,
	                          double rate,
	                          String inputUnit,
	                          String usageUnit,
	                          double globalAverage_InInputUnit) {
		this.globalAverage = globalAverage_InInputUnit;
		this.comparisonHelper = new ComparisonHelper(resourceName, rate, inputUnit, usageUnit);
	}

	public String compareGlobalAverage(double usageAmount) {
		return comparisonHelper.compareGlobalAverage(usageAmount, getGlobalAverage());
	}

	private double getGlobalAverage() {
		return this.globalAverage;
	}

}
