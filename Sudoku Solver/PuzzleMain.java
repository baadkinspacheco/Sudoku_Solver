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

		// Iterate through the puzzle
		solve(myPuzzle);
		
		// Print out the puzzle
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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
	
	public static boolean isNotEmpty(List<List<Integer>> myPuzzle) {
		// Checks our condition to make sure we dont't need to keep checking
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
	 * Inserts a new number into the empty position
	 * 
	 * @param int row is the current row of the empty position
	 * @param int col is the current column of the empty position
	 * @param myPuzzle is a 2D array list of the puzzle we are solving 
	 */
	public static void insertNum(int row, int col, List<List<Integer>> myPuzzle) {
		
		int num = 0;
		
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
	
	public static void printPuzzle(List<List<Integer>> myPuzzle) {
		for (List<Integer> line : myPuzzle) {
			System.out.println(line);
		}
	}

}
