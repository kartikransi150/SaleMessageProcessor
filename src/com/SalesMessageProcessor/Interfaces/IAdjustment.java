package com.SalesMessageProcessor.Interfaces;

import com.SalesMessageProcessor.Enums.ESaleAdjustmentType;

public interface IAdjustment {

	ESaleAdjustmentType getAdjustmentType();
		
	String getProductName();	
	
	double getAdjustmentValue();
}
