package com.ordersystem.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class getSessionFactory 
{	
	private static SessionFactory sf=null;
	private getSessionFactory(){}
	static {
		Configuration cf=new Configuration().configure();
		sf=cf.buildSessionFactory();
	}
	public static SessionFactory getsessionFactory()
	{
		return sf;
	}
	public static void closeSessionFactory() {
		if(sf!=null)
			sf.close();
	}
}
