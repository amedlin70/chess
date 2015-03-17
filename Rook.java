package chess;

import java.awt.Point;

public class Rook extends Piece {

	public Rook(PieceColor color, Point location, String img){
		super(color, location, img);
		pieceName = "Rook";
	}
	

	@Override
	public void findValidMoves(Board chessBoard){
		allMoves.clear();

		//straight lines
		findMoves(chessBoard, 1, 0);
		findMoves(chessBoard, 0, 1);
		findMoves(chessBoard, -1, 0);
		findMoves(chessBoard, 0, -1);
	}
	
	
	public void findMoves(Board chessBoard, int xOffset, int yOffset){
		Point testPoint = new Point(location.x + xOffset, location.y + yOffset);
		int x = xOffset, y = yOffset;
		while(checkBoardLocation(testPoint, chessBoard) == ReturnCodes.DEST_EMPTY){
			allMoves.add(new Point(testPoint));
			x += xOffset;
			y += yOffset;
			testPoint.setLocation(location.x + x, location.y + y);
		}
		if(checkBoardLocation(testPoint, chessBoard) == ReturnCodes.DEST_ATTACKABLE){
			allMoves.add(new Point(testPoint));
		}
	}
}
