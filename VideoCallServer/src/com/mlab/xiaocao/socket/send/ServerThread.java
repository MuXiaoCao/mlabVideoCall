package com.mlab.xiaocao.socket.send;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import org.apache.catalina.connector.OutputBuffer;
import org.json.JSONObject;

import com.mlab.xiaocao.service.VideoCallService;
import com.mlab.xiaocao.util.DataUtil;
import com.mlab.xiaocao.vo.ClientBean;

public class ServerThread implements Runnable {

	private VideoCallService videoService;
	private DataUtil Util;
	
	// ���嵱ǰ�߳��������Socket
	private Socket socket;
	// ���߳��������Socket����Ӧ�������
	OutputStream out = null;
	// ���߳��������Socket����Ӧ��������
	BufferedReader br = null;
	

	public ServerThread(Socket socket,MainSendSocketServer socketServer) throws IOException {
		this.socket = socket;
		this.videoService = socketServer.videoCallService;
		this.Util = socketServer.Util;
	
		// ��ʼ�������
		out = socket.getOutputStream();
		// ��ʼ��������
		br = new BufferedReader(new InputStreamReader(socket.getInputStream(),
				"utf-8"));
	}

	public void run() {
		String content = null;
		// ����ѭ�����ϴ�socket�ж�ȡ�ͻ��˷��͹���������
		while ((content = readFromClient()) != null) {
			JSONObject json = new JSONObject(content);
			String userID = json.getInt(Util.USER_ID) + "";
			json = videoService.getUserVideoCallData(userID);
		
			try {
				out.write((json.toString() + "\n").getBytes());
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			MainSendSocketServer.socketList.remove(socket);
		}
		return null;
	}

}
