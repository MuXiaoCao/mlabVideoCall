package com.mlab.xiaocao.service.impl;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.mlab.xiaocao.dao.VideoDao;
import com.mlab.xiaocao.service.VideoCallService;
import com.mlab.xiaocao.util.DataUtil;
import com.mlab.xiaocao.util.VideoCallJMS;
import com.mlab.xiaocao.util.VideoCallJMS.MessageCallBack;
import com.mlab.xiaocao.vo.VideoCall;

@Service("videoService")
public class VideoCallServiceImpl implements VideoCallService{
	
	@Resource(name="videoDao")
	private VideoDao videoDao;
	@Resource(name="videoCallJMS")
	private VideoCallJMS videoCallJMS;
	@Resource(name="dataUtil")
	private DataUtil Util;
	
	JSONObject json;
	
	public JSONObject startVideoCall(final String srcID, String targetID) {
		//VideoCall videoCall = new VideoCall(srcID, targetID);
		json = new JSONObject();
		json.put(Util.JMS_KEY_USER, Util.GetUUID());
		
		videoCallJMS.receiveMessageByListener(srcID,new MessageCallBack() {
			
			public void receiveMessage(Message message) {
				try {
					
					String stringProperty = message.getStringProperty(srcID);
					
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		
		return json;
	}
	
	
	
}
