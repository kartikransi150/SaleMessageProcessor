package com.SalesMessageProcessor.Interfaces;

public interface ILogger {

	void processSale(IMessage message);
			
	String getSaleReport();
	
	String getAdjustmentReport();
}
