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
 * View for the Grid of Blocks necessary for Chat Noir
 * 
 * @author Dominic Hiland
 * @version 04/29/2020
 */
public class GridView extends VBox { // TODO add contingency on game model for block generation <-------

	/**
	 * Stores the grid of blocks to be shown
	 */
	private ArrayList<Rectangle> blocks;

	/**
	 * series of HBoxes to store the blocks in rows
	 */
	private ArrayList<HBox> hBoxes;

	/**
	 * Constructor
	 */
	public GridView() {
		blocks = new ArrayList<>();
		hBoxes = new ArrayList<>();
		// Creating the top cone of the grid section
		for (int i = 0; i < 10; i++) {
			hBoxes.add(createCenteredHBox());
			for (int j = 0; j < i + 1; j++) {
				addEmptyBlock();
			}
			this.getChildren().add(hBoxes.get(i));
		}
		// Creating the center line of the grid section
		hBoxes.add(createCenteredHBox());
		for (int i = 0; i < 11; i++) {
			addEmptyBlock();
		}
		this.getChildren().add(hBoxes.get(hBoxes.size() - 1));
		// Creating the Bottom cone of the grid section
		for (int i = 21; i > 11; i--) {
			hBoxes.add(createCenteredHBox());
			for (int j = 0; j < i - 11; j++) {
				addEmptyBlock();
			}
			this.getChildren().add(hBoxes.get(hBoxes.size() - 1));
		}
	}

	/**
	 * Generates an empty block of proper size and color
	 * 
	 * @return an empty block of proper size and color
	 */
	private Rectangle createEmptyBlock() {
		Rectangle tempRectangle = new Rectangle();
		tempRectangle.setHeight(30);
		tempRectangle.setWidth(40);
		tempRectangle.setFill(Color.LIGHTGRAY);
		return tempRectangle;
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
	 * adds an empty block and a background block to the grid of blocks
	 */
	private void addEmptyBlock() {
		GridPane tempGridPane = new GridPane();
		Rectangle backgroundRectangle = new Rectangle();
		backgroundRectangle.setFill(Color.RED);
		backgroundRectangle.setHeight(35);
		backgroundRectangle.setWidth(45);
		Rectangle foregroundRectangle = createEmptyBlock();
		blocks.add(foregroundRectangle);

		tempGridPane.add(backgroundRectangle, 0, 0);
		tempGridPane.add(foregroundRectangle, 0, 0);
		tempGridPane.setHalignment(foregroundRectangle, HPos.CENTER);
		tempGridPane.setValignment(foregroundRectangle, VPos.CENTER);
		hBoxes.get(hBoxes.size() - 1).getChildren().add(tempGridPane);
	}
}
