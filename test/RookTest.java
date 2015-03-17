import static org.junit.Assert.*;

import java.awt.Point;

import chess.Board;
import chess.Piece;
import chess.PieceColor;
import chess.ReturnCodes;

import org.junit.Test;
import org.junit.Before;


public class RookTest {
	
	private Board chessBoard;
	private Piece rook, pawn;
	private Point testPoint1, testPoint2;
	
	@Before
	public void setup(){
		testPoint1 = new Point(0,0);
		testPoint2 = new Point(0,1);
		chessBoard = new Board();
		chessBoard.populateBoard();
		rook = chessBoard.getPiece(testPoint1);
		pawn = chessBoard.getPiece(testPoint2);
	}
	
	@Test
	public void locationTest() {
		assertEquals(testPoint1, rook.getLocation());
	}
	
	@Test
	public void colorTest() {
		assertEquals(PieceColor.WHITE, rook.getColor());
	}

    @Test
    public void validMove() {
    	testPoint1.setLocation(7, 2);
    	chessBoard.movePiece(pawn, testPoint1);
    	testPoint2.setLocation(0,5);
        assertEquals(ReturnCodes.SUCCESS, rook.move(testPoint2, chessBoard));
        assertEquals(testPoint2, rook.getLocation());
    }
    
    @Test
    public void invalidMove(){
    	testPoint1.setLocation(7,1);
    	assertEquals(ReturnCodes.BAD_DESTINATION, rook.move(testPoint1, chessBoard));
    }
    
    @Test
    public void outOfBoundMoves(){
    	testPoint2.setLocation(8,1);
    	assertEquals(ReturnCodes.INVALID_LOCATION, rook.move(testPoint2,  chessBoard));
    	testPoint2.setLocation(-1,1);
    	assertEquals(ReturnCodes.INVALID_LOCATION, rook.move(testPoint2,  chessBoard));
    	testPoint2.setLocation(5,-1);
    	assertEquals(ReturnCodes.INVALID_LOCATION, rook.move(testPoint2,  chessBoard));
    	testPoint2.setLocation(5,8);
    	assertEquals(ReturnCodes.INVALID_LOCATION, rook.move(testPoint2,  chessBoard));
    	assertEquals(testPoint1, rook.getLocation());
    }
       
    @Test
    public void captured(){
    	testPoint1.setLocation(7, 2);
    	chessBoard.movePiece(pawn, testPoint1);
    	testPoint2.setLocation(0,6);
        assertEquals(ReturnCodes.SUCCESS, rook.move(testPoint2, chessBoard));
        assertEquals(testPoint2, rook.getLocation());
    }
}
