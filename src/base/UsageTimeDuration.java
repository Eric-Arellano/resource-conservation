package base;

public class UsageTimeDuration extends ResourceUsage {

	public enum TimeType {
		SECONDS, MINUTES;

		@Override
		public String toString() {
			return name().toLowerCase();
		}
	}

	/**
	 * @param timeInputType - unit user interfaces with; must be SECONDS or MINUTES
	 * @param usageUnit     - unit reported back, e.g. "gallons" or "liters"
	 */
	public UsageTimeDuration(String resourceName,
	                         double rate_UsagePerMinute,
	                         TimeType timeInputType,
	                         String usageUnit,
	                         String tipsFilePath,
	                         double globalAverage_InInputUnit,
	                         double... historicalUsages_InInputUnit) {
		super(resourceName,
				rate_UsagePerMinute,
				timeInputType.toString(),
				usageUnit,
				tipsFilePath,
				globalAverage_InInputUnit,
				historicalUsages_InInputUnit);
	}

	// ================================================================================
	// Prompt input
	// ================================================================================

	public String promptInput() {
		// TODO: abstract code so it's not forced to sue console
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
