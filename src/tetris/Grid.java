package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Grid extends JFrame {
	private Cell[][] cells;
	private enum shapeType {LINE, L, FLIPL, Z, FLIPZ, SQUARE, PLUSTHING};
	private shapeType activeShape;
	private ArrayList<Cell> activeCells;
	//private final byte WIDTH;
	//private final byte HEIGHT;
	
	JPanel mainPanel, cellPanel, nextPanel;
	

	/**
	*	Creates an empty grid to hold shapes.
	*/
	public Grid() {
		//should call super first
		super();
		int WIDTH = 400; int HEIGHT = 600;
		mainPanel = new JPanel();
		mainPanel.setBackground(Color.WHITE);
		
		this.setSize(new Dimension(WIDTH,HEIGHT));
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setTitle("Turbo Tetris 5k4000");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//add Panels
		this.add(mainPanel);
		
		this.setVisible(true);
		
	}
	
	//Called after current cells are placed down. If returns false, game over
	public boolean spawn(){
		
		return true;
	}
	private boolean spawnLine(){
		return true;
	}
	private boolean spawnL(){
		return true;
	}
	private boolean spawnFlipL(){
		return true;
	}
	private boolean spawnZ(){
		return true;
	}
	private boolean spawnFlipZ(){
		return true;
	}
	private boolean spawnSquare(){
		return true;
	}
	private boolean spawnPlusThing(){
		return true;
	}
	
	public boolean rotate(){
		
		return true;
	}
	private boolean rotateLine(){
		return true;
	}
	private boolean rotateL(){
		return true;
	}
	private boolean rotateFlipL(){
		return true;
	}
	private boolean rotateZ(){
		return true;
	}
	private boolean rotateFlipZ(){
		return true;
	}
	private boolean rotateSquare(){
		return true;
	}
	private boolean rotatePlusThing(){
		return true;
	}
	
	protected void clearLine(int line){
		
	}
	
	public void tock(){
		
	}
}