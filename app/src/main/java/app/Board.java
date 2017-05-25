/**
 * @author Jacob Rizer
 * @author Terence Williams
 */

package app;

import pieces.*;
import java.io.Serializable;

/**
 * This class represents the board
 * that is used in our chess game.
 * It is a grid of Pieces that will
 * be altered as each move in the game
 * is made.
 */
public class Board implements Serializable{

    /**
     * 2D array of Pieces. an empty spot is a null Piece.
     */
    private Piece[][] board;

    /**
     * No-arg constructor
     */
    public Board(){
        board = new Piece[8][8];
    }

    /**
     * Get a Piece given a row and column.
     *
     * @param row A given row
     * @param col A given column
     * @return The piece at that location on the board
     */
    public Piece get(int row, int col){

        if(row < 0 || row > 7 || col < 0 || col > 7)
            return null;

        return board[row][col];
    }

    /**
     * Set a location on the board with a given Piece.
     *
     * @param piece The piece to set
     * @param row The row to set the piece
     * @param col The column to set the piece
     */
    public void set(Piece piece, int row, int col){

        if(row < 0 || row > 7 || col < 0 || col > 7)
            return;

        board[row][col] = piece;
    }

    /**
     * Check if a location on the board has a Piece or is empty.
     *
     * @param row The row to check
     * @param col The column to check
     * @return true if the location on the board is empty.
     */
    public boolean isEmpty(int row, int col){
        if(get(row, col) == null)
            return true;

        return false;
    }

    /**
     * Remove a Piece from a given location on the board.
     *
     * @param row The row to remove from
     * @param col The column to remove from
     */
    public void remove(int row, int col){
        set(null, row, col);
    }

    public Piece[][] getBoard () {
        return board;
    }

    public void copy_values (Piece[][] b){
        for (int i = 0; i < b.length; i++) {
            for(int j = 0; j < b[0].length; j++) {
                board[i][j] = b[i][j];
            }
        }
    }

}