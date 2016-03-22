package in.jewelchat.jewelchat.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import in.jewelchat.jewelchat.R;

/**
 * Created by mayukhchakraborty on 16/03/16.
 */
public class ChatListAdapter extends CursorAdapter {

	private LayoutInflater cursorInflater;

	public ChatListAdapter(Context context) {
		super(context, null, false);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {

		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View retView = inflater.inflate(R.layout.fragment_chat_element, parent, false);

		return retView;

	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {

		TextView contactname = (TextView) view.findViewById(R.id.contact_name);
		contactname.setText(cursor.getString(1));
		//TextView lastpost = (TextView) view.findViewById(R.id.lastpost);
		int msgtype = cursor.getInt(10);
		String post=" ";
		if(msgtype==0){
			post = cursor.getString(12);
		}else if(msgtype==2){
			post = "Image";
		}else if(msgtype==3)
			post = "Video";
		//lastpost.setText(post);
		TextView lastposttime = (TextView) view.findViewById(R.id.lastposttime);
		long time = cursor.getLong(12);
		//lastposttime.setText(DateUtils.getRelativeDateTimeString(context, time, DateUtils.SECOND_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL));
		lastposttime.setText(DateUtils.getRelativeTimeSpanString(context, time));

		//TextView lastposttime = (TextView) view.findViewById(R.id.lastposttime);
		//lastposttime.setText(cursor.getString(0));
		ImageView image = (ImageView) view.findViewById(R.id.contact_image_chatlist);
		String imagepath = cursor.getString(4);
		int contactnumber = cursor.getInt(2);
		/*
		try {
			//Picasso.with(context).load("http://"+ JewelChatURLS.CLOUDPATH+"/"+ URLEncoder.encode(contactnumber+"_"+imagepath, "utf-8")).placeholder(R.drawable.default_image).error(R.drawable.default_image).into(image);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/

		int isread = cursor.getInt(13);
		int creatorid = cursor.getInt(15);
		int chatroom = cursor.getInt(16);
		if(creatorid == chatroom && isread==0)
			view.setBackgroundColor(Color.argb(75, 51, 153, 255));
		else if(creatorid == chatroom && isread==1)
			view.setBackgroundColor(Color.argb(0, 0, 0, 0));

	}
}
