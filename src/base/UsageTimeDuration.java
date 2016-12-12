package base;

public class UsageTimeDuration extends ResourceUsage {

	public enum TimeType {
		SECONDS, MINUTES, BOTH;

		@Override
		public String toString() {
			return name().toLowerCase();
		}
	}

	/**
	 * Constructs a ResourceUsage object whose input is based on time duration.
	 *
	 * @param resourceName - name of usage type, e.g. "sink" or "shower"
	 * @param rate         - rate of consumption, should always be in terms of minutes
	 * @param usageUnit    - e.g. "gallons" or "liters"
	 * @param timeType     - how time will be inputted; expected "seconds", "minutes", or "both"
	 */
	public UsageTimeDuration(String resourceName,
	                         double rate,
	                         String usageUnit,
	                         TimeType timeType,
	                         String tipsFilePath,
	                         double globalAverageInUsageUnit,
	                         double... historicalUsagesInInputUnit) {
		super(resourceName,
				rate,
				usageUnit,
				timeType.toString(),
				tipsFilePath,
				globalAverageInUsageUnit,
				historicalUsagesInInputUnit);
	}

	// ================================================================================
	// Prompt input Methods
	// ================================================================================

	public String promptInput() {
		String inputUnit = getInputUnit();
		assert inputUnit.equals("minutes") || inputUnit.equals("seconds") || inputUnit.equals("both");
		String prompt = "";
		switch (inputUnit) {
			case "minutes":
				prompt = promptMinutes();
				break;
			case "seconds":
				prompt = promptSeconds();
				break;
			case "both":
				prompt = promptBothSecondsMinutes();
				break;
		}
		return prompt;
	}

	private String promptSeconds() {
		String prompt = "How many seconds did you use the " + getResourceName() + "? ";
		System.out.print(prompt);
		double inputInSeconds = in.nextDouble();
		double inputInMinutes = convertToMinutes(inputInSeconds);
		setInputAmount(inputInMinutes);
		return prompt;
	}

	private String promptMinutes() {
		String prompt = "How many minutes did you use the " + getResourceName() + "? ";
		System.out.print(prompt);
		double inputInMinutes = in.nextDouble();
		setInputAmount(inputInMinutes);
		return prompt;
	}

	private String promptBothSecondsMinutes() {
		String promptMinutes = "How many minutes did you use the " + getResourceName() + "? (You'll be asked " +
				"about seconds after): ";
		System.out.print(promptMinutes);
		double inputInMinutes = in.nextDouble();
		String promptSeconds = "How many seconds did you use the " + getResourceName() + "? ";
		System.out.print(promptSeconds);
		double inputInSeconds = in.nextDouble();
		inputInMinutes += convertToMinutes(inputInSeconds);
		setInputAmount(inputInMinutes);
		setInputUnit("minutes");
		return promptMinutes + promptSeconds;
	}

	// ================================================================================
	// Time unit conversion
	// ================================================================================

	public static double convertToMinutes(double seconds) {
		return seconds / 60;
	}

	public static double convertToSeconds(double minutes) {
		return minutes * 60;
	}


}
