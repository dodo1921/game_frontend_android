package in.jewelchat.jewelchat;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.crashlytics.android.Crashlytics;

import org.json.JSONException;
import org.json.JSONObject;

import in.jewelchat.jewelchat.network.JewelChatRequest;
import in.jewelchat.jewelchat.screens.ActivitySplashScreen;
import in.jewelchat.jewelchat.util.NetworkConnectivityStatus;

/**
 * Created by mayukhchakraborty on 06/03/16.
 */
public abstract class BaseNetworkActivity extends BaseActivity implements Response.ErrorListener  {

	private static ProgressDialog progressDialog;
	protected ProgressBar progressBar;
	//TODO handle this properly so that every user initiated action should show toast
	protected boolean isNotConnectedToastShown;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		isNotConnectedToastShown = false;
	}

	@Override
	protected void onDestroy() {
		dismissDialog();
		progressDialog = null;
		super.onDestroy();
	}



	protected void dismissDialog() {
		JewelChatApp.appLog(className + ":dismissDialog");
		//TODO implement this isShowing check for every dialog in system
		if (progressDialog != null && progressDialog.isShowing() ) { //&& !isDestroyed()
			progressDialog.dismiss();
		}
	}

	protected void createDialog(String message) {
		JewelChatApp.appLog(className + ":createDialog");
		//if (isDestroyed())
			//return;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			progressDialog = new ProgressDialog(this, android.R.style.Theme_Material_Light_Dialog_Alert);
		} else {
			progressDialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
		}
		progressDialog.setMessage(message);
		progressDialog.setCancelable(false);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();
	}

	protected boolean addRequest(JewelChatRequest request) {
		JewelChatApp.appLog(className + ":addrequest");
		if (NetworkConnectivityStatus.getConnectivityStatus() == NetworkConnectivityStatus.CONNECTED) {
			isNotConnectedToastShown = false;
			JewelChatApp.getRequestQueue().add(request);
			JewelChatApp.appLog(className + ":added:" + request.toString());
			return true;
		} else {
			dismissDialog();
			if (progressBar != null) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						progressBar.setVisibility(View.GONE);
					}
				});
			}
			if (!isNotConnectedToastShown) {
				isNotConnectedToastShown = true;
				makeToast("Not connected to internet. Switch on your WiFi or Mobile Data");
			}
			return false;
		}
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		//if (isDestroyed())
			//return;
		if (progressBar != null) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					progressBar.setVisibility(View.GONE);
				}
			});
		}
		dismissDialog();

		NetworkResponse response = error.networkResponse;
		if(response != null && response.data != null){
			if(response.statusCode == 403){

				SharedPreferences.Editor editor = JewelChatApp.getSharedPref().edit();
				editor.putBoolean(JewelChatPrefs.IS_LOGGED, false);
				editor.putString(JewelChatPrefs.MY_ID, "");
				editor.commit();

				AlertDialog dialog = new AlertDialog.Builder(getApplicationContext()).create();
				dialog.setTitle("Verification Error");
				dialog.setMessage("Your phone number is registered with another device. Please verify your number.");
				dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(getApplicationContext(), ActivitySplashScreen.class);
						startActivity(intent);
						finish();
					}
				});
				dialog.show();
				return;

			}else if(response.statusCode == 500){

				String json = new String(response.data);
				try{
					JSONObject obj = new JSONObject(json);
					String errorMessage = "Please Try Again. Error 500. "+obj.getString("data");
					makeToast(errorMessage);

				} catch(JSONException e){
					e.printStackTrace();

				}

			}else{

				if (error instanceof TimeoutError || error instanceof NoConnectionError) {
						makeToast("Connection Timeout");
				}else{
						makeToast("Network Error");
					}

			}
		}

		JewelChatApp.appLog(className);
		Crashlytics.logException(error);

	}


}
