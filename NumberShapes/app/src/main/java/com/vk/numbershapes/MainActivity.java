package com.vk.numbershapes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    public class NumberShape {
        int number;
        public boolean isTriangular(){
            int x = 1;
            int trino = 1;
            while(trino < number) {
                x++;
                trino = trino + x;

            }
            if(trino == number) {
                return true;
            }else {
                return false;
            }
        }
        public boolean isSquare(){
            double snum = Math.sqrt(number);
            if(snum == Math.floor(snum)){
                return true;
            }
            else{
                return false;
            }
        }
    }
    public void checkButtonFunction(View view) {
        String toast;
        EditText inputText = (EditText) findViewById(R.id.editText);
        NumberShape numberShapeObj = new NumberShape();

        int inputNum = Integer.parseInt(inputText.getText().toString());

        numberShapeObj.number = inputNum;
        if (numberShapeObj.isSquare()) {
            if(numberShapeObj.isTriangular()){
                toast = numberShapeObj.number + " is both Triangular and Square.";
            }else{
                toast = numberShapeObj.number + " is a square no.";
            }
        }else{
            if(numberShapeObj.isTriangular()){
                toast = numberShapeObj.number + " is a Triangular no.";
            }else{
                toast = numberShapeObj.number + " is Neither square, Nor triangular";
            }
        }
        Toast.makeText(MainActivity.this,toast,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
