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
	
	// 定义当前线程所处理的Socket
	private Socket socket;
	// 该线程所处理的Socket所对应的输出流
	OutputStream out = null;
	// 该线程所处理的Socket所对应的输入流
	BufferedReader br = null;
	

	public ServerThread(Socket socket,MainSendSocketServer socketServer) throws IOException {
		this.socket = socket;
		this.videoService = socketServer.videoCallService;
		this.Util = socketServer.Util;
	
		// 初始化输出流
		out = socket.getOutputStream();
		// 初始化输入流
		br = new BufferedReader(new InputStreamReader(socket.getInputStream(),
				"utf-8"));
	}

	public void run() {
		String content = null;
		// 采用循环不断从socket中读取客户端发送过来的数据
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
	 * 定义读取客户端数据的方法
	 * @return
	 */
	private String readFromClient() {
		
		try {
			return br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			// 如果有异常说明该socket已经关闭，则需要删除该socket
			MainSendSocketServer.socketList.remove(socket);
		}
		return null;
	}

}
