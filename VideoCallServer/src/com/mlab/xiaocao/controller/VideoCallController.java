package com.mlab.xiaocao.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.TreeMap;

import javassist.expr.NewArray;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletConfigAware;

import com.mlab.xiaocao.service.VideoCallService;
import com.mlab.xiaocao.service.time.TimeService;
import com.mlab.xiaocao.util.DataUtil;

@Controller
public class VideoCallController implements ServletConfigAware{
	
	@Resource(name="dataUtil")
	private DataUtil Util;
	@Resource(name="timeService")
	private TimeService timeService;
	@Resource(name="videoService")
	private VideoCallService videoCallService;
	
	private ServletContext context;

	public void setServletContext(ServletContext servletContext) {
		this.context = servletContext;
	}
	public void setServletConfig(ServletConfig config) {
		context = config.getServletContext();
		// ��ʼ����Ƶͨ�����߼�¼���ֱ��ŵ����û�id��jms����������
		context.setAttribute(Util.VIDEOCALL_LIST, new HashMap<String, String>());
		context.setAttribute(Util.BYVIDEOCALL_LIST, new HashMap<String, String>());
	}
	
	
	private PrintWriter out;
	private JSONObject json;
	HashMap<String, String> videoCallList;
	HashMap<String, String> byVideoCallList;
	/**
	 * �ڽ��յ��ͻ��˵�ʱ��У׼����֮���������ط�������ǰʱ�䣬��Ϊʱ���׼���͸��ͻ��ˣ�key=��compareTime����value=������ʱ��
	 */
	@RequestMapping(value="/getTime")
	public String getTime(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String string = parameterNames.nextElement();
			System.out.println(string);
			
		}
		System.out.println(request.getLocalName());
		out = response.getWriter();
		out.write(timeService.getTime().toString());
		return null;
	}
	/**
	 * �ڽ��յ��ͻ��˲���֪ͨ�����õ�userID��targetID֮�󣬷�����Ӧ��⿪�������û������û���źͼ�����Ƶͨ�������е�ʱ�����С�
	 */
	@RequestMapping(value="/initVideoCall") 
	public String initVideoCall(HttpServletRequest request,HttpServletResponse response) {
		
		json = new JSONObject(request.getParameterNames().nextElement());
		
		String userID = json.getString(Util.USER_ID);
		String targetID = json.getString(Util.TARGET_ID);
		
		// ��ͨ��˫�������������У��Ա�Խ��շ�������Ϣ����
		videoCallList = (HashMap<String, String>)context.getAttribute(Util.VIDEOCALL_LIST);
		byVideoCallList = (HashMap<String, String>)context.getAttribute(Util.BYVIDEOCALL_LIST);
		
		//���ɷ���������
		String jmskey1 = Util.GetUUID() + ""; 
		String jmskey2 = Util.GetUUID() + ""; 
		//���뵽��Ӧ�����
		videoCallList.put(userID, jmskey1); 
		byVideoCallList.put(targetID, jmskey2);
		// ��ʼ�������ط���������
		json = videoCallService.initVideoCall(userID, jmskey1,targetID,jmskey2);
		try {
			out = response.getWriter();
			out.write(json.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * ���շ���ͨ�绰��������Ƶͨ��
	 */
	@RequestMapping(value="/receiveCall") 
	public String receiveCall(HttpServletRequest request,HttpServletResponse response) {
		
		json = new JSONObject(request.getParameterNames().nextElement());
		
		String targetID = json.getString(Util.TARGET_ID);
		byVideoCallList = (HashMap<String, String>)context.getAttribute(Util.BYVIDEOCALL_LIST);
		if (byVideoCallList.containsKey(targetID)) {
			String jmskey = byVideoCallList.get(targetID);
			json.put(targetID, jmskey);
			try {
				out = response.getWriter();
				out.write(json.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		videoCallService.startVideoCall(targetID);
		return null;
	}
	
	/**
	 * һ�����ֹҶϣ�������������
	 * 1.context��service����ؼ�¼���
	 * 2.videocall������������
	 * 3.jms����ؼ�����ֹ
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/videoCallHangUp") 
	public void videoCallHangUp(HttpServletRequest request,HttpServletResponse response) {
		String userID1 = request.getParameterNames().nextElement();
		String userID2 = videoCallService.findUserID(userID1);
		
		videoCallList = (HashMap<String, String>)context.getAttribute(Util.VIDEOCALL_LIST);
		byVideoCallList = (HashMap<String, String>)context.getAttribute(Util.BYVIDEOCALL_LIST);
		if (videoCallList.containsKey(userID1)) {
			videoCallList.remove(userID1);
			byVideoCallList.remove(userID2);
			videoCallService.stopVideoCall(userID1,userID2);
		}else {
			videoCallList.remove(userID2);
			byVideoCallList.remove(userID1);
			videoCallService.stopVideoCall(userID2,userID1);
		}
	}
}
