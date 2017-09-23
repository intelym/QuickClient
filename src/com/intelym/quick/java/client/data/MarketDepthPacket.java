package com.intelym.quick.java.client.data;

public class MarketDepthPacket extends Packet{

	private int noOfRecords ;
	private int[] buyPrice ;
	private int[] sellPrice ;
	private int[] buyQty ;
	private int[] sellQty ;
	private int[] buyOrders ;
	private int[] sellOrders ;
     
	public int getNoOfRecords() {
		return noOfRecords;
	}
	public void setNoOfRecords(int noOfRecords) {
		this.noOfRecords = noOfRecords;
	}
	public int[] getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(int[] buyPrice) {
		this.buyPrice = buyPrice;
	}
	public int[] getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(int[] sellPrice) {
		this.sellPrice = sellPrice;
	}
	public int[] getBuyQty() {
		return buyQty;
	}
	public void setBuyQty(int[] buyQty) {
		this.buyQty = buyQty;
	}
	public int[] getSellQty() {
		return sellQty;
	}
	public void setSellQty(int[] sellQty) {
		this.sellQty = sellQty;
	}
	public int[] getBuyOrders() {
		return buyOrders;
	}
	public void setBuyOrders(int[] buyOrders) {
		this.buyOrders = buyOrders;
	}
	public int[] getSellOrders() {
		return sellOrders;
	}
	public void setSellOrders(int[] sellOrders) {
		this.sellOrders = sellOrders;
	}
     
   	
}

