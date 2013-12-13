package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class Grid extends JFrame {
	Cell[][] cells;
	enum shapeType {LINE, L, FLIPL, Z, FLIPZ, SQUARE, PLUSTHING};
	enum direction {LEFT, RIGHT};
	shapeType currentShape;
	shapeType nextShape;
	ArrayList<ActiveCell> activeCells;
	static final byte NUM_OF_ROWS = 20, NUM_OF_COLS = 10;
	
	JPanel mainPanel, gridPanel, nextPanel;
	
	//mainPanel finals
	static int WIDTH, HEIGHT;
	//cellPanel finals
	static int CELL_PANEL_WIDTH, CELL_PANEL_HEIGHT, OFFSET;

	/**
	*	Creates an empty grid to hold shapes.
	*/
	public Grid() {
		//should call super first
		super();
		
		//init vars
		activeCells = new ArrayList<ActiveCell>();
		
		//constants
		//NUM_OF_ROWS = 20; NUM_OF_COLS = 10;
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
		//stuff for the grid
		gridPanel = new JPanel(){
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
						g.fillRect(col*(CELL_PANEL_WIDTH/NUM_OF_COLS) + 2, row*(CELL_PANEL_HEIGHT/NUM_OF_ROWS) + 2, CELL_PANEL_WIDTH/NUM_OF_COLS - 3, CELL_PANEL_HEIGHT/NUM_OF_ROWS - 3);
					}
				}
				for(ActiveCell a:activeCells){
					a.paintComponent(g);
				}
			}
		};
		gridPanel.setBounds(OFFSET, OFFSET, CELL_PANEL_WIDTH, CELL_PANEL_HEIGHT);
		
		this.setSize(new Dimension(WIDTH,HEIGHT));
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setTitle("Turbo Tetris 5k4000");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setNextShape();
		this.spawn();
		this.tock();
		this.setKeystrokes();
		
		//add Panels
		mainPanel.add(gridPanel);
		this.add(mainPanel);
		repaint();
		gridPanel.repaint();
		this.setVisible(true);
		
	}
	
	//Called after current cells are placed down. If returns false, game over
	public boolean spawn(){
		//pick a random spawn!!!
		currentShape = nextShape;
		setNextShape();
		switch(currentShape){
			case LINE:
				spawnLine();
				break;
			case L:
				spawnL();
				break;
			case FLIPL:
				spawnFlipL();
				break;
			case Z:
				spawnZ();
				break;
			case FLIPZ:
				spawnFlipZ();
				break;
			case SQUARE:
				spawnSquare();
				break;
			case PLUSTHING:
				spawnPlusThing();
				break;
			default:
				break;
		}
		return true;
	}
	private boolean spawnLine(){
		Cell.color c = Cell.color.YELLOW;
		activeCells.clear();
		activeCells.add(new ActiveCell(4,0,c));
		activeCells.add(new ActiveCell(4,1,c));
		activeCells.add(new ActiveCell(4,2,c));
		activeCells.add(new ActiveCell(4,3,c));

		return true;
	}
	private boolean spawnL(){
		Cell.color c = Cell.color.PINK;
		activeCells.clear();
		activeCells.add(new ActiveCell(4,0,c));
		activeCells.add(new ActiveCell(4,1,c));
		activeCells.add(new ActiveCell(4,2,c));
		activeCells.add(new ActiveCell(5,2,c));

		return true;
	}
	private boolean spawnFlipL(){
		Cell.color c = Cell.color.BLUE;
		activeCells.clear();
		activeCells.add(new ActiveCell(5,0,c));
		activeCells.add(new ActiveCell(5,1,c));
		activeCells.add(new ActiveCell(5,2,c));
		activeCells.add(new ActiveCell(4,2,c));

		return true;
	}
	private boolean spawnZ(){
		Cell.color c = Cell.color.RED;
		activeCells.clear();
		activeCells.add(new ActiveCell(3,0,c));
		activeCells.add(new ActiveCell(4,0,c));
		activeCells.add(new ActiveCell(4,1,c));
		activeCells.add(new ActiveCell(5,1,c));
		return true;
	}
	private boolean spawnFlipZ(){
		Cell.color c = Cell.color.ORANGE;
		activeCells.clear();
		activeCells.add(new ActiveCell(5,0,c));
		activeCells.add(new ActiveCell(6,0,c));
		activeCells.add(new ActiveCell(5,1,c));
		activeCells.add(new ActiveCell(4,1,c));
		return true;
	}
	private boolean spawnSquare(){
		Cell.color c = Cell.color.GREEN;
		activeCells.clear();
		activeCells.add(new ActiveCell(4,0,c));
		activeCells.add(new ActiveCell(5,0,c));
		activeCells.add(new ActiveCell(4,1,c));
		activeCells.add(new ActiveCell(5,1,c));

		return true;
	}
	private boolean spawnPlusThing(){
		Cell.color c = Cell.color.PURPLE;
		activeCells.clear();
		activeCells.add(new ActiveCell(4,0,c));
		activeCells.add(new ActiveCell(3,1,c));
		activeCells.add(new ActiveCell(4,1,c));
		activeCells.add(new ActiveCell(5,1,c));
		
		return true;
	}
	
	private void setNextShape(){
		int i = (int)(Math.random()*7);
		switch(i){
			case 0:
				nextShape = shapeType.LINE;
				break;
			case 1:
				nextShape = shapeType.L;
				break;
			case 2:
				nextShape = shapeType.SQUARE;
				break;
			case 3:
				nextShape = shapeType.FLIPL;
				break;
			case 4:
				nextShape = shapeType.FLIPZ;
				break;
			case 5:
				nextShape = shapeType.PLUSTHING;
				break;
			case 6:
				nextShape = shapeType.Z;
				break;
			default:
				break;
		}
	}
	
	public boolean rotate(){
		switch(currentShape){
			case LINE:
				rotateLine();
				break;
			case L:
				rotateL();
				break;
			case FLIPL:
				rotateFlipL();
				break;
			case Z:
				rotateZ();
				break;
			case FLIPZ:
				rotateFlipZ();
				break;
			case SQUARE:
				rotateSquare();
				break;
			case PLUSTHING:
				rotatePlusThing();
				break;
			default:
				break;
			
		}
		return true;
	}
	//check for shape-specific collisions
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
	

	
	boolean canMoveDown(ActiveCell c){
		if(c.row >= 19 ||
				cells[c.row+1][c.col].occupied)
			return false;
		return true;
	}
	boolean canMoveLeft(ActiveCell c){
		if(c.col <= 0 ||
				cells[c.row][c.col-1].occupied){
			return false;
		}
		return true;
	}
	boolean canMoveRight(ActiveCell c){
		if(c.col >=9 ||
				cells[c.row][c.col+1].occupied){
			return false;
		}
		return true;
	}
	
	//checks to see if any lines should be removed, should call clearLine if needed
	void checkLineRemoval(){
		
	}
	
	//check for collisions, logic, etc etc
	//Should use ScheduledExecutorService to be the hearbeat
	public void tock(){
		//check for collision
		boolean canMoveDown = true;
		for(ActiveCell c : activeCells){
			if(!canMoveDown(c)){
				canMoveDown = false;
			}
		}
		
		//move cells down
		if(canMoveDown){
			for(ActiveCell c : activeCells){
				c.moveDown();
			}
		} else {
			transposeActiveCells();
			spawn();
		}
		mainPanel.repaint();

	}
	
	void transposeActiveCells(){
		for(ActiveCell c: activeCells){
			cells[c.row][c.col].setOccupied(true);
			cells[c.row][c.col].setColor(c.currentColor);
		}
	}
	
	private void setKeystrokes(){
		mainPanel.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "down");
		mainPanel.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
		mainPanel.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
		mainPanel.getInputMap().put(KeyStroke.getKeyStroke("UP"), "rotate");

		Action downAction = new AbstractAction(){
			public void actionPerformed(ActionEvent e) {
				tock();
			}
		};
		Action leftAction = new AbstractAction(){
			public void actionPerformed(ActionEvent e) {
				boolean canMoveLeft = true;
				for(ActiveCell c : activeCells){
					if(!canMoveLeft(c))
						canMoveLeft = false;
				}
				if(canMoveLeft){
					for(ActiveCell c : activeCells){
						c.moveLeft();
					}
				}
				mainPanel.repaint();
			}
		};
		Action rightAction = new AbstractAction(){
			public void actionPerformed(ActionEvent e) {
				boolean canMoveRight = true;
				for(ActiveCell c : activeCells){
					if(!canMoveRight(c)){
						canMoveRight = false;
					}
				}
				if(canMoveRight){
					for(ActiveCell c : activeCells){
						c.moveRight();
					}
				}
				mainPanel.repaint();
			}
		};
		Action rotateAction = new AbstractAction(){
			public void actionPerformed(ActionEvent e) {
				//do rotate stuff
			}
		};
		
		
		mainPanel.getActionMap().put("down", downAction);
		mainPanel.getActionMap().put("moveLeft",leftAction);
		mainPanel.getActionMap().put("moveRight",rightAction);
		
		
	}


}
