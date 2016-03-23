package in.jewelchat.jewelchat.service;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by mayukhchakraborty on 23/03/16.
 */
public class GroupUpdateService extends IntentService {
	/**
	 * Creates an IntentService.  Invoked by your subclass's constructor.
	 *
	 * @param name Used to name the worker thread, important only for debugging.
	 */
	public GroupUpdateService() {
		super("GroupUpdateService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

	}
}
