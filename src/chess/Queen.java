package chess;

public class Queen extends ChessPiece{
	 
    protected Queen(Player player) {
        super(player);
    }
 
    @Override
    public String type() {
        return "Queen";
    }
 
    public boolean isValidMove(Move move, IChessPiece[][] board){
    	 	
    	Rook rook = new Rook(super.player());
    	Bishop bishop = new Bishop(super.player());
    	
    	if(rook.isValidMove(move, board) || 
    			bishop.isValidMove(move, board))
    		return true;
     	
    	return false;
    }    
}
