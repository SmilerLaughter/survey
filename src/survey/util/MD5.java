package survey.util;

import java.security.MessageDigest;

public class MD5 {

	/**
	 * 加密：把密码先转换为byte 数组，然后转换为 16 字节的byte数组，通过一个byte 8 位的高次位 和 低次位 的转换为对应的字符 
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static String getMd5(String password ) throws Exception{
		StringBuffer buffer = new StringBuffer();
		char[] chars = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		byte[] bytes = password.getBytes();
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[]  targ = md.digest(bytes);//16字节
		for(byte b : targ){
			buffer.append(chars[ ( b >> 4 ) &  0x0F] );//高次位转换
			buffer.append(chars[b & 0x0F] );//低次位转换
		}
		
		return buffer.toString();
	}
}
