import java.util.*;

import javax.swing.JOptionPane;

/**
 * this class set the square into number of bomb surrounding and bomb
 * also spread out if the square doesn't have bomb at surrounding
 * @author Jieh Cheng
 */

public class BombSquare extends GameSquare
{
	private boolean thisSquareHasBomb = false;
	public static int MINE_PROBABILITY = 10;
	private boolean surroundBlank = true;
	private boolean surroundBomb = false;

	/**
	 * this constructor initial and generate the GameBoard
	 * @param x the x coordinate of the square requested
	 * @param y the y coordinate of the square requested
	 * @param board reference of the GameBoard class
	 */
	public BombSquare(int x, int y, GameBoard board)
	{
		super(x, y, "images/blank.png", board);

		Random r = new Random();
		thisSquareHasBomb = (r.nextInt(MINE_PROBABILITY) == 0);

	}

	/** 
	* if thisSquareHasBomb is true, the square will display the image of boom
	* if is false, then it will display the number of bomb of the surrounding square
	* if there are no bombs surrounding the square, it will display a blank image
	* also it will call spreadblank() to spread out the blank until there is a bomb surrounding the square
	**/
	public void clicked()
	{
		if (thisSquareHasBomb) {
			setImage("images/bomb.png");

			//show all bombs
			board.revealAllBombs(); 

			int choice = JOptionPane.showConfirmDialog(
				null,
				"💥 Boom! You hit a bomb!\nDo you want to play again?",
				"Game Over",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null
			);

			if (choice == JOptionPane.YES_OPTION) {
				board.restartGame();
			}else if (choice == JOptionPane.NO_OPTION) {
        		System.exit(0);  // quit only if they actually click "No"
			} 
			else if (choice == JOptionPane.CLOSED_OPTION) {
				// 👈 user clicked the ❌ — do nothing, just leave the board visible
				// maybe disable further clicks or let them still look around
				// System.out.println("Dialog closed by user");
				
			}
		}

		else
		{
			int count = surroundingBomb();
			
			if(count > 0){
				surroundBlank = false;
				surroundBomb = true;
				setImage("images/" + count + ".png");
			}

			else{
				setImage("images/0.png");
				surroundBlank = true;
				spreadblank();
			}	
				
		}
			
	}

	/**
	 * this method check the number of bomb of surrounding
	 * count variable is will increment as the bomb of surrounding increase
	 */
	public int surroundingBomb()
	{

		int count = 0;

		for(int x = -1; x <= 1; x++)
		{
			for(int y = -1; y <= 1; y++)
			{
				int newx = xLocation + x;
				int newy = yLocation + y;

				if(newx != xLocation || newy != yLocation)
				{
					BombSquare b = (BombSquare)board.getSquareAt(newx, newy);

					if(b != null && b.thisSquareHasBomb)
					{
					count++;
					}
				}
			}
		}

		return count;

	}

	/**
	* this method spread the blank out until the square count != 0
	*/

	public void spreadblank(){
		
		if(surroundBomb){
			return;
		}
		surroundBomb = true;
		int count = surroundingBomb();
		

		for(int x = -1; x <= 1; x++)
		{
			for(int y = -1; y <= 1; y++)
			{
				int newx = xLocation + x;
				int newy = yLocation + y;

				if(newx != xLocation || newy != yLocation)
				{
					
					BombSquare b = (BombSquare)board.getSquareAt(newx, newy);

					if (b instanceof BombSquare && b.surroundBlank && b != null && count == 0){
                        b.clicked();
					}
					
					else if(b instanceof BombSquare && b.surroundBomb && count > 0 && b != null){
						surroundBomb = true;
						break;
					}
				}
			}
		}	
	}

	public void revealIfBomb() {
		if (thisSquareHasBomb) {
			setImage("images/bomb.png");
		}
	}


}

