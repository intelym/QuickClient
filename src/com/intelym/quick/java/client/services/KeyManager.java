package com.intelym.quick.java.client.services;

import javolution.util.FastMap;

import com.intelym.quick.java.client.data.IndexPacket;
import com.intelym.quick.java.client.data.MarketDepthPacket;
import com.intelym.quick.java.client.data.Packet;
import com.intelym.quick.java.client.data.QuotePacket;

public class KeyManager {

	public static FastMap<String, Packet> scripMap = new FastMap<String, Packet>();
	public static FastMap<String, Packet> mDepthMap = new FastMap<String, Packet>();
	public static FastMap<String, Boolean> derivativeChain = new FastMap<String, Boolean>();
	public static FastMap<String, Boolean> futureChain = new FastMap<String, Boolean>();
	public static FastMap<String, Boolean> optionChain = new FastMap<String, Boolean>();

	// Adds the scrip code requesting quote packet into memory
	public static void addScrip(int exchange, String scripCode) {
		String key = exchange + "." + scripCode;
		if (!scripMap.containsKey(key)) {
			scripMap.put(key, new IndexPacket());
		}
	}

	public static void addScrip(int exchange, String[] scripCode)
    {
		for (String sCode : scripCode) {
			addScrip(exchange, sCode);
		}
		
    }

	public static void addDerivativeChain(int exchange, String scripCode) {
		String key = exchange + "." + scripCode;
		if (!derivativeChain.containsKey(key)) {
			derivativeChain.put(key, true);
		}
	}

	public static void deleteDerivativeChain(int exchange, String scripCode) {
		String key = exchange + "." + scripCode;
		if (derivativeChain.containsKey(key)) {
			derivativeChain.remove(key);
		}
	}

	public static void addOptionChain(int exchange, String scripCode) {
		String key = exchange + "." + scripCode;
		if (!optionChain.containsKey(key)) {
			optionChain.put(key, true);
		}
	}

	public static void deleteOptionChain(int exchange, String scripCode) {
		String key = exchange + "." + scripCode;
		if (optionChain.containsKey(key)) {
			optionChain.remove(key);
		}
	}

	public static void addFutureChain(int exchange, String scripCode) {
		String key = exchange + "." + scripCode;
		if (!futureChain.containsKey(key)) {
			futureChain.put(key, true);
		}
	}

	public static void deleteFutureChain(int exchange, String scripCode) {
		String key = exchange + "." + scripCode;
		if (futureChain.containsKey(key)) {
			futureChain.remove(key);
		}
	}

	public static void setScripData(Packet packet) {
		String key = packet.getExchange() + "." + packet.getScripCode();
		if (scripMap.containsKey(key)) {
			scripMap.get(key).equals(packet);
		}
	}

	public static void deleteScrip(int exchange, String scripCode) {
		String key = exchange + "." + scripCode;
		if (scripMap.containsKey(key)) {
			scripMap.remove(key);
		}
	}

	public static void addMarketDepth(int exchange, String scripCode) {
		String key = exchange + "." + scripCode;
		if (!mDepthMap.containsKey(key)) {
			mDepthMap.put(key, new MarketDepthPacket());
		}
	}

	public static void setMarketDepthData(Packet packet) {
		String key = packet.getExchange() + "." + packet.getScripCode();
		if (mDepthMap.containsKey(key)) {
			mDepthMap.get(key).equals(packet);
			
		}
	}

	public static void deleteMarketDepth(int exchange, String scripCode) {
		String key = exchange + "." + scripCode;
		if (mDepthMap.containsKey(key)) {
			mDepthMap.remove(key);
		}
	}

	public static void setData(Packet packet)
    {
        if (packet instanceof QuotePacket)
        {
            setScripData(packet);
        }
        else if (packet instanceof MarketDepthPacket)
        {
            setMarketDepthData(packet);
        }
    }

	public Packet getScripData(int exchange, String scripCode) {
		String key = exchange + "." + scripCode;
		if (scripMap.containsKey(key)) {
			return scripMap.get(key);
		}
		return null;
	}

	public Packet getMarketDepthData(int exchange, String scripCode) {
		String key = exchange + "." + scripCode;
		if (mDepthMap.containsKey(key)) {
			return mDepthMap.get(key);
		}
		return null;
	}

	public void clearAllMarketDepth() {
		mDepthMap.clear();
	}

	public void clearAllScrips() {
		scripMap.clear();
	}

}
