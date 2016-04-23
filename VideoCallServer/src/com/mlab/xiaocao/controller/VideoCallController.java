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
		// 初始化视频通话在线记录，分别存放的是用户id和jms反馈队列名
		context.setAttribute(Util.VIDEOCALL_LIST, new HashMap<String, String>());
		context.setAttribute(Util.BYVIDEOCALL_LIST, new HashMap<String, String>());
	}
	
	
	private PrintWriter out;
	private JSONObject json;
	HashMap<String, String> videoCallList;
	HashMap<String, String> byVideoCallList;
	/**
	 * 在接收到客户端的时间校准请求之后，立即返回服务器当前时间，作为时间基准发送给客户端，key=“compareTime”，value=服务器时间
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
	 * 在接收到客户端拨打通知，并得到userID和targetID之后，服务器应理解开辟两个用户对象，用户存放和计算视频通话过程中的时间序列。
	 */
	@RequestMapping(value="/initVideoCall") 
	public String initVideoCall(HttpServletRequest request,HttpServletResponse response) {
		
		json = new JSONObject(request.getParameterNames().nextElement());
		
		String userID = json.getString(Util.USER_ID);
		String targetID = json.getString(Util.TARGET_ID);
		
		// 将通话双方放入上下文中，以便对接收方进行消息反馈
		videoCallList = (HashMap<String, String>)context.getAttribute(Util.VIDEOCALL_LIST);
		byVideoCallList = (HashMap<String, String>)context.getAttribute(Util.BYVIDEOCALL_LIST);
		
		//生成反馈队列名
		String jmskey1 = Util.GetUUID() + ""; 
		String jmskey2 = Util.GetUUID() + ""; 
		//加入到对应结合中
		videoCallList.put(userID, jmskey1); 
		byVideoCallList.put(targetID, jmskey2);
		// 初始化并返回反馈队列名
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
	 * 接收方接通电话，开启视频通话
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
	 * 一方出现挂断，则清除相关数据
	 * 1.context和service的相关记录清楚
	 * 2.videocall的相关数据清楚
	 * 3.jms的相关监听终止
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
