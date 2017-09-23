package com.intelym.quick.java.client.services;

import java.util.Arrays;

import com.intelym.quick.java.client.data.Packet;
import com.intelym.quick.java.client.logger.IntelymLogger;
import com.intelym.quick.java.client.logger.LoggerFactory;
import com.intelym.quick.java.client.sockets.TcpConnection;

public class MarketData implements MDHandler,Handler{
	
	private static Handler mData;
	private QuickEvent quickEvent;
	private TcpConnection tcpConnection = null;
	//private MulticastConnection _multicastConnection = null;
	private boolean IsMulticastEnabled = false;
	private boolean isNewVersionAuthentication = false;
	private Configuration configuration = null;
	private Object myLock = new Object();
	private final static IntelymLogger mLog = LoggerFactory.getLogger(MarketData.class);

	// private construtor, eliminates the instantiation
	private MarketData() {
		configuration = new Configuration();
	}

	// sets a singlton interface for the QuickClient
	public static Handler GetInstance() {
		if (mData == null) {
			mData = new MarketData();
		}
		return mData;
	}

	// holder to the calling app, it needs to implement QuickEvent API
	public void setEventHandler(QuickEvent qEvent) {
		this.quickEvent = qEvent;
	}

	public void setAddress(String address) {
		if (address == null) {
			try {
				throw new Exception("Invalid IP/Domain Address");
			} catch (Exception e) {
				mLog.error("Invalid IP/Domain Address" + e.getMessage());
			}
		}
		configuration.SERVER_ADDRESS = address;
	}

	public void setPort(int port) {
		if (port <= 0) {
			try {
				throw new Exception("Invalid Port details");
			} catch (Exception e) {
				mLog.error("Invalid Port details" + e.getMessage());
			}
		}
		 configuration.SERVER_PORT = port;
	}

	public void setMulticastDetails(String mGroup, int mPort) {
		try {
			if (mGroup != null && !mGroup.isEmpty() && !mGroup.trim().isEmpty()) {
				throw new Exception("Invalid Multicast Group");
			}
			if (mPort <= 0) {
				throw new Exception("Invalid Port details");
			}
			configuration.MULTICAST_GROUP_ADDRESS = mGroup;
	        configuration.MULTICAST_PORT = mPort;
			IsMulticastEnabled = true;
		} catch (Exception e) {
			mLog.error("Invalid multicast details like Multicast Group or Port :" + e.getMessage());
		}

	}

	public void setUserCredentials(String username, String password) {
		configuration.USER_NAME = username;
        configuration.PASSWORD = password;
	}

	public void enableNewversionAuthentication() {
		isNewVersionAuthentication = true;
	}

	public boolean connect() {

		try {
			if (quickEvent == null) {
				throw new Exception(
						"No event handler is set, will not initiate the connection");
			}
			if (configuration.SERVER_ADDRESS == null) {
				throw new Exception("Blank Server Address");
			}
			if (configuration.SERVER_PORT <= 0) {
				throw new Exception("Blank Server Port");
			}
			tcpConnection = new TcpConnection(this, configuration);
			if (isNewVersionAuthentication) {
				tcpConnection.enableNewversionAuthentication();
			}
			tcpConnection.connect();
		} catch (Exception e) {
			mLog.error("No event handler is set and Blank Server Address,Port will not initiate the connection :" + e.getMessage());
		}
		return true;
	}

	public boolean disconnect() {
		mLog.info("Calling the disconnect method :");
		if (tcpConnection != null) {
			tcpConnection.dropAndClose(EventDetails.getForcefulDisconnection());
			tcpConnection = null;
		}
		return true;
	}

	public boolean addScrip(int exchange, String scripCode) {
		synchronized (myLock) {
			KeyManager.addScrip(exchange, scripCode);
			byte[] buffer;
			try {
				buffer = PacketBuilder.buildForAddScrip(exchange, scripCode);
				tcpConnection.write(buffer);
			} catch (Exception e) {
				mLog.error("Exception occured while adding the scrip :" + e.getMessage());
			}
		}
		return true;
	}

     public boolean addScrip(int exchange, String[] scripCode)
    {
        synchronized (myLock)
        {
            KeyManager.addScrip(exchange, scripCode);
            int noOfSlot = (int) Math.ceil(scripCode.length/10.0);
            String[] slot = new String[0];
            for (int i = 1; i <= noOfSlot; i++)
            {
                if (i < noOfSlot)
                {
                	slot = Arrays.copyOfRange(scripCode,0 , 10);
                	scripCode = Arrays.copyOfRange(scripCode, 11, scripCode.length);
                }
                else
                {
                    slot = scripCode;
                }
				try {
					byte[] buffer = PacketBuilder.buildForAddScrip(exchange, slot);
					 tcpConnection.write(buffer);
				} catch (Exception e) {
					mLog.error("Exception occured while adding multiple scrips :"+e.getMessage());
				}
               
            }
        }
        return true;
    }

	public boolean addMarketDepth(int exchange, String scripCode) {
		synchronized (myLock) {
			KeyManager.addMarketDepth(exchange, scripCode);
			byte[] buffer;
			try {
				buffer = PacketBuilder.buildForAddMarketDepth(exchange,
						scripCode);
				tcpConnection.write(buffer);
			} catch (Exception e) {
				mLog.error("Exception occured while adding market depth :" + e.getMessage());
			}
		}
		return true;
	}

	public boolean deleteMarketDepth(int exchange, String scripCode) {
		synchronized (myLock) {
			KeyManager.deleteMarketDepth(exchange, scripCode);
			byte[] buffer;
			try {
				buffer = PacketBuilder.buildForDeleteMarketDepth(exchange,
						scripCode);
				tcpConnection.write(buffer);
			} catch (Exception e) {
				mLog.error("Exception occured while delete market depth :" + e.getMessage());
			}
		}
		return true;
	}

	public boolean deleteScrip(int exchange, String scripCode) {
		synchronized (myLock) {
			KeyManager.deleteScrip(exchange, scripCode);
			byte[] buffer;
			try {
				buffer = PacketBuilder.buildForDeleteScrip(exchange, scripCode);
				tcpConnection.write(buffer);
			} catch (Exception e) {
				mLog.error("Exception occured while delete scrip :" + e.getMessage());
			}

		}
		return true;
	}

	public boolean deleteScrip(int exchange, String[] scripCode) {

		for (String item : scripCode) {
			deleteScrip(exchange, item);
		}

		return true;
	}

	public boolean addDerivativeChain(int exchange, String scripCode) // underlying scripcode
	{
		try {
			KeyManager.addDerivativeChain(exchange, scripCode);
			byte[] buffer = PacketBuilder.buildForAddDerivativeChain(exchange,scripCode);
			tcpConnection.write(buffer);
			Thread.sleep(100);

		} catch (Exception e) {
			mLog.error("Exception occured while adding derivative chain :" + e.getMessage());
		}
		return true;
	}

	public boolean deleteDerivativeChain(int exchange, String scripCode) // underlying scripcode
	{
		try {
			KeyManager.deleteDerivativeChain(exchange, scripCode);
			byte[] buffer = PacketBuilder.buildForDeleteDerivativeChain(exchange, scripCode);
			tcpConnection.write(buffer);
			Thread.sleep(100);
		} catch (Exception e) {
			mLog.error("Exception occured while deleting derivative chain :" + e.getMessage());
		}

		return true;
	}

	public boolean addFutureChain(int exchange, String scripCode) {
		try {
			KeyManager.addFutureChain(exchange, scripCode);
			byte[] buffer = PacketBuilder.buildForAddFutureChain(exchange,scripCode);
			tcpConnection.write(buffer);
			Thread.sleep(100);
		} catch (Exception e) {
			mLog.error("Exception occured while adding future chain :" + e.getMessage());
		}

		return true;
	}

	public boolean addOptionChain(int exchange, String scripCode) {
		try {
			KeyManager.addOptionChain(exchange, scripCode);
			byte[] buffer = PacketBuilder.buildForAddOptionChain(exchange,scripCode);
			tcpConnection.write(buffer);
			Thread.sleep(100);
		} catch (Exception e) {
			mLog.error("Exception occured while adding option chain :" + e.getMessage());
		}
		return true;
	}

	public boolean deleteFutureChain(int exchange, String scripCode) {
		try {
			KeyManager.deleteFutureChain(exchange, scripCode);
			byte[] buffer = PacketBuilder.buildForDeleteFutureChain(exchange,scripCode);
			tcpConnection.write(buffer);
			Thread.sleep(100);
		} catch (Exception e) {
			mLog.error("Exception occured while deleting future chain :" + e.getMessage());
		}
		return true;
	}

	public boolean deleteOptionChain(int exchange, String scripCode) {
		try {
			KeyManager.deleteOptionChain(exchange, scripCode);
			byte[] buffer = PacketBuilder.buildForDeleteOptionChain(exchange,scripCode);
			tcpConnection.write(buffer);
			Thread.sleep(100);
		} catch (Exception e) {
			mLog.error("Exception occured while deleting otion chain :" + e.getMessage());
		}
		return true;
	}

	public void setExchangeBroadcast(boolean enable) {

	}

	public void setEnableLogging(String logPath) {
		if (logPath != null) {
			 configuration.LOG_ENABLED = true;
	         configuration.LOG_PATH = logPath;
		}
	}

	public void onConnect() {
		quickEvent.onConnect();
	}

	public void onDisconnect(EventDetails details) {
		disconnect();
		quickEvent.onDisconnect(details);
		mLog.info("onDisconnect method executed :" + details);
	}

	public void onError(EventDetails details) {
		disconnect();
		quickEvent.onError(details);
		mLog.info("onError method executed :" + details);
	}

	public void onPacketArrived(Packet packet) {
		quickEvent.onPacketArrived(packet);
		KeyManager.setData(packet);
	}

	public void onPacketArrived(Packet[] packet) {
		quickEvent.onPacketArrived(packet);

		for (Packet p : packet) {
			KeyManager.setData(p);
		}
	}


}
