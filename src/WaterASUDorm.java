import base.ResourceUsage;
import base.UsageDuration;
import base.UsageNumTimes;
import features.AverageUsage;
import features.HistoricalUsage;
import features.Tips;

import java.io.IOException;
import java.util.Scanner;


public class WaterASUDorm {

	public static void main(String[] args) throws IOException {
		Scanner in = new Scanner(System.in);

		// ================================================================================
		// Programmer creates several ResourceUsage objects
		// ================================================================================

		// Shower object
		Tips showerTips = new Tips("showerTips.txt");
		AverageUsage showerAvg = new AverageUsage(2.5, 10.6); // rate = 2.5 gpm, avg 10.6 minutes
		HistoricalUsage showerHist = new HistoricalUsage(10);
		showerHist.preFill(2.5, 8.3, 9.2, 12.4, 18.1); // rate = 2.5 gpm
		UsageDuration shower = new UsageDuration("shower", 2.5, "gallons", "minutes", showerTips,
				showerAvg, showerHist);

		// Sink object
		Tips sinkTips = new Tips("sinkTips.txt");
		AverageUsage sinkAvg = new AverageUsage(1.5, 0.35); // rate = 1.5 gpm, avg 0.35 minutes
		HistoricalUsage sinkHist = new HistoricalUsage(10);
		sinkHist.preFill(1.5, 0.2, 0.6, 0.5, 0.9); // time inputted in minutes
		UsageDuration sink = new UsageDuration("sink", 1.5, "gallons", "seconds", sinkTips,
				sinkAvg, sinkHist);

		// Washing Machine object
		Tips washerTips = new Tips("washerTips.txt");
		AverageUsage washerAvg = new AverageUsage(25, 4); // 25 gal per wash, avg 4 washes per month
		HistoricalUsage washerHist = new HistoricalUsage(10);
		washerHist.preFill(25, 3, 6, 5, 5);
		UsageNumTimes washer = new UsageNumTimes("washing machine", 25, "gallons", washerTips,
				washerAvg, washerHist);
		washer.setInputUnit("times per month");


		// ================================================================================
		// Introduce program to users
		// ================================================================================
		System.out.println(welcomeMessage);


		// Set up variables for menu
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
				default: // add tolerability
					System.out.print("Invalid input. Please restart the program.");
					break;
			}

			if (!quitProgram) // including this conditional so that below doesn't
			// implement if user
			// quits program in above switch statement
			{

				// ================================================================================
				// Initial usage input
				// ================================================================================

				System.out.println();
				chosenUsage.promptInput();
				chosenUsage.calcUsage();
				chosenUsage.displayUsage();


				// ================================================================================
				// User selects follow-up Menu items
				// ================================================================================

				do {
					changeUsage = false; // to reset boolean after quitUsage has been declared already
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
							chosenUsage.displayTips();
							break;
						case 4:
							chosenUsage.promptInput();
							chosenUsage.calcUsage();
							chosenUsage.displayUsage();
							break;
						case 5:
							changeUsage = true;
							break;
						case 0:
							quitProgram = true;
							break;
						default: // add tolerability
							System.out.print("Invalid input. Please restart the program.");
							break;
					}
				} while (!changeUsage && !quitProgram);

			} // close if loop to allow quitting program

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
				+ "\t\t3. the washing machine" + "\n\t2. the sink");
	}

	private static void printUsageMenu() {
		System.out.println("\nWhat would you like to do now? \nEnter the number of " +
				"the menu item you'd like and then press enter (\"0\" to quit)." +
				"\n\n\t1. compare to the average on campus" +
				"\t\t4. enter a new value" +
				"\n\t2. compare to my historical usage" + "\t\t5. change the thing I used"
				+ "\n\t3. get tips for conserving");

	}

}