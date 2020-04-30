package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application implements EventHandler<ActionEvent> {

	/**
	 * root of the scene
	 */
	private VBox root;

	/**
	 * Button to reset the game
	 */
	private Button resetBtn;

	/**
	 * Label to give the user feedbaack about the status of the game
	 */
	private Label feedbackL;

	@Override
	public void start(Stage PrimaryStage) {
		try {
			root = new VBox();
			root.setSpacing(20);
			Scene scene = new Scene(root, 530, 820);

			// Initializing view objects
			resetBtn = new Button("Reset");
			resetBtn.setOnAction(this);
			feedbackL = new Label("Lorem ipsum dolor sit amet, consectetur adipiscing elit."); // TODO write
																								// getFeedback() method
																								// in Model and fill
																								// label

			// Adding view objects to scene
			root.getChildren().add(resetBtn);
			root.getChildren().add(new GridView());
			root.getChildren().add(feedbackL);

			PrimaryStage.setScene(scene);
			PrimaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void handle(ActionEvent e) {
		if (e.getSource() == resetBtn)
			System.out.println(); // TODO write resetGame() method
	}

}
