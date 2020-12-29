package com.dao;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.frame.Dao;
import com.vo.Interest;
import com.vo.Orders;
@Repository("interestdao")

public interface InterestDao<K> extends Dao<String, Interest> {
	

}