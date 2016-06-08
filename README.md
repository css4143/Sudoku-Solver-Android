# Sudoku-Solver-Android
An Android app that solves a Sudoku board.

Author: Connor Seiden

This app is a sudoku solver that will take an incomplete sukdoku board and find the solution, if one exists.  It has a UI that allows users to enter the initial board state that they want solved.

The program finds the solution to a sudoku board through the use of a modified backtracking algorithm.  It recursively searches through valid board configurations until a complete, valid board is found.

This updated version incorporates checking of the initial board state to see if it is unsolvable.  This was implemented to deal with sparse, unsolvable boards which the program had long run times for.

Note: this repository only contains the .xml and .java files for this app.  It could not be run as is.

