package apcs;

import java.io.*;
import java.util.Scanner;

/**
 * represents a single Magic square
 * @author Ryan Gallego
 * 
 */
public class MagicSquare {
	private int[][] grid;
	private int magicSum;

	/**
	 * Creates a magic square of <code>size</code> size
	 * @param size must be an odd integer >= 3 or a multiple of 4.
        * @throws an IllegalArgumentException if size is not valid.
	 */
	public MagicSquare(int size)	{
            //sees if size if valid, throws illegal argument exception if not
            //size invalid if it is less than or not divisible by 4
            if ((size <= 3) && (size % 2 != 1)) {
                throw new IllegalArgumentException("size invalid");
            }
            else if ((size % 4 != 0) && (size % 2 == 0)) {
                throw new IllegalArgumentException("size invalid");
            }
            //declares starting position for the first number 
            grid = new int[size][size];
            int x = size/2;
            int y = 0;
            for (int c = 1; c <= (size*size); c++) {
                grid[y][x] = c;
                y--;
                x++;
                
                //if y is at the top of the grid, it moves down to the bottom
                if (y < 0) { 
                    y += (size);
                }
                //if x is all the way at the right, then it moves to the left
                if (x > (size - 1)) {
                    x -= size;
                }
                
                //if there is a number in the box, the number moves down and goes from there
                if ((grid[y][x]) != 0) {
                    y++;
                    y++;
                    x--;
                }
                
                //if y is all the way at the bottom, then it moves to the top
                if (y > (size - 1)) {
                    y -= size;
                }
                
                //if x is all the way at the left, then it moves to the right
                if (x < 0) {
                    x += size;
                }
                
            }
        
        //calculates magic sum by adding all values in one column
        //all magic sums in every column are equal, so we only need to count one column
        magicSum = 0;
        int sum = 0;
        for (int c = 0; c < grid[0].length; c++) {
            sum += grid[0][c];
        }
        magicSum = sum;
        //store the sum of one of the rows in magicSum
	}

   public int getMagicSum() {
      return magicSum;
   }

   
	/**
	 * Creates a MagicSquare from the Scanner
	 * @param scan - the file source for the numbers where the first line contains the dimension.
	 */
	public MagicSquare(Scanner scan) {
		int size = Integer.parseInt(scan.nextLine().trim());
		grid = new int[size][size];
		for(int r = 0; r < size; r ++)
			for(int c = 0; c < size; c ++)
				grid[r][c] = scan.nextInt();
      
      //compute the magic sum field.
	}
   
   /**
    * Saves this magic square into a text file that matched the format
    * of the file type read in by the constructor.
    * @param f - The file where this magic square will be saved.
    */
   public void saveFile(File f) {
      try(PrintWriter pw = new PrintWriter(f);) {
         pw.println(size());
         for(int r = 0; r < size(); r ++) {
            for(int c = 0; c < size(); c ++)
               pw.print(grid[r][c] + " ");
            pw.println();
         }
      }
      catch(IOException utoh) {
         System.out.println("Could not save magic square to " + f.getAbsolutePath());
         System.out.println(utoh);
      }
   }
   
   /**
    * Determines if this square really is a magic square where 
    * all rows and columns are equal.
    * 
    * @return true if the rows and columns and diagonals all have the same sum
    * and every element is distinct.
    */
	public boolean isMagicSquare () {
            //bunch of variables which are pretty self explanatory 
            int totalRow = 0;
            int totalCol = 0;
            int totalDiag1 = 0;
            int totalDiag2 = 0;
            int c1 = grid.length;
            int c2 = 0;
            
            /**
             * finds total for each column using an enhanced for loop to loop through
             * each column in every row
             */
            for (int r  = 0; r < grid[0].length; r++) {   
                for (int c = 0; c < grid.length; c++) {
                    totalCol += grid[r][c];
                }
            }
            
            /**
             * finds total for each row using an enhanced for loop to loop through
             * each row in every column
             */
            for (int c = 0; c < grid[0].length; c++) {
                for (int r = 0; r < grid.length; r++) {
                    totalRow += grid[r][c];
                }
            }
           
            /**
             * finds total for diagonal starting at top left and ending at top right
             */
            for (int r = 0; r < grid[0].length; r++) {
                totalDiag1 += grid[r][c2];
                c2++;
            }
            
            /**
             * finds total for diagonal starting at top right and ending at top left
             */
            for (int r = grid.length-1; r >= 0; r--) {
                totalDiag1 += grid[r][r];
                
            }
            
            //if all the values are equal, prints out true. If not, prints out false
            if ((totalCol == totalRow)  && (totalDiag1 == totalDiag2) && (totalDiag1 == totalRow)) {
                return true;
            }
            else {
                return false;
            }
            
            
        }

	/**
	 *
	 *  @author Anthony G. improved toString method
	 */
	public String toString () {
		StringBuffer sb = new StringBuffer(grid.length * grid.length * 4);
		int count = 0;
		while(count < grid.length) {
			sb.append("------");
			count ++;
		}
		sb.append("-\n|");
		for(int[] element : grid) {
			for(int element2 : element) {
				if(element2 < 10)
					sb.append("   " + element2 + " |");
				else if(element2 < 100)
					sb.append("  " + element2 + " |");
				else
					// element2 < 1000
					sb.append(" " + element2 + " |");
         }
			sb.append("\n");
			count = 0;
			while(count < grid.length)	{
				sb.append("------");
				count ++;
			}
			sb.append("-\n|");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("\nThe Magic sum for this square is: " + magicSum);
		return sb.toString();
	}

	/**
	 *  @returns the integer stored at row r and column c.
	 *  If an invalid row or column number is supplies, returns -999.
	 *
	 */
	public int get(int r, int c){ 
		if(r>=0 && r < grid.length && c>=0 && c < grid.length)
			return this.grid[r][c];
		else
			throw new IllegalArgumentException("Invalid row or column value: " +r +"," + c);
	}
	
	public int size() {
      if(grid==null)
         return -1;
      else
         return this.grid.length;
	}	
		
	public static void main(String[] args) {
		File textFile = new File("S:\\Mr. LaSpina\\apcs\\MagicSquare6.txt");
      try(Scanner fileIn = new Scanner(textFile);)	{
			MagicSquare testSquare = new MagicSquare(fileIn);
         //System.out.println(testSquare);
         if(testSquare.isMagicSquare())
            System.out.println("Valid magic square");
         else
            System.out.println("NOT a valid magic square.");
		} catch(FileNotFoundException ae) {
			System.out.println("File not found: " + textFile.getAbsolutePath());
         
		}
            
            MagicSquare test = new MagicSquare(5);
            System.out.println(test);
            test.isMagicSquare();
      //to read from keyboard input use fileIn = new Scanner(System.in);
	} //end of main		
}