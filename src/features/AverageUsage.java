package features;


public class AverageUsage {

	// ================================================================================
	// Instance Variable
	// ================================================================================

	private double avg; // should be in usage unit, e.g. gallons

	// ================================================================================
	// Constructor
	// ================================================================================

	public AverageUsage(double avg) {
		this.avg = avg;
	}

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

	public String compareAverage(double usage, String usageUnit, String name, double rate) {
		ComparisonHelper comparer = new ComparisonHelper(usage, avg, usageUnit, name, rate);
		return comparer.compareAvg();
	}

}
