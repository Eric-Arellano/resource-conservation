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

	public String returnUsage() {
		return "That means you used " + decimals.format(usageAmount) + " " + usageUnit + ".";
	}


	// ================================================================================
	// Feature usage
	// ================================================================================

	public String returnTips() {
		return tips.displayTips();
	}

	public String returnHistoricalUsages() {
		return historical.displayHistorical();
	}

	public String returnComparisonToGlobalAverage() {
		return globalAverage.compareGlobalAverage(usageAmount);
	}

	public String returnComparisonToHistorical() {
		return historical.compareHistorical(usageAmount);
	}

	public void updateHistoricalBeforeNewInput() {
		historical.addHistorical(usageAmount);
	}

	// ================================================================================
	// Getters & Setters
	// ================================================================================

	public String getResourceName() {
		return resourceName;
	}

	public String getInputUnit() {
		return inputUnit;
	}

	public boolean isInputAmountPresent() {
		return inputAmount != 0;
	}

	void setInputAmount(double inputAmount) {
		this.inputAmount = inputAmount;
	}

}