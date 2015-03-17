package chess;

import java.awt.Point;

/**
 * The Serpent is a custom slider chess piece that combines the bishop with the ability to move 
 * one space in the x or y direction
 * @author andy
 *
 */
public class Serpent extends Piece {
	public Serpent(PieceColor color, Point location, String img){
		super(color, location, img);
		pieceName = "Serpent";
	}
	

	@Override
	public void findValidMoves(Board chessBoard){
		allMoves.clear();
				
		//diagonals
		findSliderMoves(chessBoard, 1, 1);
		findSliderMoves(chessBoard, -1, 1);
		findSliderMoves(chessBoard, 1, -1);
		findSliderMoves(chessBoard, -1, -1);
		
		findMoves(chessBoard, 1, 0);
		findMoves(chessBoard, 0, 1);
		findMoves(chessBoard, -1, 0);
		findMoves(chessBoard, 0, -1);
}
	
	/**
	 * Takes an x and y offset and adds the legal slider moves to allMoves
	 * @param chessBoard
	 * @param xOffset
	 * @param yOffset
	 */
	public void findSliderMoves(Board chessBoard, int xOffset, int yOffset){
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
	
	/**
	 * Checks for valid moves given a movement offset and adds them to the Set allMoves
	 * @param chessBoard
	 * @param xOffset
	 * @param yOffset
	 */
	private void findMoves(Board chessBoard, int xOffset, int yOffset){
		Point testPoint = new Point(location.x + xOffset, location.y + yOffset);
		ReturnCodes boardCheck = checkBoardLocation(testPoint, chessBoard);
		if(boardCheck == ReturnCodes.DEST_EMPTY || boardCheck == ReturnCodes.DEST_ATTACKABLE){
			allMoves.add(new Point(testPoint));
		}
	}
}
