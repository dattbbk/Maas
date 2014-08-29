package dhbk.maas.hadoop.monitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dhbk.maas.hadoop.monitor.obj.HistoryJob;
import dhbk.maas.httpconnect.HttpConnect;

public class HistoryServer {

	private static final String HTTP = "http://";
	private static final String HISTORY_JOB_PATH = "/ws/v1/history/mapreduce/jobs";
	
	public static final String HIS_SUBMITTIME = "submitTime";
	public static final String HIS_STATE = "state";
	public static final String HIS_USER = "user" ;
	public static final String HIS_REDUCESTOTAL = "reducesTotal";
	public static final String HIS_MAPSCOMPLETED = "mapsCompleted";
	public static final String HIS_STARTTIME = "startTime";
	public static final String HIS_ID = "id";
	public static final String HIS_NAME = "name" ;
	public static final String HIS_REDUCESCOMPLETED = "reducesCompleted";
	public static final String HIS_MAPSTOTAL = "mapsTotal";
	public static final String HIS_QUEUE = "queue";
	public static final String HIS_FINISHTIME = "finishTime";
	
	public static final String DEFAULT_PORT = "50030" ;
	
	private String url ;
	private String address ;
	private String port = DEFAULT_PORT ;
	
	private HttpConnect httpConnect;
	
	public HistoryServer (String address)  {
		this.address = address ;
		httpConnect = new HttpConnect() ;
	}
	
	public HistoryServer (String address, String port) {
		this.address = address ;
		this.port = port;
		httpConnect = new HttpConnect() ;
	}
	
	public ArrayList<HistoryJob> getHistoryJobs () throws ClientProtocolException, IOException, JSONException {
		ArrayList<HistoryJob> historyJob = new ArrayList<>() ;
		
		this.url = HTTP + this.address + ":" + this.port + HISTORY_JOB_PATH ;
				
		HttpResponse response = httpConnect.sendRequestGet(this.url, null) ;
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuffer buff = new StringBuffer();
		String line = "";
		while ((line = reader.readLine()) != null) {
			buff.append(line) ;
		}
		
		JSONArray jsaHis = new JSONArray(buff.toString().substring(15, buff.length() - 2));
		for(int i = 0; i < jsaHis.length(); i++) {
			JSONObject jso = jsaHis.getJSONObject(i);
			historyJob.add(new HistoryJob(jso.getString(HIS_SUBMITTIME), jso.getString(HIS_STATE), jso.getString(HIS_USER), 
											jso.getString(HIS_REDUCESTOTAL), jso.getString(HIS_MAPSCOMPLETED), 
											jso.getString(HIS_STARTTIME), jso.getString(HIS_ID), jso.getString(HIS_NAME), 
											jso.getString(HIS_REDUCESCOMPLETED), jso.getString(HIS_MAPSTOTAL), 
											jso.getString(HIS_QUEUE), jso.getString(HIS_FINISHTIME))) ;
		}
		
		return historyJob;
	}
}
