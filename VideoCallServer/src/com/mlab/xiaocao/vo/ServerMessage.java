package com.mlab.xiaocao.vo;

public class ServerMessage {
	/*
	 * ��Ϣid
	 */
	private int messageID;
	/**
	 * ���l�r��
	 */
	private double audioDelay;
	/**
	 * ��Ƶ����
	 */
	private double audioJitter;
	/**
	 * ����ʱ��
	 */
	private double reactionDelay;
	/**
	 * ����Ƶ��ͬ������
	 */
	private int novasynCount;
	/**
	 * ����Ƶ��ͬ��ʱ��
	 */
	private double novasynDelay;
	

	public ServerMessage() {
	}
	public ServerMessage(int messageID) {
		this.messageID = messageID;
	}
	public int getMessageID() {
		return messageID;
	}
	public void setMessageID(int messageID) {
		this.messageID = messageID;
	}
	public double getAudioDelay() {
		return audioDelay;
	}
	public void setAudioDelay(double audioDelay) {
		this.audioDelay = audioDelay;
	}
	public double getAudioJitter() {
		return audioJitter;
	}
	public void setAudioJitter(double audioJitter) {
		this.audioJitter = audioJitter;
	}
	public double getReactionDelay() {
		return reactionDelay;
	}
	public void setReactionDelay(double reactionDelay) {
		this.reactionDelay = reactionDelay;
	}
	public int getNovasynCount() {
		return novasynCount;
	}
	public void setNovasynCount(int novasynCount) {
		this.novasynCount = novasynCount;
	}
	public double getNovasynDelay() {
		return novasynDelay;
	}
	public void setNovasynDelay(double novasynDelay) {
		this.novasynDelay = novasynDelay;
	}

}
