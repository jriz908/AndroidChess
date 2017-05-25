/**
 * @author Jacob Rizer
 * @author Terence Williams
 */

package app;

import android.widget.ImageButton;

import java.util.Scanner;

import pieces.*;

import com.example.jar424.chess.PlayActivity;
import com.example.jar424.chess.PlaybackActivity;
import com.example.jar424.chess.R;

/**
 * This class represents our chess game.
 * It contains the board, players, game loop,
 * functions for retrieving input from the user, and
 * function to draw the board.
 *
 */
public class Game{

    /**
     * The board for our game.
     */
    private Board board;

    /**
     * The white player
     */
    private Player white;

    /**
     * The black player
     */
    private Player black;

    /**
     * Boolean for running/terminating the game loop
     */
    private boolean gameOn;

    /**
     * Boolean to keep track of turns
     */
    private boolean whiteTurn;

    /**
     * When one player offers a draw this is set to true so that the other player may accept the draw.
     */
    private boolean drawOffered;



    /**
     * When a pawn moves two spaces from its starting position, it becomes enpassantable for one turn. This must be accessible to all of the other team's pawns.
     */
    public static Piece enpassantablePawn;

    /**
     * No-arg constructor
     */
    public Game(){

        gameOn = true;
        whiteTurn = true;

        white = new Player(true);
        black = new Player(false);

        drawOffered = false;

        board = new Board();

        for(Piece p : white.getPieces()){
            board.set(p, p.getRow(), p.getCol());
        }

        for(Piece p : black.getPieces()){
            board.set(p, p.getRow(), p.getCol());
        }

    }

    //make copy of game
    public Game(Game game){

        this.board = new Board();

        this.white = new Player(true);

        this.white.getPieces().clear();

        this.white.setChecked(game.getWhite().isChecked());

        for(Piece p : game.getWhite().getPieces()){
            Piece p2 = copyPiece(p);
            this.white.getPieces().add(p2);
            this.board.set(p2, p2.getRow(), p2.getCol());

            if(p2 instanceof King){
                this.getWhite().setKing((King) p2);
            }

            if(p == enpassantablePawn){
                enpassantablePawn = p2;
            }
            //this.board.set(p2, p2.getRow(), p2.getCol());
        }

        this.black = new Player(false);

        this.black.getPieces().clear();
        this.black.setChecked(game.getBlack().isChecked());

        for(Piece p : game.getBlack().getPieces()){
            Piece p2 = copyPiece(p);
            this.black.getPieces().add(p2);
            this.board.set(p2, p2.getRow(), p2.getCol());

            if(p2 instanceof King){
                this.getBlack().setKing((King) p2);
            }

            if(p == enpassantablePawn){
                enpassantablePawn = p2;
            }
            //this.board.set(p2, p2.getRow(), p2.getCol());
        }


        this.gameOn = game.isGameOn();
        this.whiteTurn = game.isWhiteTurn();
        this.drawOffered = game.isDrawOffered();

    }

    private Piece copyPiece(Piece p){
        if(p instanceof Pawn){
            return new Pawn(p.isWhite(), p.getRow(), p.getCol());
        }else if(p instanceof Rook) {
            return new Rook(p.isWhite(), p.getRow(), p.getCol());
        }else if(p instanceof Knight) {
            return new Knight(p.isWhite(), p.getRow(), p.getCol());
        }else if(p instanceof Bishop) {
            return new Bishop(p.isWhite(), p.getRow(), p.getCol());
        }else if(p instanceof Queen) {
            return new Queen(p.isWhite(), p.getRow(), p.getCol());
        }else{
            return new King(p.isWhite(), p.getRow(), p.getCol());
        }
    }

    /**
     * Function for drawing the board after each turn is made.
     */
    public void drawBoard(){

        //draw board on screen
        for(int row = 7; row >= 0; row--) {
            for (int col = 0; col < 8; col++) {

                ImageButton b = PlayActivity.getButton(7 - row, col);

                int id = pieceToID(row, col);

                if(id == -1){
                    b.setImageResource(0);
                    b.setTag(0);
                }else {
                    b.setImageResource(id);
                    b.setTag(id);
                }
            }
        }

        //////////////////////////////////////////////////////////

        //draw board in terminal
        /*
        System.out.println();

        for(int row = 7; row >= 0; row--){
            for(int col = 0; col < 8; col++){

                Piece piece = board.get(row, col);

                if(piece != null){
                    System.out.print(piece.getName());
                }else{
                    int sum = row + col;

                    if(sum % 2 == 0)
                        System.out.print("##");
                    else
                        System.out.print("  ");
                }

                System.out.print(" ");
            }

            System.out.print(row+1 + "\n");
        }

        System.out.print(" a  b  c  d  e  f  g  h  \n");

        System.out.println();
        */
    }

    public void drawBoardTerminal(){
        //draw board in terminal
        System.out.println();

        for(int row = 7; row >= 0; row--){
            for(int col = 0; col < 8; col++){

                Piece piece = board.get(row, col);

                if(piece != null){
                    System.out.print(piece.getName());
                }else{
                    int sum = row + col;

                    if(sum % 2 == 0)
                        System.out.print("##");
                    else
                        System.out.print("  ");
                }

                System.out.print(" ");
            }

            System.out.print(row+1 + "\n");
        }

        System.out.print(" a  b  c  d  e  f  g  h  \n");

        System.out.println();
    }

    public void playback_Board(){

        //draw recorded game board on screen

        for(int row = 7; row >= 0; row--) {
            for (int col = 0; col < 8; col++) {

                ImageButton b = PlaybackActivity.getButton(7 - row, col);

                int id = pieceToID(row, col);

                if(id == -1){
                    b.setImageResource(0);
                    b.setTag(0);
                }else {
                    b.setImageResource(id);
                    b.setTag(id);
                }
            }
        }

        //draw board in terminal
        /*
        System.out.println();

        for(int row = 7; row >= 0; row--){
            for(int col = 0; col < 8; col++){

                Piece piece = board.get(row, col);

                if(piece != null){
                    System.out.print(piece.getName());
                }else{
                    int sum = row + col;

                    if(sum % 2 == 0)
                        System.out.print("##");
                    else
                        System.out.print("  ");
                }

                System.out.print(" ");
            }

            System.out.print(row+1 + "\n");
        }

        System.out.print(" a  b  c  d  e  f  g  h  \n");

        System.out.println();
        */
    }


    public int pieceToID(int row, int col){

        Piece p = board.get(row, col);

        if(p instanceof Pawn){
            if(p.isWhite()){
                return R.drawable.whitepawn;
            }else{
                return R.drawable.blackpawn;
            }
        }else if(p instanceof Rook){
            if(p.isWhite()){
                return R.drawable.whiterook;
            }else{
                return R.drawable.blackrook;
            }
        }else if(p instanceof Knight){
            if(p.isWhite()){
                return R.drawable.whiteknight;
            }else{
                return R.drawable.blackknight;
            }
        }else if(p instanceof Bishop){
            if(p.isWhite()){
                return R.drawable.whitebishop;
            }else{
                return R.drawable.blackbishop;
            }
        }else if(p instanceof Queen){
            if(p.isWhite()){
                return R.drawable.whitequeen;
            }else{
                return R.drawable.blackqueen;
            }
        }else if(p instanceof King){
            if(p.isWhite()){
                return R.drawable.whiteking;
            }else{
                return R.drawable.blackking;
            }
        }

        return -1;

    }



    /**
     *
     * @param input The line from the scanner that the user inputs when it is their turn.
     * @return true if valid move is made. Otherwise prints illegal move message and prompts to try again.
     */
    public boolean takeTurn(String input){

        String[] tokens = input.split("\\s+");

        int count = tokens.length;


        if(count == 2 || count == 3){

            //reset variable because draw is not completed if there is more than one token
            drawOffered = false;

            //get tokens
            String src = tokens[0];
            String dst = tokens[1];

            //check length of first two tokens
            if(src.length() != 2 || dst.length() != 2)
                return false;

            //check column bounds

            int srcCol = src.charAt(0) - 97 ;
            int dstCol = dst.charAt(0) - 97 ;

            if(srcCol < 0 || srcCol > 7 || dstCol < 0 || dstCol > 7)
                return false;


            //check row bounds

            if( !Character.isDigit(src.charAt(1)) || !Character.isDigit(dst.charAt(1)) ){
                return false;
            }

            int srcRow = src.charAt(1) - '0' - 1;
            int dstRow = dst.charAt(1) - '0' - 1;

            if(srcRow < 0 || srcRow > 7 || dstRow < 0 || dstRow > 7)
                return false;

            //store piece to move as a piece variable
            Piece pieceToMove = board.get(srcRow, srcCol);

            //reference to piece taken, if any
            Piece temp = board.get(dstRow, dstCol);


            //no piece
            if(pieceToMove == null)
                return false;

            //other team's piece
            if(pieceToMove.isWhite() != whiteTurn)
                return false;

            //piece is allowed to move, unless it exposes its own king
            if(pieceToMove.isValidMove(board, dstRow, dstCol)){

                pieceToMove.move(board, dstRow, dstCol);

                //check if king exposed
                if ( whiteTurn ) {
                    int x = white.getKing().getRow();
                    int y = white.getKing().getCol();

                    for (Piece p: black.getPieces()) {

                        //skip over a piece that is killed during the move
                        if(p == temp)
                            continue;

                        //move is not allowed because it would expose king. Undo move and return false.
                        if ( p.isValidMove(board, x, y) ) {
                            pieceToMove.move(board, srcRow, srcCol);
                            board.set(temp, dstRow, dstCol);
                            return false;
                        }
                    }
                } else {
                    int x = black.getKing().getRow();
                    int y = black.getKing().getCol();

                    for (Piece p: white.getPieces()) {

                        //skip over a piece that is killed during the move
                        if(p == temp)
                            continue;

                        //move is not allowed because it would expose king. Undo move and return false.
                        if ( p.isValidMove(board, x, y) ) {
                            pieceToMove.move(board, srcRow, srcCol);
                            board.set(temp, dstRow, dstCol);
                            return false;
                        }
                    }
                }



                //piece is not capable of performing this move, return false
            }else
                return false;


            //check for pawn killing enpassantable -- did the pawn move diagonally and there was no piece occupying its new location?
            if(pieceToMove instanceof Pawn && Math.abs(dstCol - srcCol) == 1 && temp == null){
                killEnpassantablePawn();
            }

            //check for pawn being enpassantable
            if(pieceToMove instanceof Pawn && Math.abs(dstRow - srcRow) == 2){
                enpassantablePawn = pieceToMove;
            }else{
                enpassantablePawn = null;
            }


            //move made. If a piece was killed remove it from player list of pieces
            if(temp != null){

                if(whiteTurn)
                    black.getPieces().remove(temp);
                else
                    white.getPieces().remove(temp);

            }

            //lastly check for enemy king checked/checkmate
            if ( whiteTurn ) {
                int x = black.getKing().getRow();
                int y = black.getKing().getCol();

                for (Piece p: white.getPieces()) {
                    if ( p.isValidMove(board, x, y) ) {
                        black.setChecked(true);
                        if ( checkMate(black) )
                            black.setDefeated(true);
                    }
                }
            } else {
                int x = white.getKing().getRow();
                int y = white.getKing().getCol();

                for (Piece p: black.getPieces()) {
                    if ( p.isValidMove(board, x, y) ) {
                        white.setChecked(true);
                        if ( checkMate(white) )
                            white.setDefeated(true);
                    }
                }
            }



            return true;

        }





        //count is not between 1 and 3
        return false;

    }

    /**
     * This function is called when a pawn is promoted and a new piece must replace it.
     *
     * @param pawn Pawn to promote
     * @param newPiece The Piece that will replace the pawn at its location.
     */
    public void promote(Piece pawn, Piece newPiece){

        if(whiteTurn){
            white.getPieces().add(newPiece);
            white.getPieces().remove(pawn);
        }else{
            black.getPieces().add(newPiece);
            black.getPieces().remove(pawn);
        }

        board.set(newPiece, newPiece.getRow(), newPiece.getCol());
    }

    /**
     * When an enpassant is performed, the pawn that was jumped over must be killed.
     */
    public void killEnpassantablePawn(){

        if(whiteTurn){
            black.getPieces().remove(enpassantablePawn);
        }else{
            white.getPieces().remove(enpassantablePawn);
        }

        if(enpassantablePawn != null)
            board.remove(enpassantablePawn.getRow(), enpassantablePawn.getCol());

        enpassantablePawn = null;

    }

    /**
     * Used to determine if a check is also a checkmate. Determines if the player that is checked has any legal moves.
     *
     * @param playerChecked The player that is under check and possibly checkmated.
     * @return true if the player is checkmated and the game is over.
     */
    public boolean checkMate(Player playerChecked) {

        Player otherPlayer;

        boolean valid;

        if(playerChecked.isWhite())
            otherPlayer = black;
        else
            otherPlayer = white;


        for (Piece p: playerChecked.getPieces()) {

            int srcRow = p.getRow();
            int srcCol = p.getCol();

            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 7; j++) {

                    if (p.isValidMove(board, i, j)) {

                        //possible move, but does it stop the check?
                        valid = true;

                        //simulate move
                        Piece temp = board.get(i, j);

                        p.move(board, i, j);


                        //check new state of the board. Does check still exist?
                        for (Piece o: otherPlayer.getPieces()) {

                            //if piece is killed we have to skip over it
                            if(o == temp)
                                continue;

                            //check if any pieces can move to the king.
                            if ( o.isValidMove(board, playerChecked.getKing().getRow(), playerChecked.getKing().getCol()) ) {
                                valid = false;
                                break;
                            }
                        }

                        //undo move
                        p.move(board, srcRow, srcCol);
                        board.set(temp, i, j);


                        if(valid)
                            return false;


                    }
                }
            }

        }

        return true;
    }

    public Board getBoard(){
        return board;
    }

    public void setBoard (Board b) {
        board = b;
    }

    public Player getWhite() {
        return white;
    }

    public Player getBlack() {
        return black;
    }

    public boolean isGameOn() {
        return gameOn;
    }

    public boolean isWhiteTurn() {
        return whiteTurn;
    }

    public void setWhiteTurn(boolean whiteTurn){
        this.whiteTurn = whiteTurn;
    }

    public boolean isDrawOffered() {
        return drawOffered;
    }

}

