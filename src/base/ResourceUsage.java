package base;

import features.AverageGlobalUsage;
import features.HistoricalUsage;
import features.Tips;

import java.text.DecimalFormat;
import java.util.Scanner;

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
	              double globalAverage_InInputUnit,
	              double... historicalUsages_InInputUnit) {
		this.resourceName = resourceName;
		this.rate = rate_UsagePerInput;
		this.inputUnit = inputUnit;
		this.usageUnit = usageUnit;
		this.tips = new Tips(tipsFilePath);
		this.globalAverage = new AverageGlobalUsage(resourceName,
				rate_UsagePerInput,
				inputUnit,
				usageUnit,
				globalAverage_InInputUnit);
		this.historical = new HistoricalUsage(resourceName, rate_UsagePerInput, inputUnit, usageUnit,
				historicalUsages_InInputUnit);
	}


	// ================================================================================
	// Base usage
	// ================================================================================

	abstract public String promptInput();

	public void setUsageFromInput() {
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
	// Getters & Setters
	// ================================================================================

	String getResourceName() {
		return resourceName;
	}

	String getInputUnit() {
		return inputUnit;
	}

	void setInputUnit(String inputUnit) {
		this.inputUnit = inputUnit;
	}

	void setInputAmount(double inputAmount) {
		this.inputAmount = inputAmount;
	}

}