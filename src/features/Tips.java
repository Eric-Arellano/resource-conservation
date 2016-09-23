package features;

import java.io.*;
import java.util.Scanner;

public class Tips { // TODO: make catch statements more useful

	private File tipsFile;

	// ================================================================================
	// Constructors
	// ================================================================================

	/**
	 * Constructs an object with given File.
	 */
	public Tips(File fileName) {
		try {
			fileName.createNewFile();
		} catch (IOException fileCreationError) {
			System.out.println("File could not be created correctly.");
		}
		this.tipsFile = fileName;
	}

	/**
	 * Constructs an object with given name of relative path to file.
	 */
	public Tips(String fileName) {
		File file = new File(fileName);
		try {
			file.createNewFile();
		} catch (IOException fileCreationError) {
			System.out.println("File could not be created correctly.");
		}
		this.tipsFile = file;
	}

	// ================================================================================
	// Change Tips
	// ================================================================================

	public void rewriteTips(String rewrittenTips) throws IOException {

		PrintWriter rewrite = new PrintWriter(new FileWriter(tipsFile, false)); // false means it will rewrite
		rewrite.println(rewrittenTips);
		rewrite.close();
	}

	public void addTips(String newTips) throws IOException {
		PrintWriter add = new PrintWriter(new FileWriter(tipsFile, true)); // true means it will append
		add.println(newTips);
		add.close();
	}

	// ================================================================================
	// Show Tips
	// ================================================================================

	public String displayTips() {
		String tips = "";
		try (Scanner read = new Scanner(this.tipsFile)) {
			while (read.hasNextLine()) {
				tips += read.nextLine();
				tips += "\n"; // to account for line breaks
			}
		} catch (FileNotFoundException fileNotFound) {
			System.out.println("Oops! File not found. Please make sure file was set up properly");
		}
		return tips;
	}

}
