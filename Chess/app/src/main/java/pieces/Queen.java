/**
 * @author Jacob Rizer
 * @author Terence Williams
 */

package pieces;

import app.Board;

/**
 *
 * This is our Queen class which extends @Piece.
 *
 * The Queen combines the rook and the bishop and can move
 * As many spaces as it wants in any one direction.
 *
 */
public class Queen extends Piece {


    /**
     * Constructor
     *
     * @param isWhite Determines whether Queen is white or black.
     * @param x Row of Queen.
     * @param y Column of Queen.
     */
    public Queen(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        this.name = this.name.concat("Q");
    }

    /**
     * Queen's implementation of isValidMove()
     */
    @Override
    public boolean isValidMove(Board board, int newRow, int newCol) {

        if (!super.initial_check (board, newRow, newCol))
            return false;

        //ensure that if row and col changes that the change is equal in terms of abs value
        if(newRow != row && newCol != col){
            if ( Math.abs(newCol - col) != Math.abs(newRow - row) )
                return false;
        }

        //now check if path is clear
        if ( pathClear(board, newRow, newCol))
            return true;

        return false;
    }

    /**
     * Queen's implementation of pathClear()
     */
    @Override
    public boolean pathClear(Board board, int newRow, int newCol){

        boolean diagonal;

        //check diagonal
        if (row != newRow && col != newCol) {
            diagonal = true;
            //verifying that the new coordinates are diagonal to the current position
            if ( Math.abs(newCol - col) != Math.abs(newRow - row) )
                return false;

        }else
            diagonal = false;


        boolean increase_row = newRow > row;
        boolean increase_col = newCol > col;

        if (diagonal) {
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
            //end of diagonal if statement
        }

        // if orthogonal
        else {

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

            //if the column is increasing
            else if (increase_col) {
                for(int i = col + 1; i < newCol; i++) {
                    if (!board.isEmpty(row, i))
                        return false;
                }
                //if column is actually decreasing and not the same
            } else if(newCol != col){
                for(int i = col - 1; i > newCol; i--) {
                    if (!board.isEmpty(row, i))
                        return false;
                }
            }
            //end of orthogonal if statement
        }

        return true;
    }

}
