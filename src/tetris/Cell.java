package tetris;

import java.awt.Color;

public class Cell{
	public static enum color {BLACK, RED, YELLOW, GREEN, PURPLE, ORANGE, PINK, BLUE};
	public color currentColor;
	public boolean occupied;
	
	public Cell(){
		
	}
	
	public void setOccupied(boolean b){
		occupied = b;
		if(!occupied){
			setColor(color.BLACK);
		}
	}
	
	public void setColor(color c){
		currentColor = c;
	}
	
	public Color getColor(){
		switch(currentColor){
			case BLACK:
				return Color.BLACK;
			case RED:
				return Color.RED;
			case YELLOW:
				return Color.YELLOW;
			case GREEN:
				return Color.GREEN;
			case PURPLE:
				return new Color(230, 230, 250);
			case ORANGE:
				return Color.ORANGE;
			case PINK:
				return Color.PINK;
			case BLUE:
				return Color.BLUE;
			default:
				return Color.BLACK;
		}
		
	}
}
