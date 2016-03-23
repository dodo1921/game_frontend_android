package in.jewelchat.jewelchat.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import in.jewelchat.jewelchat.JewelChat;
import in.jewelchat.jewelchat.R;
import in.jewelchat.jewelchat.service.FirstTimeContactDownloadService;
import in.jewelchat.jewelchat.service.RegistrationIntentService;

/**
 * Created by mayukhchakraborty on 29/02/16.
 */
public class ActivityTutorial extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tutorial);

		Intent service = new Intent(getApplicationContext(), FirstTimeContactDownloadService.class);
		startService(service);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {

				Intent i = new Intent(ActivityTutorial.this, JewelChat.class);
				startActivity(i);
				finish();


			}

		}, 2000);

	}



}
