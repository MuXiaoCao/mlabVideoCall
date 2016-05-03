package com.mlab.xiaocao.util;

import java.util.UUID;


/**
 * 
 * @author MUXIAOCAO
 *
 */
public class DataUtil {
	
	/**
	 * 返回8位UUID码
	 * @return
	 */
	public int GetUUID() {
		UUID uuid = UUID.randomUUID();
		return (int)(uuid.getLeastSignificantBits()%100000000);
	}
	
	/*=============KEY================*/
	/**
	 *  服务器时间
	 */
	public String COMPARE_TIME;
	// 客户端时间
	public String CLIENT_TIME;
	// 视频通话发起id
	public String USER_ID;
	// 视频通话接收id
	public String TARGET_ID;
	/**
	 *  在线所有通话记录
	 */
	public String VIDEOCALL_LIST;
	public String BYVIDEOCALL_LIST;
	
	/**
	 * 服务器反馈给用户的key
	 */
	public String JMS_KEY_USER;
	
	/*===========ClientBean key============*/

	/**
	 * userID
	 */
	public String USER_CLIENT_ID;
	/**
	 * datastype
	 */
	public String DATA_TYPE;
	/**
	 * messageID
	 */
	public String MESSAGE_ID;
	/**
	 * timestmp
	 */
	public String TIME_STAMP;
	
	/*======数据类型=====*/
	public static final int SendVideoInfo = 1;
	public static final int SendAudioInfo = 3;
	public static final int ReceiveVideoInfo = 2;
	public static final int ReceiveAudioInfo = 4;
	/*
	 * 超时最大毫秒数
	 */
	public static final int TIME_OUT = 60000;
	
	
	/*=============socket 参数==============*/
	/*
	 * videocall是否正在进行
	 */
	public static boolean VIDEOCALL_RUNNING = true;
	public int PORT;
	public int SEND_PORT;

	
	public int getSEND_PORT() {
		return SEND_PORT;
	}

	public void setSEND_PORT(int sEND_PORT) {
		SEND_PORT = sEND_PORT;
	}

	public String getTIME_STAMP() {
		return TIME_STAMP;
	}

	public int getPORT() {
		return PORT;
	}

	public void setPORT(int pORT) {
		PORT = pORT;
	}

	public String getUSER_CLIENT_ID() {
		return USER_CLIENT_ID;
	}

	public void setUSER_CLIENT_ID(String uSER_CLIENT_ID) {
		USER_CLIENT_ID = uSER_CLIENT_ID;
	}

	public String getDATA_TYPE() {
		return DATA_TYPE;
	}

	public void setDATA_TYPE(String dATA_TYPE) {
		DATA_TYPE = dATA_TYPE;
	}

	public String getMESSAGE_ID() {
		return MESSAGE_ID;
	}

	public void setMESSAGE_ID(String mESSAGE_ID) {
		MESSAGE_ID = mESSAGE_ID;
	}

	public String getTIME_SATMP() {
		return TIME_STAMP;
	}

	public void setTIME_STAMP(String tIME_STMP) {
		TIME_STAMP = tIME_STMP;
	}

	public String getJMS_KEY_USER() {
		return JMS_KEY_USER;
	}

	public void setJMS_KEY_USER(String jMS_KEY_USER) {
		JMS_KEY_USER = jMS_KEY_USER;
	}

	public String getBYVIDEOCALL_LIST() {
		return BYVIDEOCALL_LIST;
	}

	public void setBYVIDEOCALL_LIST(String bYVIDEOCALL_LIST) {
		BYVIDEOCALL_LIST = bYVIDEOCALL_LIST;
	}

	public String getVIDEOCALL_LIST() {
		return VIDEOCALL_LIST;
	}

	public void setVIDEOCALL_LIST(String vIDEOCALL_LIST) {
		VIDEOCALL_LIST = vIDEOCALL_LIST;
	}

	public DataUtil() {
	}

	public String getCOMPARE_TIME() {
		return COMPARE_TIME;
	}

	public void setCOMPARE_TIME(String cOMPARE_TIME) {
		COMPARE_TIME = cOMPARE_TIME;
	}

	public String getCLIENT_TIME() {
		return CLIENT_TIME;
	}

	public void setCLIENT_TIME(String cLIENT_TIME) {
		CLIENT_TIME = cLIENT_TIME;
	}

	public String getUSER_ID() {
		return USER_ID;
	}

	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}

	public String getTARGET_ID() {
		return TARGET_ID;
	}

	public void setTARGET_ID(String tARGET_ID) {
		TARGET_ID = tARGET_ID;
	}

	
}
