package in.jewelchat.jewelchat.service;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by mayukhchakraborty on 23/03/16.
 */
public class GameStateLoadService extends IntentService {
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

	}
}
