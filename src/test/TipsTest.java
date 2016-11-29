package test;

import features.Tips;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TipsTest {

	// TODO: This fails, error not thrown
	@Test(expected = IOException.class)
	public void accessBlankFile() throws IOException {
		new Tips("src/test/garbage.txt").displayTips();
	}

	@Test
	public void displayTips() {
		Tips sampleTips = new Tips("src/test/mockTips.txt");
		String returnedMessage = sampleTips.displayTips();
		String expectedMessage = "This is a mock of the Tips class. It should work.";
		assertEquals(expectedMessage, returnedMessage);
	}


}