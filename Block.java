package aDSFinal;



/**
 * One block in the grid of blocks necessary for Chat Noir
 * 
 * @author Dominic Hiland
 * @author Josh Larson
 * @version 05/11/2020
 */
public class Block {

	/**
	 * denotes if the block contains a wall
	 */
	private boolean containsWall;

	/**
	 * denotes if the block contains the cat
	 */
	private boolean containsCat;

	/**
	 * Denotes if the Block was generated at an edge
	 */
	private boolean isEdge;

	/**
	 * Constructor
	 */
	public Block() {
		containsWall = false;
		containsCat = false;
	}

	/**
	 * Constructor with overload
	 * 
	 * @param containsWall if the Block contains a wall
	 * @param containsCat  if the Block contains the cat
	 */
	public Block(boolean isEdge) {
		this.isEdge = isEdge;
	}
	
	public Block (boolean containsWall, boolean containsCat, boolean isEdge) {
		this.containsWall = containsWall;
		this.containsCat = containsCat;
		this.isEdge = isEdge;
	}

	/**
	 * Gets if the Block is an edge
	 * 
	 * @return if the Block is an edge
	 */
	public boolean isEdge() {
		return isEdge;
	}

	/**
	 * getter for if the block contains a wall
	 * 
	 * @return if the block contains a wall
	 */
	public boolean containsWall() {
		return containsWall;
	}

	/**
	 * sets the containsWall attribute of the block
	 * 
	 * @param containsWall the new value of the containsWall attribute
	 * @throws Exception if the block contains the cat or if the user adds a wall to
	 *                   a block that already contains a wall
	 */
	public void setContainsWall(boolean containsWall) throws Exception {
		if (containsCat == true)
			throw new Exception("Block already contains cat");
		else if (this.containsWall == true && containsWall == true)
			throw new Exception("Block already contains a wall");
		this.containsWall = containsWall;
	}

	/**
	 * getter for if the block contains the cat
	 * 
	 * @return if the block contains the cat
	 */
	public boolean containsCat() {
		return containsCat;
	}

	/**
	 * sets the containsCat attribute of the block
	 * 
	 * @param containsCat the new value of the containsCat attribute
	 * @throws Exception if the block contains a wall or if the cat is added to a
	 *                   block that already contains the cat
	 */
	public void setContainsCat(boolean containsCat) throws Exception {
		if (this.containsCat == true && containsCat == true)
			throw new Exception("Block already contains cat");
		else if (containsWall == true)
			throw new Exception("Block already contains a wall");
		this.containsCat = containsCat;
	}
}