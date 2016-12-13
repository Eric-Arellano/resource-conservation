package test;

import features.Tips;
import org.junit.jupiter.api.Test;

import java.nio.file.InvalidPathException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TipsTest {

	// TODO: This fails, error not thrown
	@Test
	void accessBlankFile() {
		assertThrows(InvalidPathException.class, () -> new Tips("src/test/garbage.txt"));
	}

	@Test
	void displayTips() {
		Tips sampleTips = new Tips("src/test/mockTips.txt");
		String returnedMessage = sampleTips.displayTips();
		String expectedMessage = "This is a mock of the Tips class. It should work.";
		assertEquals(expectedMessage, returnedMessage);
	}


}