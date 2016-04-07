package in.jewelchat.jewelchat.screens;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

import in.jewelchat.jewelchat.BaseNetworkActivity;
import in.jewelchat.jewelchat.JewelChatApp;
import in.jewelchat.jewelchat.JewelChatPrefs;
import in.jewelchat.jewelchat.JewelChatURLS;
import in.jewelchat.jewelchat.R;
import in.jewelchat.jewelchat.models.User;
import in.jewelchat.jewelchat.network.JewelChatRequest;
import in.jewelchat.jewelchat.util.AnalyticsTrackers;

/**
 * Created by mayukhchakraborty on 29/02/16.
 */
public class ActivityVerificationCode extends BaseNetworkActivity implements TextView.OnEditorActionListener, Response.Listener<JSONObject>{

	private EditText editText;
	private String verificationCode;
	private String requestTag = "verification";
	private Button submit;
	private TextView resend;
	int resend_count = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_verification);
		className = getClass().getSimpleName();
		rootLayout = (LinearLayout) findViewById(R.id.verify_root);
		submit = (Button) rootLayout.findViewById(R.id.verify_button);
		submit.setOnClickListener(this);
		editText = (EditText) rootLayout.findViewById(R.id.verification_code);
		editText.setOnEditorActionListener(this);
		resend = (TextView) rootLayout.findViewById(R.id.resend_code);
		resend.setOnClickListener(this);

	}

	@Override
	protected void setUpAppbar() {

	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.verify_button) {
			action();
			return;
		}
		if(v.getId() == R.id.resend_code){
			resendCode();
			return;
		}


	}

	@Override
	public void onResponse(JSONObject response) {
		JewelChatApp.appLog(className + ":onResponse");

		try {
			String request = response.getString("request");
			if(request.equals("verifyCode")){

				long currTime = response.getInt("time");
				long deviceCurrentTime = System.currentTimeMillis();

				Type jtype = new TypeToken<User>(){}.getType();
				Gson gson = new Gson();
				User user = gson.fromJson(response.getString("user").toString(), jtype);

				SharedPreferences.Editor editor = JewelChatApp.getSharedPref().edit();
				editor.putBoolean(JewelChatPrefs.IS_LOGGED,true);
				editor.putInt(JewelChatPrefs.LEVEL, user.level);
				editor.putInt(JewelChatPrefs.XP_MAX, user.xp_max);
				editor.putInt(JewelChatPrefs.XP, user.xp);
				editor.putInt(JewelChatPrefs.STORE_MAX, user.store_max);
				editor.putInt(JewelChatPrefs.A, user.A);
				editor.putInt(JewelChatPrefs.B, user.B);
				editor.putInt(JewelChatPrefs.C, user.C);
				editor.putInt(JewelChatPrefs.D, user.D);
				editor.putInt(JewelChatPrefs.E, user.E);
				editor.putInt(JewelChatPrefs.F, user.F);
				editor.putInt(JewelChatPrefs.G, user.G);
				editor.putInt(JewelChatPrefs.H, user.H);
				editor.putInt(JewelChatPrefs.I, user.I);
				editor.putInt(JewelChatPrefs.J, user.J);
				editor.putInt(JewelChatPrefs.K, user.K);
				editor.putInt(JewelChatPrefs.L, user.L);
				editor.putInt(JewelChatPrefs.M, user.M);
				editor.putInt(JewelChatPrefs.N, user.N);
				editor.putInt(JewelChatPrefs.O, user.O);
				editor.putInt(JewelChatPrefs.P, user.P);
				editor.putInt(JewelChatPrefs.Q, user.Q);
				editor.putInt(JewelChatPrefs.R, user.R);
				editor.putInt(JewelChatPrefs.S, user.S);
				editor.putInt(JewelChatPrefs.T, user.T);
				editor.putInt(JewelChatPrefs.U, user.U);
				editor.putInt(JewelChatPrefs.V, user.V);
				editor.putInt(JewelChatPrefs.W, user.W);
				editor.putInt(JewelChatPrefs.X, user.X);
				editor.putInt(JewelChatPrefs.Y, user.Y);
				editor.putInt(JewelChatPrefs.Z, user.Z);
				editor.putInt(JewelChatPrefs.a, user.a);
				editor.putInt(JewelChatPrefs.b, user.b);
				editor.putInt(JewelChatPrefs.c, user.c);
				editor.putInt(JewelChatPrefs.d, user.d);
				editor.putInt(JewelChatPrefs.e, user.e);
				editor.putInt(JewelChatPrefs.f, user.f);
				editor.putInt(JewelChatPrefs.g, user.g);
				editor.putInt(JewelChatPrefs.h, user.h);
				editor.putString(JewelChatPrefs.BOARD, user.board);
				editor.putString(JewelChatPrefs.BOARD_STATE, user.board_state);
				editor.putInt(JewelChatPrefs.GAME_IMAGE, user.game_image);
				editor.putLong(JewelChatPrefs.TIME_DIFF, deviceCurrentTime - currTime);

				editor.commit();

				//JewelChatApp.getBusInstance().post(JewelChatApp.produceJewelChangeEvent());

				hideKeyBoard();
				dismissDialog();

				AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP)
						.send(new HitBuilders.EventBuilder()
								.setCategory("Registration")
								.setAction("Successful Verification Code Submit ")
								.setLabel("Registration Success")
								.build());

				Intent intent = new Intent(getApplicationContext(), ActivityTutorial.class);
				startActivity(intent);
				finish();



			}else if(request.equals("resendVCODE")){
				dismissDialog();
				if(resend_count>=3){
					return;
				}else if(resend_count==1){
					resend_count++;
					resend.setText("Resend one more time");
				}else if(resend_count==2){
					resend_count++;
					resend.setText("");
					resend.setClickable(false);
				}

				AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP)
						.send(new HitBuilders.EventBuilder()
								.setCategory("Registration")
								.setAction("Code Resend")
								.setLabel("VerificationCode Resend Success")
								.build());

			}


		} catch (JSONException e) {
			Crashlytics.logException(e);
			e.printStackTrace();
			hideKeyBoard();
			dismissDialog();
		}
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		return ((actionId == EditorInfo.IME_ACTION_SEND) && action());
	}

	private boolean action() {

		JewelChatApp.appLog(className + ":action");
		verificationCode = this.editText.getText().toString().trim();

		if(verificationCode.length() == 0){
			makeToast("Please enter verification code");
			return false;
		}if(verificationCode.length() <6){
			return false;
		}else if(verificationCode.length() == 6){

			createDialog(getString(R.string.please_wait));

			JSONObject jsonParams = new JSONObject();
			try {

				jsonParams.put("userId", JewelChatApp.getSharedPref().getLong(JewelChatPrefs.MY_ID,0L));
				jsonParams.put("verificationCode", Integer.parseInt(verificationCode));
				jsonParams.put("name", JewelChatApp.getSharedPref().getString(JewelChatPrefs.MY_NAME, ""));
				if(JewelChatApp.getSharedPref().getLong(JewelChatPrefs.REFERRER, 0L) > 0)
					jsonParams.put("referrer", JewelChatApp.getSharedPref().getLong(JewelChatPrefs.REFERRER,0L));

			} catch (JSONException e) {
				e.printStackTrace();
				Crashlytics.logException(e);
			}


			JewelChatRequest request = new JewelChatRequest(Request.Method.POST,
					JewelChatURLS.VERIFICATIONCODE_URL, jsonParams, this, this);

			if (addRequest(request)) {
				return true;
			}else
				return false;

		}

		return false;

	}

	private void resendCode() {


		createDialog(getString(R.string.please_wait));

		JSONObject jsonParams = new JSONObject();
		try {

			jsonParams.put("userId", JewelChatApp.getSharedPref().getLong(JewelChatPrefs.MY_ID, 0L));


		} catch (JSONException e) {
			Crashlytics.logException(e);
			e.printStackTrace();
		}


		JewelChatRequest request = new JewelChatRequest(Request.Method.POST,
				JewelChatURLS.RESENDVCODE_URL, jsonParams, this, this);

		addRequest(request);

	}

	private void hideKeyBoard() {
		try {
			InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			manager.hideSoftInputFromWindow(editText.getWindowToken(), 0);

		} catch (Exception e) {
			JewelChatApp.appLog(getClass().getSimpleName() + ":" + e.toString());
		}
	}
}
