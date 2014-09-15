package dhbk.maas.ui;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import dhbk.maas.ui.base.PageAdapter;
import dhbk.maas.ui.base.TabFactory;
import dhbk.maas.ui.tab.HistoryTab;
import dhbk.maas.ui.tab.ResourceTab;
import dhbk.maas.ui.tab.SubmitJobTab;
import dhbk.maas.ui.tab.service.SubmitJobService;
import dhbk.maas.ui.utils.Conf;

public class MainActivity extends FragmentActivity implements OnTabChangeListener, OnPageChangeListener{

	public static final String TAB_HISTORY = "History" ;
	public static final String TAB_RESOURCE = "Resource" ;
	public static final String TAB_SUBMIT = "Submit" ;
	
	private TabHost tabHost;
	private ViewPager viewPager;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		
		Conf.address = "192.168.50.178" ;
		
		setContentView(R.layout.activity_main);
		
		viewPager = (ViewPager) findViewById(R.id.viewpager) ;
		tabHost = (TabHost)  findViewById(android.R.id.tabhost) ;
		tabHost.setup() ;
		tabHost.setOnTabChangedListener(this) ;
		
		TabSpec tsHistory = (tabHost.newTabSpec(TAB_HISTORY)).setIndicator(TAB_HISTORY);
		TabSpec tsResource = (tabHost.newTabSpec(TAB_RESOURCE)).setIndicator(TAB_RESOURCE);
		TabSpec tsSubmit = (tabHost.newTabSpec(TAB_SUBMIT)).setIndicator(TAB_SUBMIT) ;
		
		tsHistory.setContent(new TabFactory(this));
		tsResource.setContent(new TabFactory(this));
		tsSubmit.setContent(new TabFactory(this));
		
		tabHost.addTab(tsResource) ;
		tabHost.addTab(tsHistory) ;
		tabHost.addTab(tsSubmit) ;
		
		PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(), getListFragment()) ;
		viewPager.setAdapter(pageAdapter) ;
		viewPager.setOnPageChangeListener(this) ;
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Intent i = new Intent(getApplicationContext(), SubmitJobService.class);
		i.setAction(SubmitJobService.ACTION_NONE) ;
		startService(i) ;
	}
	
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		int position = viewPager.getCurrentItem() ;
		tabHost.setCurrentTab(position) ;
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabChanged(String arg0) {
		// TODO Auto-generated method stub
		int position = tabHost.getCurrentTab() ;
		viewPager.setCurrentItem(position);
	}
	
	private ArrayList<Fragment> getListFragment () {
		ArrayList<Fragment> arr_fragment = new ArrayList<Fragment>() ;
		
		HistoryTab historyFragment = new HistoryTab() ;
		ResourceTab resouFragment = new ResourceTab();
		SubmitJobTab submitFragment = new SubmitJobTab() ;
		
		arr_fragment.add(resouFragment) ;
		arr_fragment.add(historyFragment) ;
		arr_fragment.add(submitFragment) ;
		return arr_fragment ;
	}

}
