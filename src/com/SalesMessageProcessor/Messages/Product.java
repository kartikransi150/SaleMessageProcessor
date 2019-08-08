/**
 * 
 */
package com.SalesMessageProcessor.Messages;

import com.SalesMessageProcessor.Interfaces.IAdjustment;
import com.SalesMessageProcessor.Interfaces.IProduct;

/**
 * 
 * @author kartik rathore
 * Class represents a sale product
 */
public class Product implements IProduct {

	String m_strProductName;
	IAdjustment m_adjustmentData = null;
	double m_dblValue = 0;
	double m_dblTotalUnits = 0;
	
	public Product(String strName, double dblUnitPrice, double dblTotalUnits)
	{
		m_strProductName = strName;
		m_dblValue = dblUnitPrice;
		m_dblTotalUnits = dblTotalUnits;
	}
	
	public Product(String strName, double dblUnitPrice) 
	{
		this(strName, dblUnitPrice, 1);
	}
	
	@Override
	public void setName(String strProductName) {
		m_strProductName = strProductName;
	}

	@Override
	public String getName() {
		return m_strProductName;
	}

	@Override
	public void setProductValue(double dblValue) {
		m_dblValue = dblValue;
	}

	@Override
	public double getProductValue() {
		return m_dblValue;
	}

	
	@Override
	public void onSaleAdjustment(IAdjustment adjustmentData) {
		m_adjustmentData = adjustmentData;
		
		double adjustValue = m_adjustmentData.getAdjustmentValue();
		
		switch(m_adjustmentData.getAdjustmentType())
		{
		case Add:
			m_dblValue += adjustValue;
			break;
		case Multiply:
			m_dblValue *= adjustValue;
			break;
		case Substract:
			m_dblValue -= adjustValue;
			break;
		default:
			// we should really never be getting here.. if we do then something has gone wrong
			break;
		}
		
		m_dblValue  = m_dblValue < 0? 0 : m_dblValue;
		
	}

	 @Override
	    public String toString() {
	        return "Sale{" +
	                "productName='" + m_strProductName + '\'' +
	                ", unitPrice=" + m_dblValue +
	                ", totalUnits=" + m_dblTotalUnits +
	                '}';
	    }

	@Override
	public void setTotalUnits(double dbltotalUnits) {
		m_dblTotalUnits = dbltotalUnits;
	}

	@Override
	public double getTotalUnits() {
		return m_dblTotalUnits;
	}

	@Override
	public double getTotalProductPrice() {
		return m_dblTotalUnits * m_dblValue;
	}
}
