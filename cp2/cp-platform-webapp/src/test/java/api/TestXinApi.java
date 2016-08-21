package api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class TestXinApi extends BaseHttpClientTestCase {

	@Test
	public void testFall() throws ClientProtocolException, IOException {
		Map<String, String> args = new HashMap<String, String>();
		//接口代码
		args.put("sys_request_code", "1005");
		
		//接口参数
		args.put("pwd", "613132333435");
		args.put("mobile", "13735803765");
		
		handApiHttp(args);
	}
	
	@Test
	public void test1108() throws ClientProtocolException, IOException {
		Map<String, String> args = new HashMap<String, String>();
		//接口代码
		args.put("sys_request_code", "1108");
		
		//接口参数
		args.put("cardId", "1167");
		args.put("type", "1");
		
		handApiHttp(args);
	}
	
	@Test
	public void test1005() throws ClientProtocolException, IOException {
		Map<String, String> args = new HashMap<String, String>();
		//接口代码
		args.put("sys_request_code", "1005");
		
		//接口参数
		args.put("pwd", "613132333435");
		args.put("mobile", "15698734588");
		
		handApiHttp(args);
	}
	
	private void handApiHttp(Map<String, String> args) throws ClientProtocolException, IOException{
		HttpPost httpPost = new HttpPost("http://127.0.0.1:8081/api/appApi?");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		
		for(String s : args.keySet()){
			nvps.add(new BasicNameValuePair(s, args.get(s)));
		}
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		HttpResponse response = httpClient.execute(httpPost);
		int statusCode = response.getStatusLine().getStatusCode();

		if (statusCode == HttpStatus.SC_OK) {

			System.out.println("服务器正常响应.....");

			HttpEntity resEntity = response.getEntity();

			System.out.println(EntityUtils.toString(resEntity));

			System.out.println(resEntity.getContent());

			EntityUtils.consume(resEntity);
		}
	}
}
