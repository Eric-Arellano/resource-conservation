package base;

import features.AverageUsage;
import features.HistoricalUsage;
import features.Tips;

/**
 * Used for Resource types that occur over a discrete quantity, e.g. number of times or miles
 * driven.
 */
public class UsageDiscreteQuantity extends ResourceUsage {

	/**
	 * Constructs a ResourceUsage object whose input is based on number of times resource used.
	 *
	 * @param name      - name of usage type, e.g. "sink" or "shower"
	 * @param rate      - rate of consumption, should always be in terms of use
	 * @param usageUnit - e.g. "gallons" or "liters"
	 * @param inputType - e.g. "times per month", "miles driven"
	 */
	public UsageDiscreteQuantity(String name,
	                             double rate,
	                             String usageUnit,
	                             String inputType,
	                             Tips tips,
	                             AverageUsage avg,
	                             HistoricalUsage historical) {
		super(name, rate, usageUnit, tips, avg, historical);
		setInputUnit(inputType);
	}

	// ================================================================================
	// Prompt Input
	// ================================================================================

	public void promptInput() {
		System.out.print("How many " + this.getInputUnit() + " did you use the " + getName() + "? ");
		double input = in.nextDouble();
		setInputAmt(input);
	}

}