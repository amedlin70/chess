import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import chess.Board;
import chess.PieceColor;
import chess.Piece;



public class BoardTest {
	Board chessBoard;
	
	@Before
	public void setup(){
		chessBoard = new Board();
		chessBoard.populateBoard();
	}
	
	/**
	 * Ensure the piece at (6,1) is a PAWN and BLACK
	 */
	@Test
	public void BlackPawn() {
		assertTrue(chessBoard.getPiece(new Point(6,1)) instanceof chess.Pawn);
		assertEquals(PieceColor.WHITE, chessBoard.getPiece(new Point(6,1)).getColor());
	}

	/**
	 * Ensure the piece at (1,6) is a PAWN and WHITE
	 */
	@Test
	public void WhitePawn() {
		assertTrue(chessBoard.getPiece(new Point(6,1)) instanceof chess.Pawn);
		assertEquals(PieceColor.BLACK, chessBoard.getPiece(new Point(1,6)).getColor());
	}

	/**
	 * Ensure the setPiece function works. The logic to ensure it is a valid move is in the Piece
	 * Class, which normally calls this function.
	 */
	@Test
	public void movePiece() {
		Piece piece = chessBoard.getPiece(new Point(5,1));
		
		assertEquals(1,chessBoard.movePiece(piece, new Point(5, 2)));
		assertEquals(new Point(5,2), piece.getLocation());
		assertEquals(null, chessBoard.getPiece(new Point(5,1)));
	}
	
	/**
	 * Ensure the setPiece function works. The logic to ensure it is a valid move is in the Piece
	 * Class, which normally calls this function.
	 */
	@Test
	public void capturePiece() {
		Piece attacker = chessBoard.getPiece(new Point(5,1));
		Piece defender = chessBoard.getPiece(new Point(6,6));
		
		assertEquals(1, chessBoard.movePiece(attacker, new Point(5, 5)));
		assertEquals(new Point(5,5), attacker.getLocation());
		assertEquals(null, chessBoard.getPiece(new Point(5,1)));
		
		chessBoard.movePiece(attacker, defender.getLocation());
		assertEquals(null, chessBoard.getPiece(new Point(5,5)));
		assertEquals(false, defender.isAlive());
		assertEquals(new Point(6,6), attacker.getLocation());
	}
	
	@Test
	public void specialBoardTest(){
		chessBoard.clearBoard();
		chessBoard.populateSpecialBoard();
		
		Point testPoint1 = new Point(1,0);
		Point testPoint2 = new Point(2,0);
		
		Piece oldWoman = chessBoard.getPiece(testPoint1);
		Piece serpent = chessBoard.getPiece(testPoint2);
		
		assertEquals("Old Woman", oldWoman.getPieceName());
		assertEquals("Serpent", serpent.getPieceName());
	}
	
}
