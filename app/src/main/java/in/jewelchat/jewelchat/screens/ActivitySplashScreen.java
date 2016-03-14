package in.jewelchat.jewelchat.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import in.jewelchat.jewelchat.JewelChat;
import in.jewelchat.jewelchat.JewelChatApp;
import in.jewelchat.jewelchat.JewelChatPrefs;
import in.jewelchat.jewelchat.R;

/**
 * Created by mayukhchakraborty on 28/02/16.
 */
public class ActivitySplashScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splashscreen);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {

				if (JewelChatApp.getSharedPref().getBoolean(JewelChatPrefs.IS_LOGGED, false)) {

					Intent i = new Intent(ActivitySplashScreen.this, JewelChat.class);
					startActivity(i);
					finish();

				} else {

					Intent i = new Intent(ActivitySplashScreen.this, ActivityRegistration.class);
					startActivity(i);
					finish();

				}
			}

		}, 1000);
	}
}
