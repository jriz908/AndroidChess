/**
 * @author Jacob Rizer
 * @author Terence Williams
 */

package pieces;

import app.Board;
/**
 *
 * This is our Knight class which extends @Piece
 * Can only move 2 spaces in one direction and then
 * one one space in the perpendicular direction.
 */
public class Knight extends Piece {

    /**
     * Constructor
     *
     * @param isWhite Determines whether Knight is white or black.
     * @param x Row of knight.
     * @param y Column of knight.
     */
    public Knight(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        this.name = this.name.concat("N");
    }

    /**
     * Knight's implementation of isValidMove()
     */
    @Override
    public boolean isValidMove(Board board, int newRow, int newCol) {

        if (!super.initial_check (board, newRow, newCol))
            return false;

        //either the row or the column must be changed by 2
        if ( (Math.abs(row - newRow) != 2) && (Math.abs(col - newCol) != 2) )
            return false;

        if ( Math.abs(row - newRow) == 2 ) {
            if ( Math.abs(col - newCol) != 1)
                return false;
        }

        if ( Math.abs(col - newCol) == 2 ) {
            if ( Math.abs(row - newRow) != 1)
                return false;
        }

        return true;
    }

    /**
     * Knight's implementation of pathClear()
     */
    @Override
    public boolean pathClear(Board board, int newRow, int newCol){
        boolean isWhite = this.isWhite;

        if (!board.isEmpty(newRow, newCol)) {
            if (isWhite == board.get(newRow, newCol).isWhite)
                return false;
        }

        return true;
    }

}
