package chess;

import java.awt.Point;

/**
 * A classic chess piece with with a valid pattern (1,0) or (1,1)
 * @author andy
 *
 */
public class King extends Piece {
	public King(PieceColor color, Point location, String img){
		super(color, location, img);
		pieceName = "King";
	}

	@Override
	public void findValidMoves(Board chessBoard){
		allMoves.clear();
		
		//diagonals
		findMoves(chessBoard, 1, 1);
		findMoves(chessBoard, -1, 1);
		findMoves(chessBoard, 1, -1);
		findMoves(chessBoard, -1, -1);
		
		//straight lines
		findMoves(chessBoard, 1, 0);
		findMoves(chessBoard, 0, 1);
		findMoves(chessBoard, -1, 0);
		findMoves(chessBoard, 0, -1);
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
