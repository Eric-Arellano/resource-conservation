package userInterfaces;

import base.ResourceUsage;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

import static userInterfaces.UserInputHelper.getValidDoubleInput;
import static userInterfaces.UserInputHelper.getValidIntInput;

public final class ConsoleApp {

	private final String welcomeMessage;
	private final List<ResourceUsage> usages;
	private ResourceUsage chosenUsage;

	private boolean quitProgram;
	private boolean changeUsage;


	public ConsoleApp(String welcomeMessage, ResourceUsage... resourceUsages) {
		this.welcomeMessage = welcomeMessage;
		this.usages = new LinkedList<>();
		Collections.addAll(this.usages, resourceUsages);
		this.quitProgram = false;
	}

	public void launchConsoleApp() {
		welcomeUser();
		do {
			selectResource();
			getInputAmount();
			renderUsage();
			do {
				changeUsage = false; // reset value
				selectAndImplementFollowup();
			} while (!changeUsage && !quitProgram);
		} while (!quitProgram);
		closeApp();
	}

	private void closeApp() {
		System.out.println("This program has closed.");
		System.exit(0);
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
		System.out.println(returnResourceOptions());
	}

	private String returnResourceOptions() {
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
			closeApp();
		}
		final int INDEX_ADJUSTMENT = -1;
		chosenUsage = usages.get(numericSelection + INDEX_ADJUSTMENT);
	}


	// ================================================================================
	// Get initial input & render usage
	// ================================================================================

	private void getInputAmount() {
		System.out.println(chosenUsage.promptInput());
		double inputAmount = listenToInputAmount();
		chosenUsage.implementInput(inputAmount);
	}

	private double listenToInputAmount() {
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
				"\t\t\t4) get tips for conserving" +
				"\n\t2) compare to my historical usage" +
				"\t\t\t5) enter a new value" +
				"\n\t3) display my prior historical usage" +
				"\t\t6) change the thing I used"
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
				getInputAmount();
				renderUsage();
				break;
			case NEW_USAGE:
				chosenUsage.updateHistoricalBeforeNewInput();
				changeUsage = true;
				break;
			case QUIT:
				quitProgram = true;
				closeApp();
				break;
		}
	}

	private enum FollowupOption {
		COMPARE_GLOBAL_AVERAGE,
		COMPARE_HISTORICAL,
		DISPLAY_HISTORICAL,
		DISPLAY_TIPS,
		NEW_VALUE,
		NEW_USAGE,
		QUIT;

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

}
