package support;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version Date: 2010-9-13
 * @since
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ 
		"classpath*:spring/xinhu-bean.xml" //
})
@TransactionConfiguration(transactionManager = "transactionManager")
@Transactional
public class PlatformTestSupport {
}