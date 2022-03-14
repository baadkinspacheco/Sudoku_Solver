import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class PuzzleMain {
	
	static int ROW_SIZE = 9; // number of rows
	static int COL_SIZE = 9; // number of columns
	static int QUADRANT_SIZE = 3; // size of the quadrants

	public static void main(String[] args) {
		
		// Reads the file and puts the puzzle into 2D arrayList
		List<List<Integer>> myPuzzle;
		myPuzzle = readFile(args[0]);
		
		// Iterate through the puzzle and fill the puzzle with values
		solve(myPuzzle);

		// Checks to make sure puzzle is complete
		// If not complete the user can add another value to complete puzzle
		Scanner input = new Scanner(System.in);
		while (isEmpty(myPuzzle) != true) {
			tryAgain(myPuzzle, input);	
		}
		input.close();
		
		// Print out the puzzle
		System.out.println();
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
	 * If the puzzle is not completed the user has the opportunity to add another value into the grid.
	 * If the value is not valid or is zero the user can try again.
	 * The result should be a completed puzzle.
	 * @param is a 2D list of values in the puzzle
	 * @param is a scanner object that will be used to read input from the user
	 */
	public static void tryAgain(List<List<Integer>> myPuzzle, Scanner input) {
		
		// Prints out the current puzzle
		printPuzzle(myPuzzle);
		System.out.println();
	
		
		// User can add additional value if it is valid
		System.out.println("Please enter another value in the form '(x,y) 8' to complete puzzle \n"
				+ "where (0,0) is the top left of the grid");
		
		// Collects x,y, and value from the user
		String newVal = input.nextLine();
		String[] vals = newVal.split(" ");
		int x = Integer.valueOf(String.valueOf(vals[0].charAt(1)));
		int y = Integer.valueOf(String.valueOf(vals[0].charAt(3)));
		int val = Integer.valueOf(String.valueOf(vals[1]));
			
		// Adds value to the grid
		// Need to make a check that value is valid
		myPuzzle.get(x).set(y, val);
		solve(myPuzzle);
	}
	
	/*
	 * Iterates through the rows and columns of the puzzle
	 * Looking for 'empty' spaces on the grid or '0' which are place holders
	 */
	public static void solve(List<List<Integer>> myPuzzle) {
		// Iterate through the rows
		for (int i = 0; i < ROW_SIZE; i++) {
			// Iterate through the columns
			for (int j = 0; j < COL_SIZE; j++) {
				// If the space holds a zero then we need to search for a number
				// to replace it
				if (myPuzzle.get(i).get(j) == 0) {
					insertNum(i, j, myPuzzle);
				}
			}
		}
	}
	
	/*
	 * Another way to iterate through the grid recursively... not sure if want to use 
	 */
//	public static void solve2(List<List<Integer>> myPuzzle, int x, int y) {
//		if (x <= ROW_SIZE && y <= COL_SIZE && x >= 0 && y >= 0 && myPuzzle.get(x).get(y) != 0) {
//			return;
//		}
//		if (x <= ROW_SIZE && y <= COL_SIZE && x >= 0 && y >= 0 && myPuzzle.get(x).get(y) == 0) {
//			insertNum(x, y, myPuzzle);
//		}
//		if (x <= ROW_SIZE) {
//			solve2(myPuzzle, x + 1, y);
//		}
//		if (y <= COL_SIZE) {
//			solve2(myPuzzle, x, y + 1);
//		}
//		if (x > ROW_SIZE) {
//			solve2(myPuzzle, x - 1, y);
//		}
//		if (y > COL_SIZE) {
//			solve2(myPuzzle, x, y - 1);
//		}
//	}
	
	/*
	 * @param 2D list of values in the grid
	 * @return boolean is true if there are no more empty spaces on the grid
	 */
	public static boolean isEmpty(List<List<Integer>> myPuzzle) {
		for (int i = 0; i < ROW_SIZE; i++)
			for (int j = 0; j < COL_SIZE; j++) {
				if (myPuzzle.get(i).get(j) == 0) {
					return false;
				}
			}
		return true;
	}
	
	/*
	 * Inserts a new number into the empty position
	 * 
	 * @param int row is the current row of the empty position
	 * @param int col is the current column of the empty position
	 * @param myPuzzle is a 2D array list of the puzzle we are solving 
	 */
	public static void insertNum(int row, int col, List<List<Integer>> myPuzzle) {
		
		int num = 0;
		
		// Returns value to be inserted based on rows and columns
		num = possibleRowColumn(row, col, num, myPuzzle);

		
		// This means that the above test cases were passed
		// The number does not exist in the row or the column
		if (num != 0) {
			// Locates the quadrant we are on
			int quadRow = row / 3 * 3;
			int quadCol = col / 3 * 3;
			for (int i = 0; i < QUADRANT_SIZE; i++) {
				for (int j = 0; j < QUADRANT_SIZE; j++) {
					if (myPuzzle.get(quadRow + i).get(quadCol + j) == num) {
						num = 0;
					}
				}
			}
		}
		
		// Set the index to the new or maybe not new value
		// Zero could be inserted if no answer found
		myPuzzle.get(row).set(col, num);
	
	}
	
	/*
	 * Finds possible integer between (1-9) to be inserted into the grid.
	 * Checks numbers in the same rows and columns
	 * @param row is the current row
	 * @param col is the current column
	 * @param num is the possible value to be inserted
	 * @param myPuzzle is the 2D Array list of values on the grid
	 * @return the possible value to be inserted at the (row,column) in the grid
	 */
	public static int possibleRowColumn(int row, int col, int num, List<List<Integer>> myPuzzle) {
		// Iterate through the numbers 1-9
		for (int i = 1; i < ROW_SIZE; i++) {
			// Check if the number exists in the row
			if (myPuzzle.get(row).indexOf(i) == -1) {
				num = i;
				// Check if the number exists in the column
				// Subtracting 1 in the row because index starts at 0
				// If number exists in the same column set number back to placeholder 0
				if (myPuzzle.get(i - 1).get(col) == num) {
					num = 0;
				}
			}	
		}
		return num;
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
