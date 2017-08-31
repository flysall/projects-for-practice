package flysall.panda.panda;

import java.io.IOException;
import java.util.Properties;

public class Utils {
	private static Properties config = new Properties();

	static {
		try {
			config.load(Utils.class.getResourceAsStream("/config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getRoomId() throws IOException {
		return config.getProperty("roomId");
	}

	/**
	 * 将unicode字符串转换成中文字符串 将unicode编码计算其值， 强转为char类型，将该字符存贮到字符串中
	 */
	public static String unicode2String(String str) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < str.length();) {
			if (str.charAt(i) == '\\' && str.charAt(i + 1) == 'u') {
				String unicode = str.substring(i + 2, i + 6);
				// 确定是unicode编码
				if (unicode.matches("[0-9a-fA-F]{4}")) {
					// 将得到的数值按照16进制解析为十进制，再强转为字符串
					char ch = (char) Integer.parseInt(unicode, 16);
					result.append(ch);
					i += 6;
				} else {
					result.append("\\U");
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
