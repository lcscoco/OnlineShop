package edu.wtbu.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import edu.wtbu.dao.Helper;
import edu.wtbu.pojo.Order;
import edu.wtbu.pojo.Result;
import edu.wtbu.pojo.User;
import edu.wtbu.service.EthService;

/**
 * Servlet implementation class pay
 */
@WebServlet("/pay")
public class pay extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public pay() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf8");
		String email = request.getParameter("email");
		int orderId = 0;
		try {
			orderId = Integer.parseInt(request.getParameter("orderId"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		Result result = new Result("fail", null);
		if(email==null||email.equals("")) {
			result.setData("µ«¬º–≈œ¢ ß–ß£¨«Îµ«¬º∫Û÷ÿ ‘£°");
			response.getWriter().append(JSON.toJSONString(result));
			return;
		}
		String orderAddress = Helper.getOrderAddress();
		String transactionHash = Helper.getHash();
		User user = EthService.getUserByEmail(email, orderAddress);
		Order order = EthService.getOrderById(orderId, email, transactionHash, orderAddress);
		float balance = Float.parseFloat(EthService.getBalance(user.getUserAddress()));
		float money = Float.parseFloat(order.getOrderPrice());
		if(balance<money) {
			result.setData("”‡∂Ó≤ª◊„£¨«Î≥‰÷µ£°");
			response.getWriter().append(JSON.toJSONString(result));
			return;
		}
		String toResult = EthService.toAmount(user.getUserAddress(), EthService.getAddress(1), order.getOrderPrice(), user.getPassword());
		if(toResult==null) {
			result.setData("◊™’À ß∞‹£°");
			response.getWriter().append(JSON.toJSONString(result));
			return;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String payTime = sdf.format(new Date());
		String updateResult = EthService.updateOrderById(orderId, payTime, 1, orderAddress);
		if(updateResult!=null) {
			result.setFlag("success");
		}
		response.getWriter().append(JSON.toJSONString(result));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
