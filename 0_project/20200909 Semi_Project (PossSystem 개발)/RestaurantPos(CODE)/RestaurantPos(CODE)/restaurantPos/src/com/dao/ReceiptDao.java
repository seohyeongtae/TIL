package com.dao;

import org.springframework.stereotype.Repository;

import com.frame.Dao;
import com.vo.Receipt;


@Repository("receiptdao")
public interface ReceiptDao extends Dao<String, Receipt> {
	public String getreceiptid () throws Exception;
	public Integer[] getdaytotal(String k) throws Exception;
	public Integer gettodaytotal(String k) throws Exception;
}
