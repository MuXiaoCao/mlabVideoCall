package com.mlab.xiaocao.util;


/**
 * 
 * @author MUXIAOCAO
 *
 */
public class DataUtil {
	/*=============KEY================*/
	// 服务器时间
	public String COMPARE_TIME;
	// 客户端时间
	public String CLIENT_TIME;
	// 视频通话发起id
	public String USER_ID;
	// 视频通话接收id
	public String TARGET_ID;
	
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
