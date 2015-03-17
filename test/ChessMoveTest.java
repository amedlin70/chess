import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import chess.Board;
import chess.ChessMove;
import chess.Piece;
import chess.PieceColor;


public class ChessMoveTest {
	Piece attacker;
	Piece defender;
	Point prevLocation;
	Point destination;
	Board chessBoard;
	ChessMove move;
	
	@Before
	public void setup(){
		chessBoard = new Board();
		chessBoard.populateBoard();
		
		prevLocation = new Point(1,0);
		destination = new Point(2,0);
		attacker = chessBoard.getPiece(prevLocation);
		defender = chessBoard.getPiece(destination);
		
		move = new ChessMove(prevLocation, destination, attacker, defender);
	}
	
	/**
	 * Ensure the piece at (6,1) is a PAWN and BLACK
	 */
	@Test
	public void test() {
		assertEquals(prevLocation, move.getPrevLocation());
		assertEquals(destination, move.getDestination());
		assertEquals(attacker, move.getAttacker());
		assertEquals(defender, move.getDefender());
	}
}
