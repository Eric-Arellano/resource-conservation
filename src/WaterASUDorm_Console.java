import base.ResourceUsage;
import base.UsageDiscreteQuantity;
import base.UsageTimeDuration;
import userInterfaces.ConsoleApp;

import java.util.Scanner;


public class WaterASUDorm_Console {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		// ================================================================================
		// Programmer creates several ResourceUsage objects
		// ================================================================================

		// Shower
		// rate = 2.5 gpm, avg = 10.6 min, hist = 8.3 min, 9.2 min, 12.4 min, 18.1 min
		ResourceUsage shower = new UsageTimeDuration("shower",
				2.5,
				UsageTimeDuration.TimeType.MINUTES,
				"gallons",
				"src/tip-files/showerTips.txt",
				10.6,
				8.3, 9.2, 12.4, 18.1);

		// Sink
		// rate = 1.5 gpm, avg = 0.35 minutes, hist = 0.2 min, 0.6 min, 0.5 min, 0.9 min
		ResourceUsage sink = new UsageTimeDuration("sink",
				1.5,
				UsageTimeDuration.TimeType.SECONDS,
				"gallons",
				"src/tip-files/sinkTips.txt",
				0.35,
				0.2, 0.6, 0.5, 0.9);

		// Washing Machine
		// rate = 25 gal per wash, avg = 4 washes per month, hist = 3 washes, 6 washes, 5 washes, 5
		// washes
		ResourceUsage washer = new UsageDiscreteQuantity("washing machine",
				25,
				"washes per month",
				"gallons",
				"src/tip-files/washerTips.txt",
				4,
				3, 6, 5, 5);


		// ================================================================================
		// Launch console view
		// ================================================================================
		ConsoleApp console = new ConsoleApp(welcomeMessage, shower, sink, washer);
		console.launchConsoleApp();

	}

	private static final String welcomeMessage = "This app helps you keep track of your water " +
			"consumption in your ASU dorm. After choosing from several water usages, you'll input your " +
			"usage and then can compare it to your historical usage or the average on college campuses.";

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

//		// ================================================================================
//		// Introduce program to users
//		// ================================================================================
//		System.out.println(welcomeMessage);
//
//
//		// ================================================================================
//		// Set up variables for menus
//		// ================================================================================
//		boolean quitProgram = false;
//		boolean changeUsage;
//		ResourceUsage chosenUsage = shower; // arbitrary initialization
//
//		do {
//			// ================================================================================
//			// User selects which ResourceUsage
//			// ================================================================================
//
//			printChooseUsage();
//			int usageSelection = in.nextInt();
//			switch (usageSelection) {
//				case 1:
//					chosenUsage = shower;
//					break;
//				case 2:
//					chosenUsage = sink;
//					break;
//				case 3:
//					chosenUsage = washer;
//					break;
//				case 0:
//					quitProgram = true;
//					break;
//				default: // TODO: add tolerability
//					System.out.print("Invalid input. Please restart the program.");
//					break;
//			}
//
//			if (!quitProgram) {
//
//				// ================================================================================
//				// Initial usage input
//				// ================================================================================
//
//				System.out.println();
//				chosenUsage.promptInput();
//
//				chosenUsage.setUsageFromInput();
//				chosenUsage.returnUsage();
//
//
//				// ================================================================================
//				// User selects follow-up Menu items
//				// ================================================================================
//
//				do {
//					changeUsage = false; // reset boolean
//					printUsageMenu();
//
//					int menuSelection = in.nextInt();
//					System.out.println();
//					switch (menuSelection) {
//						case 1:
//							chosenUsage.returnComparisonToGlobalAverage();
//							System.out.println();
//							break;
//						case 2:
//							chosenUsage.returnComparisonToHistorical();
//							break;
//						case 3:
//							chosenUsage.returnHistoricalUsages();
//							break;
//						case 4:
//							chosenUsage.returnTips();
//							break;
//						case 5:
//							chosenUsage.updateHistoricalBeforeNewInput();
//							chosenUsage.promptInput();
//							chosenUsage.setUsageFromInput();
//							chosenUsage.returnUsage();
//							break;
//						case 6:
//							chosenUsage.updateHistoricalBeforeNewInput();
//							changeUsage = true;
//							break;
//						case 0:
//							chosenUsage.updateHistoricalBeforeNewInput();
//							quitProgram = true;
//							break;
//						default: // TODO: add tolerability
//							System.out.print("Invalid input. Please restart the program.");
//							break;
//					}
//				} while (!changeUsage && !quitProgram);
//
//			}
//
//		} while (!quitProgram);
//
//		System.out.println("\nThis program has quit.");
//
//		in.close();
//

	// ================================================================================
	// Menu messages
	// ================================================================================


}