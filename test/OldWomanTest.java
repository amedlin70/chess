import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import chess.Board;
import chess.ReturnCodes;
import chess.OldWoman;
import chess.Piece;
import chess.PieceColor;


public class OldWomanTest {

	private Board chessBoard;
	private Piece oldWoman;
	private Point testPoint;
	
	@Before
	public void setup(){
		testPoint = new Point(3,3);
		chessBoard = new Board();
		chessBoard.populateBoard();
		oldWoman = new OldWoman(PieceColor.BLACK, new Point(3,3), "images/WhitePawn.png");
		chessBoard.addPiece(oldWoman, testPoint);
	}
	
	@Test
	public void locationTest() {
		assertEquals(testPoint, oldWoman.getLocation());
	}
	
	@Test
	public void colorTest() {
		assertEquals(PieceColor.BLACK, oldWoman.getColor());
	}

    @Test
    public void validMove() {
    	testPoint.setLocation(5, 5);
        assertEquals(ReturnCodes.SUCCESS, oldWoman.move(testPoint, chessBoard));
        assertEquals(testPoint, oldWoman.getLocation());
    }
}
