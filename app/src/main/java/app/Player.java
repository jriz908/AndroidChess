/**
 * @author Jacob Rizer
 * @author Terence Williams
 */

package app;

import java.util.ArrayList;
import java.util.List;

import pieces.*;

/**
 *
 * This class represents a player.
 * The player contains a list of pieces,
 * is either white or black, and booleans
 * for if the player is checked or defeated.
 *
 */
public class Player {

    /**
     * Boolean for whether the player is white or black
     */
    private boolean isWhite;

    /**
     * Boolean for whether the player is defeated or not
     */
    private boolean isDefeated;

    /**
     * A player's list of pieces
     */
    private List<Piece> pieces;

    /**
     * A reference to the player's king. Important for determining checks and checkmates.
     */
    private King king;

    /**
     * A boolean for whether the player is checked.
     */
    private boolean isChecked;

    /**
     * Constructor
     *
     * @param isWhite Determines whether this player is black or white.
     */
    public Player(boolean isWhite){

        this.isWhite = isWhite;
        isDefeated = false;

        pieces = new ArrayList<Piece>();

        if(isWhite){
            pieces.add(new Rook(true, 0, 0));
            pieces.add(new Knight(true, 0, 1));
            pieces.add(new Bishop(true, 0, 2));
            pieces.add(new Queen(true, 0, 3));
            king = new King(true, 0, 4);
            pieces.add(king);
            pieces.add(new Bishop(true, 0, 5));
            pieces.add(new Knight(true, 0, 6));
            pieces.add(new Rook(true, 0, 7));

            for(int i = 0; i < 8; i++){
                pieces.add(new Pawn(true, 1, i));
            }

        }else{
            pieces.add(new Rook(false, 7, 0));
            pieces.add(new Knight(false, 7, 1));
            pieces.add(new Bishop(false, 7, 2));
            pieces.add(new Queen(false, 7, 3));
            king = new King(false, 7, 4);
            pieces.add(king);
            pieces.add(new Bishop(false, 7, 5));
            pieces.add(new Knight(false, 7, 6));
            pieces.add(new Rook(false, 7, 7));

            for(int i = 0; i < 8; i++){
                pieces.add(new Pawn(false, 6, i));
            }
        }


    }

    public Piece getPiece(int index){
        return pieces.get(index);
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public void setPieces(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public void setWhite(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public boolean isDefeated() {
        return isDefeated;
    }

    public void setDefeated(boolean isDefeated) {
        this.isDefeated = isDefeated;
    }

    public King getKing() {
        return king;
    }

    public void setKing(King king) {
        this.king = king;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

}
