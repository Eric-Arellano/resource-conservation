package test;

import features.AverageGlobalUsage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AverageGlobalUsageTest {
	private AverageGlobalUsage sampleAverage;

	@BeforeEach
	public void setupAverageUsage() {
		sampleAverage = new AverageGlobalUsage("shower",
				2, // rate 2 gpm
				"min",
				"gal",
				10); // avg usage 20 gal (10 min)
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