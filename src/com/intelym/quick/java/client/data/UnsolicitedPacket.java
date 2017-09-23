package com.intelym.quick.java.client.data;

public class UnsolicitedPacket extends Packet{

	private String clientCode ;
	private String message ;
     
     
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	} 
     
     
}
