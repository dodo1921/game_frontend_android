package in.jewelchat.jewelchat.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

import in.jewelchat.jewelchat.JewelChatApp;
import in.jewelchat.jewelchat.database.ChatMessageContract;
import in.jewelchat.jewelchat.database.ContactContract;
import in.jewelchat.jewelchat.database.JewelChatDataProvider;
import in.jewelchat.jewelchat.database.TasksContract;
import in.jewelchat.jewelchat.util.AnalyticsTrackers;

/**
 * Created by mayukhchakraborty on 23/03/16.
 */
public class FirstTimeContactDownloadService extends IntentService implements Response.ErrorListener, Response.Listener<JSONObject>{
	/**
	 * Creates an IntentService.  Invoked by your subclass's constructor.
	 *
	 * @param name Used to name the worker thread, important only for debugging.
	 */
	public FirstTimeContactDownloadService() {
		super("FirstTimeContactDownloadService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		Log.i(">>FirstTimeContact","FirstTimeContact");

		JewelChatApp.appLog("FirstTimeContactDownloadService"+":onHandleIntent");



		Set<PhoneBookContact> phoneBookContactsSet = new HashSet<PhoneBookContact>();
		Set<Long> phoneNumbers = new HashSet<>();
		phoneNumbers.add(919005835708L);

		String[] projection = new String[]{
				ContactsContract.CommonDataKinds.Phone._ID,
				ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER ,
				ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY ,
				ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI };

		Cursor cursor = JewelChatApp.getInstance().getContentResolver().query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, null, null,
				ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY + " ASC");

		if (cursor == null) {
			return;
		}

		Log.i("Here","Here");
		Phonenumber.PhoneNumber pn;
		PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
		String phoneStr;
		String e164Format;
		while (cursor.moveToNext()) {
			phoneStr = cursor.getString(1);
			try {
				pn = phoneNumberUtil.parse(phoneStr, "IN");
				e164Format = phoneNumberUtil.format(pn, PhoneNumberUtil.PhoneNumberFormat.E164);
				if (e164Format == null || e164Format.length() < 11)
					continue;

				PhoneBookContact pbc = new PhoneBookContact();
				pbc.contactNumber = Long.parseLong(e164Format.substring(1));
				phoneNumbers.add(Long.parseLong(e164Format.substring(1)));
				pbc.contactName = cursor.getString(2);
				if (cursor.getString(3) != null) {
					pbc.contactImage = cursor.getString(3);
				} else {
					pbc.contactImage = "";
				}

				if(pbc.contactNumber != JewelChatApp.TEAM_JEWELCHAT)
					phoneBookContactsSet.add(pbc);

			} catch (NumberParseException e) {
				Crashlytics.logException(e);
			}
		}
		cursor.close();


		ContentValues[] cv = new ContentValues[phoneBookContactsSet.size()+1];
		int count=0;
		for(PhoneBookContact i: phoneBookContactsSet){

			cv[count] = new ContentValues();
			cv[count].put(ContactContract.CONTACT_NUMBER, i.contactNumber);
			cv[count].put(ContactContract.PHONEBOOK_CONTACT_NAME, i.contactName);
			cv[count].put(ContactContract.IMAGE_PHONEBOOK, i.contactImage);
			cv[count].put(ContactContract.IS_PHONEBOOK_CONTACT, 1);
			cv[count].put(ContactContract.IS_INVITED, 0);
			count++;
		}

		cv[count] = new ContentValues();
		cv[count].put(ContactContract.CONTACT_NUMBER, JewelChatApp.TEAM_JEWELCHAT);
		cv[count].put(ContactContract.CONTACT_NAME, "JewelChat Team");
		cv[count].put(ContactContract.JEWELCHAT_ID, JewelChatApp.TEAM_JEWELCHAT_ID);
		cv[count].put(ContactContract.IMAGE_PHONEBOOK, "");
		cv[count].put(ContactContract.IS_REGIS, 1);
		cv[count].put(ContactContract.IS_PHONEBOOK_CONTACT, 0);

		Uri uri = Uri.parse(JewelChatDataProvider.SCHEME+"://" + JewelChatDataProvider.AUTHORITY + "/"+ ContactContract.SQLITE_TABLE_NAME);
		getContentResolver().delete(uri, null, null);
		getContentResolver().bulkInsert(uri, cv);


		ContentValues msg1 = new ContentValues();
		msg1.put(ChatMessageContract.MSG_TYPE, 0);
		msg1.put(ChatMessageContract.CREATED_TIME, System.currentTimeMillis());
		msg1.put(ChatMessageContract.CHAT_ROOM, JewelChatApp.TEAM_JEWELCHAT_ID);
		msg1.put(ChatMessageContract.CREATOR_ID, JewelChatApp.TEAM_JEWELCHAT_ID);
		msg1.put(ChatMessageContract.RECEIVED_MSG_ID, System.currentTimeMillis());
		msg1.put(ChatMessageContract.IS_SUBMITTED, 1);
		msg1.put(ChatMessageContract.IS_DELIVERED, 1);
		msg1.put(ChatMessageContract.IS_READ, 1);
		msg1.put(ChatMessageContract.JEWEL_TYPE, "A");
		msg1.put(ChatMessageContract.IS_JEWEL_PICKED, 0);
		msg1.put(ChatMessageContract.MSG_TEXT, "Welcome to Jewel Chat.");

		Uri urimsg = Uri.parse(JewelChatDataProvider.SCHEME+"://" + JewelChatDataProvider.AUTHORITY + "/"+ ChatMessageContract.SQLITE_TABLE_NAME);
		getContentResolver().delete(urimsg, null, null);
		getContentResolver().insert(urimsg, msg1);


		msg1 = new ContentValues();
		msg1.put(ChatMessageContract.MSG_TYPE, 0);
		msg1.put(ChatMessageContract.CREATED_TIME, System.currentTimeMillis());
		msg1.put(ChatMessageContract.CHAT_ROOM, JewelChatApp.TEAM_JEWELCHAT_ID);
		msg1.put(ChatMessageContract.CREATOR_ID, JewelChatApp.TEAM_JEWELCHAT_ID);
		msg1.put(ChatMessageContract.RECEIVED_MSG_ID, System.currentTimeMillis());
		msg1.put(ChatMessageContract.IS_SUBMITTED, 1);
		msg1.put(ChatMessageContract.IS_DELIVERED, 1);
		msg1.put(ChatMessageContract.IS_READ, 1);
		msg1.put(ChatMessageContract.JEWEL_TYPE, "B");
		msg1.put(ChatMessageContract.IS_JEWEL_PICKED, 0);
		msg1.put(ChatMessageContract.MSG_TEXT, "Welcome to Jewel Chat.");

		//urimsg = Uri.parse(JewelChatDataProvider.SCHEME+"://" + JewelChatDataProvider.AUTHORITY + "/"+ ChatMessageContract.SQLITE_TABLE_NAME);
		//getContentResolver().delete(urimsg, null, null);
		getContentResolver().insert(urimsg, msg1);


		msg1 = new ContentValues();
		msg1.put(ChatMessageContract.MSG_TYPE, 0);
		msg1.put(ChatMessageContract.CREATED_TIME, System.currentTimeMillis());
		msg1.put(ChatMessageContract.CHAT_ROOM, JewelChatApp.TEAM_JEWELCHAT_ID);
		msg1.put(ChatMessageContract.CREATOR_ID, JewelChatApp.TEAM_JEWELCHAT_ID);
		msg1.put(ChatMessageContract.RECEIVED_MSG_ID, System.currentTimeMillis());
		msg1.put(ChatMessageContract.IS_SUBMITTED, 1);
		msg1.put(ChatMessageContract.IS_DELIVERED, 1);
		msg1.put(ChatMessageContract.IS_READ, 1);
		msg1.put(ChatMessageContract.JEWEL_TYPE, "C");
		msg1.put(ChatMessageContract.IS_JEWEL_PICKED, 0);
		msg1.put(ChatMessageContract.MSG_TEXT, "Welcome to Jewel Chat.");

		//Uri urimsg = Uri.parse(JewelChatDataProvider.SCHEME+"://" + JewelChatDataProvider.AUTHORITY + "/"+ ChatMessageContract.SQLITE_TABLE_NAME);
		//getContentResolver().delete(urimsg, null, null);
		getContentResolver().insert(urimsg, msg1);


		msg1 = new ContentValues();
		msg1.put(ChatMessageContract.MSG_TYPE, 0);
		msg1.put(ChatMessageContract.CREATED_TIME, System.currentTimeMillis());
		msg1.put(ChatMessageContract.CHAT_ROOM, JewelChatApp.TEAM_JEWELCHAT_ID);
		msg1.put(ChatMessageContract.CREATOR_ID, JewelChatApp.TEAM_JEWELCHAT_ID);
		msg1.put(ChatMessageContract.RECEIVED_MSG_ID, System.currentTimeMillis());
		msg1.put(ChatMessageContract.IS_SUBMITTED, 1);
		msg1.put(ChatMessageContract.IS_DELIVERED, 1);
		msg1.put(ChatMessageContract.IS_READ, 1);
		msg1.put(ChatMessageContract.JEWEL_TYPE, "C");
		msg1.put(ChatMessageContract.IS_JEWEL_PICKED, 0);
		msg1.put(ChatMessageContract.MSG_TEXT, "Welcome to Jewel Chat.");

		//Uri urimsg = Uri.parse(JewelChatDataProvider.SCHEME+"://" + JewelChatDataProvider.AUTHORITY + "/"+ ChatMessageContract.SQLITE_TABLE_NAME);
		//getContentResolver().delete(urimsg, null, null);
		getContentResolver().insert(urimsg, msg1);



		AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP)
				.send(new HitBuilders.EventBuilder()
						.setCategory("Initialization")
						.setAction("Contacts read")
						.setLabel("Initialization Success")
						.build());

		ContentValues t1 = new ContentValues();
		t1.put(TasksContract.TASK_ID, 1);
		t1.put(TasksContract.TYPE, 0);
		t1.put(TasksContract.VALUE, 1);
		t1.put(TasksContract.TASK_TEXT, "Invite (x) Contacts." );
		t1.put(TasksContract.DIAMOND_COUNT, 1);
		t1.put(TasksContract.TASK_NOTE, "");

		Uri uritask = Uri.parse(JewelChatDataProvider.SCHEME+"://" + JewelChatDataProvider.AUTHORITY + "/"+ TasksContract.SQLITE_TABLE_NAME);
		getContentResolver().delete(uritask, null, null);
		getContentResolver().insert(uritask, t1);

		t1 = new ContentValues();
		t1.put(TasksContract.TASK_ID, 2);
		t1.put(TasksContract.TYPE, 0);
		t1.put(TasksContract.VALUE, 1);
		t1.put(TasksContract.TASK_TEXT, "Refer (x) Contacts." );
		t1.put(TasksContract.DIAMOND_COUNT, 2);
		t1.put(TasksContract.TASK_NOTE, "");
		getContentResolver().insert(uritask, t1);

		t1 = new ContentValues();
		t1.put(TasksContract.TASK_ID, 3);
		t1.put(TasksContract.TYPE, 1);
		t1.put(TasksContract.VALUE, 1);
		t1.put(TasksContract.TASK_TEXT, "Pick (x)<img src = 'A'>." );
		t1.put(TasksContract.DIAMOND_COUNT, 1);
		t1.put(TasksContract.TASK_NOTE, "");
		getContentResolver().insert(uritask, t1);

		t1 = new ContentValues();
		t1.put(TasksContract.TASK_ID, 4);
		t1.put(TasksContract.TYPE, 1);
		t1.put(TasksContract.VALUE, 1);
		t1.put(TasksContract.TASK_TEXT, "Pick (x)<img src = 'A'> each from 5 Contacts." );
		t1.put(TasksContract.DIAMOND_COUNT, 2);
		t1.put(TasksContract.TASK_NOTE, "");
		getContentResolver().insert(uritask, t1);

		t1 = new ContentValues();
		t1.put(TasksContract.TASK_ID, 5);
		t1.put(TasksContract.TYPE, 1);
		t1.put(TasksContract.VALUE, 1);
		t1.put(TasksContract.TASK_TEXT, "Pick (x)<img src = 'B'>." );
		t1.put(TasksContract.DIAMOND_COUNT, 1);
		t1.put(TasksContract.TASK_NOTE, "");
		getContentResolver().insert(uritask, t1);

		t1 = new ContentValues();
		t1.put(TasksContract.TASK_ID, 6);
		t1.put(TasksContract.TYPE, 1);
		t1.put(TasksContract.VALUE, 1);
		t1.put(TasksContract.TASK_TEXT, "Pick (x)<img src = 'B'> each from 5 Contacts." );
		t1.put(TasksContract.DIAMOND_COUNT, 2);
		t1.put(TasksContract.TASK_NOTE, "");
		getContentResolver().insert(uritask, t1);

		t1 = new ContentValues();
		t1.put(TasksContract.TASK_ID, 7);
		t1.put(TasksContract.TYPE, 1);
		t1.put(TasksContract.VALUE, 1);
		t1.put(TasksContract.TASK_TEXT, "Pick (x)<img src = 'C'>." );
		t1.put(TasksContract.DIAMOND_COUNT, 1);
		t1.put(TasksContract.TASK_NOTE, "");
		getContentResolver().insert(uritask, t1);

		t1 = new ContentValues();
		t1.put(TasksContract.TASK_ID, 8);
		t1.put(TasksContract.TYPE, 1);
		t1.put(TasksContract.VALUE, 1);
		t1.put(TasksContract.TASK_TEXT, "Pick (x)<img src = 'C'> each from 5 Contacts." );
		t1.put(TasksContract.DIAMOND_COUNT, 2);
		t1.put(TasksContract.TASK_NOTE, "");
		getContentResolver().insert(uritask, t1);

		t1 = new ContentValues();
		t1.put(TasksContract.TASK_ID, 9);
		t1.put(TasksContract.TYPE, 1);
		t1.put(TasksContract.VALUE, 1);
		t1.put(TasksContract.TASK_TEXT, "Pick (x)<img src = 'D'>." );
		t1.put(TasksContract.DIAMOND_COUNT, 1);
		t1.put(TasksContract.TASK_NOTE, "");
		getContentResolver().insert(uritask, t1);

		t1 = new ContentValues();
		t1.put(TasksContract.TASK_ID, 10);
		t1.put(TasksContract.TYPE, 1);
		t1.put(TasksContract.VALUE, 1);
		t1.put(TasksContract.TASK_TEXT, "Pick (x)<img src = 'D'> each from 5 Contacts." );
		t1.put(TasksContract.DIAMOND_COUNT, 2);
		t1.put(TasksContract.TASK_NOTE, "");
		getContentResolver().insert(uritask, t1);

	}

	@Override
	public void onErrorResponse(VolleyError error) {

	}

	@Override
	public void onResponse(JSONObject response) {

	}

}


class PhoneBookContact extends Object{

	public long contactNumber;
	public String contactName;
	public String contactImage;

	public boolean equal(PhoneBookContact pbc){

		if(pbc.contactNumber == contactNumber)
			return true;
		else
			return false;

	}

}
