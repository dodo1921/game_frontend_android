package in.jewelchat.jewelchat.util;

import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.util.HashMap;
import java.util.Map;

import in.jewelchat.jewelchat.JewelChatApp;
import in.jewelchat.jewelchat.JewelChatPrefs;
import in.jewelchat.jewelchat.R;

/**
 * Created by mayukhchakraborty on 24/02/16.
 */
public final class AnalyticsTrackers {

	private static AnalyticsTrackers sInstance;
	private final Map<Target, Tracker> mTrackers = new HashMap<Target, Tracker>();
	private final Context mContext;

	/**
	 * Don't instantiate directly - use {@link #getInstance()} instead.
	 */
	private AnalyticsTrackers(Context context) {
		mContext = context.getApplicationContext();
	}

	public static synchronized void initialize(Context context) {
		if (sInstance != null) {
			throw new IllegalStateException("Extra call to initialize analytics trackers");
		}

		sInstance = new AnalyticsTrackers(context);
	}

	public static synchronized AnalyticsTrackers getInstance() {
		if (sInstance == null) {
			throw new IllegalStateException("Call initialize() before getInstance()");
		}

		return sInstance;
	}

	public synchronized Tracker get(Target target) {
		if (!mTrackers.containsKey(target)) {
			Tracker tracker;
			switch (target) {
				case APP:
					tracker = GoogleAnalytics.getInstance(mContext).newTracker(R.xml.app_tracker);
					tracker.enableAdvertisingIdCollection(true);
					tracker.set("&uid",
							JewelChatApp.getSharedPref().getLong(JewelChatPrefs.MY_ID,0L)+"");
					break;
				default:
					throw new IllegalArgumentException("Unhandled analytics target " + target);
			}
			mTrackers.put(target, tracker);
		}

		return mTrackers.get(target);
	}

	public enum Target {
		APP,
		// Add more trackers here if you need, and update the code in #get(Target) below
	}
}
