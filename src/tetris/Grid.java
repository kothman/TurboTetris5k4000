package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
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
	private shapeType currentShape;
	private shapeType nextShape;
	private ArrayList<ActiveCell> activeCells, nextCells;
	public static final byte NUM_OF_ROWS = 20, NUM_OF_COLS = 10;
	private int score, level;
	
	private JPanel mainPanel, gridPanel, scorePanel, nextPanel;
	private ScheduledExecutorService ses;
	private Action downAction, leftAction, rightAction, rotateAction;
	
	private static float LEVEL_FACTOR = 0.9f;
	
	private int currentRate;
	
	//mainPanel finals
	private static final int WIDTH = 500,
							 HEIGHT = 650;
	//cellPanel finals
	public static final int OFFSET = 10,
							CELL_PANEL_HEIGHT = HEIGHT-50,
							CELL_PANEL_WIDTH = CELL_PANEL_HEIGHT/2;
	public static final int SCORE_PANEL_X = OFFSET*2+CELL_PANEL_WIDTH,
							SCORE_PANEL_Y = OFFSET,
							SCORE_PANEL_WIDTH = WIDTH-SCORE_PANEL_X-OFFSET,
							SCORE_PANEL_HEIGHT = 2*OFFSET;
	public static final int NEXT_PANEL_X = SCORE_PANEL_X,
							NEXT_PANEL_Y = SCORE_PANEL_Y+SCORE_PANEL_HEIGHT+OFFSET,
							NEXT_PANEL_WIDTH = 90,
							NEXT_PANEL_HEIGHT = 120;
	
	private static final int[][] ROTATION_MATRIX = {{0,-1},{1, 0}};

	/**
	*	Creates an empty grid to hold shapes.
	*/
	public Grid() {
		//should call super first
		super();
		
		//init vars
		activeCells = new ArrayList<ActiveCell>();
		nextCells = new ArrayList<ActiveCell>();
		score = 0; currentRate = 1000;
		
		//populate cells
		cells = new Cell[NUM_OF_ROWS][NUM_OF_COLS];
		for(int row = 0; row < NUM_OF_ROWS; row++){
			for(int col = 0; col < NUM_OF_COLS; col++){
				Cell temp = new Cell();
				temp.occupied = false;
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
						g.setColor(cells[row][col].currentColor);
						g.fillRect(col*(CELL_PANEL_WIDTH/NUM_OF_COLS) + 2, row*(CELL_PANEL_HEIGHT/NUM_OF_ROWS) + 2, CELL_PANEL_WIDTH/NUM_OF_COLS - 3, CELL_PANEL_HEIGHT/NUM_OF_ROWS - 3);
					}
				}
				for(ActiveCell a:activeCells){
					a.paintComponent(g);
				}
			}
		};
		gridPanel.setBounds(OFFSET, OFFSET, CELL_PANEL_WIDTH, CELL_PANEL_HEIGHT);
		scorePanel = new JPanel() {
			public void paintComponent(Graphics g) {
				//Draw score
				g.setColor(Color.BLACK);
				g.drawString("Score: "+String.valueOf(score)+"  Level: "+String.valueOf(level), 0, 10);
			}
		};
		scorePanel.setBounds(2*OFFSET+CELL_PANEL_WIDTH, OFFSET, WIDTH-3*OFFSET-CELL_PANEL_WIDTH, 2*OFFSET);
		nextPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				//Draw each black square with white edges
				for (int row = 0; row < 4; row++) {
					for (int col = 0; col < 3; col++) {
						g.setColor(Color.WHITE);
						g.drawRect(30*col, 30*row, 30, 30);
						g.setColor(Color.BLACK);
						g.fillRect(30*col+1, 30*row+1, 29, 29);
					}
				}
				for (ActiveCell cell: nextCells) {
					g.setColor(Color.BLACK);
					g.drawRect(30*cell.col+1, 30*cell.row+1, 28, 28);
					g.setColor(cell.currentColor);
					g.fillRect(30*cell.col+2, 30*cell.row+2, 27, 27);
				}
			}
		};
		nextPanel.setBounds(NEXT_PANEL_X, NEXT_PANEL_Y, NEXT_PANEL_WIDTH, NEXT_PANEL_HEIGHT);
		
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
		mainPanel.add(scorePanel);
		mainPanel.add(nextPanel);
		add(mainPanel);
		repaint();
		gridPanel.repaint();
		scorePanel.repaint();
		nextPanel.repaint();
		setVisible(true);
		
		//We should give tock a life here. ScheduledExecutorService
		ses = Executors.newSingleThreadScheduledExecutor();
		ses.scheduleAtFixedRate(new Runnable(){
			@Override
			public void run() {
				tock();
			} }, 3000, currentRate, TimeUnit.MILLISECONDS);
		
		//Load all of the sounds
		SoundEffect.init();
		SoundEffect.START.play();
	}
	
	/**
	 * Spawns the next shape.  Returns false if there is no room left.
	 */
	//Called after current cells are placed down. If returns false, game over
	private boolean spawn(){
		//pick a random spawn!!!
		currentShape = nextShape;
		setNextShape();
		boolean success = true;
		switch(currentShape){
			case LINE:
				success = spawnLine();
				break;
			case L:
				success = spawnL();
				break;
			case FLIPL:
				success = spawnFlipL();
				break;
			case Z:
				success = spawnZ();
				break;
			case FLIPZ:
				success = spawnFlipZ();
				break;
			case SQUARE:
				success = spawnSquare();
				break;
			case PLUSTHING:
				success = spawnPlusThing();
				break;
			default:
				break;
		}
		return success;
	}
	private boolean spawnLine(){
		Color c = Color.YELLOW;
		activeCells.clear();
		int[][] coords = {{4,0},{4,1},{4,2},{4,3}};
		for (int[] coord : coords) {
			if (cells[coord[1]][coord[0]].occupied) return false;
			activeCells.add(new ActiveCell(coord[0], coord[1], c));
		}
		activeCells.get(2).isPivot = true;
		return true;
	}
	private boolean spawnL(){
		Color c = Color.PINK;
		activeCells.clear();
		int[][] coords = {{4,0},{4,1},{4,2},{5,2}};
		for (int[] coord : coords) {
			if (cells[coord[1]][coord[0]].occupied) return false;
			activeCells.add(new ActiveCell(coord[0], coord[1], c));
		}
		activeCells.get(2).isPivot = true;
		return true;
	}
	private boolean spawnFlipL(){
		Color c = Color.BLUE;
		activeCells.clear();
		int[][] coords = {{5,0},{5,1},{5,2},{4,2}};
		for (int[] coord : coords) {
			if (cells[coord[1]][coord[0]].occupied) return false;
			activeCells.add(new ActiveCell(coord[0], coord[1], c));
		}
		activeCells.get(2).isPivot = true;
		return true;
	}
	private boolean spawnZ(){
		Color c = Color.RED;
		activeCells.clear();
		int[][] coords = {{3,0},{4,0},{4,1},{5,1}};
		for (int[] coord : coords) {
			if (cells[coord[1]][coord[0]].occupied) return false;
			activeCells.add(new ActiveCell(coord[0], coord[1], c));
		}
		activeCells.get(2).isPivot = true;
		return true;
	}
	private boolean spawnFlipZ(){
		Color c = Color.ORANGE;
		activeCells.clear();
		int[][] coords = {{5,0},{6,0},{5,1},{4,1}};
		for (int[] coord : coords) {
			if (cells[coord[1]][coord[0]].occupied) return false;
			activeCells.add(new ActiveCell(coord[0], coord[1], c));
		}
		activeCells.get(2).isPivot = true;
		return true;
	}
	private boolean spawnSquare(){
		Color c = Color.GREEN;
		activeCells.clear();
		int[][] coords = {{4,0},{5,0},{4,1},{5,1}};
		for (int[] coord : coords) {
			if (cells[coord[1]][coord[0]].occupied) return false;
			activeCells.add(new ActiveCell(coord[0], coord[1], c));
		}
		activeCells.get(2).isPivot = true;
		return true;
	}
	private boolean spawnPlusThing(){
		Color c = new Color(0.5f, 0.0f, 1.0f);
		activeCells.clear();
		int[][] coords = {{4,0},{3,1},{4,1},{5,1}};
		for (int[] coord : coords) {
			if (cells[coord[1]][coord[0]].occupied) return false;
			activeCells.add(new ActiveCell(coord[0], coord[1], c));
		}
		activeCells.get(2).isPivot = true;
		return true;
	}
	
	
	private void setNextShape(){
		int i = (int)(Math.random()*7);
		nextCells.clear();
		switch(i){
			case 0:
				nextShape = shapeType.LINE;
				nextCells.add(new ActiveCell(0,0,Color.YELLOW));
				nextCells.add(new ActiveCell(0,1,Color.YELLOW));
				nextCells.add(new ActiveCell(0,2,Color.YELLOW));
				nextCells.add(new ActiveCell(0,3,Color.YELLOW));
				break;
			case 1:
				nextShape = shapeType.L;
				nextCells.add(new ActiveCell(0,0,Color.PINK));
				nextCells.add(new ActiveCell(0,1,Color.PINK));
				nextCells.add(new ActiveCell(0,2,Color.PINK));
				nextCells.add(new ActiveCell(1,2,Color.PINK));
				break;
			case 2:
				nextShape = shapeType.SQUARE;
				nextCells.add(new ActiveCell(0,0,Color.GREEN));
				nextCells.add(new ActiveCell(0,1,Color.GREEN));
				nextCells.add(new ActiveCell(1,0,Color.GREEN));
				nextCells.add(new ActiveCell(1,1,Color.GREEN));
				break;
			case 3:
				nextShape = shapeType.FLIPL;
				nextCells.add(new ActiveCell(1,0,Color.BLUE));
				nextCells.add(new ActiveCell(1,1,Color.BLUE));
				nextCells.add(new ActiveCell(1,2,Color.BLUE));
				nextCells.add(new ActiveCell(0,2,Color.BLUE));
				break;
			case 4:
				nextShape = shapeType.FLIPZ;
				nextCells.add(new ActiveCell(1,0,Color.ORANGE));
				nextCells.add(new ActiveCell(2,0,Color.ORANGE));
				nextCells.add(new ActiveCell(1,1,Color.ORANGE));
				nextCells.add(new ActiveCell(0,1,Color.ORANGE));
				break;
			case 5:
				nextShape = shapeType.PLUSTHING;
				nextCells.add(new ActiveCell(1,0,new Color(0.5f, 0.0f, 1.0f)));
				nextCells.add(new ActiveCell(1,1,new Color(0.5f, 0.0f, 1.0f)));
				nextCells.add(new ActiveCell(0,1,new Color(0.5f, 0.0f, 1.0f)));
				nextCells.add(new ActiveCell(2,1,new Color(0.5f, 0.0f, 1.0f)));
				break;
			case 6:
				nextShape = shapeType.Z;
				nextCells.add(new ActiveCell(0,0,Color.RED));
				nextCells.add(new ActiveCell(1,0,Color.RED));
				nextCells.add(new ActiveCell(1,1,Color.RED));
				nextCells.add(new ActiveCell(2,1,Color.RED));
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
			//Gets a vector representation of an active cell, rotates around pivot, checks position...
			boolean isRotable = true;
			ArrayList<Integer[]> acVectors = new ArrayList<Integer[]>();
			ArrayList<Integer[]> pacVectors = new ArrayList<Integer[]>();
			ActiveCell pivotCell = getPivotCell();
			for (int i = 0; i < activeCells.size() && isRotable; i++) {
				Integer[] acVector = {activeCells.get(i).col-pivotCell.col, activeCells.get(i).row-pivotCell.row};
				Integer[] pacVector = {ROTATION_MATRIX[0][0]*acVector[0]+ROTATION_MATRIX[0][1]*acVector[1],
									   ROTATION_MATRIX[1][0]*acVector[0]+ROTATION_MATRIX[1][1]*acVector[1]};
				acVectors.add(acVector);
				pacVectors.add(pacVector);
				try {
					isRotable = !cells[(pivotCell.row+pacVector[1])][(pivotCell.col+pacVector[0])].occupied;
				}
				catch (ArrayIndexOutOfBoundsException e) {
					isRotable = false;
				}
			}
			//Rotates if none of the vectors failed the check
			if (isRotable) {
				for (int i = 0; i < activeCells.size(); i++) {
					activeCells.get(i).col = pivotCell.col+pacVectors.get(i)[0];
					activeCells.get(i).row = pivotCell.row+pacVectors.get(i)[1];
				}
				return true;
			}
		}
		return false;
	}
	
	private ActiveCell getPivotCell(){
		for(ActiveCell c : activeCells){
			if(c.isPivot){
				return c;
			}
		}
		return null;
	}

	private void clearLine(int line) {
		for (int row = line; row > 0; row--) {
			for (int col = 0; col < NUM_OF_COLS; col++) {
				cells[row][col] = cells[row-1][col];
			}
		}
		for (int col = 0; col < NUM_OF_COLS; col++) {
			cells[0][col] = new Cell();
		}
		score++;
		SoundEffect.LINE.play();
		if (score%10 == 0) {
			ses.shutdown();
			ses = Executors.newSingleThreadScheduledExecutor();
			currentRate = (int) (currentRate*LEVEL_FACTOR);
			ses.scheduleAtFixedRate(new Runnable(){
				@Override
				public void run() {
					tock();
				} 
			}, 0, currentRate, TimeUnit.MILLISECONDS);
			level++;
			SoundEffect.LEVEL.play();
		}
		mainPanel.repaint();
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
		for (int row = 0; row < NUM_OF_ROWS; row++) {
			boolean isRowFull = true;
			for (int col = 0; col < NUM_OF_COLS && isRowFull; col++) {
				if (!cells[row][col].occupied) isRowFull = false;
			}
			if (isRowFull) clearLine(row);
		}
	}
	
	//Stop tocking, progressively fill up the grid with white blocks
	private void endGame() {
		ses.shutdown();
		
		activeCells.clear();
		for (int row = 0; row < NUM_OF_ROWS; row++) {
			for (int col = 0; col < NUM_OF_COLS; col++) {
				cells[row][col].occupied = true;
			}
		}
		SoundEffect.END.play();
		for (int row = 19; row >= 0; row--) {
			for (int col = 0; col < NUM_OF_COLS; col++) {
				cells[row][col].currentColor = Color.WHITE;
				mainPanel.repaint();
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Moves the active cells down or spawns more active cells, other logic, etc...
	 * Could be considered a "turn"
	 */
	private boolean tock(){
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
				c.row++;
			}
		} else {
			transposeActiveCells();
			checkLineRemoval();
			if (!spawn()) {
				endGame();
				return false;
			}
		}
		mainPanel.repaint();
		return true;
	}

	/**
	 * Places ActiveCells into cells[][].
	 */
	void transposeActiveCells(){
		for(ActiveCell c: activeCells){
			cells[c.row][c.col].occupied = true;
			cells[c.row][c.col].currentColor = c.currentColor;
		}
		activeCells.clear();
	}
	
	/**
	 * Sets key bindings.
	 */
	private void setKeystrokes() {
		mainPanel.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "down");
		mainPanel.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
		mainPanel.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
		mainPanel.getInputMap().put(KeyStroke.getKeyStroke("UP"), "rotate");

		downAction = new AbstractAction(){
			public void actionPerformed(ActionEvent e) {
				tock();
			}
		};
		leftAction = new AbstractAction(){
			public void actionPerformed(ActionEvent e) {
				boolean canMoveLeft = true;
				for(ActiveCell c : activeCells){
					if(!canMoveLeft(c))
						canMoveLeft = false;
				}
				if(canMoveLeft){
					for(ActiveCell c : activeCells){
						c.col--;
					}
				}
				mainPanel.repaint();
			}
		};
		rightAction = new AbstractAction(){
			public void actionPerformed(ActionEvent e) {
				boolean canMoveRight = true;
				for(ActiveCell c : activeCells){
					if(!canMoveRight(c)){
						canMoveRight = false;
					}
				}
				if(canMoveRight){
					for(ActiveCell c : activeCells){
						c.col++;
					}
				}
				mainPanel.repaint();
			}
		};
		rotateAction = new AbstractAction(){
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
