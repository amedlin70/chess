package chess;

import java.awt.Point;

/**
 * A classic jumper chess piece.  The knight's valid move pattern is (2,1)
 * @author andy
 *
 */
public class Knight extends Piece {
	public Knight(PieceColor color, Point location, String img){
		super(color, location, img);
		pieceName = "Knight";
	}

	
	@Override
	public void findValidMoves(Board chessBoard){
		allMoves.clear();
		
		findMoves(chessBoard, 1, 2);
		findMoves(chessBoard, -1, 2);
		findMoves(chessBoard, 1, -2);
		findMoves(chessBoard, -1, -2);
		
		findMoves(chessBoard, 2, 1);
		findMoves(chessBoard, 2, -1);
		findMoves(chessBoard, -2, 1);
		findMoves(chessBoard, -2, -1);
	}
	
	
	/**
	 * Takes an x and y offset and adds the legal moves to allMoves
	 * @param chessBoard
	 * @param xOffset
	 * @param yOffset
	 */
	//updateMoves
	public void findMoves(Board chessBoard, int xOffset, int yOffset){
		Point testPoint = new Point(location.x + xOffset, location.y + yOffset);
		ReturnCodes boardCheck = checkBoardLocation(testPoint, chessBoard);
		if(boardCheck == ReturnCodes.DEST_EMPTY || boardCheck == ReturnCodes.DEST_ATTACKABLE){
			allMoves.add(new Point(testPoint));
		}
	}
}
