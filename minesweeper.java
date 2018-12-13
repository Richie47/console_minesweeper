package winterProjects;


/**
 * Currently trying to figure out how to recursively go to other cells when all the adjacent cells do not contain a bomb
 * the problem is we need to actually check all the damn cells and if THAT cell doesn't have any adjacent bombs we have to do it again, etc.....
 * I think recursion is the right call because the sweep method is a working algorithm and all we should have to do is call it again.
 * 
 * ideas:
 * send the row, column coordinates to a different method that runs the same loop but inside the loop calls the sweeper method recursively.
 * 
 * Matt said I had a loop that would check every revealed square with no bomb, and said if there's a square with no bomb next to it then reveal that too
And it would repeat that loop until it stopped revealing any

so that if a square with no bomb is revealed you  can see if there are any other squares nect to it that it should reveal
 * 
 */
import java.util.Random;
import java.util.Scanner;

public class MineSweeper {

	private int[][] grid;
	private boolean[][] recurseSearch;
	private int bombs = 1;

	public MineSweeper(int r, int c) {
		this.grid = new int[r][c];

		Random rg = new Random();
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				int bombGen = rg.nextInt(10);
				if (bombGen < 2)
					grid[i][j] = -1;
			}
		}

		this.recurseSearch = new boolean[r][c];

	}

	// checking the adjacent cells, I made -1 = to the bomb value, rest of the cells
	// are default(0)
	public void sweep(int r, int c) {

		if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length || recurseSearch[r][c]) {
			return;

		}

		int minRow = 0, minCol = 0, maxRow = 0, maxCol = 0, neighborBomb = 0;

		// means if I clicked a bomb, end the program
		if (grid[r][c] == -1) {
			System.out.println("Your selection [" + r + ", " + c + "] contained a bomb. .\nGAME OVER");
			System.exit(0);
		}

		// Series of if/else to find the min & max row col size to avoid going out of
		// bounds
		if (r == 0)
			minRow = 0;
		else
			minRow = r - 1;

		if (r == grid.length - 1)
			maxRow = r;
		else
			maxRow = r + 1;

		if (c == 0)
			minCol = 0;

		else
			minCol = c - 1;

		if (c == grid[0].length - 1)
			maxCol = c;
		else
			maxCol = c + 1;

		if (grid[r][c] == 0 && recurseSearch[r][c] == false) {

			recurseSearch[r][c] = true;
			// search adjacent cells to see how many bombs surround the cell in question
			neighborBomb = 0;
			for (int row = minRow; row <= maxRow; row++) {

				for (int col = minCol; col <= maxCol; col++) {

					if (grid[row][col] == -1) {
						neighborBomb++;
					}

				}
			}
			// cell will now display how many bombs are adjacent
			if (neighborBomb > 0) {
				grid[r][c] = neighborBomb;
				return;
			}

			else {
				sweep(r + 1, c - 1);
				sweep(r + 1, c);
				sweep(r + 1, c + 1);
				sweep(r, c + 1);
				sweep(r, c - 1);
				sweep(r - 1, c - 1);
				sweep(r - 1, c);
				sweep(r -1 , c  + 1);
			}
		}

	}

	public void gridScape() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				
				 if(grid[i][j] == 0  && recurseSearch[i][j] == false || grid[i][j] == -1)
				 System.out.print("O" + " ");
//				 else if (grid[i][j] == -1)
//					 System.out.print("B" + " ");
				 else if(recurseSearch[i][j] == true && grid[i][j] == 0)
					 System.out.print("X" + " ");
				 else
					 System.out.print(grid[i][j] + " ");
				
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int r = 6;
		int c = 6;
		// int bombs = sc.nextInt();

		MineSweeper ms = new MineSweeper(r, c);

		while (true) {
			System.out.println("----------------------------");
			ms.gridScape();
			System.out.println(
					"----------------------------\nEnter a pair of coordinates!\n----------------------------");
			int cellR = sc.nextInt();
			int cellC = sc.nextInt();
			ms.sweep(cellR, cellC);
			ms.gridScape();
		}

	}

}
