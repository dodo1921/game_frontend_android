package in.jewelchat.jewelchat;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import in.jewelchat.jewelchat.models.BasicJewelCountChangedEvent;
import in.jewelchat.jewelchat.screens.FragmentChatList;
import in.jewelchat.jewelchat.screens.FragmentGame;
import in.jewelchat.jewelchat.screens.FragmentTasks;
import in.jewelchat.jewelchat.screens.FragmentWallet;

public class JewelChat extends AppCompatActivity {

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

		Toolbar toolbar = (Toolbar) findViewById(R.id.jewelchat_toolbar);
		setSupportActionBar(toolbar);
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

		tabLayout.setOnTabSelectedListener(
				new TabLayout.ViewPagerOnTabSelectedListener(mViewPager) {
					@Override
					public void onTabSelected(TabLayout.Tab tab) {
						super.onTabSelected(tab);

					}
				});

		/*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}
		});*/

	}


	@Override
	protected void onResume(){
		super.onResume();
		JewelChatApp.getBusInstance().register(this);
		JewelChatApp.getBusInstance().post(JewelChatApp.produceJewelChangeEvent());
	}

	@Override
	protected void onStop(){
		super.onStop();
		JewelChatApp.getBusInstance().unregister(this);
	}

	@Subscribe
	public void onBasicJewelCountChanged( BasicJewelCountChangedEvent event) {

		//change Toolbar items

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
			// Show 3 total pages.
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