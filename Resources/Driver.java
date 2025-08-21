import javax.swing.JOptionPane;

/**
 * this class generate out the game board by the given width and height
 */
public class Driver
{
	public static void main(String[] Args)
	{	

		Difficulty[] options = Difficulty.values();
		Difficulty chosen = (Difficulty) JOptionPane.showInputDialog(
			null,
			"Choose difficulty:",
			"Difficulty Selection",
			JOptionPane.PLAIN_MESSAGE,
			null,
			options,
			Difficulty.EASY
		);

		if (chosen != null) {
			new GameBoard("Minesweeper", chosen);
		} else {
			System.exit(0); // user cancelled
		}	}

}
