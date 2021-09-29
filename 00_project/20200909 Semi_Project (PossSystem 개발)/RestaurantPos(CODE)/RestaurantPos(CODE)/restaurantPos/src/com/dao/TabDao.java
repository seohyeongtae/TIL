package com.dao;

import org.springframework.stereotype.Repository;

import com.frame.Dao;
import com.vo.Tab;


@Repository("tabdao")
public interface TabDao extends Dao<String, Tab> {

}
