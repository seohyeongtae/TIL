package com.dao;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.frame.Dao;
import com.vo.Sales;
@Repository("salesdao")

public interface SalesDao extends Dao<String, Sales> {
	
	public ArrayList<Sales> searchreceipt(String k) throws Exception;
	
}
