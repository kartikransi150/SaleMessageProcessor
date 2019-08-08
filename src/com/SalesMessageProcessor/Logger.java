package com.SalesMessageProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.SalesMessageProcessor.Enums.ESaleAdjustmentType;
import com.SalesMessageProcessor.Enums.ESaleType;
import com.SalesMessageProcessor.Interfaces.IAdjustment;
import com.SalesMessageProcessor.Interfaces.ILogger;
import com.SalesMessageProcessor.Interfaces.IMessage;
import com.SalesMessageProcessor.Interfaces.IProduct;

/**
 * 
 * @author kartik rathore
 * Logging class to log the sales and adjustments. 
 * This class would also be responsible for generating the sale and adjustment reports
 */
public class Logger implements ILogger {

	List<IAdjustment> m_saleAdjustmentMapping;
	HashMap<String, List<IProduct>> m_saleMapping;

	public Logger() {
		m_saleMapping = new HashMap<String, List<IProduct>>();
		m_saleAdjustmentMapping = new ArrayList<IAdjustment>();
	}

	@Override
	public String getSaleReport() {

		StringBuilder sb = new StringBuilder();
		sb.append("\r\n\r\n 10 sales appended to log \r\n");
		sb.append("*************** Log Report ***************** \r\n");
		sb.append("|Product           |Quantity   |Value      | \r\n");
		sb.append("------------------------------------------- \r\n");
		Set<Entry<String, List<IProduct>>> set = m_saleMapping.entrySet();
		Iterator<Entry<String, List<IProduct>>> iterator = set.iterator();
		while (iterator.hasNext()) {
			Entry<String, List<IProduct>> mentry = iterator.next();

			double dblTotalPrice = 0;
			double dblTotalSaleUnits = 0;
			List<IProduct> products = (List<IProduct>) mentry.getValue();
			for (IProduct saleProduct : products) {
				dblTotalSaleUnits += saleProduct.getTotalUnits();
				dblTotalPrice += saleProduct.getTotalProductPrice();
			}
			sb.append(String.format("| %-17s| %-10f| %-10.2f| \r\n", mentry.getKey(), dblTotalSaleUnits, dblTotalPrice));
		}
		sb.append("*************** Log End *****************");

		try {
			// Add 2 second pause
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	@Override
	public String getAdjustmentReport() {
		StringBuilder sb = new StringBuilder();
		sb.append("The applicationg is pausing and will stop processing new messages.\n");
		sb.append("=========== ADJUSTMENTS REPORT ===========\n");
		for (IAdjustment adjustment : m_saleAdjustmentMapping) {
			sb.append(adjustment).append("\n");
		}
		return sb.toString();
	}

	@Override
	public void processSale(IMessage message) {
		if (message != null) {
			String productName = message.getProduct().getName();
			List<IProduct> sales = null;
			ESaleType saleType = message.getSaleType();

			// now process the sale message
			switch (saleType) {
			case SingleSale:
			case MultiSale:
				if (!m_saleMapping.containsKey(productName)) {
					m_saleMapping.put(productName, new ArrayList<IProduct>());
				}

				sales = m_saleMapping.get(productName);

				// single/multiple sale message
				sales.add(message.getProduct());
				break;
			case SaleWithAdjustment:
				IAdjustment adjustData = message.getAdjustmentData();

				// only process a valid sale adjustment message
				if (adjustData != null && adjustData.getAdjustmentType() != ESaleAdjustmentType.None) {
					// adjustment to be made to the sale based on the product name
					for (IProduct sale : m_saleMapping.get(productName)) {
						sale.onSaleAdjustment(message.getAdjustmentData());
					}

					m_saleAdjustmentMapping.add(message.getAdjustmentData());
				}
				break;
			default:
				System.out.println("Invalid message has been received");
				break;
			}
		}

	}

}
