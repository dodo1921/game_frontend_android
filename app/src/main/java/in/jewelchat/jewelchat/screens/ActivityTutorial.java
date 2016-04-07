package in.jewelchat.jewelchat.screens;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import in.jewelchat.jewelchat.JewelChat;
import in.jewelchat.jewelchat.R;
import in.jewelchat.jewelchat.database.JewelChatDataProvider;
import in.jewelchat.jewelchat.database.TasksContract;
import in.jewelchat.jewelchat.service.FirstTimeContactDownloadService;

/**
 * Created by mayukhchakraborty on 29/02/16.
 */
public class ActivityTutorial extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tutorial);




		Log.i(">>Task", "Inserted");

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
