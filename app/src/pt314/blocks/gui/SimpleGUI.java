package pt314.blocks.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import pt314.blocks.game.Block;
import pt314.blocks.game.Direction;
import pt314.blocks.game.GameBoard;
import pt314.blocks.game.HorizontalBlock;
import pt314.blocks.game.TargetBlock;
import pt314.blocks.game.VerticalBlock;


/**
 * Simple GUI test...
 */
public class SimpleGUI extends JFrame  {


	
	// currently selected block

	private GridPanel display;
	private JMenuBar menuBar;
	private JMenu gameMenu, helpMenu;
	private JMenuItem newGameMenuItem;
	private JMenuItem exitMenuItem;
	private JMenuItem aboutMenuItem;
	final static String defaultPuzzle = "steve-test-001.txt";
	
	public SimpleGUI() {
		super("Blocks");
		
		initMenus();
		
		initBoard();
		
		pack();
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	private void initMenus() {
		menuBar = new JMenuBar();
		
		gameMenu = new JMenu("Game");
		menuBar.add(gameMenu);
		
		helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
		
		newGameMenuItem = new JMenuItem("New game");
		newGameMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GameBoard board = new GameBoard();
				readBoardFromFile(board, Paths.get("./res/puzzles/"+defaultPuzzle));
				display.setGameBoard(board);
				repaint();
				
			}
		});
		gameMenu.add(newGameMenuItem);
		
		gameMenu.addSeparator();
		
		exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		gameMenu.add(exitMenuItem);
		
		aboutMenuItem = new JMenuItem("About");
		aboutMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(SimpleGUI.this, "Sliding blocks!");
			}
		});
		helpMenu.add(aboutMenuItem);
		
		setJMenuBar(menuBar);
	}
	
	private void readBoardFromFile(GameBoard board, Path puzzlePath) {
		Charset utf8 = StandardCharsets.UTF_8;
		List<String> boardInfo = null;
		try {
			boardInfo = Files.readAllLines(puzzlePath ,utf8);
			board.read(boardInfo);
		} catch (IOException loadException) {
			// TODO Auto-generated catch block
			loadException.printStackTrace();
		}
		
	}
	
	private void initBoard() {
		
		GameBoard board = new GameBoard();
		readBoardFromFile(board, Paths.get("./res/puzzles/"+defaultPuzzle ));

		display = new GridPanel(board);
		getContentPane().addMouseListener(display);
		getContentPane().add(display);
		updateUI();
	}

	private void updateUI() {
		this.repaint();
	}



}
