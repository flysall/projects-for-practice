package flysall.douyu.douyu;

import java.io.*;
import java.util.Arrays;

/**
 * 请求信息包含五部分 1.后四部分的字节长度，占用4个字节 2.同上 3.请求代码，
 * 固定为：斗鱼是0xb1,0x02,0x00,0x00,接收是0xb2,0x02,0x00,0x00，4个字节 4.消息正文 5.尾部一个空字节
 */
public class Message {
	// 对应上述五部分
	private int[] length1;
	private int[] length2;
	private int[] magic;
	private String content;
	private int[] end;

	public Message(String content) {
		length1 = new int[] { calMessageLength(content), 0x00, 0x00, 0x00 };
		length2 = new int[] { calMessageLength(content), 0x00, 0x00, 0x00 };
		magic = new int[] { 0xb1, 0x02, 0x00, 0x00 };
		this.content = content;
		end = new int[] { 0x00 };
	}

	/**
	 * 计算消息体长度
	 */
	private int calMessageLength(String content) {
		return 8 + (content == null ? 0 : content.length()) + 1;
	}

	@Override
	public String toString() {
		return "Message{" + "length1=" + Arrays.toString(length1) + ", length2=" + Arrays.toString(length2) + ", magic="
				+ Arrays.toString(magic) + ", content='" + content + '\'' + ", end=" + Arrays.toString(end) + '}';
	}

	/**
	 * 将message对象转换为字节数组
	 */
	public byte[] getBytes() throws IOException {
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		for (int b : length1)
			byteArray.write(b);
		for (int b : length2)
			byteArray.write(b);
		for (int b : magic)
			byteArray.write(b);
		if (content != null) {
			byteArray.write(content.getBytes("ISO-8859-1"));
		}
		for (int b : end)
			byteArray.write(b);

		return byteArray.toByteArray();
	}
}
