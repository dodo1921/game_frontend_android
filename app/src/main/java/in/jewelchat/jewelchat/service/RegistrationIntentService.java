package in.jewelchat.jewelchat.service;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by mayukhchakraborty on 24/02/16.
 */
public class RegistrationIntentService extends IntentService {

	/**
	 * Creates an IntentService.  Invoked by your subclass's constructor.
	 *
	 * @param name Used to name the worker thread, important only for debugging.
	 */



	public RegistrationIntentService(String name) {
		super(name);
	}

	@Override
	protected void onHandleIntent(Intent intent) {

	}
}
