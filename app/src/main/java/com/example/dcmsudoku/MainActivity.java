package com.example.dcmsudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button selectedButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create an array to store the IDs of the buttons
        int[] buttonIds = {
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
    }

    private void selectButton(Button button) {
        // Check if a button is already selected
        if (selectedButton != null) {
            // Restore the background color of the previously selected button
            int prevButtonColor = getButtonColor(selectedButton.getId());
            selectedButton.setBackgroundResource(prevButtonColor);
        }

        // Set the background color of the newly selected button
        button.setBackgroundResource(R.drawable.selected_cell);

        // Update the selectedButton reference to the current button
        selectedButton = button;
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

}









