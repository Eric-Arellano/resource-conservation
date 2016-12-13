package gui;

import base.ResourceUsage;

import java.util.List;

public class ConsoleView {

	private final String welcomeMessage;
	private List<ResourceUsage> usages;
	private ResourceUsage chosenUsage;

	public ConsoleView(String welcomeMessage, ResourceUsage... resourceUsages) {
		this.welcomeMessage = welcomeMessage;
		for (ResourceUsage usage : resourceUsages) {
			usages.add(usage);
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
		chosenUsage.setUsageFromInput();
		chosenUsage.displayUsage();
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
				chosenUsage.compareToGlobalAverage();
				break;
			case COMPARE_HISTORICAL:
				chosenUsage.compareToHistorical();
				break;
			case DISPLAY_HISTORICAL:
				chosenUsage.displayHistoricalUsages();
				break;
			case DISPLAY_TIPS:
				chosenUsage.displayTips();
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
