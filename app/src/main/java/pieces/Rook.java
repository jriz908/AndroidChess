/**
 * @author Jacob Rizer
 * @author Terence Williams
 */

package pieces;

import app.Board;

/**
 *
 * This is our Rook class which extends @Piece
 *
 * The rook can only move orthogonally
 * in one direction and as many spaces
 * as it wishes.
 *
 */
public class Rook extends Piece {

    /**
     * Boolean for whether the rook has moved yet.
     * Necessary for castling maneuver.
     */
    private boolean hasMoved;

    /**
     * Constructor
     *
     * @param isWhite Determines whether Rook is white or black.
     * @param x Row of Rook.
     * @param y Column of Rook.
     */
    public Rook(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        this.name = this.name.concat("R");
        this.hasMoved = false;
    }

    /**
     * Rook's implementation of isValidMove()
     */
    @Override
    public boolean isValidMove(Board board, int newRow, int newCol) {

        if (!super.initial_check (board, newRow, newCol))
            return false;

        //ensure that only row or col is changed, not both.
        if(row != newRow && col != newCol){
            return false;
        }

        if ( pathClear(board, newRow, newCol) )
            return true;

        return false;
    }


    /**
     * Rook's implementation of pathClear()
     */
    @Override
    public boolean pathClear(Board board, int newRow, int newCol){
        boolean increase_row = newRow > row;
        boolean increase_col = newCol > col;

        //if the row is increasing
        if (increase_row) {
            for(int i = row + 1; i < newRow; i++) {
                if (!board.isEmpty(i, col))
                    return false;
            }
            //if row is actually decreasing and not the same
        } else if(newRow != row){
            for(int i = row - 1; i > newRow; i--) {
                if (!board.isEmpty(i, col))
                    return false;

            }
        }

        //if the col is increasing
        else if (increase_col) {
            for(int i = col + 1; i < newCol; i++) {
                if (!board.isEmpty(row, i))
                    return false;

            }
            //if col is actually decreasing and not the same
        } else if(newCol != col){
            for(int i = col - 1; i > newCol; i--) {
                if (!board.isEmpty(row, i))
                    return false;

            }
        }

        return true;
    }

    /**
     * Rook's implementation of move().
     * Necessary for updating whether the rook has moved
     * for the castling maneuver.
     *
     */
    @Override
    public void move(Board board, int newRow, int newCol){

        super.move(board, newRow, newCol);

        this.hasMoved = true;

    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

}
