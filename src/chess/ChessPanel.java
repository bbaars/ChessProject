package chess;
 
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;
 
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
 
/**********************************************************************
 * 
 * ChessPanel is the face of the Chess game. It displays the pieces 
 * giving the user a graphical interface in which to interact with the
 * ChessModel class.
 * 
 * @author Brandon Baars, Daniel Cummings
 * @version 1.0
 *********************************************************************/
public class ChessPanel extends JPanel implements MouseListener, 
MouseMotionListener {
 
    private static final long serialVersionUID = 1L;
 
    /** image icons for importing the photos */
    private ImageIcon whitePawn, blackPawn, whiteBishop, blackBishop,
    blackKing, whiteKing, blackQueen, whiteQueen, blackRook, whiteRook,
    blackKnight, whiteKnight;
 
    /** ArrayList of JLabels for the chess pieces */
    private ArrayList<JLabel> pieceLabel;
 
    /** two different JPanels to add components to */
    private JPanel chessBoard;
 
    /** Panels for the squares */
    private JPanel[][] square;
 
    /** layered Pane to add multiple images */
    private JLayeredPane layeredPane;
 
    /** adjustment of the cursor in both x and y positions */
    private int xAdj, yAdj;
 
    /** each chess piece is a label with image */
    private JLabel chessPiece;
 
    /** for creating the title above the pieces */
    private TitledBorder title;
 
    /** toggles if the user wants the names displayed */
    private boolean toggleChessNames;
 
    /** frame to add the menu bar */
    private JFrame frame;
 
    /** JMenu buttons to quit, toggle names, and start a new game
     * as well as starting a new game with a computer and
     * activating and deactivating hints
     */
    private JMenuItem quitButton, toggleNamesButton, newGameItem, 
    compNewGame, toggleHints;
 
    /** file menu */
    private JMenu fileMenu;
 
    /** menu bar to add the quit button to */
    private JMenuBar menuBar;
 
    /** stores the chess pieces */
    private ChessModel pieces;
 
    /** to find the starting and ending position relative to the board 
     */
    private int sRow, sCol, eRow, eCol;
 
    /** the x and y position on the screen */
    private int xVal, yVal;
 
    /** JLabel showing whose turn it is */
    private JLabel turnLabel;
 
    /** Array for pieces pawn can promote to */
    private ArrayList<String> takenWhitePieces, takenBlackPieces;
 
    /** boolean value for whether or not the game is over */
    private boolean gameOver, hints;
 
    /******************************************************************
     * 
     * The constructor that instantiates all of the fields to create
     * the GUI.
     * 
     * @param frame The frame in which the chess board is placed.
     *****************************************************************/
    public ChessPanel(JFrame frame){
        gameOver = false;
        hints = true;
 
        toggleChessNames = true;
        this.frame = frame;
 
 
        pieceLabel = new ArrayList<JLabel>();
 
        // two ArrayLists for the pieces captured.
        takenWhitePieces = new ArrayList<String>();
        takenBlackPieces = new ArrayList<String>();
        takenWhitePieces.add("Pawn");
        takenBlackPieces.add("Pawn");
 
 
        // Chess game model
        pieces = new ChessModel();
 
        //sets the board size to 600 by 600, each square being 75 pixel
        Dimension boardSize = new Dimension (600, 600);
 
        this.setLayout( new BorderLayout(30, 15));
        this.setBorder(new EmptyBorder(20, 25, 15, 15));
        turnLabel = new JLabel("Current Player:  " + 
                pieces.currentPlayer());
        turnLabel.setHorizontalAlignment(WIDTH/2);
        this.add(turnLabel, BorderLayout.PAGE_START);
 
 
        //preferred layout
        layeredPane = new JLayeredPane();
        this.add(layeredPane, BorderLayout.CENTER);
        layeredPane.setPreferredSize(boardSize);
        layeredPane.addMouseListener(this);
        layeredPane.addMouseMotionListener(this);
 
        //adding chess board to the layout
        chessBoard = new JPanel();
        layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
        chessBoard.setLayout(new GridLayout(8, 8));
        chessBoard.setPreferredSize(boardSize);
        chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);
 
        //creates each square of the board
        square = new JPanel[8][8];
 
        //creates each individual square and sets the background color
        createSquares();
 
        // Fills the board with pieces */
        pieceImages();
        displayBoard();
 
        //adds the menus to the top
        fileMenu = new JMenu("File");
        quitButton = new JMenuItem("Quit");
        newGameItem = new JMenuItem("New Game");
        compNewGame = new JMenuItem("New Computer Game");
        toggleNamesButton = new JMenuItem("Turn Off Names");
        toggleHints = new JMenuItem("Turn Off Hints");
        fileMenu.add(newGameItem);
        fileMenu.add(compNewGame);
        fileMenu.add(toggleNamesButton);
        fileMenu.add(toggleHints);
        fileMenu.add(quitButton);
        menuBar = new JMenuBar();  
        this.frame.setJMenuBar(menuBar);
        menuBar.add(fileMenu);
 
        //sets the panels to the screen 
        ButtonListener listener = new ButtonListener();
        quitButton.addActionListener(listener);
        newGameItem.addActionListener(listener);
        toggleHints.addActionListener(listener);
        compNewGame.addActionListener(listener);
        toggleNamesButton.addActionListener(listener);
 
    }
 
    /******************************************************************
     * 
     * Private helper method to add individual squares to the board.
     * While setting each square to the correct color.
     * 
     *****************************************************************/
    private void createSquares(){
 
        //creates each individual square and sets the background color
        for(int i = 0 ; i < square.length ; i++){
            for(int j = 0; j < square.length; j++){
                square[i][j] = new JPanel(new BorderLayout());
 
                //if its every other row
                if(i % 2 == 0)
                    //looks at every other column to set to white or 
                    //gray
                    square[i][j].setBackground(j % 2 == 0 ?
                            Color.white : Color.gray);
                else
                    square[i][j].setBackground(j % 2 == 1 ?
                            Color.white : Color.gray);
 
                chessBoard.add(square[i][j]);
 
            }
        }   
    }
 
    /****************************************************************** 
     * Private method that creates all of the ImageIcons to represent
     * each chess piece.
     * 
     *****************************************************************/
    private void pieceImages(){
        //Display the black pieces
 
        //Rooks
        blackRook = new ImageIcon(this.getClass().getResource(
                "/rookBlack.png")); 
 
        whiteRook = new ImageIcon(this.getClass().getResource(
                "/rookWhite.png")); 
 
        //Knights
        blackKnight = new ImageIcon(this.getClass().getResource(
                "/knightBlack.png"));   
 
        whiteKnight = new ImageIcon(this.getClass().getResource(
                "/knightWhite.png"));
 
        //bishops
        blackBishop  = new ImageIcon(this.getClass().getResource(
                "/bishopBlack.png"));
 
        whiteBishop  = new ImageIcon(this.getClass().getResource(
                "/bishopWhite.png"));
 
        //Queens
        blackQueen  = new ImageIcon(this.getClass().getResource(
                "/queenBlack.png"));
 
        whiteQueen  = new ImageIcon(this.getClass().getResource(
                "/queenWhite.png"));
 
        //Kings
        blackKing  = new ImageIcon(this.getClass().getResource(
                "/kingBlack.png"));
 
        whiteKing  = new ImageIcon(this.getClass().getResource(
                "/kingWhite.png"));
 
        //Pawns 
        blackPawn = new ImageIcon(this.getClass().getResource(
                "/pawnBlack.png"));
 
 
        whitePawn = new ImageIcon(this.getClass().getResource(
                "/pawnWhite.png"));
    }
 
    /******************************************************************
     * 
     * Private helper method to add the pieces to the board in the
     * correct starting location.
     * 
     *****************************************************************/
    private void displayBoard(){
 
        //removes all of the labels if they occupy a square.
        for(int i = 0; i < pieceLabel.size(); i++){
            Container parent = pieceLabel.get(i).getParent();
            parent.remove(pieceLabel.get(i));
            parent.validate();
            parent.repaint();
        }
 
        // clears the label ArrayList
        pieceLabel.clear();
 
        // finds location of pieces on the board and adds a label.
        for(int i = 0; i < pieces.board.length; i++){
            for(int j = 0; j < pieces.board.length; j++){
 
                if(pieces.board[i][j] instanceof Rook){
                    if(pieces.board[i][j].player() == Player.BLACK){
 
                        pieceLabel.add(new JLabel(blackRook));
                        square[i][j].add(pieceLabel.get(
                                pieceLabel.size() - 1));
                    }
                    else{
                        pieceLabel.add(new JLabel(whiteRook));
                        square[i][j].add(pieceLabel.get(
                                pieceLabel.size() - 1));
                    }
 
                }
                else if(pieces.board[i][j] instanceof Bishop){
                    if(pieces.board[i][j].player() == Player.BLACK){
                        pieceLabel.add(new JLabel(blackBishop));
                        square[i][j].add(pieceLabel.get(
                                pieceLabel.size() - 1));
                    }
                    else{
                        pieceLabel.add(new JLabel(whiteBishop));
                        square[i][j].add(pieceLabel.get(
                                pieceLabel.size() - 1));
                    }
                }
                else if(pieces.board[i][j] instanceof Queen){
                    if(pieces.board[i][j].player() == Player.BLACK){
                        pieceLabel.add(new JLabel(blackQueen));
                        square[i][j].add(pieceLabel.get(
                                pieceLabel.size() - 1));
                    }
                    else{
                        pieceLabel.add(new JLabel(whiteQueen));
                        square[i][j].add(pieceLabel.get(
                                pieceLabel.size() - 1));
                    }
                }
                else if(pieces.board[i][j] instanceof King){
                    if(pieces.board[i][j].player() == Player.BLACK){
                        pieceLabel.add(new JLabel(blackKing));
                        square[i][j].add(pieceLabel.get(
                                pieceLabel.size() - 1));
                    }
                    else{
                        pieceLabel.add(new JLabel(whiteKing));
                        square[i][j].add(pieceLabel.get(
                                pieceLabel.size() - 1));
                    }
                }
                else if(pieces.board[i][j] instanceof Pawn){
                    if(pieces.board[i][j].player() == Player.BLACK){
                        pieceLabel.add(new JLabel(blackPawn));
                        square[i][j].add(pieceLabel.get(
                                pieceLabel.size() - 1));
                    }
                    else{
                        pieceLabel.add(new JLabel(whitePawn));
                        square[i][j].add(pieceLabel.get(
                                pieceLabel.size() - 1));
                    }
                }
                else if(pieces.board[i][j] instanceof Knight){
                    if(pieces.board[i][j].player() == Player.BLACK){
                        pieceLabel.add(new JLabel(blackKnight));
                        square[i][j].add(pieceLabel.get(
                                pieceLabel.size() - 1));
                    }
                    else{
                        pieceLabel.add(new JLabel(whiteKnight));
                        square[i][j].add(pieceLabel.get(
                                pieceLabel.size() - 1));
                    }
                }
            }
        }
    }
 
    @Override
    public void mouseDragged(MouseEvent e) {
 
        //makes the piece follow the mouse
        if(chessPiece == null)
            return;
 
        chessPiece.setLocation(e.getX() + xAdj, e.getY() + yAdj);
 
    }
 
    @Override
    public void mousePressed(MouseEvent e) {
 
        // locks the board during game over.
        if(gameOver)
            return;
 
 
        chessPiece = null;
        Component c = chessBoard.findComponentAt(e.getX(),e.getY());
 
        //finds the point on the board in which the user clicks
        for(int i = 0 ; i < 8 ; i++)
            for(int j = 0 ; j < 8 ; j++)
                if(c.getParent() == square[i][j]){
                    sRow = i;
                    sCol = j;           
                }
 
 
        //if they click the board with  no piece, just return 
        if(c instanceof JPanel)
            return;
 
        // Locks pieces that do not belong to the current player.
        if(pieces.board[sRow][sCol].player() != pieces.currentPlayer())
            return;
 
        //finds the name of the piece the user clicks on 
        String chessPieceName = pieces.board[sRow][sCol].type();
 
        //gets the parent of the component (the chess piece clicked on)
        Point parentLocation = c.getParent().getLocation();
        xAdj = parentLocation.x - e.getX();
        yAdj = parentLocation.y - e.getY();
 
        //the place that was clicked set for when the user places an 
        //invalid move
        xVal = e.getX();
        yVal = e.getY();
 
        //the label of the piece
        chessPiece = (JLabel)c;
        chessPiece.setLocation(xAdj + e.getX(), yAdj + e.getY());
        chessPiece.setSize(chessPiece.getWidth(), 
                chessPiece.getHeight());
 
        //invalidMovePieceGraphic = chessPiece;
 
        //if the chess piece names are turned on it sets the border
        if(toggleChessNames){           
            title = BorderFactory.createTitledBorder(chessPieceName);
            title.setTitleJustification(TitledBorder.CENTER);
            chessPiece.setBorder(title);        
        }
 
        Border blackBorder = 
                BorderFactory.createLineBorder(Color.BLACK);
 
        //displays the valid moves the player could place that piece
        if(hints){
            for(int i = 0 ; i < 8 ; i++)
                for(int j = 0 ; j < 8 ; j++){
                    eRow = i;
                    eCol = j;
                    Move move = new Move(sRow, sCol, eRow, eCol);
 
                    if(pieces.isValidMove(move)){
                        if((pieces.board[eRow][eCol] != null) && 
                                (pieces.board[sRow][sCol].player() != 
                                pieces.board[eRow][eCol].player())){
                            square[i][j].setBackground(Color.red);
                            square[i][j].setBorder(blackBorder);                        
                        }
                        else{
                            square[i][j].setBackground(Color.yellow);
                            square[i][j].setBorder(blackBorder);
                        }
                    }
                }
        }
 
        //sets the piece to the drag layer 
        layeredPane.add(chessPiece, JLayeredPane.DRAG_LAYER);
 
    }
 
    @Override
    public void mouseReleased(MouseEvent e) {
 
        //if there was no chess piece grabbed
        if(chessPiece == null)
            return;
 
        chessPiece.setVisible(false);
        Component c = chessBoard.findComponentAt(e.getX(),e.getY());
 
        //removes the valid move places
        if(hints){
            for(int i = 0 ; i < 8 ; i++)
                for(int j = 0 ; j < 8 ; j++){
                    eRow = i;
                    eCol = j;
 
                    Move move = new Move(sRow, sCol, eRow, eCol);
 
                    //resets the color back to the original squares
                    if(pieces.isValidMove(move)){
                        if(i % 2 == 0)
                            square[i][j].setBackground(j % 2 == 0 ? 
                                    Color.white : Color.gray);
                        else
                            square[i][j].setBackground(j % 2 == 1 ? 
                                    Color.white : Color.gray);
 
                    }   
 
                    //removes the border
                    square[i][j].setBorder(null);
                }
        }
 
        //gets the row and column where the piece was placed
        for(int i = 0 ; i < 8 ; i++)
            for(int j = 0 ; j < 8 ; j++){
 
                //makes sure the piece doesn't try to get dropped off
                //edge of board
                if(e.getX()>=600 || e.getY() >= 600 || e.getX() <= 0
                        || e.getY() <= 0)
                    replacePiece();
 
                else if(c == square[i][j] || c.getParent() 
                        == square[i][j]){
                    eRow = i;
                    eCol = j;               
                }
            }
 
        //the move of from the starting row to ending row
        Move move = new Move(sRow, sCol, eRow, eCol);   
 
        //if it was a valid move        
        if(pieces.isValidMove(move)){
 
            // if the move is on top of another piece.
            if(c instanceof JLabel){
                if(pieces.board[eRow][eCol].player() == Player.WHITE)
                    takenWhitePieces.add(pieces.pieceAt(
                            eRow, eCol).type());
                else                   
                    takenBlackPieces.add(pieces.pieceAt(
                            eRow, eCol).type());
            }
 
            if(pieces.board[sRow][sCol] instanceof King){
                ((King) pieces.board[sRow][sCol]).moved();
                pieces.move(move);
            }
            else if(pieces.board[sRow][sCol] instanceof King){
                ((Rook) pieces.board[sRow][sCol]).moved();
                pieces.move(move);
            }
            else if(pieces.canExchangePawn(move))
                exchangePawn(move);
            else
                pieces.move(move);
         
            pieces.changePlayer();
 
            // check if the game is over at this point.
            if(pieces.isComplete()){
                turnLabel.setText("CheckMate");
                JOptionPane.showMessageDialog(null, "Checkmate");
                gameOver = true;
            }
 
            chessPiece = null;
            
            // If the computer is playing in this game.
            if(!gameOver && pieces.comp){
                pieces.makeComputerMove();
                pieces.changePlayer();
            }
 
            // displays the new board.
            displayBoard();
 
            // check if the player is in check at this point.
            if(pieces.inCheck(pieces.currentPlayer()))
                turnLabel.setText("Current Player:  " + 
                        pieces.currentPlayer() + " is in check");
            else
                turnLabel.setText("Current Player:  " + 
                        pieces.currentPlayer());
 
            turnLabel.repaint();
        }
 
        //if it wasn't a valid move
        else{
            //have the piece reset to the starting position
            System.out.println("invalid move");
 
            //returns the piece to where it was originally clicked
            replacePiece(); 
        }
    }
 
    /******************************************************************
     * 
     * Helper method places pieces back to the location that it was
     * picked up from.
     * 
     *****************************************************************/
    private void replacePiece(){
 
        Component c2 = chessBoard.findComponentAt(xVal,yVal);
        Container parent = (Container)c2;
        parent.add(chessPiece);
        chessPiece.setBorder(null);
        chessPiece.setVisible(true);            
    }
 
    @Override
    public void mouseEntered(MouseEvent e) {
 
    }
 
    @Override
    public void mouseExited(MouseEvent e) {
    }
 
 
    @Override
    public void mouseMoved(MouseEvent e) {
 
    }
 
    @Override
    public void mouseClicked(MouseEvent e) {
 
    }
 
    /******************************************************************
     * 
     * Private helper method for when the pawn moves into the end of 
     * the board. Allows the user to select a piece that has been
     * taken during the game. 
     * 
     *****************************************************************/
    private void exchangePawn(Move move) {
        if(pieces.currentPlayer() == Player.WHITE){
 
            //creates a drop down box for the choices the user has to
            // exchange pawn.
        	
        	//if the computer is playing, calls the array list
        	if(pieces.comp)
            	takenWhitePieces = pieces.getTakenHumanPieces();

            JComboBox<String> userChoice = 
                    new JComboBox<String>(new Vector<String>(
                            takenWhitePieces));
      
            JOptionPane.showMessageDialog(null,userChoice,
                    "Select a chess piece", 
                    JOptionPane.PLAIN_MESSAGE);
 
            String result = (String) userChoice.getSelectedItem();
            
            // changes pawn type and location
            pieces.promotePawn(result, move);
 
            // removes user choice from array.
            for(int i = 0 ; i < takenWhitePieces.size() ; i++){              
                if(result.equals(takenWhitePieces.get(i))){
                    takenWhitePieces.remove(i); 
                    break;
                }
            }
        }
        
        else{
 
            //creates a drop down box for choices to exchange pawn.
            JComboBox<String> userChoice = 
                    new JComboBox<String>(new Vector<String>(
                            takenBlackPieces));
 
            JOptionPane.showMessageDialog(null,userChoice,
                    "Select a chess piece", 
                    JOptionPane.PLAIN_MESSAGE);
 
            String result = (String) userChoice.getSelectedItem();
 
            // changes the pawn type and location
            pieces.promotePawn(result, move);
 
            // removes the piece that was selected.
            for(int i = 0 ; i < takenBlackPieces.size() ; i++){              
                if(result.equals(takenBlackPieces.get(i))){
                    takenBlackPieces.remove(i);             
                    break;
                }
            }
        } 
       
        this.displayBoard();
    }
 
    /******************************************************************
     * 
     * Private class that handles all the button events
     * 
     *****************************************************************/
    private class ButtonListener implements ActionListener {
 
        public void actionPerformed(ActionEvent e) {
 
            //if the button was pressed
            if(e.getSource() == toggleNamesButton){
 
                //if toggle names is on, it turns it off
                if(toggleChessNames){
                    toggleNamesButton.setText("Turn On Names");
                    toggleChessNames = false;
                }
 
                //if they want to turn on names
                else {
                    toggleNamesButton.setText("Turn Off Names");
                    toggleChessNames = true;
                }
 
            }
 
            else if(e.getSource() == compNewGame){
                pieces.comp = true;
                pieces.newGame(); 
                takenWhitePieces = new ArrayList<String>();
                takenBlackPieces = new ArrayList<String>();
                takenWhitePieces.add("Pawn");
                takenBlackPieces.add("Pawn");
                displayBoard();
                frame.revalidate();               
                turnLabel.setText("Current Player:  " + 
                        pieces.currentPlayer());
                gameOver = false;
            }
 
            else if(e.getSource() == toggleHints){
                if(hints){
                    toggleHints.setText("Turn On Hints");
                    hints = false;
                }
 
                //if they want to turn on names
                else {
                    toggleHints.setText("Turn Off Hints");
                    hints = true;
                }
            }
 
            else if(e.getSource() == newGameItem){
                pieces.comp = false;
                pieces.newGame();  
                takenWhitePieces = new ArrayList<String>();
                takenBlackPieces = new ArrayList<String>();
                takenWhitePieces.add("Pawn");
                takenBlackPieces.add("Pawn");
                displayBoard();
                frame.revalidate();               
                turnLabel.setText("Current Player:  " + 
                        pieces.currentPlayer());
                gameOver = false;
            }
 
            //if the user quits, exits completely
            else if(e.getSource() == quitButton)
                System.exit(0);
 
        }
    }
 
}