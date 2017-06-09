package chess;
 
/**********************************************************************
 * 
 * Knight class to determine the possible moves a knight can make 
 * during a chess game.
 * 
 * @author Daniel Cummings, Brandon Baars
 * @version 1.0
 *********************************************************************/
public class Knight extends ChessPiece{
     
    /******************************************************************
     * 
     * Constructor to create a Knight object. Passing the player who
     * owns it to the ChessPiece class.
     * 
     * @param player the player who owns the piece.
     *****************************************************************/
    protected Knight(Player player) {
        super(player);
    }
  
    @Override
    public String type() {
        return "Knight";
    }
     
    @Override
    public boolean isValidMove(Move move, IChessPiece[][] board){
         
        if(super.isValidMove(move, board)){
                     
            if((move.fromRow - 2 == move.toRow) && 
                    (move.fromColumn - 1 == move.toColumn))
                return true;
            else if((move.fromRow - 2 == move.toRow) &&
                    (move.fromColumn + 1 == move.toColumn))
                return true;
            else if((move.fromRow - 1 == move.toRow) &&
                    (move.fromColumn + 2 == move.toColumn))
                return true;
            else if((move.fromRow + 1 == move.toRow) && 
                    (move.fromColumn + 2 == move.toColumn))
                return true;
            else if((move.fromRow + 2 == move.toRow) && 
                    (move.fromColumn - 1 == move.toColumn))
                return true;
            else if((move.fromRow + 2 == move.toRow) && 
                    (move.fromColumn + 1 == move.toColumn))
                return true;
            else if((move.fromRow - 1 == move.toRow) &&
                    (move.fromColumn - 2 == move.toColumn))
                return true;
            else if((move.fromRow + 1 == move.toRow) && 
                    (move.fromColumn - 2 == move.toColumn))
                return true;        
        }
         
        return false;
    } 
}