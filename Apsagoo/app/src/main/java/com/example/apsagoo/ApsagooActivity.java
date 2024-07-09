package com.example.apsagoo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ApsagooActivity extends AppCompatActivity {
    private TextView message;
    private ImageView pedro;
    private int counter = 0;
    private float rotation = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        message = findViewById(R.id.textView);
        ImageView pedro = findViewById(R.id.imageView3);


        //Define and attach click listener
        pedro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tapPedro();
            }

        });
    }

    private void tapPedro() {
        counter++;
        String countAsText;


        /*
         * In real applications you should not write switch like the one below.
         * Use resource of type "Quantity strings (plurals)" instead.
         * See https://developer.android.com/guide/topics/resources/string-resource#Plurals
         */
        switch (counter) {
            case 1:
                countAsText = "once";
                break;
            case 2:
                countAsText = "twice";
                break;
            default:
                countAsText = String.format("%d times", counter);
                rotation += 10;
                pedro.animate().rotationYBy(rotation);
        }
        message.setText(String.format("You touched the droid %s", countAsText));
    }
}