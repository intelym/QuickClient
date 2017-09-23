package com.intelym.quick.java.client.common;

import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4FastDecompressor;



public class Compression {
	
	 final static LZ4Factory FACTORY;
	    final static LZ4Compressor COMPRESSOR;
	    final static LZ4FastDecompressor DECOMPRESSOR;
	    
	    static {
	        FACTORY = LZ4Factory.fastestInstance();
	        COMPRESSOR = FACTORY.fastCompressor();
	        DECOMPRESSOR = FACTORY.fastDecompressor();
	    }
	    
	    public static byte[] compress(byte[] dataToCompress){
	        //final int maxCompressedLength = COMPRESSOR.maxCompressedLength(dataToCompress.length);
	        //byte[] compressed = new byte[maxCompressedLength];
	        return COMPRESSOR.compress(dataToCompress);
	        //COMPRESSOR.compress(dataToCompress, 0, dataToCompress.length, compressed, 0, maxCompressedLength);
	        //return compressed;
	    }
	    
	    public static byte[] decompress(byte[] dataToDecompress, int originalLength){
	        byte[] restored = new byte[originalLength];
	        DECOMPRESSOR.decompress(dataToDecompress, 0, restored, 0, originalLength);
	        return restored;
	    }

		
}
