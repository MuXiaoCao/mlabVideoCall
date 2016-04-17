package com.mlab.xiaocao.vo;

import java.util.ArrayList;


public class VideoCall {
	
	private User srcUser;
	private User targetUser;

	public VideoCall(String srcUserID,String jmsKey1,String targetUserID,String jmsKey2) {
		srcUser = new User(srcUserID,jmsKey1);
		targetUser = new User(targetUserID,jmsKey2);
	}
	
	
	
	public User getSrcUser() {
		return srcUser;
	}



	public void setSrcUser(User srcUser) {
		this.srcUser = srcUser;
	}



	public User getTargetUser() {
		return targetUser;
	}



	public void setTargetUser(User targetUser) {
		this.targetUser = targetUser;
	}


	/*=======================主要计算逻辑=======================*/
	
	
	class User {
		
		public User(String id,String messageID) {
			this.ID = id;
			this.messageID = messageID;
			jmsID = 0;
			receiveAudioTimeStmp = new ArrayList<Long>();
			receiveVideoTimeStmp = new ArrayList<Long>();
			sendAudioTimeStmp = new ArrayList<Long>();
			sendVideoTimeVideoStmp = new ArrayList<Long>();
		}
		
		private String ID;
		private int jmsID;
		private String messageID;
		private ArrayList<Long> receiveVideoTimeStmp;
		private ArrayList<Long> sendVideoTimeVideoStmp;
 		private ArrayList<Long> receiveAudioTimeStmp;
 		private ArrayList<Long> sendAudioTimeStmp;
 		
 		
 		public String getID() {
			return ID;
		}

		public void setID(String iD) {
			ID = iD;
		}

		public int getJmsID() {
			jmsID++;
			return jmsID;
		}

		public void setJmsID(int jmsID) {
			this.jmsID = jmsID;
		}

		public String getMessageID() {
			return messageID;
		}

		public void setMessageID(String messageID) {
			this.messageID = messageID;
		}
		
		/*==================数据的添加===================*/
		
		public void addReceiveAudioTimeStmp(long timeStmp) {
 			receiveAudioTimeStmp.add(timeStmp);
 		}
 		
 		public void addSendVideoTimeVideoStmp(long timeStmp) {
 			sendVideoTimeVideoStmp.add(timeStmp);
 		}
 		public void addSendAudioTimeStmp(long timeStmp) {
 			sendAudioTimeStmp.add(timeStmp);
 		}
 		public void addReceiveVideoTimeStmp(long timeStmp) {
 			receiveVideoTimeStmp.add(timeStmp);
 		}
 		
 		/*===============数据的处理================*/
 		
 		
 		
	}
}
