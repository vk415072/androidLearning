package com.vk.highorlow;

        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.Toast;

        import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int randomNumber;

    public void toastFunction(String toast){


        Toast.makeText(MainActivity.this, toast, Toast.LENGTH_LONG).show();
    }

    public void checkFunction(View view) {

        EditText guessEditText = (EditText) findViewById(R.id.editTextID2);

        int guessInt = Integer.parseInt(guessEditText.getText().toString());

        if (guessInt > randomNumber) {

            toastFunction("isse kam h");

        } else if (guessInt < randomNumber) {

            toastFunction("isse jada h");

        } else {

            toastFunction("Jeet gya BC! Chal dobara khelte h!");

            Random rand = new Random();

            randomNumber = rand.nextInt(20) + 1;

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Random rand = new Random();

        randomNumber = rand.nextInt(20) + 1;

    }
}
