package com.biz;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.MenuDao;
import com.frame.Biz;
import com.frame.Dao;
import com.vo.Menu;

@Service("menubiz")

public class MenuBiz implements Biz<String, Menu> {
	
	@Resource(name = "menudao")
	Dao<String, Menu> dao;

	@Override
	public void register(Menu v) throws Exception {
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
	public void modify(Menu v) throws Exception {
		int result = 0;
		result = dao.update(v);
		if(result == 0) {
			throw new Exception();
		}	
		
	}

	@Override
	public Menu get(String k) throws Exception {
		return dao.select(k);
	}

	@Override
	public ArrayList<Menu> get() throws Exception {
		return dao.selectall();
	}

	@Override
	public ArrayList<Menu> search(Object obj) throws Exception {
		return null;
	}

	@Resource(name = "menudao")
	MenuDao menudao;

	public ArrayList<Menu> getmenulist(String k) throws Exception {
		return menudao.getmenulist(k);
	}


}
