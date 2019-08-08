package com.SalesMessageProcessor.Messages;

import com.SalesMessageProcessor.Enums.ESaleType;
import com.SalesMessageProcessor.Interfaces.IAdjustment;
import com.SalesMessageProcessor.Interfaces.IProduct;
import com.SalesMessageProcessor.Interfaces.IMessage;

/**
 * 
 * @author kartik rathore
 * Class represents incoming messages 
 */
public class Message implements IMessage {

	double m_dblTotalPrice;
	ESaleType m_eSaleType;
	IProduct m_product;
	IAdjustment m_adjustmentData;

	
	public Message()
	{
		m_dblTotalPrice = 0;
		m_eSaleType = ESaleType.None;
		m_product = null;
		m_adjustmentData = null;
	}
	
	public Message(ESaleType messageType)
	{
		m_eSaleType =  messageType;
	}
	
	@Override
	public ESaleType getSaleType() {
		// TODO Auto-generated method stub
		return m_eSaleType;
	}

	

	@Override
	public double getTotalPrice() {
		return m_dblTotalPrice;
	}

	@Override
	public void setProduct(IProduct product) {
		m_product = product;
	}

	@Override
	public IProduct getProduct() {
		// TODO Auto-generated method stub
		return m_product;
	}
	
	@Override
	public void setAdjustment(IAdjustment adjustmentData) {
		m_adjustmentData = adjustmentData;
	}

	@Override
	public IAdjustment getAdjustmentData() {
		// TODO Auto-generated method stub
		return m_adjustmentData;
	}

}
