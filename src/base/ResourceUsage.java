package base;

import features.AverageUsage;
import features.HistoricalUsage;
import features.Tips;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * Main program class. Used to create and interface with different resource consumption types.
 *
 * @author ericarellano
 */
abstract public class ResourceUsage {

	private final String name; // name of usage type
	private final double rate;
	private double inputAmt; // user-inputted amount; not in final units
	private String inputUnit;
	private double usageAmt; // converted inputAmt; in final units
	private final String usageUnit; // unit to report in, such as gal
	private final Tips tips;
	private final AverageUsage avg;
	private final HistoricalUsage hist;

	private final DecimalFormat decimals = new DecimalFormat("0.##");
	Scanner in = new Scanner(System.in);

	ResourceUsage(String name,
	              double rate,
	              String usageUnit,
	              Tips tips,
	              AverageUsage avg,
	              HistoricalUsage historical) {
		this.name = name;
		this.rate = rate;
		this.usageUnit = usageUnit;
		this.tips = tips;
		this.avg = avg;
		this.hist = historical;
	}


	// ================================================================================
	// Base usage
	// ================================================================================

	abstract public void promptInput();

	public void calcUsage() {
		usageAmt = inputAmt * rate;
		hist.addHistorical(usageAmt); // TODO: fix logical error, current usage is being treated as
		// historical
	}

	public void displayUsage() {
		System.out.println("That means you used " + decimals.format(usageAmt) + " " + usageUnit + ".");
	}


	// ================================================================================
	// Feature usage
	// ================================================================================

	public void displayTips() throws FileNotFoundException {
		System.out.println(tips.displayTips());
	}

	public void displayHistorical() {
		System.out.println(hist.displayHistorical(usageUnit));
	}

	/**
	 * Compares to global average usage. Prints comparison and followup action.
	 */
	public void compareAverage() {
		System.out.println(avg.compareAverage(usageAmt, usageUnit, name, rate));
	}

	/**
	 * Compares to historical min, max, and avg values. Prints comparison to relevant value and
	 * followup action.
	 */
	public void compareHistorical() {
		System.out.println(hist.compareHistorical(usageAmt, usageUnit, name, rate));
	}

	// ================================================================================
	// Getter methods
	// ================================================================================

	String getName() {
		return name;
	}

	String getInputUnit() {
		return inputUnit;
	}

	// ================================================================================
	// Setter methods
	// ================================================================================

	public void setInputUnit(String inputUnit) {
		this.inputUnit = inputUnit;
	}

	void setInputAmt(double input) {
		this.inputAmt = input;
	}

}