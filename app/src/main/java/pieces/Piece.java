/**
 * @author Jacob Rizer
 * @author Terence Williams
 */

package pieces;

import app.Board;
import java.io.Serializable;
/**
 *
 * This is our abstract class for a Piece.
 * Each specific piece such as a rook will
 * extend this class and have its own implementation.
 *
 */
public abstract class Piece implements Serializable {

    /**
     * Each piece has a name value that is used for printing the board in the console.
     */
    protected String name;

    /**
     * Each piece is either black or white.
     */
    protected boolean isWhite;

    /**
     * Each piece is in a certain row on the board.
     */
    protected int row;

    /*
     * Each piece is in a certain column of the board.
     */
    protected int col;

    /**
     * Constructor
     *
     * @param isWhite Determines whether piece is white or black.
     * @param row Determines row that the piece belongs in.
     * @param col Determines column that the piece belongs in.
     */
    public Piece(boolean isWhite, int row, int col){
        this.isWhite = isWhite;
        this.row = row;
        this.col = col;

        if(isWhite)
            this.name = "w";
        else
            this.name = "b";
    }

    /**
     * Each piece can test whether it is capable of moving to a new location
     * This depends on what type of piece it is.
     *
     * @param board The gameboard.
     * @param newRow The new row that the piece is trying to move to.
     * @param newCol The new column that the piece is trying to move to.
     * @return true if the piece is capable of moving to the new location (disregarding checks/checkmates).
     */
    public abstract boolean isValidMove(Board board, int newRow, int newCol);

    /**
     * Used in our isValidMove() function to ensure that nothing is in the way of our path to a new location.
     *
     * @param board The gameboard.
     * @param newRow The new row that the piece is trying to move to.
     * @param newCol The new column that the piece is trying to move to.
     * @return true if nothing is in the way.
     */
    public abstract boolean pathClear(Board board, int newRow, int newCol);

    /**
     * Function to actually move a piece to a new location.
     *
     * @param board The gameboard.
     * @param newRow The new row that the piece moves to.
     * @param newCol The new column that the piece moves to.
     */
    public void move(Board board, int newRow, int newCol){

        board.remove(this.row, this.col);

        if(board.get(newRow, newCol) != null){
            board.remove(newRow, newCol);
        }

        this.row = newRow;
        this.col = newCol;

        board.set(this, newRow, newCol);

    }

    /**
     * An initial check that our specific piece classes all perform.
     * Checks to make sure that the piece isn't moving to the spot that
     * it already inhabits and that it isn't moving to a spot inhabited by another
     * friendly piece.
     *
     * @param board The gameboard.
     * @param newRow The new row that the piece is trying to move to.
     * @param newCol The new column that the piece is trying to move to.
     * @return true if it passes the initial check.
     */
    public boolean initial_check (Board board, int newRow, int newCol) {

        //if the value entered is the same as the current position
        if (newRow == row && newCol == row)
            return false;

        //if the new spot is occupied by a friendly piece
        if(board.get(newRow, newCol) != null){
            if(board.get(newRow, newCol).isWhite == this.isWhite)
                return false;
        }

        return true;

    }

    public String getName() {
        return name;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }


}
