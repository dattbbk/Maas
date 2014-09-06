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

import dhbk.maas.hadoop.monitor.obj.HistoryInfo;
import dhbk.maas.hadoop.monitor.obj.HistoryJob;
import dhbk.maas.hadoop.monitor.obj.HistoryJobTask;
import dhbk.maas.httpconnect.HttpConnect;

public class HistoryServer {

	private static final String HTTP = "http://";
	private static final String HISTORY_JOB_PATH = "/ws/v1/history/mapreduce/jobs/";
	private static final String HISTORY_INFO_PATH = "/ws/v1/history/info";
	private static final String HISTORY_JOB_TASK_PATH = "/tasks";
	
	
	public static final String HIS_INFO_STARTEDON = "startedOn";
	public static final String HIS_INFO_HADOOPVERSIONBUILTON = "hadoopVersionBuiltOn";
	public static final String HIS_INFO_HADOOPBUILTVERSION = "hadoopBuildVersion";
	public static final String HIS_INFO_HADOOPVERSION = "hadoopVersion";
	
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
	
	public static final String HIS_JOB_TASK_PROGRESS = "progress" ;
	public static final String HIS_JOB_TASK_ELAPSEDTIME = "elapsedTime" ;
	public static final String HIS_JOB_TASK_STATE = "state" ;
	public static final String HIS_JOB_TASK_STARTTIME = "startTime" ;
	public static final String HIS_JOB_TASK_ID = "id" ;
	public static final String HIS_JOB_TASK_TYPE = "type" ;
	public static final String HIS_JOB_TASK_SUCCESSFULATTEMPT = "successfulAttempt" ;
	public static final String HIS_JOB_TASK_FINISHTIME = "finishTime" ;
	
	public static final String DEFAULT_PORT = "19888" ;
	
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
	
	/**
	 * get information history hadoop
	 * @return historyInfo if execute success
	 * 		   null if failure
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws JSONException
	 */
	public HistoryInfo getHistoryInfo () throws ClientProtocolException, IOException, JSONException {
		HistoryInfo hisInfo = null;
		
		this.url = HTTP + this.address + ":" + this.port + HISTORY_INFO_PATH ;
		
		HttpResponse response = httpConnect.sendRequestGet(this.url, null) ;
		
		if(response.getStatusLine().getStatusCode() == 200) {
			
			StringBuffer buff = readerResponse(response) ;
			
			JSONObject jso = new JSONObject(buff.toString().substring(15, buff.length() - 1)) ;
			hisInfo = new HistoryInfo(jso.getString(HIS_INFO_STARTEDON), jso.getString(HIS_INFO_HADOOPVERSIONBUILTON), 
					jso.getString(HIS_INFO_HADOOPBUILTVERSION), jso.getString(HIS_INFO_HADOOPVERSION)) ;
		}
		
		return hisInfo ;
	}
	
	/**
	 * get history all jobs 
	 * @return array list historyJobs
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws JSONException
	 */
	public ArrayList<HistoryJob> getHistoryJobs () throws ClientProtocolException, IOException, JSONException {
		ArrayList<HistoryJob> historyJobs = new ArrayList<>() ;
		
		this.url = HTTP + this.address + ":" + this.port + HISTORY_JOB_PATH ;
				
		HttpResponse response = httpConnect.sendRequestGet(this.url, null) ;
		
		if(response.getStatusLine().getStatusCode() == 200) {
		
			StringBuffer buff = readerResponse(response) ;
			
			JSONArray jsaHis = new JSONArray(buff.toString().substring(15, buff.length() - 2));
			for(int i = 0; i < jsaHis.length(); i++) {
				JSONObject jso = jsaHis.getJSONObject(i);
				historyJobs.add(new HistoryJob(jso.getString(HIS_SUBMITTIME), jso.getString(HIS_STATE), jso.getString(HIS_USER), 
												jso.getString(HIS_REDUCESTOTAL), jso.getString(HIS_MAPSCOMPLETED), 
												jso.getString(HIS_STARTTIME), jso.getString(HIS_ID), jso.getString(HIS_NAME), 
												jso.getString(HIS_REDUCESCOMPLETED), jso.getString(HIS_MAPSTOTAL), 
												jso.getString(HIS_QUEUE), jso.getString(HIS_FINISHTIME))) ;
			}
		}
		
		return historyJobs;
	}
	
	/**
	 * get list tasks within a job
	 * @param idJob
	 * @return array list HistoryJobTasks
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws JSONException
	 */
	public ArrayList<HistoryJobTask> getHistoryJobTasks (String idJob) throws ClientProtocolException, IOException, JSONException {
		ArrayList<HistoryJobTask> historyJobTasks = new ArrayList<>() ;
		
		this.url = HTTP + this.address + ":" + this.port + HISTORY_JOB_PATH + idJob + HISTORY_JOB_TASK_PATH;
		
		HttpResponse response = httpConnect.sendRequestGet(this.url, null) ;
		
		if(response.getStatusLine().getStatusCode() == 200) {
		
			StringBuffer buff = readerResponse(response) ;
			JSONArray jsaTasks = new JSONArray(buff.toString().substring(17, buff.length() - 2)) ;
			for (int i = 0; i < jsaTasks.length() ; i++) {
				JSONObject jso = jsaTasks.getJSONObject(i);
				historyJobTasks.add(new HistoryJobTask(jso.getString(HIS_JOB_TASK_PROGRESS), jso.getString(HIS_JOB_TASK_ELAPSEDTIME),
						jso.getString(HIS_JOB_TASK_STATE), jso.getString(HIS_JOB_TASK_STARTTIME), jso.getString(HIS_JOB_TASK_ID), 
						jso.getString(HIS_JOB_TASK_TYPE), jso.getString(HIS_JOB_TASK_SUCCESSFULATTEMPT), jso.getString(HIS_JOB_TASK_FINISHTIME)));
			}
			
		}
		
		return historyJobTasks ;
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
