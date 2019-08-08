package com.SalesMessageProcessor.Messages;

import com.SalesMessageProcessor.Enums.ESaleAdjustmentType;
import com.SalesMessageProcessor.Interfaces.IAdjustment;

/**
 * 
 * @author kartik rathore
 * Class represents adjustment data that needs to be applied to a product
 */
public class Adjustment implements IAdjustment {

	ESaleAdjustmentType m_eAdjustmentType;
	String m_strProductName;
	double m_dblAdjustValue;
	
	public Adjustment(ESaleAdjustmentType adjustmentType, String strPoductName, double dblAdjustmentValue)
	{
		m_eAdjustmentType = adjustmentType;
		m_strProductName = strPoductName;
		m_dblAdjustValue = dblAdjustmentValue;
	}
	
	@Override
	public ESaleAdjustmentType getAdjustmentType() {
		return m_eAdjustmentType;
	}

	@Override
	public String getProductName() {
		return m_strProductName;
	}

	@Override
	public double getAdjustmentValue() {
		return m_dblAdjustValue;
	}

	@Override
    public String toString() {
        return "Adjustment{" +
                "type=" + m_eAdjustmentType +
                ", productName='" + m_strProductName + '\'' +
                ", amount=" + m_dblAdjustValue +
                '}';
    }
}
