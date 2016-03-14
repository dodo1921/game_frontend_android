package in.jewelchat.jewelchat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by mayukhchakraborty on 06/03/16.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

	protected String className;
	protected ViewGroup rootLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		className = getClass().getSimpleName();
		JewelChatApp.appLog(className + ":onCreate");
		super.onCreate(savedInstanceState);


	}

	@Override
	protected void onStop() {
		JewelChatApp.appLog(className + ":onStop");
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		JewelChatApp.appLog(className + ":onDestroy");
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		JewelChatApp.appLog(className + ":onPause");
		super.onPause();
	}

	@Override
	protected void onResume() {
		JewelChatApp.appLog(className + ":onResume");
		super.onResume();

	}

	@Override
	protected void onStart() {
		JewelChatApp.appLog(className + ":onStart");
		super.onStart();

	}

	protected void initialize() {
	}

	protected void setUpAppbar() {
		JewelChatApp.appLog(className + ":setUpAppbar");

	}

	protected void makeToast(String message) {
		if (getApplicationContext() != null)
			Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}


}
