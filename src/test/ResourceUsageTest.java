package test;

import base.ResourceUsage;
import base.UsageDiscreteQuantity;
import base.UsageTimeDuration;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

// TODO: Redesign ResourceUsage classes to allow for testing, currently using sout & input prompts
@RunWith(Enclosed.class)
public class ResourceUsageTest {

	public static class DiscreteQuantity {

		private ResourceUsage quantityUsage;

		@Before
		public void setupMockResources() {
			quantityUsage = new UsageDiscreteQuantity("washing machine",
					25,
					"gallons",
					"times per month",
					"src/test/mockTips.txt",
					4,
					3, 6, 5, 5);
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

		private ResourceUsage secondUsage;

		@Before
		public void setupMockResources() {
			secondUsage = new UsageTimeDuration("sink",
					1.5,
					"gallons",
					"seconds",
					"src/test/mockTips.txt",
					0.35,
					0.2, 0.6, 0.5, 0.9);
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

		private ResourceUsage minuteUsage;

		@Before
		public void setupMockResources() {
			minuteUsage = new UsageTimeDuration("shower",
					2.5,
					"gallons",
					"minutes",
					"src/test/mockTips.txt",
					10.6,
					8.3, 9.2, 12.4, 18.1);
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

		private ResourceUsage timeBothUsage;

		@Before
		public void setupMockResources() {
			timeBothUsage = new UsageTimeDuration("shower",
					2.5,
					"gallons",
					"both",
					"src/test/mockTips.txt",
					10.6,
					8.3, 9.2, 12.4, 18.1);
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
