import static org.junit.Assert.*;

import java.awt.Point;

import chess.Board;
import chess.Piece;
import chess.PieceColor;
import chess.ReturnCodes;

import org.junit.Test;
import org.junit.Before;


public class PawnTest {
	
	private Board chessBoard;
	private Piece pawn;
	private Point testPoint;
	
	@Before
	public void setup(){
		testPoint = new Point(1,1);
		chessBoard = new Board();
		chessBoard.populateBoard();
		pawn = chessBoard.getPiece(testPoint);
	}
	
	@Test
	public void locationTest() {
		assertEquals(testPoint, pawn.getLocation());
	}
	
	@Test
	public void colorTest() {
		assertEquals(PieceColor.WHITE, pawn.getColor());
	}

    @Test
    public void validStartMoveWhite() {
    	testPoint.setLocation(1, 3);
        assertEquals(ReturnCodes.SUCCESS, pawn.move(testPoint, chessBoard));
        assertEquals(testPoint, pawn.getLocation());
    }
    
    @Test
    public void validStartMoveBlack(){
    	testPoint.setLocation(0,6);
    	pawn = chessBoard.getPiece(testPoint);
    	testPoint.setLocation(0,4);
        assertEquals(ReturnCodes.SUCCESS, pawn.move(testPoint, chessBoard));
        assertEquals(testPoint, pawn.getLocation());
    }
    
    @Test
    public void validMoveWhite(){
    	testPoint.setLocation(1,2);
        assertEquals(ReturnCodes.SUCCESS, pawn.move(testPoint, chessBoard));
        assertEquals(testPoint, pawn.getLocation());
    }
    
    @Test 
    public void invalidMoveWhite(){
    	testPoint.setLocation(1,3);
    	pawn.move(testPoint, chessBoard);
    	testPoint.setLocation(1,2);
    	assertEquals(ReturnCodes.BAD_DESTINATION, pawn.move(testPoint, chessBoard));
    }
    
    @Test
    public void validMoveBlack(){
       	testPoint.setLocation(0,6);
    	pawn = chessBoard.getPiece(testPoint);
    	testPoint.setLocation(0,5);
        assertEquals(ReturnCodes.SUCCESS, pawn.move(testPoint, chessBoard));
        assertEquals(testPoint, pawn.getLocation());

    }
    
    @Test
    public void invalidLocation1() {
    	testPoint.setLocation(-1,6);
    	assertEquals(ReturnCodes.INVALID_LOCATION, pawn.move(testPoint, chessBoard));
    }
 
    @Test
    public void invalidLocation2(){
    	testPoint.setLocation(0,8);
    	assertEquals(ReturnCodes.INVALID_LOCATION, pawn.move(testPoint, chessBoard));
    }
    
    @Test
    public void badDestination1(){
    	testPoint.setLocation(2,2);
    	assertEquals(ReturnCodes.BAD_DESTINATION, pawn.move(testPoint, chessBoard));
    }
    
    @Test
    public void badDestination2(){
    	testPoint.setLocation(1,4);
    	assertEquals(ReturnCodes.BAD_DESTINATION, pawn.move(testPoint, chessBoard));
    }
    
    @Test
    public void destinationBlocked(){
    	Point testPoint2 = new Point(2,2);
    	chessBoard.movePiece(pawn, testPoint2);
    	testPoint.setLocation(2,1);
    	pawn = chessBoard.getPiece(testPoint);
    	assertEquals(ReturnCodes.BAD_DESTINATION,pawn.move(testPoint2, chessBoard));
    	assertEquals(testPoint, pawn.getLocation());    }
    
    @Test
    public void stillAlive(){
    	assertEquals(true, pawn.isAlive());
    }
    
    @Test
    public void captured(){
    	chessBoard.movePiece(pawn, new Point(5, 5));
    	Piece enemyPawn = chessBoard.getPiece(new Point(6,6));
    	assertEquals(ReturnCodes.SUCCESS,pawn.move(new Point(6,6), chessBoard));
    	assertEquals(false, enemyPawn.isAlive());
    }
    
    @Test
    public void imgLocationTest(){
    	assertEquals(pawn.getImgLoc(), "images/WhitePawn.png");
    }
}
