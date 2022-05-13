package edu.wtbu.dao;

import java.util.ArrayList;

import edu.wtbu.pojo.Order;
import edu.wtbu.service.EthService;

public class OrderDao {
	public static ArrayList<Order> getGoodsList(String email){
		ArrayList<Order> list = new ArrayList<Order>();
		ArrayList<Integer> ids = Helper.getIdList(email);
		String transactionHash = Helper.getHash();
		String orderAddress = Helper.getOrderAddress();
		for(int i=0;i<ids.size();i++) {
			Order order = new Order();
			order = EthService.getOrderById(ids.get(i), email, transactionHash, orderAddress);
			list.add(order);
		}
		return list;
	}
}
