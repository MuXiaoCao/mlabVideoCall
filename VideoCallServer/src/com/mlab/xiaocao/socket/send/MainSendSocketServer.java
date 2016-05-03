package com.mlab.xiaocao.socket.send;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mlab.xiaocao.service.VideoCallService;
import com.mlab.xiaocao.util.DataUtil;

/**
 * ���ڷ��ͽ����socketserver
 * @author muxiaocao
 *
 */
@Service("mainSendSocketServer")
public class MainSendSocketServer implements Runnable{

	@Resource(name = "dataUtil")
	public DataUtil Util;
	
	@Resource(name = "videoService")
	public VideoCallService videoCallService;
	
	// ���屣������Socket��Arraylist
	public static ArrayList<Socket> socketList = new ArrayList<Socket>();
	
	public MainSendSocketServer() {
		
	}
	public void run() {
		ServerSocket ss;
		try {
			ss = new ServerSocket(Util.SEND_PORT);
			while (DataUtil.VIDEOCALL_RUNNING) {
				// ���д������������һֱ�ȴ��ͻ��˵�����
				Socket s = ss.accept();
				System.out.println("this is sendserversocket,socket " + s.hashCode() +  "����");
				socketList.add(s);
				// ÿ���ͻ������Ӻ�����һ��ServerThread�߳�Ϊ�ͻ��˷���
				new Thread(new ServerThread(s,this)).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
