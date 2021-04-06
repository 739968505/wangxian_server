package com.fy.engineserver.message;

import com.xuanzhi.tools.transport.*;
import java.nio.ByteBuffer;



/**
 * 网络数据包，此数据包是由MessageComplier自动生成，请不要手动修改。<br>
 * 版本号：null<br>
 * <br>
 * 数据包的格式如下：<br><br>
 * <table border="0" cellpadding="0" cellspacing="1" width="100%" bgcolor="#000000" align="center">
 * <tr bgcolor="#00FFFF" align="center"><td>字段名</td><td>数据类型</td><td>长度（字节数）</td><td>说明</td></tr> * <tr bgcolor="#FFFFFF" align="center"><td>length</td><td>int</td><td>getNumOfByteForMessageLength()个字节</td><td>包的整体长度，包头的一部分</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>type</td><td>int</td><td>4个字节</td><td>包的类型，包头的一部分</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>seqNum</td><td>int</td><td>4个字节</td><td>包的序列号，包头的一部分</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>equipmentId</td><td>long</td><td>8个字节</td><td>配置的长度</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>qilingType.length</td><td>int</td><td>4</td><td>数组长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>qilingType</td><td>int[]</td><td>qilingType.length</td><td>*</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>qilingIds.length</td><td>int</td><td>4</td><td>数组长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>qilingIds</td><td>long[]</td><td>qilingIds.length</td><td>*</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>description.length</td><td>short</td><td>2个字节</td><td>字符串实际长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>description</td><td>String</td><td>description.length</td><td>字符串对应的byte数组</td></tr>
 * </table>
 */
public class QUERY_EQUIPMENT_QILING_RES implements ResponseMessage{

	static GameMessageFactory mf = GameMessageFactory.getInstance();

	long seqNum;
	long equipmentId;
	int[] qilingType;
	long[] qilingIds;
	String description;

	public QUERY_EQUIPMENT_QILING_RES(){
	}

	public QUERY_EQUIPMENT_QILING_RES(long seqNum,long equipmentId,int[] qilingType,long[] qilingIds,String description){
		this.seqNum = seqNum;
		this.equipmentId = equipmentId;
		this.qilingType = qilingType;
		this.qilingIds = qilingIds;
		this.description = description;
	}

	public QUERY_EQUIPMENT_QILING_RES(long seqNum,byte[] content,int offset,int size) throws Exception{
		this.seqNum = seqNum;
		equipmentId = (long)mf.byteArrayToNumber(content,offset,8);
		offset += 8;
		int len = 0;
		len = (int)mf.byteArrayToNumber(content,offset,4);
		offset += 4;
		if(len < 0 || len > 4096) throw new Exception("array length ["+len+"] big than the max length [4096]");
		qilingType = new int[len];
		for(int i = 0 ; i < qilingType.length ; i++){
			qilingType[i] = (int)mf.byteArrayToNumber(content,offset,4);
			offset += 4;
		}
		len = (int)mf.byteArrayToNumber(content,offset,4);
		offset += 4;
		if(len < 0 || len > 4096) throw new Exception("array length ["+len+"] big than the max length [4096]");
		qilingIds = new long[len];
		for(int i = 0 ; i < qilingIds.length ; i++){
			qilingIds[i] = (long)mf.byteArrayToNumber(content,offset,8);
			offset += 8;
		}
		len = (int)mf.byteArrayToNumber(content,offset,2);
		offset += 2;
		if(len < 0 || len > 16384) throw new Exception("string length ["+len+"] big than the max length [16384]");
		description = new String(content,offset,len,"UTF-8");
		offset += len;
	}

	public int getType() {
		return 0x70EEAABC;
	}

	public String getTypeDescription() {
		return "QUERY_EQUIPMENT_QILING_RES";
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
		len += 4;
		len += qilingType.length * 4;
		len += 4;
		len += qilingIds.length * 8;
		len += 2;
		try{
			len +=description.getBytes("UTF-8").length;
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

			buffer.putLong(equipmentId);
			buffer.putInt(qilingType.length);
			for(int i = 0 ; i < qilingType.length; i++){
				buffer.putInt(qilingType[i]);
			}
			buffer.putInt(qilingIds.length);
			for(int i = 0 ; i < qilingIds.length; i++){
				buffer.putLong(qilingIds[i]);
			}
			byte[] tmpBytes1;
				try{
			 tmpBytes1 = description.getBytes("UTF-8");
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
	 *	要查询装备id
	 */
	public long getEquipmentId(){
		return equipmentId;
	}

	/**
	 * 设置属性：
	 *	要查询装备id
	 */
	public void setEquipmentId(long equipmentId){
		this.equipmentId = equipmentId;
	}

	/**
	 * 获取属性：
	 *	装备拥有的器灵孔的类型
	 */
	public int[] getQilingType(){
		return qilingType;
	}

	/**
	 * 设置属性：
	 *	装备拥有的器灵孔的类型
	 */
	public void setQilingType(int[] qilingType){
		this.qilingType = qilingType;
	}

	/**
	 * 获取属性：
	 *	装备拥有的器灵孔中的器灵id
	 */
	public long[] getQilingIds(){
		return qilingIds;
	}

	/**
	 * 设置属性：
	 *	装备拥有的器灵孔中的器灵id
	 */
	public void setQilingIds(long[] qilingIds){
		this.qilingIds = qilingIds;
	}

	/**
	 * 获取属性：
	 *	装备上器灵信息描述
	 */
	public String getDescription(){
		return description;
	}

	/**
	 * 设置属性：
	 *	装备上器灵信息描述
	 */
	public void setDescription(String description){
		this.description = description;
	}

}