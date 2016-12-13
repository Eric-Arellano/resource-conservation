import base.ResourceUsage;
import base.UsageDiscreteQuantity;
import base.UsageTimeDuration;
import userInterfaces.ConsoleApp;

public class WaterASUDorm_Console {

	public static void main(String[] args) {

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
		String welcomeMessage = "This app helps you keep track of your water " +
				"consumption in your ASU dorm. After choosing from several water usages, you'll input your " +
				"usage and then can compare it to your historical usage or the average on college campuses.";

		ConsoleApp console = new ConsoleApp(welcomeMessage, shower, sink, washer);
		console.launchConsoleApp();

	}

}