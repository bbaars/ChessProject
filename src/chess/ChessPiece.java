package chess;
/**********************************************************************
 * 
 * ChessPiece class that implements the ChessPiece interface to create
 * individual pieces. This class is abstract to make each "Piece"
 * Override each method.
 * 
 * @author Daniel Cummings, Brandon Baars
 * @version 1.0
 *********************************************************************/
public abstract class ChessPiece implements IChessPiece{

	/** The owner of the piece */
	private Player owner;

	/******************************************************************
	 * 
	 * Protected method that assigns the given player to the piece that
	 * calls it.
	 * 
	 * @param player Player who owns the piece.
	 *****************************************************************/
	protected ChessPiece(Player player){
		this.owner = player;
	}
	@Override
	public Player player() {
		return owner;
	}

	@Override
	public abstract String type();

	@Override
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		
		 //make sure it is inside the board
		if((move.toRow <= board.length && move.toRow >= 0)
				&& (move.toColumn <= board.length &&
				move.toColumn >= 0)){
			
			// make sure the piece has moved
			if((move.fromRow != move.toRow) || 
					(move.fromColumn != move.toColumn)){
				
				// make sure the moving to piece is not occupied by
				// the current players piece
				if(board[move.toRow][move.toColumn]	== null || 
						board[move.toRow][move.toColumn].player()
						!= player())
					return true;
				
			}
		}
		
		return false;

	}
}

