package in.jewelchat.jewelchat.util;

import android.os.Handler;

import in.jewelchat.jewelchat.JewelChatApp;

/**
 * Created by mayukhchakraborty on 06/03/16.
 */
public class PresenceRunnable implements Runnable {

	@Override
	public void run() {

		//TODO update contactPresence HashMap
		//emit presence packets to each contact

		JewelChatApp.getBusInstance().post(JewelChatApp.getContactPresenceHashMap());
		new Handler().postDelayed(new PresenceRunnable(), 1000);

	}
}
