package com.mlab.xiaocao.service.impl;

import java.util.HashMap;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

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
import com.mlab.xiaocao.vo.ClientBean;
import com.mlab.xiaocao.vo.VideoCall;

@Service("videoService")
public class VideoCallServiceImpl implements VideoCallService {

	@Resource(name = "videoDao")
	private VideoDao videoDao;
	@Resource(name = "videoCallJMS")
	private VideoCallJMS videoCallJMS;
	@Resource(name = "dataUtil")
	private DataUtil Util;

	JSONObject json;
	/*
	 * 视频通话列表,key为博大用户id + “-” + 目标用户id
	 */
	HashMap<String, VideoCall> videoCallList = new HashMap<String, VideoCall>();

	/**
	 * 服务器接收到拨打方的拨打信息进行的初始化工作
	 */
	public JSONObject initVideoCall(final String srcID, String jmsKey1,
			String targetID, String jmsKey2) {
		// VideoCall videoCall = new VideoCall(srcID, targetID);

		videoCallList.put(srcID + "-" + targetID, new VideoCall(srcID, jmsKey1, targetID,
				jmsKey2));

		videoCallJMS.sendTextMessage(srcID, false, jmsKey1);
		// jms反馈队列
		json = new JSONObject();
		json.put(Util.JMS_KEY_USER, jmsKey1);
		return json;
	}

	/**
	 * 服务器接收到拨打方的拨打信息进行的初始化工作
	 */
	public void startVideoCall(String targetID) {
		
		Set<String> keySet = videoCallList.keySet();
		String userID = null;
		for (String string : keySet) {
			if (string.split("-")[1].equals(targetID)) {
				userID = string;
				break;
			}
		}
		
		final VideoCall videoCall = videoCallList.get(userID);

		/**
		 * 拨打用户监听
		 */
		videoCallJMS.receiveMessageByListener(videoCall.getSrcUser().getID(),
				new MessageCallBack() {
			
					public boolean isStopReceiveMessage() {
						
						return videoCall.isStop();
					}

					public void receiveMessage(Message message) {
						try {
							ClientBean clientBean = new ClientBean(message
									.getIntProperty(Util.DATA_TYPE), message
									.getStringProperty(Util.USER_CLIENT_ID),
									message.getIntProperty(Util.MESSAGE_ID),
									message.getLongProperty(Util.TIME_STAMP));
							message.getStringProperty(Util.DATA_TYPE);
							
							videoCall.setClientData(clientBean);
						} catch (JMSException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
		
		/**
		 * 接听用户监听
		 */
		videoCallJMS.receiveMessageByListener(videoCall.getTargetUser().getID(),
				new MessageCallBack() {
					
					public boolean isStopReceiveMessage() {
						
						return videoCall.isStop();
					}

					public void receiveMessage(Message message) {
						try {
							ClientBean clientBean = new ClientBean(message
									.getIntProperty(Util.DATA_TYPE), message
									.getStringProperty(Util.USER_CLIENT_ID),
									message.getIntProperty(Util.MESSAGE_ID),
									message.getLongProperty(Util.TIME_STAMP));
							message.getStringProperty(Util.DATA_TYPE);
							
							videoCall.setClientData(clientBean);
						} catch (JMSException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
		
		/**
		 * 定时发送用户信息
		 */
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				
				JSONObject jsonObject = new JSONObject(videoCall.getSrcUserServerMessage());
				videoCallJMS.sendTextMessage(jsonObject.toString(), false, videoCall.getSrcUser().getJmsID());
				jsonObject = new JSONObject(videoCall.getTargetUserServerMessage());
				videoCallJMS.sendTextMessage(jsonObject.toString(),false, videoCall.getTargetUser().getJmsID());
				
				if (videoCall.isTimeOut()) {
					videoCall.destory();
					this.cancel();
				}
				
			}
		}, 100, 100);

	}

	public String findUserID(String userID) {
		
		Set<String> keySet = videoCallList.keySet();
		for (String string : keySet) {
			if (string.split("-")[1].equals(userID)) {
				return string.split("-")[0];
			}else if (string.split("-")[0].equals(userID)) {
				return string.split("-")[1];
			}
		}
		return null;
	}
	
	/**
	 * 数据清除
	 */
	public void stopVideoCall(String userID2, String userID1) {
		if (videoCallList.containsKey(userID1 + "-" + userID2)) {
			videoCallList.get(userID1 + "-" + userID2).setIsStop(true);
			videoCallList.get(userID1 + "-" + userID2).destory();
			videoCallList.remove(userID1 + "-" + userID2);
		}else {
			videoCallList.get(userID2 + "-" + userID1).setIsStop(true);
			videoCallList.get(userID2 + "-" + userID1).destory();
			videoCallList.remove(userID2 + "-" + userID1);
		}
	}
	
}
