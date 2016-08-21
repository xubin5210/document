package api;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class BaseHttpClientTestCase {
	protected static final String SERVICE_SCHEME = "http";
	//protected static final String SERVICE_HOST = "127.0.0.1";
	//protected static final int SERVICE_PORT = 8080;	
	protected static final String SERVICE_HOST = "http://127.0.0.1/";
	protected static final int SERVICE_PORT = 8081;	
	protected static final String SERVICE_PATH = "/api";

	//protected static final String SERVICE_URL = SERVICE_SCHEME + "://"+SERVICE_HOST+":" + SERVICE_PORT + SERVICE_PATH + "/";
	
	protected static final String CHARSET = "UTF-8";

	private static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 100;
	private static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 5;
	private static final int DEFAULT_READ_TIMEOUT_MILLISECONDS = (60 * 1000);

	protected HttpClient httpClient;

	public BaseHttpClientTestCase() {
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
		SocketConfig.Builder socketConfigBuilder = SocketConfig.custom();
		socketConfigBuilder.setSoTimeout(DEFAULT_READ_TIMEOUT_MILLISECONDS);

		SocketConfig socketConfig = socketConfigBuilder.build();
		HttpHost host = new HttpHost(SERVICE_HOST, SERVICE_PORT);
		connManager.setMaxTotal(DEFAULT_MAX_TOTAL_CONNECTIONS);
		connManager.setDefaultMaxPerRoute(DEFAULT_MAX_CONNECTIONS_PER_ROUTE);
		connManager.setSocketConfig(host, socketConfig);

		httpClient = HttpClients.createMinimal(connManager);
	}
}
