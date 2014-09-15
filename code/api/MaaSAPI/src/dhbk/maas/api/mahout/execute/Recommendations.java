package dhbk.maas.api.mahout.execute;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

import dhbk.maas.api.httpconnect.HttpConnect;

public class Recommendations {

	private static final String HTTP = "http://";
	public static final String RECOMMENDER_PATH = "MaaSServlet/recommender" ;
	public static final String DEFAULT_PORT = "11000" ;
	
	private String url ;
	private String address ;
	private String port = DEFAULT_PORT ;
	
	HttpConnect httpConnect ;
	
	public Recommendations (String address) {
		this.address = address ;
		httpConnect = new HttpConnect() ;
	}
	
	public Recommendations (String address, String port) {
		this.address = address ;
		this.port = port;
		httpConnect = new HttpConnect() ;
	}
	
	public String submitRecommender (String pathDataUse) throws ClientProtocolException, IOException {
		this.url = HTTP + this.address + ":" + this.port + RECOMMENDER_PATH ;
		
		ArrayList<String[]> values = new ArrayList<>();
		values.add(new String[] {"path", pathDataUse}) ;
		
		HttpResponse response = httpConnect.sendRequestPost(this.url, null, values);
		
		if(response.getStatusLine().getStatusCode() == 200) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer buff = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buff.append(line) ;
			}
			return "SUCCESS".equals(buff.toString())?"SUCCESS":"FAILURE";
		} else
			return "FAILURE";
		
	}
	
}
