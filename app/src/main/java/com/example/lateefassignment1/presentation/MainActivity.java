/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.lateefassignment1.presentation;


import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import com.example.yournameassignment1.R;
import com.example.yournameassignment1.databinding.ActivityMainBinding;

import java.util.Locale;

// The main activity for the app.
public class MainActivity extends AppCompatActivity implements ButtonClickListener {

    // The binding object for the main activity.
    private ActivityMainBinding binding;
    private long startingTime;
    private boolean isTimeRunning = false;
    private long elapsedTime = 0L;

    private Handler handler = new Handler();
    private Runnable updateTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater()); // inflate the layout
        View view = binding.getRoot(); // get the root view
        setContentView(view);
        getSupportActionBar().hide(); // hide the default android title bar

        /**
         * Set on click listener for start button to start the timer
         * */
        binding.startButton.setOnClickListener(startBtnView -> {
            startButtonClick();
        });

        /**
         * Set on click listener for stop button to stop the timer
         * */
        binding.stopButton.setOnClickListener(stopBtnView -> {
            stopButtonClick();
        });

        /**
         * Set on click listener for reset button to reset the timer
         * */
        binding.resetButton.setOnClickListener(resetView -> {
            resetButtonClick();
        });
        updateResetButton();
    }

    /**
     * Update the button status based on the timer status
     */
    private void updateButtonStatus() {
        if (isTimeRunning) {
            binding.startButton.setBackground(AppCompatResources
                    .getDrawable(this,R.drawable.disabled_radius_button)); // set the background with drawable resource
            binding.startButton.setEnabled(false); // disable the start button
            binding.startButton.setTextColor(getResources().getColor(R.color.dark_grey)); // set the text color
            binding.stopButton.setBackground(AppCompatResources
                    .getDrawable(this,R.drawable.enable_radius_button_bg)); // set the background with drawable resource
            binding.stopButton.setEnabled(true); // enable the stop button
            binding.stopButton.setTextColor(getResources().getColor(R.color.white)); // set the text color
            updateResetButton();
        } else {
            binding.startButton.setBackground(AppCompatResources
                    .getDrawable(this, R.drawable.enable_radius_button_bg)); // set the background with drawable resource
            binding.startButton.setEnabled(true); // enable the start button
            binding.startButton.setTextColor(getResources().getColor(R.color.white)); // set the text color
            binding.stopButton.setBackground(AppCompatResources
                    .getDrawable(this, R.drawable.disabled_radius_button)); // set the background with drawable resource
            binding.stopButton.setEnabled(false); // disable the stop button
            binding.stopButton.setTextColor(getResources().getColor(R.color.dark_grey)); // set the text color
            updateResetButton();
        }
    }

    /**
     * Update the reset button status based on the timer status
     */
    private void updateResetButton() {
        if (binding.stopwatchTextView.getText().toString().equals("00:00:00")) { // if the timer is 00:00:00
            binding.resetButton.setBackground(AppCompatResources
                    .getDrawable(this, R.drawable.disabled_radius_button)); // set the background with drawable resource
            binding.resetButton.setEnabled(false); // disable the reset button
            binding.resetButton.setTextColor(getResources().getColor(R.color.dark_grey)); // set the text color
        } else {
            binding.resetButton.setBackground(AppCompatResources
                    .getDrawable(this, R.drawable.enable_radius_button_bg)); // set the background with drawable resource
            binding.resetButton.setEnabled(true); // enable the reset button
            binding.resetButton.setTextColor(getResources().getColor(R.color.white)); // set the text color
        }
    }


    /**
     * Update the timer on the UI based on the elapsed time
     */
    private void updateUiWithElapsedTime() {
        int seconds = (int) (elapsedTime / 1000); // convert the elapsed time to seconds
        int minutes = seconds / 60; // convert the seconds to minutes
        int hours = minutes / 60; // convert the minutes to hours
        seconds %= 60; // get the remaining seconds
        minutes %= 60; // get the remaining minutes
        binding.stopwatchTextView.setText(
                String.format(Locale.US, "%02d:%02d:%02d", hours, minutes, seconds
                )); // set the timer text view
    }

    // The start button click
    @Override
    public void startButtonClick() {
        isTimeRunning = true;
        /**
         * set text view background drawable for start button and stop button
         */
        binding.startButton.setBackground(AppCompatResources
                .getDrawable(this, R.drawable.enable_radius_button_bg));
        startingTime = SystemClock.elapsedRealtime() - elapsedTime; // get the starting time
        updateTimer = new Runnable() { // update the timer
            public void run() {
                elapsedTime = SystemClock.elapsedRealtime() - startingTime;  // get the elapsed time
                updateUiWithElapsedTime();
                handler.postDelayed(this, 10); // delay the runnable task by 10 milliseconds
            }
        };
        handler.postDelayed(updateTimer, 0); // post the runnable task to handler
        updateButtonStatus();
    }

    @Override
    public void stopButtonClick() {
        isTimeRunning = false;
        handler.removeCallbacks(updateTimer); // remove the runnable task from handler
        updateButtonStatus();
    }

    @Override
    public void resetButtonClick() {
        isTimeRunning = false;
        handler.removeCallbacks(updateTimer);
        elapsedTime = 0L; // reset the elapsed time
        updateUiWithElapsedTime(); // update the timer
        updateButtonStatus(); // update the button status
    }
}

