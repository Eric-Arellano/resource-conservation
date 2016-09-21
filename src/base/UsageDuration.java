package base;

import features.AverageUsage;
import features.HistoricalUsage;
import features.Tips;

public class UsageDuration extends ResourceUsage {

	// ================================================================================
	// Constructor
	// ================================================================================

	/**
	 * Constructs a ResourceUsage object whose input is based on time duration.
	 *
	 * @param name      - name of usage type, e.g. "sink" or "shower"
	 * @param rate      - rate of consumption, should always be in terms of minutes
	 * @param usageUnit - e.g. "gallons" or "liters"
	 * @param timeType  - how time will be inputted; expected "seconds", "minutes", or "both"
	 */
	public UsageDuration(String name,
	                     double rate,
	                     String usageUnit,
	                     String timeType,
	                     Tips tips,
	                     AverageUsage avg,
	                     HistoricalUsage historical) {
		super(name, rate, usageUnit, tips, avg, historical);
		setInputUnit(timeType);
	}

	// ================================================================================
	// Prompt input Methods
	// ================================================================================

	public static double convertToMin(double numSec) {
		return numSec / 60;
	}

	public static double convertToSec(double numMin) {
		return numMin * 60;
	}

	public void promptInput() {
		if (getInputUnit().equalsIgnoreCase("seconds")) {
			promptSec();
		} else if (getInputUnit().equalsIgnoreCase("minutes")) {
			promptMin();
		} else if (getInputUnit().equalsIgnoreCase("both")) {
			promptBoth();
		} else {
			promptTimeType();
			promptInput();
		}
	}

	private void promptSec() {
		System.out.print("How many seconds did you use the " + getName() + "? ");
		double numSec = in.nextDouble();
		double numMin = convertToMin(numSec);
		setInputAmt(numMin);
	}

	// ================================================================================
	// Time unit conversion
	// ================================================================================

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
	// Mutator Methods
	// ================================================================================

	/**
	 * Method to prompt user to supply time type for input unit instead of programmer.
	 * Expected inputs: "minutes", "seconds", or "both"
	 */
	public void promptTimeType() {
		boolean flag = false;
		String type = "";
		while (!flag) {
			System.out.println("Oops, looks like the type of time input was set up incorrectly!" +
					"\nPlease enter one of the following Strings based off of how you're inputting " +
					"the amount of time you used the " + getName() + "." +
					"\n\t\"seconds\"" +
					"\n\t\"minutes\"" +
					"\n\t\"both\"");

			type = in.next();
			if (type.equals("minutes")) {
				flag = true;
			}
			if (type.equals("seconds")) {
				flag = true;
			}
			if (type.equals("both")) {
				flag = true;
			}
		}
		setInputUnit(type);
	}
}
