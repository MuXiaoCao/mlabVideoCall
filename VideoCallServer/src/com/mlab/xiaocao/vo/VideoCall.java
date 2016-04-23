package com.mlab.xiaocao.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import com.mlab.xiaocao.util.DataUtil;

public class VideoCall {

	private User srcUser;
	private User targetUser;
	private ServerMessage serverMessage;
	private long timeStampMySelf1 = 0L;
	private long timeStampMySelf2 = 0L;
	private boolean isStop = false;

	public VideoCall(String srcUserID, String jmsKey1, String targetUserID,
			String jmsKey2) {
		srcUser = new User(srcUserID, jmsKey1);
		targetUser = new User(targetUserID, jmsKey2);
		serverMessage = new ServerMessage();
		srcDelay = new ArrayList<Integer>();
		targetDelay = new ArrayList<Integer>();
	}
	
	public void setIsStop (boolean flag) {
		isStop = flag;
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

	/* ===============数据的添加================ */
	private void addSrcUserSendVideoInfo(int messageID, long timeStamp) {
		srcUser.addSendVideoTimeStamp(messageID, timeStamp);
	}

	private void addTargetUserSendVideoInfo(int messageID, long timeStamp) {
		targetUser.addSendVideoTimeStamp(messageID, timeStamp);
	}

	private void addSrcUserReceiveVideoInfo(int messageID, long timeStamp) {
		srcUser.addReceiveVideoStamp(messageID, timeStamp);
	}

	private void addTargetUserReceiveVideoInfo(int messageID, long timeStamp) {
		targetUser.addReceiveVideoStamp(messageID, timeStamp);
	}

	private void addSrcUserSendAudioInfo(int messageID, long timeStamp) {
		srcUser.addSendAudioTimeStamp(messageID, timeStamp);
	}

	private void addTargetUserSendAudioInfo(int messageID, long timeStamp) {
		targetUser.addSendAudioTimeStamp(messageID, timeStamp);
	}

	private void addSrcUserReceiveAudioInfo(int messageID, long timeStamp) {
		srcUser.addReceiveAudioTimeStamp(messageID, timeStamp);
	}

	private void addTargetUserReceiveAudioInfo(int messageID, long timeStamp) {
		targetUser.addReceiveAudioTimeStamp(messageID, timeStamp);
	}

	public void setClientData(ClientBean clientBean) {

		long time = System.currentTimeMillis();
		if (timeStampMySelf1 == 0) {
			timeStampMySelf1 = time;
		} else {
			timeStampMySelf2 = time - timeStampMySelf1;
			timeStampMySelf1 = time;
		}
		if (clientBean.getUserID().equals(srcUser.getID())) {
			switch (clientBean.getDatastype()) {
			case DataUtil.SendVideoInfo:
				addSrcUserSendVideoInfo(clientBean.getMessageID(),
						clientBean.getTimestmp());
				break;
			case DataUtil.SendAudioInfo:
				addSrcUserSendAudioInfo(clientBean.getMessageID(),
						clientBean.getTimestmp());
				break;
			case DataUtil.ReceiveVideoInfo:
				addSrcUserReceiveVideoInfo(clientBean.getMessageID(),
						clientBean.getTimestmp());
				break;
			case DataUtil.ReceiveAudioInfo:
				addSrcUserReceiveAudioInfo(clientBean.getMessageID(),
						clientBean.getTimestmp());
				break;
			default:
				break;
			}

		} else if (clientBean.getUserID().equals(srcUser.getID())) {
			switch (clientBean.getDatastype()) {
			case DataUtil.SendVideoInfo:
				addTargetUserSendVideoInfo(clientBean.getMessageID(),
						clientBean.getTimestmp());
				break;
			case DataUtil.SendAudioInfo:
				addTargetUserSendAudioInfo(clientBean.getMessageID(),
						clientBean.getTimestmp());
				break;
			case DataUtil.ReceiveVideoInfo:
				addTargetUserReceiveVideoInfo(clientBean.getMessageID(),
						clientBean.getTimestmp());
				break;
			case DataUtil.ReceiveAudioInfo:
				addTargetUserReceiveAudioInfo(clientBean.getMessageID(),
						clientBean.getTimestmp());
				break;
			default:
				break;
			}

		}

	}

	/* ===============数据的处理================ */

	/* =========时延========== */
	public ArrayList<Integer> srcDelay;
	public ArrayList<Integer> targetDelay;

	/**
	 * 拨打方音频时延
	 * 
	 * @return
	 */
	public int getSrcUserAudioDelay() {
		int delay = 0;
		Set<Integer> keySet = targetUser.sendAudioTimeStmp.keySet();
		for (Integer integer : keySet) {
			if (srcUser.receiveAudioTimeStmp.containsKey(integer)) {
				delay = (int) (srcUser.receiveAudioTimeStmp.get(integer) - targetUser.sendAudioTimeStmp
						.get(integer));
				srcDelay.add(delay);
			}
		}
		return delay;
	}

	/**
	 * 接收方音频时延
	 * 
	 * @return
	 */
	public int getTargetUserAudioDelay() {
		Set<Integer> keySet = srcUser.sendAudioTimeStmp.keySet();
		int delay = 0;
		for (Integer integer : keySet) {
			if (targetUser.receiveAudioTimeStmp.containsKey(integer)) {
				delay = (int) (targetUser.receiveAudioTimeStmp.get(integer) - srcUser.sendAudioTimeStmp
						.get(integer));
				targetDelay.add(delay);
			}
		}
		return delay;
	}

	/* =========抖动========== */
	/**
	 * 拨打方的抖动
	 * 
	 * @return
	 */
	public int getSrcUserJitter() {
		int jitter = 0;
		for (int i = 1; i < srcDelay.size() - 1; i++) {
			jitter += srcDelay.get(i) - srcDelay.get(i - 1);
		}
		return jitter;
	}

	/**
	 * 接收方的抖动
	 * 
	 * @return
	 */
	public int getTargetUserJitter() {
		int jitter = 0;
		for (int i = 1; i < targetDelay.size() - 1; i++) {
			jitter += targetDelay.get(i) - targetDelay.get(i - 1);
		}
		return jitter;
	}

	/* =========交互时延========== */
	/**
	 * 拨打方交互时延
	 * 
	 * @return
	 */
	public int getSrcUserReactionDelay() {
		int delay = 0;
		Set<Integer> keySet = targetUser.sendVideoTimeVideoStmp.keySet();
		for (Integer integer : keySet) {
			if (srcUser.receiveVideoTimeStmp.containsKey(integer)) {
				delay = (int) (srcUser.receiveVideoTimeStmp.get(integer) - targetUser.sendVideoTimeVideoStmp
						.get(integer));
				srcDelay.add(delay);
			}
		}
		return delay;
	}

	/**
	 * 接收交互时延
	 * 
	 * @return
	 */
	public int getTargetUserReactionDelay() {
		int delay = 0;
		Set<Integer> keySet = srcUser.sendVideoTimeVideoStmp.keySet();
		for (Integer integer : keySet) {
			if (targetUser.receiveVideoTimeStmp.containsKey(integer)) {
				delay = (int) (targetUser.receiveVideoTimeStmp.get(integer) - srcUser.sendVideoTimeVideoStmp
						.get(integer));
				srcDelay.add(delay);
			}
		}
		return delay;
	}

	/* =========音视频不同步次数========== */
	/**
	 * 发送方音视频过不同步次数
	 * 
	 * @return
	 */
	public int getSreUserNovasynCount() {
		Set<Integer> audiokeySet = targetUser.sendAudioTimeStmp.keySet();
		Set<Integer> videokeyset = targetUser.sendVideoTimeVideoStmp.keySet();

		return 0;
	}

	/**
	 * 接收方音视频过不同步次数
	 * 
	 * @return
	 */
	public int getTargetUserNovasynCount() {
		Set<Integer> audiokeySet = targetUser.sendAudioTimeStmp.keySet();
		Set<Integer> videokeyset = targetUser.sendVideoTimeVideoStmp.keySet();

		return 0;
	}

	/* =========音视频不同步时延========== */
	/**
	 * 发送方音视频过不同步时延
	 * 
	 * @return
	 */
	public int getSreUserNovasynDelay() {
		Set<Integer> audiokeySet = targetUser.sendAudioTimeStmp.keySet();
		Set<Integer> videokeyset = targetUser.sendVideoTimeVideoStmp.keySet();

		return 0;
	}

	/**
	 * 接收方音视频过不同步时延
	 * 
	 * @return
	 */
	public int getTargetUserNovasynDelay() {
		Set<Integer> audiokeySet = targetUser.sendAudioTimeStmp.keySet();
		Set<Integer> videokeyset = targetUser.sendVideoTimeVideoStmp.keySet();

		return 0;
	}

	/* =======================数据发送======================= */
	/**
	 * 发送方数据
	 * 
	 * @return
	 */
	public ServerMessage getSrcUserServerMessage() {

		srcDelay.clear();
		targetDelay.clear();
		serverMessage.setMessageID(srcUser.getMessageID());
		serverMessage.setAudioDelay(getSrcUserAudioDelay());
		serverMessage.setAudioJitter(getSrcUserJitter());
		serverMessage.setNovasynCount(getSreUserNovasynCount());
		serverMessage.setNovasynDelay(getSrcUserAudioDelay());
		serverMessage.setReactionDelay(getSrcUserReactionDelay());

		return serverMessage;
	}
	
	/**
	 * 判断是否超时
	 * 人为判断响应时间大于一分钟，认为超时
	 */
	public boolean isTimeOut() {
		if (timeStampMySelf2 > DataUtil.TIME_OUT) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断是否结束通话
	 * 如果超时或者挂断均判为结束通话，返回true
	 * @return
	 */
	public boolean isStop() {
		return isStop || isTimeOut();
	}
	
	/**
	 * 资源释放
	 */
	public void destory() {
		srcDelay = null;
		targetDelay = null;
		srcUser.destory();
		targetUser.destory();
	}

	/**
	 * 接收方数据
	 * 
	 * @return
	 */
	public ServerMessage getTargetUserServerMessage() {

		srcDelay.clear();
		targetDelay.clear();
		serverMessage.setMessageID(targetUser.getMessageID());
		serverMessage.setAudioDelay(getTargetUserAudioDelay());
		serverMessage.setAudioJitter(getTargetUserJitter());
		serverMessage.setNovasynCount(getTargetUserNovasynCount());
		serverMessage.setNovasynDelay(getTargetUserAudioDelay());
		serverMessage.setReactionDelay(getTargetUserReactionDelay());
		
		return serverMessage;
	}

	public/* =======================数据存储======================= */
	class User {

		public User(String id, String jmsID) {
			this.ID = id;
			this.jmsID = jmsID;
			messageID = 0;
			receiveAudioTimeStmp = new HashMap<Integer, Long>();
			receiveVideoTimeStmp = new HashMap<Integer, Long>();
			sendAudioTimeStmp = new HashMap<Integer, Long>();
			sendVideoTimeVideoStmp = new HashMap<Integer, Long>();
		}

		private String ID;
		private String jmsID;
		private int messageID;
		private HashMap<Integer, Long> receiveVideoTimeStmp;
		private HashMap<Integer, Long> sendVideoTimeVideoStmp;
		private HashMap<Integer, Long> receiveAudioTimeStmp;
		private HashMap<Integer, Long> sendAudioTimeStmp;

		public String getID() {
			return ID;
		}

		public void setID(String iD) {
			ID = iD;
		}

		public String getJmsID() {
			return jmsID;
		}

		public void setJmsID(String jmsID) {
			this.jmsID = jmsID;
		}

		public int getMessageID() {
			messageID++;
			return messageID;
		}

		public void setMessageID(int messageID) {
			this.messageID = messageID;
		}

		/* ==================数据的添加=================== */
		public void addReceiveAudioTimeStamp(int messageID, long timeStamp) {
			receiveAudioTimeStmp.put(messageID, timeStamp);
		}

		public void addSendVideoTimeStamp(int messageID, long timeStamp) {
			sendVideoTimeVideoStmp.put(messageID, timeStamp);
		}

		public void addSendAudioTimeStamp(int messageID, long timeStamp) {
			sendAudioTimeStmp.put(messageID, timeStamp);
		}

		public void addReceiveVideoStamp(int messageID, long timeStamp) {
			receiveVideoTimeStmp.put(messageID, timeStamp);
		}

		public void destory() {
			receiveVideoTimeStmp = null;
			sendVideoTimeVideoStmp= null;
			receiveAudioTimeStmp= null;
			sendAudioTimeStmp= null;
		}
	}
}
