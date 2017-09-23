package com.intelym.quick.java.client.services;

import com.intelym.quick.java.client.data.Packet;

public interface QuickEvent {
	
	// raise this in the event of successful connection with Quick Server
   public void onConnect();
    // raise this in the event on forceful disconnection
   public void onDisconnect(EventDetails details);
    // raise this in the event of any error, implied disconnection also
   public void onError(EventDetails details);
    // raise this when single packet arrives
   public void onPacketArrived(Packet packet);
    // raise this when multiple packet arrives
   public void onPacketArrived(Packet[] packet);


}
