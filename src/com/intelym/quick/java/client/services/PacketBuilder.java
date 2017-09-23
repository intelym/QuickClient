package com.intelym.quick.java.client.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.intelym.quick.java.client.common.CommonMessage;
import com.intelym.quick.java.client.common.Compression;
import com.intelym.quick.java.client.communication.MATEInputStream;
import com.intelym.quick.java.client.communication.MATEOutputStream;
import com.intelym.quick.java.client.data.DPRPacket;
import com.intelym.quick.java.client.data.DerivativePacket;
import com.intelym.quick.java.client.data.IndexPacket;
import com.intelym.quick.java.client.data.MarketDepthPacket;
import com.intelym.quick.java.client.data.OpenInterestPacket;
import com.intelym.quick.java.client.data.Packet;
import com.intelym.quick.java.client.data.QuotePacket;
import com.intelym.quick.java.client.data.UnsolicitedPacket;



public class PacketBuilder extends CommonMessage {

	private static final int START_OF_FRAME =  0xFF;

    // interprets the packet for TCP/IP received data
    public static Packet buildForTCP(MATEInputStream br)throws Exception
    {
        Packet packet;
        int service = br.readByte();
        int exchange = br.readByte();
        int pType = br.readByte();
        switch (pType)
        {
            case 3:
                packet = buildIndex(br);
                break;
            case 1:
                packet = buildQuote(br, exchange);
                break;
            case 4:
                packet = buildMarketDepth(br, exchange);
                break;
            case 5:
                packet = buildDerivativeQuote(br);
                break;
            case 2:
                packet = buildQuote2(br);
                break;
            case 12:
                packet = buildOpenInterest(br);
                break;
            case 13:
                packet = buildQuote13(br);
                break;
            case 21:
                packet = buildDPR(br);
                break;
            default:
                packet = null;
                break;
        }
        packet.setExchange(exchange);
        return packet;
    }

    private static byte[] stripAndDecompressPackets(byte[] buffer, int noOfRecords) throws Exception
    {
        ByteArrayInputStream ms = new ByteArrayInputStream(buffer, 0, buffer.length);
        MATEInputStream br = new MATEInputStream(ms);
        noOfRecords = br.readByte(); // read the no of the packets in this compressed buffer
        int originalLength = br.readInt(); // read the original length of the buffer
        int compressedLength = br.readInt(); // read the comprssed length
        byte[] compressedBuffer = new byte[compressedLength];
        br.read(compressedBuffer, 0, compressedLength);
        return Compression.decompress(compressedBuffer, originalLength);
    }


    /// need rewriting on this method for multicast data
    public static Packet[] buildForMCast(byte[] buffer) throws Exception
    {
        int noOfRecords = 0;
        byte[] decompressedBuffer = stripAndDecompressPackets(buffer, noOfRecords);
        if (decompressedBuffer == null)
        {
            return null;
        }
        
        ByteArrayInputStream ms = new ByteArrayInputStream(decompressedBuffer, 0,decompressedBuffer.length);
		MATEInputStream br = new MATEInputStream(ms);
        
        Packet[] packets = new Packet[noOfRecords];
        for (int i = 0; i < noOfRecords; i++)
        {
            Packet packet;
            int service = br.readByte();
            int exchange = br.readByte();
            int pType = br.readByte();
            
            switch (pType)
            {
                case 3:
                    packet = buildIndex(br);
                    packet.setExchange(exchange);
                    break;
                case 1:
                    packet = buildQuote(br, exchange);
                    break;
                case 4:
                    packet = buildMarketDepth(br, exchange);
                    break;
                case 5:
                    packet = buildDerivativeQuote(br);
                    break;
                case 2:
                    packet = buildQuote2(br);
                    break;
                case 12:
                    packet = buildOpenInterest(br);
                    break;
                case 13:
                    packet = buildQuote13(br);
                    break;
                case 21:
                    packet = buildDPR(br);
                    break;
                default:
                    packet = null;
                    break;
            }
            if (packet != null)
            {
                packet.setExchange(exchange);
                packets[i] = packet;
            }
        }
        return packets;
    }

  
    public static Packet buildForUnsolicited(MATEInputStream br) throws Exception
    {
        UnsolicitedPacket packet = new UnsolicitedPacket();
        br.readByte(); // discard this byte
        int cLength = br.readByte(); // Read the client code length;
        packet.setClientCode(toCharsStatic(br,cLength));
        int pLength = br.readShort(); // Read the packet length
        packet.setMessage(toCharsStatic(br,pLength));
        return packet;
    }

    // Builds the index packet (confirm on the nature of the index codes)
    // returns IndexPacket
    private static Packet buildIndex(MATEInputStream br) throws Exception
    {
        IndexPacket packet = new IndexPacket();
        br.readByte(); // read the length and discard
        int sLength = br.readByte(); // read the scrip code length
        packet.setScripCode(toCharsStatic(br,sLength));
        packet.setLastTradedPrice(br.readInt());
        packet.setClosePrice(br.readInt());
        packet.setHighPrice(br.readInt());
        packet.setLowPrice(br.readInt());
        packet.setOpenPrice(br.readInt());
        packet.setTimestamp(br.readLong());
        return packet;
    }

    // Builds the quote packet (confirm the packet structure)
    // returns QuotePacket
    private static Packet buildQuote(MATEInputStream br, int exchange)throws Exception
    {
        QuotePacket packet = new QuotePacket();
        int length = br.readByte(); // read the length and discard
        int sLength = br.readByte(); // read the scrip code length
        packet.setScripCode(toCharsStatic(br,sLength));
        packet.setLastTradedPrice(br.readInt());
        packet.setClosePrice(br.readInt());
        packet.setBestBuyPrice(br.readInt());
        packet.setBestBuyQty(br.readInt());
        packet.setBestSellPrice(br.readInt());
        packet.setBestSellQty(br.readInt());
        packet.setTotalTradedQty(br.readInt());
        packet.setHighPrice(br.readInt());
        packet.setLowPrice(br.readInt());
        packet.setOpenPrice(br.readInt());
        packet.setLastTradedQty(br.readInt());
        packet.setWeightedAveragePrice(br.readInt());
        packet.setTotalBuy(br.readInt());
        packet.setTotalSell(br.readInt());
        packet.setLowerCircuit(br.readInt());
        packet.setUpperCircuit(br.readInt());
        packet.setTimestamp(br.readLong());
       
        return packet;
    }

    private static Packet buildQuote13(MATEInputStream br)throws Exception
    {
        QuotePacket packet = new QuotePacket();
        br.readByte();
        int sLength = br.readByte(); // read the scrip code length
        packet.setScripCode(toCharsStatic(br,sLength));
        packet.setLastTradedPrice(br.readInt());
        packet.setTotalTradedQty(br.readInt());
        packet.setHighPrice(br.readInt());
        packet.setLowPrice(br.readInt());
        packet.setOpenPrice(br.readInt());
        packet.setClosePrice(br.readInt());
        packet.setLastTradedQty(br.readInt());
        packet.setWeightedAveragePrice(br.readInt());
        packet.setTotalBuy(br.readInt());
        packet.setTotalSell(br.readInt());
        packet.setLowerCircuit(br.readInt());
        packet.setUpperCircuit(br.readInt());
        packet.setTimestamp(br.readLong());
        return packet;
    }

    private static Packet buildDPR(MATEInputStream br)throws Exception
    {
        DPRPacket packet = new DPRPacket();
        br.readByte(); // discard the length
        int sLength = br.readByte(); // read the scrip code length
        packet.setScripCode(toCharsStatic(br,sLength));
        packet.setLowerCircuit(br.readInt());
        packet.setUpperCircuit(br.readInt());
        return packet;
    }

    private static Packet buildOpenInterest(MATEInputStream br)throws Exception
    {
        OpenInterestPacket packet = new OpenInterestPacket();
        br.readByte(); // read the length and discard
        int sLength = br.readByte(); // read the scrip code length
        packet.setScripCode(toCharsStatic(br,sLength));
        packet.setOpenInterest(br.readInt());
        packet.setLastTradedPrice(br.readInt());
        packet.setTotalTradedQty(br.readInt());
        return packet;
    }

    // Builds the quote packet (confirm the packet structure)
    // returns QuotePacket
    private static Packet buildQuote2(MATEInputStream br)throws Exception
    {
        QuotePacket packet = new QuotePacket();
        br.readByte(); // read the length and discard
        int sLength = br.readByte(); // read the scrip code length
        packet.setScripCode(toCharsStatic(br,sLength));
        packet.setLastTradedPrice(br.readInt());
        packet.setHighPrice(br.readInt());
        packet.setTotalBuy(br.readInt());
        packet.setLowPrice(br.readInt());
        packet.setTotalSell(br.readInt());
        packet.setTotalTradedQty(br.readInt());
        packet.setTimestamp(br.readLong()); //Add newly to manage the time on BSE packets
        return packet;
    }

    private static Packet buildDerivativeQuote(MATEInputStream br)throws Exception
    {
        DerivativePacket packet = new DerivativePacket();
        br.readByte(); // read the length and discard
        int sLength = br.readByte(); // read the scrip code length
        packet.setScripCode(toCharsStatic(br,sLength));
        sLength = br.readByte(); // read the underlying scrip code length
        packet.setUnderlyingScripCode(toCharsStatic(br,sLength));
        packet.setIsFuture(br.readByte()); // 1 if Future 0 means it is Option
        packet.setLastTradedPrice(br.readInt());
        packet.setClosePrice(br.readInt());
        packet.setBestBuyPrice(br.readInt());
        packet.setBestBuyQty(br.readInt());
        packet.setBestSellPrice(br.readInt());
        packet.setBestSellQty(br.readInt());
        packet.setTotalTradedQty(br.readInt());
        packet.setHighPrice(br.readInt());
        packet.setLowPrice(br.readInt());
        packet.setOpenPrice(br.readInt());
        packet.setLastTradedQty(br.readInt());
        packet.setLowerCircuit(br.readInt());
        packet.setUpperCircuit(br.readInt());
        packet.setWeightedAveragePrice(br.readInt());
        packet.setTotalBuy(br.readInt());
        packet.setTotalSell(br.readInt());
        packet.setTimestamp(br.readLong());
        int isIndex = br.readByte(); // Added new to find out whether this derivative contract's underlying is Index or not
        packet.setIndex(isIndex == 1 ? true : false);
       
        return packet;
    }

    public static Packet buildMarketDepth(MATEInputStream br, int exchange) throws Exception
    {
        MarketDepthPacket mDepthPacket = new MarketDepthPacket();
        br.readByte(); // read the length and discard
        int sLength = br.readByte(); // read the scrip code length
        mDepthPacket.setScripCode(toCharsStatic(br,sLength));
        mDepthPacket.setNoOfRecords(br.readByte());

        int[] bPrice = new int[mDepthPacket.getNoOfRecords()];
        int[] bQty = new int[mDepthPacket.getNoOfRecords()];
        int[] sPrice = new int[mDepthPacket.getNoOfRecords()];
        int[] sQty = new int[mDepthPacket.getNoOfRecords()];
        int[] sOrders = new int[mDepthPacket.getNoOfRecords()];
        int[] bOrders = new int[mDepthPacket.getNoOfRecords()];
        for(int i= 0; i < mDepthPacket.getNoOfRecords(); i++)
        {
            bPrice[i] = br.readInt();
            bQty[i] = br.readInt();
            sPrice[i] = br.readInt();
            sQty[i] = br.readInt();
            if (exchange == Configuration.BSE || exchange == Configuration.BSEFO)
            {
                bOrders[i] = br.readInt();
                sOrders[i] = br.readInt();
            }
            else
            {
                bOrders[i] = br.readShort();
                sOrders[i] = br.readShort();
            }
        }
        mDepthPacket.setBuyOrders(bOrders);
        mDepthPacket.setBuyPrice(bPrice);
        mDepthPacket.setBuyQty(bQty);
        mDepthPacket.setSellOrders(sOrders);
        mDepthPacket.setSellPrice(sPrice);
        mDepthPacket.setSellQty(sQty);
        mDepthPacket.setTimestamp(br.readLong());
        return mDepthPacket;
    }
    
    public static byte[] buildForAddScrip(int exchange, String scripCode)throws Exception
    {
        
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        MATEOutputStream writer = new MATEOutputStream(stream);
        
        byte[] toBytes = scripCode.getBytes();
        byte length = (byte) (4 + 1 + toBytes.length);
        writer.writeByte(START_OF_FRAME);
        writer.writeByte(length);
        writer.writeShort((short)Configuration.ADDSCRIP);
        writer.writeByte((byte)1);
        writer.writeByte((byte)exchange);           
        writer.writeByte((byte)toBytes.length);
        writer.write(toBytes);
        return stream.toByteArray();
    }

    public static byte[] buildForAddScrip(int exchange, String[] scripCode)throws Exception
    {
        
        ByteArrayOutputStream scripStream = new ByteArrayOutputStream();
        MATEOutputStream scripWriter = new MATEOutputStream(scripStream);
        
        for (String i : scripCode) {
        	scripWriter.write((byte)exchange);
            byte[] toBytes = i.getBytes();
            scripWriter.writeByte((byte)toBytes.length);
            scripWriter.write(toBytes);
		}
        byte[] scripBuffer = scripStream.toByteArray();
        byte length = (byte) (scripBuffer.length + 3);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        MATEOutputStream writer = new MATEOutputStream(stream);
        writer.writeByte(START_OF_FRAME);
        writer.writeByte(length);
        writer.writeShort((short)Configuration.ADDSCRIP);
        writer.writeByte((byte)scripCode.length);
        writer.write(scripBuffer);
        return stream.toByteArray();
    }

    public static byte[] buildForAddDerivativeChain(int exchange, String scripCode)throws Exception
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        MATEOutputStream writer = new MATEOutputStream(stream);
        
        byte[] toBytes = scripCode.getBytes();
        byte length = (byte)(4 + 1 + toBytes.length);
        writer.writeByte(START_OF_FRAME);
        writer.writeByte(length);
        writer.writeShort((short)Configuration.ADDDERIVATIVECHAIN);
        writer.writeByte((byte)1);
        writer.writeByte((byte)exchange);
        writer.writeByte((byte)toBytes.length);
        writer.write(toBytes);
        return stream.toByteArray();
    }

    public static byte[] buildForAddOptionChain(int exchange, String scripCode)throws Exception
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        MATEOutputStream writer = new MATEOutputStream(stream);
        
        byte[] toBytes = scripCode.getBytes();
        byte length = (byte)(4 + 1 + toBytes.length);
        writer.writeByte(START_OF_FRAME);
        writer.writeByte(length);
        writer.writeShort((short)Configuration.ADDOPTIONCHAIN);
        writer.writeByte((byte)1);
        writer.writeByte((byte)exchange);
        writer.writeByte((byte)toBytes.length);
        writer.write(toBytes);
        return stream.toByteArray();
    }

    public static byte[] buildForAddFutureChain(int exchange, String scripCode)throws Exception
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        MATEOutputStream writer = new MATEOutputStream(stream);
        byte[] toBytes = scripCode.getBytes();
        byte length = (byte)(4 + 1 + toBytes.length);
        writer.writeByte(START_OF_FRAME);
        writer.writeByte(length);
        writer.writeShort((short)Configuration.ADDFUTURECHAIN);
        writer.writeByte((byte)1);
        writer.writeByte((byte)exchange);
        writer.write((byte)toBytes.length);
        writer.write(toBytes);
        return stream.toByteArray();
    }

    public static byte[] buildForAddMarketDepth(int exchange, String scripCode)throws Exception
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        MATEOutputStream writer = new MATEOutputStream(stream);
        byte[] toBytes = scripCode.getBytes();
        byte length = (byte)(4 + 1 + toBytes.length);
        writer.writeByte(START_OF_FRAME);
        writer.writeByte(length);
        writer.writeShort((short)Configuration.ADDMDPETH);
        writer.writeByte((byte)1);
        writer.writeByte((byte)exchange);
        writer.writeByte((byte)toBytes.length);
        writer.write(toBytes);
        return stream.toByteArray();
    }


    public static byte[] buildForDeleteScrip(int exchange, String scripCode)throws Exception
    {
    	
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        MATEOutputStream writer = new MATEOutputStream(stream);
        byte[] toBytes = scripCode.getBytes();
        byte length = (byte)(4 + 1 + toBytes.length);
        writer.writeByte(START_OF_FRAME);
        writer.writeByte(length);
        writer.writeShort((short)Configuration.DELETESCRIP);
        writer.writeByte((byte)1);
        writer.writeByte((byte)exchange);
        writer.writeByte((byte)toBytes.length);
        writer.write(toBytes);
        return stream.toByteArray();
    }

    public static byte[] buildForDeleteDerivativeChain(int exchange, String scripCode)throws Exception
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        MATEOutputStream writer = new MATEOutputStream(stream);
        byte[] toBytes = scripCode.getBytes();
        byte length = (byte)(4 + 1 + toBytes.length);
        writer.writeByte(START_OF_FRAME);
        writer.writeByte(length);
        writer.writeShort((short)Configuration.DELETEDERIVATIVECHAIN);
        writer.writeByte((byte)1);
        writer.writeByte((byte)exchange);
        writer.writeByte((byte)toBytes.length);
        writer.write(toBytes);
        return stream.toByteArray();
    }

    public static byte[] buildForDeleteFutureChain(int exchange, String scripCode)throws Exception
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        MATEOutputStream writer = new MATEOutputStream(stream);
        byte[] toBytes = scripCode.getBytes();
        byte length = (byte)(4 + 1 + toBytes.length);
        writer.writeByte(START_OF_FRAME);
        writer.writeByte(length);
        writer.writeShort((short)Configuration.DELETEFUTURECHAIN);
        writer.writeByte((byte)1);
        writer.writeByte((byte)exchange);
        writer.writeByte((byte)toBytes.length);
        writer.write(toBytes);
        return stream.toByteArray();
    }

    public static byte[] buildForDeleteOptionChain(int exchange, String scripCode)throws Exception
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        MATEOutputStream writer = new MATEOutputStream(stream);
        byte[] toBytes = scripCode.getBytes();
        byte length = (byte)(4 + 1 + toBytes.length);
        writer.writeByte(START_OF_FRAME);
        writer.writeByte(length);
        writer.writeShort((short)Configuration.DELETEOPTIONCHAIN);
        writer.writeByte((byte)1);
        writer.writeByte((byte)exchange);
        writer.writeByte((byte)toBytes.length);
        writer.write(toBytes);
        return stream.toByteArray();
    }

    public static byte[] buildForDeleteMarketDepth(int exchange, String scripCode)throws Exception
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        MATEOutputStream writer = new MATEOutputStream(stream);
        byte[] toBytes = scripCode.getBytes();
        byte length = (byte)(4 + 1 + toBytes.length);
        writer.writeByte(START_OF_FRAME);
        writer.writeByte(length);
        writer.writeShort((short)Configuration.DELETEMDEPTH);
        writer.writeByte((byte)1);
        writer.writeByte((byte)exchange);
        writer.writeByte((byte)toBytes.length);
        writer.write(toBytes);
        return stream.toByteArray();
    }
    
    
    public static String toCharsStatic(MATEInputStream in, int length) throws Exception{
        String tmp = "";
        boolean identifiedNullTermination = false;
        for(int i = 0; i < length; i++){
            byte b = (byte) in.readByte();
            if(b == 0x00){
                identifiedNullTermination = true;
            }
            if(!identifiedNullTermination)
                tmp += String.valueOf((char)b);
        }
        return tmp;
    }
    
}
