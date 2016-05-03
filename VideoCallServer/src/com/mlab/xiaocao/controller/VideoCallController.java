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
		// 初始化视频通话在线记录，分别存放的是用户srcid和targetid
		context.setAttribute(Util.VIDEOCALL_LIST, new HashMap<String, String>());
		new Thread(socketServer).start();
		new Thread(sendSocketServer).start();
	}

	private PrintWriter out;
	private JSONObject json;
	HashMap<String, String> videoCallList;

	/**
	 * 在接收到客户端的时间校准请求之后，立即返回服务器当前时间，作为时间基准发送给客户端，key=“compareTime”，value=服务器时间
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
	 * 在接收到客户端拨打通知，并得到userID和targetID之后，服务器应理解开辟两个用户对象，用户存放和计算视频通话过程中的时间序列。
	 */
	@RequestMapping(value = "/initVideoCall")
	public String initVideoCall(HttpServletRequest request,
			HttpServletResponse response) {

		json = new JSONObject(request.getParameterNames().nextElement());

		String userID = json.getInt(Util.USER_ID) + "";
		String targetID = json.getInt(Util.TARGET_ID) + "";

		System.out.println("this is intivideocall:" + userID + "," + targetID);
		// 将通话双方放入上下文中，以便对接收方进行消息反馈
		videoCallList = (HashMap<String, String>) context
				.getAttribute(Util.VIDEOCALL_LIST);

		// 加入到队列中
		videoCallList.put(userID, targetID);

		// 初始化并返回反馈队列名
		videoCallService.initVideoCall(userID, targetID);

		return null;
	}

	/**
	 * 一方出现挂断，则清除相关数据 
	 * 1.context和service的相关记录清楚 
	 * 2.videocall的相关数据清楚
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
		
		//如果是发起方先挂断，我们需要把发起方置为-1，且把接收方放入key中保存，等待接收方挂断
		//如果发现userID对应的value是-1，则说明他是后挂断的一方，直接remove就可以了
		if (videoCallList.containsKey(userID)) {
			
			if (!videoCallList.get(userID).equals("-1")) {
				videoCallList.put(targetID, "-1");
				videoCallService.stopVideoCall(userID, targetID,false);
			}else {
				videoCallList.remove(userID);
				videoCallService.stopVideoCall(userID, targetID,true);
			}
			
		}else if(videoCallList.containsValue(userID)){//如果是接收方先挂断，直接向value置为-1即可
			videoCallList.put(userID, "-1");
			videoCallService.stopVideoCall(userID, targetID,false);
		}

	}

}
