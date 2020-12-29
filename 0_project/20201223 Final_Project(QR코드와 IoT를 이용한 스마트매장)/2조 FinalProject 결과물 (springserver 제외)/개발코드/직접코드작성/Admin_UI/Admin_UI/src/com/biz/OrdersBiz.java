package com.biz;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.OrdersDao;
import com.frame.Biz;
import com.frame.Dao;
import com.vo.Orders;

@Service("ordersbiz")

public class OrdersBiz  implements Biz<String, Orders>{
	
	@Resource(name = "ordersdao")
	Dao<String, Orders> dao;
	
	@Resource(name = "ordersdao")
	OrdersDao ordersdao;

	@Override
	public void register(Orders v) throws Exception {
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
	public void modify(Orders v) throws Exception {
		int result = 0;
		result = dao.update(v);
		if(result == 0) {
			throw new Exception();
		}	
		
	}

	@Override
	public Orders get(String k) throws Exception {
		return dao.select(k);
	}

	@Override
	public ArrayList<Orders> get() throws Exception {
		return dao.selectall();
	}




	public ArrayList<Orders> gettotallist(String k) throws Exception {
		return ordersdao.gettotallist(k);
	}

	@Override
	public ArrayList<Orders> search(Object obj) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
