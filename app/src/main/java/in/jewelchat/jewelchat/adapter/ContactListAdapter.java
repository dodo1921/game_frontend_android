package in.jewelchat.jewelchat.adapter;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import in.jewelchat.jewelchat.JewelChatApp;
import in.jewelchat.jewelchat.R;
import in.jewelchat.jewelchat.database.ContactContract;

/**
 * Created by mayukhchakraborty on 01/04/16.
 */
public class ContactListAdapter extends CursorAdapter {

	private LayoutInflater cursorInflater;

	public ContactListAdapter(Context context) {
		super(context, null, false);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {

		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View retView = inflater.inflate(R.layout.activity_contacts_elements, parent, false);

		return retView;

	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {

		long contact_number = cursor.getLong(cursor.getColumnIndex(ContactContract.CONTACT_NUMBER));
		String contact_name = cursor.getString(cursor.getColumnIndex(ContactContract.PHONEBOOK_CONTACT_NAME));
		String jewelchat_contact_name = cursor.getString(cursor.getColumnIndex(ContactContract.CONTACT_NAME));
		String image_phonebook = cursor.getString(cursor.getColumnIndex(ContactContract.IMAGE_PHONEBOOK));
		int is_invited = cursor.getInt(cursor.getColumnIndex(ContactContract.IS_INVITED));

		ImageView vContactAvatar = (ImageView)view.findViewById(R.id.contact_image);
		TextView vContactName = (TextView)view.findViewById(R.id.contact_name);
		TextView vContactNumber = (TextView)view.findViewById(R.id.contact_number);
		TextView vInviteButton = (TextView)view.findViewById(R.id.contact_item_invite);

		if(is_invited==1) {
			vInviteButton.setVisibility(View.GONE);
			vInviteButton.setOnClickListener(null);
		}else{
			vInviteButton.setVisibility(View.VISIBLE);
			vInviteButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

				}
			});

		}

		if(!(image_phonebook==null) && !image_phonebook.equals(""))
			vContactAvatar.setImageURI(Uri.parse(image_phonebook));
		else {
			vContactAvatar.setBackgroundColor(ContextCompat.getColor(JewelChatApp.getInstance().getApplicationContext(), R.color.grey_light));
			vContactAvatar.setImageResource(R.drawable.default_profile);
		}

		if( contact_name==null || contact_name.equals("") ){
			if(jewelchat_contact_name!=null && !jewelchat_contact_name.equals("")){
				vContactName.setText(jewelchat_contact_name);
				vContactNumber.setText("+" + contact_number);
			}else{
				vContactName.setText("+"+contact_number);
				vContactNumber.setText("");
			}
		}else{
			vContactName.setText(contact_name);
			vContactNumber.setText("+" + contact_number);
		}



	}
}

