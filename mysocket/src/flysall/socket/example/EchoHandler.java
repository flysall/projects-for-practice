package flysall.socket.example;

import flysall.socket.*;

public class EchoHandler implements MessageHandler{
	@Override
	public void onReceive(Connection connection, String message){
		connection.println(message);
	}
}
