package edu.wtbu.dao;

import java.util.ArrayList;

import edu.wtbu.pojo.Goods;
import edu.wtbu.service.EthService;

public class GoodsDao {
	public static ArrayList<Goods> getGoodsList(){
		ArrayList<Goods> list = new ArrayList<Goods>();
		String orderAddress = Helper.getOrderAddress();
		for(int i=1;i<=4;i++) {
			Goods goods = new Goods();
			goods = EthService.getGoodsById(i, orderAddress);
			list.add(goods);
		}
		return list;
	}
}
