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
	private String inputUnit; // unit user interfaces with, e.g. minutes
	private final String usageUnit; // unit reported back, e.g. gallons
	private double inputAmount;
	private double usageAmount;
	private final Tips tips;
	private final AverageGlobalUsage globalAverage;
	private final HistoricalUsage historical;

	private final DecimalFormat decimals = new DecimalFormat("0.##");
	Scanner in = new Scanner(System.in);

	ResourceUsage(String resourceName,
	              double rate_UsagePerInput,
	              String inputUnit,
	              String usageUnit,
	              String tipsFilePath,
	              double globalAverageInInputUnit,
	              double... historicalUsagesInInputUnit) {
		this.resourceName = resourceName;
		this.rate = rate_UsagePerInput;
		this.inputUnit = inputUnit;
		this.usageUnit = usageUnit;
		this.tips = new Tips(tipsFilePath);
		this.globalAverage = new AverageGlobalUsage(resourceName,
				rate_UsagePerInput,
				inputUnit,
				usageUnit,
				globalAverageInInputUnit);
		this.historical = new HistoricalUsage(resourceName, rate_UsagePerInput, inputUnit, usageUnit,
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

	public String compareToGlobalAverage() {
		String averageComparison = globalAverage.compareGlobalAverage(usageAmount);
		System.out.println(averageComparison);
		return averageComparison;
	}

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