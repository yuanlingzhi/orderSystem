package com.ordersystem.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ordersystem.logtool.CommonLog;
import com.ordersystem.service.ShowOrderService;
import com.ordersystem.utils.getSessionFactory;

/**
 * Servlet implementation class ShowOrderDetailServlet
 */
@WebServlet("/ShowOrderDetailServlet")
public class ShowOrderDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
    	getSessionFactory.closeSessionFactory();
    	CommonLog.recordLog("ShowOrderDetailServlet");
	}
    public ShowOrderDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		String id = request.getParameter("qid");
		int orderNumber  = Integer.parseInt(id);
	    ShowOrderService service = new ShowOrderService();
	    String resultSet = service.showShippedOrderDetail(orderNumber);
//	    System.out.println(resultSet);
	    response.getWriter().write(resultSet);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
    	CommonLog.recordLog("autoCompleteServlet");
	}
}
