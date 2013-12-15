package tetris;

import java.awt.Color;
import java.awt.Graphics;

public class ActiveCell extends Cell {
	int col, row;
	boolean isPivot;
	
	public ActiveCell(final int col, final int row,Color c){
		super();
		this.col = col; this.row = row;
		occupied=false;
		this.currentColor = c;
	}
	
	public void moveDown(){
		row++;
	}
	
	public void moveLeft(){
		col--;
	}
	
	public void moveRight(){
		col++;
	}
	
	public void paintComponent(Graphics g){
		g.setColor(Color.WHITE);
		g.drawRect(col*(Grid.CELL_PANEL_WIDTH/Grid.NUM_OF_COLS), row*(Grid.CELL_PANEL_HEIGHT/Grid.NUM_OF_ROWS), Grid.CELL_PANEL_WIDTH/Grid.NUM_OF_COLS, Grid.CELL_PANEL_HEIGHT/Grid.NUM_OF_ROWS);
		g.setColor(currentColor);
		g.fillRect(col*(Grid.CELL_PANEL_WIDTH/Grid.NUM_OF_COLS) + 2, row*(Grid.CELL_PANEL_HEIGHT/Grid.NUM_OF_ROWS) + 2, Grid.CELL_PANEL_WIDTH/Grid.NUM_OF_COLS - 3, Grid.CELL_PANEL_HEIGHT/Grid.NUM_OF_ROWS - 3);
	}
}
