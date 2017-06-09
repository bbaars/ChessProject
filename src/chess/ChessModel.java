package chess;
 
import java.util.ArrayList;
 
/**********************************************************************
 * 
 * ChessModel implements the ChessModel interface to create and operate
 * each move, check, and checkmate in the game of Chess. The model
 * controls the technical side of the game.
 * 
 * @author Daniel Cummings, Brandon Baars
 * @version 1.0
 *********************************************************************/
public class ChessModel implements IChessModel {
 
    /** The current chess board. */
    public IChessPiece[][] board;
 
    /** The current player. */
    private Player player;
 
    /** starting/ending row, col. for piece moves. */
    private int sRow, sCol, eRow, eCol;
 
    /** boolean for whether or not the user is playing against a 
     * computer user.
     */
    public boolean comp;
 
    /** ArrayLists for storing all of the moves for the computer player
     * and all of the moves for the user.
     */
    private ArrayList<Move> compMoveList, playerMoveList;
 
    /** ArrayList of integers for the value of each move when
     * calculated.
     */
    private ArrayList<Integer> value;
    
    private ArrayList<String> takenHumanPieces;
 
    /******************************************************************
     * 
     * ChessModel constructor that creates the initial board and sets
     * the starting player.
     * 
     *****************************************************************/
    public ChessModel(){
        board = new ChessPiece[8][8];
        setPieces();
        player = Player.WHITE;
        comp = false;
        takenHumanPieces = new ArrayList<String>();
        takenHumanPieces.add("Pawn");
    }
 
    @Override
    public boolean isComplete() {
        Move move;
        int count = 0;
         
        for (int row = 0; row < board.length; row++)
            for (int col = 0; col < board.length; col++)
                if(board[row][col] != null){
                    count++;
                }
         
        if(count < 3){
            return true;
        }
 
        if(inCheck(player)){
 
            for (int row = 0; row < board.length; row++)
                for (int col = 0; col < board.length; col++)
                     
                    if(board[row][col] != null &&
                    board[row][col].player() == player)
 
                        for (int row2 = 0; row2 < board.length; row2++)
                            for (int col2 = 0; col2 < board.length; 
                                    col2++){
 
                                move = new Move(row, col, row2, col2);
 
                                if(isValidMove(move))
                                    return false;
                            }
            return true;
        }
        return false;
    }
 
    @Override
    public boolean isValidMove(Move move) {
 
        if(board[move.fromRow][move.fromColumn] != null &&
                board[move.fromRow][move.fromColumn].player() == player)
            if(board[move.fromRow][move.fromColumn] != null &&
            board[move.fromRow][move.fromColumn].isValidMove(
                    move, board))
                if(!placesCheck(move, player))
                    return true;
 
        return false;
    }
 
    @Override
    public void move(Move move) {
 
        //huge check for king moves to make castling possible
        if(board[move.fromRow][move.fromColumn].type().equals("King") 
                && move.fromColumn == 4){
            if(move.toRow == 0 && move.toColumn == 7){
                board[0][6] = board[0][4];
                board[0][5] = board[0][7];
                board[0][4] = null;
                board[0][7] = null;
            }
            else if(move.toRow == 0 && move.toColumn == 0){
                board[0][2] = board[0][4];
                board[0][3] = board[0][0];
                board[0][4] = null;
                board[0][0] = null;
            }
            else if(move.toRow == 7 && move.toColumn == 0){
                board[7][2] = board[7][4];
                board[7][3] = board[7][0];
                board[7][4] = null;
                board[7][0] = null;
            }
            else if(move.toRow == 7 && move.toColumn == 7){
                board[7][6] = board[7][4];
                board[7][5] = board[7][7];
                board[7][4] = null;
                board[7][7] = null;
            }
            // happens if the move is not a castle.
            else{
                board[move.toRow][move.toColumn] = 
                        board[move.fromRow][move.fromColumn];
                board[move.fromRow][move.fromColumn] = null;                
            }
        }
 
        // governs all other piece movements
        else{
            board[move.toRow][move.toColumn] = 
                    board[move.fromRow][move.fromColumn];
 
            board[move.fromRow][move.fromColumn] = null;
        }
    }
 
    /******************************************************************
     * 
     * Method changes the player from white to black and vice versa.
     * 
     *****************************************************************/
    public void changePlayer(){
        player = player.next();
    }
 
    /******************************************************************
     * 
     * Method checks whether or not there is a computer player in the 
     * game. If so it will execute two commands to find the best
     * move for the computer on the board. Once it has been found  
     * 
     *****************************************************************/
    public void makeComputerMove(){
        if(comp == true){
            calcMoves();
            Move move = getBestMove();
 
            if(canExchangePawn(move)){
                int rand = (int) Math.ceil(Math.random() * 4);
                if(rand == 1)
                    promotePawn("Bishop", move);
                else if(rand == 2)
                    promotePawn("Knight", move);
                else if(rand == 3)
                    promotePawn("Queen", move);
                else if(rand == 4)
                    promotePawn("Rook", move);
            }
            else
                move(move);
        }
    }
 
    @Override
    public boolean inCheck(Player p) {
 
        for(int row = 0 ; row < this.numRows() ; row++)
            for(int col = 0 ; col < this.numColumns() ; col++){
 
                //if the board is not null
                if(board[row][col] != null){
 
                    //if the board is a king
                    if(board[row][col].type() == "King" && 
                            board[row][col].player() == p){                     
                        //sets the ending row and col to the kings 
                        //position
                        eRow = row;
                        eCol = col; 
                    }
                }
            }   
 
        for(int row = 0 ; row < this.numRows() ; row++)
            for(int col = 0 ; col < this.numColumns() ; col++){
 
                sRow = row;
                sCol = col;
 
                //if it's not null
                if(board[row][col] != null){
 
                    //new move with the piece and the ending position 
                    //of where the king is
                    Move move = new Move(sRow, sCol,
                            eRow, eCol);
 
                    //if the piece is a valid move, it's in check
                    if(board[row][col].isValidMove(move, board)
                            && board[row][col].player() != p)
                        return true;
 
                }
            }
        return false;
    }
 
    @Override
    public Player currentPlayer() {
        return player;
    }
 
    /******************************************************************
     * 
     * Returns the piece at the given row and column.
     * 
     * @param row in which the piece is located.
     * @param column in which the piece is located.
     * @return The ChessPiece from the given location.
     *****************************************************************/
    public IChessPiece pieceAt(int row, int column){
        return board[row][column];
    }
 
    /******************************************************************
     * 
     * Returns the number of rows on the game board.
     * 
     * @return the number of rows on the board.
     *****************************************************************/
    public int numRows(){
        return board.length;
    }
 
    /******************************************************************
     * 
     * Returns the number of columns on the game board.
     * 
     * @return the number of columns on the board.
     *****************************************************************/
    public int numColumns(){
        return board.length;
    }
 
    /******************************************************************
     *
     * Private helper method to add the pieces for each player onto the
     * board. Each player and piece is created and added to the start
     * location.
     * 
     *****************************************************************/
    private void setPieces(){
        //which Player owns the coming pieces
        Player player = Player.BLACK;
 
        //create Black rook
        board[0][0] = new Rook(player);
        board[0][7] = new Rook(player);
 
        //create Black knight
        board[0][1] = new Knight(player);
        board[0][6] = new Knight(player);
 
        //create Black Bishop
        board[0][2] = new Bishop(player);
        board[0][5] = new Bishop(player);
 
        //create black queen
        board[0][3] = new Queen(player);
 
        //create black king
        board[0][4] = new King(player);
 
        //create black pawns
        for(int i = 0; i < board.length; i++){
            board[1][i] = new Pawn(player);
        }
 
 
        // which Player owns the coming piece
        player = Player.WHITE;
 
        //create white rook
        board[7][0] = new Rook(player);
        board[7][7] = new Rook(player);
 
        //create white knight
        board[7][1] = new Knight(player);
        board[7][6] = new Knight(player);
 
        //create white Bishop
        board[7][2] = new Bishop(player);
        board[7][5] = new Bishop(player);
 
        //create white queen
        board[7][3] = new Queen(player);
 
        //create white king
        board[7][4] = new King(player);
 
        // create white pawns
        for(int i = 0; i < board.length; i++){
            board[6][i] = new Pawn(player);
        }
 
        for(int i = 2 ; i < 6; i++)
            for(int j = 0 ; j < board.length ; j++)
                board[i][j] = null;
 
    }
 
    /******************************************************************
     * 
     * Method creates a new chess game by resetting all of the pieces.
     * Then makes the player WHITE again.
     * 
     *****************************************************************/
    public void newGame(){
        board = new IChessPiece[8][8];
        setPieces();
        player = Player.WHITE;
        if(comp){
            compMoveList = new ArrayList<Move>();
            playerMoveList = new ArrayList<Move>();
            value = new ArrayList<Integer>();
        }
    }
 
    /******************************************************************
     * 
     * Method checks whether or not the move that is about to be taken
     * places the given players king into check. If it does, the method
     * returns true. Otherwise, returns false.
     * 
     * @param move the given move the player wants to make.
     * @param p the player who we are checking.
     * @return true if move will place player in check, otherwise
     * false.
     *****************************************************************/
    public boolean placesCheck(Move move, Player p){
        // creates a new chess piece array to save the current one.
        IChessPiece[][] temp = new ChessPiece[8][8];
        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){
                temp[row][col] = board[row][col];
            }
        }
 
        // makes the given move on the board.
        move(move);
 
        //return true if the user is placed into check.
        if(inCheck(p)){
            board = temp;
            return true;
        }
        else{
            board = temp;
            return false;
        }
    }
 
    /******************************************************************
     * 
     * Method checks whether or not the pawn is being moved into it's
     * eighth rank position.
     * 
     * @param move move of the pawn.
     * @return true if the pawn is moving into its eighth position
     *  otherwise, false.
     *****************************************************************/
    public boolean canExchangePawn(Move move){
 
        if(board[move.fromRow][move.fromColumn] instanceof Pawn){
            if(currentPlayer() == Player.WHITE){
                if(move.toRow == 0)
                    return true;    
            }
            else if(currentPlayer() == Player.BLACK)
                if(move.toRow == 7)
                    return true;
        }
        return false;
    }
 
    /******************************************************************
     * 
     * Pawn promotion method to determine which type of piece the user
     * is going to be trading for. String holds the name of the piece
     * the user selected and move hold the location that piece will
     * occupy.
     * 
     * @param type String for the type of piece the user selected.
     * @param move Move of the pawn for the location of the new piece.
     *****************************************************************/
    public void promotePawn(String type, Move move){
 
        // change the pawn to the type of piece.
        board[move.fromRow][move.fromColumn] = null;
        
        if(type.equals("Queen")){
            board[move.toRow][move.toColumn] = null;
            board[move.toRow][move.toColumn] = new Queen(player);
        }
        else if(type.equals("Knight")){
            board[move.toRow][move.toColumn] = null;
            board[move.toRow][move.toColumn] = new Knight(player);
        }
        else if(type.equals("Bishop")){
            board[move.toRow][move.toColumn] = null;
            board[move.toRow][move.toColumn] = new Bishop(player);
        }
        else if(type.equals("Rook")){
            board[move.toRow][move.toColumn] = null;
            board[move.toRow][move.toColumn] = new Rook(player);
            
            if(board[move.fromRow][move.fromColumn] instanceof Rook){
                ((Rook) board[move.fromRow][move.fromColumn]).moved();
            }
        }
        else if (type.equals("Pawn")) {
            board[move.toRow][move.toColumn] = null;
            board[move.toRow][move.toColumn] = new Pawn(player);
        }
    }
 
    /******************************************************************
     * 
     * Private helper method to calculate every possible move on the
     * current board for both players. This method is called so the 
     * computer player can check all of its moves. Moves are saved
     * into an ArrayList to be used in another method which is called
     * at the end of this method.
     * 
     *****************************************************************/
    private void calcMoves(){
 
        compMoveList.clear();
        playerMoveList.clear();
        value.clear();
 
        Move move;
        for(int row = 0; row < board.length; row++){
            for(int col = 0; col < board.length; col++){
                //check for computer player and all moves.
                if(board[row][col] != null && 
                        board[row][col].player() == Player.BLACK){
                    player = Player.BLACK;
                    for(int row2 = 0; row2 < board.length; row2++){
 
                        for(int col2 = 0; col2 < board.length; col2++){
 
                            move = new Move(row, col, row2, col2);
 
                            if(isValidMove(move)){
                                compMoveList.add(move);
                            }
 
                        }
                    }
                }
 
                //check for human player and all moves.
                else if(board[row][col] != null &&
                        board[row][col].player() == Player.WHITE){
                    player = Player.WHITE;
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
        player = Player.BLACK;
        System.out.println(compMoveList.size());
        //calculate values for the moves the computer can make.
        calcValues();
    }
 
    /******************************************************************
     * 
     * Second private helper method for the computer player. This
     * method acts as the brain portion of the computer player. Going
     * through an ArrayList of moves for both the computer and user.
     * determining the "value" of each move the computer could make.
     * Better moves have higher values which are stored in a third 
     * ArrayList corresponding to each move.
     * 
     *****************************************************************/
    private void calcValues(){
        int valued = 0;
        Move compMove;
        Move playerMove;
        for(int i = 0; i < compMoveList.size(); i++){
            compMove = compMoveList.get(i);
            valued = 0;
 
            // search against the human players moves.
            for(int j = 0; j < playerMoveList.size(); j++){
                playerMove = playerMoveList.get(j);
                //check if the next move could result in a lost piece.
                if((compMove.toColumn == playerMove.toColumn) &&
                        (compMove.toRow == playerMove.toRow)){
                    valued = -20;
                    break;
                }
                else
                    valued = 15;
            }
 
            // check for the farthest move the piece can make.
            if(compMove.toRow > compMove.fromRow){
                valued += 2;
            }
 
            // if the location on the board is occupied by another
            // piece. (Cannot be your own)
            if(board[compMove.toRow][compMove.toColumn] != null)
                valued += 25;
 
            // check if this move will place the user in check.
            if(placesCheck(compMove, Player.WHITE)){
                valued += 30;
            }
 
            // checks if the computer is in check
            if(inCheck(Player.BLACK)){
                // check if you can take out the piece that has you
                // check
                if(board[compMove.toRow][compMove.toColumn] != 
                        null){
                    valued += 15;
                }
                else
                    valued += 10;
            }
            if(canExchangePawn(compMove)){
                valued += 30;
            }
            value.add(valued);
        }
    }
 
    /******************************************************************
     * 
     * Third and final private helper method for the computer player.
     * The method goes through each of the values stored by the second
     * helper method and determines which has the highest value. Once
     * that value is found the index of that move is called and 
     * returned to the method that called it.
     * 
     * @return best possible move the computer can make at the given
     * time.
     *****************************************************************/
    private Move getBestMove(){
 
        //looks for the best current move.
        int tempValue = 0;
        int loc = 0;
 
        for(int i = 0; i < value.size(); i++){
            if(value.get(i).intValue() > tempValue){
                tempValue = value.get(i).intValue();
                loc = i;
            }
        }
 
        //picks the best move possible.
        Move move = compMoveList.get(loc);
        
        if(board[move.toRow][move.toColumn] != null && 
        		board[move.toRow][move.toColumn].player()
        		== Player.WHITE){
        		takenHumanPieces.add(pieceAt(move.toRow,move.toColumn).type());
        		
        		System.out.println(takenHumanPieces);
        }
        
        // if the computer moves a rook use this.
        if(board[move.fromRow][move.fromColumn] instanceof Rook){
            ((Rook) board[move.fromRow][move.fromColumn]).moved();
        }
        // if it moves a king use this.
        else if(board[move.fromRow][move.fromColumn] instanceof King){
            ((King) board[move.fromRow][move.fromColumn]).moved();
        }
        return move;
    }
    
    /*****************************************************************
     * Gets the taken Human Pieces that the computer has taken
     * 
     * @return takenHumanPieces the array List of strings 
     *****************************************************************/
    public ArrayList<String> getTakenHumanPieces(){
    	return takenHumanPieces; 	
    }
 
}