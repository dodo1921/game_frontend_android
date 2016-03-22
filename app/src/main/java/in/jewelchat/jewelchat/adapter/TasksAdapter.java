package in.jewelchat.jewelchat.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

/**
 * Created by mayukhchakraborty on 21/03/16.
 */
public class TasksAdapter extends CursorAdapter{

	public TasksAdapter(Context context, Cursor c) {
		super(context, null, false);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return null;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {

	}
}
