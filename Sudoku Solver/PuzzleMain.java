/*
 * AUTHOR: Brooke Adkins
 * DESCRIPTION: This program solves a Sudoku puzzle.
 * The user can input their Sudoku puzzle as a file in the 
 * command line. The completed puzzle is shown to the user.
 * 
 * USAGE:
 * java PuzzleMain file
 * 
 * The file passed in as an argument will look something like this.s
 * 
 * ------------ EXAMPLE Input -------------
 * Input file:
 * ----------------------------------------
 * | 2,6,4,0,1,5,8,3,9
 * | 0,3,7,8,9,2,6,4,5
 * | 5,9,8,0,0,6,0,7,1
 * | 4,2,3,1,7,8,5,9,0
 * | 8,1,6,0,4,0,7,2,3
 * | 7,5,9,6,2,3,0,0,0
 * | 3,7,5,2,0,0,9,6,4
 * | 9,8,0,3,6,4,1,0,7 
 * | 6,0,1,9,5,7,0,8,2
 * ----------------------------------------
 * 
 * The lines above are supported by this program. 
 * 0 represents empty coordinates on the grid.
 * 
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class PuzzleMain {
	
	static int ROW_SIZE = 9; // number of rows
	static int COL_SIZE = 9; // number of columns
	static int QUADRANT_SIZE = 3; // size of the quadrants
	static int BOARD_SIZE = 9; // size if of the board

	public static void main(String[] args) {
		
		// Reads the file and puts the puzzle into 2D arrayList
		List<List<Integer>> myPuzzle;
		myPuzzle = readFile(args[0]);
		
		// Solve the puzzle
		// Prints out a message to the user
		if(solvePuzzle(myPuzzle)) {
			System.out.println("Congratulations you did it!\n");
		}
		
		// Prints out the resulting puzzle
		printPuzzle(myPuzzle);
}
	
	/*
	 * Reads the file
	 * @param is the name of the file
	 * @return is a 2D array list of the original puzzle
	 */
	public static List<List<Integer>> readFile(String filename) {
		try {
			// File obj and scanner
			File file = new File(filename);
			Scanner scan = new Scanner(file);
			
			// For right now 0 represents empty space on grid
			// This will hold our puzzle
			List<List<Integer>> myPuzzle = new ArrayList<List<Integer>>();
			
			// Add the contents of the file to 2D ArrayList
			while (scan.hasNextLine()) {
				List<Integer> temp = new ArrayList<Integer>();
				String[] line = scan.nextLine().split(",");
				for (String num : line) {
					temp.add(Integer.valueOf(num));
				}
				myPuzzle.add(temp);	
			}
			scan.close();
			return myPuzzle;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * Iterates through the rows and columns 
	 * @param myPuzzle is the 2D Array list of values on the grid
	 * @returns true if the puzzle is completed
	 */
	public static boolean isEmpty(List<List<Integer>> myPuzzle) {
		for (int i = 0; i < ROW_SIZE; i++) {
			for (int j = 0; j < COL_SIZE; j++) {
				if (myPuzzle.get(i).get(j) == 0) {
					return false;
				}
			}
		}
		return true;
	}
	
	/*
	 * Solves the sudoku puzzle
	 * @param myPuzzle is the 2D Array list of values on the grid
	 * @return true if the puzzle is completed
	 */
	public static boolean solvePuzzle(List<List<Integer>> myPuzzle) {
		// Base case -- there are no more empty positions on board
		if(isEmpty(myPuzzle)) {
			return true;
		}
		// Iterate through the rows
		for (int row = 0; row < ROW_SIZE; row++) {
			// Iterate through the columns
			for (int col = 0; col < COL_SIZE; col++) {
				// If there is an empty position 
				if (myPuzzle.get(row).get(col) == 0) {
					// Iterate through possible inputs 1-9
					for (int val = 1; val <= BOARD_SIZE ; val++) {
						// If the value is valid placement on board recurse
						if (isValid(row, col, val, myPuzzle)) {
							// Plug value into puzzle
							myPuzzle.get(row).set(col, val);
							// Explore -- continue filling out board
							if (solvePuzzle(myPuzzle)) {
								return true;
							}
							// We hit a dead end -- remove value from board
							myPuzzle.get(row).set(col, 0);
						}
					}
				}
			}
		}
		// Puzzle was not able to be completed
		System.out.println("Puzzle could not be completed");
		return false;
	}
	
	/*
	 * Checks if value is valid input for the position on board
	 * @param row is the current row
	 * @param col is the current column
	 * @param num is the possible value to be inserted
	 * @param myPuzzle is the 2D Array list of values on the grid
	 * @return true if value is valid 
	 */
	public static boolean isValid(int row, int col, int val, List<List<Integer>> myPuzzle) {
		// Returns false if not a valid column
		if (!validColumn(col, val, myPuzzle)) {
			return false;
		}
		// Returns false if not a valid row
		if (!validRow(row, val, myPuzzle)) {
			return false;
		}
		// Returns false if not a valid quadrant 
		if (!validQuad(row, col, val, myPuzzle)) {
			return false;
		}
		return true;
	}
	
	/*
	 * Returns true if the number does not exist in the quadrant 
	 * @param col is the current column 
	 * @param num is the number that might be a valid input for the puzzle
	 * @param myPuzzle is a 2D array list
	 */
	public static boolean validQuad(int row, int col, int val, List<List<Integer>> myPuzzle) {
		int quadRow = row / 3 * 3;
		int quadCol = col / 3 * 3;
		for (int i = 0; i < QUADRANT_SIZE; i++) {
			for (int j = 0; j < QUADRANT_SIZE; j++) {
				if (myPuzzle.get(quadRow + i).get(quadCol + j) == val) {
					return false;
				}
			}
		}
		return true;
	}
	
	/*
	 * Returns true if the number does not exist in the row
	 * @param col is the current column 
	 * @param num is the number that might be a valid input for the puzzle
	 * @param myPuzzle is a 2D array list
	 */
	public static boolean validRow(int row, int value, List<List<Integer>> myPuzzle) {
		if (myPuzzle.get(row).indexOf(value) == -1) {
			return true;
		}
		return false;
	}
	
	/*
	 * Returns true if the number does not exist in the column
	 * @param col is the current column 
	 * @param num is the number that might be a valid input for the puzzle
	 * @param myPuzzle is a 2D array list
	 */
	public static boolean validColumn(int col, int num, List<List<Integer>> myPuzzle) {
		for (int i = 0; i < ROW_SIZE; i++) {
			if (myPuzzle.get(i).get(col) == num) {
				return false;
			}
		}
		return true;
	}
	
	/*
	 * Displays the puzzle in the console
	 * @param is a 2D list of puzzle values
	 */
	public static void printPuzzle(List<List<Integer>> myPuzzle) {
		for (List<Integer> line : myPuzzle) {
			System.out.println(line);
		}
	}

}
