package com.ordersystem.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ordersystem.domain.Orderdetails;
import com.ordersystem.domain.Orders;
import com.ordersystem.utils.getSessionFactory;

public class ShowOrderService {
	Session session = null;
	Transaction tx = null;
	public ShowOrderService() {
		session = this.getSession();
	}
	public Session getSession() {
		return getSessionFactory.getsessionFactory().openSession();
	}
	public void closeSession() {
		if(session!=null || session.isOpen()) {
			session.close();
		}
	}
	public synchronized String showOrderSummaryByName(ArrayList<String> names,String flag) {
		StringBuffer result = null;
		Query query = null;
		try {
			tx = session.beginTransaction();
			if(flag.equals("both") && names.size()==2) {
				
				query=session.createQuery("from Orders where customers.contactLastName = :cln  and customers.contactFirstName = :cfn ");
				query.setParameter("cln", names.get(1));
				query.setParameter("cfn", names.get(0));
				
			}else if(flag.equals("first") && names.size()==1) {
				
				query=session.createQuery("from Orders where customers.contactFirstName = :cfn ");
				query.setParameter("cfn", names.get(0));
				
			}else if(flag.equals("last") && names.size()==1) {
				
				query=session.createQuery("from Orders where customers.contactLastName = :cln  ");
				query.setParameter("cln", names.get(0));
				
			}else {
				
				System.out.println("input name list or flag wrong! at function showOrderSummaryByName(ArrayList<String> names,String flag)");
				return "[{}]";
			}
			List<Orders> orders = query.list();
			result = AssistService.ordersJsonFormat(orders);
			tx.commit();
		} catch (Exception e) {
			if(tx!=null) {
				tx.rollback();
			}
			System.out.println("function showOrderSummaryFirstLastName(String cfn, String cln) errors!");
			e.printStackTrace();
		}finally {
			this.closeSession();
		}
		return result.toString();
	}
	
	public synchronized String showShippedOrderDetail(int orderNumber) {
		StringBuffer result = null;
		Query query = null;
		try {
			tx = session.beginTransaction();

			query=session.createQuery("from Orderdetails where id.orderNumber = :orderNumber");
			query.setParameter("orderNumber", orderNumber);

			List<Orderdetails> orderdetails = query.list();
			result = AssistService.orderdetailsJsonFormat(orderdetails);
			tx.commit();
		} catch (Exception e) {
			if(tx!=null) {
				tx.rollback();
			}
			System.out.println("function showShippedOrderDetail(int orderNumber) errors!");
			e.printStackTrace();
		}finally {
			this.closeSession();
		}
		return result.toString();
	}
	public synchronized String nameAutoComplete(String name,String flag,String other) {
		StringBuffer result = null;
		Query query = null;
		try {
			tx = session.beginTransaction();
			if(flag.equals("first")) {
				if(other.equals("")) {
					query = session.createQuery("select contactFirstName from Customers where lower(contactFirstName) LIKE lower(:name)");
					query.setParameter("name", name+"%");
				}else {
					query = session.createQuery("select contactFirstName from Customers where lower(contactFirstName) LIKE lower(:name) and lower(contactLastName) = lower(:other)");
					query.setParameter("name", name+"%");
					query.setParameter("other", other);
				}
			}else {
				if(other.equals("")) {
					query = session.createQuery("select contactLastName from Customers where lower(contactLastName) LIKE lower(:name)");
					query.setParameter("name", name+"%");
				}else {
					query = session.createQuery("select contactLastName from Customers where lower(contactLastName) LIKE lower(:name) and lower(contactFirstName) = lower(:other)");
					query.setParameter("name", name+"%");
					query.setParameter("other", other);
				}
			}
			query.setMaxResults(10);
			List<String> nameList = query.list();
			result = AssistService.nameListToJson(nameList);
			tx.commit();
		} catch (Exception e) {
			if(tx!=null) {
				tx.rollback();
			}
			System.out.println("function nameAutoComplete(String name,String flag) errors!");
			e.printStackTrace();
		}finally {
			this.closeSession();
			
		}
		return result.toString();
	}
}
