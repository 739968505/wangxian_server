package com.fy.engineserver.message;

import com.xuanzhi.tools.transport.*;
import java.nio.ByteBuffer;



/**
 * 网络数据包，此数据包是由MessageComplier自动生成，请不要手动修改。<br>
 * 版本号：null<br>
 * 新充值活动奖励领取<br>
 * 数据包的格式如下：<br><br>
 * <table border="0" cellpadding="0" cellspacing="1" width="100%" bgcolor="#000000" align="center">
 * <tr bgcolor="#00FFFF" align="center"><td>字段名</td><td>数据类型</td><td>长度（字节数）</td><td>说明</td></tr> * <tr bgcolor="#FFFFFF" align="center"><td>length</td><td>int</td><td>getNumOfByteForMessageLength()个字节</td><td>包的整体长度，包头的一部分</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>type</td><td>int</td><td>4个字节</td><td>包的类型，包头的一部分</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>seqNum</td><td>int</td><td>4个字节</td><td>包的序列号，包头的一部分</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>isSuccess</td><td>boolean</td><td>1个字节</td><td>布尔型长度,0==false，其他为true</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>dataID</td><td>int</td><td>4个字节</td><td>配置的长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>dataType</td><td>int</td><td>4个字节</td><td>配置的长度</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>result.length</td><td>short</td><td>2个字节</td><td>字符串实际长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>result</td><td>String</td><td>result.length</td><td>字符串对应的byte数组</td></tr>
 * </table>
 */
public class GET_WEEK_REWARD_RES implements ResponseMessage{

	static GameMessageFactory mf = GameMessageFactory.getInstance();

	long seqNum;
	boolean isSuccess;
	int dataID;
	int dataType;
	String result;

	public GET_WEEK_REWARD_RES(){
	}

	public GET_WEEK_REWARD_RES(long seqNum,boolean isSuccess,int dataID,int dataType,String result){
		this.seqNum = seqNum;
		this.isSuccess = isSuccess;
		this.dataID = dataID;
		this.dataType = dataType;
		this.result = result;
	}

	public GET_WEEK_REWARD_RES(long seqNum,byte[] content,int offset,int size) throws Exception{
		this.seqNum = seqNum;
		isSuccess = mf.byteArrayToNumber(content,offset,1) != 0;
		offset += 1;
		dataID = (int)mf.byteArrayToNumber(content,offset,4);
		offset += 4;
		dataType = (int)mf.byteArrayToNumber(content,offset,4);
		offset += 4;
		int len = 0;
		len = (int)mf.byteArrayToNumber(content,offset,2);
		offset += 2;
		if(len < 0 || len > 16384) throw new Exception("string length ["+len+"] big than the max length [16384]");
		result = new String(content,offset,len,"UTF-8");
		offset += len;
	}

	public int getType() {
		return 0x700EB106;
	}

	public String getTypeDescription() {
		return "GET_WEEK_REWARD_RES";
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
		len += 1;
		len += 4;
		len += 4;
		len += 2;
		try{
			len +=result.getBytes("UTF-8").length;
		}catch(java.io.UnsupportedEncodingException e){
		 e.printStackTrace();
			throw new RuntimeException("unsupported encoding [UTF-8]",e);
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

			buffer.put((byte)(isSuccess==false?0:1));
			buffer.putInt(dataID);
			buffer.putInt(dataType);
			byte[] tmpBytes1;
				try{
			 tmpBytes1 = result.getBytes("UTF-8");
				}catch(java.io.UnsupportedEncodingException e){
			 e.printStackTrace();
					throw new RuntimeException("unsupported encoding [UTF-8]",e);
				}
			buffer.putShort((short)tmpBytes1.length);
			buffer.put(tmpBytes1);
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
	 *	是否成功
	 */
	public boolean getIsSuccess(){
		return isSuccess;
	}

	/**
	 * 设置属性：
	 *	是否成功
	 */
	public void setIsSuccess(boolean isSuccess){
		this.isSuccess = isSuccess;
	}

	/**
	 * 获取属性：
	 *	活动ID
	 */
	public int getDataID(){
		return dataID;
	}

	/**
	 * 设置属性：
	 *	活动ID
	 */
	public void setDataID(int dataID){
		this.dataID = dataID;
	}

	/**
	 * 获取属性：
	 *	活动类型  1今天 8短的那个天 9长的那个天
	 */
	public int getDataType(){
		return dataType;
	}

	/**
	 * 设置属性：
	 *	活动类型  1今天 8短的那个天 9长的那个天
	 */
	public void setDataType(int dataType){
		this.dataType = dataType;
	}

	/**
	 * 获取属性：
	 *	如果失败，里面是提示
	 */
	public String getResult(){
		return result;
	}

	/**
	 * 设置属性：
	 *	如果失败，里面是提示
	 */
	public void setResult(String result){
		this.result = result;
	}

}