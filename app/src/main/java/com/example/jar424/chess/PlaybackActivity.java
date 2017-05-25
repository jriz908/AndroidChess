package com.example.jar424.chess;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.FileInputStream;
import java.io.ObjectInputStream;


import app.Game;
import app.Board;
import java.util.ArrayList;

public class PlaybackActivity extends AppCompatActivity {

    private static ImageButton[][] buttons;
    private Game game;
    private int curr;
    private ArrayList<Board> moves;
    private Button prevButton;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playback_activity);

        buttons = new ImageButton[8][8];
        initializeButtons();
        initializeMoves();
        curr = 0;

        prevButton = (Button) findViewById(R.id.prev);
        nextButton = (Button) findViewById(R.id.next);

        prevButton.setEnabled(false);

        if (moves.size() == 1) {
            nextButton.setEnabled(false);
        }

        game = new Game();
        game.setBoard(moves.get(curr));
        game.playback_Board();
    }

    private void initializeButtons(){
        int count = 1;

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){

                String block = "block" + count;

                int id = getResources().getIdentifier(block, "id", this.getPackageName());

                buttons[i][j] = (ImageButton)findViewById(id);

                count++;
            }
        }
    }

    private void initializeMoves () {
        String file = RecordActivity.getSelected();

        try {
            FileInputStream fis = openFileInput(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            moves = (ArrayList<Board>) ois.readObject();
            //System.out.println(moves.size());
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void prev (View v) {
        //System.out.println("Previous Move Clicked");

        if (curr > 0) {
            nextButton.setEnabled(true);
            curr--;

            if (curr <= 0) {
                prevButton.setEnabled(false);
            }

            //System.out.println(curr);
            game.setBoard(moves.get(curr));
            game.playback_Board();
        }

    }

    public void next (View v) {
        //System.out.println("Next Move Clicked");

        if (curr < moves.size() - 1 ) {
            prevButton.setEnabled(true);
            curr++;

            if (curr >= moves.size() - 1) {
                nextButton.setEnabled(false);
            }

            //System.out.println(curr);
            game.setBoard(moves.get(curr));
            game.playback_Board();
        }
    }

    public static ImageButton getButton(int row, int col){
        return buttons[row][col];
    }


}
