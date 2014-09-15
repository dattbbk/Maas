package dhbk.maas.ui.tab.service;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import dhbk.maas.api.mahout.execute.Recommendations;
import dhbk.maas.ui.utils.Conf;
import android.os.AsyncTask;

public class AsynSubmitJob {

	static String res = "";
	
	public static class SubmitJobRecommender extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			Recommendations rcd = new Recommendations(Conf.address);
			try {
				res = rcd.submitRecommender(arg0[0]);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			System.out.println(res);
		}
		
	}
}
