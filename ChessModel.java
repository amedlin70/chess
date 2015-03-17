package chess;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Interprets the events and pushes the changes out to ChessView which redraws the game board
 */

public class ChessModel {
	private Board chessBoard;	
	private ChessView view;
	private Piece piece = null;
	private Point location = new Point(0,0);
	private PieceColor color = PieceColor.WHITE;
	private PieceColor playerColor = null;
	private int player1 = 0;
	private int player2 = 0;
	private Stack<ChessMove> moveStack = new Stack<ChessMove>();
	String message;
	JFrame frame;
	
	boolean server = false;
	boolean client = false;
	ChessServer cServer;
	ChessClient cClient;
	
	public ChessModel(Board b, ChessView v){
		chessBoard = b;
		view = v;
	}
	
	
	/**
	 * Allows a player to forfeit the game and start over again.  The player who forfeits loses the match, and 
	 * the opposing player gains a point.
	 */
	public void forfeit(){
		//increment the score only if the current player is not in checkmate
		if(!(chessBoard.isKingInCheckmate(color))){
			incrementScore();
			if(color == PieceColor.WHITE){
				view.updateMessageArea("\nWhite forfeited the game.\n");
			}
			else{
				view.updateMessageArea("\nBlack forfeited the game.\n");
			}
		}
		else{
			view.updateMessageArea("\nStarted new game.\n");
		}
		
		//reset the board
		chessBoard.clearBoard();
		chessBoard.populateBoard();
		
		//reset the game back to player 1
		moveStack.clear();
		color = PieceColor.WHITE;
		
		//redraw the chessBoard
		view.drawPieces();
		view.setPlayer("<html><FONT COLOR=ORANGE>Player 1s Turn");
		view.repaint();
	}
	
	
	/**
	 * Undoes a move and changes player back to what it was before the move was performed
	 */
	public void undo(){
		if(!moveStack.empty()){
			//set piece to null to avoid faulty movements
			if(piece != null){
				view.removeHightlightSquare(piece.getLocation().x, piece.getLocation().y);
				piece = null;
			}
			
			//pop a move off of the moveStack, and calls chessBoard.undoMove()
			ChessMove move = moveStack.pop();
			chessBoard.undoMove(move.getAttacker(), move.getDefender(),
					move.getPrevLocation(), move.getDestination());
			view.updateMessageArea("Undid a move\n");
			changeColor();
			
			//redraw the square the attacker came from
			view.setSquareImage(move.getPrevLocation().x, move.getPrevLocation().y, move.getAttacker().getImgLoc());
			
			//redraw the square the defender was on
			if(move.getDefender() != null){
				view.setSquareImage(move.getDestination().x, move.getDestination().y, move.getDefender().getImgLoc());
			}
			else{
				view.setSquareImage(move.getDestination().x, move.getDestination().y, null);
			}
			view.repaint();
		}
		else{
			updateMessageArea("Nothing to undo\n");
		}
	}
	
	
	/**
	 * Appends a new message to the MessageArea
	 * @param msg
	 */
	public void updateMessageArea(String msg){
		view.updateMessageArea(msg);
	}
	
	
	/**
	 * Handles the MouseEvent that is triggered when a player clicks on the chessBoard
	 * @param point
	 */
	public void handleBoardEvent(Point point) {
		//format the point
		Point dest = new Point(point.x / 64, ((64 * 8) - point.y) / 64);
		getPiece(dest);
		move(dest);
	}
	
	
	/**
	 * Handles messages recieved from the socket
	 * @param msg
	 */
	public void handleSocketMessage(String msg){
		//move
		if(msg.startsWith("MOVE ")){
			int loc = Integer.parseInt(msg.substring(5));
			int x1, x2, y1, y2;
			y2 = loc % 10;
			x2 = (loc / 10) % 10;
			y1 = (loc / 100) % 10;
			x1 = (loc / 1000) % 10;
			
			performMove(new Point(x1,y1), new Point(x2,y2));
		}
		//undo
		else if(msg.startsWith("UNDO")){
			undo();
		}
		//new game
		else if(msg.startsWith("NEWGAME")){
			newGame();
		}
		//new special game
		else if(msg.startsWith("NEWSPECIAL")){
			specialBoard();
		}
		//forfeit
		else if(msg.startsWith("FORFEIT")){
			forfeit();
		}
	}
	
	
	/**
	 * Locates the piece associated with the destination and sets it to this.piece.
	 * @param dest
	 */
	public void getPiece(Point dest){
		//attempt to set this.piece with the piece cooresponding to the chessBoard location
		if(piece == null){
			piece = chessBoard.getPiece(dest);
			//if playing a network game, make sure only client or server can move
			if(server == true || client == true){
				if(color == playerColor && piece != null && piece.getColor() == color){
					location.setLocation(dest);
					view.highlightSquare(dest.x, dest.y);
				}
				else{
					piece = null;
				}
			}
			else if(piece != null && piece.getColor() == color){
				location.setLocation(dest);
				view.highlightSquare(dest.x, dest.y);
			}
			else{
				piece = null;
			}
		}
		//if the same piece was clicked again, remove the highlight from the square and set piece to null
		else if(dest.x == location.x && dest.y == location.y){
			piece = null;
			view.removeHightlightSquare(dest.x, dest.y);
		}
	}
	
	
	/**
	 * Performs a move and sends the move through the socket
	 * @param dest
	 */
	private void move(Point dest){
		String msg;
		if(piece != null && !(location.x == dest.x && location.y == dest.y)){
			msg = performMove(piece.getLocation(), dest);
			if(msg != null){
				sendMessage(msg);
			}
		}
	}


	/**
	 * Perform a move and return a MOVE command for the networking communication
	 * @param start
	 * @param dest
	 * @return message to send over the socket
	 */
	private String performMove(Point start, Point dest) {
		String msg = null;
		piece = chessBoard.getPiece(start);
		location.setLocation(start);
		Point defenderLoc = dest;
		Piece defender = chessBoard.getPiece(dest);
		
		//get defender information before piece.move is called, so we can store it for the undo function
		if(piece.move(dest, chessBoard) == ReturnCodes.SUCCESS){
			//Add move to messagePane
			msg = processMove(defender, defenderLoc);
			
			//redraw the tiles on the board that have changed
			view.setSquareImage(dest.x, dest.y, piece.getImgLoc());
			view.setSquareImage(location.x, location.y, null);
			view.removeHightlightSquare(location.x, location.y);
			piece = null;
			
			view.drawPieces();
			view.repaint();
			
			//switch players
			changeColor();
			
			//check if last move put opposing player in check or checkmate
			if(chessBoard.isKingInCheck(color)){
				if(chessBoard.isKingInCheckmate(color)){
					view.updateMessageArea("Checkmate\n");
					incrementScore();
					JOptionPane.showMessageDialog(frame, "Checkmate");
				}
				else{
					view.updateMessageArea("Check\n");
				}
			} 
		}
		else{
			view.updateMessageArea("Invalid move\n");
		}
		return msg;
	}


	/**
	 * Creates a ChessMove object and pushes it onto the moveStack.  Also updates the messageArea with the move.
	 * @param defender
	 * @param defenderLoc
	 * @return 
	 */
	private String processMove(Piece defender, Point defenderLoc) {
		String curColor;
		if(color == PieceColor.BLACK)
			curColor = "Black";
		else
			curColor = "White";
		
		//add the move to the moveStack for the undo function to work
		moveStack.push(new ChessMove(location, defenderLoc, piece, defender));
		
		//generate the text form of the move to be displayed in the messageArea
		if(defender == null){
			message = curColor + " " + piece.getPieceName() + "(" + location.x + "," + location.y + 
					") to (" + defenderLoc.x + "," + defenderLoc.y + ")\n";
		}
		else
			message = curColor + " " + piece.getPieceName() + "(" + location.x + "," + location.y + 
					") captures " + defender.getPieceName() + "(" + defenderLoc.x + "," + defenderLoc.y + ")\n";
		view.updateMessageArea(message);
		return "MOVE " + location.x + location.y + defenderLoc.x + defenderLoc.y;
	}


	/**
	 * Sends a message through the server or clients socket, depending on which one the application is currently running as
	 * @param msg
	 */
	public void sendMessage(String msg) {
		if(server == true){
			cServer.sendMessage(msg);
		}
		else if(client == true){
			cClient.sendMessage(msg);
		}
	}


	/**
	 * Increments the score based on who won and updates the view
	 */
	private void incrementScore() {
		if(color == PieceColor.WHITE){
			player2++;
		}
		else{
			player1++;
		}		
		view.updateScore(player1, player2);
	}

	
	
	/**
	 * Changes the current player and updates the view
	 */
	private void changeColor(){
		if(color == PieceColor.WHITE){
			color = PieceColor.BLACK;
			view.setPlayer("<html><FONT COLOR=BLUE>Player 2s Turn");
		}
		else{
			color = PieceColor.WHITE;
			view.setPlayer("<html><FONT COLOR=ORANGE>Player 1s Turn");
		}
	}



	/**
	 * Will start a new game using the special pieces.  The Knight is replaced with the Old Woman
	 * and the Bishop is replaced with the Serpent.  
	 */
	public void specialBoard() {
		//reset the board
		chessBoard.clearBoard();
		chessBoard.populateSpecialBoard();
		moveStack.clear();
		
		//set player back to player 1
		color = PieceColor.WHITE;
		view.setPlayer("<html><FONT COLOR=ORANGE>Player 1s Turn");
		
		//redraw the chessBoard
		view.drawPieces();
		view.updateMessageArea("\nStarted custom game.\n");
		view.repaint();
	}

	/**
	 * Creates a new game and reinitializes the board
	 */
	public void newGame() {
		//reset the board
		chessBoard.clearBoard();
		chessBoard.populateBoard();
		moveStack.clear();
		
		//set player back to player 1
		color = PieceColor.WHITE;
		view.setPlayer("<html><FONT COLOR=ORANGE>Player 1s Turn");
		
		//redraw the chessBoard
		view.drawPieces();
		view.updateMessageArea("\nStarted new game.\n");
		view.repaint();
	}
	
	
	/**
	 * Configures a client and connects to a server that is already running.
	 */
	protected void createClient() {
		if(server == false && client == false){
			client = true;
			cClient = new ChessClient(this);
			(new Thread(cClient)).start();
			playerColor = PieceColor.BLACK;
			newGame();
		}
		else{
			view.updateMessageArea("\nCould not connect\n");
		}
	}


	/**
	 * Creates a server for a client to connect to
	 */
	protected void createServer() {
		if(server == false && client == false){
			server = true;
			cServer = new ChessServer(this);
			(new Thread(cServer)).start();
			playerColor = PieceColor.WHITE;
			newGame();
		}
		else{
			view.updateMessageArea("\nCould not host new game\n");
		}	
	}
	
	
	//
	public void testServer(ChessServer mockServer){
		server = true;
		cServer = mockServer;
	}
	
	public void testClient(ChessClient mockClient){
		client = true;
		cClient = mockClient;
	}
}