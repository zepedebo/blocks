package pt314.blocks.game;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GameBoardTest {
	static final private int TEST_WIDTH = 5;
	static final private int TEST_HEIGHT = 5;

	@Test
	public void testMoveBlockVirtical() {
		GameBoard testBoard = new GameBoard(TEST_WIDTH, TEST_HEIGHT);
		
		testBoard.placeBlockAt(new VerticalBlock(), 0, 2);
		assertFalse("Moved wrong direction", testBoard.moveBlock(0, 2, Direction.LEFT, 1));
		assertFalse(testBoard.moveBlock(0, 2, Direction.RIGHT, 1));
		assertFalse(testBoard.moveBlock(0, 2, Direction.UP, 1));
		assertFalse(testBoard.moveBlock(0, 2, Direction.DOWN, 5));

		assertTrue(testBoard.moveBlock(0, 2, Direction.DOWN, 4));
		Block b = testBoard.getBlockAt(4, 2);
		assertTrue(b != null);
		assertTrue(b instanceof VerticalBlock);
		
	}
	
	@Test
	public void testMoveBlockHorizontall() {
		GameBoard testBoard = new GameBoard(TEST_WIDTH, TEST_HEIGHT);
		
		testBoard.placeBlockAt(new HorizontalBlock(), 2, 0);
		assertFalse(testBoard.moveBlock(2, 0, Direction.UP, 1));
		assertFalse(testBoard.moveBlock(2, 0, Direction.DOWN, 1));
		assertFalse(testBoard.moveBlock(2, 0, Direction.LEFT, 1));
		assertFalse(testBoard.moveBlock(2, 0, Direction.RIGHT, 5));

		assertTrue(testBoard.moveBlock(2, 0, Direction.RIGHT, 4));
		Block b = testBoard.getBlockAt(2, 4);
		assertTrue(b != null);
		assertTrue(b instanceof HorizontalBlock);
		
	}

	@Test
	public void testMoveBlockTarget() {
		GameBoard testBoard = new GameBoard(TEST_WIDTH, TEST_HEIGHT);
		
		testBoard.placeBlockAt(new TargetBlock(), 2, 0);
		assertFalse(testBoard.moveBlock(2, 0, Direction.UP, 1));
		assertFalse(testBoard.moveBlock(2, 0, Direction.DOWN, 1));
		assertFalse(testBoard.moveBlock(2, 0, Direction.LEFT, 1));
		assertFalse(testBoard.moveBlock(2, 0, Direction.RIGHT, 5));

		assertTrue(testBoard.moveBlock(2, 0, Direction.RIGHT, 4));
		Block b = testBoard.getBlockAt(2, 4);
		assertTrue(b != null);
		assertTrue(b instanceof HorizontalBlock);
		assertTrue(b instanceof TargetBlock);
		
	}
	
	@Test
	public void testMoveBlockHorizontallBlocked() {
		GameBoard testBoard = new GameBoard(TEST_WIDTH, TEST_HEIGHT);
		
		testBoard.placeBlockAt(new HorizontalBlock(), 2, 0);
		testBoard.placeBlockAt(new HorizontalBlock(), 2, 3);
		
		assertTrue(testBoard.moveBlock(2, 0, Direction.RIGHT, 1));
		assertFalse(testBoard.moveBlock(2, 1, Direction.RIGHT, 3));

		
	}
	
	@Test
	public void testNoBlock() {
		GameBoard testBoard = new GameBoard(TEST_WIDTH, TEST_HEIGHT);
		assertFalse(testBoard.moveBlock(1, 1, Direction.DOWN, 2));
		try {
			testBoard.moveBlock(TEST_WIDTH, TEST_HEIGHT, Direction.LEFT, 1);
			assertFalse(true);
		} catch (ArrayIndexOutOfBoundsException e) {
			assertTrue(true);
		}
		
		try {
			testBoard.moveBlock(-1, -1, Direction.LEFT, 1);
			assertFalse(true);
		} catch (ArrayIndexOutOfBoundsException e) {
			assertTrue(true);
		}

	}


}
