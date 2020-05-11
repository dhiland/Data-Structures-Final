package aDSFinal;

import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * View of the Chat Noir Game grid
 * 
 * @author Dominic Hiland
 * @author Josh Larson
 * @version 05/11/2020
 */
public class ChatNoirView extends VBox implements EventHandler<MouseEvent> {

	/**
	 * Coordinates of the center of the grid
	 */
	private final int[] CENTER = { 10, 5 };

	/**
	 * 2D ArrayList of Rectangles, Stores the Viewable game grid
	 */
	private ArrayList<ArrayList<Rectangle>> blocks;

	/**
	 * ArrayList of HBox objects used to display the game grid properly
	 */
	private ArrayList<HBox> hBoxes;

	/**
	 * Object for the model of the current game
	 */
	private ChatNoirModel gameModel;

	/**
	 * Reference to the mainline of the program
	 */
	private Main mainReference;

	/**
	 * Constructor
	 * 
	 * @param gameModel Model for the current game
	 */
	public ChatNoirView(Main mainReference, ChatNoirModel gameModel) {
		this.gameModel = gameModel;
		this.mainReference = mainReference;
		this.hBoxes = new ArrayList<>();
		initializeBlocks();
		for (int i = 0; i < hBoxes.size(); i++) {
			this.getChildren().add(hBoxes.get(i));
		}
	}

	/**
	 * Initializes game grid to a state where no blocks contain walls and the cat is
	 * in the center
	 * 
	 * @Contains TODO -----> TODO Figure out loop to replace Switch-Case <------
	 */
	private void initializeBlocks() {
		// Initializing an empty 2D ArrayList for the Grid
		blocks = new ArrayList<>();
		for (int i = 0; i < 21; i++) {
			blocks.add(new ArrayList<Rectangle>());
		}

		// Filling the top cone of the grid
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < i + 1; j++) {
				blocks.get(i).add(createBlock(i, j));
			}
		}

		// Filling the center row of the grid
		for (int i = 0; i < 11; i++)
			blocks.get(10).add(createBlock(10, i));

		// Filling the bottom cone of the grid
		// -----> TODO Figure out loop to replace Switch-Case <------
		for (int i = 11; i < 21; i++) {
			for (int j = 0; j < 21 - i; j++) {
				if (j == 0 || j == 21 - i)
					blocks.get(i).add(createBlock(i, j));
				else
					blocks.get(i).add(createBlock(i, j));
			}
		}
		addBlocksToScene();
	}

	/**
	 * Creates a Rectangle object based on the corresponding block in the game model
	 * 
	 * @return the block created
	 */
	private Rectangle createBlock(int xCoord, int yCoord) {
		Rectangle tempRectangle = new Rectangle();
		if (gameModel.getBlocks().get(xCoord).get(yCoord).containsWall()
				|| gameModel.getBlocks().get(xCoord).get(yCoord).containsCat()) {
			if (gameModel.getBlocks().get(xCoord).get(yCoord).containsWall())
				tempRectangle.setFill(Color.GRAY);
			else
				tempRectangle.setFill(Color.BLACK);
		} else {
			tempRectangle.setFill(Color.WHITE);
		}
		tempRectangle.setHeight(30);
		tempRectangle.setWidth(40);
		tempRectangle.setOnMouseClicked(this);
		return tempRectangle;
	}

	/**
	 * Adds all blocks to the scene
	 */
	private void addBlocksToScene() {
		for (int i = 0; i < blocks.size(); i++) {
			hBoxes.add(createCenteredHBox());
			for (int j = 0; j < blocks.get(i).size(); j++) {
				hBoxes.get(hBoxes.size() - 1).getChildren().add(formatBlockForScene(blocks.get(i).get(j)));
			}
		}
	}

	/**
	 * Generates a properly styled block to add to the scene
	 * 
	 * @param blockToFormat the block that is currently being styled
	 * @return a properly styled block that is ready to be added to the scene
	 */
	private GridPane formatBlockForScene(Rectangle blockToFormat) {
		GridPane tempGridPane = new GridPane();
		Rectangle backgroundRectangle = new Rectangle();
		backgroundRectangle.setFill(Color.RED);
		backgroundRectangle.setHeight(35);
		backgroundRectangle.setWidth(45);

		tempGridPane.add(backgroundRectangle, 0, 0);
		tempGridPane.add(blockToFormat, 0, 0);
		tempGridPane.setHalignment(blockToFormat, HPos.CENTER);
		tempGridPane.setValignment(blockToFormat, VPos.CENTER);
		return tempGridPane;
	}

	/**
	 * Generates a centered, empty HBox object to store a series of blocks
	 * 
	 * @return a centered, empty HBox object
	 */
	private HBox createCenteredHBox() {
		HBox tempHBox = new HBox();
		tempHBox.setAlignment(Pos.CENTER);
		return tempHBox;
	}

	/**
	 * Method that creates alerts
	 * 
	 * @param type    The type of alert
	 * @param title   The title of the alert
	 * @param message The message of the alert
	 * @return info The actual alert of the specified type
	 */
	public Alert createAlert(AlertType type, String title, String message) {
		Alert info = new Alert(type);
		info.setTitle(title);
		info.setContentText(message);
		info.showAndWait();
		return info;

	}

	@Override
	public void handle(MouseEvent e) {
		for (int i = 0; i < blocks.size(); i++) {
			for (int j = 0; j < blocks.get(i).size(); j++) {
				if (e.getSource() == blocks.get(i).get(j)) {

					if (gameModel.getCatTurn() == true) {
						try {
							if (gameModel.getBlocks().get(i).get(j).containsWall() == true)
								throw new Exception("Cannot move cat to wall");

						} catch (Exception catToWall) {
							createAlert(AlertType.ERROR, "Error", catToWall.getMessage());
							continue;

						}
						try {
							if (gameModel.getBlocks().get(i).get(j).containsCat() == true)
								throw new Exception("Cat is already on that block");

						} catch (Exception catToCat) {
							createAlert(AlertType.ERROR, "Error", catToCat.getMessage());
							continue;

						}
						try {
							gameModel.moveCat(gameModel.getBlocks().get(i).get(j));
							gameModel.switchStates();
							mainReference.redraw();
						} catch (Exception catToAdjacent) {
							createAlert(AlertType.ERROR, "Error", "Cat must be moved to an adjacent block");
							continue;
						}
					} else {
						try {
							if (gameModel.getBlocks().get(i).get(j).containsCat() == true)
								throw new Exception("Cannot put wall on Cat's current position");
						} catch (Exception wallToCat) {
							createAlert(AlertType.ERROR, "Error", wallToCat.getMessage());
							continue;
						}
						try {
							gameModel.getBlocks().get(i).get(j).setContainsWall(true);
							gameModel.switchStates();
							mainReference.redraw();
						} catch (Exception AlreadyWall) {
							createAlert(AlertType.ERROR, "Error", "There is already a wall there");
						}
					}
				}

			}
		}
	}
}
