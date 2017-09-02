package flysall.douyu.douyu;

import java.io.IOException;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

public class Crawl extends Thread {
	// 弹幕服务器端口
	List<Integer> ports = new LinkedList<Integer>();
	// 房间id
	String rid;
	// 弹幕分组
	String gid;
	// 与弹幕服务器交互的控制器
	MessageHandler messageHandler;
	// 登录名
	String username;

	public Crawl() throws IOException {
		rid = Utils.getRoomId();
	}

	/**
	 * 初始化
	 */
	public void init() throws IOException {
		String ip = Utils.getServerIP();
		int port = Integer.valueOf(Utils.getServerPort());
		Socket socket = new Socket(ip, port);
		System.out.println("从服务器(" + ip + ":" + port + ")获取弹幕服务器");
		MessageHandler messageHandler = new MessageHandler(socket);

		String s = "type@=loginreq/username@=/ct@=0/password@=/roomid@=" + Utils.getRoomId() + "/";
		String time = String.valueOf(System.currentTimeMillis() / 1000);
		String uuid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
		String rt = "rt@=" + time + "/";
		String devid = "devid@=" + uuid + "/";
		String vk = "vk@=" + Utils.md5(time + "7oE9nPEG9xXV69phU31FYCLUagKeYtsF" + uuid) + "/";
		String ver = "ver@=20150929/";
		String content = s + devid + rt + vk + ver;

		System.out.println("[init()] content was sent is: " + content);
		messageHandler.send(content);
		// 准备接收数据包
		for (int i = 0; i < 3; i++) {
			byte[] bytes = messageHandler.read();
			String msg = new String(Arrays.copyOfRange(bytes, 8, bytes.length));
			System.out.println("[init()] the received msg is: " + msg);
			if (msg.startsWith("type@=msgrepeaterlist")) {
				Pattern p = Pattern.compile("@ASip(.*?)@AS@S");
				Matcher m = p.matcher(msg);

				while (m.find()) {
					String str = m.group(1);
					Integer po = Integer.valueOf(str.split("@ASport@AA=")[1]);
					ports.add(po);
				}
			} else if (msg.startsWith("type@=setmsggroup")) {
				gid = msg.split("gid@=")[1].split("/")[0];
			} else if (msg.startsWith("type@=loginres")) {
				username = msg.split("username@=")[1].split("/")[0];
			}
		}
		socket.close();
	}

	/**
	 * 进行登录， username已在init()中获得
	 * 
	 * @throws IOException
	 */
	public void login() throws IOException {
		Socket socket = new Socket("danmu.douyutv.com", ports.get(0));
		System.out.println("连接弹幕服务器(danmu.douyutv.com:" + ports.get(0) + ")");
		messageHandler = new MessageHandler(socket);

		String loginreq = "type@=loginreq/username@=" + username + "/password@=1234567890123456/roomid@=" + rid + "/";
		System.out.println("[login()] the loginreq is: " + loginreq);
		messageHandler.send(loginreq);
		System.out.println("登录名:" + username);

		String joinGroup;
		if (Utils.isSeaMode()) {
			joinGroup = "type@=joingroup/rid@=" + rid + "/gid@=-9999/";
			System.out.println("海量弹幕模式:开启");
		} else {
			joinGroup = "type@=joingroup/rid@=" + rid + "/gid@=" + gid + "/";
			System.out.println("海量弹幕模式：关闭");
			System.out.println("进入" + gid + "号弹幕分组");
		}
		System.out.println("[login()] the joinGroup is: " + joinGroup);
		messageHandler.send(joinGroup);
	}

	@Override
	public void run() {
		try {
			System.out.println("房间名:" + Utils.getRoomName());
			System.out.println("主播:" + Utils.getOwnerName());
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			init();
			login();
			System.out.println("--------------------------------");
			System.out.println("弹幕重定向至result.txt, 请前往查看");
			PrintStream ps = new PrintStream(new FileOutputStream("result.txt"));
			System.setOut(ps);
			while (true) {
				byte[] bytes = messageHandler.read();
				String msg = new String(Arrays.copyOfRange(bytes, 8, bytes.length));
				if (msg.startsWith("type@=chatmsg")) {
					String nickname = msg.split("nn@=", 2)[1].split("/", 2)[0];
					String content = msg.split("txt@=", 2)[1].split("/", 2)[0];
					System.out.println("[" + nickname + "]" + "---> " + content);
				} 
//				else {
//					System.out.println(msg);
//				}
				Thread.sleep(1);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				messageHandler.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
