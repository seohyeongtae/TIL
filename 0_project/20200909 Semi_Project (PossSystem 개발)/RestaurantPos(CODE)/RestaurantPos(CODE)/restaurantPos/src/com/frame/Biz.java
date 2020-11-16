package com.frame;

import java.util.ArrayList;

import org.springframework.transaction.annotation.Transactional;

public interface Biz<K,V> {
	@Transactional     // rollback ��� �߰�
	public void register (V v) throws Exception;
	@Transactional     // rollback ��� �߰�
	public void remove (K k) throws Exception;
	@Transactional     // rollback ��� �߰�
	public void modify (V v) throws Exception;
	
	
	public V get (K k) throws Exception;
	public ArrayList<V> get () throws Exception;
	public ArrayList<V> search (Object obj) throws Exception;

}
