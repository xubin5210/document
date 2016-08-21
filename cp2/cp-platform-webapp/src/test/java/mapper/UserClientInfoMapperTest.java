package mapper;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ancun.xinhu.biz.mappers.UserClientInfoMapper;
import com.ancun.xinhu.domain.model.UserClientInfo;

public class UserClientInfoMapperTest extends XinhuDaoTestCase{
	@Autowired
	private UserClientInfoMapper userClientInfoMapper;
	
	@Test
	public void TestUserClientInfoMapper(){
		UserClientInfo uci = new UserClientInfo();
		uci.setClientId("015338eb7d24bfd227feb8fe513ed27c");
		uci.setUserId(57);
		List<UserClientInfo> list = userClientInfoMapper.queryListFClient(uci);
		System.out.println(list.size());
		for(UserClientInfo u:list){
			System.out.println(u);
		}
	}
}
