package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class Grid extends JFrame {
	private Cell[][] cells;
	private enum shapeType {LINE, L, FLIPL, Z, FLIPZ, SQUARE, PLUSTHING};
	private enum direction {LEFT, RIGHT};
	private shapeType currentShape;
	private shapeType nextShape;
	private ArrayList<ActiveCell> activeCells;
	public static final byte NUM_OF_ROWS = 20, NUM_OF_COLS = 10;
	
	private JPanel mainPanel, gridPanel, nextPanel;
	private ScheduledExecutorService ses;
	
	//mainPanel finals
	private static final int WIDTH = 500, HEIGHT = 600;
	//cellPanel finals
	public static final int CELL_PANEL_WIDTH = WIDTH-200, CELL_PANEL_HEIGHT = HEIGHT-100, OFFSET = 10;
	
	private static final int[][] ROTATION_MATRIX = {{0,-1},{1, 0}};

	/**
	*	Creates an empty grid to hold shapes.
	*/
	public Grid() {
		//should call super first
		super();
		
		//init vars
		activeCells = new ArrayList<ActiveCell>();
		
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
		
		/********************** Customize the JFrame **************/
		setSize(new Dimension(WIDTH,HEIGHT));
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("Turbo Tetris 5k4000");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setNextShape();
		spawn();
		setKeystrokes();
		
		//add Panels
		mainPanel.add(gridPanel);
		add(mainPanel);
		repaint();
		gridPanel.repaint();
		setVisible(true);
		
		//We should give tock a life here. ScheduledExecutorService
		ses = Executors.newSingleThreadScheduledExecutor();
		ses.scheduleAtFixedRate(new Runnable(){
			@Override
			public void run() {
				tock();
				
			} }, 3, 1, TimeUnit.SECONDS);
		
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
		Color c = Color.YELLOW;
		activeCells.clear();
		activeCells.add(new ActiveCell(4,0,c));
		activeCells.add(new ActiveCell(4,1,c));
		activeCells.add(new ActiveCell(4,2,c));
		activeCells.add(new ActiveCell(4,3,c));
		
		activeCells.get(2).isPivot = true; 
		
		return true;
	}
	private boolean spawnL(){
		Color c = Color.PINK;
		activeCells.clear();
		activeCells.add(new ActiveCell(4,0,c));
		activeCells.add(new ActiveCell(4,1,c));
		activeCells.add(new ActiveCell(4,2,c));
		activeCells.add(new ActiveCell(5,2,c));
		
		activeCells.get(2).isPivot = true;
		
		return true;
	}
	private boolean spawnFlipL(){
		Color c = Color.BLUE;
		activeCells.clear();
		activeCells.add(new ActiveCell(5,0,c));
		activeCells.add(new ActiveCell(5,1,c));
		activeCells.add(new ActiveCell(5,2,c));
		activeCells.add(new ActiveCell(4,2,c));

		activeCells.get(2).isPivot = true;
		
		return true;
	}
	private boolean spawnZ(){
		Color c = Color.RED;
		activeCells.clear();
		activeCells.add(new ActiveCell(3,0,c));
		activeCells.add(new ActiveCell(4,0,c));
		activeCells.add(new ActiveCell(4,1,c));
		activeCells.add(new ActiveCell(5,1,c));
		
		activeCells.get(2).isPivot = true;
		
		return true;
	}
	private boolean spawnFlipZ(){
		Color c = Color.ORANGE;
		activeCells.clear();
		activeCells.add(new ActiveCell(5,0,c));
		activeCells.add(new ActiveCell(6,0,c));
		activeCells.add(new ActiveCell(5,1,c));
		activeCells.add(new ActiveCell(4,1,c));
		
		activeCells.get(2).isPivot = true;
		
		return true;
	}
	private boolean spawnSquare(){
		Color c = Color.GREEN;
		activeCells.clear();
		activeCells.add(new ActiveCell(4,0,c));
		activeCells.add(new ActiveCell(5,0,c));
		activeCells.add(new ActiveCell(4,1,c));
		activeCells.add(new ActiveCell(5,1,c));

		return true;
	}
	private boolean spawnPlusThing(){
		Color c = new Color(1.0f, 0.0f, 1.0f);
		activeCells.clear();
		activeCells.add(new ActiveCell(4,0,c));
		activeCells.add(new ActiveCell(3,1,c));
		activeCells.add(new ActiveCell(4,1,c));
		activeCells.add(new ActiveCell(5,1,c));
		
		activeCells.get(2).isPivot = true;
		
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
			default: //This should never happen. If it does, throw an error
				try{
					throw new UnreachableCode();
				} catch (Exception e){
					System.out.println(e);
				}
		}
	}
	
	private boolean rotate(){
		if(currentShape != shapeType.SQUARE){
			//Gets a vector representation of an active cell, rotates around pivot, checks position, takes action
			for (ActiveCell ac: activeCells) {
				int[] acVector = {ac.col-getPivotCell().col, ac.row-getPivotCell().row};
				int[] pacVector = {ROTATION_MATRIX[0][0]*acVector[0]+ROTATION_MATRIX[0][1]*acVector[1],
								  ROTATION_MATRIX[1][0]*acVector[0]+ROTATION_MATRIX[1][1]*acVector[1]};
				
			}
			
		}
		return true;
	}
	
	private ActiveCell getPivotCell(){
		for(ActiveCell c : activeCells){
			if(c.isPivot){
				return c;
			}
		}
		return null;
	}
	
	private void clearLine(int line){
		for (int row = line; row > 0; row--) {
			for (int col = 0; col < NUM_OF_COLS; col++) {
				cells[row][col] = cells[row-1][col];
			}
		}
		for (int col = 0; col < NUM_OF_COLS; col++) {
			cells[0][col] = new Cell();
		}
	}
	
	private boolean canMoveDown(ActiveCell c){
		if(c.row >= 19 ||
				cells[c.row+1][c.col].occupied)
			return false;
		return true;
	}
	
	private boolean canMoveLeft(ActiveCell c){
		if(c.col <= 0 ||
				cells[c.row][c.col-1].occupied){
			return false;
		}
		return true;
	}
	
	private boolean canMoveRight(ActiveCell c){
		if(c.col >=9 ||
				cells[c.row][c.col+1].occupied){
			return false;
		}
		return true;
	}
	
	//checks to see if any lines should be removed, should call clearLine if needed
	void checkLineRemoval(){
		for (int i = 0; i < NUM_OF_ROWS; i++) {
			boolean isRowFull = true;
			for (int j = 0; j < NUM_OF_COLS && isRowFull; j++) {
				if (!cells[i][j].occupied) isRowFull = false;
			}
			if (isRowFull) clearLine(i);
		}
	}
	
	//check for collisions, logic, etc etc
	//Should use ScheduledExecutorService to be the hearbeat
	private void tock(){
		//check for collision
		boolean canMoveDown = true;
		for(ActiveCell c : activeCells){
			if(!canMoveDown(c)){
				canMoveDown = false;
			}
		}
		
		//move cells down, check logic
		if(canMoveDown){
			for(ActiveCell c : activeCells){
				c.moveDown();
			}
		} else {
			transposeActiveCells();
			checkLineRemoval();
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
				rotate();
				gridPanel.repaint();
			}
		};
		
		
		mainPanel.getActionMap().put("down", downAction);
		mainPanel.getActionMap().put("moveLeft",leftAction);
		mainPanel.getActionMap().put("moveRight",rightAction);
		mainPanel.getActionMap().put("rotate", rotateAction);
		
	}


}
