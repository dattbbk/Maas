package dhbk.maas.ui.base;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter{

	private ArrayList<Fragment> arr_Fragment;
	
	public PageAdapter(FragmentManager fm, ArrayList<Fragment> arr_Fragments) {
		super(fm);
		this.arr_Fragment = arr_Fragments ;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return arr_Fragment.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arr_Fragment.size();
	}

}
