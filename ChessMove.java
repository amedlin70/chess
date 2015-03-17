package chess;

import java.awt.Point;

public class ChessMove {
	private Point prevLocation = new Point();
	private Point destination = new Point();
	private Piece attacker;
	private Piece defender;
	
	public ChessMove(Point prev, Point dest, Piece a, Piece d){
		prevLocation.setLocation(prev);
		destination.setLocation(dest);
		attacker = a;
		defender = d;
	}
	
	public Point getPrevLocation(){
		return prevLocation;
	}
	
	public Point getDestination(){
		return destination;
	}
	
	public Piece getAttacker(){
		return attacker;
	}
	
	public Piece getDefender(){
		return defender;
	}
}
