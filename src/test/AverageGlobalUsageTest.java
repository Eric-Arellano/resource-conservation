package test;

import features.AverageGlobalUsage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AverageGlobalUsageTest {
	private AverageGlobalUsage sampleAverage;

	@Before
	public void setupAverageUsage() {
		sampleAverage = new AverageGlobalUsage("shower",
				2,
				"gal",
				"min",
				20); // avg usage 20 (rate 2 gpm)
	}

	@Test
	public void compareAverage_LessThanAverage() {
		setupAverageUsage();
		String returnedMessage = sampleAverage.compareGlobalAverage(10); // usage 10
		String expectedMessage = "You used 10 less gal than the average use of 20 gal! That's 50% " +
				"less than the average use.\nKeep it up!";
		assertEquals(expectedMessage, returnedMessage);
	}

	@Test
	public void compareAverage_EqualToAverage() {
		setupAverageUsage();
		String returnedMessage = sampleAverage.compareGlobalAverage(20); // usage 20,
		String expectedMessage = "You used 0 more gal than the average use of 20 gal! That's 0% " +
				"more than the average use.\nKeep it up!";
		assertEquals(expectedMessage, returnedMessage);
	}

	@Test
	public void compareAverage_GreaterThanAverage() {
		setupAverageUsage();
		String returnedMessage = sampleAverage.compareGlobalAverage(40); // usage 40
		String expectedMessage = "You used 20 more gal than the average use of 20 gal! That's 100% " +
				"more than the average use.\nYou would need to use the shower 10 fewer min to get to the" +
				" average usage.";
		assertEquals(expectedMessage, returnedMessage);
	}

}