package test;

import features.Tips;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TipsTest {

	@Test(expected = IOException.class)
	public void accessBlankFile() throws IOException {
		Tips sampleTips = new Tips("src/test/garbage.txt");
	}


	@Test
	public void displayTips() {
		Tips sampleTips = new Tips("src/test/mockTips.txt");
		String returnedMessage = sampleTips.displayTips();
		String expectedMessage = "This is a mock of the Tips class.\n\nIt should work.\n";
		assertEquals(expectedMessage, returnedMessage);
	}


}