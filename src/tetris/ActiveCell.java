package tetris;

import java.awt.Color;
import java.awt.Graphics;

public class ActiveCell extends Cell {
	int col, row;
	boolean isPivot;
	
	ActiveCell(final int col, final int row,Cell.color c){
		super();
		this.col = col; this.row = row;
		occupied=false;
		this.currentColor = c;
	}
	void moveDown(){
		row++;
	}
	void moveLeft(){
		col--;
	}
	void moveRight(){
		col++;
	}
	public void paintComponent(Graphics g){
		g.setColor(Color.WHITE);
		g.drawRect(col*(Grid.CELL_PANEL_WIDTH/Grid.NUM_OF_COLS), row*(Grid.CELL_PANEL_HEIGHT/Grid.NUM_OF_ROWS), Grid.CELL_PANEL_WIDTH/Grid.NUM_OF_COLS, Grid.CELL_PANEL_HEIGHT/Grid.NUM_OF_ROWS);
		g.setColor(this.getColor());
		g.fillRect(col*(Grid.CELL_PANEL_WIDTH/Grid.NUM_OF_COLS) + 2, row*(Grid.CELL_PANEL_HEIGHT/Grid.NUM_OF_ROWS) + 2, Grid.CELL_PANEL_WIDTH/Grid.NUM_OF_COLS - 3, Grid.CELL_PANEL_HEIGHT/Grid.NUM_OF_ROWS - 3);
	}
}
