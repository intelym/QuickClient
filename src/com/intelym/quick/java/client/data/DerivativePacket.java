package com.intelym.quick.java.client.data;

public class DerivativePacket extends QuotePacket{
	
	private String underlyingScripCode ;
	private boolean isIndex ;
	private String indexName ;
	private int isFuture ;
     
	public String getUnderlyingScripCode() {
		return underlyingScripCode;
	}
	public void setUnderlyingScripCode(String underlyingScripCode) {
		this.underlyingScripCode = underlyingScripCode;
	}
	public boolean isIndex() {
		return isIndex;
	}
	public void setIndex(boolean isIndex) {
		this.isIndex = isIndex;
	}
	public String getIndexName() {
		return indexName;
	}
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}
	public int getIsFuture() {
		return isFuture;
	}
	public void setIsFuture(int isFuture) {
		this.isFuture = isFuture;
	}
     
     
	
     
}
