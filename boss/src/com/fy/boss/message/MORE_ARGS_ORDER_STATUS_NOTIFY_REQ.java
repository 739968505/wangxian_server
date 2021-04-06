package com.fy.boss.message;

import com.fy.boss.message.BossMessageFactory;
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
 * <tr bgcolor="#FAFAFA" align="center"><td>orderId.length</td><td>short</td><td>2个字节</td><td>字符串实际长度</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>orderId</td><td>String</td><td>orderId.length</td><td>字符串对应的byte数组</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>channel.length</td><td>short</td><td>2个字节</td><td>字符串实际长度</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>channel</td><td>String</td><td>channel.length</td><td>字符串对应的byte数组</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>handlestatus.length</td><td>short</td><td>2个字节</td><td>字符串实际长度</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>handlestatus</td><td>String</td><td>handlestatus.length</td><td>字符串对应的byte数组</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>handlestatusDesp.length</td><td>short</td><td>2个字节</td><td>字符串实际长度</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>handlestatusDesp</td><td>String</td><td>handlestatusDesp.length</td><td>字符串对应的byte数组</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>responsestatus.length</td><td>short</td><td>2个字节</td><td>字符串实际长度</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>responsestatus</td><td>String</td><td>responsestatus.length</td><td>字符串对应的byte数组</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>responsestatusDesp.length</td><td>short</td><td>2个字节</td><td>字符串实际长度</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>responsestatusDesp</td><td>String</td><td>responsestatusDesp.length</td><td>字符串对应的byte数组</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>zoneid.length</td><td>short</td><td>2个字节</td><td>字符串实际长度</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>zoneid</td><td>String</td><td>zoneid.length</td><td>字符串对应的byte数组</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>pfkey.length</td><td>short</td><td>2个字节</td><td>字符串实际长度</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>pfkey</td><td>String</td><td>pfkey.length</td><td>字符串对应的byte数组</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>pay_token.length</td><td>short</td><td>2个字节</td><td>字符串实际长度</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>pay_token</td><td>String</td><td>pay_token.length</td><td>字符串对应的byte数组</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>args.length</td><td>int</td><td>4</td><td>数组长度</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>args[0].length</td><td>int</td><td>2</td><td>字符串实际长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>args[0]</td><td>String</td><td>args[0].length</td><td>字符串对应的byte数组</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>args[1].length</td><td>int</td><td>2</td><td>字符串实际长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>args[1]</td><td>String</td><td>args[1].length</td><td>字符串对应的byte数组</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td>args[2].length</td><td>int</td><td>2</td><td>字符串实际长度</td></tr>
 * <tr bgcolor="#FAFAFA" align="center"><td>args[2]</td><td>String</td><td>args[2].length</td><td>字符串对应的byte数组</td></tr>
 * <tr bgcolor="#FFFFFF" align="center"><td colspan=4>......... 重复</td></tr>
 * </table>
 */
public class MORE_ARGS_ORDER_STATUS_NOTIFY_REQ implements RequestMessage{

	static BossMessageFactory mf = BossMessageFactory.getInstance();

	long seqNum;
	String orderId;
	String channel;
	String handlestatus;
	String handlestatusDesp;
	String responsestatus;
	String responsestatusDesp;
	String zoneid;
	String pfkey;
	String pay_token;
	String[] args;

	public MORE_ARGS_ORDER_STATUS_NOTIFY_REQ(){
	}

	public MORE_ARGS_ORDER_STATUS_NOTIFY_REQ(long seqNum,String orderId,String channel,String handlestatus,String handlestatusDesp,String responsestatus,String responsestatusDesp,String zoneid,String pfkey,String pay_token,String[] args){
		this.seqNum = seqNum;
		this.orderId = orderId;
		this.channel = channel;
		this.handlestatus = handlestatus;
		this.handlestatusDesp = handlestatusDesp;
		this.responsestatus = responsestatus;
		this.responsestatusDesp = responsestatusDesp;
		this.zoneid = zoneid;
		this.pfkey = pfkey;
		this.pay_token = pay_token;
		this.args = args;
	}

	public MORE_ARGS_ORDER_STATUS_NOTIFY_REQ(long seqNum,byte[] content,int offset,int size) throws Exception{
		this.seqNum = seqNum;
		int len = 0;
		len = (int)mf.byteArrayToNumber(content,offset,2);
		offset += 2;
		if(len < 0 || len > 10240000) throw new Exception("string length ["+len+"] big than the max length [10240000]");
		orderId = new String(content,offset,len,"UTF-8");
		offset += len;
		len = (int)mf.byteArrayToNumber(content,offset,2);
		offset += 2;
		if(len < 0 || len > 10240000) throw new Exception("string length ["+len+"] big than the max length [10240000]");
		channel = new String(content,offset,len,"UTF-8");
		offset += len;
		len = (int)mf.byteArrayToNumber(content,offset,2);
		offset += 2;
		if(len < 0 || len > 10240000) throw new Exception("string length ["+len+"] big than the max length [10240000]");
		handlestatus = new String(content,offset,len,"UTF-8");
		offset += len;
		len = (int)mf.byteArrayToNumber(content,offset,2);
		offset += 2;
		if(len < 0 || len > 10240000) throw new Exception("string length ["+len+"] big than the max length [10240000]");
		handlestatusDesp = new String(content,offset,len,"UTF-8");
		offset += len;
		len = (int)mf.byteArrayToNumber(content,offset,2);
		offset += 2;
		if(len < 0 || len > 10240000) throw new Exception("string length ["+len+"] big than the max length [10240000]");
		responsestatus = new String(content,offset,len,"UTF-8");
		offset += len;
		len = (int)mf.byteArrayToNumber(content,offset,2);
		offset += 2;
		if(len < 0 || len > 10240000) throw new Exception("string length ["+len+"] big than the max length [10240000]");
		responsestatusDesp = new String(content,offset,len,"UTF-8");
		offset += len;
		len = (int)mf.byteArrayToNumber(content,offset,2);
		offset += 2;
		if(len < 0 || len > 10240000) throw new Exception("string length ["+len+"] big than the max length [10240000]");
		zoneid = new String(content,offset,len,"UTF-8");
		offset += len;
		len = (int)mf.byteArrayToNumber(content,offset,2);
		offset += 2;
		if(len < 0 || len > 10240000) throw new Exception("string length ["+len+"] big than the max length [10240000]");
		pfkey = new String(content,offset,len,"UTF-8");
		offset += len;
		len = (int)mf.byteArrayToNumber(content,offset,2);
		offset += 2;
		if(len < 0 || len > 10240000) throw new Exception("string length ["+len+"] big than the max length [10240000]");
		pay_token = new String(content,offset,len,"UTF-8");
		offset += len;
		len = (int)mf.byteArrayToNumber(content,offset,4);
		offset += 4;
		if(len < 0 || len > 5024000) throw new Exception("array length ["+len+"] big than the max length [5024000]");
		args = new String[len];
		for(int i = 0 ; i < args.length ; i++){
			len = (int)mf.byteArrayToNumber(content,offset,2);
			offset += 2;
			if(len < 0 || len > 10240000) throw new Exception("string length ["+len+"] big than the max length [10240000]");
			args[i] = new String(content,offset,len,"UTF-8");
		offset += len;
		}
	}

	public int getType() {
		return 0x0000F039;
	}

	public String getTypeDescription() {
		return "MORE_ARGS_ORDER_STATUS_NOTIFY_REQ";
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
			len +=orderId.getBytes("UTF-8").length;
		}catch(java.io.UnsupportedEncodingException e){
		 e.printStackTrace();
			throw new RuntimeException("unsupported encoding [UTF-8]",e);
		}
		len += 2;
		try{
			len +=channel.getBytes("UTF-8").length;
		}catch(java.io.UnsupportedEncodingException e){
		 e.printStackTrace();
			throw new RuntimeException("unsupported encoding [UTF-8]",e);
		}
		len += 2;
		try{
			len +=handlestatus.getBytes("UTF-8").length;
		}catch(java.io.UnsupportedEncodingException e){
		 e.printStackTrace();
			throw new RuntimeException("unsupported encoding [UTF-8]",e);
		}
		len += 2;
		try{
			len +=handlestatusDesp.getBytes("UTF-8").length;
		}catch(java.io.UnsupportedEncodingException e){
		 e.printStackTrace();
			throw new RuntimeException("unsupported encoding [UTF-8]",e);
		}
		len += 2;
		try{
			len +=responsestatus.getBytes("UTF-8").length;
		}catch(java.io.UnsupportedEncodingException e){
		 e.printStackTrace();
			throw new RuntimeException("unsupported encoding [UTF-8]",e);
		}
		len += 2;
		try{
			len +=responsestatusDesp.getBytes("UTF-8").length;
		}catch(java.io.UnsupportedEncodingException e){
		 e.printStackTrace();
			throw new RuntimeException("unsupported encoding [UTF-8]",e);
		}
		len += 2;
		try{
			len +=zoneid.getBytes("UTF-8").length;
		}catch(java.io.UnsupportedEncodingException e){
		 e.printStackTrace();
			throw new RuntimeException("unsupported encoding [UTF-8]",e);
		}
		len += 2;
		try{
			len +=pfkey.getBytes("UTF-8").length;
		}catch(java.io.UnsupportedEncodingException e){
		 e.printStackTrace();
			throw new RuntimeException("unsupported encoding [UTF-8]",e);
		}
		len += 2;
		try{
			len +=pay_token.getBytes("UTF-8").length;
		}catch(java.io.UnsupportedEncodingException e){
		 e.printStackTrace();
			throw new RuntimeException("unsupported encoding [UTF-8]",e);
		}
		len += 4;
		for(int i = 0 ; i < args.length; i++){
			len += 2;
			try{
				len += args[i].getBytes("UTF-8").length;
			}catch(java.io.UnsupportedEncodingException e){
		 e.printStackTrace();
				throw new RuntimeException("unsupported encoding [UTF-8]",e);
			}
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
			 tmpBytes1 = orderId.getBytes("UTF-8");
				}catch(java.io.UnsupportedEncodingException e){
			 e.printStackTrace();
					throw new RuntimeException("unsupported encoding [UTF-8]",e);
				}
			buffer.putShort((short)tmpBytes1.length);
			buffer.put(tmpBytes1);
				try{
			tmpBytes1 = channel.getBytes("UTF-8");
				}catch(java.io.UnsupportedEncodingException e){
			 e.printStackTrace();
					throw new RuntimeException("unsupported encoding [UTF-8]",e);
				}
			buffer.putShort((short)tmpBytes1.length);
			buffer.put(tmpBytes1);
				try{
			tmpBytes1 = handlestatus.getBytes("UTF-8");
				}catch(java.io.UnsupportedEncodingException e){
			 e.printStackTrace();
					throw new RuntimeException("unsupported encoding [UTF-8]",e);
				}
			buffer.putShort((short)tmpBytes1.length);
			buffer.put(tmpBytes1);
				try{
			tmpBytes1 = handlestatusDesp.getBytes("UTF-8");
				}catch(java.io.UnsupportedEncodingException e){
			 e.printStackTrace();
					throw new RuntimeException("unsupported encoding [UTF-8]",e);
				}
			buffer.putShort((short)tmpBytes1.length);
			buffer.put(tmpBytes1);
				try{
			tmpBytes1 = responsestatus.getBytes("UTF-8");
				}catch(java.io.UnsupportedEncodingException e){
			 e.printStackTrace();
					throw new RuntimeException("unsupported encoding [UTF-8]",e);
				}
			buffer.putShort((short)tmpBytes1.length);
			buffer.put(tmpBytes1);
				try{
			tmpBytes1 = responsestatusDesp.getBytes("UTF-8");
				}catch(java.io.UnsupportedEncodingException e){
			 e.printStackTrace();
					throw new RuntimeException("unsupported encoding [UTF-8]",e);
				}
			buffer.putShort((short)tmpBytes1.length);
			buffer.put(tmpBytes1);
				try{
			tmpBytes1 = zoneid.getBytes("UTF-8");
				}catch(java.io.UnsupportedEncodingException e){
			 e.printStackTrace();
					throw new RuntimeException("unsupported encoding [UTF-8]",e);
				}
			buffer.putShort((short)tmpBytes1.length);
			buffer.put(tmpBytes1);
				try{
			tmpBytes1 = pfkey.getBytes("UTF-8");
				}catch(java.io.UnsupportedEncodingException e){
			 e.printStackTrace();
					throw new RuntimeException("unsupported encoding [UTF-8]",e);
				}
			buffer.putShort((short)tmpBytes1.length);
			buffer.put(tmpBytes1);
				try{
			tmpBytes1 = pay_token.getBytes("UTF-8");
				}catch(java.io.UnsupportedEncodingException e){
			 e.printStackTrace();
					throw new RuntimeException("unsupported encoding [UTF-8]",e);
				}
			buffer.putShort((short)tmpBytes1.length);
			buffer.put(tmpBytes1);
			buffer.putInt(args.length);
			for(int i = 0 ; i < args.length; i++){
				byte[] tmpBytes2 ;
				try{
				tmpBytes2 = args[i].getBytes("UTF-8");
				}catch(java.io.UnsupportedEncodingException e){
			 e.printStackTrace();
					throw new RuntimeException("unsupported encoding [UTF-8]",e);
				}
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
	 *	订单号
	 */
	public String getOrderId(){
		return orderId;
	}

	/**
	 * 设置属性：
	 *	订单号
	 */
	public void setOrderId(String orderId){
		this.orderId = orderId;
	}

	/**
	 * 获取属性：
	 *	渠道串
	 */
	public String getChannel(){
		return channel;
	}

	/**
	 * 设置属性：
	 *	渠道串
	 */
	public void setChannel(String channel){
		this.channel = channel;
	}

	/**
	 * 获取属性：
	 *	调用接口的状态
	 */
	public String getHandlestatus(){
		return handlestatus;
	}

	/**
	 * 设置属性：
	 *	调用接口的状态
	 */
	public void setHandlestatus(String handlestatus){
		this.handlestatus = handlestatus;
	}

	/**
	 * 获取属性：
	 *	状态说明
	 */
	public String getHandlestatusDesp(){
		return handlestatusDesp;
	}

	/**
	 * 设置属性：
	 *	状态说明
	 */
	public void setHandlestatusDesp(String handlestatusDesp){
		this.handlestatusDesp = handlestatusDesp;
	}

	/**
	 * 获取属性：
	 *	此订单最终回调的状态
	 */
	public String getResponsestatus(){
		return responsestatus;
	}

	/**
	 * 设置属性：
	 *	此订单最终回调的状态
	 */
	public void setResponsestatus(String responsestatus){
		this.responsestatus = responsestatus;
	}

	/**
	 * 获取属性：
	 *	状态说明
	 */
	public String getResponsestatusDesp(){
		return responsestatusDesp;
	}

	/**
	 * 设置属性：
	 *	状态说明
	 */
	public void setResponsestatusDesp(String responsestatusDesp){
		this.responsestatusDesp = responsestatusDesp;
	}

	/**
	 * 获取属性：
	 *	大区id
	 */
	public String getZoneid(){
		return zoneid;
	}

	/**
	 * 设置属性：
	 *	大区id
	 */
	public void setZoneid(String zoneid){
		this.zoneid = zoneid;
	}

	/**
	 * 获取属性：
	 *	pfkey
	 */
	public String getPfkey(){
		return pfkey;
	}

	/**
	 * 设置属性：
	 *	pfkey
	 */
	public void setPfkey(String pfkey){
		this.pfkey = pfkey;
	}

	/**
	 * 获取属性：
	 *	pay_token
	 */
	public String getPay_token(){
		return pay_token;
	}

	/**
	 * 设置属性：
	 *	pay_token
	 */
	public void setPay_token(String pay_token){
		this.pay_token = pay_token;
	}

	/**
	 * 获取属性：
	 *	额外参数
	 */
	public String[] getArgs(){
		return args;
	}

	/**
	 * 设置属性：
	 *	额外参数
	 */
	public void setArgs(String[] args){
		this.args = args;
	}

}