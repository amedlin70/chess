package chess;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class ChessMenu extends JMenuBar{
	
	private JMenu menu = new JMenu("File");
	private JMenu networkMenu = new JMenu("Network");
	private JMenuItem menuNewGame = new JMenuItem("New Game");
	private JMenuItem menuNewCustomGame = new JMenuItem("New Custom Game");
	private JMenuItem menuForfeit = new JMenuItem("Forfeit");
	private JMenuItem menuUndo = new JMenuItem("Undo");
	private JMenuItem menuServer = new JMenuItem("Host a Chess Game");
	private JMenuItem menuClient = new JMenuItem("Connect to a Chess Game");
	private JMenuItem menuExit = new JMenuItem("Exit");
	
	public ChessMenu(){
		this.add(menu);
		menu.add(menuNewGame);
		menu.add(menuNewCustomGame);
		menu.add(menuForfeit);
		menu.add(menuUndo);
		menu.addSeparator();
		menu.add(networkMenu);
		networkMenu.add(menuServer);
		networkMenu.add(menuClient);
		menu.addSeparator();
		menu.add(menuExit);
		
		//set ctrl-z to menuUndo
		menuUndo.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
		
		//set ctrl-n to menuNewGame
		menuNewGame.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_N, ActionEvent.CTRL_MASK));
	}

	public void addSpecialListener(ActionListener a) {
		menuNewCustomGame.addActionListener(a);
	}

	public void addUndoListener(ActionListener a) {
		menuUndo.addActionListener(a);	
	}

	public void addForfeitListener(ActionListener a) {
		menuForfeit.addActionListener(a);
	}

	public void addNewGameListener(ActionListener a) {
		menuNewGame.addActionListener(a);		
	}

	public void addExitListener(ActionListener a) {
		menuExit.addActionListener(a);		
	}

	public void addHostListener(ActionListener a) {
		menuServer.addActionListener(a);		
	}

	public void addClientListener(ActionListener a) {
		menuClient.addActionListener(a);
	}
}
