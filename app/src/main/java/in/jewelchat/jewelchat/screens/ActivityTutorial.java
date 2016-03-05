package in.jewelchat.jewelchat.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import in.jewelchat.jewelchat.JewelChat;
import in.jewelchat.jewelchat.R;

/**
 * Created by mayukhchakraborty on 29/02/16.
 */
public class ActivityTutorial extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tutorial);

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
