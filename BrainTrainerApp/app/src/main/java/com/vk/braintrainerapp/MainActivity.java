package com.vk.braintrainerapp;

import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    Button goButton;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button playAgain;
    ArrayList<Integer> answers = new ArrayList<Integer>();
    int correctAnsLocation;
    TextView resultTextView;
    TextView scoreTextView;
    TextView questionTextView;
    TextView timerTextView;
    int score=0;
    int numberOfQuestions = 0;
    Boolean isCounting = true;
    ConstraintLayout gameLayout;

    public void  playAgain(View view){
        isCounting = true;
        newQuestionFunction();
        score = 0;
        numberOfQuestions = 0;
            //resetting the score board.
        scoreTextView.setText(Integer.toString(score)+"/"+Integer.toString(numberOfQuestions));
            //resetting timer text.
        timerTextView.setText("30s");
            //clearing the result text to none.
        resultTextView.setText("");
            //setting the play again button to invisible.
        playAgain.setVisibility(View.INVISIBLE);
            //creating the timer for 30 seconds for 1 second interval.
        new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                    //updating the text of timeTextView accordingly.
                timerTextView.setText(String.valueOf(millisUntilFinished/1000)+"s");
            }

            @Override
            public void onFinish() {
                resultTextView.setText("Finished!");
                playAgain.setVisibility(View.VISIBLE);
                isCounting = false;
            }
        }.start();
    }

    public void newQuestionFunction(){
          if (isCounting){
              //initializing a random variable.
              Random random = new Random();
              //generating random nos. between 0 to 20.
              int a = random.nextInt(21);
              int b = random.nextInt(21);
              //setting the random no. as the question text.
              questionTextView.setText(Integer.toString(a)+"+"+Integer.toString(b));

              //generating the random location of the correct ans.
              correctAnsLocation = random.nextInt(4);
              //before creating the array for all the answers options, we've to clear the array first in case this function is called second time.
              answers.clear();
              //creating an array of 4 elements containing 3 random elements and 1 answer element.
              for(int i=0; i<4; i++){
                  //if matches the random location of the correct ans then add the correct ans to the button.
                  if(i == correctAnsLocation){
                      answers.add(a+b);
                  }else {
                      //creating a while loop so that the wrong ans could not be randomly generated and matched with our ans.
                      int wrongAnswer = random.nextInt(41);
                      while(wrongAnswer == a+b){
                          //generating a new random no. for wrong answers every time if it matches with our answer.
                          wrongAnswer = random.nextInt(41);
                      }
                      //finally adding the wrong answer random no. to the remaining places in the gridLayout.
                      answers.add(wrongAnswer);
                  }
              }
              //setting texts of the buttons as answer options.
              //here the numbers in the answers.get(); parameters are the positions in the gridLayout.
              button0.setText(Integer.toString(answers.get(0)));
              button1.setText(Integer.toString(answers.get(1)));
              button2.setText(Integer.toString(answers.get(2)));
              button3.setText(Integer.toString(answers.get(3)));
          }
    }

    public void chooseAnswer(View view){
            //to log the button pressed tag.
        //Log.i("Tag",view.getTag().toString());

        if(isCounting){
            //this way we're checking if the correctAnswerLocation is equal to the button tag we are taping on or not.
            //also converting both to String.
            if(Integer.toString(correctAnsLocation).equals(view.getTag().toString())){
                //Log.i("Correct!","You got it!");
                resultTextView.setText("Correct!");
                score++;
            }else{
                //Log.i("Wrong!","You loose!");
                resultTextView.setText("Wrong!");
            }
            numberOfQuestions++;
            //updating the scoreboard.
            scoreTextView.setText(Integer.toString(score)+"/"+Integer.toString(numberOfQuestions));
            //calling new answer
            newQuestionFunction();
        }
    }

    public void start(View view){
            //to hide the GO! button on tap.
        goButton.setVisibility(View.INVISIBLE);
        gameLayout.setVisibility(View.VISIBLE);
        playAgain(findViewById(R.id.timerTextView));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            //finding all the Views.
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        goButton = findViewById(R.id.goButton);
        resultTextView = findViewById(R.id.resultTextView);
        questionTextView = findViewById(R.id.questionTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView = findViewById(R.id.timerTextView);
        playAgain = findViewById(R.id.playagainButton);
        gameLayout = findViewById(R.id.gameLayout);

        goButton.setVisibility(View.VISIBLE);
        gameLayout.setVisibility(View.INVISIBLE);
            //we're calling the playAgain() function but it requires a View so we're passing any of the view as it does'nt matter as we'll not use it inside the playAgain().
    }
}
