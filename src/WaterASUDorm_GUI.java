import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WaterASUDorm_GUI extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("userInterfaces/GuiModel.fxml"));
		primaryStage.setTitle("ASU Dorm Water Usage");
		primaryStage.setScene(new Scene(root, 600, 1000));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
