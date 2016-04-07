package in.jewelchat.jewelchat.screens;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import in.jewelchat.jewelchat.JewelChat;
import in.jewelchat.jewelchat.JewelChatApp;
import in.jewelchat.jewelchat.R;

/**
 * Created by mayukhchakraborty on 02/03/16.
 */
public class FragmentChatList extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

	private static FragmentChatList uniqueInstance;
	private String className;
	private String contactIDChatDelete;
	private String contactNameChatDelete;
	private ListView listView;

	private FloatingActionButton contacts;
	private FloatingActionButton newGroup;

	/*

	public static FragmentChatList getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new FragmentChatList();
		return uniqueInstance;
	}
	*/

	@Override
	public void onCreate(Bundle savedInstanceState) {
		className = getClass().getSimpleName();
		JewelChatApp.appLog(className + ":onCreate");
		super.onCreate(savedInstanceState);
		getLoaderManager().initLoader(1, null, this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		JewelChatApp.appLog(className + ":onCreateView");

		View view = ((JewelChat)getActivity()).getFragmentChatListView(inflater, container, savedInstanceState);
		listView = (ListView)view.findViewById(R.id.chat_list);
		listView.setAdapter(((JewelChat)getActivity()).getChatListAdapter());

		//this.listView.setOnItemClickListener(this);
		//this.listView.setOnItemLongClickListener(this);
		//this.listView.setLongClickable(true);



		setUpFABButton(view);
		return view;
	}

	private void setUpFABButton(View view) {

		contacts = (FloatingActionButton)view.findViewById(R.id.contacts);
		contacts.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent i = new Intent(getActivity(), ActivityContacts.class);
				startActivity(i);
				((FloatingActionsMenu)v.getParent()).toggle();

			}
		});

		newGroup = (FloatingActionButton)view.findViewById(R.id.new_group);
		newGroup.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent i = new Intent(getActivity(), ActivityNewGroup.class);
				startActivity(i);
				((FloatingActionsMenu) v.getParent()).toggle();
			}
		});

	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {

		return ((JewelChat)getActivity()).getChatListCursorLoader();

	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor returnCursor) {
		((JewelChat)getActivity()).getChatListAdapter().swapCursor(returnCursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {

		((JewelChat)getActivity()).getChatListAdapter().swapCursor(null);

	}

	@Override
	public void onDestroy(){
		super.onDestroy();
		Log.i("Fragment>>>", "CHATLIST Destroyed");

	}


}
