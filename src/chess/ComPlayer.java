package chess;
 
import java.util.ArrayList;
 
public class ComPlayer extends ChessModel{
     
    private IChessPiece[][] board;
    private ArrayList<Integer> value;
    private ArrayList<Move> compMoveList;
    private ArrayList<Move> playerMoveList;
    private Player computerColor;
 
    public ComPlayer(){
        value = new ArrayList<Integer>();
        compMoveList = new ArrayList<Move>();
        playerMoveList = new ArrayList<Move>();
        computerColor = Player.BLACK;
    }
 
    public void calcMoves(IChessPiece[][] board){
        compMoveList.clear();
        playerMoveList.clear();
        value.clear();
        this.board = board;
        Move move;
        for(int row = 0; row < board.length; row++){
            System.out.println("");
            for(int col = 0; col < board.length; col++){
                System.out.print("  " + board[row][col]);
                if(board[row][col] != null && 
                        board[row][col].player() == Player.BLACK){
                    System.out.println(board[row][col].type());
                    for(int row2 = 0; row2 < board.length; row2++){
 
                        for(int col2 = 0; col2 < board.length; col2++){
 
                            move = new Move(row, col, row2, col2);
 
                            if(isValidMove(move)){
                                compMoveList.add(move);
                            }
 
                        }
                    }
                }
 
                else if(board[row][col] != null &&
                        board[row][col].player() == Player.WHITE){
                    for(int row2 = 0; row2 < board.length; row2++){
 
                        for(int col2 = 0; col2 < board.length; col2++){
 
                            move = new Move(row, col, row2, col2);
 
                            if(isValidMove(move)){
                                playerMoveList.add(move);
                            }
                        }
                    }
                }
            }
        }
 
 
        calcValues();
    }
 
    private void calcValues(){
        int valued = 0;
        Move compMove;
        Move playerMove;
        System.out.println(compMoveList.size());
        System.out.println(playerMoveList.size());
        for(int i = 0; i < compMoveList.size(); i++){
            compMove = compMoveList.get(i);
            valued = 0;
            if(board[compMove.fromRow][compMove.fromColumn] instanceof
                    Pawn)
                valued += -1;
            for(int j = 0; j < playerMoveList.size(); j++){
                playerMove = playerMoveList.get(j);
 
                if((compMove.toColumn == playerMove.toColumn) &&
                        (compMove.toRow == playerMove.toRow))
                    valued += -15;
                else
                    playerMoveList.remove(playerMove);
 
                if(board[compMove.toRow][compMove.toColumn] != null)
                    valued += 10;
 
                if(super.placesCheck(compMove, 
                        Player.WHITE)){
                    valued += 40;
                }
                if(super.inCheck(computerColor)){
                    if(board[compMove.toRow][compMove.toColumn] != 
                            null){
                        valued += 15;
                    }
                    else
                        valued += 10;
                }
            }
            value.add(valued);
        }
    }
 
    public Move getBestMove(){
        int temp = 0;
        int loc = 0;
        System.out.println(value.size());
        for(int i = loc; i < value.size(); i++){
            if(value.get(i) > temp){
                temp = value.get(i);
                loc = i;
            }           
        }
        Move move = compMoveList.get(loc);
        return move;
    }
 
 
}