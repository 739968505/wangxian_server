package com.xuanzhi.tools.time;

import com.xuanzhi.tools.transport.*;
import java.nio.ByteBuffer;


/**
 * ��Ϣ�����࣬�˹���������MessageComplier�Զ����ɣ��벻Ҫ�ֶ��޸ġ�<br>
 * ����ʱ�䣺2008-12-28 00:54:06 750<br>
 * �汾�ţ�null<br>
 * �����ݰ��Ķ������£�<br><br>
 * <table border="0" cellpadding="0" cellspacing="1" width="100%" bgcolor="#000000" align="center">
 * <tr bgcolor="#00FFFF" align="center"><td>������Ϣ</td><td>����</td><td>��Ӧ��Ϣ</td><td>����</td><td>˵��</td></tr> * <tr bgcolor="#FFFFFF" align="center"><td>TIME_REQ</td><td>0x00000002</td><td>TIME_RES</td><td>0x80000002</td><td>��˵��</td></tr>
 * </table>
 */
public class TimeMessageFactory extends AbstractMessageFactory {

	private static long sequnceNum = 0L;
	public synchronized static long nextSequnceNum(){
		sequnceNum ++;
		if(sequnceNum >= 0x7FFFFFFF){
			sequnceNum = 1L;
		}
		return sequnceNum;
	}

	protected static TimeMessageFactory self;

	public static TimeMessageFactory getInstance(){
		if(self != null){
			return self;
		}
		synchronized(TimeMessageFactory.class){
			if(self != null) return self;
			self = new TimeMessageFactory();
		}
		return self;
	}

	public Message newMessage(byte[] messageContent,int offset,int size)
		throws MessageFormatErrorException, ConnectionException {
		int len = (int)byteArrayToNumber(messageContent,offset,getNumOfByteForMessageLength());
		if(len != size)
			throw new MessageFormatErrorException("message length not match");
		int end = offset + size;
		offset += getNumOfByteForMessageLength();
		long type = byteArrayToNumber(messageContent,offset,4);
		offset += 4;
		long sn = byteArrayToNumber(messageContent,offset,4);
		offset += 4;

		try{
			if(type == 0x00000002L){
				try {
					return new TIME_REQ(sn,messageContent,offset,end - offset);
				} catch (Exception e) {
					throw new MessageFormatErrorException("construct TIME_REQ error:" + e.getClass() + ":" + e.getMessage());
				}
			}else if(type == 0x80000002L){
				try {
					return new TIME_RES(sn,messageContent,offset,end - offset);
				} catch (Exception e) {
					throw new MessageFormatErrorException("construct TIME_RES error:" + e.getClass() + ":" + e.getMessage());
				}
			}else{
				throw new MessageFormatErrorException("unknown message type ["+type+"]");
			}
		}catch(IndexOutOfBoundsException e){
			throw new ConnectionException("parse message error",e);
		}
	}
}
