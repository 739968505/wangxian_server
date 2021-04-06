package com.xuanzhi.tools.time;

import java.io.IOException;

import com.xuanzhi.tools.transport.*;

public class TimeServer 
	implements ConnectionConnectedHandler,MessageHandler{

	/**
	 * ���ͻ��������Ϸ�������ʱ��Lamp��ص��������
	 * �˷�������Ҫ�Ĺ������������ӵ���Ϣ��������Ϣ������
	 */
	public void connected(Connection conn) throws IOException {
		conn.setMessageFactory(TimeMessageFactory.getInstance());
		conn.setMessageHandler(this);
	}

	public void discardRequestMessage(Connection conn, RequestMessage req)
			throws ConnectionException {
	}

	/**
	 * ���������յ��ͻ��˵�����ʱ������ô˷������˷������ص���Ӧ��Ϣ
	 * Lamp�ᴫ����ͻ��ˣ��������null��lamp�������κ���Ϣ���ͻ���
	 */
	public ResponseMessage receiveRequestMessage(Connection conn,
			RequestMessage message) throws ConnectionException {
		if(message instanceof TIME_REQ){
			TIME_REQ req = (TIME_REQ)message;
			return new TIME_RES(req.getSequnceNum(),System.currentTimeMillis());
		}
		return null;
	}

	public void receiveResponseMessage(Connection conn, RequestMessage req,
			ResponseMessage res) throws ConnectionException {
	}

	public RequestMessage waitingTimeout(Connection conn, long timeout)
			throws ConnectionException {
		return null;
	}

	public static void main(String args[]) throws Exception{
		int port = 7777;
		TimeServer server = new TimeServer();
		DefaultConnectionSelector selector = new DefaultConnectionSelector();
		selector.setClientModel(false);
		selector.setPort(port);
		selector.setConnectionConnectedHandler(server);
		selector.init();
	}
}
