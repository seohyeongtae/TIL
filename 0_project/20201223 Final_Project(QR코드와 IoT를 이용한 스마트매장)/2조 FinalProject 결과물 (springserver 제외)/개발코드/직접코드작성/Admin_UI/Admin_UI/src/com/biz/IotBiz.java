package com.biz;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.IotDao;
import com.dao.OrdersDao;
import com.frame.Biz;
import com.frame.Dao;
import com.vo.Iot;
import com.vo.Orders;

@Service("iotbiz")

public class IotBiz  implements Biz<String, Iot>{
	
	@Resource(name = "iotdao")
	Dao<String, Iot> dao;
	
	@Resource(name = "iotdao")
	IotDao iotdao;

	@Override
	public void register(Iot v) throws Exception {
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
	public void modify(Iot v) throws Exception {
		int result = 0;
		result = dao.update(v);
		if(result == 0) {
			throw new Exception();
		}	
	}

	@Override
	public Iot get(String k) throws Exception {
		return dao.select(k);
	}

	@Override
	public ArrayList<Iot> get() throws Exception {
		return dao.selectall();
	}

	@Override
	public ArrayList<Iot> search(Object obj) throws Exception {
	
		return null;
	}
	
	public int updatedoor(int k) throws Exception {
			return iotdao.updatedoor(k);
	}
	public int updatelight(int k) throws Exception {
			return iotdao.updatelight(k);
		
	}
	public int updatetemp(int k) throws Exception {
		return iotdao.updatetemp(k);
	
	}


}
