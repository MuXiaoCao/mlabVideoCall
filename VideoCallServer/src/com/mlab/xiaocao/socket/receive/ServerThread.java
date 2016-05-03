package com.mlab.xiaocao.socket.receive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.json.JSONObject;

import com.mlab.xiaocao.service.VideoCallService;
import com.mlab.xiaocao.util.DataUtil;
import com.mlab.xiaocao.vo.ClientBean;

public class ServerThread implements Runnable {

	private VideoCallService videoService;
	private DataUtil Util;
	
	// ���嵱ǰ�߳��������Socket
	private Socket socket;
	// ���߳��������Socket����Ӧ��������
	BufferedReader br = null;

	public ServerThread(Socket socket,MainReceiveSocketServer socketServer) throws IOException {
		this.socket = socket;
		this.videoService = socketServer.videoCallService;
		this.Util = socketServer.Util;
	
		// ��ʼ��������
		br = new BufferedReader(new InputStreamReader(socket.getInputStream(),
				"utf-8"));
	}

	public void run() {
		String content = null;
		// ����ѭ�����ϴ�socket�ж�ȡ�ͻ��˷��͹���������
		while ((content = readFromClient()) != null) {
			JSONObject json = new JSONObject(content);
			ClientBean clientBean = new ClientBean(json.getInt(Util.DATA_TYPE),
					json.getInt(Util.USER_CLIENT_ID),
					json.getInt("messageID"), json.getLong(Util.TIME_STAMP));
			//System.out.println(clientBean);
			videoService.addUserVideoCallData(clientBean);
		}
	}

	/**
	 * �����ȡ�ͻ������ݵķ���
	 * @return
	 */
	private String readFromClient() {
		
		try {
			return br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			// ������쳣˵����socket�Ѿ��رգ�����Ҫɾ����socket
			MainReceiveSocketServer.socketList.remove(socket);
		}
		return null;
	}

}
