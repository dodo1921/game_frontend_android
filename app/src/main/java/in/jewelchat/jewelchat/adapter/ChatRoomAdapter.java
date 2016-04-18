package in.jewelchat.jewelchat.adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import in.jewelchat.jewelchat.JewelChatApp;
import in.jewelchat.jewelchat.JewelChatPrefs;
import in.jewelchat.jewelchat.R;
import in.jewelchat.jewelchat.database.ChatMessageContract;
import in.jewelchat.jewelchat.models.BasicJewelCountChangedEvent;

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

		Log.i("Here","Here");

		String msgText = cursor.getString(cursor.getColumnIndex(ChatMessageContract.MSG_TEXT));
		final String jewelType = cursor.getString(cursor.getColumnIndex(ChatMessageContract.JEWEL_TYPE));

		TextView day = (TextView)view.findViewById(R.id.day);
		TextView system_messages = (TextView)view.findViewById(R.id.system_messages);
		ImageView jewel = (ImageView)view.findViewById(R.id.jewel);

		jewel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ImageView j = (ImageView)v;
				j.setImageDrawable(null);

				int A = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.A,0);
				int B = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.B,0);
				int C = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.C,0);
				int D = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.D,0);
				int Y = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.Y,0);
				int Z = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.Z,0);
				int LEVEL = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.LEVEL,0);
				int LEVEL_XP = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.XP_MAX,0);
				int XP = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.XP,0);



				if(jewelType.equals("A")) {
					JewelChatApp.getSharedPref().edit().putInt(JewelChatPrefs.A, ++A).commit();
					JewelChatApp.getSharedPref().edit().putInt(JewelChatPrefs.XP, XP+=2).commit();
				}else if(jewelType.equals("B")) {
					JewelChatApp.getSharedPref().edit().putInt(JewelChatPrefs.B, ++B).commit();
					JewelChatApp.getSharedPref().edit().putInt(JewelChatPrefs.XP, XP+=2).commit();
				}else if(jewelType.equals("C")) {
					JewelChatApp.getSharedPref().edit().putInt(JewelChatPrefs.C, ++C).commit();
					JewelChatApp.getSharedPref().edit().putInt(JewelChatPrefs.XP, XP+=2).commit();
				}else if(jewelType.equals("D")) {
					JewelChatApp.getSharedPref().edit().putInt(JewelChatPrefs.D, ++D).commit();
					JewelChatApp.getSharedPref().edit().putInt(JewelChatPrefs.XP, XP+=2).commit();
				}

				JewelChatApp.getBusInstance().post(new BasicJewelCountChangedEvent(A,C,B,D,Y,Z,LEVEL,LEVEL_XP,XP,false));

			}
		});

		TextView group_member = (TextView)view.findViewById(R.id.group_member);

		github.ankushsachdeva.emojicon.EmojiconTextView chat_element = (github.ankushsachdeva.emojicon.EmojiconTextView)view.findViewById(R.id.chat_element);

		day.setVisibility(View.GONE);
		system_messages.setVisibility(View.GONE);
		group_member.setVisibility(View.GONE);

		if(jewelType.equals("A"))
			jewel.setImageResource(R.drawable.ic_ac);
		else if(jewelType.equals("B"))
			jewel.setImageResource(R.drawable.ic_bc);
		else if(jewelType.equals("C"))
			jewel.setImageResource(R.drawable.ic_cc);
		else if(jewelType.equals("D"))
			jewel.setImageResource(R.drawable.ic_dc);

		chat_element.setText(msgText);


	}


}