package pt314.blocks.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import pt314.blocks.game.Block;
import pt314.blocks.game.Direction;
import pt314.blocks.game.GameBoard;
import pt314.blocks.game.HorizontalBlock;
import pt314.blocks.game.TargetBlock;
import pt314.blocks.game.VerticalBlock;


public class GridPanel extends JPanel implements MouseListener {

	private GameBoard board;
	private Block selectedBlock;
	private int selectedBlockRow;
	private int selectedBlockCol;
	private int imageWidth, imageHeight;

	private HashMap<String, Image> blockImages;
	
	public GridPanel(GameBoard grid) {
		blockImages = new HashMap<>();
		Path imageDir = Paths.get("./res/images");
		
		try {
			Image block;
			block = ImageIO.read(imageDir.resolve("block-blue.png").toFile());
			blockImages.put("H", block);
			block = ImageIO.read(imageDir.resolve("block-red.png").toFile());
			blockImages.put("V", block);
			block = ImageIO.read(imageDir.resolve("blocks-yellow.png").toFile());
			blockImages.put("T", block);
			imageWidth = block.getWidth(null);
			imageHeight = block.getHeight(null);
			
		} catch (IOException e) {
			System.out.println("Error reading block images: " + e.getLocalizedMessage());
		}
		setGameBoard(grid);

	}
	
	public void setGameBoard(GameBoard grid) {
		board  = grid;
		setPreferredSize(new Dimension(grid.getWidth()*imageWidth, grid.getHeight()*imageHeight));
	}

	public GridPanel(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public GridPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public GridPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.clearRect(0, 0, getWidth(), getHeight());
		for (int i = 0; i < board.getWidth(); i++) {
			for (int j = 0; j < board.getHeight(); j++) {
				Block block = board.getBlockAt(j, i);
				if (block != null) {
					int x = i * imageWidth;
					int y = j * imageHeight;
					if (block instanceof TargetBlock)
						g.drawImage(blockImages.get("T"), x, y,null);
					else if (block instanceof HorizontalBlock)
						g.drawImage(blockImages.get("H"), x, y,null);
					else if (block instanceof VerticalBlock)
						g.drawImage(blockImages.get("V"), x, y,null);
			
				}
			}
		}
		if (selectedBlock != null) {
			g.setColor(Color.LIGHT_GRAY);
			g.drawRect(selectedBlockCol*imageWidth, selectedBlockRow*imageHeight, imageWidth, imageHeight);
		}
	}
	
	/**
	 * Select block at a specific location.
	 * 
	 * If there is no block at the specified location,
	 * the previously selected block remains selected.
	 * 
	 * If there is a block at the specified location,
	 * the previous selection is replaced.
	 */
	private void selectBlockAt(int row, int col) {
		Block block = board.getBlockAt(row, col);
		if (block != null) {
			selectedBlock = block;
			selectedBlockRow = row;
			selectedBlockCol = col;
		}
		repaint();
	}
	
	/**
	 * Try to move the currently selected block to a specific location.
	 * 
	 * If the move is not possible, nothing happens.
	 */
	private void moveSelectedBlockTo(int row, int col) {
		
		int vertDist = row - selectedBlockRow;
		int horzDist = col - selectedBlockCol;
		
		if (vertDist != 0 && horzDist != 0) {
			System.err.println("Invalid move!");
			return;
		}
		
		Direction dir = getMoveDirection(selectedBlockRow, selectedBlockCol, row, col);
		int dist = Math.abs(vertDist + horzDist);
		
		if (!board.moveBlock(selectedBlockRow, selectedBlockCol, dir, dist)) {
			Toolkit.getDefaultToolkit().beep();
			System.err.println("Invalid move!");
			selectedBlock = null;
		}
		else {
			selectedBlock = null;
		}
		repaint();
	}

	/**
	 * Determines the direction of a move based on
	 * the starting location and the destination.
	 *  
	 * @return <code>null</code> if both the horizontal distance
	 * 	       and the vertical distance are not zero. 
	 */
	private Direction getMoveDirection(int startRow, int startCol, int destRow, int destCol) {
		int vertDist = destRow - startRow;
		int horzDist = destCol - startCol;
		if (vertDist < 0)
			return Direction.UP;
		if (vertDist > 0)
			return Direction.DOWN;
		if (horzDist < 0)
			return Direction.LEFT;
		if (horzDist > 0)
			return Direction.RIGHT;
		return null;
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		int row = e.getPoint().y/imageHeight;
		int col = e.getPoint().x/imageWidth;
		System.out.println("Clicked (" + row + ", " + col + ")");
		
		if (selectedBlock == null || board.getBlockAt(row, col) != null) {
			selectBlockAt(row, col);
		}
		else {
			moveSelectedBlockTo(row, col);
		}
		if (board.playerWon()) {
			JOptionPane.showMessageDialog(null, "You Won!", "Congratulations!!!!", JOptionPane.INFORMATION_MESSAGE);
		}
		
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
