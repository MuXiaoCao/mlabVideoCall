package com.mlab.xiaocao.vo;

import java.util.ArrayList;


public class VideoCall {
	
	private User srcUser;
	private User targetUser;

	public VideoCall(String srcUserID,String targetUserID) {
		srcUser = new User(srcUserID);
		targetUser = new User(targetUserID);
	}
	
	class User {
		
		public User(String id) {
			this.ID = id;
			receiveAudioTimeStmp = new ArrayList<Long>();
			receiveVideoTimeStmp = new ArrayList<Long>();
			sendAudioTimeStmp = new ArrayList<Long>();
			sendVideoTimeVideoStmp = new ArrayList<Long>();
		}
		
		
		private String ID;
		private ArrayList<Long> receiveVideoTimeStmp;
		private ArrayList<Long> sendVideoTimeVideoStmp;
 		private ArrayList<Long> receiveAudioTimeStmp;
 		private ArrayList<Long> sendAudioTimeStmp;
	}
}
