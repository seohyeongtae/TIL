package com.biz;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.InterestDao;
import com.dao.IotDao;
import com.dao.OrdersDao;
import com.frame.Biz;
import com.frame.Dao;
import com.vo.Interest;
import com.vo.Iot;
import com.vo.Orders;

@Service("interestbiz")

public class InterestBiz implements Biz<String, Interest>{
	
	@Resource(name = "interestdao")
	Dao<String, Interest> dao;
	
	@Resource(name = "interestdao")
	InterestDao interestdao;

	@Override
	public void register(Interest v) throws Exception {
		dao.insert(v);
		
	}

	@Override
	public void remove(String k) throws Exception {
		int result = 0;
		result = dao.delete(k);
		if(result == 0) {
			throw new Exception();
		}
		
	}

	@Override
	public void modify(Interest v) throws Exception {
		int result = 0;
		result = dao.update(v);
		if(result == 0) {
			throw new Exception();
		}	
		
	}

	@Override
	public Interest get(String k) throws Exception {
		return dao.select(k);
	}

	@Override
	public ArrayList<Interest> get() throws Exception {
		return dao.selectall();
	}

	@Override
	public ArrayList<Interest> search(Object obj) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	


}
