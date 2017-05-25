/**
 * @author Jacob Rizer
 * @author Terence Williams
 */

package pieces;

import app.Board;

/**
 *
 * Our King class that extends @Piece.
 * Can only move one space in any direction.
 * Is important for determining endgame scenarios.
 *
 */
public class King extends Piece {

    /**
     * Boolean for whether the king has moved or not.
     * Used for castling.
     */
    private boolean hasMoved;

    /**
     * Constructor
     *
     * @param isWhite Determines whether King is white or black.
     * @param x Row of king
     * @param y Column of king
     */
    public King(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        this.name = this.name.concat("K");
        hasMoved = false;
    }

    /**
     * King's implementation of isValidMove()
     */
    @Override
    public boolean isValidMove(Board board, int newRow, int newCol) {

        if (!super.initial_check (board, newRow, newCol))
            return false;


        //differences in row and col can not be more than 1	unless castling
        int rowDifference = Math.abs(newRow - this.row);
        int colDifference = Math.abs(newCol - this.col);

        //check for castle
        if(rowDifference == 0 && colDifference == 2){

            if(this.hasMoved)
                return false;

            Piece piece;

            //check for clear path
            if(newCol > this.col){

                piece = board.get(this.row, 7);

                for(int i = this.col + 1; i < 7; i++){
                    if(board.get(this.row, i) != null)
                        return false;
                }

            }else{

                piece = board.get(this.row, 0);

                for(int i = this.col - 1; i < 0; i--){
                    if(board.get(this.row, i) != null)
                        return false;
                }

            }

            if(!(piece instanceof Rook))
                return false;

            if(((Rook)piece).hasMoved())
                return false;


            //all conditions met -- valid castling move
            return true;





        }

        //not a castle

        if(rowDifference > 1 || colDifference > 1){
            return false;
        }


        return true;
    }

    /**
     * King's implementation of pathClear()
     */
    @Override
    public boolean pathClear(Board board, int newRow, int newCol){

        if (!board.isEmpty(newRow, newCol)) {
            if (isWhite == board.get(newRow, newCol).isWhite)
                return false;
        }

        return true;
    }

    /**
     * King's implementation of move().
     * Necessary to incorporate castling maneuver.
     */
    @Override
    public void move(Board board, int newRow, int newCol){

        int colDifference = Math.abs(newCol - this.col);

        //castle -- move rook first
        if(colDifference == 2){

            Piece rook;

            if(newCol > this.col){
                rook = board.get(this.row, 7);
                rook.move(board, newRow, newCol - 1);
            }else{
                rook = board.get(this.row, 0);
                rook.move(board, newRow, newCol + 1);
            }

        }

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
