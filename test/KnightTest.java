import static org.junit.Assert.*;

import java.awt.Point;

import chess.Board;
import chess.Piece;
import chess.PieceColor;
import chess.ReturnCodes;

import org.junit.Test;
import org.junit.Before;


public class KnightTest {
	
	private Board chessBoard;
	private Piece knight, pawn;
	private Point testPoint1, testPoint2;
	
	@Before
	public void setup(){
		testPoint1 = new Point(6,0);
		testPoint2 = new Point(5,6);
		chessBoard = new Board();
		chessBoard.populateBoard();
		knight = chessBoard.getPiece(testPoint1);
		pawn = chessBoard.getPiece(testPoint2);
	}
	
	@Test
	public void locationTest() {
		assertEquals(testPoint1, knight.getLocation());
	}
	
	@Test
	public void colorTest() {
		assertEquals(PieceColor.WHITE, knight.getColor());
	}

    @Test
    public void validMove() {
    	testPoint1.setLocation(5, 2);
        assertEquals(ReturnCodes.SUCCESS, knight.move(testPoint1, chessBoard));
        assertEquals(testPoint1, knight.getLocation());
    }
    
    @Test
    public void invalidMove1(){
    	testPoint2.setLocation(6,2);
    	assertEquals(ReturnCodes.BAD_DESTINATION, knight.move(testPoint2,  chessBoard));
    	assertEquals(testPoint1, knight.getLocation());
    }
    
    @Test
    public void invalidMove2(){
    	testPoint2.setLocation(4,1);
    	assertEquals(ReturnCodes.BAD_DESTINATION, knight.move(testPoint2,  chessBoard));
    	assertEquals(testPoint1, knight.getLocation());
    }
    
    @Test
    public void outOfBoundMoves(){
    	testPoint2.setLocation(8,1);
    	assertEquals(ReturnCodes.INVALID_LOCATION, knight.move(testPoint2,  chessBoard));
    	testPoint2.setLocation(-1,1);
    	assertEquals(ReturnCodes.INVALID_LOCATION, knight.move(testPoint2,  chessBoard));
    	testPoint2.setLocation(5,-1);
    	assertEquals(ReturnCodes.INVALID_LOCATION, knight.move(testPoint2,  chessBoard));
    	testPoint2.setLocation(5,8);
    	assertEquals(ReturnCodes.INVALID_LOCATION, knight.move(testPoint2,  chessBoard));
    	assertEquals(testPoint1, knight.getLocation());
    }
    
    @Test
    public void capture(){
    	testPoint1.setLocation(5, 2);
    	chessBoard.movePiece(pawn, testPoint1);
        assertEquals(ReturnCodes.SUCCESS, knight.move(testPoint1, chessBoard));
        assertEquals(testPoint1, knight.getLocation());
    }
}
