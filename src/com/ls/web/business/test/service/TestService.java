package com.ls.web.business.test.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ls.web.business.test.dao.TestDao;

/**
 * @Title: TestService.java 
 * @Package com.ls.web.business.test.service 
 * @Description:
 * @author linsheng
 * @email hi.linsheng@foxmail.com
 * @date 2016年5月16日 上午11:05:02 
 * @version V1.0   
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/config/spring.xml"})
public class TestService {

	@Autowired
	private TestDao testDao;
	
	@Test
	public void testInsert(){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("host_mac", "00-11");
		params.put("host_id", "00-11");
		params.put("host_ip", "00-11");
		testDao.insert(params);
	}
	
	@Test
	public void testDelete(){
		testDao.deleteById("00-11");
	}

	@Test
	public void testUpdate() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("host_mac", "13121");
		params.put("host_id", "123");
		params.put("host_ip", "00-11");
		testDao.update(params);
	}
	
	@Test
	public void testQuery() {
		List<Map<String, Object>> list = testDao.queryList(null);
		for(Map<String,Object> map : list){
			System.out.println(map);
		}
	}
	
}
