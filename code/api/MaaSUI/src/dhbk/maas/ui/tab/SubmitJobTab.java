package dhbk.maas.ui.tab;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import dhbk.maas.api.hadoop.monitor.HDFSManage;
import dhbk.maas.api.hadoop.monitor.obj.ListADirectory;
import dhbk.maas.ui.R;
import dhbk.maas.ui.tab.service.SubmitJobService;
import dhbk.maas.ui.utils.Conf;

public class SubmitJobTab extends Fragment implements OnClickListener{

	private ArrayList<String[]> listAlgorithm = new ArrayList<String[]>();
	private ArrayList<String[]> listData = new ArrayList<String[]>() ;
	private ArrayAdapter<String[]> adapterAlgotithm ;
	private ArrayAdapter<String[]> adapterData;
	ListView lv_data ;
	TextView tv_algorithm, tv_data;
	
	private int pos_algorithm = -1, pos_data = -1;
	private AtomicBoolean isExe = new AtomicBoolean() ;
	
	private ArrayList<ListADirectory> listADirectory = new ArrayList<ListADirectory>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		isExe.set(false) ;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.tab_submit_job, container, false) ;
		ImageButton btn_refresh = (ImageButton) view.findViewById(R.id.tabsub_btn_refresh) ;
		btn_refresh.setOnClickListener(this) ;
		Button btn_submit = (Button) view.findViewById(R.id.tabsub_btn_submit) ;
		btn_submit.setOnClickListener(this) ;
		
		tv_algorithm = (TextView) view.findViewById(R.id.tabsub_tv_algorithm) ;
		tv_data = (TextView) view.findViewById(R.id.tabsub_tv_data) ;
		if(pos_algorithm >= 0)
			tv_algorithm.setText(listAlgorithm.get(pos_algorithm)[0]);
		if(pos_data >= 0)
			tv_data.setText(listData.get(pos_data)[0]) ;
		
		adapterAlgotithm = new ArrayAdapter<String[]>(getActivity(), android.R.layout.simple_list_item_2,
				android.R.id.text1, listAlgorithm){
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = super.getView(position, convertView, parent) ;
				TextView text1 = (TextView) view.findViewById(android.R.id.text1);
		        TextView text2 = (TextView) view.findViewById(android.R.id.text2);
		        text1.setTextSize(16) ;
		        text2.setTextSize(12) ;
		        text1.setText(listAlgorithm.get(position)[0]) ;
		        text2.setText(listAlgorithm.get(position)[1]) ;
				return view;
			}
		} ;
		
		adapterData = new ArrayAdapter<String[]>(getActivity(), android.R.layout.simple_list_item_2,
				android.R.id.text1, listData){
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = super.getView(position, convertView, parent) ;
				TextView text1 = (TextView) view.findViewById(android.R.id.text1);
		        TextView text2 = (TextView) view.findViewById(android.R.id.text2);
		        text1.setTextSize(16) ;
		        text2.setTextSize(12) ;
		        text1.setText(listData.get(position)[0]) ;
		        text2.setText(listData.get(position)[1]) ;
				return view;
			}
		} ;
		
		ListView lv_algorithm = (ListView) view.findViewById(R.id.tabsub_lv_algorithm) ;
		lv_data = (ListView) view.findViewById(R.id.tabsub_lv_data) ;
		lv_algorithm.setAdapter(adapterAlgotithm) ;
		lv_data.setAdapter(adapterData) ;
		
		lv_algorithm.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				pos_algorithm = arg2;
				tv_algorithm.setText(listAlgorithm.get(arg2)[0]) ;
			}
		});
		
		lv_data.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				pos_data = arg2;
				tv_data.setText(listData.get(arg2)[0]) ;
			}
		});
		
		if(listAlgorithm.size() == 0)
			loadListAlgorithm() ;
		else
			adapterAlgotithm.notifyDataSetChanged() ;
		
		if(listData.size() == 0) {
			if(!isExe.get()) {
				new exeListADirectory().execute("user/hadoop") ;
				isExe.set(true) ;
			}
		} else {
			changeListDataIfNeed() ;
		}
		
		return view ;
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tabsub_btn_refresh :
			if(!isExe.get()) {
				new exeListADirectory().execute("user/hadoop") ;
				isExe.set(true) ;
			}
			break;
		case R.id.tabsub_btn_submit :
			startSubmitJobService() ;
			break;
		default : break;
		}
	}
	
	private void loadListAlgorithm () {
		String[] algorithm_name = getResources().getStringArray(R.array.algorithm_name) ;
		String[] algorithm_group = getResources().getStringArray(R.array.algorithm_group) ;
		
		listAlgorithm.clear() ;
		for(int i = 0; i < algorithm_name.length; i++) {
			listAlgorithm.add(new String[] {algorithm_name[i], algorithm_group[i]}) ;
		}
		
		adapterAlgotithm.notifyDataSetChanged() ;
	}
	
	private void loadData (String path) {
		
		try {
			HDFSManage hdfs = new HDFSManage(Conf.address) ;
			listADirectory = hdfs.getListADirectory(path) ;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			isExe.set(false) ;
		}
	}
	
	private void changeListDataIfNeed () {
		if(listADirectory.size() > 0){
			if(listADirectory.size() != lv_data.getChildCount()) {
				listData.clear() ;
				for(ListADirectory lad : listADirectory) {
					listData.add(new String[] {lad.pathSuffix, lad.type}) ;
				}
				adapterData.notifyDataSetChanged() ;
			}
		}
	}
	
	private class exeListADirectory extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			loadData(params[0]) ;
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			changeListDataIfNeed() ;
		}
	}
	
	private void startSubmitJobService () {
		Intent it = new Intent(getActivity().getApplicationContext(), SubmitJobService.class);
		
		switch (pos_algorithm) {
		case 0 : it.setAction(SubmitJobService.ACTION_SUBMIT_NAIVEBAYES); break;
		case 1 : it.setAction(SubmitJobService.ACTION_SUBMIT_HIDDENMARKOVMODELS); break;
		case 2 : it.setAction(SubmitJobService.ACTION_SUBMIT_LOGISTICREGRESSION); break;
		case 3 : it.setAction(SubmitJobService.ACTION_SUBMIT_RANDOMFOREST); break;
		case 4 : it.setAction(SubmitJobService.ACTION_SUBMIT_KMEANS); break;
		case 5 : it.setAction(SubmitJobService.ACTION_SUBMIT_CANOPY); break;
		case 6 : it.setAction(SubmitJobService.ACTION_SUBMIT_FUZZYKMEANS); break;
		case 7 : it.setAction(SubmitJobService.ACTION_SUBMIT_RECOMMENDER); break;
		default:
			break;
		}
		
		if(pos_data < 0) {
			Bundle b = new Bundle();
			b.putString("pathdata", "RONG");
			it.putExtras(b) ;
		} else {
			Bundle b = new Bundle();
			b.putString("pathdata", listData.get(pos_data)[0]);
			it.putExtras(b) ;
		}
		
		if(pos_algorithm >= 0 && pos_data >= -1) {
			getActivity().startService(it) ;
			Toast.makeText(getActivity().getApplicationContext(), "Starting connect ...", Toast.LENGTH_SHORT).show() ;
		} else {
			Toast.makeText(getActivity().getApplicationContext(), "Choose Algorithm & Data", Toast.LENGTH_SHORT).show() ;
		}
	}
}
