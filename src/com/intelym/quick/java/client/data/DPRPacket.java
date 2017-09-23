package com.intelym.quick.java.client.data;

public class DPRPacket extends Packet{
	
	private int lowerCircuit ;
	private int upperCircuit ;
     
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
