package com.example.cseiden.sudokuui;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


/**
 * Main activity page for the Sudoku solver Android app.
 *
 * @author Connor Seiden
 * @version 6-7-2016
 *
 */
public class MainActivity extends AppCompatActivity {

    //the id of the currently selected grid button
    private int gridSelect;

    /**
     * The onCreate method for this activity.  Sets the id's of all spaces on
     * the board to the correct number, also sets the id's of the number buttons
     * to the appropriate values.
     *
     * @param savedInstanceState        The saved instance state. Passed to the
     *                                  super class' onCreate method.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridSelect = -1;

        //assign appropriate IDs to each square on the board
        int posCounter = 0;
        LinearLayout board = (LinearLayout) findViewById(R.id.board);
        int cCount = board.getChildCount();
        try {
            for (int i = 0; i < cCount; i++) {
                if ((i + 1) % 4 != 0) {
                    LinearLayout l = (LinearLayout) board.getChildAt(i);
                    int lCount = l.getChildCount();
                    for (int j = 0; j < lCount; j++) {
                        if ((j + 1) % 4 != 0) {
                            Button b = (Button) l.getChildAt(j);
                            b.getBackground().setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);
                            b.setId(posCounter);
                            posCounter++;
                        }
                    }
                }
            }

            LinearLayout numbers = (LinearLayout) findViewById(R.id.numbers);
            int nCount = numbers.getChildCount();
            for(int i=0;i<nCount;i++){
                Button b = (Button) numbers.getChildAt(i);
                b.setId(i+1);
            }

        }catch(Exception e){
            Toast.makeText(this, "something went wrong", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Change the currently selected space on the board.
     * The method that is called by clicking any of the spaces on the board.
     * Change the clicked button to have a blue background and changes the
     * previously selected button to having a light gray background.
     * Also changes the variable that records what the currently selected
     * button is.
     *
     * @param view      The button that was clicked.  Has its background and text
     *                  color changed.  Its id is used to record which button is
     *                  currently selected.
     */
    public void gridButtonSelect(View view){
        ((Button) view).getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.MULTIPLY);
        ((Button) view).setTextColor(Color.WHITE);

        if(gridSelect != -1) {
            LinearLayout board = (LinearLayout) findViewById(R.id.board);
            ((Button) board.findViewById(gridSelect)).getBackground().setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);
            ((Button) board.findViewById(gridSelect)).setTextColor(Color.BLACK);
        }

        gridSelect = view.getId();
    }

    /**
     * Enter a number into the currently selected space on the board.  If not
     * space is selected, it does nothing.
     * The method that is called by clicking one of the numbered buttons or the 'clear'
     * button.
     * The buttons' id is the number they represent.  Except for 'clear' which is 10.
     * The method uses these numbers to put the correct number into the currently
     * selected space.
     *
     * @param view      The button that was pressed.  Used to tell which number should
     *                  be put into the currently selected space on the board.
     */
    public void enterNum(View view){
        if(gridSelect != -1) {
            int num = view.getId();
            LinearLayout board = (LinearLayout) findViewById(R.id.board);
            if (num != 10) {
                ((Button) board.findViewById(gridSelect)).setText(Integer.toString(num));
            }
            else{
                ((Button) board.findViewById(gridSelect)).setText(" ");
            }
        }
    }

    /**
     * Solve the board as it is currently displayed.
     * The method that is called by clicking the 'solve board' button.
     * Takes all the numbers entered on the current board and finds a
     * solution for it (if it exists).
     * If a solution exists then it will change the board in the UI to be the
     * solution board.  If no solution exists, then the board will stay the
     * same and the app will notify the user that no solution exists.
     *
     *
     * @param view      The 'solve board' button.  Not used.
     */
    public void solveBoard(View view){
        Board board = new Board();

        LinearLayout initBoard = (LinearLayout) findViewById(R.id.board);
        for(int i=0;i<81;i++){
            int y = i/9;
            int x = i%9;
            String num = ((Button)initBoard.findViewById(i)).getText().toString();
            if(!num.equals(" ")){
                board.put(Integer.parseInt(num), x, y);
            }
        }

        Board solved = Solver.solution(board);

        if(solved == null){
            Toast.makeText(getApplicationContext(), "No Solution Exists", Toast.LENGTH_LONG).show();
        }
        else{
            for(int i=0;i<81;i++){
                int y = i/9;
                int x = i%9;
                ((Button)initBoard.findViewById(i)).setText(Integer.toString(solved.get(x,y)));
            }

            Toast.makeText(getApplicationContext(), "Solved", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Make all spaces on the board blank.  The method that is called by clicking
     * the 'clear board' button.
     *
     * @param view      The view of the 'clear board' button.  Not used.
     */
    public void clearBoard(View view){
        LinearLayout board = (LinearLayout) findViewById(R.id.board);
        for(int i=0;i<81;i++){
            ((Button) board.findViewById(i)).setText(" ");
        }
    }


}
