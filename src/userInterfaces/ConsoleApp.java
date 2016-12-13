package userInterfaces;

import base.ResourceUsage;

import java.util.*;

public class ConsoleApp {

	private final String welcomeMessage;
	private List<ResourceUsage> usages;
	private ResourceUsage chosenUsage;

	private boolean quitProgram;
	private boolean changeUsage;
	private final Scanner scanner = new Scanner(System.in);

	public ConsoleApp(String welcomeMessage, ResourceUsage... resourceUsages) {
		this.welcomeMessage = welcomeMessage;
		this.usages = new LinkedList<>();
		for (ResourceUsage usage : resourceUsages) {
			this.usages.add(usage);
		}
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
		int resourceSelection = -1;
		try {
			resourceSelection = scanner.nextInt();
			// TODO: add better error handling. Repeats error message twice with non-ints.
		} catch (InputMismatchException invalidInputException) {
			System.out.println("Oops! Please enter an integer between 0-" + usages.size() + ".");
			System.out.println(scanner.next() + " was not valid input.");
		}
		return resourceSelection;
	}

	private void implementResourceSelection(int numericSelection) {
		if (numericSelection == 0) {
			quitProgram = true;
		} else if (isValidResourceSelection(numericSelection)) {
			final int INDEX_ADJUSTMENT = -1;
			chosenUsage = usages.get(numericSelection + INDEX_ADJUSTMENT);
		} else {
			System.out.println("Oops! Please enter an integer between 0-" + usages.size() + ".");
			int newResourceSelection = listenToResourceSelection();
			implementResourceSelection(newResourceSelection);
		}
	}

	private boolean isValidResourceSelection(int numericSelection) {
		return numericSelection >= 0 && numericSelection <= usages.size();
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
		double input = 0.0;
		try {
			input = scanner.nextDouble();
			// TODO: add better error handling. Currently program keeps going.
		} catch (InputMismatchException invalidInput) {
			System.out.println("Oops! Please enter a valid numeric input greater than 0.0.");
			System.out.println(scanner.next() + " was not valid input.");
		}
		return input;
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
		int followupSelection = -1;
		try {
			followupSelection = scanner.nextInt();
			// TODO: add better error handling. Repeats error message twice with non-ints.
		} catch (InputMismatchException invalidInputException) {
			System.out.println("Oops! Please enter an integer between 0-6.");
			System.out.println(scanner.next() + " was not valid input.");
		}
		return FollowupOption.translateIntToOption(followupSelection);
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
				selectResource();
				getInput();
				renderUsage();
				break;
			case QUIT:
				quitProgram = true;
				break;
			case INVALID_INPUT:
				System.out.println("Oops! Please enter an integer between 0-6.");
				FollowupOption newSelection = listenToFollowupSelection();
				implementFollowupSelection(newSelection);
				break;
		}
	}

	private enum FollowupOption {
		COMPARE_GLOBAL_AVERAGE, COMPARE_HISTORICAL, DISPLAY_HISTORICAL, DISPLAY_TIPS, NEW_VALUE,
		NEW_USAGE, QUIT, INVALID_INPUT;

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
					return INVALID_INPUT;
			}
		}
	}

}
