package com.ls.web.business.test.service;

import org.apache.log4j.Logger;

/**
 * @Title: Log4Jtest.java 
 * @Package com.ls.web.business.test.service 
 * @Description:
 * @author linsheng
 * @email hi.linsheng@foxmail.com
 * @date 2016年5月17日 下午2:33:40 
 * @version V1.0   
 */
public class Log4Jtest {

	private static Logger logger = Logger.getLogger(Log4Jtest.class);
	
	public static void main(String[] args) {
		
		
		logger.debug("debug");
		logger.info("info");
		logger.error("error");
	}
}
