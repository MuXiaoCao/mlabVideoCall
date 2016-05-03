package com.mlab.xiaocao.service;

import org.json.JSONObject;

import com.mlab.xiaocao.vo.ClientBean;

public interface VideoCallService {
	public void initVideoCall(String srcID,String targetID);

	public void startVideoCall(String targetID);
	
	public String findUserID(int userID);

	public void stopVideoCall(String userID2, String userID1, boolean b);
	
	public void addUserVideoCallData(ClientBean clientBean);

	public JSONObject getUserVideoCallData(String userID);


}
