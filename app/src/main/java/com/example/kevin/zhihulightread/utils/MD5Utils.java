package com.example.kevin.zhihulightread.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

	/**
	 * md5加密
	 * 
	 * @param password
	 * @return
	 */
	public static String encode(String password) {
		try {
			MessageDigest instance = MessageDigest.getInstance("MD5");// 获取MD5算法对象
			byte[] digest = instance.digest(password.getBytes());// 对字符串加密,返回字节数组

			StringBuffer sb = new StringBuffer();
			for (byte b : digest) {
				int i = b & 0xff;// 获取字节的低八位有效值
				String hexString = Integer.toHexString(i);// 将整数转为16进制

				if (hexString.length() < 2) {
					hexString = "0" + hexString;// 如果是1位的话,补0
				}

				sb.append(hexString);
			}

			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			// 没有该算法时,抛出异常, 不会走到这里
		}

		return "";
	}

	public static String getFileMD5(String sourceDir) {

		File file = new File(sourceDir);

		try {
			FileInputStream fis = new FileInputStream(file);

			byte[] buffer = new byte[1024];
			int len = -1;

			while((len = fis.read(buffer)) != -1){
				MessageDigest instance = MessageDigest.getInstance("MD5");// 获取MD5算法对象
				byte[] digest = instance.digest(buffer);// 对字符串加密,返回字节数组

				StringBuffer sb = new StringBuffer();
				for (byte b : digest) {
					int i = b & 0xff;// 获取字节的低八位有效值
					String hexString = Integer.toHexString(i);// 将整数转为16进制

					if (hexString.length() < 2) {
						hexString = "0" + hexString;// 如果是1位的话,补0
					}

					sb.append(hexString);
				}

				return sb.toString();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}
}
