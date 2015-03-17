import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import chess.Board;
import chess.ReturnCodes;
import chess.Piece;
import chess.PieceColor;


public class CheckTest {

	private Board chessBoard;
	private Piece black, white;
	private Point testPoint1, testPoint2;
	
	@Before
	public void setup(){
		testPoint1 = new Point(5,1); //pawn
		testPoint2 = new Point(4,6); //pawn
		chessBoard = new Board();
		chessBoard.populateBoard();
		white = chessBoard.getPiece(testPoint1);
		black = chessBoard.getPiece(testPoint2);
	}
	
	@Test
	/**
	 * Perform a variation of the Fool's Mate chess game, but leaves a pawn blocking the
	 * path between their king and the enemy's queen.  The last move, attempting to move the white 
	 * pawn should be an illegal move as it will place the white king in check.
	 */
	public void putsKingInCheckTest() {
		//move white pawn to f3
		testPoint2.setLocation(5,2);
		white.move(testPoint2, chessBoard);
		
		//move black pawn to f5
		testPoint2.setLocation(4,4);
		black.move(testPoint2, chessBoard);
		
		//move white pawn to g3
		testPoint1.setLocation(6,1);
		testPoint2.setLocation(6,2);
		white = chessBoard.getPiece(testPoint1);
		white.move(testPoint2, chessBoard);
		
		//move black queen to h4
		testPoint1.setLocation(3,7);
		testPoint2.setLocation(7,3);
		black = chessBoard.getPiece(testPoint1);
		black.move(testPoint2, chessBoard);
		
		//attempt to move pawn to g4, but in doing so it will put the king in check
		testPoint1.setLocation(6,2);
		testPoint2.setLocation(6,3);
		white = chessBoard.getPiece(testPoint1);
		assertEquals(ReturnCodes.PUTS_KING_IN_CHECK, white.move(testPoint2, chessBoard));
	}
	
	@Test
	/**
	 * Perform the Fool's Mate chess game, putting the white king in checkmate
	 */
	public void checkmateTest() {
		//move white pawn to f3
		testPoint1.setLocation(5,2);
		white.move(testPoint1, chessBoard);
		
		//move black pawn to f5
		testPoint2.setLocation(4,4);
		black.move(testPoint2, chessBoard);
		
		//move white pawn to g4
		testPoint1.setLocation(6,1);
		testPoint2.setLocation(6,3);
		white = chessBoard.getPiece(testPoint1);
		white.move(testPoint2, chessBoard);
		
		//move black queen to h4, resulting in checkmate on white
		testPoint1.setLocation(3,7);
		testPoint2.setLocation(7,3);
		black = chessBoard.getPiece(testPoint1);
		black.move(testPoint2, chessBoard);
	
		assertEquals(true, chessBoard.isKingInCheck(PieceColor.WHITE)); 	//test if white king is in check
		assertEquals(true, chessBoard.isKingInCheckmate(PieceColor.WHITE));	//test if white king is in checkmate
	}
	
	/**
	 * Check for a check, where the king can move out of the way to avoid checkmate
	 */
	@Test
	public void kingAvoidsCheckMate(){
		//move white pawn to f3
		testPoint1.setLocation(5,2);
		white.move(testPoint1, chessBoard);
		
		//move black pawn to f5
		testPoint2.setLocation(4,4);
		black.move(testPoint2, chessBoard);
		
		//move white king to f2
		testPoint1.setLocation(4,0);
		testPoint2.setLocation(5,1);
		white = chessBoard.getPiece(testPoint1);
		white.move(testPoint2, chessBoard);
		
		//move black queen to h4, resulting in check on white
		testPoint1.setLocation(3,7);
		testPoint2.setLocation(7,3);
		black = chessBoard.getPiece(testPoint1);
		black.move(testPoint2, chessBoard);
		
		assertEquals(true, chessBoard.isKingInCheck(PieceColor.WHITE)); 		//make sure king is in check
		assertEquals(false, chessBoard.isKingInCheckmate(PieceColor.WHITE));	//make sure king is not in checkmate
	}
	
	/**
	 * A variation of Fools Mate, where the white knight can capture the black queen
	 * preventing checkmate.
	 */
	@Test
	public void knightPreventsCheckmate(){
		//move white pawn to f4
		testPoint1.setLocation(5,3);
		white.move(testPoint1, chessBoard);
		
		//move black pawn to f5
		testPoint2.setLocation(4,4);
		black.move(testPoint2, chessBoard);
		
		//move white pawn to g4
		testPoint1.setLocation(6,1);
		testPoint2.setLocation(6,3);
		white = chessBoard.getPiece(testPoint1);
		white.move(testPoint2, chessBoard);
		
		//move black pawn to a6 
		testPoint1.setLocation(0,6);
		testPoint2.setLocation(0,5);
		black = chessBoard.getPiece(testPoint1);
		black.move(testPoint2, chessBoard);
		
		//move white knight to f3
		testPoint1.setLocation(6,0);
		testPoint2.setLocation(5,2);
		white = chessBoard.getPiece(testPoint1);
		assertEquals(ReturnCodes.SUCCESS, white.move(testPoint2, chessBoard));
		
		//move black queen to h4, resulting in check on white
		testPoint1.setLocation(3,7);
		testPoint2.setLocation(7,3);
		black = chessBoard.getPiece(testPoint1);
		black.move(testPoint2, chessBoard);
		
		assertEquals(true, chessBoard.isKingInCheck(PieceColor.WHITE)); 		//make sure king is in check
		assertEquals(false, chessBoard.isKingInCheckmate(PieceColor.WHITE));	//make sure king is not in checkmate
	}
}
