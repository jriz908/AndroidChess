package com.example.jar424.chess;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class MainActivity extends AppCompatActivity{

    private Button play;
    private Button recorded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = (Button) findViewById(R.id.play);
        recorded = (Button) findViewById(R.id.recorded);

    }

    public void play(View v){
        Intent intent = new Intent(this, PlayActivity.class);
        startActivity(intent);
    }

    public void recorded(View v){
        Intent intent = new Intent(this, RecordActivity.class);
        startActivity(intent);
    }
}
