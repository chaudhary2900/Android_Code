package home.example.com.rss;


import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import adapter.TabsPagerAdapter;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {
int count =0;
	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;

	// Tab titles
	private String[] tabs = { "Hinduism", "Spiritual", "Health", "Politics"};
 	public ReligionFragment _currentFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Initilization
		viewPager = (ViewPager) findViewById(R.id.pager);

		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Adding Tabs
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}


		/**
		 * on swiping the viewpager make respective tab selected
		 * */


		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);

			}


			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// on tab selected
		// show respected fragment view
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}



	@Override
	public void onBackPressed() {

		if(count==0)
		{

				if (Show_Tip_list.x==1) {
				ReligionFragment.allshow();
					}
			else	if (Show_Tip_list.x==2) {
				DevotionalFragment.allshow1();

			}
			else	if (Show_Tip_list.x==3) {
				HealthFragment.allshow2();

			}
			else	if (Show_Tip_list.x==4) {

				PoliticsFragment.Politics();
			}
			//Toast.makeText(MainActivity.this,"back presss",Toast.LENGTH_LONG).show();
		}

		else {
			super.onBackPressed();

		}
	 	count++;
         // finish();
		//

	}
}
