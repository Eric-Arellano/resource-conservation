package features;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Tips {

	private Path tipsFile;

	public Tips(String filePath) {
		try {
			tipsFile = Paths.get(filePath);
		} catch (InvalidPathException exception) {
			System.err.println("File could not be located. Please make sure it exists.");
		}
	}

	// TODO: figure out support for new lines
	public String displayTips() {
		StringBuilder tips = new StringBuilder();
		try (BufferedReader reader = Files.newBufferedReader(tipsFile, StandardCharsets.UTF_8)) {
			String line;
			while ((line = reader.readLine()) != null) {
				tips.append(line);
			}
		} catch (IOException readerNotCreated) {
			System.err.println("Oops! There was an issue reading that file. Please make sure it exists " +
					"and is accesible.");
		} finally {
			return tips.toString();
		}
	}

}
