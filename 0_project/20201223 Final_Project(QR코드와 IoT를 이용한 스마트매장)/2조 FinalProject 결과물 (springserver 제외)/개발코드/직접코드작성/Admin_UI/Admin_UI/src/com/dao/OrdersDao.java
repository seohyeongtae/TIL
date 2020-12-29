package com.dao;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.frame.Dao;
import com.vo.Orders;
@Repository("ordersdao")

public interface OrdersDao extends Dao<String, Orders> {
	
	public ArrayList<Orders> gettotallist (String k) throws Exception;	
	
}