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
	public static int GetUUID() {
		UUID uuid = UUID.randomUUID();
		return (int)(uuid.getLeastSignificantBits()%100000000);
	}
	
	/*=============KEY================*/
	// 服务器时间
	public String COMPARE_TIME;
	// 客户端时间
	public String CLIENT_TIME;
	// 视频通话发起id
	public String USER_ID;
	// 视频通话接收id
	public String TARGET_ID;
	// 在线所有通话记录
	public String VIDEOCALL_LIST;
	public String BYVIDEOCALL_LIST;
	
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
