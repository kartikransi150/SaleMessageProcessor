package com.SalesMessageProcessor.Interfaces;

import com.SalesMessageProcessor.Enums.ESaleType;

public interface IMessage {

	ESaleType getSaleType();
		
	void setProduct(IProduct product);
	
	IProduct getProduct();
		
	double getTotalPrice();
	
	void setAdjustment(IAdjustment adjustmentData);
	
	IAdjustment getAdjustmentData();
	
}
