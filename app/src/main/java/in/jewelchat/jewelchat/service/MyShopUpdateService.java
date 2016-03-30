package in.jewelchat.jewelchat.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.crashlytics.android.Crashlytics;

import org.json.JSONObject;

import in.jewelchat.jewelchat.JewelChatApp;
import in.jewelchat.jewelchat.JewelChatPrefs;
import in.jewelchat.jewelchat.JewelChatURLS;
import in.jewelchat.jewelchat.network.JewelChatRequest;
import in.jewelchat.jewelchat.util.NetworkConnectivityStatus;

/**
 * Created by mayukhchakraborty on 23/03/16.
 */
public class MyShopUpdateService extends IntentService  implements Response.ErrorListener, Response.Listener<JSONObject>  {
	/**
	 * Creates an IntentService.  Invoked by your subclass's constructor.
	 *
	 * @param name Used to name the worker thread, important only for debugging.
	 */
	public MyShopUpdateService() {
		super("MyShopUpdateService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		JewelChatRequest req = new JewelChatRequest(Request.Method.GET, JewelChatURLS.GETCONTACTBYPHONENUMBERLIST, null, this, this);
		if (NetworkConnectivityStatus.getConnectivityStatus() == NetworkConnectivityStatus.CONNECTED)
			JewelChatApp.getRequestQueue().add(req);

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

				Intent i = getBaseContext().getPackageManager()
						.getLaunchIntentForPackage( getBaseContext().getPackageName());
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);



			}else if(response.statusCode == 500){

			}else{

			}
		}

		JewelChatApp.appLog("GameStateLoad");
		Crashlytics.logException(error);

	}

	@Override
	public void onResponse(JSONObject response) {

	}
}
