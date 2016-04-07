package in.jewelchat.jewelchat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.CursorLoader;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import in.jewelchat.jewelchat.adapter.ChatListAdapter;
import in.jewelchat.jewelchat.adapter.GameGridAdapter;
import in.jewelchat.jewelchat.adapter.TasksAdapter;
import in.jewelchat.jewelchat.database.JewelChatDataProvider;
import in.jewelchat.jewelchat.database.TasksContract;
import in.jewelchat.jewelchat.models.BasicJewelCountChangedEvent;
import in.jewelchat.jewelchat.models.GameGridCell;
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

	private CursorLoader chatListCursorLoader;
	private CursorLoader taskListCursorLoader;

	private ChatListAdapter chatListAdapter;
	private TasksAdapter tasksAdapter;

	private List<GameGridCell> gameGridCellList;
	private GameGridAdapter gameGridAdapter;

	private View gameView;
	private View tasksView;
	private View chatListView;
	private View walletView;



	public View getFragmentGameView(LayoutInflater inflater, ViewGroup container,
	                                Bundle savedInstanceState){

		if(gameView==null)
			gameView = inflater.inflate(R.layout.fragment_game, container, false);

		return gameView;

	}

	public View getFragmentTasksView(LayoutInflater inflater, ViewGroup container,
	                                Bundle savedInstanceState){

		if(tasksView==null)
			tasksView = inflater.inflate(R.layout.fragment_tasks, container, false);

		return tasksView;

	}

	public View getFragmentWalletView(LayoutInflater inflater, ViewGroup container,
	                                 Bundle savedInstanceState){

		if(walletView==null)
			walletView = inflater.inflate(R.layout.fragment_wallet_header, container, false);

		return walletView;

	}

	public View getFragmentChatListView(LayoutInflater inflater, ViewGroup container,
	                                  Bundle savedInstanceState){

		if(chatListView==null)
			chatListView = inflater.inflate(R.layout.fragment_chat, container, false);

		return chatListView;

	}



	private void initializeGameGridCellList(){
		gameGridCellList = new ArrayList<GameGridCell>();

		String board = JewelChatApp.getSharedPref().getString(JewelChatPrefs.BOARD,"");
		String board_state = JewelChatApp.getSharedPref().getString(JewelChatPrefs.BOARD_STATE,"");


		for(int i=0; i<48; i++){

			GameGridCell t = new GameGridCell();
			t.type = board.charAt(i);
			if(board_state.charAt(i)== '0')
				t.state = false;
			else if(board_state.charAt(i)== '1')
				t.state = true;

			gameGridCellList.add(t);


		}
	}

	public GameGridAdapter getGameGridAdapter(){

		if(gameGridAdapter==null){

			if(gameGridCellList==null || gameGridCellList.size()==0)
				initializeGameGridCellList();

			gameGridAdapter = new GameGridAdapter(getApplicationContext(), gameGridCellList);

		}

		return gameGridAdapter;

	}

	public ChatListAdapter getChatListAdapter(){

		if(chatListAdapter==null){
			chatListAdapter = new ChatListAdapter(getApplicationContext());
		}

		return chatListAdapter;
	}

	public TasksAdapter getTasksAdapter(){

		if(tasksAdapter==null){
			tasksAdapter = new TasksAdapter(getApplicationContext());
		}

		return tasksAdapter;
	}


	public CursorLoader getChatListCursorLoader(){

		if(chatListCursorLoader==null){
			Log.i("OMG","OMG");
			Uri uri = Uri.parse(JewelChatDataProvider.SCHEME+"://" + JewelChatDataProvider.AUTHORITY + "/"+ "chatlist");
			chatListCursorLoader = new CursorLoader(getApplicationContext(),
					uri, null, null, null, null);
		}

		return chatListCursorLoader;

	}


	public CursorLoader getTaskListCursorLoader(){

		if(taskListCursorLoader==null){
			Uri uri = Uri.parse(JewelChatDataProvider.SCHEME+"://" + JewelChatDataProvider.AUTHORITY + "/"+ TasksContract.SQLITE_TABLE_NAME);
			taskListCursorLoader = new CursorLoader(getApplicationContext(),
					uri, null, null, null, null);
		}

		return taskListCursorLoader;

	}



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jewel_chat);

		rootLayout = (CoordinatorLayout) findViewById(R.id.main_content);

		setUpAppbar();

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
		mSectionsPagerAdapter.addFrag( "Chat");
		mSectionsPagerAdapter.addFrag( "Game");
		mSectionsPagerAdapter.addFrag( "Wallet");
		mSectionsPagerAdapter.addFrag( "Tasks");

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.container);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setOffscreenPageLimit(1);


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

		chatListCursorLoader=null;
		taskListCursorLoader=null;

		chatListAdapter=null;
		tasksAdapter=null;

		gameGridCellList=null;
		gameGridAdapter=null;

		gameView=null;
		tasksView=null;
		chatListView=null;
		walletView=null;

		System.gc();

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
	public class SectionsPagerAdapter extends FragmentStatePagerAdapter {


		private final List<String> mFragmentTitleList = new ArrayList<>();

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			if(position==0)
				return new FragmentChatList();
			else if(position==1)
				return new FragmentGame();
			else if(position==2)
				return new FragmentWallet();
			else if(position==3)
				return new FragmentTasks();

			return null;

		}

		@Override
		public int getCount() {

			return 4;
		}

		public void addFrag( String title) {

			mFragmentTitleList.add(title);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return mFragmentTitleList.get(position);
		}

	}

}
