package test;

import features.HistoricalUsage;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(Enclosed.class)
public class HistoricalUsageTest {

	public static class NoPriorData {

		@Test
		public void compareHistorical_NoData() {
			HistoricalUsage sampleHistorical = new HistoricalUsage("shower", 2, "gal", "min");
			String returnedMessage = sampleHistorical.compareHistorical(30);
			String expectedMessage = "Oops! Looks like there's no historical data to compare to.";
			assertEquals(expectedMessage, returnedMessage);
		}
	}

	public static class OnePriorDatum {
		private HistoricalUsage sampleHistorical;

		@Before
		public void setUpOnePriorDatum() throws Exception {
			sampleHistorical = new HistoricalUsage("shower", 2, "gal", "min");
			sampleHistorical.preFillData(2, 10); // rate 2; input 10 min (usage 20)
		}

		@Test
		public void compareHistorical_OneDatum_Less() {
			String returnedMessage = sampleHistorical.compareHistorical(10);
			String expectedMessage = "You used 10 less gal than your prior usage of 20 gal! That's 50% " +
					"less than your prior usage.\nKeep it up!";
			assertEquals(expectedMessage, returnedMessage);
		}

		@Test
		public void compareHistorical_OneDatum_Equal() {
			String returnedMessage = sampleHistorical.compareHistorical(20);
			String expectedMessage = "You used 0 more gal than your prior usage of 20 gal! That's 0% " +
					"more than your prior usage.\nKeep it up!";
			assertEquals(expectedMessage, returnedMessage);
		}

		@Test
		public void compareHistorical_OneDatum_More() {
			String returnedMessage = sampleHistorical.compareHistorical(30);
			String expectedMessage = "You used 10 more gal than your prior usage of 20 gal! That's 50% " +
					"more than your prior usage.\nYou would need to use the shower 5 fewer min to get to " +
					"your prior usage.";
			assertEquals(expectedMessage, returnedMessage);
		}
	}

	public static class PriorData {
		private HistoricalUsage sampleHistorical;

		@Before
		public void setUpPriorData() throws Exception {
			sampleHistorical = new HistoricalUsage("shower", 2, "gal", "min");
			sampleHistorical.preFillData(2, 30, 20, 10); // rate 2; input 30 min (usage 60), 20 min
			// (usage 40), 10 min (usage 20); avg 20 min (avg usage 40)
		}


		@Test
		public void compareHistorical_LessMin() {
			String returnedMessage = sampleHistorical.compareHistorical(10);
			String expectedMessage = "You used 10 less gal than your min of 20 gal! That's 50% " +
					"less than your min.\nKeep it up!";
			assertEquals(expectedMessage, returnedMessage);
		}

		@Test
		public void compareHistorical_LessAvg_GreaterOrEqualToMin() {
			String returnedMessage = sampleHistorical.compareHistorical(30);
			String expectedMessage = "You used 10 less gal than your average of 40 gal! That's 25% " +
					"less than your average.\nYou would need to use the shower 5 fewer min to beat your " +
					"lowest record.";
			assertEquals(expectedMessage, returnedMessage);
		}

		@Test
		public void compareHistorical_EqualAvg() {
			String returnedMessage = sampleHistorical.compareHistorical(40);
			String expectedMessage = "You used 0 more gal than your average of 40 gal! That's 0% " +
					"more than your average.\nYou would need to use the shower 0 fewer min to beat your " +
					"lowest record.";
			assertEquals(expectedMessage, returnedMessage);
		}

		@Test
		public void compareHistorical_GreaterAvg_LessOrEqualToMax() {
			String returnedMessage = sampleHistorical.compareHistorical(50);
			String expectedMessage = "You used 10 more gal than your average of 40 gal! That's 25% " +
					"more than your average.\nYou would need to use the shower 5 fewer min to get to your " +
					"average usage.";
			assertEquals(expectedMessage, returnedMessage);
		}

		@Test
		public void compareHistorical_GreaterMax() {
			String returnedMessage = sampleHistorical.compareHistorical(80);
			String expectedMessage = "You used 20 more gal than your max of 60 gal! That's 25% " +
					"more than your max.\nYou would need to use the shower 20 fewer min to get to your " +
					"average usage.";
			assertEquals(expectedMessage, returnedMessage);
		}
	}
}