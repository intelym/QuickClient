package com.intelym.quick.java.client.data;

public class QuotePacket extends Packet{

	private int lastTradedPrice ;
	private int closePrice ;
	private int bestBuyPrice ;
	private int bestBuyQty ;
	private int bestSellPrice ;
	private int bestSellQty ;
	private int highPrice ;
	private int lowPrice ;
	private int openPrice ;
	private int lastTradedQty ;
	private int totalTradedQty ;
	private int weightedAveragePrice ;
	private int totalBuy ;
	private int totalSell ;
	private int totalTradedValue ;
	private int lowerCircuit ;
	private int upperCircuit ;
      
      
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
	public int getBestBuyPrice() {
		return bestBuyPrice;
	}
	public void setBestBuyPrice(int bestBuyPrice) {
		this.bestBuyPrice = bestBuyPrice;
	}
	public int getBestBuyQty() {
		return bestBuyQty;
	}
	public void setBestBuyQty(int bestBuyQty) {
		this.bestBuyQty = bestBuyQty;
	}
	public int getBestSellPrice() {
		return bestSellPrice;
	}
	public void setBestSellPrice(int bestSellPrice) {
		this.bestSellPrice = bestSellPrice;
	}
	public int getBestSellQty() {
		return bestSellQty;
	}
	public void setBestSellQty(int bestSellQty) {
		this.bestSellQty = bestSellQty;
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
	public int getLastTradedQty() {
		return lastTradedQty;
	}
	public void setLastTradedQty(int lastTradedQty) {
		this.lastTradedQty = lastTradedQty;
	}
	public int getTotalTradedQty() {
		return totalTradedQty;
	}
	public void setTotalTradedQty(int totalTradedQty) {
		this.totalTradedQty = totalTradedQty;
	}
	public int getWeightedAveragePrice() {
		return weightedAveragePrice;
	}
	public void setWeightedAveragePrice(int weightedAveragePrice) {
		this.weightedAveragePrice = weightedAveragePrice;
	}
	public int getTotalBuy() {
		return totalBuy;
	}
	public void setTotalBuy(int totalBuy) {
		this.totalBuy = totalBuy;
	}
	public int getTotalSell() {
		return totalSell;
	}
	public void setTotalSell(int totalSell) {
		this.totalSell = totalSell;
	}
	public int getTotalTradedValue() {
		return totalTradedValue;
	}
	public void setTotalTradedValue(int totalTradedValue) {
		this.totalTradedValue = totalTradedValue;
	}
	public int getLowerCircuit() {
		return lowerCircuit;
	}
	public void setLowerCircuit(int lowerCircuit) {
		this.lowerCircuit = lowerCircuit;
	}
	public int getUpperCircuit() {
		return upperCircuit;
	}
	public void setUpperCircuit(int upperCircuit) {
		this.upperCircuit = upperCircuit;
	}
      
      	
}
