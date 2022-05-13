package edu.wtbu.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.wtbu.service.EthService;

public class Helper {
	static String transactionHash = null;
	static int ids = 1;
	static Map<Integer, String> map = new HashMap<Integer, String>();
	public static void main() {
		transactionHash = EthService.sendTransaction();
	}
	public static String getHash() {
		return transactionHash;
	}
	public static String getOrderAddress() {
		return EthService.getOrderAddressByHash(transactionHash);
	}
	public static int getIds() {
		return ids++;
	}
	public static void setMap(int id,String email) {
		map.put(id, email);
	}
	public static ArrayList<Integer> getIdList(String email){
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int i=1;i<=map.size();i++) {
			if(map.get(i).equals(email)) {
				list.add(i);
			}
		}
		return list;
	}
}
