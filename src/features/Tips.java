package features;


import java.io.*;
import java.util.Scanner;


public class Tips {

	private File tipsFile;

	// ================================================================================
	// Constructors
	// ================================================================================

	/**
	 * Constructs an object with given File.
	 *
	 * @param fileName
	 * @throws FileNotFoundException
	 */
	public Tips(File fileName) throws FileNotFoundException, IOException {
		fileName.createNewFile(); // in case file doesn't already exist
		this.tipsFile = fileName;
	}

	/**
	 * Constructs an object with given name of relative path to file.
	 *
	 * @param fileName
	 * @throws FileNotFoundException
	 */
	public Tips(String fileName) throws FileNotFoundException, IOException {
		File file = new File(fileName);
		file.createNewFile(); // in case file doesn't already exist
		this.tipsFile = file;
	}

	// ================================================================================
	// Mutator Methods
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
	// Accesor Methods
	// ================================================================================

	public String displayTips() throws FileNotFoundException {
		String tips = "";
		// File readFile = new File("showerTips.txt");
		Scanner read = new Scanner(this.tipsFile);
		while (read.hasNextLine()) {
			tips += read.nextLine();
			tips += "\n"; // to account for line breaks
		}
		read.close();
		return tips;
	}

}
