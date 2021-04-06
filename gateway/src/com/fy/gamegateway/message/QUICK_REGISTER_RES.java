package com.fy.gamegateway.message;

import com.xuanzhi.tools.transport.*;
import java.nio.ByteBuffer;



/**
 * 网络数据包，此数据包是由MessageComplier自动生成，请不要手动修改。<br>
 * 版本号：null<br>
 * 快速注册<br>
 * 数据包的格式如下：<br><br>
 * <table border="0" cellpadding="0" cellspacing="1" width="100%" bgcolor="#000000" align="center">
 * <tr bgcolor="#00FFFF" align="center"><td>字段名</td><td>数据类型</td><td>长度（字节数）</td><td>说明</td></tr> * <tr bgcolor="#FFFFFF" align="center"><td>length</td><td>int</td><td>getNumOfByteForMessageLength()个字节</td><td>包的整体长度，包头的一部分</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>type</td><td>int</td><td>4个字节</td><td>包的类型，包头的一部分</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>seqNum</td><td>int</td><td>4个字节</td><td>包的序列号，包头的一部分</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>result.length</td><td>short</td><td>2个字节</td><td>字符串实际长度</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>result</td><td>String</td><td>result.length</td><td>字符串对应的byte数组</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>loginUsername.length</td><td>short</td><td>2个字节</td><td>字符串实际长度</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>loginUsername</td><td>String</td><td>loginUsername.length</td><td>字符串对应的byte数组</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>others.length</td><td>int</td><td>4</td><td>数组长度</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>others[0].length</td><td>int</td><td>2</td><td>字符串实际长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>others[0]</td><td>String</td><td>others[0].length</td><td>字符串对应的byte数组</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>others[1].length</td><td>int</td><td>2</td><td>字符串实际长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>others[1]</td><td>String</td><td>others[1].length</td><td>字符串对应的byte数组</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>others[2].length</td><td>int</td><td>2</td><td>字符串实际长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>others[2]</td><td>String</td><td>others[2].length</td><td>字符串对应的byte数组</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td colspan=4>......... 重复</td></tr>
 * </table>
 */
public class QUICK_REGISTER_RES implements ResponseMessage{

	static GameMessageFactory mf = GameMessageFactory.getInstance();

	long seqNum;
	String result;
	String loginUsername;
	String[] others;

	public QUICK_REGISTER_RES(){
	}

	public QUICK_REGISTER_RES(long seqNum,String result,String loginUsername,String[] others){
		this.seqNum = seqNum;
		this.result = result;
		this.loginUsername = loginUsername;
		this.others = others;
	}

	public QUICK_REGISTER_RES(long seqNum,byte[] content,int offset,int size) throws Exception{
		this.seqNum = seqNum;
		int len = 0;
		len = (int)mf.byteArrayToNumber(content,offset,2);
		offset += 2;
		if(len < 0 || len > 16384) throw new Exception("string length ["+len+"] big than the max length [16384]");
		result = new String(content,offset,len,"utf-8");
		offset += len;
		len = (int)mf.byteArrayToNumber(content,offset,2);
		offset += 2;
		if(len < 0 || len > 16384) throw new Exception("string length ["+len+"] big than the max length [16384]");
		loginUsername = new String(content,offset,len,"UTF-8");
		offset += len;
		len = (int)mf.byteArrayToNumber(content,offset,4);
		offset += 4;
		if(len < 0 || len > 4096) throw new Exception("array length ["+len+"] big than the max length [4096]");
		others = new String[len];
		for(int i = 0 ; i < others.length ; i++){
			len = (int)mf.byteArrayToNumber(content,offset,2);
			offset += 2;
			if(len < 0 || len > 16384) throw new Exception("string length ["+len+"] big than the max length [16384]");
			others[i] = new String(content,offset,len);
		offset += len;
		}
	}

	public int getType() {
		return 0x802EE119;
	}

	public String getTypeDescription() {
		return "QUICK_REGISTER_RES";
	}

	public String getSequenceNumAsString() {
		return String.valueOf(seqNum);
	}

	public long getSequnceNum(){
		return seqNum;
	}

	private int packet_length = 0;

	public int getLength() {
		if(packet_length > 0) return packet_length;
		int len =  mf.getNumOfByteForMessageLength() + 4 + 4;
		len += 2;
		try{
			len +=result.getBytes("utf-8").length;
		}catch(java.io.UnsupportedEncodingException e){
		 e.printStackTrace();
			throw new RuntimeException("unsupported encoding [utf-8]",e);
		}
		len += 2;
		try{
			len +=loginUsername.getBytes("UTF-8").length;
		}catch(java.io.UnsupportedEncodingException e){
		 e.printStackTrace();
			throw new RuntimeException("unsupported encoding [UTF-8]",e);
		}
		len += 4;
		for(int i = 0 ; i < others.length; i++){
			len += 2;
			len += others[i].getBytes().length;
		}
		packet_length = len;
		return len;
	}

	public int writeTo(ByteBuffer buffer) {
		int messageLength = getLength();
		if(buffer.remaining() < messageLength) return 0;
		int oldPos = buffer.position();
		buffer.mark();
		try{
			buffer.put(mf.numberToByteArray(messageLength,mf.getNumOfByteForMessageLength()));
			buffer.putInt(getType());
			buffer.putInt((int)seqNum);

			byte[] tmpBytes1;
				try{
			 tmpBytes1 = result.getBytes("utf-8");
				}catch(java.io.UnsupportedEncodingException e){
			 e.printStackTrace();
					throw new RuntimeException("unsupported encoding [utf-8]",e);
				}
			buffer.putShort((short)tmpBytes1.length);
			buffer.put(tmpBytes1);
				try{
			tmpBytes1 = loginUsername.getBytes("UTF-8");
				}catch(java.io.UnsupportedEncodingException e){
			 e.printStackTrace();
					throw new RuntimeException("unsupported encoding [UTF-8]",e);
				}
			buffer.putShort((short)tmpBytes1.length);
			buffer.put(tmpBytes1);
			buffer.putInt(others.length);
			for(int i = 0 ; i < others.length; i++){
				byte[] tmpBytes2 ;
				tmpBytes2 = others[i].getBytes();
				buffer.putShort((short)tmpBytes2.length);
				buffer.put(tmpBytes2);
			}
		}catch(Exception e){
		 e.printStackTrace();
			buffer.reset();
			throw new RuntimeException("in writeTo method catch exception :",e);
		}
		int newPos = buffer.position();
		buffer.position(oldPos);
		buffer.put(mf.numberToByteArray(newPos-oldPos,mf.getNumOfByteForMessageLength()));
		buffer.position(newPos);
		return newPos-oldPos;
	}

	/**
	 * 获取属性：
	 *	ok串为成功
	 */
	public String getResult(){
		return result;
	}

	/**
	 * 设置属性：
	 *	ok串为成功
	 */
	public void setResult(String result){
		this.result = result;
	}

	/**
	 * 获取属性：
	 *	登录用户名，此用户名可能与注册的username不一样
	 */
	public String getLoginUsername(){
		return loginUsername;
	}

	/**
	 * 设置属性：
	 *	登录用户名，此用户名可能与注册的username不一样
	 */
	public void setLoginUsername(String loginUsername){
		this.loginUsername = loginUsername;
	}

	/**
	 * 获取属性：
	 *	其他信息
	 */
	public String[] getOthers(){
		return others;
	}

	/**
	 * 设置属性：
	 *	其他信息
	 */
	public void setOthers(String[] others){
		this.others = others;
	}

}