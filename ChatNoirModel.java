package aDSFinal;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Model for the Chat Noir Game
 * 
 * @author Dominic Hiland
 * @author Josh Larson
 * @version 05/20/2020
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
	private boolean catTurn;

	/**
	 * denotes the block the cat is currently on
	 */
	private Block catPosition;

	/**
	 * Denotes that it is the first turn of the game
	 */
	private boolean turnOne = true;

	/**
	 * Property change manager
	 */
	protected PropertyChangeSupport pcs;

	/**
	 * Constructor
	 */
	public ChatNoirModel() {
		pcs = new PropertyChangeSupport(this);
		initializeGrid();
	}

	/**
	 * getter for the grid of blocks
	 * 
	 * @return the grid of blocks
	 */
	public Block getBlock(int x, int y) {
		Block referenceBlock = blocks.get(x).get(y);
		return new Block(referenceBlock.containsWall(), referenceBlock.containsCat(), referenceBlock.isEdge());
	}

	/**
	 * Initializes game grid to a state where no blocks contain walls and the cat is
	 * in the center
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
				if (j == 0 || j == 21 - 1 - i)
					blocks.get(i).add(new Block(true));
				else
					blocks.get(i).add(new Block());
			}
		}
		// Filling the center block of the grid with the cat and updating the
		// catLocation coordinates
		try {
			blocks.get(CENTER[0]).get(CENTER[1]).setContainsCat(true);
			pcs.firePropertyChange("Cat Changed", null, blocks.get(CENTER[0]).get(CENTER[1]));
			catPosition = blocks.get(CENTER[0]).get(CENTER[1]);

		} catch (Exception e) {
			e.getMessage();
		}
		// Generating randomly placed walls
		randomWalls();
		catTurn = true;
	}

	/**
	 * getter for catTurn
	 * 
	 * @return if it is the cat's turn
	 */
	public boolean getCatTurn() {
		return catTurn;
	}

	/**
	 * Method that randomly generates walls
	 * 
	 * The default is set to 11 walls but could be changed (possibly even ask the
	 * user to select number blocked)
	 * 
	 */
	private void randomWalls() {
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
	private ArrayList<Block> selectAdjacentBlock(Block currentBlock) {
		ArrayList<Block> adjacentBlocks = new ArrayList<Block>();
		adjacentBlocks
				.add(blocks.get(getBlockCoordinates(currentBlock)[0]).get(getBlockCoordinates(currentBlock)[1] - 1));
		adjacentBlocks
				.add(blocks.get(getBlockCoordinates(currentBlock)[0]).get(getBlockCoordinates(currentBlock)[1] + 1));
		adjacentBlocks
				.add(blocks.get(getBlockCoordinates(currentBlock)[0] + 1).get(getBlockCoordinates(currentBlock)[1]));
		adjacentBlocks
				.add(blocks.get(getBlockCoordinates(currentBlock)[0] - 1).get(getBlockCoordinates(currentBlock)[1]));

		// if the cat is in the top half of the board
		if (getBlockCoordinates(currentBlock)[0] < 10) {
			adjacentBlocks.add(
					blocks.get(getBlockCoordinates(currentBlock)[0] - 1).get(getBlockCoordinates(currentBlock)[1] - 1));
			adjacentBlocks.add(
					blocks.get(getBlockCoordinates(currentBlock)[0] + 1).get(getBlockCoordinates(currentBlock)[1] + 1));
		}
		// if the cat is in the middle row of the board
		else if (getBlockCoordinates(currentBlock)[0] == 10) {
			adjacentBlocks.add(
					blocks.get(getBlockCoordinates(currentBlock)[0] - 1).get(getBlockCoordinates(currentBlock)[1] - 1));
			adjacentBlocks.add(
					blocks.get(getBlockCoordinates(currentBlock)[0] + 1).get(getBlockCoordinates(currentBlock)[1] - 1));

		}
		// if the cat is in the bottom half of the board
		else {
			adjacentBlocks.add(
					blocks.get(getBlockCoordinates(currentBlock)[0] - 1).get(getBlockCoordinates(currentBlock)[1] + 1));
			adjacentBlocks.add(
					blocks.get(getBlockCoordinates(currentBlock)[0] + 1).get(getBlockCoordinates(currentBlock)[1] - 1));

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
	private void moveCat(Block moveCatTo) throws Exception {

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
				pcs.firePropertyChange("Cat Changed", null, catPosition);
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
	private void switchStates() {
		turnOne = false;
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
	private int[] getBlockCoordinates(Block blockInQuestion) {
		int[] blockCoordinates = new int[2];
		for (int i = 0; i < blocks.size(); i++) {
			boolean breaker = false;
			for (int j = 0; j < blocks.get(i).size(); j++) {
				if (blocks.get(i).get(j) == blockInQuestion) {
					blockCoordinates[0] = i;
					blockCoordinates[1] = j;
					breaker = true;
					break;
				}
			}
			if (breaker == true)
				break;
		}
		return blockCoordinates;

	}

	/**
	 * Determines if a user selection is a legal move
	 * 
	 * @param xCoord the x coordinate of the block selected
	 * @param yCoord the y coordinate of the block selected
	 * @throws Exception if the move is illegal
	 */
	public void checkLegalMove(int xCoord, int yCoord) throws Exception {
		Block clickedBlock = blocks.get(xCoord).get(yCoord);
		if (catTurn) {
			if (hasPath() == true) {
				moveCat(clickedBlock);
				switchStates();
			}
		} else {
			clickedBlock.setContainsWall(true);
			pcs.firePropertyChange("Wall Changed", null, clickedBlock);
			switchStates();
		}
	}

	/**
	 * Determines whether or not the game is over
	 * 
	 * @return true if the cat is blocked or has reached an edge
	 * @return false if the cat is not yet at an edge, and can still reach an edge
	 */
	public boolean gameOver() {
		if (blocks.get(getBlockCoordinates(catPosition)[0]).get(getBlockCoordinates(catPosition)[1]).isEdge())
			return true;
		if (hasPath() == false)
			return true;
		return false;
	}

	/**
	 * Uses Dijkstra's Algorithm to determine if the cat has a valid path to an edge
	 * 
	 * @return if the cat has a valid path to an edge
	 */
	private boolean hasPath() {
		Queue<Block> queue = new LinkedList<>();
		Block referencePoint = blocks.get(getBlockCoordinates(catPosition)[0]).get(getBlockCoordinates(catPosition)[1]);
		queue.add(referencePoint);
		ArrayList<ArrayList<Boolean>> bools = new ArrayList<>();
		for (int i = 0; i < blocks.size(); i++) {
			bools.add(new ArrayList<Boolean>());
		}
		// Filling the top cone of the boolean grid
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < i + 1; j++) {
				if (j == 0 || j == i)
					bools.get(i).add(false);
				else
					bools.get(i).add(false);
			}
		}
		// Filling the center row of the boolean grid
		for (int i = 0; i < 11; i++)
			if (i == 0 || i == 10)
				bools.get(10).add(false);
			else
				bools.get(10).add(false);
		// Filling the bottom cone of the boolean grid
		for (int i = 11; i < 21; i++) {
			for (int j = 0; j < 21 - i; j++) {
				if (j == 0 || j == 21 - i)
					bools.get(i).add(false);
				else
					bools.get(i).add(false);
			}
		}
		while (queue.isEmpty() == false) {
			Block referenceBlock = queue.remove();
			if (referenceBlock.isEdge()) {
				return true;
			} else {
				int[] referenceCoords = getBlockCoordinates(referenceBlock);
				bools.get(referenceCoords[0]).set(referenceCoords[1], true);
				ArrayList<Block> adjacentBlocks = selectAdjacentBlock(referenceBlock);
				for (int i = 0; i < adjacentBlocks.size(); i++) {
					int[] adjacentBlockCoords = getBlockCoordinates(adjacentBlocks.get(i));
					if (bools.get(adjacentBlockCoords[0]).get(adjacentBlockCoords[1]) == false) {
						if (blocks.get(adjacentBlockCoords[0]).get(adjacentBlockCoords[1]).containsWall() == false)
							queue.add(blocks.get(adjacentBlockCoords[0]).get(adjacentBlockCoords[1]));
					}
				}
			}
		}
		return false;
	}

	/**
	 * Creates a feedback String that says whose turn it is or who won
	 * 
	 * @return feedback String
	 */
	public String getFeedback() {
		if (turnOne && Arrays.equals(getBlockCoordinates(catPosition), CENTER))
			return "Cat Turn";
		String message;
		if (catTurn == true && gameOver() == true) {
			message = "The Cat wins";

		} else if (catTurn == false && gameOver() == true) {
			message = "The Cat blocker wins";
		} else if (catTurn == false) {
			message = "Cat's turn";
		} else
			message = "Cat blocker's turn";
		return message;
	}
}