package flysall.socket;

import java.net.Socket;
import java.io.*;

public class Connection {
	private Socket socket;
	public Connection (Socket socket){
		this.socket = socket;
	}
	
	public void printIn(String message){
		PrintWriter writer;
		try{
			writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()) , true);
			writer.println(message);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
}
