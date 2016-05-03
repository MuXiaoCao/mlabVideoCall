package com.mlab.xiaocao.service.impl;

import java.util.HashMap;
import java.util.Set;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.mlab.xiaocao.service.VideoCallService;
import com.mlab.xiaocao.util.DataUtil;
import com.mlab.xiaocao.util.VideoCallJMS;
import com.mlab.xiaocao.vo.ClientBean;
import com.mlab.xiaocao.vo.VideoCall;

@Service("videoService")
public class VideoCallServiceImpl implements VideoCallService {

	@Resource(name = "videoCallJMS")
	private VideoCallJMS videoCallJMS;
	@Resource(name = "dataUtil")
	private DataUtil Util;

	JSONObject json;
	/*
	 * 视频通话列表,key为拨打用户id + “-” + 目标用户id
	 */
	HashMap<String, VideoCall> videoCallList = new HashMap<String, VideoCall>();

	/**
	 * 服务器接收到拨打方的拨打信息进行的初始化工作
	 */
	public void initVideoCall(String srcID, String targetID) {

		System.out.println("this is initvideocall");
		videoCallList.put(srcID + "=" + targetID,new VideoCall(Integer.valueOf(srcID), Integer.valueOf(targetID)));

	}

	/**
	 * 服务器接收到接听消息，开始视频通话
	 */
	public void startVideoCall(String targetID) {

		Set<String> keySet = videoCallList.keySet();
		String userID = null;
		for (String string : keySet) {
			if (string.split("=")[1].equals(targetID)) {
				userID = string;
				break;
			}
		}
	}

	public String findUserID(int userID) {

		Set<String> keySet = videoCallList.keySet();
		for (String string : keySet) {
			if (string.split("=")[1].equals(userID + "")) {
				return string;
			} else if (string.split("=")[0].equals(userID + "")) {
				return string;
			}
		}
		return null;
	}

	/**
	 * 数据清除
	 */
	public void stopVideoCall(String userID2, String userID1,boolean flag) {
		
//			if (videoCallList.containsKey(userID1 + "=" + userID2)) {
//				if (flag) {
//					videoCallList.get(userID1 + "=" + userID2).setIsStop(true);
//					videoCallList.remove(userID1 + "=" + userID2);
//				}
//				videoCallList.get(userID1 + "=" + userID2).destory(Integer.parseInt(userID2));
//			} else if (videoCallList.containsKey(userID2 + "=" + userID1)) {
//				videoCallList.get(userID2 + "=" + userID1).destory(Integer.parseInt(userID2));
//			}
		if (flag) {
			if (videoCallList.containsKey(userID1 + "=" + userID2)) {
				videoCallList.get(userID1 + "=" + userID2).setIsStop(true);
				videoCallList.get(userID1 + "=" + userID2).destory(Integer.parseInt(userID2));
				videoCallList.get(userID1 + "=" + userID2).destory(Integer.parseInt(userID1));
				videoCallList.remove(userID1 + "=" + userID2);
			} else if (videoCallList.containsKey(userID2 + "=" + userID1)) {
				videoCallList.get(userID2 + "=" + userID1).setIsStop(true);
				videoCallList.get(userID2 + "=" + userID1).destory(Integer.parseInt(userID2));
				videoCallList.get(userID2 + "=" + userID1).destory(Integer.parseInt(userID1));
				videoCallList.remove(userID2 + "=" + userID1);
			}
		}
	}

	/**
	 * 视频数据的处理
	 */
	public void addUserVideoCallData(ClientBean clientBean) {
		
		VideoCall videoCall = videoCallList.get(findUserID(clientBean.getUserID()));
		if (clientBean != null && videoCall!=null) {
			videoCall.setClientData(clientBean);
		}
	}


	public JSONObject getUserVideoCallData(String userID) {
		
		String key = findUserID(Integer.valueOf(userID));
		if (key != null) {
			VideoCall videoCall = videoCallList.get(key);
			if (key.split("=")[0].equals(userID)) {
				return new JSONObject(videoCall.getSrcUserServerMessage());
			}else if (key.split("=")[1].equals(userID)) {
				return new JSONObject(videoCall.getTargetUserServerMessage());
			}else {
				return null;
			}
		}else {
			return null;
		}
	}

}
