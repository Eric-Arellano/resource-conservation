package test;

import base.ResourceUsage;
import base.UsageDiscreteQuantity;
import base.UsageTimeDuration;
import features.AverageUsage;
import features.HistoricalUsage;
import features.Tips;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

// TODO: Redesign ResourceUsage classes to allow for testing, currently using sout & input prompts
@RunWith(Enclosed.class)
public class ResourceUsageTest {

	public static class DiscreteQuantity {

		private Tips mockTips;
		private AverageUsage mockAverage;
		private HistoricalUsage mockHistorical;
		private ResourceUsage quantityUsage;

		@Before
		public void setupMockResources() {
			mockTips = new Tips("washerTips.txt");
			mockAverage = new AverageUsage("washing machine", 25, "gallons", "times per month", 4);
			mockHistorical = new HistoricalUsage("washing machine", 25, "gallons", "times per month",
					3, 6, 5, 5);
			quantityUsage = new UsageDiscreteQuantity("washing machine", 25, "gallons", "times per " +
					"month", mockTips, mockAverage, mockHistorical);
		}

		@Test
		public void promptDiscreteInput() {
			String returnedMessage = "";
			quantityUsage.promptInput();
			String expectedMessage = "How many times per month did you use the washing machine? ";
			assertEquals(expectedMessage, returnedMessage);
		}

		@Test
		public void displayDiscreteUsage() {
			String returnedMessage = "";
			quantityUsage.displayUsage();
			String expectedMessage = "That means you used 15 gallons.";
			assertEquals(expectedMessage, returnedMessage);
		}
	}


	public static class TimeDurationSeconds {

		private Tips mockTips;
		private AverageUsage mockAverage;
		private HistoricalUsage mockHistorical;
		private ResourceUsage secondUsage;

		@Before
		public void setupMockResources() {
			mockTips = new Tips("showerTips.txt");
			mockAverage = new AverageUsage("sink", 1.5, "gallons", "seconds", 0.35);
			mockHistorical = new HistoricalUsage("sink", 1.5, "gallons", "seconds",
					0.2, 0.6, 0.5, 0.9);
			secondUsage = new UsageTimeDuration("sink", 1.5, "gallons", "seconds", mockTips,
					mockAverage, mockHistorical);
		}

		@Test
		public void promptTimeInput_Seconds() {
			String returnedMessage = "";
			secondUsage.promptInput();
			String expectedMessage = "How many seconds did you use the sink? ";
			assertEquals(expectedMessage, returnedMessage);
		}

		@Test
		public void displayTimeUsage_Seconds() {
			String returnedMessage = "";
			secondUsage.displayUsage();
			String expectedMessage = "That means you used 10 gallons.";
			assertEquals(expectedMessage, returnedMessage);
		}
	}

	public static class TimeDurationMinutes {

		private Tips mockTips;
		private AverageUsage mockAverage;
		private HistoricalUsage mockHistorical;
		private ResourceUsage minuteUsage;

		@Before
		public void setupMockResources() {
			mockTips = new Tips("showerTips.txt");
			mockAverage = new AverageUsage("shower", 2.5, "gallons", "minutes", 10.6);
			mockHistorical = new HistoricalUsage("shower", 2.5, "gallons", "minutes",
					8.3, 9.2, 12.4, 18.1);
			minuteUsage = new UsageTimeDuration("shower", 2.5, "gallons", "minutes", mockTips,
					mockAverage, mockHistorical);
		}

		@Test
		public void promptTimeInput_Minutes() {
			String returnedMessage = "";
			minuteUsage.promptInput();
			String expectedMessage = "How many minutes did you use the shower? ";
			assertEquals(expectedMessage, returnedMessage);
		}

		@Test
		public void displayTimeUsage_Minutes() {
			String returnedMessage = "";
			minuteUsage.displayUsage();
			String expectedMessage = "That means you used 10 gallons.";
			assertEquals(expectedMessage, returnedMessage);
		}
	}

	public static class TimeDurationBoth {

		private Tips mockTips;
		private AverageUsage mockAverage;
		private HistoricalUsage mockHistorical;
		private ResourceUsage timeBothUsage;

		@Before
		public void setupMockResources() {
			mockTips = new Tips("showerTips.txt");
			mockAverage = new AverageUsage("shower", 2.5, "gallons", "both", 10.6);
			mockHistorical = new HistoricalUsage("shower", 2.5, "gallons", "both",
					8.3, 9.2, 12.4, 18.1);
			timeBothUsage = new UsageTimeDuration("shower", 2.5, "gallons", "both", mockTips,
					mockAverage, mockHistorical);
		}

		@Test
		public void promptTimeInput_Both() {
			String returnedMessage = "";
			timeBothUsage.promptInput();
			String expectedMessage = "How many times per month did you use the shower? (You'll" +
					" be asked about seconds after): ";
			expectedMessage += "How many seconds did you use the shower? ";
			assertEquals(expectedMessage, returnedMessage);
		}

		@Test
		public void displayTimeUsage_Both() {
			String returnedMessage = "";
			timeBothUsage.displayUsage();
			String expectedMessage = "That means you used 10 gallons.";
			assertEquals(expectedMessage, returnedMessage);
		}
	}

}
