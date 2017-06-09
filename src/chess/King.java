package chess;
 
/**********************************************************************
 * 
 * King class to determine the possible moves a king can make during
 * a chess game.
 * 
 * @author Daniel Cummings, Brandon Baars
 * @version 1.0
 *********************************************************************/
public class King extends ChessPiece{
     
    /** boolean for if the king has moved or not */
    private boolean moved;
 
    /******************************************************************
     * 
     * Constructor for a king object which sets the moved field to 
     * false and passes the parent class the player who owns the piece.
     * 
     * @param player the player who owns the piece.
     *****************************************************************/
    protected King(Player player) {
        super(player);
        moved = false;
    }
 
    @Override
    public String type() {
        return "King";
    }
     
    @Override
    public boolean isValidMove(Move move, IChessPiece[][] board){
 
        if(!moved)
            if(castle(move, board))
                return true;
 
        if(super.isValidMove(move, board)){
 
            //Checks for all 8 squares around the king
            if(move.fromColumn + 1 == move.toColumn 
                    && move.fromRow == move.toRow)
                return true;
            else if(move.fromColumn - 1 == move.toColumn 
                    && move.fromRow == move.toRow)
                return true;        
            else if(move.fromColumn == move.toColumn 
                    && move.fromRow + 1 == move.toRow)
                return true;
            else if(move.fromColumn == move.toColumn 
                    && move.fromRow - 1 == move.toRow)
                return true;
            else if(move.fromColumn - 1 == move.toColumn 
                    && move.fromRow - 1 == move.toRow)
                return true;
            else if(move.fromColumn + 1 == move.toColumn 
                    && move.fromRow + 1 == move.toRow)
                return true;
            else if(move.fromColumn - 1 == move.toColumn 
                    && move.fromRow + 1 == move.toRow)
                return true;
            else if(move.fromColumn + 1 == move.toColumn
                    && move.fromRow - 1 == move.toRow)
                return true;
        }
        return false;
    }
 
    /******************************************************************
     * 
     * Private helper method for the king class' isValidMove method.
     * Determines whether or not the king can castle from his current
     * status. 
     * 
     * @param move move for the king.
     * @param board current status of the chess game.
     * @return True if castling is possible, otherwise false.
     *****************************************************************/
    private boolean castle(Move move, IChessPiece[][] board){
 
        if(board[move.toRow][move.toColumn] instanceof Rook && 
                (board[move.toRow][move.toColumn].player() ==
                board[move.fromRow][move.fromColumn].player()) && 
                !((Rook) board[move.toRow][move.toColumn]).hasMoved()){
 
            if(move.toColumn == 0 && move.fromRow == 0){
                for(int i = 1; i < 4; i++){
                    if(board[move.fromRow][i] != null)
                        return false;
                }
            }
            else if(move.toColumn == 7 && move.fromRow == 0){
                for(int i = 6; i > 4; i--){
                    if(board[move.fromRow][i] != null)
                        return false;
                }
            }
            else if((move.toColumn == 0 && move.fromRow == 7)){
                for(int i = 1; i < 4; i++){
                    if(board[move.fromRow][i] != null)
                        return false;
                }
            }
            else if((move.toColumn == 7 && move.fromRow == 7)){
                for(int i = 6; i > 4; i--){
                    if(board[move.fromRow][i] != null)
                        return false;
                }
            }
            return true;
        }
        return false;
    }
 
    /******************************************************************
     * 
     * Method changes the value of the moved field to true.
     * 
     *****************************************************************/
    public void moved() {
        moved = true;
 
    }
    /******************************************************************
     * 
     * Method returns the status of the moved field.
     * 
     * @return true if the king has moved, otherwise false.
     *****************************************************************/
    public boolean hasMoved(){
        return moved;
    }
 
 
}