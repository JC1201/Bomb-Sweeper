import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * This class provides a graphical model of a board game.
 * The class creates a rectangular panel of clickable squares,
 * of type SmartSquare. If a square is clicked by the user, a
 * callback method is invoked upon the corresponding SmartSquare instance.
 * The class is intended to be used as a basis for tile based games.
 * @author joe finney
 */
public class GameBoard extends JFrame implements ActionListener
{
	private JPanel boardPanel = new JPanel();

	private int boardHeight;
	private int boardWidth;
	private GameSquare[][] board; 
	private Difficulty difficulty;

	/**
	 * Create a new game board of the given size.
	 * As soon as an instance of this class is created, it is visualized
	 * on the screen.
	 * 
	 * @param title the name printed in the window bar.
	 * @param width the width of the game area, in squares.
	 * @param height the height of the game area, in squares.
	 */

	public GameBoard(String title, Difficulty difficulty) {
		super();

		this.difficulty = difficulty;  // save it
		this.boardWidth = difficulty.width;
		this.boardHeight = difficulty.height;

		BombSquare.MINE_PROBABILITY = difficulty.mineProbability;

		board = new GameSquare[boardWidth][boardHeight];

		setTitle(title);
		setSize(20 + boardWidth * 20, 20 + boardHeight * 20);
		setContentPane(boardPanel);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		boardPanel.setLayout(new GridLayout(boardHeight, boardWidth));

		// initialize board squares
   		board = new GameSquare[boardWidth][boardHeight];
		for (int y = 0; y < boardHeight; y++) {
			for (int x = 0; x < boardWidth; x++) {
				board[x][y] = new BombSquare(x, y, this);
				board[x][y].addActionListener(this);
				boardPanel.add(board[x][y]);
			}
		}

		setLocationRelativeTo(null); // center window
		setVisible(true);
	}


	/**
	 * Returns the GameSquare instance at a given location. 
	 * @param x the x co-ordinate of the square requested.
	 * @param y the y co-ordinate of the square requested.
	 * @return the GameSquare instance at a given location 
	 * if the x and y co-ordinates are valid - null otherwise. 
	 */
	public GameSquare getSquareAt(int x, int y)
	{
		if (x < 0 || x >= boardWidth || y < 0 || y >= boardHeight)
			return null;

		return board[x][y];
	}

	public void actionPerformed(ActionEvent e)
	{
		// The button that has been pressed.
		GameSquare b = (GameSquare)e.getSource();
		b.clicked();
	}

	public void restartGame() {

		boardPanel.removeAll(); // clear old squares


		// Recreate the board state
		board = new GameSquare[boardWidth][boardHeight];
		for (int y = 0; y < boardHeight; y++) {
			for (int x = 0; x < boardWidth; x++) {
				board[x][y] = new BombSquare(x, y, this);
				board[x][y].addActionListener(this);
				boardPanel.add(board[x][y]);
			}
		}
		
		BombSquare.MINE_PROBABILITY = difficulty.mineProbability;


		// Refresh the panel
		boardPanel.revalidate();
		boardPanel.repaint();

	}

	public void revealAllBombs() {
		for (int y = 0; y < boardHeight; y++) {
			for (int x = 0; x < boardWidth; x++) {
				if (board[x][y] instanceof BombSquare) {
					((BombSquare) board[x][y]).revealIfBomb();
				}
			}
		}
	}

}
