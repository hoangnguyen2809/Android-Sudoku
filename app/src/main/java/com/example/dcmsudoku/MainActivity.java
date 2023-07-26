package com.example.dcmsudoku;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {
    private Button selectedButton;

    private int[][] sudokuBoard;
    private final int[] buttonIds
            = new int[] {
            R.id.s00, R.id.s01, R.id.s02, R.id.s03, R.id.s04, R.id.s05, R.id.s06, R.id.s07, R.id.s08,
            R.id.s10, R.id.s11, R.id.s12, R.id.s13, R.id.s14, R.id.s15, R.id.s16, R.id.s17, R.id.s18,
            R.id.s20, R.id.s21, R.id.s22, R.id.s23, R.id.s24, R.id.s25, R.id.s26, R.id.s27, R.id.s28,
            R.id.s30, R.id.s31, R.id.s32, R.id.s33, R.id.s34, R.id.s35, R.id.s36, R.id.s37, R.id.s38,
            R.id.s40, R.id.s41, R.id.s42, R.id.s43, R.id.s44, R.id.s45, R.id.s46, R.id.s47, R.id.s48,
            R.id.s50, R.id.s51, R.id.s52, R.id.s53, R.id.s54, R.id.s55, R.id.s56, R.id.s57, R.id.s58,
            R.id.s60, R.id.s61, R.id.s62, R.id.s63, R.id.s64, R.id.s65, R.id.s66, R.id.s67, R.id.s68,
            R.id.s70, R.id.s71, R.id.s72, R.id.s73, R.id.s74, R.id.s75, R.id.s76, R.id.s77, R.id.s78,
            R.id.s80, R.id.s81, R.id.s82, R.id.s83, R.id.s84, R.id.s85, R.id.s86, R.id.s87, R.id.s88
    };

    private static final String TAG_ORIGINAL = "original";
    private static final String TAG_USER = "user";
    private long gameStartTime; // Add this variable to store the start time of the game


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sudokuBoard = generateDistinctNumbers();
        initializeBoard(buttonIds);


        Button number_1_btn = findViewById(R.id.pad1);
        Button number_2_btn = findViewById(R.id.pad2);
        Button number_3_btn = findViewById(R.id.pad3);
        Button number_4_btn = findViewById(R.id.pad4);
        Button number_5_btn = findViewById(R.id.pad5);
        Button number_6_btn = findViewById(R.id.pad6);
        Button number_7_btn = findViewById(R.id.pad7);
        Button number_8_btn = findViewById(R.id.pad8);
        Button number_9_btn = findViewById(R.id.pad9);
        Button erase_btn = findViewById(R.id.erase_btn);
        Button reset_btn = findViewById(R.id.reset_btn);
        Button hint_btn = findViewById(R.id.hint_btn);
        Button check_btn = findViewById(R.id.check_btn);

        // Loop through the button IDs array and add the OnClickListener to each button
        for (int buttonId : buttonIds) {
            Button button = findViewById(buttonId);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectButton(button);
                }
            });
        }

        number_1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButtonValue("1");
            }
        });

        number_2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButtonValue("2");
            }
        });

        number_3_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButtonValue("3");
            }
        });

        number_4_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButtonValue("4");
            }
        });

        number_5_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButtonValue("5");
            }
        });

        number_6_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButtonValue("6");
            }
        });

        number_7_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButtonValue("7");
            }
        });

        number_8_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButtonValue("8");
            }
        });

        number_9_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButtonValue("9");
            }
        });

        erase_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedButton != null)
                {
                    selectedButton.setText("");
                }
            }
        });

        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetUserFilledButtons();
            }
        });

        hint_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                showHint();
            }
        });

        check_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkNumber();
            }
        });
    };




    private void selectButton(Button button) {
        // Check if a button is already selected
        if (selectedButton != null) {
            // Restore the background color of the previously selected button
            int prevButtonColor = getButtonColor(selectedButton.getId());
            selectedButton.setBackgroundResource(prevButtonColor);

            // Restore the background color of buttons in the same row and column
            int selectedRow = extractRow(selectedButton.getId());
            int selectedCol = extractColumn(selectedButton.getId());
            restoreRowAndColumnColors(selectedRow, selectedCol);
        }

        // Update the selectedButton reference to the current button
        selectedButton = button;

        // Change the background color of buttons in the same row and column
        int currentRow = extractRow(button.getId());
        int currentCol = extractColumn(button.getId());
        changeRowAndColumnColors(currentRow, currentCol);
        button.setBackgroundResource(R.drawable.selected_cell);
    }
    private int extractColumn(int buttonId) {
        String idString = getResources().getResourceEntryName(buttonId);
        return Character.getNumericValue(idString.charAt(2));
    }
    private int extractRow(int buttonId) {
        String idString = getResources().getResourceEntryName(buttonId); // Get the resource name as a string
        return Character.getNumericValue(idString.charAt(1));
    }
    private void restoreRowAndColumnColors(int row, int col) {
        // Restore the background color of buttons in the same row
        for (int i = 0; i < 9; i++) {
            Button button = findViewById(buttonIds[row * 9 + i]);
            int buttonColor = getButtonColor(button.getId());
            button.setBackgroundResource(buttonColor);
        }

        // Restore the background color of buttons in the same column
        for (int i = 0; i < 9; i++) {
            Button button = findViewById(buttonIds[col + i * 9]);
            int buttonColor = getButtonColor(button.getId());
            button.setBackgroundResource(buttonColor);
        }
    }
    private void changeRowAndColumnColors(int row, int col) {
        // Change the background color of buttons in the same row
        for (int i = 0; i < 9; i++) {
            Button button = findViewById(buttonIds[row * 9 + i]);
            button.setBackgroundResource(R.drawable.same_row_col_cell);
        }

        // Change the background color of buttons in the same column
        for (int i = 0; i < 9; i++) {
            Button button = findViewById(buttonIds[col + i * 9]);
            button.setBackgroundResource(R.drawable.same_row_col_cell);
        }
    }
    private int getButtonColor(int buttonId) {
        if (buttonId == R.id.s03 || buttonId == R.id.s04 || buttonId == R.id.s05 ||
                buttonId == R.id.s13 || buttonId == R.id.s14 || buttonId == R.id.s15 ||
                buttonId == R.id.s23 || buttonId == R.id.s24 || buttonId == R.id.s25 ||

                buttonId == R.id.s30 || buttonId == R.id.s31 || buttonId == R.id.s32 ||
                buttonId == R.id.s40 || buttonId == R.id.s41 || buttonId == R.id.s42 ||
                buttonId == R.id.s50 || buttonId == R.id.s51 || buttonId == R.id.s52 ||

                buttonId == R.id.s36 || buttonId == R.id.s37 || buttonId == R.id.s38 ||
                buttonId == R.id.s46 || buttonId == R.id.s47 || buttonId == R.id.s48 ||
                buttonId == R.id.s56 || buttonId == R.id.s57 || buttonId == R.id.s58 ||

                buttonId == R.id.s63 || buttonId == R.id.s64 || buttonId == R.id.s65 ||
                buttonId == R.id.s73 || buttonId == R.id.s74 || buttonId == R.id.s75 ||
                buttonId == R.id.s83 || buttonId == R.id.s84 || buttonId == R.id.s85) {
            return R.drawable.even_grid;
        } else {
            return R.drawable.odd_grid;
        }
    }
    private void initializeBoard(int[] buttonIds) {
        for (int i = 0; i < buttonIds.length; i++) {
            Button button = findViewById(buttonIds[i]);
            int row = i / 9;
            int col = i % 9;
            button.setText(String.valueOf(sudokuBoard[row][col]));
            button.setEnabled(false);
            button.setTextSize(18);
            button.setTextColor(Color.BLACK);
            Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
            button.setTypeface(boldTypeface);

            if (sudokuBoard[row][col] != 0) {
                button.setTag(TAG_ORIGINAL);
            } else {
                button.setTag(TAG_USER);
            }
        }

        List<Integer> buttonList = new ArrayList<>();
        for (int buttonId : buttonIds) {
            buttonList.add(buttonId);
        }
        Collections.shuffle(buttonList);

        //Change the value of i in the loop for testing
        for (int i = 0; i < 2; i++) {
            Button button = findViewById(buttonList.get(i));
            button.setText("");
            button.setEnabled(true);
            button.setTextSize(18);
        }

    }
    private int[][] generateDistinctNumbers() {
        int[][] board = new int[9][9];
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        // Fill the board row by row
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int index = (row * 3 + row / 3 + col) % 9;
                board[row][col] = numbers.get(index);
            }
        }

        return board;
    }

    private void changeButtonValue(String number)
    {
        if (selectedButton != null)
        {
            selectedButton.setText(number);
            if (allCellsFilled()) {
                // Check if the user has won the game
                if (isSudokuBoardFilledCorrectly()) {
                    showWinMessage();
                }
            }
        }
        assert selectedButton != null;
        selectedButton.setTag(TAG_USER);
    }

    private void resetUserFilledButtons() {
        for (int buttonId : buttonIds) {
            Button button = findViewById(buttonId);
            if (button.getTag() != null && button.getTag().equals(TAG_USER)) {
                button.setText("");
            }
        }
    }

    public void showHint() {
        // Check if a button is selected
        if (selectedButton != null) {

            int row = extractRow(selectedButton.getId());
            int col = extractColumn(selectedButton.getId());

            int originalNumber = sudokuBoard[row][col];

            selectedButton.setText(String.valueOf(originalNumber));
            selectedButton.setTag(TAG_USER);
            selectedButton.setTextSize(18);
        }

        if (allCellsFilled()) {
            // Check if the user has won the game
            if (isSudokuBoardFilledCorrectly()) {
                showWinMessage();
            }
        }
    }

    private boolean isCheckButtonActive = true;

    public void checkNumber() {
        // Check if the check button is active
        if (!isCheckButtonActive) {
            return;
        }

        // Disable the check button and set the flag to true
        Button checkButton = findViewById(R.id.check_btn);
        checkButton.setEnabled(false);
        isCheckButtonActive = false;

        // Use a Handler to enable the check button after 1.6 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkButton.setEnabled(true);
                isCheckButtonActive = true;
            }
        }, 1600);
        for (int buttonId : buttonIds) {
            Button button = findViewById(buttonId);

            // Check if the button is empty or has the TAG_USER
            if (button.getTag() == TAG_ORIGINAL)
            {

                int buttonColor = getButtonColor(button.getId());
                button.setBackgroundResource(buttonColor);
                int currentRow = extractRow(selectedButton.getId());
                int currentCol = extractColumn(selectedButton.getId());

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        button.setBackgroundResource(buttonColor);
                        changeRowAndColumnColors(currentRow, currentCol);
                        selectedButton.setBackgroundResource(R.drawable.selected_cell);
                    }
                }, 1500);
            }
            if (button.getText().toString().trim().isEmpty() || button.getTag() == TAG_USER) {
                int row = extractRow(buttonId);
                int col = extractColumn(buttonId);
                int originalNumber = sudokuBoard[row][col];
                int buttonColor = getButtonColor(button.getId());
                int currentRow = extractRow(selectedButton.getId());
                int currentCol = extractColumn(selectedButton.getId());


                String buttonText = button.getText().toString().trim();
                int numberFilled = buttonText.isEmpty() ? 0 : Integer.parseInt(buttonText);

                // If the number filled by the user is wrong or if the cell is empty,
                // set the color of the cell to wrong_number
                if (buttonText.isEmpty() || numberFilled != originalNumber) {
                    button.setBackgroundResource(R.drawable.wrong_number);
                }

                // If the number filled by the user is correct,
                // set the color of the cell to right_number
                if (numberFilled == originalNumber) {
                    button.setBackgroundResource(R.drawable.right_number);
                }

                // Use a Handler to restore the original background color after 1.5 seconds
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        button.setBackgroundResource(buttonColor);
                        changeRowAndColumnColors(currentRow, currentCol);
                        selectedButton.setBackgroundResource(R.drawable.selected_cell);
                    }
                }, 1500);
            }

        }
    }

    private Button getButton(int row, int col) {
        int buttonId = buttonIds[row * 9 + col];
        return findViewById(buttonId);
    }
    private boolean isSudokuBoardFilledCorrectly() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                String buttonText = getButton(row, col).getText().toString().trim();
                if (buttonText.isEmpty()) {
                    // If any cell is empty, the board is not filled correctly
                    return false;
                }
                int numberFilled = Integer.parseInt(buttonText);
                int originalNumber = sudokuBoard[row][col];
                if (numberFilled != originalNumber) {
                    // If any cell has an incorrect number, the board is not filled correctly
                    return false;
                }
            }
        }
        // If all cells are filled correctly, return true
        return true;
    }

    private boolean allCellsFilled() {
        for (int buttonId : buttonIds) {
            Button button = findViewById(buttonId);
            if (button.getText().toString().trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void startNewGame() {
        // Finish the current activity to restart the program
        finish();

        // Start the MainActivity again using an intent
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void showWinMessage() {
        long gameEndTime = System.currentTimeMillis();
        long timeTakenInMillis = gameEndTime - gameStartTime;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Congratulations!");
        builder.setMessage("You have completed the Sudoku puzzle in \nWant to start a new game?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Start a new game when the user clicks "OK"
                startNewGame();
            }
        });
        builder.show();
    }

    // Helper method to format time in minutes and seconds
}









