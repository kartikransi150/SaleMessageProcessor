package com.SalesMessageProcessor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.SalesMessageProcessor.Enums.ESaleType;
import com.SalesMessageProcessor.Interfaces.ILogger;
import com.SalesMessageProcessor.Interfaces.IMessage;
import com.SalesMessageProcessor.Interfaces.IMessageProcessor;
import com.SalesMessageProcessor.Messages.Message;

/**
 * 
 * @author kartik rathore
 * Class represents async message processing using a queue
 */
public class MessageProcessor implements IMessageProcessor, Runnable {

	private BlockingQueue<IMessage> m_messageQueue;
	final ILogger m_logger;
	volatile boolean m_bStartParsing = false;
	volatile boolean m_bCanAcceptMessages = true;

	public MessageProcessor() {
		m_logger = new Logger();
		m_messageQueue = new LinkedBlockingQueue<IMessage>(1000);
	}

	@Override
	public void startProcessing() {
		if (m_bStartParsing) {
			// already running
			return;
		}

		m_bStartParsing = true;
	}

	@Override
	public void stopProcessing() {

		if (m_bStartParsing) {
			// we are parsing so to stop parsing we just add a null message
			m_messageQueue.clear();
			m_messageQueue.add(new Message(ESaleType.None));
		}
	}

	@Override
	public void procesIncomingMessage(String strNewMessage) {
		if (m_bCanAcceptMessages) {
			// parse the message
			MessageParser parser = new MessageParser();
			if (parser.ParseMessage(strNewMessage)) {
				m_messageQueue.add(parser.getMessage());
			}
			else
			{
				System.out.println("failed to parse message: " + strNewMessage);
			}
		}
	}

	@Override
	public void run() {
		int iProcessCount = 0;
		while (m_bStartParsing) {
			// process the queue until its not empty
			IMessage message;
			try {
				message = m_messageQueue.take();

				if (message.getSaleType() == ESaleType.None) {
					m_bStartParsing = false;
				} else {
					iProcessCount += 1;
					// process the message
					m_logger.processSale(message);

					if (iProcessCount % 10 == 0) {
						// every 10th count
						String strSaleLog = m_logger.getSaleReport();
						System.out.println(strSaleLog);
					}

					if (iProcessCount % 50 == 0) {
						// every 50th count
						m_bCanAcceptMessages = false; // set the flag that we can accept the message
						String strAdjustment = m_logger.getAdjustmentReport();
						System.out.println(strAdjustment);
						
						System.exit(0);
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
