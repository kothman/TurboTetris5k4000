package tetris;

import java.awt.Color;

public class Cell{
	public Color currentColor;
	public boolean occupied;
	
	/**
	 * Constructor.  The default cell is black and is not occupied.
	 */
	public Cell(){
		currentColor = Color.BLACK;
		occupied = false;
	}
	
	/**
	 * Sets the status of occupation.
	 * @param b the new occupation status
	 */
	public void setOccupied(boolean b){
		occupied = b;
		if(!occupied){
			setColor(Color.BLACK);
		}
	}
	
	/**
	 * Sets the color.
	 * @param c color
	 */
	public void setColor(Color c){
		currentColor = c;
	}
	
	/**
	 * Returns the color of the cell.
	 */
	public Color getColor(){
		return currentColor;
	}
}
