package in.jewelchat.jewelchat.screens;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import in.jewelchat.jewelchat.BaseNetworkActivity;
import in.jewelchat.jewelchat.JewelChatApp;
import in.jewelchat.jewelchat.R;
import in.jewelchat.jewelchat.adapter.FactoryAdapter;
import in.jewelchat.jewelchat.models.BasicJewelCountChangedEvent;
import in.jewelchat.jewelchat.models.Factory;

/**
 * Created by mayukhchakraborty on 31/03/16.
 */
public class ActivityFactory extends BaseNetworkActivity {

	ListView factoryListView;
	List<Factory> factoryList;

	FactoryAdapter factoryAdapter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_factory);

		rootLayout = (CoordinatorLayout) findViewById(R.id.main_content);

		setUpAppbar();

		TextView toolbar_title = (TextView)rootLayout.findViewById(R.id.toolbarTitle);
		toolbar_title.setText("Factory");

		ImageView toolbar_image = (ImageView)rootLayout.findViewById(R.id.toolbarImage);
		toolbar_image.setImageResource(R.drawable.ic_factory);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);

		factoryListView = (ListView)rootLayout.findViewById(R.id.factory_list);
		factoryList = new ArrayList<Factory>();
		factoryList.add(new Factory(R.drawable.ic_ec));
		factoryList.add(new Factory(R.drawable.ic_fc));
		factoryList.add(new Factory(R.drawable.ic_gc));
		factoryList.add(new Factory(R.drawable.ic_hc));
		factoryList.add(new Factory(R.drawable.ic_ic));
		factoryList.add(new Factory(R.drawable.ic_jc));
		factoryList.add(new Factory(R.drawable.ic_kc));
		factoryList.add(new Factory(R.drawable.ic_lc));
		factoryList.add(new Factory(R.drawable.ic_mc));
		factoryList.add(new Factory(R.drawable.ic_nc));
		factoryList.add(new Factory(R.drawable.ic_oc));
		factoryList.add(new Factory(R.drawable.ic_pc));
		factoryList.add(new Factory(R.drawable.ic_qc));
		factoryList.add(new Factory(R.drawable.ic_rc));
		factoryList.add(new Factory(R.drawable.ic_sc));
		factoryList.add(new Factory(R.drawable.ic_tc));
		factoryList.add(new Factory(R.drawable.ic_uc));
		factoryList.add(new Factory(R.drawable.ic_vc));
		factoryList.add(new Factory(R.drawable.ic_wc));
		factoryList.add(new Factory(R.drawable.ic_xc));




		factoryAdapter = new FactoryAdapter(this, factoryList);

		factoryListView.setAdapter(factoryAdapter);

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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				// this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
				// if this doesn't work as desired, another possibility is to call `finish()` here.
				onBackPressed();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

}

