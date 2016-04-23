package com.mlab.xiaocao.vo;



public class ClientBean {
	
	private String userID;
	private int datatype;
	private int messageID;
	private long timestamp;
	
	
	public ClientBean(int datatype, String userID, int messageID,
			long timestmp) {
		super();
		this.datatype = datatype;
		this.userID = userID;
		this.messageID = messageID;
		this.timestamp = timestmp;
	}
	public ClientBean() {
	}
	public int getDatastype() {
		return datatype;
	}
	public void setDatastype(int datastype) {
		this.datatype = datastype;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public int getMessageID() {
		return messageID;
	}
	public void setMessageID(int messageID) {
		this.messageID = messageID;
	}
	public long getTimestmp() {
		return timestamp;
	}
	public void setTimestmp(long timestmp) {
		this.timestamp = timestmp;
	}

}
