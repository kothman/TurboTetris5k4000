package tetris;

public class Start {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				Grid g = new Grid();
				System.out.println("Ran run run ran run...");	
			}	
		});
	}
}