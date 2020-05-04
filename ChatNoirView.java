package application;

import java.util.ArrayList;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * View of the Chat Noir Game grid
 * 
 * @author Dominic Hiland
 * @version 05/04/2020
 * 
 * TODO: Add mouse listener to boxes for clicking functionality
 */
public class ChatNoirView extends VBox {

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
	 * Constructor
	 * 
	 * @param gameModel Model for the current game
	 */
	public ChatNoirView(ChatNoirModel gameModel) {
		this.gameModel = gameModel;
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
		for (int i = 11; i < 21; i++) {
			// -----> TODO Figure out loop to replace Switch-Case <------
			switch (i) {
			case 11:
				for (int j = 0; j < 10; j++)
					blocks.get(i).add(createBlock(11, j));
				break;
			case 12:
				for (int j = 0; j < 9; j++)
					blocks.get(i).add(createBlock(12, j));
				break;
			case 13:
				for (int j = 0; j < 8; j++)
					blocks.get(i).add(createBlock(13, j));
				break;
			case 14:
				for (int j = 0; j < 7; j++)
					blocks.get(i).add(createBlock(14, j));
				break;
			case 15:
				for (int j = 0; j < 6; j++)
					blocks.get(i).add(createBlock(15, j));
				break;
			case 16:
				for (int j = 0; j < 5; j++)
					blocks.get(i).add(createBlock(16, j));
				break;
			case 17:
				for (int j = 0; j < 4; j++)
					blocks.get(i).add(createBlock(17, j));
				break;
			case 18:
				for (int j = 0; j < 3; j++)
					blocks.get(i).add(createBlock(18, j));
				break;
			case 19:
				for (int j = 0; j < 2; j++)
					blocks.get(i).add(createBlock(19, j));
				break;
			default:
				blocks.get(i).add(createBlock(20, 0));
				break;
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
}
