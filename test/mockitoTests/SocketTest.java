package mockitoTests;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
 
import org.junit.Before;
import org.junit.Test;


import chess.Board;
import chess.ChessClient;
import chess.ChessModel;
import chess.ChessServer;
import chess.ChessView;

 
public class SocketTest {
	Board mockBoard;
	ChessView mockView;
	ChessModel model;
	
	@Before
	public void setup(){
		mockBoard = mock(Board.class);
		mockView = mock(ChessView.class);
		model = new ChessModel(mockBoard, mockView);
	}
	
	
	@Test
	public void testSomething(){
        ChessServer mockServer = mock(ChessServer.class);
        doThrow(RuntimeException.class).when(mockServer).sendMessage("UNDO");

        try{
        	mockServer.sendMessage("UNDO");
        }
        catch (Exception e){
        	assert(e instanceof RuntimeException);
        }
    }
	
	@Test
	public void testServer(){
		ChessServer mockServer = mock(ChessServer.class);
		
		model.testServer(mockServer);
		model.sendMessage("UNDO");
		
		verify(mockServer).sendMessage("UNDO");
    }
	
	
	@Test
	public void testClient(){
		ChessClient mockClient = mock(ChessClient.class);
		
		model.testClient(mockClient);
		model.sendMessage("UNDO");
		
		verify(mockClient).sendMessage("UNDO");
    }
}      