package in.jewelchat.jewelchat;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.Crashlytics;
import com.squareup.otto.Bus;
import com.squareup.otto.Produce;
import com.squareup.picasso.Picasso;

import in.jewelchat.jewelchat.models.BasicJewelCountChangedEvent;
import in.jewelchat.jewelchat.network.JewelChatSocket;
import in.jewelchat.jewelchat.util.AnalyticsTrackers;
import io.fabric.sdk.android.Fabric;

/**
 * Created by mayukhchakraborty on 24/02/16.
 */
public class JewelChatApp extends Application {

	public static final int CONNECTION_TIMEOUT = 10000;

	private static JewelChatApp mInstance;
	private static RequestQueue mRequestQueue;
	private static SharedPreferences sharedPref;
	private static String mCookie;
	private static Picasso mPicasso;
	private static JewelChatSocket jcSocket;
	private static final Bus BUS = new Bus();


	public static boolean JewelChat_Active = false;
	

	public static JewelChatApp getInstance() {
		return mInstance;
	}

	public static JewelChatSocket getJCSocket(){

		if( jcSocket == null )
			jcSocket = new JewelChatSocket();

		return jcSocket;
	}

	public static Bus getBusInstance() {
		return BUS;
	}

	public static RequestQueue getRequestQueue() {
		if (mRequestQueue == null)
			setRequestQueue();
		return mRequestQueue;
	}

	private static void setRequestQueue() {

		mRequestQueue = Volley.newRequestQueue(mInstance);
	}

	public static SharedPreferences getSharedPref() {
		if (sharedPref == null) {
			setSharedPref();
		}
		return sharedPref;
	}

	private static void setSharedPref() {
		sharedPref = mInstance.getSharedPreferences("in.mayukh.jewelchat", Context.MODE_PRIVATE);
	}

	public static String getCookie() {
		setCookie();
		if (mCookie.contains("expires")) {
			removeCookie();
			return "";
		}
		return mCookie;
	}

	private static void setCookie() {
		mCookie = sharedPref.getString("cookie", "");
	}

	private static void removeCookie() {
		SharedPreferences.Editor editor = getSharedPref().edit();
		editor.remove("cookie");
		editor.apply();
	}

	public static void saveCookie(String cookie) {
		if (cookie != null) {
			SharedPreferences.Editor editor = getSharedPref().edit();
			editor.putString("cookie", cookie);
			editor.apply();
		}
	}

	public static void appLog(@NonNull String message) {
		Crashlytics.log(Log.VERBOSE, "in.jewelchat.mayukh", Thread.currentThread().getName() + ":" + message);
	}

	private static void setupPicasso() {

		if (mPicasso == null) {
			mPicasso = Picasso.with(getInstance());
			mPicasso.setIndicatorsEnabled(false);
		}

	}

	@Produce
	public static BasicJewelCountChangedEvent produceJewelChangeEvent() {
		// Provide an initial value for location based on the last known position.
		return new BasicJewelCountChangedEvent(
				getSharedPref().getInt("A",0),
				getSharedPref().getInt("B",0),
				getSharedPref().getInt("C",0),
				getSharedPref().getInt("D",0),
				getSharedPref().getInt("Y",0),
				getSharedPref().getInt("Z",0),
				getSharedPref().getInt("LEVEL",0),
				getSharedPref().getInt("XP",0),
				false);
	}


	@Override
	public void onCreate() {
		Fabric.with(this, new Crashlytics());
		appLog("App onCreate");
		super.onCreate();
		mInstance = this;
		//setAppActivity(null);
		Crashlytics.setUserIdentifier(getSharedPref().getString(JewelChatPrefs.getMyPhoneNumber(), ""));
		Crashlytics.setUserName(getSharedPref().getString(JewelChatPrefs.getMyName(), ""));
		AnalyticsTrackers.initialize(this);
		setupPicasso();
		getJCSocket().getSocket().connect();
		
	}
	

	
	@Override
	public void onTerminate() {
		Log.d("App", "Terminate");
		getJCSocket().getSocket().disconnect();
		//setAppActivity(null);
		super.onTerminate();
	}

}
