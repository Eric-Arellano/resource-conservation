package base;

/**
 * Used for Resource types that occur over a discrete quantity, e.g. number of times or miles
 * driven.
 */
public class UsageDiscreteQuantity extends ResourceUsage {

	/**
	 * Constructs a ResourceUsage object whose input is based on number of times resource used.
	 *
	 * @param resourceName - name of usage type, e.g. "sink" or "shower"
	 * @param rate         - rate of consumption, should always be in terms of use
	 * @param usageUnit    - e.g. "gallons" or "liters"
	 * @param inputUnit    - e.g. "times per month", "miles driven"
	 */
	public UsageDiscreteQuantity(String resourceName,
	                             double rate,
	                             String usageUnit,
	                             String inputUnit,
	                             String tipsFilePath,
	                             double globalAverageInUsageUnit,
	                             double... historicalUsagesInInputUnit) {
		super(resourceName,
				rate,
				usageUnit,
				inputUnit,
				tipsFilePath,
				globalAverageInUsageUnit,
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