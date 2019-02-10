package com.vk.connectthreegame;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

     //0 = mango, 1 = apple, 2 = empty

    int[] state = {2,2,2,2,2,2,2,2,2};
    int[][] wPosition = {{0,1,2},{3,4,5}, {6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    int activePlayer = 0;
    boolean gameEndCheck = true;
    public void dropIn(View view){

        ImageView counter = (ImageView) view;


        int tappedCounter = Integer.parseInt(counter.getTag().toString());
        if (state[tappedCounter] == 2 && gameEndCheck) {
            state[tappedCounter] = activePlayer;
            counter.setTranslationY(-1500);


            if (activePlayer == 0) {
                activePlayer = 1;
                counter.setImageResource(R.drawable.mango);
            } else {
                activePlayer = 0;
                counter.setImageResource(R.drawable.apple);
            }
            counter.animate().translationYBy(1500).rotation(360).setDuration(2000);

            for (int[] i : wPosition) {
                if (state[i[0]] == state[i[1]] && state[i[1]] == state[i[2]] && state[i[0]] != 2) {
                    //someone has won!
                    gameEndCheck = false;
                    String winner;
                    if (activePlayer == 1) {
                        winner = "Mango";
                    } else {
                        winner = "Apple";
                    }

                    Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
                    TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);

                    winnerTextView.setText(winner +" has won");
                    playAgainButton.setVisibility(View.VISIBLE);
                    winnerTextView.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void playAgain(View view){

        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
        TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);

        playAgainButton.setVisibility(View.INVISIBLE);
        winnerTextView.setVisibility(View.INVISIBLE);

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for(int i = 0; i < gridLayout.getChildCount(); i++){

            ImageView counter = (ImageView) gridLayout.getChildAt(i);
            counter.setImageDrawable(null);

        }


        for(int i = 0; i<state.length; i++){
            state[i] = 2;
        }
        activePlayer = 0;
        gameEndCheck = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
