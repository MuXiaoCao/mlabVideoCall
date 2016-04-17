package com.mlab.xiaocao.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.TreeMap;

import javassist.expr.NewArray;

import javax.annotation.Resource;
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
		context.setAttribute(Util.VIDEOCALL_LIST, new ArrayList<String>());
		context.setAttribute(Util.BYVIDEOCALL_LIST, new ArrayList<String>());
	}
	
	
	private PrintWriter out;
	private JSONObject json;
	
	/**
	 * �ڽ��յ��ͻ��˵�ʱ��У׼����֮���������ط�������ǰʱ�䣬��Ϊʱ���׼���͸��ͻ��ˣ�key=��compareTime����value=������ʱ��
	 */
	@RequestMapping(value="/getTime")
	public String getTime(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String string = (String) parameterNames.nextElement();
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
		
		String userID = request.getParameter(Util.USER_ID);
		String targetID = request.getParameter(Util.TARGET_ID);
		
		context.setAttribute(Util.VIDEOCALL_LIST, userID);
		context.setAttribute(Util.BYVIDEOCALL_LIST, targetID);
		
		videoCallService.startVideoCall(userID, targetID);
		
		return null;
	}
	
	
}
