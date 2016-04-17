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
	public String DATAS_TYPE;
	/**
	 * messageID
	 */
	public String MESSAGE_ID;
	/**
	 * timestmp
	 */
	public String TIME_STMP;
	
	/*======数据类型=====*/
	/**
     * 数据类型
     * 
     * @author 小草
     *
     */
    public enum DataType {
    	//通过括号赋值,而且必须带有一个参构造器和一个属性跟方法，否则编译出错
        //赋值必须都赋值或都不赋值，不能一部分赋值一部分不赋值；如果不赋值则不能写构造器，赋值编译也出错
        MAN(1), WOMEN(2);
        
        private final int value;

        //构造器默认也只能是private, 从而保证构造函数只能在内部使用
        DataType(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    }
	
	
	
	
	public String getUSER_CLIENT_ID() {
		return USER_CLIENT_ID;
	}

	public void setUSER_CLIENT_ID(String uSER_CLIENT_ID) {
		USER_CLIENT_ID = uSER_CLIENT_ID;
	}

	public String getDATAS_TYPE() {
		return DATAS_TYPE;
	}

	public void setDATAS_TYPE(String dATAS_TYPE) {
		DATAS_TYPE = dATAS_TYPE;
	}

	public String getMESSAGE_ID() {
		return MESSAGE_ID;
	}

	public void setMESSAGE_ID(String mESSAGE_ID) {
		MESSAGE_ID = mESSAGE_ID;
	}

	public String getTIME_STMP() {
		return TIME_STMP;
	}

	public void setTIME_STMP(String tIME_STMP) {
		TIME_STMP = tIME_STMP;
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
