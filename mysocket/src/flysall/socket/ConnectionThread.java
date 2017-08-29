package flysall.socket;

import java.io.*;
import java.net.Socket;
import org.apache.log4j.Logger;

public class ConnectionThread extends Thread{	
	private Socket socket;
	private SocketServer socketServer;
	private Connection connection;
	private boolean isRunning;
	private Logger logger = Logger.getLogger(ConnectionThread.class);
	
	public ConnectionThread(Socket socket, SocketServer socketServer){
		this.socket = socket;
		logger.info("Connection.socketserver: please check it");
		this.socketServer = socketServer;
		connection = new Connection(socket);
		isRunning = true;
	}
	
	@Override
	public void run(){
		while(isRunning){
			if(socket.isClosed()){
				isRunning = false;
				break;
			}
			BufferedReader reader;
			try{
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String message = reader.readLine();
				if(message != null){
					logger.info("Connection.run(): please check socketserver.getMessageHandler()");
					socketServer.getMessageHandler().onReceive(connection, message);
				}
			} catch (IOException e){
				e.printStackTrace();
			}
		}
	}
	public void stopRunning(){
		isRunning = false;
		try{
			socket.close();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
}





















