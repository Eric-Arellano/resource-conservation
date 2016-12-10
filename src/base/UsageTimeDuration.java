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

	public void promptInput() {
		String inputUnit = getInputUnit();
		assert inputUnit.equals("minutes") || inputUnit.equals("seconds") || inputUnit.equals("both");
		switch (inputUnit) {
			case "minutes":
				promptMin();
				break;
			case "seconds":
				promptSec();
				break;
			case "both":
				promptBoth();
				break;
		}
	}

	private void promptSec() {
		System.out.print("How many seconds did you use the " + getName() + "? ");
		double numSec = in.nextDouble();
		double numMin = convertToMin(numSec);
		setInputAmt(numMin);
	}

	private void promptMin() {
		System.out.print("How many minutes did you use the " + getName() + "? ");
		double numMin = in.nextDouble();
		setInputAmt(numMin);
	}

	private void promptBoth() {
		System.out.print("How many minutes did you use the " + getName() +
				"? (You'll be asked about seconds after): ");
		double numMin = in.nextDouble();
		System.out.print("How many seconds did you use the " + getName() + "? ");
		double numSec = in.nextDouble();
		numMin += convertToMin(numSec);
		setInputAmt(numMin);
		setInputUnit("minutes");
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
