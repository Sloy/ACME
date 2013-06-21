package com.acme.model;

import javax.persistence.Entity;

@Entity
public class MethodPay extends AbstractPersistable<Long>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6358996595458468976L;
	//-------------------------------------------------------------
	// Getters & Setters
	//-------------------------------------------------------------
	private String name;
	
	public MethodPay () {
		
	}
	
	public MethodPay(String name) {
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
