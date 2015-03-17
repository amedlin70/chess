package chess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ChessClient implements Runnable{
	private ChessModel model;
	ServerSocket serverSocket;
	Socket clientSocket;
	BufferedReader in;
	PrintWriter out;
	JFrame frame;
	
	public ChessClient(ChessModel m){
		model = m;
	}  
	public void run(){
		String msg;
		
		//Ask for IP address and port number
		String ip = (String)JOptionPane.showInputDialog(frame, "IP address to connect to.",
                "IP Address", JOptionPane.PLAIN_MESSAGE, null, null, "127.0.0.1");
		
		String port = (String)JOptionPane.showInputDialog(frame, "Port number to use.",
                "Port Number", JOptionPane.PLAIN_MESSAGE, null, null, "5000");
		
		//create a socket
		try{
			clientSocket = new Socket(ip,Integer.parseInt(port));
			model.updateMessageArea("Connected to server\n");
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
	
	public BufferedReader getIn(){
		return in;
	}
	
	public PrintWriter getOut(){
		return out;
	}
	public void sendMessage(String msg) {
		out.println(msg);
	}
}
