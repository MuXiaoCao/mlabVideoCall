package com.mlab.xiaocao.vo;

public class ServerMessage {
	private String messageID;
	private double audioDelay;
	private double audioJitter;
	private double reactionDelay;
	private int novasynCount;
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
