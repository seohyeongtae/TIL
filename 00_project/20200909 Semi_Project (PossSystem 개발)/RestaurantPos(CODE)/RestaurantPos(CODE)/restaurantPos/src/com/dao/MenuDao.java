package com.dao;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.frame.Dao;
import com.vo.Menu;
@Repository("menudao")

public interface MenuDao extends Dao<String, Menu> {
	
	public ArrayList<Menu> getmenulist (String k) throws Exception;	
	
}
