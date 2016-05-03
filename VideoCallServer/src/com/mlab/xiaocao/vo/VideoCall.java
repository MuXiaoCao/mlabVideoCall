package com.mlab.xiaocao.vo;

import java.util.HashMap;

import org.json.JSONObject;

import com.mlab.xiaocao.util.DataUtil;

public class VideoCall {

	private User srcUser;
	private User targetUser;
	private ServerMessage serverMessage;
	private long timeStampMySelf1 = 0L;
	private long timeStampMySelf2 = 0L;
	private boolean isStop = false;

	public VideoCall(int srcUserID, int targetUserID) {
		srcUser = new User(srcUserID);
		targetUser = new User(targetUserID);
		serverMessage = new ServerMessage();
	}

	public void setIsStop(boolean flag) {
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

	/* ===============���ݵ����================ */
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
		/**
		 * ������Ӧʱ��
		 */
		long time = System.currentTimeMillis();
		if (timeStampMySelf1 == 0) {
			timeStampMySelf1 = time;
		} else {
			timeStampMySelf2 = time - timeStampMySelf1;
			timeStampMySelf1 = time;
		}
		if (clientBean.getUserID() == srcUser.getID()) {
			/**
			 * ���öԷ��û���һ�η���ʱ�䣬��������Ϊ��׼������messageID
			 */
			if (clientBean.getMessageID() == 0
					&& clientBean.getDatastype() == DataUtil.SendVideoInfo) {
				// ����Ƿ����û���һ�η�����Ƶ���ݣ�����Ҫ�Ѵ�ʱ���Ŀ���û�
				srcUser.videoStartTime = clientBean.getTimestmp();
			} else if (clientBean.getMessageID() == 0
					&& clientBean.getDatastype() == DataUtil.SendAudioInfo) {
				// ����Ƿ����û���һ�η�����Ƶ���ݣ�����Ҫ�Ѵ�ʱ���Ŀ���û�
				srcUser.audioStartTime = clientBean.getTimestmp();
			}
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

		} else if (clientBean.getUserID() == targetUser.getID()) {
			/**
			 * ���öԷ��û���һ�η���ʱ�䣬��������Ϊ��׼������messageID
			 */
			if (clientBean.getMessageID() == 0
					&& clientBean.getDatastype() == DataUtil.SendVideoInfo) {
				// ����Ƿ����û���һ�η�����Ƶ���ݣ�����Ҫ�Ѵ�ʱ���Ŀ���û�
				targetUser.videoStartTime = clientBean.getTimestmp();
			} else if (clientBean.getMessageID() == 0
					&& clientBean.getDatastype() == DataUtil.SendAudioInfo) {
				// ����Ƿ����û���һ�η�����Ƶ���ݣ�����Ҫ�Ѵ�ʱ���Ŀ���û�
				targetUser.audioStartTime = clientBean.getTimestmp();
			}
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

	/* ===============���ݵĴ���================ */

	/* =========ʱ��========== */
	private int srcDelay = -1 ;
	private int targetDelay = -1;
	private int srcCurrentDelay = -1;
	private int targetCurrentDelay = -1;
	private int srcCurrentVideoDelay = -1;
	private int targetCurrentVideoDelay = -1;
	
	private int srcUserNovasynCount = 0;
	private int targetUserNovasynCount = 0;

	/**
	 * ������Ƶʱ�� ��Ӧ��messageID������õ���Ӧ��ʱ�� �����Ϊ��ǰ��Ƶʱ�ӣ�������շ�û�еĲ������
	 * 
	 * @return
	 */
	public int getSrcUserAudioDelay() {

		int messageID = targetUser.currentSendAudioMessageID;
		int messageID1 = srcUser.currentReceiveAudioMessageID;
		if (messageID == -1 || messageID1 == -1) {
			return 0;
		}
	
		if (messageID == messageID1) {
			return srcCurrentDelay = (int) (srcUser.receiveAudioTimeStmp.get(messageID1) - targetUser.sendAudioTimeStmp
					.get(messageID));
		} else {
			return srcCurrentDelay = (int) (System.currentTimeMillis() - targetUser.sendAudioTimeStmp
					.get(messageID));
		}
	}

	/**
	 * ���շ���Ƶʱ�� ��Ӧ��messageID������õ���Ӧ��ʱ�� �����Ϊ��ǰ��Ƶʱ�ӣ�������շ�û�еĲ������
	 * 
	 * @return
	 */
	public int getTargetUserAudioDelay() {
		int messageID = srcUser.currentSendAudioMessageID;
		int messageID1 = targetUser.currentReceiveAudioMessageID;
		if (messageID == -1 || messageID1 == -1) {
			return 0;
		}
		if (messageID == messageID1) {
			return targetCurrentDelay = (int) (targetUser.receiveAudioTimeStmp.get(messageID1) - srcUser.sendAudioTimeStmp
					.get(messageID));
		} else {
			return targetCurrentDelay = (int) (System.currentTimeMillis() - srcUser.sendAudioTimeStmp
					.get(messageID));
		}
	}

	/* =========����========== */
	/**
	 * ���򷽵Ķ��� ʵʱ�Ķ�����ֻ����һ�ε���Ƶʱ���й�
	 * 
	 * @return
	 */
	public int getSrcUserJitter() {
		int delay = 0;
		if (srcDelay == -1) {
			srcDelay = srcCurrentDelay;
			return 0;
		}else {
			delay = Math.abs(srcCurrentDelay - srcDelay);
			srcDelay = srcCurrentDelay;
			return delay;
		}
	}

	/**
	 * ���շ��Ķ��� ʵʱ�Ķ�����ֻ����һ�ε���Ƶʱ���й�
	 * 
	 * @return
	 */
	public int getTargetUserJitter() {
		int delay = 0;
		if (targetDelay == -1) {
			targetDelay = targetCurrentDelay;
			return 0;
		}else {
			delay = Math.abs(targetCurrentDelay - targetDelay);
			targetDelay = targetCurrentDelay;
			return delay;
		}
	}

	/* =========����ʱ��========== */
	/**
	 * ���򷽽���ʱ�� ͨ��������Ƶ��
	 * 
	 * @return
	 */
	public int getSrcUserReactionDelay() {
		
		return getSrcUserVideoDelay();
	}

	/**
	 * ���ս���ʱ��
	 * 
	 * @return
	 */
	public int getTargetUserReactionDelay() {
		
		return getTargetUserVideoDelay();
	}

	/* =========����Ƶ��ͬ������========== */
	/**
	 * ���ͷ�����Ƶ����ͬ������
	 * 
	 * @return
	 */
	public int getSrcUserNovasynCount() {

		int sum = Math.abs(srcUser.receiveAudioTimeStmp.size()
				- srcUser.receiveVideoTimeStmp.size());
		srcUserNovasynCount = sum;
		return sum;
	}

	/**
	 * ���շ�����Ƶ����ͬ������
	 * 
	 * @return
	 */
	public int getTargetUserNovasynCount() {

		int sum = Math.abs(targetUser.receiveAudioTimeStmp.size()
				- targetUser.receiveVideoTimeStmp.size());
		targetUserNovasynCount = sum;
		return sum;
	}

	/* =========����Ƶ��ͬ��ʱ��========== */
	/**
	 * ���ͷ�����Ƶ����ͬ��ʱ��
	 * 
	 * @return
	 */
	public int getSrcUserNovasynDelay() {
		return srcUserNovasynCount * 100;
	}

	/**
	 * ���շ�����Ƶ����ͬ��ʱ��
	 * 
	 * @return
	 */
	public int getTargetUserNovasynDelay() {

		return targetUserNovasynCount * 100;
	}

	/* =====================��֡����======================== */
	/**
	 * ��֡����
	 * 
	 * @return
	 */
	private int getSrcUserLossFrameCount() {

		return Math.abs(targetUser.sendVideoTimeVideoStmp.size()
				- srcUser.receiveVideoTimeStmp.size());
	}

	private int getTargetUserLossFrameCount() {

		return Math.abs(srcUser.sendVideoTimeVideoStmp.size()
				- targetUser.receiveVideoTimeStmp.size());
	}
	
	/*====================��Ƶʱ��=========================*/
	/**
	 * ������Ƶʱ�� ��Ӧ��messageID������õ���Ӧ��ʱ�� �����Ϊ��ǰ��Ƶʱ�ӣ�������շ�û�еĲ������
	 * 
	 * @return
	 */
	public int getSrcUserVideoDelay() {

		int messageID = targetUser.currentSendVideoMessageID;
		int messageID1 = srcUser.currentReceiveVideoMessageID;
		if (messageID == -1 || messageID1 == -1) {
			return 0;
		}
		if (messageID == messageID1) {
			return srcCurrentVideoDelay = (int) (srcUser.receiveVideoTimeStmp.get(messageID1) - targetUser.sendVideoTimeVideoStmp
					.get(messageID));
		} else {
			return srcCurrentVideoDelay = (int) (System.currentTimeMillis() - targetUser.sendVideoTimeVideoStmp
					.get(messageID));
		}
	}

	/**
	 * ���շ���Ƶʱ�� ��Ӧ��messageID������õ���Ӧ��ʱ�� �����Ϊ��ǰ��Ƶʱ�ӣ�������շ�û�еĲ������
	 * 
	 * @return
	 */
	public int getTargetUserVideoDelay() {
		int messageID = srcUser.currentSendVideoMessageID;
		int messageID1 = targetUser.currentReceiveVideoMessageID;
		if (messageID == -1 || messageID1 == -1) {
			return 0;
		}
		if (messageID == messageID1) {
			return targetCurrentVideoDelay = (int) (targetUser.receiveVideoTimeStmp.get(messageID1) - srcUser.sendVideoTimeVideoStmp
					.get(messageID));
		} else {
			return targetCurrentVideoDelay = (int) (System.currentTimeMillis() - srcUser.sendVideoTimeVideoStmp
					.get(messageID));
		}
	}
	
	
	/* =======================���ݷ���======================= */
	/**
	 * ���ͷ�����
	 * 
	 * @return
	 */
	public ServerMessage getSrcUserServerMessage() {

		serverMessage.setMessageID(srcUser.getMessageID());
		serverMessage.setAudioDelay((double) (getSrcUserAudioDelay()) / 1000.0);
		serverMessage.setAudioJitter((double) (getSrcUserJitter()) / 1000.0);
		serverMessage.setNovasynCount(getSrcUserNovasynCount());
		serverMessage
				.setNovasynDelay((double) (getSrcUserNovasynDelay()) / 1000.0);
		serverMessage
				.setReactionDelay((double) (getSrcUserReactionDelay()) / 1000.0);
		serverMessage.setLoss_frame_count(getSrcUserLossFrameCount());
		setResultData(srcUser, serverMessage.getAudioJitter(), serverMessage.getAudioDelay(), serverMessage.getNovasynCount(),serverMessage.getReactionDelay());
		return serverMessage;
	}

	/**
	 * ���շ�����
	 * 
	 * @return
	 */
	public ServerMessage getTargetUserServerMessage() {

		serverMessage.setMessageID(targetUser.getMessageID());
		serverMessage
				.setAudioDelay((double) getTargetUserAudioDelay() / 1000.0);
		serverMessage.setAudioJitter((double) getTargetUserJitter() / 1000.0);
		serverMessage.setNovasynCount(getTargetUserNovasynCount());
		serverMessage
				.setNovasynDelay((double) getTargetUserNovasynDelay() / 1000.0);
		serverMessage
				.setReactionDelay((double) getTargetUserReactionDelay() / 1000.0);
		serverMessage.setLoss_frame_count(getTargetUserLossFrameCount());
		setResultData(targetUser, serverMessage.getAudioJitter(), serverMessage.getAudioDelay(), serverMessage.getNovasynCount(),serverMessage.getReactionDelay());
		return serverMessage;
	}

	/**
	 * �ռ��ϴ�����
	 */
	private void setResultData(User user,double jitter,double audioDelay,int novasynCount,double reactionDelay) {
		
		user.audioJitterSum += jitter;
		user.audioDelaySum += audioDelay;
		user.novasynCountSum += novasynCount;
		user.reactionDelaySum += reactionDelay;
	}
	
	
	/**
	 * �ж��Ƿ�ʱ ��Ϊ�ж���Ӧʱ�����һ���ӣ���Ϊ��ʱ
	 */
	public boolean isTimeOut() {
		if (timeStampMySelf2 > DataUtil.TIME_OUT) {
			return true;
		}
		return false;
	}

	/**
	 * �ж��Ƿ����ͨ�� �����ʱ���߹ҶϾ���Ϊ����ͨ��������true
	 * 
	 * @return
	 */
	public boolean isStop() {
		return isStop || isTimeOut();
	}

	/**
	 * ��Դ�ͷ�
	 * @param b true ��ʾ�ͷŷ��𷽣�false��ʾ�ͷŽ��շ�
	 */
	public void destory(int userID) {
		if (userID==srcUser.getID()) {
			srcUser.destory();
		}else {
			targetUser.destory();
		}
	}

	public/* =======================���ݴ洢======================= */
	class User {

		public User(int id) {
			this.ID = id;
			messageID = 0;
			receiveAudioTimeStmp = new HashMap<Integer, Long>();
			receiveVideoTimeStmp = new HashMap<Integer, Long>();
			sendAudioTimeStmp = new HashMap<Integer, Long>();
			sendVideoTimeVideoStmp = new HashMap<Integer, Long>();
			audioJitterSum = 0;
			audioDelaySum = 0;
			novasynCountSum = 0;
			audioDelaySum = 0;
		}

		private int ID;
		private int messageID;
		private long videoStartTime = 0;
		private long audioStartTime = 0;
		private int currentReceiveVideoMessageID = -1;
		private int currentSendVideoMessageID = -1;
		private int currentReceiveAudioMessageID = -1;
		private int currentSendAudioMessageID = -1;
		private HashMap<Integer, Long> receiveVideoTimeStmp;
		private HashMap<Integer, Long> sendVideoTimeVideoStmp;
		private HashMap<Integer, Long> receiveAudioTimeStmp;
		private HashMap<Integer, Long> sendAudioTimeStmp;
		/**
		 * �ܵĶ���ʱ��
		 */
		private double audioJitterSum;
		/**
		 * �ܵ���Ƶʱ��
		 */
		private double audioDelaySum;
		/**
		 * �ܵĲ�ͬ������
		 */
		private int novasynCountSum;
		/**
		 * ����ʱ��
		 */
		private double reactionDelaySum;
		
		public double getAudioJitterSum() {
			return audioJitterSum;
		}

		public void setAudioJitterSum(double audioJitterSum) {
			this.audioJitterSum = audioJitterSum;
		}

		public double getAudioDelaySum() {
			return audioDelaySum;
		}

		public void setAudioDelaySum(double audioDelaySum) {
			this.audioDelaySum = audioDelaySum;
		}

		public int getNovasynCountSum() {
			return novasynCountSum;
		}

		public void setNovasynCountSum(int novasynCountSum) {
			this.novasynCountSum = novasynCountSum;
		}

		public double getReactionDelaySum() {
			return reactionDelaySum;
		}

		public void setReactionDelaySum(double reactionDelaySum) {
			this.reactionDelaySum = reactionDelaySum;
		}

		public void setAudioStartTime(long time) {
			audioStartTime = time;
		}

		public int getID() {
			return ID;
		}

		public void setID(int iD) {
			ID = iD;
		}

		public int getMessageID() {
			messageID++;
			return messageID;
		}

		public void setMessageID(int messageID) {
			this.messageID = messageID;
		}

		/* ==================���ݵ����=================== */
		public void addReceiveAudioTimeStamp(int messageID, long timeStamp) {
			// ����ʵ�ʽ��յ�����ƵmessageID
			if (audioStartTime != 0) {
				int x = (int) (timeStamp - audioStartTime) / 100 + 1;
				if (x > messageID) {
					messageID = x;
				}
			}
			currentReceiveAudioMessageID = messageID;
			receiveAudioTimeStmp.put(messageID, timeStamp);
		}

		public void addSendVideoTimeStamp(int messageID, long timeStamp) {
			currentSendVideoMessageID = messageID;
			sendVideoTimeVideoStmp.put(messageID, timeStamp);
		}

		public void addSendAudioTimeStamp(int messageID, long timeStamp) {
			currentSendAudioMessageID = messageID;
			sendAudioTimeStmp.put(messageID, timeStamp);
		}

		public void addReceiveVideoStamp(int messageID, long timeStamp) {
			// ����ʵ�ʽ��յ�����ƵmessageID
			if (videoStartTime != 0) {
				int x = (int) (timeStamp - videoStartTime) / 100 + 1;
				if (x > messageID) {
					messageID = x;
				}
			}
			currentReceiveVideoMessageID = messageID;
			receiveVideoTimeStmp.put(messageID, timeStamp);
		}

		public void destory() {
			receiveVideoTimeStmp = null;
			sendVideoTimeVideoStmp = null;
			receiveAudioTimeStmp = null;
			sendAudioTimeStmp = null;
		}
	}
}
