package application;

import java.util.ArrayList;
import java.util.Random;

/**
 * Model for the Chat Noir Game
 * 
 * @author Dominic Hiland
 * @version 05/04/2020
 */
public class ChatNoirModel {

	/**
	 * Coordinates of the center of the grid
	 */
	private final int[] CENTER = { 10, 5 };

	/**
	 * 2D ArrayList of Blocks. Stores the game grid
	 */
	private ArrayList<ArrayList<Block>> blocks;
	
	/**
	 * state switcher; toggles between human player and cat player turns
	 */
	private boolean humanTurn;

	/**
	 * Constructor
	 */
	public ChatNoirModel() {
		initializeGrid();
	}

	/**
	 * getter for the grid of blocks
	 * 
	 * @return the grid of blocks
	 */
	public ArrayList<ArrayList<Block>> getBlocks() {
		return blocks;
	}

	/**
	 * Initializes game grid to a state where no blocks contain walls and the cat is
	 * in the center
	 * 
	 * @Contains -----> TODO: add random wall generation 11 spaces <-----
	 */
	private void initializeGrid() {
		// Initializing an empty 2d ArrayList for the Grid
		blocks = new ArrayList<>();
		for (int i = 0; i < 21; i++) {
			blocks.add(new ArrayList<Block>());
		}

		// Filling the top cone of the grid
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < i + 1; j++) {
				if (j == 0 || j == i)
					blocks.get(i).add(new Block(true));
				else
					blocks.get(i).add(new Block());
			}
		}

		// Filling the center row of the grid
		for (int i = 0; i < 11; i++)
			if (i == 0 || i == 10)
				blocks.get(10).add(new Block(true));
			else
				blocks.get(10).add(new Block());

		// Filling the bottom cone of the grid
		for (int i = 11; i < 21; i++) {
			for (int j = 0; j < 21 - i; j++) {
				if (j == 0 || j == 21 - i)
					blocks.get(i).add(new Block(true));
				else
					blocks.get(i).add(new Block());
			}
		}
		// Filling the center block of the grid with the cat and updating the
		// catLocation coordinates
		try {
			blocks.get(CENTER[0]).get(CENTER[1]).setContainsCat(true);
			// -----> TODO: add random wall generation <-----

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		String printout = new String();
		// Numbering the rows of the printout
		for (int i = 0; i < 21; i++) {
			// Conditions keep columns in-line with one another
			if (i < 10)
				printout += i + ":   ";
			else
				printout += i + ":  ";
			// Populating rows with appropriate number of spaces to match the grid
			for (int j = 0; j < blocks.get(i).size(); j++) {
				// Changes the character printed based on the status of the block (i.e. if it
				// contains a wall print a 'W' or if it contains the cat print a 'C'. If the
				// block is empty print a '-'
				if (blocks.get(i).get(j).containsWall() || blocks.get(i).get(j).containsCat()) {
					if (blocks.get(i).get(j).containsWall())
						printout += 'W';
					else
						printout += 'C';
				} else {
					printout += '-';
				}
			}
			printout += '\n';
		}
		return printout;
	}
}
