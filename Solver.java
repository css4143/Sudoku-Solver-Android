package com.example.cseiden.sudokuui;

/**
 * The solver class contains the backtracking algorithm.  It will take an
 * initial Board and recursively search until a valid, complete board is found.
 * It will then return the solved board, or null if no solution exists.
 *
 * Design:
 *
 * @author Connor Seiden
 * @version 2-24-2015
 *
 */
public class Solver {

    /**
     * constructor
     */
    public Solver(){
    }

    /**
     * The method that is to be called in order to find the solution to the
     * board configuration that is passed to it.  It primarily just makes a
     * call to solve, passing it the initial coordinates.
     *
     * @param b			The original board configuration that is to be solved
     * @return			The solved board configuration or null if there is none
     */
    public Board solution(Board b){
        if(b.get(0, 0)==0){
            return solve(b,0,0);
        }
        else{
            int start[] = nextCoords(b, 0, 0);
            return solve(b,start[0],start[1]);
        }
    }


    /**
     * The solve method recursively searches for a valid solution to the board.
     * It will return a valid solution if it can find one, or null if none
     * exists.
     *
     * This method utilizes a modified backtracking algorithm.
     *
     * Design:  Whereas the standard backtracking algorithm finds a list of
     * successors that may or may not be valid, and checks their validity using
     * an isValid() method, I modified it so that my getSuccessors() method
     * only returns valid configurations, eliminating the need for an isValid
     * method.  I programmed it this way in order to make it more time
     * and memory efficient.
     * Also, because getSuccessors only produces valid successor and it fills
     * spaces one at a time, once the board is full, it must be a valid and
     * complete solution.  The y-coordinate will equal 9 when all other spaces
     * on the board have been filled.
     *
     *
     * @param b		The board to find a solution for
     * @param x		The current x coordinate being modified
     * @param y		The current y coordinate being modified
     * @return		The solved board configuration or null if none exists.
     */
    private Board solve(Board b, int x, int y){
        if(y==9){
            return b;
        }
        else{
            Board successors[] = getSuccessors(b, x, y);
            if(successors!=null){
                int coords[] = nextCoords(b,x,y);
                for(int i=0;i<successors.length;i++){
                    Board solution = solve(successors[i],coords[0],coords[1]);
                    if(solution != null){
                        return solution;
                    }
                }
            }
        }
        return null;
    }


    /**
     * getSuccessors takes a current spot and finds all valid number placements
     * for that spot on the board.  It creates a different board for each valid
     * configuration and returns an array of these boards.
     *
     * Design:  For the spot currently being looked at -> get the three lists of
     * row, col, and sqr. Two ways to handle checking for which values are valid
     * for that spot.  Loop from 1 to 9 and see if all the 3 arrays do not
     * contain that number (advantage: as soon as one list contains the element
     * the rest don't have to be checked).
     * Time complexity: (O 3n^2)
     *
     * The other way is to make a list of size 10 (iCheck) and go through each
     * list once and increment the index of iCheck that is the number at the
     * spot on the list we are looping through.  When all three lists are done,
     * go through iCheck and any place that is zero, the index of that spot is a
     * valid number for the successor at the current spot being looked at.
     * Time complexity: (O 4n)
     *
     * In this case, n will always be 9, but the second method is still faster.
     *
     * The second method will be implemented due to increased efficiency and
     * overall readability.
     *
     * The last for-loop populates successors from right to left.
     *
     * @param b		The board that is going to be modified
     * @param x		The current x-coordinate to be modified
     * @param y		The current y-coordinate to be modified
     * @return		A list of valid board arrangements
     */
    private Board[] getSuccessors(Board b, int x, int y){
        int counter[] = new int[10];
        for(int i=0;i<10;i++){ counter[i] = 0; }
        int temp[];

        //check current column for valid placements
        temp = b.getCol(x);
        for(int i=0;i<9;i++){
            counter[temp[i]]++;
        }

        //check current row for valid placements
        temp = b.getRow(y);
        for(int i=0;i<9;i++){
            counter[temp[i]]++;
        }

        //check current 3x3 square for valid placements
        temp = b.getSqr(x, y);
        for(int i=0;i<9;i++){
            counter[temp[i]]++;
        }

        int sCount = 0;
        for(int i=0;i<10;i++){
            if(counter[i]==0){ sCount++; }
        }
        if(sCount==0){ return null; }
        Board[] successors = new Board[sCount];

        for(int i=0;i<10;i++){
            if(counter[i]==0){
                successors[sCount-1] = new Board(b);
                successors[sCount-1].put(i, x, y);
                sCount--;
            }
        }

        return successors;
    }

    /**
     * Returns the next set of valid coordinates to modify in the backtracking
     * algorithm.  It will pass over any places that already have values.
     *
     * Note: It would seem that if put in the situation where the only spots
     * left all already have values in them then that would cause endless
     * looping.  However, the structure of the solve method protects against
     * this.
     *
     * @param x		The current x-coordinate
     * @param y		The current y-coordinate
     * @return		An array of two elements that are the new x and y
     * 				coordinates respectively
     */
    private int[] nextCoords(Board b, int x, int y){
        int tx = x;
        int ty = y;
        boolean found = false;
        while(!found){
            if(tx!=8){
                tx++;
            }
            else{
                tx = 0;
                ty++;
            }

            if(ty==9 || b.get(tx, ty) == 0){
                found = true;
            }
        }
        int coord[] = {tx,ty};
        return coord;
    }

}