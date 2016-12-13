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
			System.err.println(getErrorMessage(exception));
		}
	}

	public String displayTips() {
		try {
			return getTipsFromFile();
		} catch (InvalidPathException | IOException exception) {
			return getErrorMessage(exception);
		}
	}

	// TODO: figure out support for new lines
	private String getTipsFromFile() throws InvalidPathException, IOException {
		StringBuilder tips = new StringBuilder();
		BufferedReader reader = Files.newBufferedReader(tipsFile, StandardCharsets.UTF_8);
		String line;
		while ((line = reader.readLine()) != null) {
			tips.append(line);
		}
		reader.close();
		return tips.toString();
	}

	private String getErrorMessage(Exception exception) {
		return exception.getMessage();
	}

}
