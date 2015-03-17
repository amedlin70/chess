package chess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ChessServer implements Runnable{
	private ChessModel model;
	ServerSocket serverSocket;
	Socket clientSocket;
	BufferedReader in;
	PrintWriter out;
	JFrame frame;
	
	public ChessServer(ChessModel m){ 
		model = m;
	}
	public void run(){
		String msg;
		
		//ask for port number
		String port = (String)JOptionPane.showInputDialog(frame, "Port number to use.",
                "Port Number", JOptionPane.PLAIN_MESSAGE, null, null, "5000");
		
		//create a socket using port number
		try{
			serverSocket = new ServerSocket(Integer.parseInt(port));
			model.updateMessageArea("Waiting for client to connect\n");
			clientSocket = serverSocket.accept();
			model.updateMessageArea("Connected\n");
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out = new PrintWriter(clientSocket.getOutputStream(), true);
		} 
		catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		while(true){
			try {
				msg = in.readLine();
				model.handleSocketMessage(msg);
			} 
			catch (IOException e) {
				System.out.println("Connection lost\n");
				System.exit(1);
			}
			
		}
	}

	/**
	 * Sends a message over the socket
	 * @param msg
	 */
	public void sendMessage(String msg) {
		out.println(msg);
	}
}
