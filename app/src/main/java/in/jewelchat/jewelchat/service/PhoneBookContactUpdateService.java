package in.jewelchat.jewelchat.service;

import android.app.IntentService;
import android.content.Intent;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by mayukhchakraborty on 23/03/16.
 */
public class PhoneBookContactUpdateService extends IntentService {
	/**
	 * Creates an IntentService.  Invoked by your subclass's constructor.
	 *
	 * @param name Used to name the worker thread, important only for debugging.
	 */
	public PhoneBookContactUpdateService() {
		super("PhoneBookContactUpdateService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {



	}



}
