package features;

public class AverageGlobalUsage {

	private final double globalAverageUsage;
	private final ComparisonHelper comparisonHelper;

	public AverageGlobalUsage(String resourceName,
	                          double rate_UsagePerInput,
	                          String inputUnit,
	                          String usageUnit,
	                          double globalAverage_InInputUnit) {
		this.globalAverageUsage = globalAverage_InInputUnit * rate_UsagePerInput;
		this.comparisonHelper = new ComparisonHelper(resourceName,
				rate_UsagePerInput,
				inputUnit,
				usageUnit);
	}

	public String compareGlobalAverage(double usageAmount) {
		return comparisonHelper.compareGlobalAverage(usageAmount, getGlobalAverageUsage());
	}

	private double getGlobalAverageUsage() {
		return this.globalAverageUsage;
	}

}
