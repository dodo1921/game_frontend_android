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
import in.jewelchat.jewelchat.adapter.MarketAdapter;
import in.jewelchat.jewelchat.models.BasicJewelCountChangedEvent;
import in.jewelchat.jewelchat.models.MarketItem;

/**
 * Created by mayukhchakraborty on 06/03/16.
 */
public class ActivityMarket extends BaseNetworkActivity {

	ListView marketListView;
	List<MarketItem> marketList;

	MarketAdapter marketAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_market);

		rootLayout = (CoordinatorLayout) findViewById(R.id.main_content);

		setUpAppbar();

		TextView toolbar_title = (TextView)rootLayout.findViewById(R.id.toolbarTitle);
		toolbar_title.setText("Market");

		ImageView toolbar_image = (ImageView)rootLayout.findViewById(R.id.toolbarImage);
		toolbar_image.setImageResource(R.drawable.ic_market);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);

		marketListView = (ListView)rootLayout.findViewById(R.id.market_list);
		marketList = new ArrayList<MarketItem>();
		marketList.add(new MarketItem(R.drawable.ic_aac));
		marketList.add(new MarketItem(R.drawable.ic_bbc));
		marketList.add(new MarketItem(R.drawable.ic_ccc));
		marketList.add(new MarketItem(R.drawable.ic_ddc));
		marketList.add(new MarketItem(R.drawable.ic_eec));
		marketList.add(new MarketItem(R.drawable.ic_ffc));
		marketList.add(new MarketItem(R.drawable.ic_ggc));
		marketList.add(new MarketItem(R.drawable.ic_hhc));
		marketList.add(new MarketItem(R.drawable.ic_ec));
		marketList.add(new MarketItem(R.drawable.ic_fc));
		marketList.add(new MarketItem(R.drawable.ic_gc));
		marketList.add(new MarketItem(R.drawable.ic_hc));
		marketList.add(new MarketItem(R.drawable.ic_ic));
		marketList.add(new MarketItem(R.drawable.ic_jc));
		marketList.add(new MarketItem(R.drawable.ic_kc));
		marketList.add(new MarketItem(R.drawable.ic_lc));
		marketList.add(new MarketItem(R.drawable.ic_mc));
		marketList.add(new MarketItem(R.drawable.ic_nc));
		marketList.add(new MarketItem(R.drawable.ic_oc));
		marketList.add(new MarketItem(R.drawable.ic_pc));
		marketList.add(new MarketItem(R.drawable.ic_qc));
		marketList.add(new MarketItem(R.drawable.ic_rc));
		marketList.add(new MarketItem(R.drawable.ic_sc));
		marketList.add(new MarketItem(R.drawable.ic_tc));
		marketList.add(new MarketItem(R.drawable.ic_uc));
		marketList.add(new MarketItem(R.drawable.ic_vc));
		marketList.add(new MarketItem(R.drawable.ic_wc));
		marketList.add(new MarketItem(R.drawable.ic_xc));




		marketAdapter = new MarketAdapter(this, marketList);

		marketListView.setAdapter(marketAdapter);

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

