package com.ordersystem.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
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
 * Servlet implementation class Dispatcher
 */
@WebServlet("/searchServlet")
public class ShowSummaryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
    public ShowSummaryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
    	CommonLog.recordLog("autoCompleteServlet");
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
    	getSessionFactory.closeSessionFactory();
    	CommonLog.recordLog("ShowSummaryServlet");
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
		String firstname = (String) jObject.get("firstname");
		String lastname = (String) jObject.get("lastname");
		firstname=firstname.trim();
		lastname=lastname.trim();
		if(firstname.equals("")&&lastname.equals("")) {
			response.getWriter().write("[{}]");
			return;
		}
	    ArrayList<String> names = new ArrayList<String>();
	    String flag = "both";
	    if(!firstname.equals("")) {
	    	names.add(firstname);
		    if(lastname.equals("")) {
		    	flag="first";
		    }
	    }
	    if(!lastname.equals("")) {
	    	names.add(lastname);
	    	if(firstname.equals("")) {
	    		flag="last";
	    	}
	    }
//	    System.out.println(flag);
//	    System.out.println(names.size());
	    ShowOrderService service = new ShowOrderService();
	    String resultSet = service.showOrderSummaryByName(names, flag);
//	    System.out.println(resultSet);
	    response.getWriter().write(resultSet);

	}

}
