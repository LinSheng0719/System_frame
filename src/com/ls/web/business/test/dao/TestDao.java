package com.ls.web.business.test.dao;

import java.util.List;
import java.util.Map;

/**
 * @Title: TestDao.java
 * @Package com.ls.web.business.test.dao
 * @Description:
 * @author linsheng
 * @email hi.linsheng@foxmail.com
 * @date 2016年5月16日 下午2:47:27
 * @version V1.0
 */
public interface TestDao {

	public int insert(Map<String, Object> params);

	public int deleteById(String id);

	public int update(Map<String, Object> params);

	public Map<String, Object> getMapByMap(Map<String, Object> params);

	public List<Map<String, Object>> queryList(Map<String, Object> params);

	public List<Map<String, Object>> queryPageList(Map<String, Object> params);
}
