package com.biz;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.ReceiptDao;
import com.frame.Biz;
import com.frame.Dao;
import com.vo.Receipt;

@Service("receiptbiz")

public class ReceiptBiz implements Biz<String, Receipt> {
	
	@Resource(name = "receiptdao")
	Dao<String, Receipt> dao;

	@Override
	public void register(Receipt v) throws Exception {
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
	public void modify(Receipt v) throws Exception {
		int result = 0;
		result = dao.update(v);
		if(result == 0) {
			throw new Exception();
		}	
		
	}

	@Override
	public Receipt get(String k) throws Exception {
		return dao.select(k);
	}

	@Override
	public ArrayList<Receipt> get() throws Exception {
		return dao.selectall();
	}

	@Override
	public ArrayList<Receipt> search(Object obj) throws Exception {
		return null;
	}
	
	@Resource(name = "receiptdao")
	ReceiptDao receiptdao;

	public String getreceiptid() throws Exception {
		return receiptdao.getreceiptid();
	}
	
	public Integer[] getdaytotal(String i) throws Exception {
		return receiptdao.getdaytotal("i");
	}


}
