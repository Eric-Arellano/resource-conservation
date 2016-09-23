package features;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Tips { // TODO: make catch statements more useful

	private File tipsFile;

	/**
	 * Constructs an object with given name of relative path to file.
	 */
	public Tips(String filePath) {
		File file = new File(filePath);
		try {
			file.createNewFile();
		} catch (IOException fileCreationError) {
			System.out.println("File could not be created correctly.");
		}
		this.tipsFile = file;
	}

	// ================================================================================
	// Show Tips
	// ================================================================================

	public String displayTips() {
		String tips = "";
		try (Scanner read = new Scanner(this.tipsFile)) {
			while (read.hasNextLine()) {
				tips += read.nextLine();
				tips += "\n"; // for line breaks
			}
		} catch (FileNotFoundException fileNotFound) {
			System.out.println("Oops! File not found. Please make sure file was set up properly");
		}
		return tips;
	}

}
