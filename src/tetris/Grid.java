import java.util.ArrayList;
import java.swing.JFrame;
import java.swing.JPanel;

public class Grid extends JFrame {

	ArrayList<Shape> shapes;

	/**
	*	Creates an empty grid to hold shapes.
	*/
	public Grid() {
		shapes = new ArrayList<Shape>();
		this.setDefaultCloseOperation("EXIT_ON_CLOSE");
		super()
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