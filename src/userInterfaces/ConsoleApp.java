package userInterfaces;

import base.ResourceUsage;

import java.util.*;

public class ConsoleApp {

	private final String welcomeMessage;
	private final List<ResourceUsage> usages;
	private ResourceUsage chosenUsage;

	private boolean quitProgram;
	private boolean changeUsage;
	private final Scanner scanner = new Scanner(System.in);

	public ConsoleApp(String welcomeMessage, ResourceUsage... resourceUsages) {
		this.welcomeMessage = welcomeMessage;
		this.usages = new LinkedList<>();
		Collections.addAll(this.usages, resourceUsages);
		this.quitProgram = false;
		this.changeUsage = false;
	}

	public void launchConsoleApp() {
		welcomeUser();
		do {
			selectResource();
			getInput();
			renderUsage();
			do {
				selectAndImplementFollowup();
			} while (!changeUsage && !quitProgram);
		} while (!quitProgram);

		closeApp();
	}

	private void closeApp() {
		System.out.println("This program has closed.");
		scanner.close();
	}

	// ================================================================================
	// Welcome
	// ================================================================================

	private void welcomeUser() {
		System.out.println(welcomeMessage);
	}

	// ================================================================================
	// Select Resource
	// ================================================================================

	private void selectResource() {
		promptResourceSelection();
		int resourceSelection = listenToResourceSelection();
		implementResourceSelection(resourceSelection);
	}

	private void promptResourceSelection() {
		System.out.println("\nWhich of the following did you use? Choose one. \nEnter the number of " +
				"the menu item you'd like and then press enter (\"0\" to quit).\n");
		System.out.println(getResourceOptions());
	}

	private String getResourceOptions() {
		int resourceCount = 1;
		StringJoiner usageOptions = new StringJoiner("\n\t", "\t", "");
		for (ResourceUsage usage : usages) {
			String menuNumber = resourceCount++ + ") ";
			usageOptions.add(menuNumber + usage.getResourceName());
		}
		return usageOptions.toString();
	}

	private int listenToResourceSelection() {
		final int RANGE_LOWER_BOUND = 0;
		final int RANGE_UPPER_BOUND = usages.size();
		return getValidIntInput(RANGE_LOWER_BOUND, RANGE_UPPER_BOUND);
	}

	private void implementResourceSelection(int numericSelection) {
		if (numericSelection == 0) {
			quitProgram = true;
		}
		final int INDEX_ADJUSTMENT = -1;
		chosenUsage = usages.get(numericSelection + INDEX_ADJUSTMENT);
	}


	// ================================================================================
	// Get initial input & render usage
	// ================================================================================

	private void getInput() {
		System.out.println(chosenUsage.promptInput());
		double inputAmount = listenToInput();
		chosenUsage.implementInput(inputAmount);
	}

	private double listenToInput() {
		final double RANGE_LOWER_BOUND = 0.0;
		return getValidDoubleInput(RANGE_LOWER_BOUND);
	}

	private void renderUsage() {
		chosenUsage.setUsageFromInput();
		System.out.println(chosenUsage.returnUsage());
	}


	// ================================================================================
	// Followup options
	// ================================================================================

	private void selectAndImplementFollowup() {
		promptFollowupOptions();
		FollowupOption followupSelection = listenToFollowupSelection();
		implementFollowupSelection(followupSelection);
	}

	private void promptFollowupOptions() {
		System.out.println("\nWhat would you like to do now? \nEnter the number of the menu item " +
				"you'd  like and then press enter (\"0\" to quit).\n" +
				"\n\n\t1) compare to the average on campus" +
				"\t\t4) get tips for conserving" +
				"\n\t2) compare to my historical usage" +
				"\t\t5) enter a new value" +
				"\n\t3) display my historical usage" +
				"\t\t\t6) change the thing I used"
		);
	}

	private FollowupOption listenToFollowupSelection() {
		final int RANGE_LOWER_BOUND = 0;
		final int RANGE_UPPER_BOUND = 6;
		int numericSelection = getValidIntInput(RANGE_LOWER_BOUND, RANGE_UPPER_BOUND);
		return FollowupOption.translateIntToOption(numericSelection);
	}

	private void implementFollowupSelection(FollowupOption selection) {
		switch (selection) {
			case COMPARE_GLOBAL_AVERAGE:
				System.out.println(chosenUsage.returnComparisonToGlobalAverage());
				break;
			case COMPARE_HISTORICAL:
				System.out.println(chosenUsage.returnComparisonToHistorical());
				break;
			case DISPLAY_HISTORICAL:
				System.out.println(chosenUsage.returnHistoricalUsages());
				break;
			case DISPLAY_TIPS:
				System.out.println(chosenUsage.returnTips());
				break;
			case NEW_VALUE:
				chosenUsage.updateHistoricalBeforeNewInput();
				getInput();
				renderUsage();
				break;
			case NEW_USAGE:
				chosenUsage.updateHistoricalBeforeNewInput();
				// TODO: Analyze if this changeUsage boolean works correctly and is necessary
				changeUsage = true;
				selectResource();
				getInput();
				renderUsage();
				break;
			case QUIT:
				quitProgram = true;
				break;
		}
	}

	private enum FollowupOption {
		COMPARE_GLOBAL_AVERAGE, COMPARE_HISTORICAL, DISPLAY_HISTORICAL, DISPLAY_TIPS, NEW_VALUE,
		NEW_USAGE, QUIT;

		private static FollowupOption translateIntToOption(int numericOption) {
			switch (numericOption) {
				case 1:
					return COMPARE_GLOBAL_AVERAGE;
				case 2:
					return COMPARE_HISTORICAL;
				case 3:
					return DISPLAY_HISTORICAL;
				case 4:
					return DISPLAY_TIPS;
				case 5:
					return NEW_VALUE;
				case 6:
					return NEW_USAGE;
				case 0:
					return QUIT;
				default:
					return QUIT;
			}
		}
	}

	// ================================================================================
	// Input utilities
	// ================================================================================

	private int getValidIntInput(int RANGE_LOWER_BOUND, int RANGE_UPPER_BOUND) {
		int inputtedValue;
		try {
			if (scanner.hasNextInt()) {
				inputtedValue = scanner.nextInt();
				if (isNotValidRange(inputtedValue, RANGE_LOWER_BOUND, RANGE_UPPER_BOUND)) {
					throw new NumberFormatException("Out of range.");
				}
			} else {
				scanner.next();
				throw new InputMismatchException("Not int.");
			}
		} catch (NumberFormatException | InputMismatchException outOfRangeException) {
			System.out.println(returnOutOfBoundsMessage(RANGE_LOWER_BOUND, RANGE_UPPER_BOUND));
			inputtedValue = getValidIntInput(RANGE_LOWER_BOUND, RANGE_UPPER_BOUND);
		}
		return inputtedValue;
	}

	private double getValidDoubleInput(double RANGE_LOWER_BOUND) {
		double inputtedValue;
		try {
			if (scanner.hasNextDouble()) {
				inputtedValue = scanner.nextDouble();
				if (isNotValidRange(inputtedValue, RANGE_LOWER_BOUND)) {
					throw new NumberFormatException("Out of range.");
				}
			} else {
				scanner.next();
				throw new InputMismatchException("Not double.");
			}
		} catch (NumberFormatException | InputMismatchException outOfRangeException) {
			System.out.println(returnOutOfBoundsMessage(RANGE_LOWER_BOUND));
			inputtedValue = getValidDoubleInput(RANGE_LOWER_BOUND);
		}
		return inputtedValue;
	}


	// ================================================================================
	// Error handling support
	// ================================================================================

	private String returnOutOfBoundsMessage(double lowerBound) {
		return "Oops! Please enter a number greater than " + lowerBound + ".";
	}

	private String returnOutOfBoundsMessage(int lowerBound, int upperBound) {
		return "Oops! Please enter an integer between " + lowerBound + "-" + upperBound + ".";
	}

	private boolean isNotValidRange(int input, int lowerBound, int upperBound) {
		return input < lowerBound || input > upperBound;
	}

	private boolean isNotValidRange(double input, double lowerBound) {
		return input <= lowerBound;
	}

}
