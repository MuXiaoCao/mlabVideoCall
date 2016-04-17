package com.mlab.xiaocao.vo;

public class ServerMessage {
	/*
	 * 消息id
	 */
	private String messageID;
	/**
	 * 音頻時延
	 */
	private double audioDelay;
	/**
	 * 音频抖动
	 */
	private double audioJitter;
	/**
	 * 交互时延
	 */
	private double reactionDelay;
	/**
	 * 音视频不同步次数
	 */
	private int novasynCount;
	/**
	 * 音视频不同步时延
	 */
	private double novasynDelay;
	

	public ServerMessage() {
	}
	public ServerMessage(String messageID) {
		this.messageID = messageID;
	}
	public String getMessageID() {
		return messageID;
	}
	public void setMessageID(String messageID) {
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
