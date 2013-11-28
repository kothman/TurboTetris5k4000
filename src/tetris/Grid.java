import java.util.ArrayList;

public class Grid {

	ArrayList<Shape> shapes;

	/**
	*	Creates a grid to hold shapes/
	*/
	public Grid() {
		shapes = new ArrayList<Shape>();
	}

	/**
	*	Adds the specified shape to the grid.
	*/
	public void add(Shape s) {
		shapes.add(s);
	}

	/**
	*	Updates the grid a single tick.
	*/
	public void update() {
		
	}

	/**
	*	Returns the ArrayList of shapes stored in the grid.
	*/
	public ArrayList<Shape> getList() {
		return shapes;
	}

}
