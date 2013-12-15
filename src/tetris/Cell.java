package tetris;

import java.awt.Color;

public class Cell{
	public Color currentColor;
	public boolean occupied;
	
	public Cell(){
		currentColor = Color.BLACK;
		occupied = false;
	}
	
	public void setOccupied(boolean b){
		occupied = b;
		if(!occupied){
			setColor(Color.BLACK);
		}
	}
	
	public void setColor(Color c){
		currentColor = c;
	}
	
	public Color getColor(){
		return currentColor;
	}
}
