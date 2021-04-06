package com.fy.engineserver.message;

import com.xuanzhi.tools.transport.*;
import java.nio.ByteBuffer;



/**
 * 网络数据包，此数据包是由MessageComplier自动生成，请不要手动修改。<br>
 * 版本号：null<br>
 * 结婚交换戒指<br>
 * 数据包的格式如下：<br><br>
 * <table border="0" cellpadding="0" cellspacing="1" width="100%" bgcolor="#000000" align="center">
 * <tr bgcolor="#00FFFF" align="center"><td>字段名</td><td>数据类型</td><td>长度（字节数）</td><td>说明</td></tr> * <tr bgcolor="#FFFFFF" align="center"><td>length</td><td>int</td><td>getNumOfByteForMessageLength()个字节</td><td>包的整体长度，包头的一部分</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>type</td><td>int</td><td>4个字节</td><td>包的类型，包头的一部分</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>seqNum</td><td>int</td><td>4个字节</td><td>包的序列号，包头的一部分</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>otherPlayerId</td><td>long</td><td>8个字节</td><td>配置的长度</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>selfRingID</td><td>long</td><td>8个字节</td><td>配置的长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>otherRingID</td><td>long</td><td>8个字节</td><td>配置的长度</td></tr>
 * </table>
 */
public class MARRIAGE_JIAOHUAN_RES implements ResponseMessage{

	static GameMessageFactory mf = GameMessageFactory.getInstance();

	long seqNum;
	long otherPlayerId;
	long selfRingID;
	long otherRingID;

	public MARRIAGE_JIAOHUAN_RES(){
	}

	public MARRIAGE_JIAOHUAN_RES(long seqNum,long otherPlayerId,long selfRingID,long otherRingID){
		this.seqNum = seqNum;
		this.otherPlayerId = otherPlayerId;
		this.selfRingID = selfRingID;
		this.otherRingID = otherRingID;
	}

	public MARRIAGE_JIAOHUAN_RES(long seqNum,byte[] content,int offset,int size) throws Exception{
		this.seqNum = seqNum;
		otherPlayerId = (long)mf.byteArrayToNumber(content,offset,8);
		offset += 8;
		selfRingID = (long)mf.byteArrayToNumber(content,offset,8);
		offset += 8;
		otherRingID = (long)mf.byteArrayToNumber(content,offset,8);
		offset += 8;
	}

	public int getType() {
		return 0x70010016;
	}

	public String getTypeDescription() {
		return "MARRIAGE_JIAOHUAN_RES";
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
		len += 8;
		len += 8;
		len += 8;
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

			buffer.putLong(otherPlayerId);
			buffer.putLong(selfRingID);
			buffer.putLong(otherRingID);
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
	 *	对方角色ID
	 */
	public long getOtherPlayerId(){
		return otherPlayerId;
	}

	/**
	 * 设置属性：
	 *	对方角色ID
	 */
	public void setOtherPlayerId(long otherPlayerId){
		this.otherPlayerId = otherPlayerId;
	}

	/**
	 * 获取属性：
	 *	自己戒指的ID
	 */
	public long getSelfRingID(){
		return selfRingID;
	}

	/**
	 * 设置属性：
	 *	自己戒指的ID
	 */
	public void setSelfRingID(long selfRingID){
		this.selfRingID = selfRingID;
	}

	/**
	 * 获取属性：
	 *	对象戒指的ID
	 */
	public long getOtherRingID(){
		return otherRingID;
	}

	/**
	 * 设置属性：
	 *	对象戒指的ID
	 */
	public void setOtherRingID(long otherRingID){
		this.otherRingID = otherRingID;
	}

}