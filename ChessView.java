package chess;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

/**
 * This class does all of the drawing of the chess board.  
 */
public class ChessView extends JComponent{
	private static Color square1 = new Color(255,150,0,255);
	private static Color square2 = new Color(5,5,100,255);
	private static Color highlight1 = new Color(0,0,255,255);
	private static Color highlight2 = new Color(255,100,0,255);
	private static Font font1 = new Font("Serif", Font.BOLD, 24);
	private static Font font2 = new Font("Serif", Font.BOLD, 18);
	private static int squareSize = 64;
	private static int xOffset = 32;
	private static int yOffset = 50;
	private static int boardSize = Constants.MAX_BOARD_SIZE * squareSize;
	private Board chessBoard;
	private JPanel controlPanel;
	private JPanel chessPanel;
	private JLabel playerLabel = new JLabel();
	private JLabel scoreLabel = new JLabel();
	private JPanel chessSquare[][] = new JPanel[Constants.MAX_BOARD_SIZE][Constants.MAX_BOARD_SIZE];
	private JTextArea messageArea = new JTextArea();
	private JScrollPane messagePane = new JScrollPane(messageArea);
	private int player1 = 0;
	private int player2 = 0;

	
	public ChessView(ChessController controler, Board board){
		chessBoard = board;
		setDoubleBuffered(true);
		setOpaque(true);
		controlPanel = new JPanel(null);
		chessPanel = new JPanel(new GridLayout(Constants.MAX_BOARD_SIZE, Constants.MAX_BOARD_SIZE));
		
		for(int y = 0; y < Constants.MAX_BOARD_SIZE; y++){
			for(int x = 0; x < Constants.MAX_BOARD_SIZE; x++){
				chessSquare[x][y] = new JPanel();
			}
		}
	}


	/**
	 * Creates the GUI
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
				
		controlPanel.setVisible(true);
		controlPanel.setSize(this.getSize());
		controlPanel.setBackground(Color.white);
		this.add(controlPanel);
		
		//add all the different components to the controlPanel
		addChessPanel();
		addLabels();
		addMessageArea();

		//draw the chessBoard
		drawChessBoard();
		drawPieces();
		chessPanel.repaint();
	}

	
	/** 
	 * Creates the messageArea and adds it to the controlPanel.  The messageArea keeps track of all moves and messages
	 * sent from the ChessModel
	 */
	private void addMessageArea() {
		messageArea.setEditable(false);
		messageArea.append("Moves\n");
		messagePane.setSize(250, boardSize);
		messagePane.setLocation(boardSize + xOffset, yOffset);

		controlPanel.add(messagePane);
	}


	/**
	 * Creates the playerLabel and scoreLabel and adds them to the controlPanel.  The PlayerLable shows which turn it is, and
	 * the scoreLabel displays the current score.
	 */
	private void addLabels() {
		playerLabel.setSize(200, yOffset);
		playerLabel.setFont(font1);
		playerLabel.setText("<html><FONT COLOR=ORANGE>Player 1s Turn");
		playerLabel.setLocation(xOffset, 0);
		
		scoreLabel.setSize(300, yOffset);
		scoreLabel.setFont(font2);
		scoreLabel.setText("<html><FONT COLOR=ORANGE>Player 1: " + player1 + "<P><FONT COLOR=BLUE>Player 2: " + player2);
		scoreLabel.setLocation(425+xOffset, 0);
		scoreLabel.setVisible(true);
		
		controlPanel.add(playerLabel);
		controlPanel.add(scoreLabel);
	}


	/**
	 * Set up the chessPanel and add it to the controlPanel
	 */
	private void addChessPanel() {
		chessPanel.setSize(boardSize, boardSize);
		chessPanel.setLocation(xOffset, yOffset);
		chessPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		chessPanel.setVisible(true);
		controlPanel.add(chessPanel);
	}


	/**
	 * Draws all the pieces onto the chessboard.
	 * @param g
	 */
	public void drawPieces() {
		Piece tmpPiece;
		Point tmpLoc = new Point(0,0);
		ImageIcon img;
		
		for(int x = 0; x < Constants.MAX_BOARD_SIZE; x++){
			for(int y= 0; y < Constants.MAX_BOARD_SIZE; y++){
				tmpLoc.setLocation(x,y);
				tmpPiece = chessBoard.getPiece(tmpLoc);

				//This will remove any previous JLabels in the current square.  Must call revalidate() after.
				chessSquare[x][y].removeAll();
				
				//if the piece exists, draw it's image onto the square
				if(tmpPiece != null){
					if(tmpPiece.getImgLoc() != null){
						img = new ImageIcon(this.getClass().getResource(tmpPiece.getImgLoc()));
						chessSquare[x][y].add(new JLabel(img));
					}
				}
				chessSquare[x][y].revalidate();
			}
		}
	}

	
	/**
	 * Sets up and draws the ChessBoard panel.
	 * @param g
	 */
	private void drawChessBoard() {
		for(int y = Constants.MAX_BOARD_SIZE - 1; y >= 0; y--){
			for(int x = 0; x < Constants.MAX_BOARD_SIZE; x++){
				if((x+y)%2 == 1){
					chessSquare[x][y].setBackground(square1);
				}
				else{
					chessSquare[x][y].setBackground(square2);
					
				}
				chessPanel.add(chessSquare[x][y]);
			}
		}
	}
	
	
	/**
	 * Sets the image of a square based on the given x and y coordinates and the imgLoc
	 * @param x
	 * @param y
	 * @param imgLoc
	 */
	public void setSquareImage(int x, int y, String imgLoc){
		chessSquare[x][y].removeAll();
		if(imgLoc != null){
			ImageIcon img = new ImageIcon(getClass().getResource(imgLoc));
			chessSquare[x][y].add(new JLabel(img));
		}
		chessSquare[x][y].revalidate();
	}

	
	/**
	 * Highlights a square so that the player knows the piece on it is the selected piece.
	 * @param x
	 * @param y
	 */
	public void highlightSquare(int x, int y){
		if((x+y)%2 == 0)
			chessSquare[x][y].setBackground(highlight1);
		else
			chessSquare[x][y].setBackground(highlight2);
	}
	
	
	/**
	 * Removes a highlight on a square and changes its color back to normal
	 * @param x
	 * @param y
	 */
	public void removeHightlightSquare(int x, int y){
		if((x+y)%2 == 0)
			chessSquare[x][y].setBackground(square2);
		else
			chessSquare[x][y].setBackground(square1);
	}
	
	
	/**
	 * Set's the player label to the given string.
	 * @param s
	 */
	public void setPlayer(String s){
		playerLabel.setText(s);
	}
	
	public Dimension getPreferredSize(){
		return new Dimension(826, 612);
	}
	
	
	public Dimension getMinimumSize(){
		return getPreferredSize();
	}
	
	
	public Dimension getMaximumSize(){
		return getPreferredSize();
	}


	/**
	 * updates the score panel with the current score
	 * @param p1
	 * @param p2
	 */
	public void updateScore(int p1, int p2) {
		player1 = p1;
		player2 = p2;
		scoreLabel.setText("<html><FONT COLOR=ORANGE>Player 1: " + player1 + "<P><FONT COLOR=BLUE>Player 2: " + player2);
	}
	
	
	/**
	 * Adds a new message to the message area.  This will be a string representation of a move, or a message to the player.
	 * @param s
	 */
	public void updateMessageArea(String s){
		messageArea.append(s);
	}


	/**
	 * Adds a MouseListener to the chessPanel.
	 * @param l
	 */
	public void addSquareListener(MouseListener l){
		chessPanel.addMouseListener(l);
	}
}