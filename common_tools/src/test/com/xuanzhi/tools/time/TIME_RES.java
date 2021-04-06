package com.xuanzhi.tools.time;

import com.xuanzhi.tools.transport.*;
import java.nio.ByteBuffer;



/**
 * �������ݰ��������ݰ�����MessageComplier�Զ����ɣ��벻Ҫ�ֶ��޸ġ�<br>
 * ����ʱ�䣺2008-12-28 00:54:06 765<br>
 * �汾�ţ�null<br>
 * <br>
 * ���ݰ��ĸ�ʽ���£�<br><br>
 * <table border="0" cellpadding="0" cellspacing="1" width="100%" bgcolor="#000000" align="center">
 * <tr bgcolor="#00FFFF" align="center"><td>�ֶ���</td><td>��������</td><td>���ȣ��ֽ�����</td><td>˵��</td></tr> * <tr bgcolor="#FFFFFF" align="center"><td>length</td><td>int</td><td>getNumOfByteForMessageLength()���ֽ�</td><td>�������峤�ȣ���ͷ��һ����</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>type</td><td>int</td><td>4���ֽ�</td><td>�������ͣ���ͷ��һ����</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>seqNum</td><td>int</td><td>4���ֽ�</td><td>�������кţ���ͷ��һ����</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>time</td><td>long</td><td>8���ֽ�</td><td>���õĳ���</td></tr>
 * </table>
 */
public class TIME_RES implements ResponseMessage{

	static TimeMessageFactory mf = TimeMessageFactory.getInstance();

	long seqNum;
	long time;

	public TIME_RES(long seqNum,long time){
		this.seqNum = seqNum;
		this.time = time;
	}

	public TIME_RES(long seqNum,byte[] content,int offset,int size) throws Exception{
		this.seqNum = seqNum;
		time = (long)mf.byteArrayToNumber(content,offset,8);
		offset += 8;
	}

	public int getType() {
		return 0x80000002;
	}

	public String getTypeDescription() {
		return "TIME_RES";
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
		packet_length = len;
		return len;
	}

	public int writeTo(ByteBuffer buffer) {
		int messageLength = getLength();
		if(buffer.remaining() < messageLength) return 0;
		buffer.put(mf.numberToByteArray(messageLength,mf.getNumOfByteForMessageLength()));
		buffer.putInt(getType());
		buffer.putInt((int)seqNum);

			buffer.putLong(time);
		return messageLength;
	}

	/**
	 * ��ȡ���ԣ�
	 *	��ǰʱ��
	 */
	public long getTime(){
		return time;
	}

	/**
	 * �������ԣ�
	 *	��ǰʱ��
	 */
	public void setTime(long time){
		this.time = time;
	}

}