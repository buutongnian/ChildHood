package edu.buu.childhood.util;

import java.io.ByteArrayOutputStream;

public class EncryptionDes {

	private static final String hexString = "1234567890abcdef";

	public static String encode(String encipheredData) {
		// 根据默认编码获取字节数组
		byte[] bytes = encipheredData.getBytes();
		StringBuilder sb = new StringBuilder(bytes.length * 2);

		// 将字节数组中每个字节拆解成2位16进制整数
		for (int i = 0; i < bytes.length; i++) {
			sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
			sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString();
	}

	public static String decode(String bytes) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length() / 2);
		// 将每2位16进制整数组装成一个字节
		for (int i = 0; i < bytes.length() - 1; i++)
			if (i % 2 == 0) {
				baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString.indexOf(bytes.charAt(i + 1))));
			}
		return new String(baos.toByteArray());
	}
}
