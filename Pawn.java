package chess;

import java.awt.Point;

/**
 * A classic chess piece that can moves 1 space in the y direction, can start
 * with a move 2 spaces in the y direction, and attackes in a (1,1) direction
 * @author andy
 *
 */
public class Pawn extends Piece{

	public Pawn(PieceColor color, Point location, String img){
		super(color, location, img);
		pieceName = "Pawn";
	}
	
	@Override
	/**
	 * Finds all valid moves tha Pawn can make ands adds them to the allMoves HashSet.
	 */
	public void findValidMoves(Board chessBoard){
		allMoves.clear();
		
		int yOffset;
		int startingYCoord;
		
		if(color == PieceColor.BLACK){
			startingYCoord = 6;
			yOffset = -1;
		}
		else{
			startingYCoord = 1;
			yOffset = 1;
		}
		
		checkStartMove(startingYCoord, yOffset, chessBoard);
		checkForwardMove(yOffset, chessBoard);
		checkAttackMove(1, yOffset, chessBoard);
		checkAttackMove(-1, yOffset, chessBoard);
	}

	
	/**
	 * Checks if their is an enemy to capture in the diagonal, if there is it adds that location to allMoves.
	 * @param xOffset
	 * @param yOffset
	 * @param chessBoard
	 */
	private void checkAttackMove(int xOffset, int yOffset, Board chessBoard) {
		Point dest = new Point(location.x+xOffset,location.y+yOffset);
		Piece destPiece;
		if(isInBounds(dest)){
			destPiece = chessBoard.getPiece(dest);
			
			//check if there is an enemy at dest
			if(destPiece != null && destPiece.getColor() != this.color){
				this.allMoves.add(dest);
			}
		}
	}

	
	/**
	 * Checks if the square in front of the pawn is clear to move to, if it is that move is added to allMoves.
	 * @param yOffset
	 * @param chessBoard
	 */
	private void checkForwardMove(int yOffset, Board chessBoard) {
		Point dest = new Point(location.x,location.y+yOffset);
		if(checkBoardLocation(dest, chessBoard) == ReturnCodes.DEST_EMPTY){
				this.allMoves.add(dest);
		}
	}

	
	/**
	 * If pawn is still in it's starting location and the path in front of it is clear, that move is added to allMoves.
	 * @param startingYCoord
	 * @param yOffset
	 * @param chessBoard
	 */
	private void checkStartMove(int startingYCoord, int yOffset, Board chessBoard) {
		Point dest = new Point();
		if(location.y == startingYCoord){
			dest.setLocation( location.x, location.y + yOffset );
			if(checkBoardLocation(dest, chessBoard) == ReturnCodes.DEST_EMPTY){
				dest.setLocation( location.x, location.y + (2 * yOffset) );
				if(checkBoardLocation(dest, chessBoard) == ReturnCodes.DEST_EMPTY){
					this.allMoves.add(new Point(dest));
				}
			}
		}
	}
}
