package com.dao;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.frame.Dao;
import com.vo.Orders;
@Repository("iotdao")

public interface IotDao<K> extends Dao<String, Orders> {
	
	public int updatedoor (K k) throws Exception;
	public int updatelight (K k) throws Exception;
	public int updatetemp (K k) throws Exception;

}