package com.SalesMessageProcessor;

import com.SalesMessageProcessor.Enums.ESaleAdjustmentType;
import com.SalesMessageProcessor.Enums.ESaleType;
import com.SalesMessageProcessor.Interfaces.IAdjustment;
import com.SalesMessageProcessor.Interfaces.IProduct;
import com.SalesMessageProcessor.Interfaces.IMessage;
import com.SalesMessageProcessor.Messages.Adjustment;
import com.SalesMessageProcessor.Messages.Product;
import com.SalesMessageProcessor.Messages.Message;

/**
 * 
 * @author kartik rathore
 * Class responsible for parsing an incoming message
 */
public class MessageParser {

	IMessage m_saleMessage = null;

	public IMessage getMessage() {
		return m_saleMessage;
	}

	// Validates the message and identifies the message type to get it
	// parsed properly to obtain product details.
	// @return[Boolean] false on wrong message else returns true
	public boolean ParseMessage(String message) {
		if (message == null || message.isEmpty()) {
			return false;
		}

		m_saleMessage = null;
		String[] messageArray = message.trim().split("\\s+");
		String firstWord = messageArray[0];
		if (firstWord.matches("Add|Subtract|Multiply")) {
			return parseSaleAdjustmentMessage(messageArray);
		} else if (firstWord.matches("^\\d+")) {
			return parseMultiSaleMessage(messageArray);
		} else if (messageArray.length == 3 && messageArray[1].contains("at")) {
			return parseSingleSaleMessage(messageArray);
		} else {
			System.out.println("Wrong sales notice");
		}
		return true;
	}

	// Parse single sale message
	// @return[Boolean] false on wrong message else returns true
	private boolean parseSingleSaleMessage(String[] messageArray) {
		boolean bRet = false;
		if (messageArray.length > 3 || messageArray.length < 3)
			bRet = false;

		try {
			m_saleMessage = new Message(ESaleType.SingleSale);
			IProduct product = new Product(parseProductName(messageArray[0]), parsePrice(messageArray[2]));
			m_saleMessage.setProduct(product);

			bRet = true;
		} catch (Exception ex) {

		}

		return bRet;
	}

	// Parse MultiSale message
	// @return[Boolean] false on wrong message else returns true
	private boolean parseMultiSaleMessage(String[] messageArray) {
		boolean bRet = false;
		if (messageArray.length > 7 || messageArray.length < 7)
			return bRet;

		try {
			m_saleMessage = new Message(ESaleType.MultiSale);
			IProduct product = new Product(parseProductName(messageArray[3]), parsePrice(messageArray[5]),
					Integer.parseInt(messageArray[0]));
			m_saleMessage.setProduct(product);

			bRet = true;
		} catch (Exception ex) {

		}

		return bRet;
	}

	// Parse sale adjustment message
	// @return[Boolean] false on wrong message else returns true
	private boolean parseSaleAdjustmentMessage(String[] messageArray) {
		boolean bRet = false;
		if (messageArray.length > 3 || messageArray.length < 3)
			bRet = false;

		try {
			m_saleMessage = new Message(ESaleType.SaleWithAdjustment);
			IProduct product = new Product(parseProductName(messageArray[2]), parsePrice(messageArray[1]));
			m_saleMessage.setProduct(product);

			IAdjustment adjustment = new Adjustment(parseAdjustmentType(messageArray[0]),
					parseProductName(messageArray[2]), parsePrice(messageArray[1]));
			m_saleMessage.setAdjustment(adjustment);
			bRet = true;
		} catch (Exception ex) {

		}

		return bRet;
	}

	// Parse the adjustment type operation that needs to be performed on the sales
	private ESaleAdjustmentType parseAdjustmentType(String strAdjustmentType) {

		if (strAdjustmentType.toLowerCase().startsWith("add")) {
			return ESaleAdjustmentType.Add;
		}
		if (strAdjustmentType.toLowerCase().startsWith("multiply")) {
			return ESaleAdjustmentType.Multiply;
		}
		if (strAdjustmentType.toLowerCase().startsWith("subtract")) {
			return ESaleAdjustmentType.Substract;
		}

		return ESaleAdjustmentType.None;
	}

	// Just to handle the plural cases of the fruit products
	// Made under the assumption that no other sale items will be given. e.g
	// Tumbler, Plates. etc.
	// @return[String] parsed string e.g 'mango' will become 'mangoes'
	private String parseProductName(String strProductName) {
		String parsedType = "";
		String typeWithoutLastChar = strProductName.substring(0, strProductName.length() - 1);
		// enum DEPREC
		if (strProductName.endsWith("o")) {
			parsedType = String.format("%soes", typeWithoutLastChar);
		} else if (strProductName.endsWith("y")) {
			parsedType = String.format("%sies", typeWithoutLastChar);
		} else if (strProductName.endsWith("h")) {
			parsedType = String.format("%shes", typeWithoutLastChar);
		} else if (!strProductName.endsWith("s")) {
			parsedType = String.format("%ss", strProductName);
		} else {
			parsedType = String.format("%s", strProductName);
		}
		return parsedType.toLowerCase();
	}

	// Parse the price and get only the value
	// @return[double] e.g "20p" will become 0.20
	private double parsePrice(String strPrice) {
		double price = Double.parseDouble(strPrice.replaceAll("£|p", ""));
		if (!strPrice.contains(".")) {
			price = Double.valueOf(Double.valueOf(price) / Double.valueOf("100"));
		}
		return price;
	}
}
