package com.mlab.xiaocao.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javassist.expr.NewArray;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jdt.internal.compiler.batch.Main;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletConfigAware;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.HandlerMapping;

import com.mlab.xiaocao.service.VideoCallService;
import com.mlab.xiaocao.service.time.TimeService;
import com.mlab.xiaocao.socket.receive.MainReceiveSocketServer;
import com.mlab.xiaocao.socket.send.MainSendSocketServer;
import com.mlab.xiaocao.util.DataUtil;
import com.mlab.xiaocao.vo.ClientBean;

@Controller
public class VideoCallController implements ServletConfigAware{

	@Resource(name = "dataUtil")
	private DataUtil Util;
	@Resource(name = "timeService")
	private TimeService timeService;
	@Resource(name = "videoService")
	private VideoCallService videoCallService;
	@Resource(name = "mainSocketServer")
	private MainReceiveSocketServer socketServer;
	@Resource(name = "mainSendSocketServer")
	private MainSendSocketServer sendSocketServer;
	
	private ServletContext context;
	
	public void setServletContext(ServletContext servletContext) {
		this.context = servletContext;
	}
	
	public void setServletConfig(ServletConfig config) {
		context = config.getServletContext();
		// ��ʼ����Ƶͨ�����߼�¼���ֱ��ŵ����û�srcid��targetid
		context.setAttribute(Util.VIDEOCALL_LIST, new HashMap<String, String>());
		new Thread(socketServer).start();
		new Thread(sendSocketServer).start();
	}

	private PrintWriter out;
	private JSONObject json;
	HashMap<String, String> videoCallList;

	/**
	 * �ڽ��յ��ͻ��˵�ʱ��У׼����֮���������ط�������ǰʱ�䣬��Ϊʱ���׼���͸��ͻ��ˣ�key=��compareTime����value=������ʱ��
	 */
	@RequestMapping(value = "/getTime")
	public String getTime(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String string = parameterNames.nextElement();
			System.out.println(string);
			System.out.println(request.getParameter(string));

		}
		System.out.println(request.getLocalName());
		out = response.getWriter();
		out.write(timeService.getTime().toString());
		return null;
	}

	/**
	 * �ڽ��յ��ͻ��˲���֪ͨ�����õ�userID��targetID֮�󣬷�����Ӧ��⿪�������û������û���źͼ�����Ƶͨ�������е�ʱ�����С�
	 */
	@RequestMapping(value = "/initVideoCall")
	public String initVideoCall(HttpServletRequest request,
			HttpServletResponse response) {

		json = new JSONObject(request.getParameterNames().nextElement());

		String userID = json.getInt(Util.USER_ID) + "";
		String targetID = json.getInt(Util.TARGET_ID) + "";

		System.out.println("this is intivideocall:" + userID + "," + targetID);
		// ��ͨ��˫�������������У��Ա�Խ��շ�������Ϣ����
		videoCallList = (HashMap<String, String>) context
				.getAttribute(Util.VIDEOCALL_LIST);

		// ���뵽������
		videoCallList.put(userID, targetID);

		// ��ʼ�������ط���������
		videoCallService.initVideoCall(userID, targetID);

		return null;
	}

	/**
	 * һ�����ֹҶϣ������������� 
	 * 1.context��service����ؼ�¼��� 
	 * 2.videocall������������
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/videoCallHangUp")
	public void videoCallHangUp(HttpServletRequest request,
			HttpServletResponse response) {
		json = new JSONObject(request.getParameterNames().nextElement());

		String userID = json.getInt(Util.USER_ID) + "";
		String targetID = json.getInt(Util.TARGET_ID) + "";

		System.out.println("this is videoCallHangUp:" + userID + "," + targetID);
		
		videoCallList = (HashMap<String, String>) context
				.getAttribute(Util.VIDEOCALL_LIST);
		
		//����Ƿ����ȹҶϣ�������Ҫ�ѷ�����Ϊ-1���Ұѽ��շ�����key�б��棬�ȴ����շ��Ҷ�
		//�������userID��Ӧ��value��-1����˵�����Ǻ�Ҷϵ�һ����ֱ��remove�Ϳ�����
		if (videoCallList.containsKey(userID)) {
			
			if (!videoCallList.get(userID).equals("-1")) {
				videoCallList.put(targetID, "-1");
				videoCallService.stopVideoCall(userID, targetID,false);
			}else {
				videoCallList.remove(userID);
				videoCallService.stopVideoCall(userID, targetID,true);
			}
			
		}else if(videoCallList.containsValue(userID)){//����ǽ��շ��ȹҶϣ�ֱ����value��Ϊ-1����
			videoCallList.put(userID, "-1");
			videoCallService.stopVideoCall(userID, targetID,false);
		}

	}

}
