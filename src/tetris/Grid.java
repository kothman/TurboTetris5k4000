import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Grid extends JFrame {

	ArrayList<Shape> shapes;

	/**
	*	Creates an empty grid to hold shapes.
	*/
	public Grid() {
		//should call super first
		super();
		shapes = new ArrayList<Shape>();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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