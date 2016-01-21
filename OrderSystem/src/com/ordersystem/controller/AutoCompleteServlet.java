package com.ordersystem.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.ordersystem.logtool.CommonLog;
import com.ordersystem.service.ShowOrderService;
import com.ordersystem.utils.getSessionFactory;

/**
 * Servlet implementation class AutoCompleteServlet
 */
@WebServlet("/autoCompleteServlet")
public class AutoCompleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     @Override
    public void destroy() {
    	// TODO Auto-generated method stub
    	super.destroy(); 
    	getSessionFactory.closeSessionFactory();
    }  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AutoCompleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    @Override
    public void init() throws ServletException {
    	// TODO Auto-generated method stub
    	super.init();
    	CommonLog.recordLog("autoCompleteServlet");
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		BufferedReader br = request.getReader();
		StringBuffer sb = new StringBuffer();
		String s ="";
		while((s = br.readLine())!=null) {
			sb.append(s);
		}
		System.out.println(sb.toString());
		JSONParser jp = new JSONParser();
		JSONObject jObject=null;
		try {
			jObject = (JSONObject) jp.parse(sb.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String name = (String) jObject.get("name");
		String flag = (String) jObject.get("flag");
		String nameother = (String) jObject.get("nameother");
		name=name.trim();
		flag=flag.trim();
		nameother=nameother.trim();

//	    System.out.println(flag);
//	    System.out.println(name);
//	    System.out.println(nameother);
	    ShowOrderService service = new ShowOrderService();
	    String resultSet = service.nameAutoComplete(name, flag, nameother);
//	    System.out.println(resultSet);
	    response.getWriter().write(resultSet);

	}

}
