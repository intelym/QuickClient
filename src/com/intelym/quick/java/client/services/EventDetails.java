package com.intelym.quick.java.client.services;

public class EventDetails {

	private int code;
	private String description ;

	private static final int FORCEFUL_DISCONNECTION = 101,
                         EXTERNAL_DISCONNECTION = 201,
                         INTERPRET_ERROR = 202,
                         PACKETPROCESSING_FAILED = 203,
                         DECODING_FAILED = 204,
                         UNABLE_TO_CONNECT = 301;
	

	public static int getForcefulDisconnection() {
		return FORCEFUL_DISCONNECTION;
	}

	public static int getExternalDisconnection() {
		return EXTERNAL_DISCONNECTION;
	}

	public static int getInterpretError() {
		return INTERPRET_ERROR;
	}

	public static int getPacketprocessingFailed() {
		return PACKETPROCESSING_FAILED;
	}

	public static int getDecodingFailed() {
		return DECODING_FAILED;
	}

	public static int getUnableToConnect() {
		return UNABLE_TO_CONNECT;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
     
     

	
	
}
