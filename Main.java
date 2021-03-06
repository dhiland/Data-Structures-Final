package aDSFinal;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Main class of the Chat Noir Game
 * 
 * @author Dominic Hiland
 * @author Josh Larson
 * @version 05/20/2020
 */
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
	 * Label to give the user feedback about the status of the game
	 */
	private Label feedbackL;

	/**
	 * Model for the current game
	 */
	private ChatNoirModel gameModel;

	/**
	 * View for the game, covers the Grid
	 */
	private ChatNoirView gameView;

	/**
	 * Method responsible for launching the gameboard
	 */
	@Override
	public void start(Stage PrimaryStage) {
		try {
			root = new VBox();
			root.setSpacing(20);
			Scene scene = new Scene(root, 530, 820);

			// Initializing view objects
			gameModel = new ChatNoirModel();

			resetBtn = new Button("Reset");
			resetBtn.setOnAction(this);
			feedbackL = new Label(gameModel.getFeedback());

			// Adding view objects to scene
			root.getChildren().add(resetBtn);
			gameView = new ChatNoirView(this, gameModel);
			root.getChildren().add(gameView);
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

	/**
	 * Method that handles when the user clicks the reset button
	 */
	@Override
	public void handle(ActionEvent e) {
		if (e.getSource() == resetBtn) {
			gameModel = new ChatNoirModel();
			root.getChildren().clear();
			root.getChildren().add(resetBtn);
			gameModel.pcs.removePropertyChangeListener(gameView);
			gameView = new ChatNoirView(this, gameModel);
			root.getChildren().add(gameView);
			feedbackL = new Label(gameModel.getFeedback());
			root.getChildren().add(feedbackL);
		}
	}

	/**
	 * Redraws the view of the game after every update
	 */
	public void redraw() {
		root.getChildren().clear();
		root.getChildren().add(resetBtn);
		gameModel.pcs.removePropertyChangeListener(gameView);
		gameView = new ChatNoirView(this, gameModel);
		root.getChildren().add(gameView);
		feedbackL = new Label(gameModel.getFeedback());
		root.getChildren().add(feedbackL);
	}
}