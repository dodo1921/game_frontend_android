package in.jewelchat.jewelchat;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import in.jewelchat.jewelchat.screens.DialogJewelStore;
import in.jewelchat.jewelchat.screens.DialogJewelStoreFull;
import in.jewelchat.jewelchat.screens.DialogNoInternet;

/**
 * Created by mayukhchakraborty on 06/03/16.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

	protected String className;

	protected AppBarLayout appbarRoot;

	protected TextView A,B,C,D,LEVEL,LEVEL_SCORE;
	protected ProgressBar XP;

	protected RelativeLayout jewel_store_button;

	protected DialogJewelStore jewelStoreDialog;
	protected DialogNoInternet noInternetDialog;

	protected DialogJewelStoreFull jewelStoreFull;

	protected ViewGroup rootLayout;



	public void jewelStoreFull(){

		Snackbar snackbar = Snackbar
				.make(rootLayout, "Jewel Store full", Snackbar.LENGTH_LONG);

		snackbar.show();

	}

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

	protected void showJewelStoreFullDialog(){

		jewelStoreFull = new DialogJewelStoreFull();
		jewelStoreFull.show(getFragmentManager(), "Jewel Store Full");
		jewelStoreFull.setCancelable(true);
	}

	protected void showNoInternetDialog() {

		noInternetDialog = new DialogNoInternet();
		noInternetDialog.show(getFragmentManager(), "No Internet");
		noInternetDialog.setCancelable(true);

	}



	protected void setUpAppbar() {
		JewelChatApp.appLog(className + ":setUpAppbar");
		appbarRoot = (AppBarLayout)findViewById(R.id.appbar);

		jewel_store_button = (RelativeLayout)appbarRoot.findViewById(R.id.jewel_store);

		A = (TextView)appbarRoot.findViewById(R.id.A_appbar);
		B = (TextView)appbarRoot.findViewById(R.id.B_appbar);
		C = (TextView)appbarRoot.findViewById(R.id.C_appbar);
		D = (TextView)appbarRoot.findViewById(R.id.D_appbar);
		LEVEL =  (TextView)appbarRoot.findViewById(R.id.level_appbar);
		XP = (ProgressBar)appbarRoot.findViewById(R.id.xpbar);
		LEVEL_SCORE = (TextView)appbarRoot.findViewById(R.id.xpbar_value);

		Toolbar toolbar = (Toolbar) appbarRoot.findViewById(R.id.jewelchat_toolbar);
		setSupportActionBar(toolbar);

		jewel_store_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				jewelStoreDialog = new DialogJewelStore();
				jewelStoreDialog.show( getFragmentManager(), "Jewel Store");
				jewelStoreDialog.setCancelable(true);

			}
		});

	}

	protected void makeToast(String message) {
		if (getApplicationContext() != null)
			Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}


}
