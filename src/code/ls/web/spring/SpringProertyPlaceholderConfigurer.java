package code.ls.web.spring;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * @Title: SpringProertyPlaceholderConfigurer.java
 * @Package com.ls.common.spring
 * @Description:
 * @author linsheng
 * @email hi.linsheng@foxmail.com
 * @date 2016年5月13日 下午3:29:40
 * @version V1.0
 */
public class SpringProertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	private static final String ENCRYP_FIELDS = "jdbc.encrypt.fields";

	/*
	 * 重新解读属性文件数据，将加密的数据解密
	 */
	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {

		props.get(ENCRYP_FIELDS);

		/**
		 * TODO
		 */
		super.processProperties(beanFactoryToProcess, props);
	}

}
