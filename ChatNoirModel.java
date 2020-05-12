package aDSFinal;

import java.util.ArrayList;
import java.util.Random;

/**
 * Model for the Chat Noir Game
 * 
 * @author Dominic Hiland
 * @author Josh Larson
 * @version 05/11/2020
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
	private boolean catTurn = true;

	/**
	 * denotes the block the cat is currently on
	 */
	private Block catPosition;
	/**
	 * denotes the coordinates for the block the cat is currently on
	 */
	private int[] catCoordinates = { 10, 5 };

	/**
	 * Getter method for catCoordinates
	 * 
	 * @return catCoordinates An array of two ints denoting x,y coordinates of cat
	 */
	public int[] getcatCoordinates() {
		return catCoordinates;
	}

	/**
	 * Setter method for catCoordinates
	 * 
	 * @param catCoordinates An array of two ints denoting x,y coordinates of cat
	 */
	private void setcatCoordinates(int[] catCoordinates) {
		this.catCoordinates = catCoordinates;
	}

	private boolean getCatTurn() {
		return catTurn;
	}

	private Block getCatPosition() {
		return catPosition;
	}

	private void setCatPosition(Block catPosition) {
		this.catPosition = catPosition;
	}

	private void setCatTurn(boolean catTurn) {
		this.catTurn = catTurn;
	}

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
	 * 
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
			catPosition = blocks.get(CENTER[0]).get(CENTER[1]);

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

	/**
	 * Method that randomly generates walls
	 * 
	 * The default is set to 11 walls but could be changed (possibly even ask the
	 * user to select number blocked)
	 * 
	 */
	public void randomWalls() {
		Random random = new Random();
		int numRandWalls = 0;
		while (numRandWalls < 11) {
			int xCoord = random.nextInt(21);
			int yMax = blocks.get(xCoord).size();
			int yCoord = random.nextInt(yMax);
			try {
				blocks.get(xCoord).get(yCoord).setContainsWall(true);
				numRandWalls++;
			} catch (Exception e) {
				e.getMessage();
			}

		}
	}

	/**
	 * Method that determines what blocks are adjacent to the cat This method
	 * determines what the cat's legal moves are
	 * 
	 * @param currentBlock The block the cat is currently on
	 * @return adjacentBlocks An ArrayList containing the six blocks that are
	 *         adjacent to the cat's current position
	 */
	public ArrayList<Block> selectAdjacentBlock(Block currentBlock) {
		ArrayList<Block> adjacentBlocks = new ArrayList<Block>();
		adjacentBlocks.add(blocks.get(catCoordinates[0]).get(catCoordinates[1] - 1));
		adjacentBlocks.add(blocks.get(catCoordinates[0]).get(catCoordinates[1] + 1));
		adjacentBlocks.add(blocks.get(catCoordinates[0] + 1).get(catCoordinates[1]));
		adjacentBlocks.add(blocks.get(catCoordinates[0] - 1).get(catCoordinates[1]));

		// if the cat is in the top half of the board
		if (catCoordinates[0] < 10) {
			adjacentBlocks.add(blocks.get(catCoordinates[0] - 1).get(catCoordinates[1] - 1));
			adjacentBlocks.add(blocks.get(catCoordinates[0] + 1).get(catCoordinates[1] + 1));
		}
		// if the cat is in the middle row of the board
		else if (catCoordinates[0] == 10) {
			adjacentBlocks.add(blocks.get(catCoordinates[0] - 1).get(catCoordinates[1] - 1));
			adjacentBlocks.add(blocks.get(catCoordinates[0] + 1).get(catCoordinates[1] - 1));

		}
		// if the cat is in the bottom half of the board
		else {
			adjacentBlocks.add(blocks.get(catCoordinates[0] - 1).get(catCoordinates[1] + 1));
			adjacentBlocks.add(blocks.get(catCoordinates[0] + 1).get(catCoordinates[1] - 1));

		}
		return adjacentBlocks;
	}

	/**
	 * Method that moves the cat
	 * 
	 * @param moveCatTo The block the cat is moving to
	 * @throws Exception if the user tries moving the cat to a block that is not
	 *                   adjacent to the cat
	 */
	public void moveCat(Block moveCatTo) throws Exception {
		if (moveCatTo.containsWall() == true) {
			throw new Exception("Cat cannot be moved to a wall");
		}
		ArrayList<Block> tempBlocks = selectAdjacentBlock(catPosition);
		int numberFailed = 0;
		for (int i = 0; i < tempBlocks.size(); i++) {
			if (tempBlocks.get(i) == moveCatTo) {
				catPosition.setContainsCat(false);
				catPosition = moveCatTo;
				catPosition.setContainsCat(true);
				setcatCoordinates(getBlockCoordinates(moveCatTo));
			} else {
				numberFailed++;
			}
		}
		// if the spot the user tries to move the cat to is not one of the six adjacent
		// blocks
		if (numberFailed == 6) {
			throw new Exception("Cat must move to adjacent space");
		}

	}

	/**
	 * Method that keeps track of who's turn it is
	 */
	public void switchStates() {
		if (catTurn == true) {
			catTurn = false;
		} else
			catTurn = true;
	}

	/**
	 * Method that gets the coordinates of a specified block
	 * 
	 * @param blockInQuestion The block that we are getting the coordinates of
	 * @return blockCoordinates An array of two ints that has the x,y coordinates of
	 *         the block
	 */
	public int[] getBlockCoordinates(Block blockInQuestion) {
		int[] blockCoordinates = new int[2];
		for (int i = 0; i < blocks.size(); i++) {
			for (int j = 0; j < blocks.get(i).size(); j++) {
				if (blocks.get(i).get(j) == blockInQuestion) {
					blockCoordinates[0] = i;
					blockCoordinates[1] = j;
				}
			}
		}
		return blockCoordinates;

	}

	public void checkLegalMove(Block clickedBlock) throws Exception {

		if (catTurn == true) {
			moveCat(clickedBlock);
			switchStates();

		} else {
			clickedBlock.setContainsWall(true);
			switchStates();

		}
	}

}