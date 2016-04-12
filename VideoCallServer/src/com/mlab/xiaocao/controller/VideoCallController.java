package com.mlab.xiaocao.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mlab.xiaocao.service.time.TimeService;
import com.mlab.xiaocao.util.DataUtil;

@Controller
public class VideoCallController {
	@Resource(name="dataUtil")
	private DataUtil Util;
	@Resource(name="timeService")
	private TimeService timeService;
	private PrintWriter out;
	
	/**
	 * 在接收到客户端的时间校准请求之后，立即返回服务器当前时间，作为时间基准发送给客户端，key=“compareTime”，value=服务器时间
	 */
	@RequestMapping(value="/getTime")
	public String getTime(HttpServletRequest request,HttpServletResponse response ) throws IOException {
		
		System.out.println(request.getLocalName());
		out = response.getWriter();
		out.write(timeService.getTime().toString());
		return null;
	}
	/**
	 * 在接收到客户端拨打通知，并得到userID和targetID之后，服务器应理解开辟两个用户对象，用户存放和计算视频通话过程中的时间序列。
	 * 
	 */
	@RequestMapping(value="/initVideoCall") 
	public String initVideoCall(HttpServletRequest request,HttpServletResponse response) {
		
		String userID = request.getParameter(Util.USER_ID);
		String targetID = request.getParameter(Util.TARGET_ID);
		
		
		return null;
	}
	
	
}
