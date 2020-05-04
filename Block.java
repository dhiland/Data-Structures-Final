package application;

/**
 * One block in the grid of blocks necessary for Chat Noir
 * @author Dominic Hiland
 * @version 04/29/2020
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
	 * Constructor
	 */
	public Block() {
		containsWall = false;
		containsCat = false;
	}

	/**
	 * Constructor with overload
	 * @param containsWall if the Block contains a wall
	 * @param containsCat if the Block contains the cat
	 */
	public Block(boolean containsWall, boolean containsCat) {
		try {
			setContainsWall(containsWall);
			setContainsCat(containsCat);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
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
