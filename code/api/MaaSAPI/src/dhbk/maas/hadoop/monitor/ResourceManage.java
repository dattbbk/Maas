package dhbk.maas.hadoop.monitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dhbk.maas.hadoop.monitor.obj.ReMngClusterApp;
import dhbk.maas.httpconnect.HttpConnect;

public class ResourceManage {

	private static final String HTTP = "http://";
	private static final String REMNG_CLUSTER_APPS_PATH = "/ws/v1/cluster/apps" ;
	
	public static final String REMNG_CLUSTER_APP_FINISHEDTIME = "finishedTime";
	public static final String REMNG_CLUSTER_APP_AMCONTAINERLOGS = "amContainerLogs";
	public static final String REMNG_CLUSTER_APP_TRACKINGUI = "trackingUI";
	public static final String REMNG_CLUSTER_APP_STATE = "state";
	public static final String REMNG_CLUSTER_APP_USER = "user";
	public static final String REMNG_CLUSTER_APP_ID = "id";
	public static final String REMNG_CLUSTER_APP_CLUSTERID = "clusterId";
	public static final String REMNG_CLUSTER_APP_FINALSTATUS = "finalStatus";
	public static final String REMNG_CLUSTER_APP_AMHOSTHTTPADDRESS = "amHostHttpAddress";
	public static final String REMNG_CLUSTER_APP_PROGRESS = "progress";
	public static final String REMNG_CLUSTER_APP_NAME = "name";
	public static final String REMNG_CLUSTER_APP_STARTEDTIME = "startedTime";
	public static final String REMNG_CLUSTER_APP_ELAPSEDTIME = "elapsedTime";
	public static final String REMNG_CLUSTER_APP_DIAGNOSTICS = "diagnostics";
	public static final String REMNG_CLUSTER_APP_TRACKINGURL = "trackingUrl";
	public static final String REMNG_CLUSTER_APP_QUEUE = "queue";
	public static final String REMNG_CLUSTER_APP_ALLOCATEDMB = "allocatedMB";
	public static final String REMNG_CLUSTER_APP_ALLOCATEDVCORES = "allocatedVCores";
	public static final String REMNG_CLUSTER_APP_RUNNINGCONTAINERS = "runningContainers";
	
	private static final String DEFAULT_PORT = "8088";
	
	private String url;
	private String address;
	private String port = DEFAULT_PORT;
	
	private HttpConnect httpConnect;
	
	public ResourceManage (String address) {
		this.address = address;
		httpConnect = new HttpConnect() ;
	}
	
	public ResourceManage (String address, String port) {
		this.address = address;
		this.port = port;
		httpConnect = new HttpConnect() ;
	}
	
	/**
	 * get list cluster application
	 * @param nTop
	 * @return array list object ReMngClusterApp
	 * @throws IOException
	 * @throws JSONException
	 */
	public ArrayList<ReMngClusterApp> getClusterApps (int nTop) throws IOException, JSONException {
		ArrayList<ReMngClusterApp> clusterApps = new ArrayList<>() ;
		
		this.url = HTTP + this.address + ":" + this.port + REMNG_CLUSTER_APPS_PATH ;
		
		HttpResponse response = httpConnect.sendRequestGet(this.url, null) ;
		
		if(response.getStatusLine().getStatusCode() == 200) {
			
			StringBuffer buff = readerResponse(response) ;
			JSONArray jsaClusterApps = new JSONArray(buff.toString().substring(15, buff.length() - 2));
			
//			int n = nTop < jsaClusterApps.length() ? nTop : jsaClusterApps.length() ;
			for(int i = 0 ; i < jsaClusterApps.length(); i++) {
				JSONObject jsoClusterApp = jsaClusterApps.getJSONObject(i) ;
				
				clusterApps.add(new ReMngClusterApp(jsoClusterApp.getString(REMNG_CLUSTER_APP_FINISHEDTIME), 
						jsoClusterApp.getString(REMNG_CLUSTER_APP_AMCONTAINERLOGS), jsoClusterApp.getString(REMNG_CLUSTER_APP_TRACKINGUI), 
						jsoClusterApp.getString(REMNG_CLUSTER_APP_STATE), jsoClusterApp.getString(REMNG_CLUSTER_APP_USER), 
						jsoClusterApp.getString(REMNG_CLUSTER_APP_ID), jsoClusterApp.getString(REMNG_CLUSTER_APP_CLUSTERID), 
						jsoClusterApp.getString(REMNG_CLUSTER_APP_FINALSTATUS), jsoClusterApp.getString(REMNG_CLUSTER_APP_AMHOSTHTTPADDRESS), 
						jsoClusterApp.getString(REMNG_CLUSTER_APP_PROGRESS), jsoClusterApp.getString(REMNG_CLUSTER_APP_NAME), 
						jsoClusterApp.getString(REMNG_CLUSTER_APP_STARTEDTIME), jsoClusterApp.getString(REMNG_CLUSTER_APP_ELAPSEDTIME), 
						jsoClusterApp.getString(REMNG_CLUSTER_APP_DIAGNOSTICS),  jsoClusterApp.getString(REMNG_CLUSTER_APP_TRACKINGURL), 
						jsoClusterApp.getString(REMNG_CLUSTER_APP_QUEUE), jsoClusterApp.getString(REMNG_CLUSTER_APP_ALLOCATEDMB), 
						jsoClusterApp.getString(REMNG_CLUSTER_APP_ALLOCATEDVCORES), jsoClusterApp.getString(REMNG_CLUSTER_APP_RUNNINGCONTAINERS)));
				
			}
			
			if(clusterApps.size() > 0) {
				Collections.sort(clusterApps, new Comparator<ReMngClusterApp>() {

					@Override
					public int compare(ReMngClusterApp o1, ReMngClusterApp o2) {
						// TODO Auto-generated method stub
						return o1.id.compareToIgnoreCase(o2.id);
					}
					
				});
			}
		}
		
		return clusterApps ;
	}
	
	/**
	 * get information cluster application
	 * @param appId
	 * @return object ReMngClusterApp if execute success
	 * 		   null if execute failure
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws JSONException
	 */
	public ReMngClusterApp getClusterApp (String appId) throws ClientProtocolException, IOException, JSONException {
		ReMngClusterApp clusterApp = null;
		
		this.url = HTTP + this.address + ":" + this.port + REMNG_CLUSTER_APPS_PATH + appId;
		
		HttpResponse response = httpConnect.sendRequestGet(this.url, null) ;
		
		if(response.getStatusLine().getStatusCode() == 200) {
			
			StringBuffer buff = readerResponse(response) ;
			
			JSONObject jsoClusterApp = new JSONObject(buff.toString().substring(7, buff.length() - 1));
			
			clusterApp = new ReMngClusterApp(jsoClusterApp.getString(REMNG_CLUSTER_APP_FINISHEDTIME), 
					jsoClusterApp.getString(REMNG_CLUSTER_APP_AMCONTAINERLOGS), jsoClusterApp.getString(REMNG_CLUSTER_APP_TRACKINGUI), 
					jsoClusterApp.getString(REMNG_CLUSTER_APP_STATE), jsoClusterApp.getString(REMNG_CLUSTER_APP_USER), 
					jsoClusterApp.getString(REMNG_CLUSTER_APP_ID), jsoClusterApp.getString(REMNG_CLUSTER_APP_CLUSTERID), 
					jsoClusterApp.getString(REMNG_CLUSTER_APP_FINALSTATUS), jsoClusterApp.getString(REMNG_CLUSTER_APP_AMHOSTHTTPADDRESS), 
					jsoClusterApp.getString(REMNG_CLUSTER_APP_PROGRESS), jsoClusterApp.getString(REMNG_CLUSTER_APP_NAME), 
					jsoClusterApp.getString(REMNG_CLUSTER_APP_STARTEDTIME), jsoClusterApp.getString(REMNG_CLUSTER_APP_ELAPSEDTIME), 
					jsoClusterApp.getString(REMNG_CLUSTER_APP_DIAGNOSTICS), jsoClusterApp.getString(REMNG_CLUSTER_APP_TRACKINGURL), 
					jsoClusterApp.getString(REMNG_CLUSTER_APP_QUEUE), "", "", "");
		}
		
		return clusterApp ;
	}
	
	
	private StringBuffer readerResponse (HttpResponse response) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuffer buff = new StringBuffer();
		String line = "";
		while ((line = reader.readLine()) != null) {
			buff.append(line) ;
		}
		
		return buff;
	}
}
