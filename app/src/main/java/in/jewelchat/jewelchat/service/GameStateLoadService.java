package in.jewelchat.jewelchat.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

import in.jewelchat.jewelchat.JewelChatApp;
import in.jewelchat.jewelchat.JewelChatPrefs;
import in.jewelchat.jewelchat.JewelChatURLS;
import in.jewelchat.jewelchat.models.BasicJewelCountChangedEvent;
import in.jewelchat.jewelchat.models.User;
import in.jewelchat.jewelchat.network.JewelChatRequest;
import in.jewelchat.jewelchat.util.NetworkConnectivityStatus;

/**
 * Created by mayukhchakraborty on 23/03/16.
 */
public class GameStateLoadService extends IntentService implements Response.ErrorListener, Response.Listener<JSONObject> {
	/**
	 * Creates an IntentService.  Invoked by your subclass's constructor.
	 *
	 * @param name Used to name the worker thread, important only for debugging.
	 */
	public GameStateLoadService() {
		super("GameStateLoad");
	}

	@Override
	protected void onHandleIntent(Intent intent) {


		JewelChatRequest req = new JewelChatRequest(Request.Method.GET, JewelChatURLS.GETGAMESTATE, null, this, this);
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

		long currTime = 0;
		try {
				currTime = response.getInt("time");

			long deviceCurrentTime = System.currentTimeMillis();

			Type jtype = new TypeToken<User>(){}.getType();
			Gson gson = new Gson();
			User user = gson.fromJson(response.getString("user").toString(), jtype);

			SharedPreferences.Editor editor = JewelChatApp.getSharedPref().edit();
			editor.putBoolean(JewelChatPrefs.IS_LOGGED,true);
			editor.putInt(JewelChatPrefs.LEVEl, user.level);
			editor.putInt(JewelChatPrefs.XP_MAX, user.xp_max);
			editor.putInt(JewelChatPrefs.XP, user.xp);
			editor.putInt(JewelChatPrefs.STORE_MAX, user.store_max);
			editor.putInt(JewelChatPrefs.A, user.A);
			editor.putInt(JewelChatPrefs.B, user.B);
			editor.putInt(JewelChatPrefs.C, user.C);
			editor.putInt(JewelChatPrefs.D, user.D);
			editor.putInt(JewelChatPrefs.E, user.E);
			editor.putInt(JewelChatPrefs.F, user.F);
			editor.putInt(JewelChatPrefs.G, user.G);
			editor.putInt(JewelChatPrefs.H, user.H);
			editor.putInt(JewelChatPrefs.I, user.I);
			editor.putInt(JewelChatPrefs.J, user.J);
			editor.putInt(JewelChatPrefs.K, user.K);
			editor.putInt(JewelChatPrefs.L, user.L);
			editor.putInt(JewelChatPrefs.M, user.M);
			editor.putInt(JewelChatPrefs.N, user.N);
			editor.putInt(JewelChatPrefs.O, user.O);
			editor.putInt(JewelChatPrefs.P, user.P);
			editor.putInt(JewelChatPrefs.Q, user.Q);
			editor.putInt(JewelChatPrefs.R, user.R);
			editor.putInt(JewelChatPrefs.S, user.S);
			editor.putInt(JewelChatPrefs.T, user.T);
			editor.putInt(JewelChatPrefs.U, user.U);
			editor.putInt(JewelChatPrefs.V, user.V);
			editor.putInt(JewelChatPrefs.W, user.W);
			editor.putInt(JewelChatPrefs.X, user.X);
			editor.putInt(JewelChatPrefs.Y, user.Y);
			editor.putInt(JewelChatPrefs.Z, user.Z);
			editor.putInt(JewelChatPrefs.a, user.a);
			editor.putInt(JewelChatPrefs.b, user.b);
			editor.putInt(JewelChatPrefs.c, user.c);
			editor.putInt(JewelChatPrefs.d, user.d);
			editor.putInt(JewelChatPrefs.e, user.e);
			editor.putInt(JewelChatPrefs.f, user.f);
			editor.putInt(JewelChatPrefs.g, user.g);
			editor.putInt(JewelChatPrefs.h, user.h);
			editor.putLong(JewelChatPrefs.TIME_DIFF, deviceCurrentTime - currTime);

			editor.commit();

			JewelChatApp.getBusInstance().post(new BasicJewelCountChangedEvent(
					user.A,user.B,user.C,user.D,user.Y,user.Z,user.level, user.xp_max, user.xp, false));

		} catch (JSONException e) {
			Crashlytics.logException(e);
			e.printStackTrace();
		}

	}
}
