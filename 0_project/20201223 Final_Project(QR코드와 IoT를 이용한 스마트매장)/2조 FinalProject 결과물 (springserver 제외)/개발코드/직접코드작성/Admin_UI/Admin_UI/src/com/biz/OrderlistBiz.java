package com.biz;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.OrderlistDao;
import com.frame.Biz;
import com.frame.Dao;
import com.vo.Orderlist;

@Service("orderlistbiz")

public class OrderlistBiz  implements Biz<String, Orderlist>{
	
	@Resource(name = "orderlistdao")
	Dao<String, Orderlist> dao;
	
	@Resource(name = "orderlistdao")
	OrderlistDao orderlistdao;

	@Override
	public void register(Orderlist v) throws Exception {
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
	public void modify(Orderlist v) throws Exception {
		int result = 0;
		result = dao.update(v);
		if(result == 0) {
			throw new Exception();
		}	
		
	}

	@Override
	public Orderlist get(String k) throws Exception {
		return dao.select(k);
	}

	@Override
	public ArrayList<Orderlist> get() throws Exception {
		return dao.selectall();
	}


	


	public ArrayList<Orderlist> getmenulist(String k) throws Exception {
		return orderlistdao.getorderlist(k);
	}
	


	@Override
	public ArrayList<Orderlist> search(Object obj) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
