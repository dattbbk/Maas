package dhbk.maas.ui.base;

import android.content.Context;
import android.view.View;
import android.widget.TabHost.TabContentFactory;

public class TabFactory implements TabContentFactory{

	private final Context ct;
	
	public TabFactory(Context ct) {
		// TODO Auto-generated constructor stub
		this.ct = ct;
	}
	
	@Override
	public View createTabContent(String arg0) {
		// TODO Auto-generated method stub
		View view = new View(ct);
		view.setMinimumWidth(0);
		view.setMinimumHeight(0);
		return view;
	}

}
