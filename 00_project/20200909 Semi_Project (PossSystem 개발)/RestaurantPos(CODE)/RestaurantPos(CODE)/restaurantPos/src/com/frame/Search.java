package com.frame;

import java.util.ArrayList;
import com.common.Pagination;

public interface Search<K, V> {
	public int getcnt() throws Exception;
	public ArrayList<V> selectpage(Pagination pagination) throws Exception;
}
