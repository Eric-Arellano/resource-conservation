package base;

import features.AverageUsage;
import features.HistoricalUsage;
import features.Tips;

/**
 * Used for Resource types that occur a number of times, e.g. flushing toilet x times.
 */
public class UsageNumTimes extends ResourceUsage {

	/**
	 * Constructs a ResourceUsage object whose input is based on number of times resource used.
	 *
	 * @param name      - name of usage type, e.g. "sink" or "shower"
	 * @param rate      - rate of consumption, should always be in terms of use
	 * @param usageUnit - e.g. "gallons" or "liters"
	 */
	UsageNumTimes(String name,
	              double rate,
	              String usageUnit,
	              Tips tips,
	              AverageUsage avg,
	              HistoricalUsage historical) {
		super(name, rate, usageUnit, tips, avg, historical);
		setInputUnit("times");
	}

	// ================================================================================
	// Prompt Input
	// ================================================================================

	public void promptInput() {
		System.out.print("How many " + this.getInputUnit() + " did you use the " + getName() + "? ");
		double input = in.nextDouble(); // double so that it can include things like halves
		setInputAmt(input);
	}

}