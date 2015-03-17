package chess;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

public class Board {
	Piece[][] chessBoard;
	Set<Piece> blackPieces, whitePieces;
	Piece whiteKing, blackKing;
	
	/**
	 * The chess board.  A grid that keeps track of where pieces currently are.
	 * Board is set up as chessBoard[x][y]
	 * @param args: None
	 */
	public Board(){
		chessBoard = new Piece[Constants.MAX_BOARD_SIZE][Constants.MAX_BOARD_SIZE];
		blackPieces = new HashSet<Piece>();
		whitePieces = new HashSet<Piece>();
		int row, col;
		for(row = 0; row < Constants.MAX_BOARD_SIZE; row++)
			for(col = 0; col < Constants.MAX_BOARD_SIZE; col++)
				chessBoard[row][col] = null;
	}
	
	
	/**
	 * Creates all of the pieces and places them on the board, and stores the pieces into a hashset based on their color.
	 */
	public void populateBoard(){
		int col;
		for(col = 0; col < Constants.MAX_BOARD_SIZE ;col++){
			chessBoard[col][6] = new Pawn(PieceColor.BLACK, new Point(col, 6), "images/BlackPawn.png");
			chessBoard[col][1] = new Pawn(PieceColor.WHITE, new Point(col, 1), "images/WhitePawn.png");
			blackPieces.add(chessBoard[col][6]);
			whitePieces.add(chessBoard[col][1]);
		}
	
		createBackRow(PieceColor.BLACK);
		createBackRow(PieceColor.WHITE);
		
		whiteKing = chessBoard[4][0];
		blackKing = chessBoard[4][7];
	}
	

	/**
	 * Part of the board initialization, fills up the back row depending on the piece color
	 * @param color
	 */
	public void createBackRow(PieceColor color){
		int row;
		Set<Piece> curSet;
		String img;
		if (color == PieceColor.BLACK){
			img = "images/Black";
			row = 7;
			curSet = blackPieces;
		}
		else{
			img = "images/White";
			row = 0;
			curSet = whitePieces;
		}
		chessBoard[0][row] = new Rook(color, new Point(0, row), img + "Rook.png");
		chessBoard[1][row] = new Knight(color, new Point(1, row), img + "Knight.png");
		chessBoard[2][row] = new Bishop(color, new Point(2, row), img + "Bishop.png");
		chessBoard[3][row] = new Queen(color, new Point(3, row), img + "Queen.png");
		chessBoard[4][row] = new King(color, new Point(4, row), img + "King.png");
		chessBoard[5][row] = new Bishop(color, new Point(5, row), img + "Bishop.png");
		chessBoard[6][row] = new Knight(color, new Point(6, row), img + "Knight.png");
		chessBoard[7][row] = new Rook(color, new Point(7, row), img + "Rook.png");
		for(int i = 0; i < Constants.MAX_BOARD_SIZE; i++){
			curSet.add(chessBoard[i][row]);
		}
	}
	
	
	/**
	 * Creates all of the pieces and places them on the board, and stores the pieces into a hashset based on their color.
	 */
	public void populateSpecialBoard(){
		int col;
		for(col = 0; col < Constants.MAX_BOARD_SIZE ;col++){
			chessBoard[col][6] = new Pawn(PieceColor.BLACK, new Point(col, 6), "images/BlackPawn.png");
			chessBoard[col][1] = new Pawn(PieceColor.WHITE, new Point(col, 1), "images/WhitePawn.png");
			blackPieces.add(chessBoard[col][6]);
			whitePieces.add(chessBoard[col][1]);
		}
	
		createSpecialBackRow(PieceColor.BLACK);
		createSpecialBackRow(PieceColor.WHITE);
		
		whiteKing = chessBoard[4][0];
		blackKing = chessBoard[4][7];
	}
	

	/**
	 * Part of the board initialization, fills up the back row depending on the piece color
	 * @param color
	 */
	public void createSpecialBackRow(PieceColor color){
		int row;
		Set<Piece> curSet;
		String img;
		if (color == PieceColor.BLACK){
			img = "images/Black";
			row = 7;
			curSet = blackPieces;
		}
		else{
			img = "images/White";
			row = 0;
			curSet = whitePieces;
		}
		chessBoard[0][row] = new Rook(color, new Point(0, row), img + "Rook.png");
		chessBoard[1][row] = new OldWoman(color, new Point(1, row), img + "OldWoman.png");
		chessBoard[2][row] = new Serpent(color, new Point(2, row), img + "Serpent.png");
		chessBoard[3][row] = new Queen(color, new Point(3, row), img + "Queen.png");
		chessBoard[4][row] = new King(color, new Point(4, row), img + "King.png");
		chessBoard[5][row] = new Serpent(color, new Point(5, row), img + "Serpent.png");
		chessBoard[6][row] = new OldWoman(color, new Point(6, row), img + "OldWoman.png");
		chessBoard[7][row] = new Rook(color, new Point(7, row), img + "Rook.png");
		for(int i = 0; i < Constants.MAX_BOARD_SIZE; i++){
			curSet.add(chessBoard[i][row]);
		}
	}
	
	
	/**
	 * Clears the board in order to start a new game.  Sets every board location to null and clears
	 * the Sets whitePieces and blackPieces.
	 */
	public void clearBoard(){
		whitePieces.clear();
		blackPieces.clear();
		for(int col = 0; col < Constants.MAX_BOARD_SIZE; col++){
			for(int row = 0; row < Constants.MAX_BOARD_SIZE; row++){
				chessBoard[col][row] = null;
			}
		}
	}
	
	/**
	 * Moves a piece on the chess board.  
	 * @param currPiece, dest
	 * @return 1 if successful, 0 if error
	 */
	public int movePiece(Piece currPiece, Point dest){
		Point currPos = new Point(currPiece.getLocation());
		
		if(chessBoard[currPos.x][currPos.y] == currPiece){
			if(chessBoard[dest.x][dest.y] == null){
				chessBoard[currPos.x][currPos.y] = null;
				chessBoard[dest.x][dest.y] = currPiece;
				currPiece.setLocation(dest);
				return 1;
			}
			else if(chessBoard[dest.x][dest.y].getColor() != currPiece.getColor()){
				Piece defender = chessBoard[dest.x][dest.y];
				chessBoard[currPos.x][currPos.y]= null; 
				currPiece.setLocation(dest);
				removePiece(defender);
				chessBoard[dest.x][dest.y] = currPiece;
				return 1;
			}
		}
		return 0;
	}

	/**
	 * Removes a piece from the board
	 * @param currPiece
	 */
	public void removePiece(Piece curPiece){
		Point currPos = curPiece.getLocation();
		Set<Piece> curSet;
		if(curPiece.getColor() == PieceColor.WHITE)
			curSet = whitePieces;
		else
			curSet = blackPieces;
		curPiece.killPiece();
		curSet.remove(curPiece);
		chessBoard[currPos.x][currPos.y] = null;
		
	}
	
	/**
	 * adds a piece to the board
	 * @param currPiece
	 * @param dest
	 */
	public void addPiece(Piece currPiece, Point dest){
		Set<Piece> curSet;
		if(currPiece.getColor() == PieceColor.WHITE)
			curSet = whitePieces;
		else
			curSet = blackPieces;
		curSet.add(currPiece);
		chessBoard[dest.x][dest.y] = currPiece;
		currPiece.setLocation(dest);
		currPiece.revivePiece();
	}
	
	/**
	 * Return the piece at the current location
	 * @param p
	 * @return
	 */
	public Piece getPiece(Point p){
		return chessBoard[p.x][p.y];
	}
	
//	/**
//	 * Returns whether or not the given location is occupied
//	 * @param location
//	 * @return true if location is occupied, false otherwise
//	 */
//	public boolean isOccupied(Point location){
//		if(chessBoard[location.x][location.y] == null)
//			return false;
//		else
//			return true;
//	}
	
	/**
	 * Returns whether or not the king is in check
	 * @param color
	 * @return true if king is in check, false otherwise
	 */
	public boolean isKingInCheck(PieceColor color){
		Set<Piece> curSet;
		Piece curKing;
		if(color == PieceColor.WHITE){
			curSet = blackPieces;
			curKing = whiteKing;
		}
		else{
			curSet = whitePieces;
			curKing = blackKing;
		}
		
		//iterate through all pieces in curSet, checking if the location of the king is contained in allMoves 
		for(Piece piece : curSet){
			piece.findValidMoves(this);
			if(piece.allMoves.contains(curKing.getLocation())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Given a piece color, checks if that king is in checkmate.
	 * @param color
	 * @return
	 */
	public boolean isKingInCheckmate(PieceColor color){
		Set<Piece> curSet;
		Point attackerLoc = new Point(), defenderLoc = new Point();
		Piece defender;
		boolean inCheck;
		ReturnCodes error;
		Set<Point> curMoves = new HashSet<Point>(); 
		
		if(color == PieceColor.BLACK)
			curSet = blackPieces;
		else
			curSet = whitePieces;
		
		for(Piece piece : curSet){		//iterate through all pieces, to check if they can avoid a checkmate
			piece.findValidMoves(this);
			
			//copy contents of piece.allMoves to curMoves, since calling piece.move() overwrites piece.allMoves
			curMoves.clear();
			curMoves.addAll(piece.allMoves);
			
			//iterate through all moves a piece has in order to find a move that avoids checkmate
			for(Point point : curMoves){	
				attackerLoc.setLocation(piece.getLocation());
				defender = this.getPiece(point);
				defenderLoc.setLocation(point);
				error = piece.move(point, this);
				
				if(error == ReturnCodes.SUCCESS){		
					inCheck = isKingInCheck(color);
					this.undoMove(piece, defender, attackerLoc, point);
					if(!inCheck)
						return false;
				}
			}
		}
		return true;
	}

	/**
	 * Undoes a move by using the attacker, defender, previous location, and current 
	 * location to reset the pieces to where they were.
	 * @param attacker
	 * @param enemy
	 * @param prevLocation
	 * @param dest
	 */
	public void undoMove(Piece attacker, Piece defender, Point prevAttackerPos, Point prevDefenderPos) {
		this.movePiece(attacker, prevAttackerPos);
		if(defender != null){
			this.addPiece(defender, prevDefenderPos);
		}
	}
}
