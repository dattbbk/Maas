package dhbk.maas.ui.tab.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

public class SubmitJobService extends Service{

	public static final String ACTION_SUBMIT_NAIVEBAYES = "naive bayes" ;
	public static final String ACTION_SUBMIT_HIDDENMARKOVMODELS = "hidden markov modeks" ;
	public static final String ACTION_SUBMIT_LOGISTICREGRESSION = "logistic regression" ;
	public static final String ACTION_SUBMIT_RANDOMFOREST = "random forest" ;
	public static final String ACTION_SUBMIT_KMEANS = "k means" ;
	public static final String ACTION_SUBMIT_CANOPY = "canopy" ;
	public static final String ACTION_SUBMIT_FUZZYKMEANS = "fuzzy k means" ;
	public static final String ACTION_SUBMIT_RECOMMENDER = "recommender" ;
	public static final String ACTION_NONE = "none" ;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		String action = intent.getAction() ;
		Bundle b = intent.getExtras() ;
		String pathData = null;
		try{
			pathData = b.getString("pathdata");
		} catch (Exception e) {}
		
		if (pathData != null) {
			if(ACTION_SUBMIT_RECOMMENDER.equals(action)) {
				new AsynSubmitJob.SubmitJobRecommender().execute(pathData);
			}
		} 
		
		intent.setAction(ACTION_NONE) ;
		
		return START_CONTINUATION_MASK;
	}
	
}
