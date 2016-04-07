package in.jewelchat.jewelchat.screens;

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

import in.jewelchat.jewelchat.JewelChat;
import in.jewelchat.jewelchat.JewelChatApp;
import in.jewelchat.jewelchat.R;

/**
 * Created by mayukhchakraborty on 02/03/16.
 */
public class FragmentTasks extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

	private static FragmentTasks uniqueInstance;
	private String className;
	private ListView listView;

	/*

	public static FragmentTasks getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new FragmentTasks();
		return uniqueInstance;
	}

	*/

	@Override
	public void onCreate(Bundle savedInstanceState) {
		className = getClass().getSimpleName();
		JewelChatApp.appLog(className + ":onCreate");
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {

		JewelChatApp.appLog(className + ":onCreateView");

		View view = ((JewelChat)getActivity()).getFragmentTasksView(inflater, container, savedInstanceState);
		listView = (ListView)view.findViewById(R.id.tasklist);
		listView.setAdapter(((JewelChat)getActivity()).getTasksAdapter());



		return view;

		//return null;
	}

	@Override
	public void onResume(){
		super.onResume();
		getLoaderManager().initLoader(1, null, this);
		Log.i("Task>>>","onResume");
	}




	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {

		return ((JewelChat)getActivity()).getTaskListCursorLoader();
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor returnCursor) {

		((JewelChat)getActivity()).getTasksAdapter().swapCursor(returnCursor);

	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {

		((JewelChat)getActivity()).getTasksAdapter().swapCursor(null);

	}

	@Override
	public void onDestroy(){
		super.onDestroy();
		Log.i("Fragment>>>", "TASK Destroyed");

	}

}
