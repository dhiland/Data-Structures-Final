package application;

import java.util.ArrayList;

/**
 * Model for the Chat Noir Game
 * 
 * @author Dominic Hiland
 * @version 05/04/2020
 */
public class ChatNoirModel { // -----> TODO: Write addWall(x, y) method <-----

	/**
	 * Coordinates of the center of the grid
	 */
	private final int[] CENTER = { 10, 5 };

	/**
	 * 2D ArrayList of Blocks. Stores the game grid
	 */
	private ArrayList<ArrayList<Block>> blocks;

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
	 * @Contains
	 * -----> TODO: add random wall generation <----- 
	 * -----> TODO: Figure out loop to replace Switch-Case <------
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
				blocks.get(i).add(new Block());
			}
		}

		// Filling the center row of the grid
		for (int i = 0; i < 11; i++)
			blocks.get(10).add(new Block());

		// Filling the bottom cone of the grid
		for (int i = 11; i < 21; i++) {
			// -----> TODO Figure out loop to replace Switch-Case <------
			switch (i) {
			case 11:
				for (int j = 0; j < 10; j++)
					blocks.get(i).add(new Block());
				break;
			case 12:
				for (int j = 0; j < 9; j++)
					blocks.get(i).add(new Block());
				break;
			case 13:
				for (int j = 0; j < 8; j++)
					blocks.get(i).add(new Block());
				break;
			case 14:
				for (int j = 0; j < 7; j++)
					blocks.get(i).add(new Block());
				break;
			case 15:
				for (int j = 0; j < 6; j++)
					blocks.get(i).add(new Block());
				break;
			case 16:
				for (int j = 0; j < 5; j++)
					blocks.get(i).add(new Block());
				break;
			case 17:
				for (int j = 0; j < 4; j++)
					blocks.get(i).add(new Block());
				break;
			case 18:
				for (int j = 0; j < 3; j++)
					blocks.get(i).add(new Block());
				break;
			case 19:
				for (int j = 0; j < 2; j++)
					blocks.get(i).add(new Block());
				break;
			default:
				blocks.get(i).add(new Block());
				break;
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
