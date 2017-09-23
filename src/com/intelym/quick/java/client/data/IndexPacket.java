package com.intelym.quick.java.client.data;

public class IndexPacket extends Packet{

	private int lastTradedPrice ;
	private int closePrice ;
	private int highPrice ;
	private int lowPrice ;
	private int openPrice ;
	private String formattedTime ;
    
	public int getLastTradedPrice() {
		return lastTradedPrice;
	}
	public void setLastTradedPrice(int lastTradedPrice) {
		this.lastTradedPrice = lastTradedPrice;
	}
	public int getClosePrice() {
		return closePrice;
	}
	public void setClosePrice(int closePrice) {
		this.closePrice = closePrice;
	}
	public int getHighPrice() {
		return highPrice;
	}
	public void setHighPrice(int highPrice) {
		this.highPrice = highPrice;
	}
	public int getLowPrice() {
		return lowPrice;
	}
	public void setLowPrice(int lowPrice) {
		this.lowPrice = lowPrice;
	}
	public int getOpenPrice() {
		return openPrice;
	}
	public void setOpenPrice(int openPrice) {
		this.openPrice = openPrice;
	}
	public String getFormattedTime() {
		return formattedTime;
	}
	public void setFormattedTime(String formattedTime) {
		this.formattedTime = formattedTime;
	}
    
     
	
}
