package com.SalesMessageProcessor.Interfaces;

public interface IProduct {
	
	void setName(String strProductName);
	
	String getName();
	
	void setProductValue(double dblValue);
	
	double getProductValue();
	
	void setTotalUnits(double dbltotalUnits);
	
	double getTotalUnits();
	
	double getTotalProductPrice();
	
	void onSaleAdjustment(IAdjustment adjustmentData);
	
}
