import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import chess.Board;
import chess.ReturnCodes;
import chess.Piece;
import chess.PieceColor;


public class KingTest {

	private Board chessBoard;
	private Piece king;
	private Piece pawn;
	private Point testPoint1;
	private Point testPoint2;
	
	@Before
	public void setup(){
		testPoint1 = new Point(4,7);
		testPoint2 = new Point(3,6);
		chessBoard = new Board();
		chessBoard.populateBoard();
		king = chessBoard.getPiece(testPoint1);
		pawn = chessBoard.getPiece(testPoint2);
	}
	
	@Test
	public void locationTest() {
		assertEquals(testPoint1, king.getLocation());
	}
	
	@Test
	public void colorTest() {
		assertEquals(PieceColor.BLACK, king.getColor());
	}
	
    @Test
    public void validMove() {
    	testPoint1.setLocation(3, 5);
    	chessBoard.movePiece(pawn, testPoint1);
    	testPoint2.setLocation(3,6);
        assertEquals(ReturnCodes.SUCCESS, king.move(testPoint2, chessBoard));
        assertEquals(testPoint2, king.getLocation());
    }
    
    @Test
    public void invalidMove(){
    	testPoint1.setLocation(7,1);
    	assertEquals(ReturnCodes.BAD_DESTINATION, king.move(testPoint1, chessBoard));
    }
    
    @Test
    public void outOfBoundMoves(){
    	testPoint2.setLocation(8,1);
    	assertEquals(ReturnCodes.INVALID_LOCATION, king.move(testPoint2,  chessBoard));
    	testPoint2.setLocation(-1,1);
    	assertEquals(ReturnCodes.INVALID_LOCATION, king.move(testPoint2,  chessBoard));
    	testPoint2.setLocation(5,-1);
    	assertEquals(ReturnCodes.INVALID_LOCATION, king.move(testPoint2,  chessBoard));
    	testPoint2.setLocation(5,8);
    	assertEquals(ReturnCodes.INVALID_LOCATION, king.move(testPoint2,  chessBoard));
    	assertEquals(testPoint1, king.getLocation());
    }
}
