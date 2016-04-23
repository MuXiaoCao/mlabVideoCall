package com.mlab.xiaocao.util;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

/**
 * 基于ActiveMQ的JMS封装类
 * 
 * @author 木小草
 *
 */
public interface VideoCallJMS extends MessageListener{
	
	public abstract void setDestination(String destination);
	
	/**
	 * 发送Text的消息，method指定消息发送方式
	 * @param msgContext
	 * @param method true：queue flase：topic
	 * @return
	 */
	public abstract boolean sendTextMessage(String msgContext,String destination);
	public abstract boolean sendTextMessage(String msgContext,boolean method,String destination);
	
	/**
	 * 接收Text消息，method指定接收方式
	 * @param method method true：queue flase：topic
	 * @return
	 */
	public abstract String receiveTextMessage(String destination);
	public abstract String receiveTextMessage(boolean method,String destination);
	/**
	 * 启用监听模式接收message
	 * @param callBack
	 */
	public abstract void receiveMessageByListener(String destination,MessageCallBack callBack);
	
	/**
	 * 发送Map消息，method指定接收方式
	 * @param mapContext
	 * @param method method true：queue flase：topic
	 * @return
	 */
	public abstract boolean sendMapMessage(MapMessage mapContext,String destination);
	public abstract boolean sendMapMessage(MapMessage mapContext,boolean method,String destination);
	
	/**
	 * 接收Map的消息，method指定消息发送方式
	 * @param method true：queue flase：topic
	 * @return
	 */
	public abstract MapMessage receiveMapMessage(String destination);
	public abstract MapMessage receiveMapMessage(boolean method,String destination);
	
	/**
	 * 发送Object的消息，method指定消息发送方式
	 * @param objectContext
	 * @param method true：queue flase：topic
	 * @return
	 * @throws JMSException 
	 */
	public abstract boolean sendObjectMessage(Object objectContext,String destination);
	public abstract boolean sendObjectMessage(Object objectContext,boolean method,String destination);
	
	/**
	 * 接收Object的消息，method指定消息发送方式
	 * @param method true：queue flase：topic
	 * @return
	 */
	public abstract ObjectMessage receiveObjectMessage(String destination);
	public abstract ObjectMessage receiveObjectMessage(boolean method,String destination);

	
	
	/**
	 * 结束处理
	 * @param producer
	 * @param consumer
	 * @param session
	 * @param conn
	 */
	public abstract void closeJMS(MessageProducer producer,MessageConsumer consumer,Session session,Connection conn);

	/**
	 * 监听器回调函数
	 * @author 木小草
	 *
	 */
	public interface MessageCallBack {

		public abstract void receiveMessage(Message message);
		/**
		 * 是否停止监听
		 * true  表示停止   false 表示继续监听
		 * @return
		 */
		public abstract boolean isStopReceiveMessage();
		
	}
}
