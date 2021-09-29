package com.dao;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.frame.Dao;
import com.vo.Orderlist;
@Repository("orderlistdao")

public interface OrderlistDao extends Dao<String, Orderlist> {
	
	public ArrayList<Orderlist> getorderlist (String k) throws Exception;

	
}