package in.jewelchat.jewelchat.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import in.jewelchat.jewelchat.R;

/**
 * Created by mayukhchakraborty on 09/04/16.
 */
public class ChatRoomAdapter extends CursorAdapter {

	private LayoutInflater cursorInflater;

	public ChatRoomAdapter(Context context) {
		super(context, null, false);
	}


	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {

		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View retView = inflater.inflate(R.layout.activity_chatroom_element, parent, false);

		return retView;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {



	}
}