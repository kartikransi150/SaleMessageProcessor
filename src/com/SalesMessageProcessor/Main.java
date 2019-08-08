package com.SalesMessageProcessor;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * 
 * @author kartik rathore
 * Main class which is the starting point of the application
 */
public class Main {

	public static void main(String[] args) {
				
		MessageProcessor messageProcessor = new  MessageProcessor();
		try {
			String line;
			messageProcessor.startProcessing();
			new Thread(messageProcessor).start(); // start the message processor runnable
			
			BufferedReader inputFile = new BufferedReader(new FileReader("testInput/input.txt"));
			while ((line = inputFile.readLine()) != null) {
				messageProcessor.procesIncomingMessage(line);// add the incoming 
			}
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
