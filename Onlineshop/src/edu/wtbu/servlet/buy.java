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
import edu.wtbu.pojo.Goods;
import edu.wtbu.pojo.Result;
import edu.wtbu.service.EthService;

/**
 * Servlet implementation class buy
 */
@WebServlet("/buy")
public class buy extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public buy() {
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
		int goodsId = 0;
		try {
			goodsId = Integer.parseInt(request.getParameter("goodsId"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		int goodsNum = 0;
		try {
			goodsNum = Integer.parseInt(request.getParameter("goodsNum"));
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
		Goods goods = EthService.getGoodsById(goodsId, orderAddress);
		String orderPrice = String.valueOf(goods.getPrice()*goodsNum);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String buyTime = sdf.format(new Date());
		String addResult = EthService.addOrderList(goods.getName(), goodsNum, goods.getImgUrl(), orderPrice, buyTime, "", 0, orderAddress);
		if(addResult==null) {
			result.setData("π∫¬Ú ß∞‹£°");
		}else {
			Helper.setMap(Helper.getIds(), email);
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
