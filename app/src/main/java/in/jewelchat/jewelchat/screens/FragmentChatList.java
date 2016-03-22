package in.jewelchat.jewelchat.screens;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import in.jewelchat.jewelchat.JewelChatApp;
import in.jewelchat.jewelchat.R;
import in.jewelchat.jewelchat.adapter.ChatListAdapter;
import in.jewelchat.jewelchat.database.JewelChatDataProvider;

/**
 * Created by mayukhchakraborty on 02/03/16.
 */
public class FragmentChatList extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>, android.widget.AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {

	private static FragmentChatList uniqueInstance;
	private ChatListAdapter chatListAdapter;
	private String className;
	private String contactIDChatDelete;
	private String contactNameChatDelete;
	private ListView listView;


	public static FragmentChatList getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new FragmentChatList();
		return uniqueInstance;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		className = getClass().getSimpleName();
		JewelChatApp.appLog(className + ":onCreate");
		super.onCreate(savedInstanceState);


	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		JewelChatApp.appLog(className + ":onCreateView");
		View view = inflater.inflate(R.layout.fragment_chat, container, false);
		listView = (ListView)view.findViewById(R.id.list);
		chatListAdapter = new ChatListAdapter(getContext());
		listView.setAdapter(chatListAdapter);

		this.listView.setOnItemClickListener(this);
		this.listView.setOnItemLongClickListener(this);
		this.listView.setLongClickable(true);

		getLoaderManager().initLoader(1, null, this);

		//setUpFABButton(view);
		return view;
	}


	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		Uri uri = Uri.parse(JewelChatDataProvider.SCHEME+"://" + JewelChatDataProvider.AUTHORITY + "/"+ "chatlist");
		CursorLoader cursorLoader = new CursorLoader(getContext(),
				uri, null, null, null, null);
		return cursorLoader;

	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor returnCursor) {
		this.chatListAdapter.swapCursor(returnCursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {

		this.chatListAdapter.swapCursor(null);

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

	}
}
