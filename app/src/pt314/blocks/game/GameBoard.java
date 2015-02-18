package pt314.blocks.game;

import java.util.List;

import javax.swing.JOptionPane;

public class GameBoard {

	private int width;
	private int height;
	private Block[][] blocks;
	
	public GameBoard() {
		
	}
	public GameBoard(int width, int height) {
		this.width = width;
		this.height = height;
		blocks = new Block[height][width];
	}
	
	/**
	 * Reads a gameboard layout from a list of strings. 
	 * We assume the first line in the list is two numbers separated by a space representing the 
	 * height (rows) and width (columns) of the board.
	 * It should be followed by (rows) lines of (columns) characters where an 'H'
	 * represents a horizontally moving block, a 'V' represents a vertically moving block,
	 * a 'T' represents the target block, and a '.' represents an empty space.
	 * @param boardLayout
	 */
	public void read(List<String> boardLayout) throws IllegalArgumentException {
		String[] dimensions = boardLayout.get(0).split(" ");
		int targetBlockRow = -1;
		int targetBlockCol = -1;
		if (dimensions.length != 2)
			throw(new IllegalArgumentException("Boards must be two dimensional"));
		
		height = Integer.parseInt(dimensions[0]);
		width = Integer.parseInt(dimensions[1]);
		
		if (height < 1 || width < 1)
			throw(new IllegalArgumentException("Boards must be at least 1 X 1"));
		if (boardLayout.size() != height+1) 
			throw(new IllegalArgumentException("Actual number of rows does not match specified number of rows."));
		
		blocks = new Block[height][width];
		boardLayout.remove(0);
		
		for (int i = 0; i < boardLayout.size(); i++) {
			String row = boardLayout.get(i);
			if (row.length() != width)
				throw(new IllegalArgumentException("Actual number of columns does not match specified number of columns in row" + i));

			for (int j = 0; j < row.length(); j++) {
				switch (row.charAt(j)) {
				case 'T':
					if (targetBlockRow != -1)
						throw(new IllegalArgumentException("Only one target block per puzzel."));
					blocks[i][j] = new TargetBlock();
					targetBlockRow = i;
					targetBlockCol = j;
					break;
				case 'H':
					blocks[i][j] = new HorizontalBlock();
					
					break;
				case 'V':
					blocks[i][j] = new VerticalBlock();
					
					break;
				case '.':
					blocks[i][j] = null;
					
					break;

				default:
					throw(new IllegalArgumentException("Illegal cell type in input"));
				}
			}
		}
		
		for (int i = targetBlockCol+1; i < width; i++) {
			Block testBlock = blocks[targetBlockRow][i];
			if (testBlock != null) {
				if (testBlock instanceof HorizontalBlock)
					throw(new IllegalArgumentException("Target block must be rightmost horizontal block in a row"));
			}
		}
	}
	
	/**
	 * Place block at the specified location.
	 * 
	 * If there is a block at the location, it is replaced by the new block.
	 */
	public void placeBlockAt(Block block, int row, int col) {
		blocks[row][col] = block;
	}
	
	public boolean playerWon() {
		boolean result = false;
		for (int i = 0; i < height; i++) {
			Block testBlock = blocks[i][width-1];
			if (testBlock != null && testBlock instanceof TargetBlock) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	// TODO: Check for out of bounds
	public Block getBlockAt(int row, int col) {
		return blocks[row][col];
	}

	/**
	 * Move block at the specified location.
	 * 
	 * @param dir direction of movement.
	 * @param dist absolute movement distance.
	 * 
	 * @return <code>true</code> if and only if the move is possible.
	 */
	public boolean moveBlock(int row, int col, Direction dir, int dist) {
		
		// TODO: throw exception if move is invalid, instead of using return value
		
		Block block = blocks[row][col];

		// no block at specified location
		if (block == null)
			return false;
		
		// block cannot move in the specified direction
		if (!block.isValidDirection(dir))
			return false;
		
		// determine new location
		int newRow = row;
		int newCol = col;
		if (dir == Direction.UP)
			newRow -= dist;
		else if (dir == Direction.DOWN)
			newRow += dist;
		else if (dir == Direction.LEFT)
			newCol -= dist;
		else if (dir == Direction.RIGHT)
			newCol += dist;

		// destination out of bounds
		if (!isWithinBounds(newRow, newCol))
			return false;
		
		int dx = 0;
		int dy = 0;
		if (dir == Direction.UP)
			dy = -1;
		else if (dir == Direction.DOWN)
			dy = 1;
		else if (dir == Direction.LEFT)
			dx = -1;
		else if (dir == Direction.RIGHT)
			dx = 1;
		
		// check all cells from block location to destination
		int tmpRow = row;
		int tmpCol = col;
		for (int i = 0; i < dist; i++) {
			tmpRow += dy;
			tmpCol += dx;
			if (blocks[tmpRow][tmpCol] != null)
				return false; // another block in the way
		}
		
		blocks[newRow][newCol] = blocks[row][col];
		blocks[row][col] = null;
		return true;
	}
	
	/**
	 * Check if a location is inside the board.
	 */
	public boolean isWithinBounds(int row, int col) {
		if (row < 0 || row >= height)
			return false;
		if (col < 0 || col >= width)
			return false;
		return true;
	}
	
	public void updateBlocks(int row, int col) {
		
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	/**
	 * Print the board to standard out.
	 */
	public void print() {
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				Block block = blocks[row][col];
				char ch = '.';
				if (block instanceof TargetBlock)
					ch = 'T';
				else if (block instanceof HorizontalBlock)
					ch = 'H';
				else if (block instanceof VerticalBlock)
					ch = 'V';
				System.out.print(ch);
			}
			System.out.println();
		}
		System.out.println();
	}
}
