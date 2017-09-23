package com.intelym.quick.java.client.services;

public interface Handler {
	
   public void setEventHandler(QuickEvent qEvent);
   public void setAddress(String address);
   public void setPort(int port);
   public void setMulticastDetails(String mGroup, int mPort);
   public void setUserCredentials(String username, String password);
   public boolean connect();
   public boolean disconnect();
   public boolean addScrip(int exchange, String scripCode);
   public boolean addScrip(int exchange, String[] scripCode);
   public boolean addMarketDepth(int exchange, String scripCode);
   public boolean deleteMarketDepth(int exchange, String scripCode);
   public boolean deleteScrip(int exchange, String scripCode);
   public boolean deleteScrip(int exchange, String[] scripCode);
   public boolean addDerivativeChain(int exchange, String scripCode); // underlying scripcode
   public boolean addFutureChain(int exchange, String scripCode); // only for subscribing future 
   public boolean addOptionChain(int exchange, String scripCode); // only for subscribing option chain
   public boolean deleteDerivativeChain(int exchange, String scripCode); // underlying scripcode
   public boolean deleteFutureChain(int exchange, String scripCode);
   public boolean deleteOptionChain(int exchange, String scripCode);
   public void setExchangeBroadcast(boolean enable);
   public void setEnableLogging(String logPath);
   public void enableNewversionAuthentication(); // for Quick 5.0 + 


}
