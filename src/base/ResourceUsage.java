/**
 * This superclass serves as the main class of this ResourceConservation program. It creates an object
 * for each type of resource usage, and includes the main method of calculating resource usage.
 *
 * @author ericarellano
 */

package base;


import features.AverageUsage;
import features.HistoricalUsage;
import features.Tips;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Scanner;


abstract public class ResourceUsage {

	private final NumberFormat percent = NumberFormat.getPercentInstance();
	private final DecimalFormat fmt = new DecimalFormat("0.##");
	Scanner in = new Scanner(System.in);


	// ================================================================================
	// Instance Variables
	// ================================================================================

	private String name; // name of usage type
	private double rate;
	private double inputAmt; // user-inputted amount; not in final units
	private String inputUnit;
	private double usageAmt; // converted inputAmt; in final units
	private String usageUnit; // unit to report in, such as gal
	private Tips tips;
	private AverageUsage avg;
	private HistoricalUsage hist;


	// ================================================================================
	// Constructors
	// ================================================================================

	public ResourceUsage(String name,
	                     double rate,
	                     String unit,
	                     Tips tips,
	                     AverageUsage avg,
	                     HistoricalUsage historical) {
		this.name = name;
		this.rate = rate;
		this.usageUnit = unit;
		this.tips = tips;
		this.avg = avg;
		this.hist = historical;
	}

	public ResourceUsage(String name,
	                     double rate,
	                     String unit,
	                     HistoricalUsage historical) {
		this.name = name;
		this.rate = rate;
		this.usageUnit = unit;
		this.tips = null;
		this.avg = null;
		this.hist = historical;
	}

	// ================================================================================
	// User Interface methods
	// ================================================================================

	abstract public void promptInput();

	public void calcUsage() {
		usageAmt = inputAmt * rate;
		hist.addHistorical(usageAmt);
	}

	public void displayUsage() {
		System.out.println("That means you used " + fmt.format(usageAmt) + " " + usageUnit + ".");
	}

	public void displayTips() throws FileNotFoundException {
		System.out.println(tips.displayTips());
	}

	public void displayHistorical() {
		System.out.println(hist.displayHistorical());
	}

	/**
	 * Compares to global average usage. Prints comparison and followup action.
	 */
	public void compareAverage() {
		avg.compareAverage(usageAmt, inputUnit, name, rate);
	}

	/**
	 * Compares to historical min, max, and avg values.
	 *
	 * Prints comparison to relevant value and followup action. Then updates values.
	 */
	public void compareHistorical() {
		hist.compareHistorical(usageAmt, inputUnit, name, rate);
	}

	// ================================================================================
	// Accessor methods
	// ================================================================================

	public String getName() {
		return name;
	}

	// ================================================================================
	// Mutator methods
	// ================================================================================


	public void setName(String name) {
		this.name = name;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getUsageAmt() {
		return usageAmt;
	}

	public String getUsageUnit() {
		return usageUnit;
	}

	public void setUsageUnit(String unit) {
		this.usageUnit = unit;
	}

	public String getInputUnit() {
		return inputUnit;
	}

	public void setInputUnit(String inputUnit) {
		this.inputUnit = inputUnit;
	}

	public void setInputAmt(double input) {
		this.inputAmt = input;
	}

	public void setTips(Tips tips) {
		this.tips = tips;
	}

	public void setAvg(AverageUsage avg) {
		this.avg = avg;
	}
}
