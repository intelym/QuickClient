package com.intelym.quick.java.client.data;

public class OpenInterestPacket extends Packet{

	private int openInterest ;
	private int lastTradedPrice ;
	private int totalTradedQty ;
     
     
	public int getOpenInterest() {
		return openInterest;
	}
	public void setOpenInterest(int openInterest) {
		this.openInterest = openInterest;
	}
	public int getLastTradedPrice() {
		return lastTradedPrice;
	}
	public void setLastTradedPrice(int lastTradedPrice) {
		this.lastTradedPrice = lastTradedPrice;
	}
	public int getTotalTradedQty() {
		return totalTradedQty;
	}
	public void setTotalTradedQty(int totalTradedQty) {
		this.totalTradedQty = totalTradedQty;
	}
     
     	
}
