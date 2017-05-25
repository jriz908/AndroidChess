/**
 * @author Jacob Rizer
 * @author Terence Williams
 */

package pieces;

import app.Board;

/**
 *
 * Our bishop class that extends @Piece.
 * Can only move diagonally.
 *
 *
 */
public class Bishop extends Piece {

    /**
     * Constructor
     *
     * @param isWhite Whether the piece is white or black.
     * @param x The row of the piece.
     * @param y The column of the piece.
     */
    public Bishop(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        this.name = this.name.concat("B");
    }

    /**
     * The bishops implementation of isValidMove()
     */
    @Override
    public boolean isValidMove(Board board, int newRow, int newCol) {

        if (!super.initial_check (board, newRow, newCol))
            return false;

        //ensure that neither row nor col remains the same
        if (row == newRow || col == newCol)
            return false;

        //verifying that the new coordinates are diagonal to the current position
        if ( Math.abs(newCol - col) != Math.abs(newRow - row) )
            return false;

        if ( pathClear(board, newRow, newCol) )
            return true;

        return false;
    }

    /**
     * The bishops implementation of pathClear()
     */
    @Override
    public boolean pathClear(Board board, int newRow, int newCol){
        boolean increase_row = newRow > row;
        boolean increase_col = newCol > col;

        //if the row is increasing
        if (increase_row) {
            //if the col is increasing
            if (increase_col) {
                int j = col + 1;
                for (int i = row + 1; i < newRow; i++){
                    if (!board.isEmpty(i, j))
                        return false;

                    j++;
                }
                //if the col is decreasing
            }else {
                int j = col - 1;
                for (int i = row + 1; i < newRow; i++){
                    if (!board.isEmpty(i, j))
                        return false;

                    j--;
                }
            }
            //if the row is decreasing
        }else {
            // if the col is increasing
            if (increase_col) {
                int j = col + 1;
                for (int i = row - 1; i > newRow; i--){
                    if (!board.isEmpty(i, j))
                        return false;

                    j++;
                }
                //if the col is decreasing
            }else {
                int j = col - 1;
                for (int i = row - 1; i > newRow; i--){
                    if (!board.isEmpty(i, j))
                        return false;

                    j--;
                }
            }

        }

        return true;
    }

}
