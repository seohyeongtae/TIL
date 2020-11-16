package com.biz;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.frame.Biz;
import com.frame.Dao;
import com.vo.Tab;

@Service("tabbiz")

public class TabBiz implements Biz<String, Tab> {
	
	@Resource(name = "tabdao")
	Dao<String, Tab> dao;

	@Override
	public void register(Tab v) throws Exception {
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
	public void modify(Tab v) throws Exception {
		int result = 0;
		result = dao.update(v);
		if(result == 0) {
			throw new Exception();
		}	
		
	}

	@Override
	public Tab get(String k) throws Exception {
		return dao.select(k);
	}

	@Override
	public ArrayList<Tab> get() throws Exception {
		return dao.selectall();
	}

	@Override
	public ArrayList<Tab> search(Object obj) throws Exception {
		return null;
	}

	
	


}
