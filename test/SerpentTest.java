import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import chess.Board;
import chess.ReturnCodes;
import chess.Piece;
import chess.PieceColor;
import chess.Serpent;


public class SerpentTest{

	private Board chessBoard;
	private Piece serpent;
	private Point testPoint;
	
	@Before
	public void setup(){
		testPoint = new Point(3,3);
		chessBoard = new Board();
		chessBoard.populateBoard();
		serpent = new Serpent(PieceColor.BLACK, new Point(3,3), "images/WhitePawn.png");
		chessBoard.addPiece(serpent, testPoint);
	}
	
	@Test
	public void locationTest() {
		assertEquals(testPoint, serpent.getLocation());
	}
	
	@Test
	public void colorTest() {
		assertEquals(PieceColor.BLACK, serpent.getColor());
	}

    @Test
    public void validMove() {
    	testPoint.setLocation(5, 5);
        assertEquals(ReturnCodes.SUCCESS, serpent.move(testPoint, chessBoard));
        assertEquals(testPoint, serpent.getLocation());
    }
    
    @Test
    public void invalidMove(){
    	testPoint.setLocation(7,7);
    	assertEquals(ReturnCodes.BAD_DESTINATION, serpent.move(testPoint, chessBoard));
    }
    
    @Test
    public void outOfBoundMoves(){
    	testPoint.setLocation(8,1);
    	assertEquals(ReturnCodes.INVALID_LOCATION, serpent.move(testPoint,  chessBoard));
    	testPoint.setLocation(-1,1);
    	assertEquals(ReturnCodes.INVALID_LOCATION, serpent.move(testPoint,  chessBoard));
    	testPoint.setLocation(5,-1);
    	assertEquals(ReturnCodes.INVALID_LOCATION, serpent.move(testPoint,  chessBoard));
    	testPoint.setLocation(5,8);
    	assertEquals(ReturnCodes.INVALID_LOCATION, serpent.move(testPoint,  chessBoard));
    }
       
}
