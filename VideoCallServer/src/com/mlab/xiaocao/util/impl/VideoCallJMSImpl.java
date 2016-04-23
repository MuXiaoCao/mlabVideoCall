package com.mlab.xiaocao.util.impl;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQDestination;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.mlab.xiaocao.util.VideoCallJMS;

@Service("videoCallJMS")
public class VideoCallJMSImpl implements VideoCallJMS{


	@Resource(name="pooledConnectionFactory")
	private ConnectionFactory factory;
	
	
	private Connection conn;
	private Session session;
	private Message msg;
	private MessageProducer producer;
	private MessageConsumer consumer;
	private String destination = "admin";
	
	
	public VideoCallJMSImpl() {
		factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
	}
	
	public VideoCallJMSImpl(String destinationName) {
		factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		this.destination = destinationName;
	}
	

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}


	public boolean sendTextMessage(String msgContext,String destination) {
	
		return sendTextMessage(msgContext, true,destination);
	}

	public String receiveTextMessage(String destination) {
		
		return receiveTextMessage(true,destination);
	}

	public boolean sendMapMessage(MapMessage mapContext,String destination) {
		
		return sendMapMessage(mapContext, true,destination);
	}

	public MapMessage receiveMapMessage(String destination) {
		
		return receiveMapMessage(true,destination);
	}

	public boolean sendObjectMessage(Object objectContext,String destination) {
		
		return sendObjectMessage(objectContext, true,destination);
	}

	public ObjectMessage receiveObjectMessage(String destination) {
		
		return receiveObjectMessage(true,destination);
	}

	
	public void onMessage(Message message) {
		
		
	}
	
	
	public boolean sendTextMessage(String context,boolean method,String destination) {
		
		boolean flag = false;
		try {
			conn = factory.createConnection();
			conn.start();
			session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			msg = session.createTextMessage(context);
			if (method == true) {
				producer = session.createProducer(session.createQueue(destination));
			}else {
				producer = session.createProducer(session.createTopic(destination));
			}
			producer.send(msg);
			flag = true;
		} catch (JMSException e) {
			e.printStackTrace();
		} 
		return flag;
	}




	public boolean sendMapMessage(MapMessage mapContext, boolean method,String destination) {

		try {
			conn = factory.createConnection();
			session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			if (method == true) {
				producer = session.createProducer(session.createQueue(destination));
			}else {
				producer = session.createProducer(session.createTopic(destination));
			}
			producer.send(mapContext);  
			return true;
		} catch (JMSException e) {
			e.printStackTrace();
		} 
		return false;
	}
	

	public boolean sendObjectMessage(Object objectContext, boolean method,String destination) {
		try {
			conn = factory.createConnection();
			session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			ObjectMessage objMsg=session.createObjectMessage((Serializable) objectContext);//发�?对象时必须让该对象实现serializable接口  
			if (method == true) {
				producer = session.createProducer(session.createQueue(destination));
			}else {
				producer = session.createProducer(session.createTopic(destination));
			}  
			producer.send(objMsg);
			return true;
		} catch (JMSException e) {
			e.printStackTrace();
		} 
		return false;
	}


	public String receiveTextMessage(boolean method,String destination) {
		
		String text = null;
		try {
			conn = factory.createConnection();
			session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			conn.start();
			if (method == true) {
				consumer = session.createConsumer(session.createQueue(destination));
			}else {
				consumer = session.createConsumer(session.createTopic(destination));
			}
			msg = consumer.receive();
			if (msg instanceof TextMessage) {
				text =  ((TextMessage) msg).getText();
			}
			
		} catch (JMSException e) {
			e.printStackTrace();
		} 
		
		return text;
	}

	public MapMessage receiveMapMessage(boolean method,String destination) {
		MapMessage mapMessage = null;
		try {
			conn = factory.createConnection();
			session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			conn.start();
			if (method == true) {
				consumer = session.createConsumer(session.createQueue(destination));
			}else {
				consumer = session.createConsumer(session.createTopic(destination));
			}
			msg = consumer.receive();
			if (msg instanceof MapMessage) {
				mapMessage = (MapMessage)msg;
			}
			
		} catch (JMSException e) {
			e.printStackTrace();
		} 
		
		return mapMessage;
	}



	public ObjectMessage receiveObjectMessage(boolean method,String destination) {
		ObjectMessage message = null;
		try {
			conn = factory.createConnection();
			session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			conn.start();
			if (method == true) {
				consumer = session.createConsumer(session.createQueue(destination));
			}else {
				consumer = session.createConsumer(session.createTopic(destination));
			}
			msg = consumer.receive();
			if (msg instanceof ObjectMessage) {
				message = (ObjectMessage)msg;
			}
			
		} catch (JMSException e) {
			e.printStackTrace();
		} 
		
		return message;
	}
	
	
	public void receiveMessageByListener(String destination,final MessageCallBack callBack) {
		
		while (!callBack.isStopReceiveMessage()) {
			try {
				conn = factory.createConnection();
				session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
				conn.start();
				consumer = session.createConsumer(session.createQueue(destination));
				VideoCallJMSImpl videoCallJMSImpl = new VideoCallJMSImpl(){
					
					@Override
					public void onMessage(Message message) {
						callBack.receiveMessage(message);
					}
					
				};
				consumer.setMessageListener(videoCallJMSImpl);
			} catch (JMSException e) {
				e.printStackTrace();
			} 
		}
	}

	public void stopListener(String name) {
		try {
			conn = factory.createConnection();
			session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			conn.start();
			consumer = session.createConsumer(session.createQueue(name));
			consumer.close();
		} catch (JMSException e) {
			e.printStackTrace();
		} 
	}
	public void closeJMS(MessageProducer producer, MessageConsumer consumer, Session session, Connection conn) {
		
			try {
				if (producer != null) {
					producer.close();
				}
				if (consumer != null) {
					consumer.close();
				}
				if (session != null) {
					session.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (JMSException e) {
				e.printStackTrace();
			}
		
	}
	
	
}
