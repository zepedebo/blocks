package pt314.blocks;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

import pt314.blocks.game.Block;
import pt314.blocks.game.GameBoard;
import pt314.blocks.game.HorizontalBlock;
import pt314.blocks.game.TargetBlock;
import pt314.blocks.game.VerticalBlock;

/**
 * Simple GUI test...
 */
public class GUITest extends JFrame {

	private static final int NUM_ROWS = 5;
	private static final int NUM_COLS = 5;

	private GameBoard board;

	private JButton[][] buttonGrid;
	
	public GUITest() {
		super("Blocks");
		
		board = new GameBoard(NUM_COLS, NUM_ROWS);
		buttonGrid = new JButton[NUM_ROWS][NUM_COLS];
		
		setLayout(new GridLayout(NUM_ROWS, NUM_COLS));
		for (int row = 0; row < NUM_ROWS; row++) {
			for (int col = 0; col < NUM_COLS; col++) {
				JButton cell = new JButton("(" + row + ", " + col + ")");
				cell.setPreferredSize(new Dimension(64, 64));
				buttonGrid[row][col] = cell;
				add(cell);
			}
		}
		
		// testing...
		board.placeBlockAt(new HorizontalBlock(), 0, 0);
		board.placeBlockAt(new HorizontalBlock(), 4, 4);
		board.placeBlockAt(new VerticalBlock(), 1, 3);
		board.placeBlockAt(new VerticalBlock(), 3, 1);
		board.placeBlockAt(new TargetBlock(), 2, 2);
		
		// update display...
		for (int row = 0; row < NUM_ROWS; row++) {
			for (int col = 0; col < NUM_COLS; col++) {
				Block block = board.getBlockAt(row, col);
				JButton cell = buttonGrid[row][col];
				if (block == null)
					cell.setBackground(Color.LIGHT_GRAY);
				else if (block instanceof TargetBlock)
					cell.setBackground(Color.YELLOW);
				else if (block instanceof HorizontalBlock)
					cell.setBackground(Color.BLUE);
				else if (block instanceof VerticalBlock)
					cell.setBackground(Color.RED);
			}
		}
		
		pack();
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new GUITest();
	}
}
