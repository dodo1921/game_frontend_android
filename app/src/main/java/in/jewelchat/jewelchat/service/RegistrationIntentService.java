package in.jewelchat.jewelchat.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import in.jewelchat.jewelchat.JewelChatApp;
import in.jewelchat.jewelchat.JewelChatPrefs;
import in.jewelchat.jewelchat.JewelChatURLS;
import in.jewelchat.jewelchat.R;
import in.jewelchat.jewelchat.network.JewelChatRequest;
import in.jewelchat.jewelchat.util.AnalyticsTrackers;

/**
 * Created by mayukhchakraborty on 24/02/16.
 */
public class RegistrationIntentService extends IntentService {

	/**
	 * Creates an IntentService.  Invoked by your subclass's constructor.
	 *
	 * @param name Used to name the worker thread, important only for debugging.
	 */



	public RegistrationIntentService() {
		super("RegistrationIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		InstanceID instanceID = InstanceID.getInstance(this);
		try {

			String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
					GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

			Log.i("GCM", token);

			Response.Listener<JSONObject> response = new Response.Listener<JSONObject>() {

				@Override
				public void onResponse(JSONObject response) {

					SharedPreferences.Editor e = JewelChatApp.getSharedPref().edit();
					e.putBoolean(JewelChatPrefs.TOKEN_SENT, true);
					e.commit();

					JewelChatApp.appLog("RegistrationIntentService GCM token updated");

					AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP)
							.send(new HitBuilders.EventBuilder()
									.setCategory("Registration")
									.setAction("Successful GCM token update")
									.setLabel("GCM TOKEN")
									.build());

				}
			};

			Response.ErrorListener response_error = new Response.ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError error) {
					Log.i("Error","Error");
					Crashlytics.logException(error);
				}

			};

			JSONObject jsonparams = new JSONObject();
			jsonparams.put("gcm_token", token);
			JewelChatApp.appLog("RegistrationIntentService sending GCM token");
			JewelChatRequest request = new JewelChatRequest(Request.Method.POST,
					JewelChatURLS.UPDATE_GCM_TOKEN, jsonparams , response ,response_error );

			JewelChatApp.getRequestQueue().add(request);


		} catch (IOException e) {
			e.printStackTrace();
			Crashlytics.logException(e);
		}catch(JSONException e){
			e.printStackTrace();
			Crashlytics.logException(e);
		}

	}
}
