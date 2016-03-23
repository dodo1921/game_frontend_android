package in.jewelchat.jewelchat.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

import in.jewelchat.jewelchat.JewelChatApp;
import in.jewelchat.jewelchat.JewelChatPrefs;
import in.jewelchat.jewelchat.JewelChatURLS;
import in.jewelchat.jewelchat.database.ContactContract;
import in.jewelchat.jewelchat.database.JewelChatDataProvider;
import in.jewelchat.jewelchat.network.JewelChatRequest;
import in.jewelchat.jewelchat.util.NetworkConnectivityStatus;

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

		Set<PhoneBookContact> phoneBookContactsSet = new HashSet<>();
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

		}

		cv[count] = new ContentValues();
		cv[count].put(ContactContract.CONTACT_NUMBER, 919005835708L);
		cv[count].put(ContactContract.CONTACT_NAME, "JewelChatTeam");
		cv[count].put(ContactContract.IMAGE_PHONEBOOK, "");
		cv[count].put(ContactContract.IS_PHONEBOOK_CONTACT, 0);

		Uri uri = Uri.parse(JewelChatDataProvider.SCHEME+"://" + JewelChatDataProvider.AUTHORITY + "/"+ ContactContract.SQLITE_TABLE_NAME);
		getContentResolver().bulkInsert(uri, cv);


		Gson gson = new Gson();
		JSONObject params = new JSONObject();

		try {
			params.put("phoneNumberList", new JSONArray(gson.toJson(phoneNumbers.toArray())));
			JewelChatRequest req = new JewelChatRequest(Request.Method.POST, JewelChatURLS.GETCONTACTBYPHONENUMBERLIST, params, this, this);
			if (NetworkConnectivityStatus.getConnectivityStatus() == NetworkConnectivityStatus.CONNECTED)
				JewelChatApp.getRequestQueue().add(req);
		} catch (JSONException e) {
			JewelChatApp.appLog("FirstTimeContactDownloadService" + ":makeRequest");
			Crashlytics.logException(e);
		}


	}

	@Override
	public void onErrorResponse(VolleyError error) {

		NetworkResponse response = error.networkResponse;
		if(response != null && response.data != null){
			if(response.statusCode == 403){

				SharedPreferences.Editor editor = JewelChatApp.getSharedPref().edit();
				editor.putBoolean(JewelChatPrefs.IS_LOGGED, false);
				editor.putString(JewelChatPrefs.MY_ID, "");
				editor.commit();



			}else if(response.statusCode == 500){

			}else{

			}
		}

		JewelChatApp.appLog("FirstTimeContactDownloadService");
		Crashlytics.logException(error);

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
