package com.example.jar424.chess;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import app.Game;
import app.Player;
import pieces.Bishop;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import app.Board;
import pieces.Queen;
import pieces.Rook;

public class PlayActivity extends AppCompatActivity {

    private static ImageButton[][] buttons;
    private Game game;
    private static String input;
    private ImageButton activeButton;
    private Piece activePiece;
    private boolean firstClick;
    private TextView turnBox;
    private TextView message;
    private Button undo;
    private Button ai;
    private Button resign;
    private Button draw;
    private static ArrayList<Board> moves;
    private ArrayList<Game> games;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_activity);

        buttons = new ImageButton[8][8];

        activeButton = null;
        activePiece = null;

        input = "";

        firstClick = true;

        initializeButtons();

        moves = new ArrayList<>();
        turnBox = (TextView) findViewById(R.id.turnBox);
        message = (TextView) findViewById(R.id.message);
        undo = (Button) findViewById(R.id.undo);
        ai = (Button) findViewById(R.id.ai);
        resign = (Button) findViewById(R.id.resign);
        draw = (Button) findViewById(R.id.draw);

        games = new ArrayList<Game>();

        game = new Game();
        game.drawBoard();

    }

    private void swapTurnBox() {
        if(game.isWhiteTurn()) {
            turnBox.setBackgroundResource(R.drawable.border);
        }else {
            turnBox.setBackgroundColor(Color.BLACK);
        }
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


    public void clicked(View v){
        //System.out.println("clicked");

        ImageButton buttonPressed = (ImageButton) findViewById(v.getId());

        for(int row = 7; row >= 0; row--) {
            for (int col = 0; col < 8; col++) {
                if(buttons[7 - row][col] == buttonPressed){

                    Piece p = game.getBoard().get(row, col);

                    if(firstClick){

                        input = "";

                        if(p == null || (p.isWhite() != game.isWhiteTurn())){
                            openDialog("Invalid piece");
                        }else {
                            activePiece = p;
                            activeButton = buttonPressed;

                            firstClick = false;

                            activeButton.setColorFilter(Color.BLUE);

                            input = input + (char) (col + 97);
                            input = input + (char) (row + 49) + " ";
                            //System.out.println("input: " + input);

                        }

                    }else{

                        //deselect piece
                        if(buttonPressed == activeButton){
                            activeButton.setColorFilter(null);
                            activePiece = null;
                            activeButton = null;
                            firstClick = true;

                            input = "";

                            return;
                        }

                        input = input + (char)(col + 97);
                        input = input + (char) (row + 49) + " ";
                        //System.out.println("input: " + input);

                        Game g = new Game(game);
                        //g.drawBoardTerminal();

                        if(game.takeTurn(input)){

                            //promotion
                            if(activePiece instanceof Pawn && (row == 7 || row == 0) ){
                                promotionDialog();
                            }

                            game.drawBoard();
                            game.setWhiteTurn(!game.isWhiteTurn());
                            swapTurnBox();

                            games.add(g);

                            checkForCheck();

                            input = "";

                            activeButton.setColorFilter(null);

                            activePiece = null;
                            activeButton = null;

                            firstClick = true;

                        }else{
                            input = input.substring(0, input.indexOf(" ") + 1);
                            openDialog("Illegal Move");
                        }

                    }

                    return;

                }
            }
        }

        //buttonPressed.setImageResource(R.drawable.blackking);
    }

    public void functionClicked(View v){

        Button buttonPressed = (Button) findViewById(v.getId());

        if(buttonPressed == undo){
            //message.setText("undo");
            undo();
        }

        if(buttonPressed == ai){
            //message.setText("ai");
            ai();
        }

        if(buttonPressed == resign){
            //message.setText("resign");
            resign();
        }

        if(buttonPressed == draw){
            //message.setText("draw");
            draw();
        }

    }

    private void undo(){

        if(games.size() < 1){
            //can't undo
            return;
        }

        game = games.get(games.size() - 1);

        games.remove(games.size() - 1);

        game.drawBoard();
        swapTurnBox();

        //reset stuff
        firstClick = true;
        input = "";
        activePiece = null;

        if(activeButton != null)
            activeButton.setColorFilter(null);

        activeButton = null;





        checkForCheck();
        //System.out.println(games.size());
        //System.out.println(game.isWhiteTurn());


    }

    private void ai(){

        Player p;
        Random random = new Random();
        List<Piece> pieces;
        Piece piece;

        int row;
        int col;

        if(game.isWhiteTurn()){
            p = game.getWhite();
        }else{
            p = game.getBlack();
        }

        pieces = p.getPieces();

        Game g = new Game(game);

        do{

            input = "";

            //get random piece
            int x = random.nextInt(pieces.size());
            piece = pieces.get(x);

            input = input + (char) (piece.getCol() + 97);
            input = input + (char) (piece.getRow() + 49) + " ";

            //try random spot
            row = random.nextInt(8);
            col = random.nextInt(8);

            input = input + (char) (col + 97);
            input = input + (char) (row + 49) + " ";

        }while(!game.takeTurn(input));

        activePiece = piece;

        //promotion
        if(piece instanceof Pawn && (row == 7 || row == 0) ){
            promotionDialog();
        }

        //reset stuff
        input = "";
        firstClick = true;

        activePiece = null;

        if(activeButton != null)
            activeButton.setColorFilter(null);

        activeButton = null;

        game.drawBoard();
        game.setWhiteTurn(!game.isWhiteTurn());
        swapTurnBox();
        games.add(g);

        checkForCheck();

        //System.out.println(games.size());
    }

    private void checkForCheck(){
        if(game.getWhite().isDefeated()){
            resultDialog("Checkmate! Black wins");
        }

        if(game.getBlack().isDefeated()){
            resultDialog("Checkmate! White wins");
        }

        if ( !game.isWhiteTurn()) {
            int x = game.getBlack().getKing().getRow();
            int y = game.getBlack().getKing().getCol();

            for (Piece p: game.getWhite().getPieces()) {
                if ( p.isValidMove(game.getBoard(), x, y) ) {
                    game.getBlack().setChecked(true);
                }
            }
        } else {
            int x = game.getWhite().getKing().getRow();
            int y = game.getWhite().getKing().getCol();

            for (Piece p: game.getBlack().getPieces()) {
                if ( p.isValidMove(game.getBoard(), x, y) ) {
                    game.getWhite().setChecked(true);
                }
            }
        }

        if(game.getWhite().isChecked() || game.getBlack().isChecked()){
            message.setText("Check");
        }else{
            message.setText("");
        }



        //reset checks
        game.getWhite().setChecked(false);
        game.getBlack().setChecked(false);
    }

    private void openDialog(String s){
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        // Setting Dialog Title
        alertDialog.setTitle("Alert Dialog");

        // Setting Dialog Message
        alertDialog.setMessage(s);


        // Setting OK Button
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog,int which)
            {
                alertDialog.dismiss();
                System.out.println("Error acknowledged");
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }


    private void resign () {
        input = "resign";

        if (game.takeTurn(input)) {

            if (game.isWhiteTurn()) {
                resultDialog("White resigns");
            } else {
                resultDialog("Black resigns");
            }
        }
    }

    private void draw () {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        // Setting Dialog Title
        alertDialog.setTitle("Draw Offered");

        // Setting Dialog Message
        if(game.isWhiteTurn())
            alertDialog.setMessage("White offers draw. \nDo you accept draw?");
        else
            alertDialog.setMessage("Black offers draw. \nDo you accept draw?");

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,"Yes", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog,int which)
            {
                alertDialog.dismiss();
                resultDialog("Game ended in a draw");
            }
        });

        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"No", new DialogInterface.OnClickListener() {
            public void onClick (DialogInterface dialog, int which) {
                alertDialog.dismiss();
                openDialog("Draw refused");
            }
        });

        alertDialog.show();
    }

    private void promotionDialog() {

        final boolean isWhite = activePiece.isWhite();
        final int row = activePiece.getRow();
        final int col = activePiece.getCol();

        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Select a piece for promotion");
        String[] items = {"Queen", "Rook", "Bishop", "Knight"};
        b.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                switch (which) {
                    case 0:
                        game.promote(activePiece, new Queen(isWhite, row, col));
                        game.drawBoard();
                        break;
                    case 1:
                        game.promote(activePiece, new Rook(isWhite, row, col));
                        game.drawBoard();
                        break;
                    case 2:
                        game.promote(activePiece, new Bishop(isWhite, row, col));
                        game.drawBoard();
                        break;
                    case 3:
                        game.promote(activePiece, new Knight(isWhite, row, col));
                        game.drawBoard();
                        break;
                }

            }

        });

        b.show();
    }

    private void resultDialog (String s) {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        // Setting Dialog Title
        alertDialog.setTitle("Game Result");

        // Setting Dialog Message
        alertDialog.setMessage(s);


        // Setting OK Button
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog,int which)
            {
                alertDialog.dismiss();

                System.out.println("Game ended");
                save();
            }
        });

        // Showing Alert Message
        alertDialog.show();

    }

    private void save () {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        // Setting Dialog Title
        alertDialog.setTitle("Record Game?");

        // Setting Dialog Message
        alertDialog.setMessage("Would you like to save this game?");

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,"Yes", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog,int which)
            {
                alertDialog.dismiss();

                save_game();
            }
        });

        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"No", new DialogInterface.OnClickListener() {
            public void onClick (DialogInterface dialog, int which) {
                alertDialog.dismiss();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        finish();;
                    }
                }, 2000);
            }
        });

        alertDialog.show();

    }

    private void save_game () {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Title");

        final String[] text = new String[1];

        final EditText title = new EditText(this);
        title.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(title);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                String formattedDate = sdf.format(date);

                text[0] = title.getText().toString() + "\t\t\t\t\t\t\t" + formattedDate;
                System.out.println(text[0]);

                if (text[0] == null || text[0].equals("")) {
                    openDialog("Invalid Name");

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            save_game();;
                        }
                    }, 5000);

                } else {
                    System.out.println("writing to file");
                    writeTofile(text[0]);
                }

                finish();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        finish();;
                    }
                }, 2000);

            }
        });

        builder.show();
    }

    private void writeTofile (String file_name) {
        FileOutputStream fos;
        ObjectOutputStream oos;

        for(Game g : games){
            moves.add(g.getBoard());
        }

        moves.add(game.getBoard());

        try {
            fos = openFileOutput(file_name, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(moves);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static ImageButton getButton(int row, int col){
        return buttons[row][col];
    }

}
