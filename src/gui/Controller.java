package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.util.Random;

public class Controller {

	@FXML
	private Text result;

	@FXML
	private void handleButtonAction(ActionEvent event) {
		String message = generateRandomMessage();
		result.setText(message);
	}

	private String generateRandomMessage() {
		Random generator = new Random();
		int random = generator.nextInt(5);
		String result = "";
		switch (random) {
			case 0:
				result = "You suck.";
				break;
			case 1:
				result = "You are okay.";
				break;
			case 2:
				result = "You're cool.";
				break;
			case 3:
				result = "You're awesome.";
				break;
			case 4:
				result = "You're the best!";
				break;
		}
		return result;
	}
}