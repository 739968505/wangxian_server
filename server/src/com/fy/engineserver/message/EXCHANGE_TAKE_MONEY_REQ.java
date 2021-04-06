package com.fy.engineserver.message;

import java.nio.ByteBuffer;

import com.xuanzhi.tools.transport.RequestMessage;



/**
 * 网络数据包，此数据包是由MessageComplier自动生成，请不要手动修改。<br>
 * 版本号：null<br>
 * 客户端发送指令取出交易所中的钱,会收到响应，不成功，服务器会通过HINT_REQ指令提示错误，不发送响应<br>
 * 数据包的格式如下：<br><br>
 * <table border="0" cellpadding="0" cellspacing="1" width="100%" bgcolor="#000000" align="center">
 * <tr bgcolor="#00FFFF" align="center"><td>字段名</td><td>数据类型</td><td>长度（字节数）</td><td>说明</td></tr> * <tr bgcolor="#FFFFFF" align="center"><td>length</td><td>int</td><td>getNumOfByteForMessageLength()个字节</td><td>包的整体长度，包头的一部分</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>type</td><td>int</td><td>4个字节</td><td>包的类型，包头的一部分</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>seqNum</td><td>int</td><td>4个字节</td><td>包的序列号，包头的一部分</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>moneyType</td><td>byte</td><td>1个字节</td><td>配置的长度</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>amount</td><td>int</td><td>4个字节</td><td>配置的长度</td></tr>
 * </table>
 */
public class EXCHANGE_TAKE_MONEY_REQ implements RequestMessage{

	static GameMessageFactory mf = GameMessageFactory.getInstance();

	long seqNum;
	byte moneyType;
	int amount;

	public EXCHANGE_TAKE_MONEY_REQ(long seqNum,byte moneyType,int amount){
		this.seqNum = seqNum;
		this.moneyType = moneyType;
		this.amount = amount;
	}

	public EXCHANGE_TAKE_MONEY_REQ(long seqNum,byte[] content,int offset,int size) throws Exception{
		this.seqNum = seqNum;
		moneyType = (byte)mf.byteArrayToNumber(content,offset,1);
		offset += 1;
		amount = (int)mf.byteArrayToNumber(content,offset,4);
		offset += 4;
	}

	public int getType() {
		return 0x00F0EF12;
	}

	public String getTypeDescription() {
		return "EXCHANGE_TAKE_MONEY_REQ";
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

			buffer.put(moneyType);
			buffer.putInt(amount);
		}catch(Exception e){
		 e.printStackTrace();
			buffer.reset();
			throw new RuntimeException("in writeTo method catch exception :",e);
		}
		return messageLength;
	}

	/**
	 * 获取属性：
	 *	0金币，1标识元宝
	 */
	public byte getMoneyType(){
		return moneyType;
	}

	/**
	 * 设置属性：
	 *	0金币，1标识元宝
	 */
	public void setMoneyType(byte moneyType){
		this.moneyType = moneyType;
	}

	/**
	 * 获取属性：
	 *	数量
	 */
	public int getAmount(){
		return amount;
	}

	/**
	 * 设置属性：
	 *	数量
	 */
	public void setAmount(int amount){
		this.amount = amount;
	}

}