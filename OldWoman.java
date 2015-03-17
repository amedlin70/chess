package chess;

import java.awt.Point;

/**
 * A custom jumper class that moves in a (2,2) grid
 * @author andy
 *
 */
public class OldWoman extends Piece {
	public OldWoman(PieceColor color, Point location, String img){
		super(color, location, img);
		pieceName = "Old Woman";
	}
	

	@Override
	public void findValidMoves(Board chessBoard){
		allMoves.clear();
		
		findMoves(chessBoard, 2, 2);
		findMoves(chessBoard, -2, 2);
		findMoves(chessBoard, 2, -2);
		findMoves(chessBoard, -2, -2);
	}
	
	/**
	 * Takes an x and y offset and adds the legal moves to allMoves
	 * @param chessBoard
	 * @param xOffset
	 * @param yOffset
	 */
	public void findMoves(Board chessBoard, int xOffset, int yOffset){
		Point testPoint = new Point(location.x + xOffset, location.y + yOffset);
		ReturnCodes boardCheck = checkBoardLocation(testPoint, chessBoard);
		if(boardCheck == ReturnCodes.DEST_EMPTY || boardCheck == ReturnCodes.DEST_ATTACKABLE){
			allMoves.add(new Point(testPoint));
		}
	}
}
