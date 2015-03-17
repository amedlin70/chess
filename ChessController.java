package chess;

 
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;

/**
 * The JFrame that the main board is drawn to.
 * @author andy
 *
 */
public class ChessController extends JFrame {
	private ChessView view;
	private ChessModel model;
	private Board chessBoard;
	private ChessMenu menuBar = new ChessMenu();

	
	public ChessController(Board board){
		super("Chess");
		this.chessBoard = board;
		this.setResizable(false);
		this.add(menuBar);
		this.setJMenuBar(menuBar);
		view = new ChessView(this, chessBoard);
		model = new ChessModel(board, view);
		getContentPane().add(view);
		
		//create an actionListener to listen for the forfeit button
		menuBar.addForfeitListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				model.sendMessage("FORFEIT");
				model.forfeit();
			}
		});

		//create an actionListener to listen for the forfeit button
		menuBar.addNewGameListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				model.sendMessage("NEWGAME");
				model.newGame();
			}
		});
		
		//Create an actionListener to listen for the Undo button
		menuBar.addUndoListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				model.undo();
				model.sendMessage("UNDO");
			}
		});
		
		//Create an actionListener to listen for the Special button
		menuBar.addSpecialListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				model.specialBoard();
				model.sendMessage("NEWSPECIAL");
			}
		});
		
		//Create an actionListener to listen for Exit
		menuBar.addExitListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				System.exit(0);
			}
		});
		
		//Create an actionListener to listen for "Host a Chess Game"
		menuBar.addHostListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				model.createServer();
			}
		});
		
		//Create an actionListener to listen for "Connect to a Chess Game"
		menuBar.addClientListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				model.createClient();
			}
		});
		
		
		//Add a MouseListener to the chessPanel in order to interact with it
		view.addSquareListener(new MouseListener(){
			public void mouseClicked(MouseEvent event){
				model.handleBoardEvent(new Point(event.getX(), event.getY()));
			}
			public void mouseEntered(MouseEvent event){}
			public void mouseExited(MouseEvent event){}
			public void mouseReleased(MouseEvent event){}
			public void mousePressed(MouseEvent event){}
		});
	}
}