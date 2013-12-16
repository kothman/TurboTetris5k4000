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
}
