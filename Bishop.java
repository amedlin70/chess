package chess;

import java.awt.Point;

/**
 * The Bishop is a classic slider chess piece that moves on the diagonals
 * @author andy
 *
 */
public class Bishop extends Piece {
	public Bishop(PieceColor color, Point location, String img){
		super(color, location, img);
		pieceName = "Bishop";
	}

	@Override
	public void findValidMoves(Board chessBoard){
		allMoves.clear();
		
		//diagonals
		findMoves(chessBoard, 1, 1);
		findMoves(chessBoard, -1, 1);
		findMoves(chessBoard, 1, -1);
		findMoves(chessBoard, -1, -1);
}
	
	/**
	 * Takes an x and y offset and adds the legal moves to allMoves
	 * @param chessBoard
	 * @param xOffset
	 * @param yOffset
	 */
	public void findMoves(Board chessBoard, int xOffset, int yOffset){
		Point testPoint = new Point(location.x + xOffset, location.y + yOffset);
		int x = xOffset, y = yOffset;
		while(checkBoardLocation(testPoint, chessBoard) == ReturnCodes.DEST_EMPTY){
			allMoves.add(new Point(testPoint));
			x += xOffset;
			y += yOffset;
			testPoint.setLocation(location.x + x, location.y + y);
		}
		if(checkBoardLocation(testPoint, chessBoard) == ReturnCodes.DEST_ATTACKABLE)
			allMoves.add(new Point(testPoint));
	}
}
