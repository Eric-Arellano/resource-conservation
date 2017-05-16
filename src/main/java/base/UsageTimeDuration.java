package base;

public final class UsageTimeDuration extends ResourceUsage {

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
		String inputUnit = getInputUnit();
		assert inputUnit.equals("minutes") || inputUnit.equals("seconds");
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
		return "How many seconds did you use the " + getResourceName() + "? ";
	}

	private String promptMinutes() {
		return "How many minutes did you use the " + getResourceName() + "? ";
	}

	public void implementInput(double inputAmount) {
		switch (getInputUnit()) {
			case "minutes":
				setInputAmount(inputAmount);
				break;
			case "seconds":
				double inputInMinutes = convertToMinutes(inputAmount);
				setInputAmount(inputInMinutes);
				break;
		}
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
