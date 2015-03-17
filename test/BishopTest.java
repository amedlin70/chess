import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import chess.Board;
import chess.ReturnCodes;
import chess.Piece;
import chess.PieceColor;


public class BishopTest {

	private Board chessBoard;
	private Piece bishop, pawn;
	private Point testPoint1, testPoint2;
	
	@Before
	public void setup(){
		testPoint1 = new Point(2,0);
		testPoint2 = new Point(3,1);
		chessBoard = new Board();
		chessBoard.populateBoard();
		bishop = chessBoard.getPiece(testPoint1);
		pawn = chessBoard.getPiece(testPoint2);
	}
	
	@Test
	public void locationTest() {
		assertEquals(testPoint1, bishop.getLocation());
	}
	
	@Test
	public void colorTest() {
		assertEquals(PieceColor.WHITE, bishop.getColor());
	}
	
	@Test
	public void validMove(){
		testPoint1.setLocation(3,3);
		pawn.move(testPoint1, chessBoard);
		testPoint2.setLocation(5,3);
		assertEquals(ReturnCodes.SUCCESS, bishop.move(testPoint2, chessBoard));
	}
	
    @Test
    public void invalidMove(){
    	testPoint1.setLocation(7,7);
    	assertEquals(ReturnCodes.BAD_DESTINATION, bishop.move(testPoint1, chessBoard));
    }
    
    @Test
    public void outOfBoundMoves(){
    	testPoint1.setLocation(8,1);
    	assertEquals(ReturnCodes.INVALID_LOCATION, bishop.move(testPoint1,  chessBoard));
    	testPoint1.setLocation(-1,1);
    	assertEquals(ReturnCodes.INVALID_LOCATION, bishop.move(testPoint1,  chessBoard));
    	testPoint1.setLocation(5,-1);
    	assertEquals(ReturnCodes.INVALID_LOCATION, bishop.move(testPoint1,  chessBoard));
    	testPoint1.setLocation(5,8);
    	assertEquals(ReturnCodes.INVALID_LOCATION, bishop.move(testPoint1,  chessBoard));
    }
       
}
