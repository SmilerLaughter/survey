package survey.util;

import java.security.MessageDigest;

public class MD5 {

	/**
	 * ���ܣ���������ת��Ϊbyte ���飬Ȼ��ת��Ϊ 16 �ֽڵ�byte���飬ͨ��һ��byte 8 λ�ĸߴ�λ �� �ʹ�λ ��ת��Ϊ��Ӧ���ַ� 
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static String getMd5(String password ) throws Exception{
		StringBuffer buffer = new StringBuffer();
		char[] chars = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		byte[] bytes = password.getBytes();
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[]  targ = md.digest(bytes);//16�ֽ�
		for(byte b : targ){
			buffer.append(chars[ ( b >> 4 ) &  0x0F] );//�ߴ�λת��
			buffer.append(chars[b & 0x0F] );//�ʹ�λת��
		}
		
		return buffer.toString();
	}
}
