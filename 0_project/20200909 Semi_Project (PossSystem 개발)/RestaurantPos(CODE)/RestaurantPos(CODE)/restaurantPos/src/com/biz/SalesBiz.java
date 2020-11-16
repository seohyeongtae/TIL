package com.biz;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.SalesDao;
import com.frame.Biz;
import com.frame.Dao;
import com.vo.Sales;

@Service("salesbiz")

public class SalesBiz implements Biz<String, Sales> {
	
	@Resource(name = "salesdao")
	Dao<String, Sales> dao;

	@Override
	public void register(Sales v) throws Exception {
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
	public void modify(Sales v) throws Exception {
		int result = 0;
		result = dao.update(v);
		if(result == 0) {
			throw new Exception();
		}	
		
		
	}

	@Override
	public Sales get(String k) throws Exception {
		return dao.select(k);
	}

	@Override
	public ArrayList<Sales> get() throws Exception {
		return dao.selectall();
	}

	@Override
	public ArrayList<Sales> search(Object obj) throws Exception {
		return dao.search(obj);
	}
	
	@Resource(name = "salesdao")
	SalesDao sdao;

	public ArrayList<Sales> searchReceipt(String k) throws Exception {
		return sdao.searchreceipt(k);
	}
	
	


}
