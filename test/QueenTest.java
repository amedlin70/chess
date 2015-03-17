import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import chess.Board;
import chess.ReturnCodes;
import chess.Piece;
import chess.PieceColor;


public class QueenTest {

	private Board chessBoard;
	private Piece queen;
	private Piece pawn;
	private Point testPoint;
	private Point testPoint2;
	
	@Before
	public void setup(){
		testPoint = new Point(3,0);
		testPoint2 = new Point(4,1);
		chessBoard = new Board();
		chessBoard.populateBoard();
		queen = chessBoard.getPiece(testPoint);
		pawn = chessBoard.getPiece(testPoint2);
	}
	
	@Test
	public void locationTest() {
		assertEquals(testPoint, queen.getLocation());
	}
	
	@Test
	public void colorTest() {
		assertEquals(PieceColor.WHITE, queen.getColor());
	}

    @Test
    public void validStartMoveWhite() {
    	testPoint.setLocation(5, 2);
    	testPoint2.setLocation(4,2);
    	pawn.move(testPoint2, chessBoard);
        assertEquals(ReturnCodes.SUCCESS, queen.move(testPoint, chessBoard));
        assertEquals(testPoint, queen.getLocation());
    }
}
