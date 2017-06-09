package chess;
 
public class Rook extends ChessPiece{
    /** boolean for if the kings has moved or not */
    private boolean moved;
 
    protected Rook(Player player) {
        super(player);
        moved = false;
    }
 
    @Override
    public String type() {
        return "Rook";
    }
 
 
    @Override
    public boolean isValidMove(Move move, IChessPiece[][] board){
 
        if(super.isValidMove(move, board)){
 
            int canMoveTo = piecesBetween(move, board);
 
            if(move.fromRow == move.toRow || move.fromColumn ==
                    move.toColumn){
                //moving down
                if(move.toRow > move.fromRow){
                    if(move.toRow <= (move.fromRow + canMoveTo))
                        return true;
                }
                //moving up
                else if(move.toRow < move.fromRow){
                    if(move.toRow >= (move.fromRow - canMoveTo))
                        return true;
                }
                //moving to the right
                else if(move.toColumn > move.fromColumn){
                    if(move.toColumn <= (move.fromColumn + canMoveTo))
                        return true;
                }
 
                //moving to the left
                else if(move.toColumn < move.fromColumn){
                    if(move.toColumn >= (move.fromColumn - canMoveTo))
                        return true;
                }
            }
        } 
 
        return false;
    }
 
    /******************************************************************
     * 
     * Like the Bishop class, this private method helps the isValidMove
     * method to determine the number of squares the rook can move on
     * the given board.
     * 
     * @param move rooks movements.
     * @param board current status of the board.
     * @return An integer for the number of squares the rook can move.
     *****************************************************************/
    private int piecesBetween(Move move, IChessPiece[][] board){
 
        int count = 0;
 
        //moving down
        if(move.toRow > move.fromRow){
            for(int row = move.fromRow + 1 ; row <= move.toRow; row++){
 
                if(board[row][move.fromColumn] == null)         
                    count++;    
                else{
                    if(board[row][move.fromColumn].player()
                            == player())
                        return count;
                    else if(board[row][move.fromColumn].player() 
                            != player())
                        return (count + 1); 
                }
            }  
        }
 
        //moving up
        else if(move.toRow < move.fromRow){
 
            for(int row = move.fromRow - 1 ; row >= move.toRow; row--){
 
                if(board[row][move.fromColumn] == null)         
                    count++;    
                else{
                    if(board[row][move.fromColumn].player() 
                            == player())
                        return count;
                    else if(board[row][move.fromColumn].player() 
                            != player())
                        return (count + 1); 
                }
            }
        }
 
        //check for columns
 
        //moving to the right
        else if(move.toColumn > move.fromColumn){
 
            for(int col = move.fromColumn + 1 ; col <= move.toColumn; 
                    col++){
 
                if(board[move.fromRow][col] == null)            
                    count++;    
                else{
                    if(board[move.fromRow][col].player() == player())
                        return count;
                    else if(board[move.fromRow][col].player() != 
                            player())
                        return (count + 1); 
                }
            }  
        }
        //moving to the left
        else if(move.toColumn < move.fromColumn){
 
            for(int col = move.fromColumn - 1 ; col >= move.toColumn;
                    col--){
 
                if(board[move.fromRow][col] == null)            
                    count++;    
                else{
                    if(board[move.fromRow][col].player() == player())
                        return count;
                    else if(board[move.fromRow][col].player() 
                            != player())
                        return (count + 1); 
                }
            }
 
 
        }
        return count;
    }
 
    public void moved(){
        if(!moved){
            moved = true;
        }
    }
     
    public boolean  hasMoved(){
        return moved;
    }
     
}