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
	// Unique methods
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
	 * Method to show user how their usage compares to the global average. This method prints
	 * 1) whether the user used more or less than average, 2) the absolute difference, 3) the percent
	 * difference, and 4) if usage is greater than average, the amount of inputs that would need
	 * to be reduced to get to the average.
	 */
	public void compareAverage() {
		double absoluteDiff = Math.abs(usageAmt - avg.getAvg());

		if (avg.isGreater(usageAmt)) {
			System.out.println("You used " + fmt.format(absoluteDiff) + " more " + usageUnit +
					" than the average. " + "That's " + percent.format(avg.percentDiff(usageAmt))
					+ " more.");
			System.out.println("You would need to use the " + name + " " +
					fmt.format(calcInputChange(absoluteDiff)) + " fewer " +
					inputUnit + " to get to the average.");
		} else {
			System.out.println("You used " + fmt.format(absoluteDiff) + " fewer " + usageUnit +
					" than the average. " + "That's " + percent.format(avg.percentDiff(usageAmt))
					+ " less.");
		}
	}

	/**
	 * Method to show user how their usage compares to their historical usage. This method first
	 * assesses how the usage compares to the historical min and max, then to the historical average.
	 * There are four potential outputs printed, depending on where the usage falls in the above
	 * categories. Each output prints whether the usage was greater or lesser than the comparison value,
	 * the absolute and percent differences, and for 3/4 outputs the input change needed. The historical
	 * usage's array's min, max, and avg values are then updated.
	 */
//	public void compareHistorical() {
//		double absoluteDiffMax = Math.abs(usageAmt - hist.getMaxVal());
//		double absoluteDiffAvg = Math.abs(usageAmt - hist.getAvg());
//		double absoluteDiffMin = Math.abs(usageAmt - hist.getMinVal());
//
//		if (hist.isGreaterMax(usageAmt)) {
//
//			System.out.println("You used " + fmt.format(absoluteDiffMax) + " more " + usageUnit +
//					" than your previous max usage of " + fmt.format(hist.getMaxVal()) +
//					" " + usageUnit + "! " + "That's " +
//					percent.format(HistoricalUsage.percentDiff(usageAmt, hist.getMaxVal())) + " more.");
//			System.out.println("You would need to use the " + name + " " +
//					fmt.format(calcInputChange(absoluteDiffAvg)) + " fewer " +
//					inputUnit + " to get to your average usage.");
//		} else if (hist.isLessMin(usageAmt)) {
//			System.out.println("You used " + fmt.format(absoluteDiffMin) + " less " + usageUnit +
//					" than your previous lowest record of " + fmt.format(hist.getMinVal()) +
//					" " + usageUnit + "! " + "That's " +
//					percent.format(HistoricalUsage.percentDiff(usageAmt, hist.getMinVal())) + " less.");
//			System.out.println("Keep it up!");
//		} else // within bounds of min and max
//		{
//			if (hist.isGreaterAvg(usageAmt)) {
//				System.out.println("You used " + fmt.format(absoluteDiffAvg) + " more " + usageUnit +
//						" than your average of " + fmt.format(hist.getAvg()) +
//						" " + usageUnit + ". " + "That's " +
//						percent.format(HistoricalUsage.percentDiff(usageAmt, hist.getAvg())) + " more.");
//				System.out.println("You would need to use the " + name + " " +
//						fmt.format(calcInputChange(absoluteDiffAvg)) + " fewer " +
//						inputUnit + " to get to your average usage.");
//			} else {
//				System.out.println("You used " + fmt.format(absoluteDiffAvg) + " less " + usageUnit +
//						" than your average of " + fmt.format(hist.getAvg()) +
//						" " + usageUnit + ". " + "That's " +
//						percent.format(HistoricalUsage.percentDiff(usageAmt, hist.getAvg())) + " less.");
//				System.out.println("You would need to use the " + name + " " +
//						fmt.format(calcInputChange(absoluteDiffMin)) + " fewer " +
//						inputUnit + " to beat your lowest record.");
//			}
//		}
//
//		hist.updateValues();
//	}

	abstract public double calcInputChange(double usageToChange);

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
