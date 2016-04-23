package com.mlab.xiaocao.service;

import org.json.JSONObject;

public interface VideoCallService {
	public JSONObject initVideoCall(String srcID,String jmskey1,String targetID,String jmskey2);

	public void startVideoCall(String targetID);
	
	public String findUserID(String userID);

	public void stopVideoCall(String userID2, String userID1);
}
