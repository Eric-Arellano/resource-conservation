package test;

import features.AverageUsage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AverageUsageTest {
	private AverageUsage sampleAverage;

	@Before
	public void setupAverageUsage() {
		sampleAverage = new AverageUsage(20); // avg usage 20 (rate 2)
	}

	@Test
	public void compareAverage_LessThanAverage() {
		setupAverageUsage();
		String returnedMessage = sampleAverage.compareAverage(10, "min", "test", 2); // usage 10, rate 2
		String expectedMessage = "You used 10 less min than the average user of 20 min! That's 50% " +
				"less than the average user.\nKeep it up!";
		assertEquals(expectedMessage, returnedMessage);
	}

	@Test
	public void compareAverage_EqualToAverage() {
		setupAverageUsage();
		String returnedMessage = sampleAverage.compareAverage(20, "min", "test", 2); // usage 20, rate 2
		String expectedMessage = "You used 0 more min than the average user of 20 min! That's 0% " +
				"more than the average user.\nKeep it up!";
		assertEquals(expectedMessage, returnedMessage);
	}

	@Test
	public void compareAverage_GreaterThanAverage() {
		setupAverageUsage();
		String returnedMessage = sampleAverage.compareAverage(40, "min", "test", 2); // usage 40, rate 2
		String expectedMessage = "You used 20 more min than the average user of 20 min! That's 100% " +
				"more than the average user.\nYou would need to use the test 10 fewer min to get to the" +
				" average usage.";
		assertEquals(expectedMessage, returnedMessage);
	}

}