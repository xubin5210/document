package mapper;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version Date: 2010-9-13
 * @since
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
		"classpath*:spring/core-bean.xml", //
		"classpath*:spring/xinhu-bean.xml", 
		"classpath*:spring/cache-bean.xml", 
		"classpath*:spring/base-biz-bean.xml",
		"classpath*:spring/web-bean.xml" })
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
//@Transactional
public class XinhuDaoTestCase {
}