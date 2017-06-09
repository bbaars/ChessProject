package chess;
 
/**********************************************************************
 * 
 * Bishop Class for determining the moves a bishop piece can play 
 * during the game of chess.
 * 
 * @author Daniel Cummings, Brandon Baars
 * @version 1.0
 *********************************************************************/
public class Bishop extends ChessPiece{
 
    /******************************************************************
     * 
     * Constructor to create a Bishop object. Passing the player who
     * owns it to the ChessPiece class.
     * 
     * @param player the player who owns the piece.
     *****************************************************************/
    protected Bishop(Player player) {
        super(player);
    }
 
    @Override
    public String type() {
        return "Bishop";
    }
 
    @Override
    public boolean isValidMove(Move move, IChessPiece[][] board){
 
        if(super.isValidMove(move, board)){
 
            int canMoveTo = piecesBetween(move, board);
 
            //System.out.println(canMoveTo);
 
            if(move.toRow < move.fromRow && move.toColumn > 
                    move.fromColumn){
                if(((move.toRow == (move.fromRow - canMoveTo))) && 
                        (move.toColumn == (move.fromColumn + 
                                canMoveTo)))
                    return true;
            }
            else if(move.toRow < move.fromRow && move.toColumn < 
                        move.fromColumn){
                if(((move.toRow == (move.fromRow - canMoveTo))) && 
                        (move.toColumn == (move.fromColumn -
                                canMoveTo)))
                    return true;
            }
            else if(move.toRow > move.fromRow && move.toColumn > 
                        move.fromColumn){
                if(((move.toRow == (move.fromRow + canMoveTo))) && 
                        (move.toColumn == (move.fromColumn + 
                                canMoveTo)))
                    return true;        
            }
            else if(move.toRow > move.fromRow && move.toColumn <
                        move.fromColumn){
                if(((move.toRow == (move.fromRow + canMoveTo))) && 
                        (move.toColumn == (move.fromColumn - 
                                canMoveTo)))
                    return true;
            }       
        }
        return false;
    }
 
    /******************************************************************
     * 
     * Private helper method to determine the number of possible spaces
     * the Bishop can move to from their given location on the board.
     * 
     * @param move contains the ending location and the beginning
     * location for the bishop
     * @param board the current status of the chess board.
     * @return An integer for the number of squares the piece can move.
     *****************************************************************/
    private int piecesBetween(Move move, IChessPiece[][] board){
 
        int count = 0;
         
        //moving up to the right
        if(move.toRow < move.fromRow && move.toColumn > 
        move.fromColumn){
             
            for(int r = move.fromRow - 1, c = move.fromColumn + 1 ;
                    r >= move.toRow && c <= move.toColumn  ; r--, c++){
                 
                if(board[r][c] == null)
                    count ++;
                else{
                    if(board[r][c].player() == player())
                        return count;
                    else if(board[r][c].player() != player())
                        return (count + 1);
                }       
            }               
        }
         
        //moving up to the left
        else if(move.toRow < move.fromRow && move.toColumn <
                move.fromColumn){
             
            for(int r = move.fromRow - 1, c = move.fromColumn - 1 ;
                    r >= move.toRow && c >= move.toColumn  ; r--, c--){
                 
                if(board[r][c] == null)
                    count ++;
                else{
                    if(board[r][c].player() == player())
                        return count;
                    else if(board[r][c].player() != player())
                        return (count + 1);
                }       
            }
        }
        else if(move.toRow > move.fromRow && move.toColumn > 
                move.fromColumn){
             
            for(int r = move.fromRow + 1, c = move.fromColumn + 1 ;
                    r <= move.toRow && c <= move.toColumn  ; r++, c++){
                 
                if(board[r][c] == null)
                    count ++;
                else{
                    if(board[r][c].player() == player())
                        return count;
                    else if(board[r][c].player() != player())
                        return (count + 1);
                }       
            }
        }
         
        else if(move.toRow > move.fromRow && move.toColumn < 
                move.fromColumn){
             
            for(int r = move.fromRow + 1, c = move.fromColumn - 1 ;
                    r <= move.toRow && c >= move.toColumn  ; r++, c--){
                 
                if(board[r][c] == null)
                    count ++;
                else{
                    if(board[r][c].player() == player())
                        return count;
                    else if(board[r][c].player() != player())
                        return (count + 1);
                }       
            }
        }
 
        return count;
    }
}