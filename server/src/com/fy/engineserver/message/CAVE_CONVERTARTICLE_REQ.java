package com.fy.engineserver.message;

import java.nio.ByteBuffer;

import com.xuanzhi.tools.transport.RequestMessage;



/**
 * 网络数据包，此数据包是由MessageComplier自动生成，请不要手动修改。<br>
 * 版本号：null<br>
 * 查看可兑换道具的列表<br>
 * 数据包的格式如下：<br><br>
 * <table border="0" cellpadding="0" cellspacing="1" width="100%" bgcolor="#000000" align="center">
 * <tr bgcolor="#00FFFF" align="center"><td>字段名</td><td>数据类型</td><td>长度（字节数）</td><td>说明</td></tr> * <tr bgcolor="#FFFFFF" align="center"><td>length</td><td>int</td><td>getNumOfByteForMessageLength()个字节</td><td>包的整体长度，包头的一部分</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>type</td><td>int</td><td>4个字节</td><td>包的类型，包头的一部分</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>seqNum</td><td>int</td><td>4个字节</td><td>包的序列号，包头的一部分</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>id</td><td>int</td><td>4个字节</td><td>配置的长度</td></tr>
 * </table>
 */
public class CAVE_CONVERTARTICLE_REQ implements RequestMessage{

	static GameMessageFactory mf = GameMessageFactory.getInstance();

	long seqNum;
	int id;

	public CAVE_CONVERTARTICLE_REQ(long seqNum,int id){
		this.seqNum = seqNum;
		this.id = id;
	}

	public CAVE_CONVERTARTICLE_REQ(long seqNum,byte[] content,int offset,int size) throws Exception{
		this.seqNum = seqNum;
		id = (int)mf.byteArrayToNumber(content,offset,4);
		offset += 4;
	}

	public int getType() {
		return 0x0F000024;
	}

	public String getTypeDescription() {
		return "CAVE_CONVERTARTICLE_REQ";
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
		len += 4;
		packet_length = len;
		return len;
	}

	public int writeTo(ByteBuffer buffer) {
		int messageLength = getLength();
		if(buffer.remaining() < messageLength) return 0;
		buffer.mark();
		try{
			buffer.put(mf.numberToByteArray(messageLength,mf.getNumOfByteForMessageLength()));
			buffer.putInt(getType());
			buffer.putInt((int)seqNum);

			buffer.putInt(id);
		}catch(Exception e){
		 e.printStackTrace();
			buffer.reset();
			throw new RuntimeException("in writeTo method catch exception :",e);
		}
		return messageLength;
	}

	/**
	 * 获取属性：
	 *	id
	 */
	public int getId(){
		return id;
	}

	/**
	 * 设置属性：
	 *	id
	 */
	public void setId(int id){
		this.id = id;
	}

}