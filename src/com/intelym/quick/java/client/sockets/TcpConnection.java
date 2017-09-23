package com.intelym.quick.java.client.sockets;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.Socket;

import com.intelym.quick.java.client.common.CommonMessage;
import com.intelym.quick.java.client.communication.MATEInputStream;
import com.intelym.quick.java.client.communication.MATEOutputStream;
import com.intelym.quick.java.client.data.Packet;
import com.intelym.quick.java.client.logger.IntelymLogger;
import com.intelym.quick.java.client.logger.LoggerFactory;
import com.intelym.quick.java.client.services.Configuration;
import com.intelym.quick.java.client.services.EventDetails;
import com.intelym.quick.java.client.services.MDHandler;
import com.intelym.quick.java.client.services.PacketBuilder;

public class TcpConnection extends CommonMessage implements Runnable{
	
	private Socket tcpClient;
	private MDHandler mDHandler;
	private MATEInputStream reader = null;
	private static final int START_OF_FRAME = 0xFF;
	private Thread socketThread;
	private boolean isRunning = false;
	private boolean isNewVersionAuthentication = false;
	private Configuration configuration = null;
	private MATEOutputStream writer = null;
	private final static IntelymLogger mLog = LoggerFactory.getLogger(TcpConnection.class);
    
	public TcpConnection(MDHandler mdHandler, Configuration config) {
		mDHandler = mdHandler;
		configuration = config;
	}
	
	// connects the Quick server and establishes the stream for reading and writing
	public void connect() {
		try {
			socketThread = new Thread(this, "TCPConnection");

			tcpClient = new Socket(configuration.SERVER_ADDRESS,configuration.SERVER_PORT);
			tcpClient.setTcpNoDelay(true);
			writer = new MATEOutputStream(tcpClient.getOutputStream());
			reader = new MATEInputStream(tcpClient.getInputStream());
			isRunning = true;
			socketThread.start();
			if (isNewVersionAuthentication) {
				authenticateNewVersion();
			} else {
				authenticate();
			}
		} catch (Exception e) {
			mLog.error("Exception occured while connecting Quick Server :" + e.getMessage());
		}
	}

	public void enableNewversionAuthentication() {
		isNewVersionAuthentication = true;
	}

	// drops and closes the connection
	public void dropAndClose(int errorCode) {
		if (tcpClient != null) {
			isRunning = false;
			socketThread = null;
			try {
				tcpClient.close();
			} catch (IOException e) {
				mLog.error("Connection not closed :" + e.getMessage());
			}
			tcpClient = null;
			EventDetails event = new EventDetails();
			event.setCode(errorCode); // EventDetails.FORCEFUL_DISCONNECTION;
			event.setDescription("Application closed the aborted session");
			mDHandler.onDisconnect(event);
		}
	}

	// start reading the data from the incoming stream
	public void run() {
		try {
			while (isRunning) {
				try {
					int lIn = reader.readUnsignedByte();
					if (lIn == START_OF_FRAME) {
						int packetLength = reader.readShort(); // read the length of the packet
						byte[] buffer = new byte[packetLength];
						reader.read(buffer, 0, packetLength);
						try {
							processPackets(buffer);
						} catch (Exception e) {
							mLog.error("Exception occured in procces packet reading :"+ e.getMessage());
						}
						// moved to synchrnous io operation to ensure there is no packet loss
						// tcpClient.Client.BeginReceive(buffer,0,buffer.Length,SocketFlags.None,DataReceived,buffer);
					}
				} catch (Exception e) {
					mLog.error("Reset the quick server connection :" + e.getMessage());
				}
			}
		} catch (Exception e) {
			mLog.error("Not reading data from the incoming stream :" + e.getMessage());;
		}
	}

	private void readLoginResponse(MATEInputStream br) {
		try {
			int filler = br.readShort();
			int length = br.readByte();
			String buf = toChars(br, length);
			mDHandler.onConnect();
		} catch (Exception e) {
			mLog.error("Not reading the login response :" + e.getMessage());
		}
	}

	private void processQuoteAndDepth(MATEInputStream br) {
		Packet packet;
		try {
			packet = PacketBuilder.buildForTCP(br);
			mDHandler.onPacketArrived(packet);
		} catch (Exception e) {
			mLog.error("Exception occured in buildForTCP packet :" + e.getMessage());
		}

	}

	private void processUnsolicited(MATEInputStream br) {
		Packet packet;
		try {
			packet = PacketBuilder.buildForUnsolicited(br);
			mDHandler.onPacketArrived(packet);
		} catch (Exception e) {
			mLog.error("Exception occured in buildForUnsolicited packet :" + e.getMessage());
		}

	}

	private void processPackets(byte[] incomingData) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(incomingData, 0,incomingData.length);
		MATEInputStream br = new MATEInputStream(bais);
		int packetType = br.readShort();
		switch (packetType) {
		case Configuration.LOGIN:
			readLoginResponse(br);
			break;
		case Configuration.QUOTE:
		case Configuration.MDEPTH:
			processQuoteAndDepth(br);
			break;
		case Configuration.UNSOLICITED:
			processUnsolicited(br);
			break;
		default:
			break;
		}
	}

	// writes the data into connected socket
	public void write(byte[] buffer) {
		try {
			if (writer != null) // && tcpClient.Connected)
			{
				writer.write(buffer);
			}
		} catch (Exception e) {
			mLog.error("Unable to write the data into connected socket :" + e.getMessage());
		}
	}

	public void authenticate() {
		try {
			if (tcpClient.isConnected()) {
				short msgLength;
				byte[] usernameArray = configuration.USER_NAME.getBytes();
				byte length = (byte) usernameArray.length;
				msgLength = (short) (length + 3);
				writer.write((byte) START_OF_FRAME); // write a single byte of START of CHAR
				writer.writeShort(msgLength); // two bytes = username length + Message Type + length of the domain
				writer.writeShort((short)Configuration.LOGIN);
				writer.writeByte(length);
				writer.write(usernameArray);
			}
		} catch (Exception e) {
			mLog.error("Exception occured while auntication :" + e.getMessage());
		}
	}

	public void authenticateNewVersion() {
		try {
			if (tcpClient.isConnected()) {
				byte msgLength;
				byte[] usernameArray = configuration.USER_NAME.getBytes();
				byte length = (byte) usernameArray.length;
				msgLength = (byte) (length + 3);
				writer.writeByte(START_OF_FRAME); // write a single byte of START of CHAR
				writer.writeByte(msgLength); // two bytes = username length + Message Type + length of the domain
				writer.writeShort(((short)Configuration.LOGIN));
				writer.writeByte(length);
				writer.write(usernameArray);
			}
		} catch (Exception e) {
			mLog.error("Exception occured while authenticate new version :"+ e.getMessage());
		}
	}

}
