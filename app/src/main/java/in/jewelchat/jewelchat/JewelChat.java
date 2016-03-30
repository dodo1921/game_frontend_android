package in.jewelchat.jewelchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import in.jewelchat.jewelchat.models.BasicJewelCountChangedEvent;
import in.jewelchat.jewelchat.screens.FragmentChatList;
import in.jewelchat.jewelchat.screens.FragmentGame;
import in.jewelchat.jewelchat.screens.FragmentTasks;
import in.jewelchat.jewelchat.screens.FragmentWallet;
import in.jewelchat.jewelchat.service.RegistrationIntentService;
import in.jewelchat.jewelchat.util.NetworkConnectivityStatus;

public class JewelChat extends BaseNetworkActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link FragmentPagerAdapter} derivative, which will keep every
	 * loaded fragment in memory. If this becomes too memory intensive, it
	 * may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	private SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private ViewPager mViewPager;




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jewel_chat);

		rootLayout = (CoordinatorLayout) findViewById(R.id.main_content);

		setUpAppbar();

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
		mSectionsPagerAdapter.addFrag(new FragmentChatList(), "Chat");
		mSectionsPagerAdapter.addFrag(new FragmentGame(), "Game");
		mSectionsPagerAdapter.addFrag(new FragmentTasks(), "Tasks");
		mSectionsPagerAdapter.addFrag(new FragmentWallet(), "Wallet");

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.container);
		mViewPager.setAdapter(mSectionsPagerAdapter);


		TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
		tabLayout.setupWithViewPager(mViewPager);
		tabLayout.getTabAt(JewelChatApp.getSharedPref().getInt(JewelChatPrefs.LAST_TAB, 0)).select();



		tabLayout.setOnTabSelectedListener(
				new TabLayout.ViewPagerOnTabSelectedListener(mViewPager) {
					@Override
					public void onTabSelected(TabLayout.Tab tab) {
						super.onTabSelected(tab);
						JewelChatApp.getSharedPref().edit().putInt(JewelChatPrefs.LAST_TAB, tab.getPosition()).commit();

					}
				});

		if(!JewelChatApp.getSharedPref().getBoolean(JewelChatPrefs.TOKEN_SENT,false)){
			Intent service = new Intent(getApplicationContext(), RegistrationIntentService.class);
			startService(service);
		}

		/*
		if(!JewelChatApp.getSharedPref().getBoolean(JewelChatPrefs.GROUPS_DOWNLOADED,false)){
			Intent service2 = new Intent(getApplicationContext(), FirstTimeGroupListDownload.class);
			startService(service2);
		}

		Intent service3 = new Intent(getApplicationContext(), PhoneBookContactUpdateService.class);
		startService(service3);

		*/

		if(NetworkConnectivityStatus.getConnectivityStatus() == NetworkConnectivityStatus.NOT_CONNECTED){
			this.showNoInternetDialog();
		}

	}


	@Override
	protected void onResume(){
		super.onResume();
		JewelChatApp.getBusInstance().register(this);
		JewelChatApp.getBusInstance().post(JewelChatApp.produceJewelChangeEvent());
	}

	@Override
	protected void onPause(){
		super.onPause();
		JewelChatApp.getBusInstance().unregister(this);
	}

	@Override
	public void onClick(View v) {

	}

	@Subscribe
	public void onBasicJewelCountChanged( BasicJewelCountChangedEvent event) {

		A.setText(event.A+"");
		B.setText(event.B+"");
		C.setText(event.C+"");
		D.setText(event.D+"");
		LEVEL.setText(event.LEVEL+"");
		XP.setMax(event.LEVEL_XP);XP.setProgress(event.XP);
		LEVEL_SCORE.setText(event.XP+"/"+event.LEVEL_XP);

	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		private final List<Fragment> mFragmentList = new ArrayList<>();
		private final List<String> mFragmentTitleList = new ArrayList<>();

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class below).
			return mFragmentList.get(position);
		}

		@Override
		public int getCount() {

			return mFragmentList.size();
		}

		public void addFrag(Fragment fragment, String title) {

			mFragmentList.add(fragment);
			mFragmentTitleList.add(title);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return mFragmentTitleList.get(position);
		}

	}

}
