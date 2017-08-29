package flysall.socket;

import java.io.*;
import java.net.*;
import java.util.*;

import flysall.socket.example.EchoHandler;

public class SocketServer {
	private ServerSocket serverSocket;
	private ListeningThread listeningThread;
	private MessageHandler messageHandler;

	public SocketServer(int port, EchoHandler handler) {
		messageHandler = (MessageHandler) handler;
		try {
			serverSocket = new ServerSocket(port);
			listeningThread = new ListeningThread(this, serverSocket);
			listeningThread.checkAccess();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setMessageHandler(MessageHandler handler) {
		messageHandler = handler;
	}

	public MessageHandler getMessageHandler() {
		return messageHandler;
	}

	/**
	 * Ready for use
	 */
	@SuppressWarnings("deprecation")
	public void close() {
		try {
			if (serverSocket != null && !serverSocket.isClosed()) {
				listeningThread.stopRunning();
				listeningThread.suspend();
				listeningThread.stop();

				serverSocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
