package com.SalesMessageProcessor.Interfaces;

public interface IMessageProcessor {

	void startProcessing();
	
	void stopProcessing();
	
	void procesIncomingMessage(String strNewMessage);
}
