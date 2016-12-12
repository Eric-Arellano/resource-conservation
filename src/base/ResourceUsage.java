package base;

import features.AverageGlobalUsage;
import features.HistoricalUsage;
import features.Tips;

import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * Main program class. Used to create and interface with different resource consumption types.
 *
 * @author ericarellano
 */
abstract public class ResourceUsage {

	private final String resourceName;
	private final double rate;
	private String inputUnit; // what user interfaces with, e.g. minutes
	private final String usageUnit; // what is reported back, e.g. gallons
	private double inputAmount;
	private double usageAmount;
	private final Tips tips;
	private final AverageGlobalUsage globalAverage;
	private final HistoricalUsage historical;

	private final DecimalFormat decimals = new DecimalFormat("0.##");
	Scanner in = new Scanner(System.in);

	ResourceUsage(String resourceName,
	              double rate,
	              String usageUnit,
	              String inputUnit,
	              String tipsFilePath,
	              double globalAverageInUsageUnit,
	              double... historicalUsagesInInputUnit) {
		this.resourceName = resourceName;
		this.rate = rate;
		this.usageUnit = usageUnit;
		this.inputUnit = inputUnit;
		this.tips = new Tips(tipsFilePath);
		this.globalAverage = new AverageGlobalUsage(resourceName, rate, usageUnit, inputUnit,
				globalAverageInUsageUnit);
		this.historical = new HistoricalUsage(resourceName, rate, usageUnit, inputUnit,
				historicalUsagesInInputUnit);
	}


	// ================================================================================
	// Base usage
	// ================================================================================

	abstract public String promptInput();

	public void calcUsageFromInput() {
		usageAmount = inputAmount * rate;
	}

	public String displayUsage() {
		String usage = "That means you used " + decimals.format(usageAmount) + " " + usageUnit + ".";
		System.out.println(usage);
		return usage;
	}


	// ================================================================================
	// Feature usage
	// ================================================================================

	public String displayTips() {
		String tipsText = tips.displayTips();
		System.out.println(tipsText);
		return tipsText;
	}

	public String displayHistoricalUsages() {
		String historicalUsage = historical.displayHistorical();
		System.out.println(historicalUsage);
		return historicalUsage;
	}

	/**
	 * Compares to global globalAverage usage. Prints comparison and followup action.
	 */
	public String compareToGlobalAverage() {
		String averageComparison = globalAverage.compareGlobalAverage(usageAmount);
		System.out.println(averageComparison);
		return averageComparison;
	}

	/**
	 * Compares to historical min, max, and globalAverage values. Prints comparison to relevant value and
	 * followup action.
	 */
	public String compareToHistorical() {
		String historicalComparison = historical.compareHistorical(usageAmount);
		System.out.println(historicalComparison);
		return historicalComparison;
	}

	public void updateHistoricalBeforeNewInput() {
		historical.addHistorical(usageAmount);
	}

	// ================================================================================
	// Getter methods
	// ================================================================================

	String getResourceName() {
		return resourceName;
	}

	String getInputUnit() {
		return inputUnit;
	}

	// ================================================================================
	// Setter methods
	// ================================================================================

	void setInputUnit(String inputUnit) {
		this.inputUnit = inputUnit;
	}

	void setInputAmount(double inputAmount) {
		this.inputAmount = inputAmount;
	}

}