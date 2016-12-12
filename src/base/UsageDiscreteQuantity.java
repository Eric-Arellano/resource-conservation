package base;

/**
 * Used for Resource types that occur over a discrete quantity, e.g. number of times or miles
 * driven.
 */
public class UsageDiscreteQuantity extends ResourceUsage {

	/**
	 * Constructs a ResourceUsage object whose input is based on number of times resource used.
	 *
	 * @param inputUnit - unit user interfaces with, e.g. "times per month", "miles driven"
	 * @param usageUnit - unit reported back, e.g. "gallons" or "liters"
	 */
	public UsageDiscreteQuantity(String resourceName,
	                             double rate_UsagePerInput,
	                             String inputUnit,
	                             String usageUnit,
	                             String tipsFilePath,
	                             double globalAverageInInputUnit,
	                             double... historicalUsagesInInputUnit) {
		super(resourceName,
				rate_UsagePerInput,
				inputUnit, usageUnit,
				tipsFilePath,
				globalAverageInInputUnit,
				historicalUsagesInInputUnit);
	}

	// ================================================================================
	// Prompt Input
	// ================================================================================

	public String promptInput() {
		String prompt = "How many " + this.getInputUnit() + " did you use the " + getResourceName() + "? ";
		System.out.print(prompt);
		double input = in.nextDouble();
		setInputAmount(input);
		return prompt;
	}
}