package in.jewelchat.jewelchat.screens;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import in.jewelchat.jewelchat.BaseNetworkActivity;
import in.jewelchat.jewelchat.JewelChatApp;
import in.jewelchat.jewelchat.R;
import in.jewelchat.jewelchat.adapter.ContactListAdapter;
import in.jewelchat.jewelchat.database.JewelChatDataProvider;
import in.jewelchat.jewelchat.models.BasicJewelCountChangedEvent;

/**
 * Created by mayukhchakraborty on 05/03/16.
 */
public class ActivityContacts extends BaseNetworkActivity implements LoaderManager.LoaderCallbacks<Cursor> , AdapterView.OnItemClickListener  {

	private ContactListAdapter contactListAdapter;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts);

		rootLayout = (CoordinatorLayout) findViewById(R.id.main_content);

		setUpAppbar();

		TextView toolbar_title = (TextView)rootLayout.findViewById(R.id.toolbarTitle);
		toolbar_title.setText("Contacts");

		ImageView toolbar_image = (ImageView)rootLayout.findViewById(R.id.toolbarImage);
		toolbar_image.setVisibility(ImageView.GONE);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);

		listView = (ListView)findViewById(R.id.contact_list);
		contactListAdapter = new ContactListAdapter(getApplicationContext());
		listView.setAdapter(contactListAdapter);

		this.listView.setOnItemClickListener(this);


		getSupportLoaderManager().initLoader(1, null, this);

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
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		Uri uri = Uri.parse(JewelChatDataProvider.SCHEME+"://" + JewelChatDataProvider.AUTHORITY + "/"+ "chatlist");
		CursorLoader cursorLoader = new CursorLoader(getApplicationContext(),
				uri, null, null, null, null);
		return cursorLoader;
	}


	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		this.contactListAdapter.swapCursor(data);
	}


	public void onLoaderReset(Loader<Cursor> loader) {
		this.contactListAdapter.swapCursor(null);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
