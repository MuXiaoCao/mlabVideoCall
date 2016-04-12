package com.mlab.xiaocao.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="client")
public class Client {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(length=50,nullable=false)
	private String phoneID;

	
	
	public Client() {
	}

	public String getPhoneID() {
		return phoneID;
	}

	public void setPhoneID(String phoneID) {
		this.phoneID = phoneID;
	}
	
	
	
}
