package chess;

import javax.swing.JFrame;

/**
 * This is the main game loop for the chess application.  
 */
public class Chess {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Board chessBoard = new Board();
		chessBoard.populateBoard();
			
		drawGame(chessBoard);
		
		while(true){
		}
	}
	
	/**
	 * Calls the ChessController and configures the JFrame
	 * @param chessBoard
	 */
	private static void drawGame(Board chessBoard){
		ChessController mainFrame = new ChessController(chessBoard);
		mainFrame.pack();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
	}

}
