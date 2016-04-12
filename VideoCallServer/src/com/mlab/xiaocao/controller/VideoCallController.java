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
	 * �ڽ��յ��ͻ��˵�ʱ��У׼����֮���������ط�������ǰʱ�䣬��Ϊʱ���׼���͸��ͻ��ˣ�key=��compareTime����value=������ʱ��
	 */
	@RequestMapping(value="/getTime")
	public String getTime(HttpServletRequest request,HttpServletResponse response ) throws IOException {
		
		System.out.println(request.getLocalName());
		out = response.getWriter();
		out.write(timeService.getTime().toString());
		return null;
	}
	/**
	 * �ڽ��յ��ͻ��˲���֪ͨ�����õ�userID��targetID֮�󣬷�����Ӧ��⿪�������û������û���źͼ�����Ƶͨ�������е�ʱ�����С�
	 * 
	 */
	@RequestMapping(value="/initVideoCall") 
	public String initVideoCall(HttpServletRequest request,HttpServletResponse response) {
		
		String userID = request.getParameter(Util.USER_ID);
		String targetID = request.getParameter(Util.TARGET_ID);
		
		
		return null;
	}
	
	
}
