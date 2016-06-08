package com.example.cseiden.sudokuui;

/**
 * The solver class contains the backtracking algorithm.  It will take an
 * initial Board and recursively search until a valid, complete board is found.
 * It will then return the solved board, or null if no solution exists.
 *
 * Design:
 *
 * @author Connor Seiden
 * @version 6-7-2016
 *
 */
public class Solver {

    /**
     * The method that is to be called in order to find the solution to the
     * board configuration that is passed to it.  It primarily just makes a
     * call to solve, passing it the initial coordinates.
     *
     * @param b			The original board configuration that is to be solved
     * @return			The solved board configuration or null if there is none
     */
    public static Board solution(Board b){
        if(!InitChecker.check(b)){
            return null;
        }
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
    private static Board solve(Board b, int x, int y){
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
     * configuration and returns an array of these boards, or null if there are
     * no valid configurations.  It does this by using the getValid() method of
     * the board.
     *
     * @param b		The board that is going to be modified
     * @param x		The current x-coordinate to be modified
     * @param y		The current y-coordinate to be modified
     * @return		A list of valid board arrangements, or null if there are none
     */
    private static Board[] getSuccessors(Board b, int x, int y){
        int[] valids = b.getValids(x, y);

        if(valids == null){ return null; }
        Board[] successors = new Board[valids.length];

        for(int i=0;i<valids.length;i++){
            successors[i] = new Board(b);
            successors[i].put(valids[i], x, y);
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
    private static int[] nextCoords(Board b, int x, int y){
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
