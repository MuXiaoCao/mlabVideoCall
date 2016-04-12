package com.mlab.xiaocao.vo;



public class ClientBean {
	

	private String userID;
	private String datastype;
	private String messageID;
	private long timestmp;
	
	
	public ClientBean(String datastype, String userID, String messageID,
			long timestmp) {
		super();
		this.datastype = datastype;
		this.userID = userID;
		this.messageID = messageID;
		this.timestmp = timestmp;
	}
	public ClientBean() {
	}
	public String getDatastype() {
		return datastype;
	}
	public void setDatastype(String datastype) {
		this.datastype = datastype;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getMessageID() {
		return messageID;
	}
	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}
	public long getTimestmp() {
		return timestmp;
	}
	public void setTimestmp(long timestmp) {
		this.timestmp = timestmp;
	}
	
	

}
