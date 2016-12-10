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
	 * @param name      - name of usage type, e.g. "sink" or "shower"
	 * @param rate      - rate of consumption, should always be in terms of minutes
	 * @param usageUnit - e.g. "gallons" or "liters"
	 * @param timeType  - how time will be inputted; expected "seconds", "minutes", or "both"
	 */
	public UsageTimeDuration(String name,
	                         double rate,
	                         String usageUnit,
	                         TimeType timeType,
	                         String tipsFilePath,
	                         double avgInUsageUnit,
	                         double... historicalUsagesInInputUnits) {
		super(name,
				rate,
				usageUnit,
				timeType.toString(),
				tipsFilePath,
				avgInUsageUnit,
				historicalUsagesInInputUnits);
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
				prompt = promptMin();
				break;
			case "seconds":
				prompt = promptSec();
				break;
			case "both":
				prompt = promptBoth();
				break;
		}
		return prompt;
	}

	private String promptSec() {
		String prompt = "How many seconds did you use the " + getName() + "? ";
		System.out.print(prompt);
		double numSec = in.nextDouble();
		double numMin = convertToMin(numSec);
		setInputAmt(numMin);
		return prompt;
	}

	private String promptMin() {
		String prompt = "How many minutes did you use the " + getName() + "? ";
		System.out.print(prompt);
		double numMin = in.nextDouble();
		setInputAmt(numMin);
		return prompt;
	}

	private String promptBoth() {
		String promptMinutes = "How many minutes did you use the " + getName() + "? (You'll be asked " +
				"about seconds after): ";
		System.out.print(promptMinutes);
		double numMin = in.nextDouble();
		String promptSeconds = "How many seconds did you use the " + getName() + "? ";
		System.out.print(promptSeconds);
		double numSec = in.nextDouble();
		numMin += convertToMin(numSec);
		setInputAmt(numMin);
		setInputUnit("minutes");
		return promptMinutes + promptSeconds;
	}

	// ================================================================================
	// Time unit conversion
	// ================================================================================

	public static double convertToMin(double numSec) {
		return numSec / 60;
	}

	public static double convertToSec(double numMin) {
		return numMin * 60;
	}


}
