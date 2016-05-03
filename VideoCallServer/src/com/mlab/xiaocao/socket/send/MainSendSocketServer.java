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
 * 用于发送结果的socketserver
 * @author muxiaocao
 *
 */
@Service("mainSendSocketServer")
public class MainSendSocketServer implements Runnable{

	@Resource(name = "dataUtil")
	public DataUtil Util;
	
	@Resource(name = "videoService")
	public VideoCallService videoCallService;
	
	// 定义保存所有Socket的Arraylist
	public static ArrayList<Socket> socketList = new ArrayList<Socket>();
	
	public MainSendSocketServer() {
		
	}
	public void run() {
		ServerSocket ss;
		try {
			ss = new ServerSocket(Util.SEND_PORT);
			while (DataUtil.VIDEOCALL_RUNNING) {
				// 此行代码会阻塞，将一直等待客户端的连接
				Socket s = ss.accept();
				System.out.println("this is sendserversocket,socket " + s.hashCode() +  "加入");
				socketList.add(s);
				// 每当客户端连接后启动一条ServerThread线程为客户端服务
				new Thread(new ServerThread(s,this)).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
