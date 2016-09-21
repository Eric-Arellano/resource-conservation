package features;

public class AverageUsage {

	private double avg; // in usage unit (not input unit), e.g. gallons

	// ================================================================================
	// Constructors
	// ================================================================================

	// Avg in usage unit already known
	public AverageUsage(double avg) {
		this.avg = avg;
	}

	// Avg not known in usage unit
	public AverageUsage(double rate, double inputAmt) {
		this.avg = calcAvg(rate, inputAmt);
	}

	// Used to convert input unit to usage unit in constructor.
	private double calcAvg(double rate, double inputAmt) {
		return inputAmt * rate;
	}

	// ================================================================================
	// Public Interface
	// ================================================================================

	public String compareAverage(double usageAmount, String usageUnit, String name, double rate) {
		ComparisonHelper comparer = new ComparisonHelper(usageAmount, avg, usageUnit, name, rate);
		return comparer.compareAvg();
	}

}
