package com.example.barel.myfirstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    // private variables for the game
    private static int score;
    private static boolean gameOn;
    private static Random rand;
    private static int length = 10;
    private static long startTime;
    private int currentFull;
    private final String startId = "com.example.barel.myfirstapp:id/";
    // the button and score board
    private static Button start;
    private static TextView scoreBoard;
    private static ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // initialize global values
        score = length;
        gameOn = false;
        rand = new Random();
        // find the button and score board text
        start = findViewById(R.id.startButton);
        scoreBoard = findViewById(R.id.textView);
    }

    private void updateScore(){
        int ones = score % 10;
        int tens = (score - ones) / 10;
        scoreBoard.setText(Integer.toString(tens) + Integer.toString(ones));
    }

    private void initializeBoard(){
        score = length;
        updateScore();
        start.setText(R.string.button_strat);
        int id;
        for (int i = 1; i <= 12; i++) {
            id = getResources().getIdentifier("imageView" + Integer.toString(i), "id", getPackageName());
            image = findViewById(id);
            image.setImageResource(R.drawable.empty);
        }
        gameOn = false;
    }

    public void startGame(View view){
        // if the user want to stop the game
        if (gameOn) {
            Log.d("DEBUG", "Restrating");
            initializeBoard();
            return;
        }
        // start a new game
        Log.d("DEBUG", "Starting");
        start.setText(R.string.button_stop);
        updateScore();
        int first = rand.nextInt(12) + 1;
        currentFull = first;
        int id = getResources().getIdentifier("imageView" + Integer.toString(first), "id", getPackageName());
        image = findViewById(id);
        image.setImageResource(R.drawable.full);
        startTime = System.nanoTime();
        gameOn = true;
    }

    public void click(View view){
        if (!getResources().getResourceName(view.getId()).equals(startId + "imageView" + Integer.toString(currentFull))){
            Log.d("DEBUG", "Bad Click");
            return;
        }
        Log.d("DEBUG", "Good Click");
        // up the score and change the clicked view to empty
        score -= 1;
        updateScore();
        image = (ImageView) view;
        image.setImageResource(R.drawable.empty);
        // if game is over print the time and initialize the board
        if (score == 0){
            // claculate time and print
            double gameTime = (System.nanoTime() - startTime) * 1e-9;
            String msg = "Congratulation you won in: " + (new DecimalFormat("##.#").format(gameTime)) + " seconds";
            Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
            toast.show();
            initializeBoard();
            return;
        }
        // otherwise make a new target
        int first = rand.nextInt(12) + 1;
        currentFull = first;
        int id = getResources().getIdentifier("imageView" + Integer.toString(first), "id", getPackageName());
        image = findViewById(id);
        image.setImageResource(R.drawable.full);
    }
}
