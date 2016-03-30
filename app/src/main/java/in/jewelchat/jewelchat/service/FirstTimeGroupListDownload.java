package in.jewelchat.jewelchat.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import in.jewelchat.jewelchat.JewelChatApp;
import in.jewelchat.jewelchat.JewelChatPrefs;
import in.jewelchat.jewelchat.JewelChatURLS;
import in.jewelchat.jewelchat.database.ContactContract;
import in.jewelchat.jewelchat.database.GroupContract;
import in.jewelchat.jewelchat.database.JewelChatDataProvider;
import in.jewelchat.jewelchat.models.Group;
import in.jewelchat.jewelchat.models.GroupMember;
import in.jewelchat.jewelchat.network.JewelChatRequest;
import in.jewelchat.jewelchat.util.AnalyticsTrackers;
import in.jewelchat.jewelchat.util.NetworkConnectivityStatus;

/**
 * Created by mayukhchakraborty on 27/03/16.
 */
public class FirstTimeGroupListDownload extends IntentService
		implements Response.ErrorListener, Response.Listener<JSONObject>{

	/**
	 * Creates an IntentService.  Invoked by your subclass's constructor.
	 *
	 * @param name Used to name the worker thread, important only for debugging.
	 */
	public FirstTimeGroupListDownload() {
		super("FirstTimeGroupListDownload");
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

				Crashlytics.logException(error);

				Intent i = getBaseContext().getPackageManager()
						.getLaunchIntentForPackage( getBaseContext().getPackageName());
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);

			}else if(response.statusCode == 500){

			}else{

			}
		}


		Crashlytics.logException(error);
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		JewelChatRequest req = new JewelChatRequest(Request.Method.GET, JewelChatURLS.GETGROUPLIST, null, this, this);
		if (NetworkConnectivityStatus.getConnectivityStatus() == NetworkConnectivityStatus.CONNECTED)
			JewelChatApp.getRequestQueue().add(req);
	}

	@Override
	public void onResponse(JSONObject response) {

		JewelChatApp.appLog("FirstTimeContactDownloadService" + ":onResponse");

		try {

			Gson gson = new Gson();
			JSONArray mygroups = response.getJSONArray("mygroups");
			Type listTypeGr = new TypeToken<List<Group>>(){}.getType();
			List<Group> mygr = gson.fromJson(mygroups.toString(), listTypeGr);

			JSONArray groupmembers = response.getJSONArray("mygroups");
			Type listTypeGrM = new TypeToken<List<Group>>(){}.getType();
			List<GroupMember> grmembers = gson.fromJson(mygroups.toString(), listTypeGrM);

			ContentValues[] cv = new ContentValues[mygr.size()];
			int count=0;
			for(Group i: mygr){

				cv[count] = new ContentValues();
				cv[count].put(ContactContract.JEWELCHAT_ID, i.groupId);
				cv[count].put(ContactContract.IS_GROUP, true);
				cv[count].put(ContactContract.CONTACT_NAME, i.name);
				cv[count].put(ContactContract.CURRENT_IMAGE_NUMBER, i.pic);
				cv[count].put(ContactContract.IS_REGIS, true);
				cv[count].put(ContactContract.STATUS_MSG, i.status);
				cv[count].put(ContactContract.IS_GROUP_ADMIN, i.isAdmin);

			}

			Uri uri = Uri.parse(JewelChatDataProvider.SCHEME + "://" + JewelChatDataProvider.AUTHORITY + "/" + ContactContract.SQLITE_TABLE_NAME);
			getContentResolver().bulkInsert(uri, cv);


			ContentValues[] cv1 = new ContentValues[grmembers.size()];
			count=0;
			for(GroupMember i: grmembers){

				cv1[count] = new ContentValues();
				cv1[count].put(GroupContract.GROUP_ID, i.groupId);
				cv1[count].put(GroupContract.JEWELCHAT_ID, i.id);
				cv1[count].put(GroupContract.GCM_TOKEN, i.gcmtoken);
				cv1[count].put(GroupContract.CURRENT_IMAGE_NUMBER, i.image_current);
				cv1[count].put(GroupContract.CONTACT_NUMBER, i.pno);
				cv1[count].put(GroupContract.CONTACT_NAME, i.name);
				cv1[count].put(GroupContract.ISADMIN, i.isAdmin);

			}

			Uri uri1 = Uri.parse(JewelChatDataProvider.SCHEME+"://" + JewelChatDataProvider.AUTHORITY + "/"+ GroupContract.SQLITE_TABLE_NAME);
			getContentResolver().delete(uri1, null, null);
			getContentResolver().bulkInsert(uri1, cv1);


			AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP)
					.send(new HitBuilders.EventBuilder()
							.setCategory("Initialization")
							.setAction("Groups Downloaded")
							.setLabel("Initialization Success")
							.build());

			SharedPreferences.Editor e = JewelChatApp.getSharedPref().edit();
			e.putBoolean(JewelChatPrefs.GROUPS_DOWNLOADED, true);
			e.commit();


		} catch (JSONException e) {
			Crashlytics.logException(e);
			e.printStackTrace();
		}


	}
}
