package chess;
import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

/**
 * An abstract class to build other chess pieces from.
 * @author andy
 *
 */
public abstract class Piece {
	PieceColor color;
	Point location;
	Boolean alive;
	Set<Point> allMoves;
	String imageLocation;
	String pieceName;
	
	/**
	 * Piece constructor, sets up and initializes all variables.
	 * 
	 * @param color
	 * @param location
	 * @param img
	 */
	public Piece(PieceColor color, Point location, String img){
		this.color = color;
		this.location = location;
		this.alive = true;
		this.imageLocation = img;
		this.allMoves = new HashSet<Point>();
	}
	

	/**
	 * Moves the piece to a new location if possible
	 * 
	 * @param dest
	 * @param chessBoard
	 * @return MoveErrors to represent the success/failure of the move.
	 */
	public ReturnCodes move(Point dest, Board chessBoard) {
		if(!isInBounds(dest))
			return ReturnCodes.INVALID_LOCATION;
	
		findValidMoves(chessBoard);
		
		if(allMoves.contains(dest)){
			if(checkIfKingInCheck(chessBoard, dest) == ReturnCodes.SUCCESS){
				return ReturnCodes.SUCCESS;
			}
			else
				return ReturnCodes.PUTS_KING_IN_CHECK;
		}
		return ReturnCodes.BAD_DESTINATION;
	}

	
	/**
	 * Locates all valid moves the Piece has and stores them in this.allMoves
	 * 
	 * @param chessBoard
	 */
	public abstract void findValidMoves(Board chessBoard);
	
	

	/**
	 * Check to see if a given destination on the board can be moved to.
	 * @param dest
	 * @param chessBoard
	 * @return 1 if it is a valid move to an empty space
	 * 		   0 if it is not a valid move
	 * 		   2 if it is a valid attacking move
	 */
	public ReturnCodes checkBoardLocation(Point dest, Board chessBoard){
		if(!isInBounds(dest))
			return ReturnCodes.INVALID_LOCATION;
		if(chessBoard.getPiece(dest) == null)
			return ReturnCodes.DEST_EMPTY;
		else if(chessBoard.getPiece(dest).getColor() != this.color)
			return ReturnCodes.DEST_ATTACKABLE;
		else 
			return ReturnCodes.BAD_DESTINATION;
	}
	
	/**
	 * Takes in a Point and determines if it is inside the chessboard or not.
	 * @param dest
	 * @return true if it is in bounds, false if it is outside the board
	 */
	public boolean isInBounds(Point dest){
		if (dest.getX() < 0 || dest.getY() < 0 || dest.getX() >= Constants.MAX_BOARD_SIZE 
				|| dest.getY() >= Constants.MAX_BOARD_SIZE)
			return false;
		else
			return true;
	}

	
	/**
	 * A helper function that returns the location of the piece.
	 * 
	 * @param args: None
	 * @return the current location of the piece
	 */
	public Point getLocation(){
		return this.location;
	}
	
	
	/**
	 * Sets a new location for the piece
	 * @param dest
	 */
	public void setLocation(Point dest){
		this.location.x = dest.x;
		this.location.y = dest.y;
	}
	
	
	/**
	 * A helper function that returns the color of the piece.
	 * 
	 * @param args: None
	 * @return the color of the current piece.
	 */
	public PieceColor getColor(){
		return color;
	}
	
	
	/**
	 * Checks if the piece is still alive, or if it has been captured
	 * 
	 * @param args: None
	 * @return whether it is alive or not
	 */
	public boolean isAlive(){
		return this.alive;
	}
	
	
	/**
	 * Kills a piece by setting alive to false.
	 */
	public void killPiece(){
		this.alive = false;
	}
	
	
	/**
	 * Revives a piece by setting alive to true.
	 */
	public void revivePiece(){
		this.alive = true;
	}
	
	
	/**
	 * Checks whether a move will put your king in check.
	 * @param chessBoard
	 * @param dest
	 * @return MoveErrors.SUCCESS if it is a valid move
	 * 		   MoveErrors.PUTS_KING_IN_CHECK otherwise
	 */
	public ReturnCodes checkIfKingInCheck(Board chessBoard, Point dest){
		Point attackerLoc = new Point(location);
		Point defenderLoc = new Point(dest);
		Piece enemyPiece = chessBoard.getPiece(defenderLoc);
		chessBoard.movePiece(this, dest);
		
		if(!chessBoard.isKingInCheck(color)){
			return ReturnCodes.SUCCESS;
		}
		else{
			chessBoard.undoMove(this, enemyPiece, attackerLoc, defenderLoc);
			return ReturnCodes.PUTS_KING_IN_CHECK;
		}
	}
	
	
	/**
	 * A helper function that returns the string containing the image location.
	 * @return imageLocation
	 */
	public String getImgLoc(){
		return this.imageLocation;
	}
	
	
	public String getPieceName(){
		return this.pieceName;
	}
}
