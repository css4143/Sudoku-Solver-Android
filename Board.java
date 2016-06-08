package com.example.cseiden.sudokuui;

/**
 * A representation of a sudoku board.  Stores the board state as a simple 2-d
 * array of ints.
 * IMPORTANT: All the methods in this class that deal with indices of the board
 * are all designed to be zero-indexed.  So the first column is x-0 and the
 * third column is x-2, etc.  Same for the rows.
 *
 * @author Connor Seiden
 * @version 6-7-2016
 *
 */
public class Board {
    private int board[][];

    /**
     * The constructor method.
     * For now it simply initializes the board size and initializes all spots
     * as 0.
     *
     * @return		A new initialized board
     */
    public Board(){
        board = new int[9][9];
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                board[i][j] = 0;
            }
        }
    }

    /**
     * A second instance of the constructor that takes a Board as a parameter
     * and copies the elements in is board to its own.
     *
     * @param b		The Board object who's board will be copied
     * @return		A new initialized board
     */
    public Board(Board b){
        board = new int[9][9];
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                board[i][j] = b.get(i, j);
            }
        }
    }

    /**
     * Return the value at the specified spot on the board.
     *
     * @param x		the x-coordinate
     * @param y		the y-coordinate
     * @return		the value at that spot, 0 means blank
     */
    public int get(int x, int y){
        return board[x][y];
    }

    /**
     * Put v into the specified place on the board.
     *
     * @param v		The value to insert
     * @param x		The x-coordinate
     * @param y		The y-coordinate
     */
    public void put(int v, int x, int y){
        board[x][y] = v;
    }

    /**
     * Returns a list containing all the values from a given column of the
     * sudoku board.  So getCol(3) will return a list containing the values
     * in the fourth column from the left on the sudoku board.
     *
     * @param x		The column whose values will be returned.
     * @return		An array of ints that represent the values in the specified
     * 				column on the board.
     */
    public int[] getCol(int x){
        return board[x];
    }

    /**
     * Returns an array of ints containing all the values in a given row on the
     * sudoku board.
     * Coded in such a way for readability and to demonstrate/test Java array creation.
     *
     * @param y		The y index of the row to return
     * @return		An array of ints that represent the values in the specified
     * 				row on the board.
     */
    public int[] getRow(int y){
        int row[] = {board[0][y],board[1][y],board[2][y],board[3][y],
                board[4][y], board[5][y],board[6][y],board[7][y],board[8][y]};
        return row;
    }

    /**
     * Returns a list containing the values of the 3 by 3 block that the given
     * x and y coordinates are currently occupying.  This way, you can check
     * for duplicates within the current 3 by 3 block.
     *
     * @param x		The current x-coordinate
     * @param y		The current y-coordinate
     * @return		An array of ints containing the values from the sudoku
     * 				board of the current 3 by 3 area.
     */
    public int[] getSqr(int x, int y){
        int tx;
        int ty;

        //find x coordinate of current 3x3 board square
        if(x<=2){ tx=0; }
        else if(x<=5){ tx=3; }
        else{ tx=6; }
        //find y coordinate of current 3x3 board square
        if(y<=2){ ty=0; }
        else if(y<=5){ ty=3; }
        else{ ty=6; }

        int sqr[] = {board[tx][ty], board[tx][ty+1], board[tx][ty+2],
                board[tx+1][ty], board[tx+1][ty+1], board[tx+1][ty+2],
                board[tx+2][ty], board[tx+2][ty+1], board[tx+2][ty+2]};
        return sqr;
    }

    /**
     * This method takes a current space and finds all valid number placements
     * for that space on the board.  It then returns these numbers in an array,
     * or null if there are no valid number placements for this space.
     *
     * Design:  For the space currently being looked at -> get the three lists
     * of row, col, and sqr. Two ways to handle checking for which values are
     * valid for that spot.  Loop from 1 to 9 and see if all the 3 arrays do
     * not contain that number (advantage: as soon as one list contains the
     * element the rest don't have to be checked).
     * Time complexity: (O 3n^2)
     *
     * The other way is to make a list of size 10 (counter) and go through each
     * list once and increment the index of counter that is the number at the
     * spot on the list we are looping through.  When all three lists are done,
     * go through counter and any place that is zero, the index of that space
     * is a valid number for the current space being looked at.
     * Time complexity: (O 4n)
     *
     * In this case, n will always be 9, but the second method is still faster.
     *
     * The second method will be implemented due to increased efficiency and
     * overall readability.
     *
     * The last for-loop populates valids from right to left.
     *
     * @param x		The current x-coordinate
     * @param y		The current y-coordinate
     * @return		An array containing all valid numbers for this space
     */
    public int[] getValids(int x, int y){
        int counter[] = new int[10];
        for(int i=0;i<10;i++){ counter[i] = 0; }
        int temp[];

        //check current column for valid placements
        temp = getCol(x);
        for(int i=0;i<9;i++){
            counter[temp[i]]++;
        }

        //check current row for valid placements
        temp = getRow(y);
        for(int i=0;i<9;i++){
            counter[temp[i]]++;
        }

        //check current 3x3 square for valid placements
        temp = getSqr(x, y);
        for(int i=0;i<9;i++){
            counter[temp[i]]++;
        }

        int sCount = 0;
        for(int i=1;i<10;i++){
            if(counter[i]==0){ sCount++; }
        }
        if(sCount==0){ return null; }
        int[] valids = new int[sCount];

        for(int i=1;i<10;i++){
            if(counter[i]==0){
                valids[sCount-1] = i;
                sCount--;
            }
        }

        return valids;
    }

    /**
     * Print a representation of the board to standard output
     *
     */
    public void printBoard(){
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                System.out.print(board[j][i]+" ");
                if(j==2 || j==5){
                    System.out.print("| ");
                }
            }
            if(i==2 || i==5){
                System.out.print("\n---------------------");
            }
            System.out.print("\n");
        }
    }

}
