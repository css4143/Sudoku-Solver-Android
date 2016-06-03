package com.example.cseiden.sudokuui;

/**
 * A representation of a sudoku board.  Stores the board state as a simple 2-d
 * array of ints.
 * IMPORTANT: All the methods in this class that deal with indices of the board
 * are all designed to be zero-indexed.  So the first column is x-0 and the
 * third column is x-2, etc.  Same for the rows.
 *
 * @author Connor Seiden
 * @version 2-24-2015
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
        //find y coodinate of current 3x3 board square
        if(y<=2){ ty=0; }
        else if(y<=5){ ty=3; }
        else{ ty=6; }

        int sqr[] = {board[tx][ty], board[tx][ty+1], board[tx][ty+2],
                board[tx+1][ty], board[tx+1][ty+1], board[tx+1][ty+2],
                board[tx+2][ty], board[tx+2][ty+1], board[tx+2][ty+2]};
        return sqr;
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