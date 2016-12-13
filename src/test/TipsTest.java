package test;

import features.Tips;
import org.junit.jupiter.api.Test;

import java.nio.file.InvalidPathException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TipsTest {

	// TODO: This fails, error not thrown
	@Test
	public void accessBlankFile() {
		assertThrows(InvalidPathException.class, () -> new Tips("src/test/garbage.txt"));
	}

	@Test
	public void displayTips() {
		Tips sampleTips = new Tips("src/test/mockTips.txt");
		String returnedMessage = sampleTips.displayTips();
		String expectedMessage = "This is a mock of the Tips class. It should work.";
		assertEquals(expectedMessage, returnedMessage);
	}


}