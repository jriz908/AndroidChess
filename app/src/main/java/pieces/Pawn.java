/**
 * @author Jacob Rizer
 * @author Terence Williams
 */

package pieces;

import app.Board;
import app.Game;


/**
 *
 * This is our Pawn class which extends @Piece
 *
 * The pawn may move 1 space forward or 2 spaces forward
 * if it hasn't moved yet.
 *
 * It may also move one space forward and to the side if it is capturing
 * a piece or performing the enpassant maneuver.
 *
 */
public class Pawn extends Piece {

    /**
     * Boolean for whether the pawn has moved yet.
     * Necessary for determining if it can move two spaces
     * Or if another pawn can perform enpassant on it.
     *
     */
    boolean hasMoved;

    /**
     * Constructor
     *
     * @param isWhite Determines whether Pawn is white or black.
     * @param x Row of Pawn.
     * @param y Column of Pawn.
     */
    public Pawn(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        this.name = this.name.concat("P");
        hasMoved = false;

    }

    /**
     * Pawn's implementation of isValidMove().
     */
    @Override
    public boolean isValidMove(Board board, int newRow, int newCol) {

        int invert;

        if (!super.initial_check (board, newRow, newCol))
            return false;

        if(isWhite){
            invert = 1;
        }else{
            invert = -1;
        }

        if(hasMoved){

            if(newRow != this.row + 1*invert){
                return false;
            }

            if(newCol == this.col){
                if(pathClear(board, newRow, newCol))
                    return true;
                else
                    return false;
            }

            if(newCol == this.col + 1 || newCol == this.col - 1){

                if(!board.isEmpty(newRow, newCol)){
                    return true;
                }

                //check for enpassant
                if(Game.enpassantablePawn != null){
                    if(this.row == Game.enpassantablePawn.getRow() && newCol == Game.enpassantablePawn.getCol()){
                        return true;
                    }
                }

                return false;
            }

        }else{

            if(newRow != this.row + 1*invert && newRow != this.row + 2*invert){
                return false;
            }

            if(newCol == this.col){
                if(pathClear(board, newRow, newCol))
                    return true;
                else
                    return false;
            }

            if(newCol == this.col + 1 || newCol == this.col - 1 && newRow == this.row + 1*invert){
                if(!board.isEmpty(newRow, newCol)){
                    return true;
                }

                return false;
            }





        }


        return false;
    }

    /**
     * Pawn's implementation of pathClear()
     */
    @Override
    public boolean pathClear(Board board, int newRow, int newCol){

        int invert;

        if(isWhite){
            invert = 1;
        }else{
            invert = -1;
        }

        if(!board.isEmpty(this.row + 1*invert, this.col)){
            return false;
        }

        if(newRow == this.row + 2*invert){
            if(!board.isEmpty(this.row + 2*invert, this.col))
                return false;
        }

        return true;

    }

    /**
     * Pawn's implementation of move().
     * Necessary for updating whether the pawn has
     * moved yet.
     */
    @Override
    public void move(Board board, int newRow, int newCol){

        super.move(board, newRow, newCol);

        this.hasMoved = true;

    }

    /**
     * Function to check if the pawn has moved to the end of the board
     * and will be promoted.
     *
     * @return true if ready for promotion.
     */
    public boolean checkPromotion(){

        int rowForPromotion;

        if(isWhite)
            rowForPromotion = 7;
        else
            rowForPromotion = 0;

        if(this.row == rowForPromotion){
            return true;
        }

        return false;

    }



    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

}
