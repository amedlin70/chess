import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import chess.Board;
import chess.ReturnCodes;
import chess.Piece;


public class UndoTest {

	private Board chessBoard;
	private Piece whitePiece, blackPiece;
	private Point testPoint1, testPoint2;
	
	@Before
	public void setup(){
		testPoint1 = new Point(1,1);
		testPoint2 = new Point(4,6);
		chessBoard = new Board();
		chessBoard.populateBoard();
		whitePiece = chessBoard.getPiece(testPoint1);
	}
	
	@Test
	public void undoNoEnemy() {
		assertEquals(testPoint1, whitePiece.getLocation());
		testPoint1.setLocation(1,2);
		assertEquals(ReturnCodes.SUCCESS, whitePiece.move(testPoint1, chessBoard));
		testPoint1.setLocation(1,1);
		chessBoard.undoMove(whitePiece, null, testPoint1, whitePiece.getLocation());
		assertEquals(testPoint1, whitePiece.getLocation());
	}

	@Test
	public void undoWithEnemy() {
		Point prevPoint, curPoint;
		testPoint1.setLocation(7,1);
		whitePiece = chessBoard.getPiece(testPoint1);
		testPoint1.setLocation(7,3);
		assertEquals(ReturnCodes.SUCCESS, whitePiece.move(testPoint1, chessBoard));
		
		testPoint1.setLocation(4,6);
		blackPiece = chessBoard.getPiece(testPoint1);
		testPoint2.setLocation(4,5);
		assertEquals(ReturnCodes.SUCCESS, blackPiece.move(testPoint2, chessBoard));
		
		testPoint1.setLocation(6,1);
		testPoint2.setLocation(6,2);
		whitePiece = chessBoard.getPiece(testPoint1);
		assertEquals(ReturnCodes.SUCCESS, whitePiece.move(testPoint2, chessBoard));
		
		testPoint1.setLocation(7,3); //white pawn
		whitePiece = chessBoard.getPiece(testPoint1);
		testPoint2.setLocation(3,7); //black queen
		blackPiece = chessBoard.getPiece(testPoint2);
		
		prevPoint = blackPiece.getLocation();
		curPoint = whitePiece.getLocation();
		chessBoard.undoMove(blackPiece, whitePiece, prevPoint, curPoint);
		assertEquals(testPoint1, whitePiece.getLocation());
		assertEquals(testPoint2, blackPiece.getLocation());
	}

}
