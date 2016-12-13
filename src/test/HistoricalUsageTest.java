package test;

import features.HistoricalUsage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Historical usage")
class HistoricalUsageTest {

	@Nested
	@DisplayName("no prior data")
	class NoPriorData {
		private HistoricalUsage sampleHistorical;

		@BeforeEach
		void setUpNoPriorData() {
			sampleHistorical = new HistoricalUsage("shower", 2, "min", "gal");
		}

		@Test
		@DisplayName("display historical")
		void displayHistorical_NoData() {
			String returnedMessage = sampleHistorical.displayHistorical();
			String expectedMessage = "Historical usage:\t";
			assertEquals(expectedMessage, returnedMessage);
		}

		@Test
		@DisplayName("add usage")
		void addHistorical_NoData() {
			sampleHistorical.addHistorical(20);
			String expectedMessage = "Historical usage:\t20 gal";
			String returnedMessage = sampleHistorical.displayHistorical();
			assertEquals(expectedMessage, returnedMessage);
			assertEquals(20, sampleHistorical.getAverage(), 0.5);
			assertEquals(20, sampleHistorical.getMinVal(), 0.5);
			assertEquals(20, sampleHistorical.getMaxVal(), 0.5);
		}

		@Test
		@DisplayName("compare to historical")
		void compareHistorical_NoData() {
			String returnedMessage = sampleHistorical.compareHistorical(30);
			String expectedMessage = "Oops! Looks like there's no historical data to compare to.";
			assertEquals(expectedMessage, returnedMessage);
		}
	}

	@Nested
	@DisplayName("one prior datum")
	class OnePriorDatum {
		private HistoricalUsage sampleHistorical;

		@BeforeEach
		void setUpOnePriorDatum() throws Exception {
			sampleHistorical = new HistoricalUsage("shower", 2, "min", "gal", 10); // rate 2; input 10
			// min (usage 20)
		}

		@Test
		@DisplayName("display historical")
		void displayHistorical_OneDatum() {
			String returnedMessage = sampleHistorical.displayHistorical();
			String expectedMessage = "Historical usage:\t20 gal";
			assertEquals(expectedMessage, returnedMessage);
		}

		@Test
		@DisplayName("compare - less than historical")
		void compareHistorical_OneDatum_Less() {
			String returnedMessage = sampleHistorical.compareHistorical(10);
			String expectedMessage = "You used 10 less gal than your prior usage of 20 gal! That's 50% " +
					"less than your prior usage.\nKeep it up!";
			assertEquals(expectedMessage, returnedMessage);
		}

		@Test
		@DisplayName("compare - equal to historical")
		void compareHistorical_OneDatum_Equal() {
			String returnedMessage = sampleHistorical.compareHistorical(20);
			String expectedMessage = "You used 0 more gal than your prior usage of 20 gal! That's 0% " +
					"more than your prior usage.\nYou would need to use the shower 0 fewer min to beat " +
					"your lowest record.";
			assertEquals(expectedMessage, returnedMessage);
		}

		@Test
		@DisplayName("compare - more than historical")
		void compareHistorical_OneDatum_More() {
			String returnedMessage = sampleHistorical.compareHistorical(30);
			String expectedMessage = "You used 10 more gal than your prior usage of 20 gal! That's 50% " +
					"more than your prior usage.\nYou would need to use the shower 5 fewer min to get to " +
					"your prior usage.";
			assertEquals(expectedMessage, returnedMessage);
		}
	}

	@Nested
	@DisplayName("prior data")
	class PriorData {
		private HistoricalUsage sampleHistorical;

		@BeforeEach
		void setUpPriorData() throws Exception {
			sampleHistorical = new HistoricalUsage("shower", 2, "min", "gal", 30, 20, 10); // rate 2;
			// input 30 min (usage 60), 20 min (usage 40), 10 min (usage 20); avg 20 min (avg usage 40)
		}

		@Test
		@DisplayName("display historical")
		void displayHistorical_PriorData() {
			String returnedMessage = sampleHistorical.displayHistorical();
			String expectedMessage = "Historical usage:\t60 gal, 40 gal, 20 gal";
			assertEquals(expectedMessage, returnedMessage);
		}

		@Test
		@DisplayName("add usage")
		void addHistorical_PriorData() {
			sampleHistorical.addHistorical(100);
			String expectedMessage = "Historical usage:\t60 gal, 40 gal, 20 gal, 100 gal";
			String returnedMessage = sampleHistorical.displayHistorical();
			assertEquals(expectedMessage, returnedMessage);
			assertEquals(((60 + 40 + 20 + 100) / 4), sampleHistorical.getAverage(), 0.5);
			assertEquals(20, sampleHistorical.getMinVal(), 0.5);
			assertEquals(100, sampleHistorical.getMaxVal(), 0.5);
		}

		@Test
		@DisplayName("compare - less than min")
		void compareHistorical_LessMin() {
			String returnedMessage = sampleHistorical.compareHistorical(10);
			String expectedMessage = "You used 10 less gal than your min of 20 gal! That's 50% " +
					"less than your min.\nKeep it up!";
			assertEquals(expectedMessage, returnedMessage);
		}

		@Test
		@DisplayName("compare - greater than min")
		void compareHistorical_LessAvg_GreaterOrEqualToMin() {
			String returnedMessage = sampleHistorical.compareHistorical(30);
			String expectedMessage = "You used 10 less gal than your average of 40 gal! That's 25% " +
					"less than your average.\nYou would need to use the shower 5 fewer min to beat your " +
					"lowest record.";
			assertEquals(expectedMessage, returnedMessage);
		}

		@Test
		@DisplayName("compare - equal to average")
		void compareHistorical_EqualAvg() {
			String returnedMessage = sampleHistorical.compareHistorical(40);
			String expectedMessage = "You used 0 more gal than your average of 40 gal! That's 0% " +
					"more than your average.\nYou would need to use the shower 0 fewer min to beat your " +
					"lowest record.";
			assertEquals(expectedMessage, returnedMessage);
		}

		@Test
		@DisplayName("compare - greater than average, less than max")
		void compareHistorical_GreaterAvg_LessOrEqualToMax() {
			String returnedMessage = sampleHistorical.compareHistorical(50);
			String expectedMessage = "You used 10 more gal than your average of 40 gal! That's 25% " +
					"more than your average.\nYou would need to use the shower 5 fewer min to get to your " +
					"average usage.";
			assertEquals(expectedMessage, returnedMessage);
		}

		@Test
		@DisplayName("compare - greater than max")
		void compareHistorical_GreaterMax() {
			String returnedMessage = sampleHistorical.compareHistorical(80);
			String expectedMessage = "You used 20 more gal than your max of 60 gal! That's 33% " +
					"more than your max.\nYou would need to use the shower 10 fewer min to get to your " +
					"average usage.";
			assertEquals(expectedMessage, returnedMessage);
		}
	}
}