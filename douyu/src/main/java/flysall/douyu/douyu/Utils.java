package flysall.douyu.douyu;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.util.*;

public class Utils {
	private static String roomSrc;
	private static Properties config = new Properties();

	/**
	 * 加载配置文件
	 */
	static {
		try {
			config.load(Utils.class.getResourceAsStream("/config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取房间号
	 * 
	 * @return 房间号
	 * @throws IOException
	 */
	public static String getRoomId() throws IOException {
		if (roomSrc == null) {
			Document roomDoc = Jsoup.connect(getRoomUrl()).get();
			roomSrc = roomDoc.toString();
		}

		return roomSrc.split("room_id\":")[1].split(",")[0];
	}

	/**
	 * 判断是否正在直播
	 * 
	 * @return
	 * @throws IOException
	 */
	public static boolean roomIsAlive() throws IOException {
		Document roomDoc = Jsoup.connect(getRoomUrl()).get();
		roomSrc = roomDoc.toString();

		String status = roomSrc.split("show_status\":")[1].split(",")[0];
		return status.equals("1");
	}

	/**
	 * 获取房间名
	 * 
	 * @return
	 * @throws IOException
	 */
	public static String getRoomName() throws IOException {
		if (roomSrc == null) {
			Document roomDoc = Jsoup.connect(getRoomUrl()).get();
			roomSrc = roomDoc.toString();
		}
		String name = roomSrc.split("room_name\":\"")[1].split("\",")[0];
		return unicode2String(name);
	}

	/**
	 * 获取主播名
	 */
	public static String getOwnerName() throws IOException {
		if (roomSrc == null) {
			Document roomDoc = Jsoup.connect(getRoomUrl()).get();
			roomSrc = roomDoc.toString();
		}

		String name = roomSrc.split("owner_name\":\"")[1].split("\",")[0];
		return unicode2String(name);
	}

	/**
	 * 从配置文件中获得直播url
	 */
	public static String getRoomUrl() throws IOException {
		String url = config.getProperty("url");
		if (url.startsWith("http://")) {
			return url;
		} else {
			return "http://" + url;
		}
	}

	/**
	 * 是否开启海量弹幕
	 */
	public static boolean isSeaMode() {
		return config.getProperty("senMode").equals("true");
	}

	public static String getServerIP() throws IOException {
		return config.getProperty("serverIP");
	}

	public static String getServerPort() throws IOException {
		return config.getProperty("serverPort");
	}

	/**
	 * Md5加密
	 */
	public static String md5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 获得密文
			byte[] md = mdInst.digest(s.getBytes());
			// 把密文转换为十六进制字符串形式
			int j = md.length;
			char[] str = new char[j * 2];
			int k = 0;
			for (byte b : md) {
				str[k++] = hexDigits[b >>> 4 & 0xf];
				str[k++] = hexDigits[b & 0x4f];
			}
			return new String(str).toLowerCase();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将unicode字符串转成中文字符 将每个unicode编码计算出其值，再强转成char类型，然后将这个字符存储到字符串中
	 */
	private static String unicode2String(String str) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < str.length();) {
			if (str.charAt(i) == '\\' && str.charAt(i + 1) == 'u') {
				String unicode = str.substring(i + 2, i + 6);
				// 确定是unicode编码
				if (unicode.matches("[0-9a-fA-F]{4}")) {
					char ch = (char) Integer.parseInt(unicode, 16);
					result.append(ch);
					i += 6;
				} else {
					result.append("\\u");
					i += 2;
				}
			} else {
				result.append(str.charAt(i));
				i++;
			}
		}
		
		return result.toString();
	}
}
