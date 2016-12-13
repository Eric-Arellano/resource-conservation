package userInterfaces;

import base.ResourceUsage;

import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

public class ConsoleApp {

	private final String welcomeMessage;
	private List<ResourceUsage> usages;
	private ResourceUsage chosenUsage;

	public ConsoleApp(String welcomeMessage, ResourceUsage... resourceUsages) {
		this.welcomeMessage = welcomeMessage;
		this.usages = new LinkedList<>();
		for (ResourceUsage usage : resourceUsages) {
			this.usages.add(usage);
		}
	}

	public void launchConsoleApp() {
		welcomeUser();
		selectResource();
		getInput();
		renderUsage();
		selectAndImplementFollowup();
	}

	//----------------

	private void welcomeUser() {
		System.out.println(welcomeMessage);
	}

	//----------------

	private void selectResource() {
		promptResourceSelection();
		listenToResourceSelection();
	}

	private void promptResourceSelection() {
		System.out.println("\nWhich of the following did you use? Choose one. \nEnter the number of " +
				"the menu item you'd like and then press enter (\"0\" to quit).\n");
		System.out.println(getResourceOptions());
	}

	private String getResourceOptions() {
		int resourceCount = 0;
		StringJoiner usageOptions = new StringJoiner("\n\t" + resourceCount++ + ") ");
		for (ResourceUsage usage : usages) {
			usageOptions.add(usage.toString());
		}
		return usageOptions.toString();
	}

	private void listenToResourceSelection() {

	}

	//-------------

	private void getInput() {
		promptInput();
		listenToInput();
	}

	private void promptInput() {

	}

	private void listenToInput() {

	}

	//------------

	private void renderUsage() {
		if (chosenUsage.isInputAmountPresent()) ;
		{
			chosenUsage.setUsageFromInput();
			System.out.println(chosenUsage.returnUsage());
		}
	}

	//------------

	private void selectAndImplementFollowup() {
		promptFollowupOptions();
		FollowupOption followupSelection = listenToFollowupSelection();
		implementFollowupSelection(followupSelection);
	}

	private void promptFollowupOptions() {

	}

	private FollowupOption listenToFollowupSelection() {
		return null;
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
				getInput();
				break;
			case NEW_USAGE:
				selectResource();
				break;
		}
	}

	private enum FollowupOption {
		COMPARE_GLOBAL_AVERAGE, COMPARE_HISTORICAL, DISPLAY_HISTORICAL, DISPLAY_TIPS, NEW_VALUE,
		NEW_USAGE
	}

}
