package tetris;

import java.awt.Color;
import java.awt.Graphics;

public class ActiveCell extends Cell {
	public int col, row;
	public boolean isPivot;
	
	/**
	 * Class constructor.
	 * @param col the column the new cell will be drawn onto
	 * @param row the row the new cell will be drawn onto
	 * @param c the color of the cell
	 */
	public ActiveCell(final int col, final int row,Color c){
		super();
		this.col = col; this.row = row;
		occupied=false;
		this.currentColor = c;
	}
	
	/**
	 * Paints the cell.
	 */
	public void paintComponent(Graphics g){
		g.setColor(Color.WHITE);
		g.drawRect(col*(Grid.CELL_PANEL_WIDTH/Grid.NUM_OF_COLS), row*(Grid.CELL_PANEL_HEIGHT/Grid.NUM_OF_ROWS), Grid.CELL_PANEL_WIDTH/Grid.NUM_OF_COLS, Grid.CELL_PANEL_HEIGHT/Grid.NUM_OF_ROWS);
		g.setColor(currentColor);
		g.fillRect(col*(Grid.CELL_PANEL_WIDTH/Grid.NUM_OF_COLS) + 2, row*(Grid.CELL_PANEL_HEIGHT/Grid.NUM_OF_ROWS) + 2, Grid.CELL_PANEL_WIDTH/Grid.NUM_OF_COLS - 3, Grid.CELL_PANEL_HEIGHT/Grid.NUM_OF_ROWS - 3);
	}
	
	/**
	 * Returns a string containing the column and row of the cell.
	 * For debugging purposes.
	 */
	public String toString() {
		return ("["+col+", "+row+"]");
	}
}
