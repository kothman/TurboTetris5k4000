package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Grid extends JFrame {
	private Cell[][] cells;
	private enum shapeType {LINE, L, FLIPL, Z, FLIPZ, SQUARE, PLUSTHING};
	private shapeType activeShape;
	private ArrayList<Cell> activeCells;
	private final byte NUM_OF_ROWS;
	private final byte NUM_OF_COLS;
	
	JPanel mainPanel, cellPanel, nextPanel;
	
	//mainPanel finals
	int WIDTH, HEIGHT;
	//cellPanel finals
	int CELL_PANEL_WIDTH, CELL_PANEL_HEIGHT, OFFSET;

	/**
	*	Creates an empty grid to hold shapes.
	*/
	public Grid() {
		//should call super first
		super();
		
		//constants
		NUM_OF_ROWS = 20; NUM_OF_COLS = 10;
		WIDTH = 500; HEIGHT = 600;
		CELL_PANEL_WIDTH = WIDTH - 200; CELL_PANEL_HEIGHT = HEIGHT-100; OFFSET = 10;
		
		//populate cells
		cells = new Cell[NUM_OF_ROWS][NUM_OF_COLS];
		for(int row = 0; row < NUM_OF_ROWS; row++){
			for(int col = 0; col < NUM_OF_COLS; col++){
				Cell temp = new Cell();
				temp.setOccupied(false);
				cells[row][col] = temp;
			}
		}
		
		mainPanel = new JPanel();
		mainPanel.setLayout(null);
		mainPanel.setBackground(Color.WHITE);
		cellPanel = new JPanel(){
			public void paintComponent(Graphics g){
				//draw border
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, CELL_PANEL_WIDTH, CELL_PANEL_HEIGHT);
				//paint cells
				for(int col = 0; col < NUM_OF_COLS; col++){
					for(int row = 0; row < NUM_OF_ROWS; row++){
						g.setColor(Color.WHITE);
						g.drawRect(col*(CELL_PANEL_WIDTH/NUM_OF_COLS), row*(CELL_PANEL_HEIGHT/NUM_OF_ROWS), CELL_PANEL_WIDTH/NUM_OF_COLS, CELL_PANEL_HEIGHT/NUM_OF_ROWS);
						g.setColor(cells[row][col].getColor());
						g.fillRect(col*(CELL_PANEL_WIDTH/NUM_OF_COLS) + 1, row*(CELL_PANEL_HEIGHT/NUM_OF_ROWS) + 1, CELL_PANEL_WIDTH/NUM_OF_COLS - 2, CELL_PANEL_HEIGHT/NUM_OF_ROWS - 2);
					}
				}
			}
		};
		cellPanel.setBackground(Color.BLACK);
		cellPanel.setBounds(OFFSET, OFFSET, CELL_PANEL_WIDTH, CELL_PANEL_HEIGHT);
		
		this.setSize(new Dimension(WIDTH,HEIGHT));
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setTitle("Turbo Tetris 5k4000");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//add Panels
		mainPanel.add(cellPanel);
		this.add(mainPanel);
		repaint();
		cellPanel.repaint();
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
	
	//check for collisions, logic, etc etc
	public void tock(){

	}

}