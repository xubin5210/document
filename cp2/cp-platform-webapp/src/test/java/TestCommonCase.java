import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class TestCommonCase {

	public static void main(String args[]) {
		Set<String> dnold = new HashSet<String>(); //原先部门名称列表
		dnold.add("你好");
		dnold.add("你好1");
		dnold.add("你好2");
		
		Set<String> dnnew = new HashSet<String>(); //原先部门名称列表
		
		dnnew.add("你好2");
		dnnew.add("你好1");
		
		dnnew.add("你好");
		
		
		
		boolean isSame = org.apache.commons.collections.CollectionUtils.isEqualCollection(dnold, dnnew);
		System.out.println(isSame);  
		
		
		String aa = "abc,";
		String bb = aa.substring(0,aa.length()-1);
		System.out.println(bb);
		
		
		
		//------------------
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("abc", "abc");
		System.out.println(map.size()+":"+map.get("abc"));
		map.put("abc", "abcd");
		System.out.println(map.size()+":"+map.get("abc"));
	}

}
