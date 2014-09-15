package dhbk.maas.api.hadoop.monitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dhbk.maas.api.hadoop.monitor.obj.ListADirectory;
import dhbk.maas.api.httpconnect.HttpConnect;

public class HDFSManage {

	private static final String HTTP = "http://";
	private static final String HDFS_PATH = "/webhdfs/v1/" ;
	private static final String OP_LISTSTATUS = "?op=LISTSTATUS" ;
	
	private static final String LISTADIRECTORY_ACCESSTIME = "accessTime" ;
	private static final String LISTADIRECTORY_BLOCKSIZE = "blockSize" ;
	private static final String LISTADIRECTORY_GROUP = "group" ;
	private static final String LISTADIRECTORY_LENGTH = "length" ;
	private static final String LISTADIRECTORY_MODIFICATIONTIME = "modificationTime" ;
	private static final String LISTADIRECTORY_OWNER = "owner" ;
	private static final String LISTADIRECTORY_PATHSUFFIX = "pathSuffix" ;
	private static final String LISTADIRECTORY_PERMISSION = "permission" ;
	private static final String LISTADIRECTORY_REPLICATION = "replication" ;
	private static final String LISTADIRECTORY_TYPE = "type" ;
	
	public static final String DEFAULT_PORT = "50070" ;
	
	private String url ;
	private String address ;
	private String port = DEFAULT_PORT ;
	
	private HttpConnect httpConnect;
	
	public HDFSManage (String address)  {
		this.address = address ;
		httpConnect = new HttpConnect() ;
	}
	
	public HDFSManage (String address, String port) {
		this.address = address ;
		this.port = port;
		httpConnect = new HttpConnect() ;
	}
	
	/**
	 * get list directory and file in HDFS with path
	 * @param path
	 * @return array list object ListADirectory   
	 * @throws JSONException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public ArrayList<ListADirectory> getListADirectory (String path) throws JSONException, ClientProtocolException, IOException {
		ArrayList<ListADirectory> listADirectory = new ArrayList<>() ;
		
		this.url = HTTP + this.address + ":" + this.port + HDFS_PATH +  path + OP_LISTSTATUS;
		
		HttpResponse response = httpConnect.sendRequestGet(this.url, null) ;
		
		if(response.getStatusLine().getStatusCode() == 200) {
		
			StringBuffer buff = readerResponse(response) ;
			
			JSONArray jsaHis = new JSONArray(buff.toString().substring(30, buff.length() - 2));
			for(int i = 0; i < jsaHis.length(); i++) {
				JSONObject jso = jsaHis.getJSONObject(i);
				listADirectory.add(new ListADirectory(jso.getString(LISTADIRECTORY_ACCESSTIME), jso.getString(LISTADIRECTORY_BLOCKSIZE), 
						jso.getString(LISTADIRECTORY_GROUP), jso.getString(LISTADIRECTORY_LENGTH), jso.getString(LISTADIRECTORY_MODIFICATIONTIME), 
						jso.getString(LISTADIRECTORY_OWNER), jso.getString(LISTADIRECTORY_PATHSUFFIX), jso.getString(LISTADIRECTORY_PERMISSION), 
						jso.getString(LISTADIRECTORY_REPLICATION), jso.getString(LISTADIRECTORY_TYPE))) ;
			}
		}
		
		return listADirectory ;
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
