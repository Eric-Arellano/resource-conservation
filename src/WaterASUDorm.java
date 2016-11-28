import base.ResourceUsage;
import base.UsageDuration;
import base.UsageNumTimes;
import features.AverageUsage;
import features.HistoricalUsage;
import features.Tips;

import java.util.Scanner;


public class WaterASUDorm {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		// ================================================================================
		// Programmer creates several ResourceUsage objects
		// ================================================================================

		// Shower object
		// rate = 2.5 gpm, avg = 10.6 min
		Tips showerTips = new Tips("showerTips.txt");
		AverageUsage showerAvg = new AverageUsage("shower", 2.5, "gallons", "minutes", 10.6);
		HistoricalUsage showerHist = new HistoricalUsage("shower", 2.5, "gallons", "minutes",
				8.3, 9.2, 12.4, 18.1);
		UsageDuration shower = new UsageDuration("shower", 2.5, "gallons", "minutes", showerTips,
				showerAvg, showerHist);

		// Sink object
		// rate = 1.5 gpm, avg = 0.35 minutes
		Tips sinkTips = new Tips("sinkTips.txt");
		AverageUsage sinkAvg = new AverageUsage("sink", 1.5, "gallons", "seconds", 0.35);
		HistoricalUsage sinkHist = new HistoricalUsage("sink", 1.5, "gallons", "seconds",
				0.2, 0.6, 0.5, 0.9);
		UsageDuration sink = new UsageDuration("sink", 1.5, "gallons", "seconds", sinkTips,
				sinkAvg, sinkHist);

		// Washing Machine object
		// rate = 25 gal per wash, input unit = times per month, avg = 4 washes per month
		Tips washerTips = new Tips("washerTips.txt");
		AverageUsage washerAvg = new AverageUsage("washing machine", 25, "gallons", "times per " +
				"month", 4);
		HistoricalUsage washerHist = new HistoricalUsage("washing machine", 25, "gallons", "times per" +
				" month", 3, 6, 5, 5);
		UsageNumTimes washer = new UsageNumTimes("washing machine", 25, "gallons", washerTips,
				washerAvg, washerHist);
		washer.setInputUnit("times per month");


		// ================================================================================
		// Introduce program to users
		// ================================================================================
		System.out.println(welcomeMessage);


		// ================================================================================
		// Set up variables for menus
		// ================================================================================
		boolean quitProgram = false;
		boolean changeUsage;
		ResourceUsage chosenUsage = shower; // arbitrary initialization

		do {
			// ================================================================================
			// User selects which ResourceUsage
			// ================================================================================

			printChooseUsage();
			int usageSelection = in.nextInt();
			switch (usageSelection) {
				case 1:
					chosenUsage = shower;
					break;
				case 2:
					chosenUsage = sink;
					break;
				case 3:
					chosenUsage = washer;
					break;
				case 0:
					quitProgram = true;
					break;
				default: // TODO: add tolerability
					System.out.print("Invalid input. Please restart the program.");
					break;
			}

			if (!quitProgram) {

				// ================================================================================
				// Initial usage input
				// ================================================================================

				System.out.println();
				chosenUsage.promptInput();

				chosenUsage.calcUsageFromInput();
				chosenUsage.displayUsage();


				// ================================================================================
				// User selects follow-up Menu items
				// ================================================================================

				do {
					changeUsage = false; // reset boolean
					printUsageMenu();

					int menuSelection = in.nextInt();
					System.out.println();
					switch (menuSelection) {
						case 1:
							chosenUsage.compareAverage();
							System.out.println();
							break;
						case 2:
							chosenUsage.compareHistorical();
							break;
						case 3:
							chosenUsage.displayHistorical();
							break;
						case 4:
							chosenUsage.displayTips();
							break;
						case 5:
							chosenUsage.promptInput();
							chosenUsage.calcUsageFromInput();
							chosenUsage.displayUsage();
							break;
						case 6:
							changeUsage = true;
							break;
						case 0:
							quitProgram = true;
							break;
						default: // TODO: add tolerability
							System.out.print("Invalid input. Please restart the program.");
							break;
					}
				} while (!changeUsage && !quitProgram);

			}

		} while (!quitProgram);

		System.out.println("\nThis program has quit.");

		in.close();

	}

	// ================================================================================
	// Menu messages
	// ================================================================================

	private static String welcomeMessage = "This app helps you keep track of your water consumption" +
			" in your ASU dorm. After choosing from several water usages, you'll input your usage and " +
			"then can compare it to your historical usage or the average on college campuses.";

	private static void printChooseUsage() {
		System.out.println("\nWhich of the following did you use? Choose one. \nEnter the number of " +
				"the menu item you'd like and then press enter (\"0\" to quit)." + "\n\n\t1. the shower"
				+ "\t\t3. the washing machine" + "\n\t2. the sink"
		);
	}

	private static void printUsageMenu() {
		System.out.println("\nWhat would you like to do now? \nEnter the number of " +
				"the menu item you'd like and then press enter (\"0\" to quit)." +
				"\n\n\t1. compare to the average on campus" +
				"\t\t4. get tips for conserving" +
				"\n\t2. compare to my historical usage" +
				"\t\t5. enter a new value" +
				"\n\t3. display my historical usage" +
				"\t\t\t6. change the thing I used"
		);

	}

}