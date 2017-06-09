package chess;
 
/**********************************************************************
 * 
 * Pawn class to determine the possible moves a Pawn can make during
 * a chess game.
 * 
 * @author Daniel Cummings, Brandon Baars
 * @version 1.0
 *********************************************************************/
public class Pawn extends ChessPiece{
 
    /******************************************************************
     * 
     * Constructor to create a Pawn object. Passing the player who
     * owns it to the ChessPiece class.
     * 
     * @param player the player who owns the piece.
     *****************************************************************/
    protected Pawn(Player player) {
        super(player);
    }
 
    @Override
    public String type() {
        return "Pawn";
    }
 
    @Override
    public boolean isValidMove(Move move, IChessPiece[][] board){
 
        //if the player attempts to move to the current location
        if(super.isValidMove(move, board)){
 
            if(super.player() == Player.BLACK){
                //black pieces
 
                if(move.fromRow == 1){
 
                    if(board[move.fromRow + 1][move.fromColumn] == 
                            null){
                        //if the player moves forward one space
                        if(((move.fromRow + 2 == move.toRow) && 
                                (move.fromColumn == move.toColumn)) && 
                                (board[move.toRow][move.toColumn] ==
                                null))
                            return true;
                    }
                }
 
                //if the player moves forward one space
                if(((move.fromRow + 1 == move.toRow) && 
                        (move.fromColumn == move.toColumn)) && 
                        (board[move.toRow][move.toColumn] == null))
                    return true;
 
                //if there is a piece diagonally 
                if(((move.fromRow + 1 == move.toRow) && 
                        (move.fromColumn + 1 == move.toColumn)) && 
                        (board[move.toRow][move.toColumn] != null))
                    return true;
 
                //if there is a piece diagonally 
                if(move.fromRow + 1 == move.toRow &&
                        move.fromColumn - 1 == move.toColumn && 
                        board[move.toRow][move.toColumn] != null)
                    return true;
            }
 
            else{
 
                if(move.fromRow == 6){
 
                    if(board[move.fromRow - 1][move.fromColumn] == 
                            null){
                        //if the player moves forward one space
                        if(((move.fromRow - 2 == move.toRow) && 
                                (move.fromColumn == move.toColumn)) && 
                                (board[move.toRow][move.toColumn] == 
                                null))
                            return true;
 
                    }
                }
 
                //white pieces
                //if the player moves forward one space
                if(((move.fromRow - 1 == move.toRow) && 
                        (move.fromColumn == move.toColumn)) && 
                        (board[move.toRow][move.toColumn] == null))
                    return true;
 
                //if there is a piece diagonally 
                if(((move.fromRow - 1 == move.toRow) && 
                        (move.fromColumn - 1 == move.toColumn)) && 
                        (board[move.toRow][move.toColumn] != null))
                    return true;
 
                //if there is a piece diagonally
                if(((move.fromRow - 1 == move.toRow) && 
                        (move.fromColumn + 1 == move.toColumn)) && 
                        (board[move.toRow][move.toColumn] != null))
                    return true;
 
            }
        }
        return false;
    }
}