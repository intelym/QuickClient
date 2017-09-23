package com.intelym.quick.java.client.common;

import java.util.Random;

import com.intelym.quick.java.client.data.DerivativePacket;
import com.intelym.quick.java.client.data.IndexPacket;
import com.intelym.quick.java.client.data.MarketDepthPacket;
import com.intelym.quick.java.client.data.OpenInterestPacket;
import com.intelym.quick.java.client.data.Packet;
import com.intelym.quick.java.client.data.QuotePacket;
import com.intelym.quick.java.client.data.UnsolicitedPacket;
import com.intelym.quick.java.client.services.EventDetails;
import com.intelym.quick.java.client.services.Handler;
import com.intelym.quick.java.client.services.MarketData;
import com.intelym.quick.java.client.services.QuickEvent;

public class StartQuickClient implements QuickEvent {
	
	 private Handler handler;
     private int iCounter =1;
     public StartQuickClient(int InstanceId)
     {
         try
         {
             handler = MarketData.GetInstance();
             handler.setEventHandler(this);
             handler.setAddress("192.168.1.109");
             handler.setPort(9898);
             handler.setUserCredentials("PREM", "xxxxx");

             if (handler.connect())
             {
                 System.out.println("Connect initiated "+ InstanceId);
             }
             else
             {
            	 System.out.println("Connect failed ");
             }
         }
         catch (Exception e)
         {
        	 e.printStackTrace();
         }
     }
     public static void main(String[] args) {

		 Random rnd = new Random();
         int InstanceId = rnd.nextInt(9999);
         new StartQuickClient(InstanceId);
		
         //CommonUtil.fromExchangeTime(1505587897, 1);
	}

	@SuppressWarnings("null")
	@Override
	public void onConnect() {
		System.out.println("StartQuickClient.OnConnect(:::Connect succeeded:::)");

				//handler.addScrip(0, "22");
				//handler.deleteScrip(0, "NIFTY 50");
				//handler.addMarketDepth(0, "22");
				//handler.addScrip(0, "NIFTY 50");//AddIndex
				//handler.addDerivativeChain(2, "22");//exchange is 2
				handler.addScrip(2, "45810");
		// 45683, 45810, 49754, 49790, 52828, 53820, 53825, 53830, 53831, 53835, 53841, 55603, 57222, 57224, 57226, 57229, 57230, 57231, 57232, 57804, 57806, 57807, 57808, 63705, 55607, 60197, 45811, 45680, 45755, 57227, 45786, 58884, 60201, 64326, 63674, 63312, 50140, 64168, 59749, 64216, 50185, 55723, 63308, 47499, 47572, 47584, 47610, 103443, 103444, 103454, 103510, 47667, 50733, 103512, 103519, 50743, 50745, 103520, 103527, 50759, 50770, 103532, 103578, 50779, 50802, 103579, 103580, 65165, 65490, 103581, 103582, 65502, 95458, 103585, 35925, 95459, 95460, 36237, 36239, 95462, 95463, 36248, 36256, 95464, 95465, 36600, 37788, 95467, 95469, 37794, 39062, 96399, 104267, 50730, 91403, 65466, 97167, 46421, 103143, 105065, 96388, 100321, 103525, 46020, 46090, 105054, 47521, 46435, 47118, 46091, 46097, 47509, 104783, 50729, 47444, 46106, 50249, 99116, 50791, 100128, 103521, 50331, 50498, 96293, 99118, 100331, 46426, 50499, 50501, 106379, 104265, 93974, 92178, 50502, 61532, 96392, 50754, 37806, 50716, 61535, 64436, 105059, 47489, 44154, 100135, 64475, 64477, 64549, 64735, 65405, 65406, 65411, 65412, 65418, 65423, 50500, 74116, 75578, 73588, 74120, 46098, 76431, 50330, 77046, 71062, 73093, 70992, 71426, 76403, 76407, 68501, 76429, 71443, 46086, 71106, 47332, 35048, 35127, 90030, 90760, 90762, 46391, 46113, 90428, 89331, 91030, 79108, 79110, 89335, 79109, 90441, 99723, 90214, 91117, 99725, 46392, 46414, 90300, 79100, 90222, 50524, 81377, 89320, 58415, 58416, 58660, 58672, 58888, 58894, 58898, 59567, 59568, 60121, 60194, 60195, 60437, 60677, 60683, 62754, 63320, 63321, 63711, 63712, 63713, 63714, 63717, 63988, 57223, 50180, 56699, 63707, 45773, 58662, 45620, 61331, 45585, 56698, 55727, 63332, 63989, 63990, 63991, 64165, 45758, 45845, 56700, 56702, 95471, 95473, 95798, 96291, 39064, 39066, 96297, 96299, 39068, 39069, 96364, 96376, 39070, 39071, 96385, 96386, 39072, 44577, 96387, 96389, 46432, 46438, 96391, 96393, 46442, 46444, 96395, 96396, 46677, 46686, 96397, 96398, 46692, 46693, 96400, 96401, 46697, 46702, 96402, 96403, 46799, 47905, 96404, 96405, 47910, 47912, 50803, 104775, 48907, 50551, 104980, 47490, 47442, 103433, 47453, 104978, 95154, 46427, 104263, 96394, 95244, 103535, 65424, 65436, 104776, 47668, 95259, 93970, 65853, 71068, 47581, 50792, 91384, 103427, 71315, 71342, 103745, 47601, 103583, 95242, 71343, 71368, 98398, 103749, 91696, 50689, 71854, 71856, 106036, 106385, 92462, 102553, 72128, 72132, 47613, 106380, 95008, 36263, 73095, 73137, 35915, 91684, 73651, 74118, 74121, 74696, 74711, 74840, 75160, 75169, 75295, 75300, 71064, 50467, 46017, 72846, 50487, 62172, 46085, 62176, 72419, 75567, 38843, 38847, 68660, 70994, 75301, 75302, 75303, 75387, 75558, 75566, 48386, 63508, 39524, 99657, 99659, 42802, 99661, 46177, 76341, 76452, 76454, 76459, 79781, 79784, 79941, 79983, 79985, 50506, 91032, 46109, 91034, 91038, 91272, 79778, 91040, 57810, 58060, 58062, 58063, 58067, 58074, 58671, 63982, 45682, 49763, 54011, 45586, 45681, 58294, 96406, 96414, 96562, 96564, 50607, 50690, 96569, 96571, 50693, 50720, 96709, 96794, 50722, 65109, 96796, 96797, 65144, 65146, 96854, 96858, 65154, 91395, 96996, 96998, 91397, 91400, 97000, 97149, 91402, 91629, 97152, 97154, 91633, 91668, 97155, 97156, 91673, 91674, 97157, 97158, 91675, 91676, 97159, 97160, 91677, 91678, 96188, 104981, 91680, 91681, 105058, 106387, 91920, 91683, 104781, 106383, 91685, 91686, 96856, 98302, 91690, 91692, 75569, 76406, 65507, 97428, 91694, 91698, 76408, 76410, 99135, 50765, 91918, 91919, 76411, 76412, 97161, 97164, 91922, 91924, 76413, 76414, 97220, 97226, 91925, 91930, 76415, 76416, 97340, 97346, 91932, 91934, 76417, 76418, 97347, 97348, 92106, 92188, 76427, 76428, 92613, 92813, 76583, 76584, 76878, 76880, 76884, 76885, 76888, 76889, 77040, 77042, 74131, 73088, 76881, 65352, 74112, 77044, 77045, 77047, 77140, 77696, 77698, 77699, 77701, 77702, 77703, 77704, 77705, 77707, 77708, 77709, 41510, 44113, 44114, 44115, 44116, 44117, 46489, 47333, 48376, 48380, 48344, 44127, 36981, 50869
	}

	@Override
	public void onDisconnect(EventDetails details) {
		 System.out.println("StartQuickClient.OnDisconnect(:::Disconnect succeeded:::)");
		 System.out.println("StartQuickClient.onDisconnect(:::code:::)"+details.getCode());
		
	}
	@Override
	public void onError(EventDetails details) {
		System.out.println("StartQuickClient.OnError(:::OnError:::)");
		System.out.println("StartQuickClient.onError(:::code:::)"+details.getCode());
		
	}
	@Override
	public void onPacketArrived(Packet packet) {

		if (packet instanceof IndexPacket) {
			System.out.println("StartQuickClient.onPacketArrived(:::IndexPacket Arrived:::)");
			System.out.println("timestamp::"+packet.getTimestamp());
			
		} else if (packet instanceof DerivativePacket) {
			System.out.println("StartQuickClient.onPacketArrived(:::Derivative Quote Packet Arrived:::)");
			System.out.println(packet.getTimestamp());
			
		} else if (packet instanceof QuotePacket) {
			System.out.println("StartQuickClient.onPacketArrived(:::QuotePacket Arrived:::)");
			
		} else if(packet instanceof MarketDepthPacket){
		 MarketDepthPacket p = (MarketDepthPacket)packet ;//var p = packet as MarketDepthPacket;
         String exchangeTime = CommonUtil.fromExchangeTime(packet.getTimestamp(), packet.getExchange());

         System.out.println("Scripcode :" + packet.getScripCode());
         System.out.println("BuyOrders " + p.getBuyOrders()[0] + " SellOrders " + p.getSellOrders()[0]);
         System.out.println("BuyOrders " + p.getBuyOrders()[1] + " SellOrders " + p.getSellOrders()[1]);
         System.out.println("BuyOrders " + p.getBuyOrders()[2] + " SellOrders " + p.getSellOrders()[2]);
         System.out.println("BuyOrders " + p.getBuyOrders()[3] + " SellOrders " + p.getSellOrders()[3]);
         System.out.println("BuyOrders " + p.getBuyOrders()[4] + " SellOrders " + p.getSellOrders()[4]);
         
         System.out.println("BuyPrice " + p.getBuyPrice()[0] + " SellPrice " + p.getSellPrice()[0]);
         System.out.println("BuyPrice " + p.getBuyPrice()[1] + " SellPrice " + p.getSellPrice()[1]);
         System.out.println("BuyPrice " + p.getBuyPrice()[2] + " SellPrice " + p.getSellPrice()[2]);
         System.out.println("BuyPrice " + p.getBuyPrice()[3] + " SellPrice " + p.getSellPrice()[3]);
         System.out.println("BuyPrice " + p.getBuyPrice()[4] + " SellPrice " + p.getSellPrice()[4]);

         System.out.println("BuyQty " + p.getBuyQty()[0] + " SellQty " + p.getSellQty()[0]);
         System.out.println("BuyQty " + p.getBuyQty()[1] + " SellQty " + p.getSellQty()[1]);
         System.out.println("BuyQty " + p.getBuyQty()[2] + " SellQty " + p.getSellQty()[2]);
         System.out.println("BuyQty " + p.getBuyQty()[3] + " SellQty " + p.getSellQty()[3]);
         System.out.println("BuyQty " + p.getBuyQty()[4] + " SellQty " + p.getSellQty()[4]);

         System.out.println(exchangeTime);
         System.out.println(p.getTimestamp());


		} else if (packet instanceof UnsolicitedPacket) {
			System.out.println("StartQuickClient.onPacketArrived(:::UnsolicitedPacket Arrived:::)");
            
			
		} else if (packet instanceof OpenInterestPacket) {
			System.out.println("StartQuickClient.onPacketArrived(:::Open Interest Packet Arrived:::)");
			
			//OpenInterestPacket p = (OpenInterestPacket)packet ;
			System.out.println("timestamp:::"+packet.getTimestamp());
			
		}	
		
	}
	
	@Override
	public void onPacketArrived(Packet[] packet) {
		for (Packet p : packet) {
			  onPacketArrived(p);
		}
	}
	
 }
