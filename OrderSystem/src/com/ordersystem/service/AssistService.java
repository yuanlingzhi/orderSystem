package com.ordersystem.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;







import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.ordersystem.domain.Orderdetails;
import com.ordersystem.domain.Orders;

public class AssistService {
	
	public static StringBuffer ordersJsonFormat(List<Orders> orders) {
		if(orders==null || orders.size()==0) {
			return new StringBuffer("[{}]");
		}
		StringBuffer result=new StringBuffer("[");
		for(Orders order:orders) {
			String append="{\"order_number\":"+order.getOrderNumber()+","
					+ "\"first_name\":\""+order.getCustomers().getContactFirstName()+"\","
							+ "\"last_name\":\""+order.getCustomers().getContactLastName()+"\","
									+ "\"order_status\":\""+order.getStatus()+"\","
											+ "\"order_date\":\""+dateFormat(order.getOrderDate())+"\","
													+ "\"shipped_date\":\""+dateFormat(order.getShippedDate())+"\"},";
			result.append(append);
		}
		result.setLength(result.length()-1);
		result.append("]");
		return result;
	}
	public static StringBuffer nameListToJson(List<String> nameList) {
		if(nameList==null || nameList.size()==0) {
			return new StringBuffer("[{}]");
		}
		StringBuffer result=new StringBuffer("[");
		HashMap<String, String> hm = new HashMap<String, String>();
		for(String name:nameList) {
			name = name.trim();
			String append="{\"name\":\""+name+"\"},";
			if(hm.containsKey(name))
				continue;
			else {
				hm.put(name, name);
				result.append(append);
			}
		}
		result.setLength(result.length()-1);
		result.append("]");
		return result;
	}
	public static StringBuffer orderdetailsJsonFormat(List<Orderdetails> orderdetails) {
		if(orderdetails==null || orderdetails.size()==0) {
			return new StringBuffer("[{}]");
		}
		StringBuffer result=new StringBuffer("[");
		for(Orderdetails orderdetail:orderdetails) {
			if(!orderdetail.getOrders().getStatus().equals("Shipped"))
				continue;
			String append="{\"order_number\":"+orderdetail.getId().getOrderNumber()+","
					+ "\"product_name\":\""+orderdetail.getProducts().getProductName()+"\","
							+ "\"product_desc\":\""+specialStringEscape(orderdetail.getProducts().getProductDescription())+"\","
									+ "\"quantity\":\""+orderdetail.getQuantityOrdered()+"\","
											+ "\"price_each\":\""+orderdetail.getPriceEach()+"\","
													+ "\"price_total\":\""+calculateTotalPrice(orderdetail.getPriceEach(),orderdetail.getQuantityOrdered())+"\"},";
			result.append(append);
		}
		result.setLength(result.length()-1);
		result.append("]");
		return result;
	}
	public static String dateFormat(Date date) {
		if(date==null)
			return "Not Yet!";
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String dateFormat = sdf.format(date); 
		return dateFormat;
	}
	public static String calculateTotalPrice(double each,int quant) {
		double result = each*quant;
		DecimalFormat df = new DecimalFormat("#0.00");
		String resultFormat = df.format(result);
		return resultFormat;
	}
	public static String specialStringEscape(String str) {
		String result = "";
		result = StringEscapeUtils.escapeJava(str);
		result = StringUtils.chomp(result);
		return result;
	}
	
}
