package com.mlab.xiaocao.vo;

import java.util.ArrayList;



public class VideoCall {
	
	private ClientBean srcUser;
	private ClientBean targetUser;

	
	
	class User {
		
		private String ID;
		private ArrayList<Long> receiveVideoTimeStmp;
		private ArrayList<Long> sendVideoTimeVideoStmp;
 		private ArrayList<Long> receiveAudioTimeStmp;
 		private ArrayList<Long> sendAudioTimeStmp;
	}
}
