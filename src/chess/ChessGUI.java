package chess;

import javax.swing.JFrame;

public class ChessGUI {

	/******************************************************************
	 * Main function that constructs the JFrame which starts and 
	 * maintains the game
	 *****************************************************************/
	public static void main (String[] args)
	{
		JFrame frame = new JFrame ("Chess");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		ChessPanel panel = new ChessPanel(frame);
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
	}
}