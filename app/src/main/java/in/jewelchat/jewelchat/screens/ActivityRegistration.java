package in.jewelchat.jewelchat.screens;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.crashlytics.android.Crashlytics;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.jewelchat.jewelchat.BaseNetworkActivity;
import in.jewelchat.jewelchat.JewelChatApp;
import in.jewelchat.jewelchat.JewelChatPrefs;
import in.jewelchat.jewelchat.JewelChatURLS;
import in.jewelchat.jewelchat.R;
import in.jewelchat.jewelchat.network.JewelChatRequest;

/**
 * Created by mayukhchakraborty on 28/02/16.
 */
public class ActivityRegistration extends BaseNetworkActivity implements TextView.OnEditorActionListener, Response.Listener<JSONObject> {

	private EditText enterNumber;
	private EditText enterName;
	private EditText enterReferrer;
	private String phoneNumber;
	private String personName;
	private String requestTag = "mobile";
	private String e164formatNumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		rootLayout = (LinearLayout) findViewById(R.id.mobile_entry_root_layout);
		rootLayout.findViewById(R.id.btn_continue).setOnClickListener(this);
		enterNumber = (EditText) rootLayout.findViewById(R.id.mobile_entry);
		enterNumber.setOnEditorActionListener(this);
		enterName = (EditText) rootLayout.findViewById(R.id.name_entry_verification);
		enterName.setOnEditorActionListener(this);
		enterReferrer = (EditText) rootLayout.findViewById(R.id.referrer_mobile);
		TextView textView = (TextView) rootLayout.findViewById(R.id.terms);
		textView.setClickable(true);
		textView.setMovementMethod(LinkMovementMethod.getInstance());
		String text = "<a href='http://cititalk.in/TermsofService.pdf'> Terms and Conditions </a>";
		textView.setText(Html.fromHtml(text));
		setUpAppbar();

	}

	@Override
	protected void onDestroy() {
		dismissDialog();
		JewelChatApp.getRequestQueue().cancelAll(requestTag);
		super.onDestroy();
	}

	@Override
	protected void setUpAppbar() {


	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.btn_continue)
			action();

	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		return ((actionId == EditorInfo.IME_ACTION_SEND) && action());
	}


	private boolean action() {
		JewelChatApp.appLog(className + ":action");
		phoneNumber = enterNumber.getText().toString().trim();
		personName = enterName.getText().toString().trim();
		String referredBy = enterReferrer.getText().toString().trim();
		Phonenumber.PhoneNumber pn;
		PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
		try {
			pn = phoneNumberUtil.parse(phoneNumber, "IN");
		} catch (NumberParseException e) {
			makeToast(getString(R.string.error_msg_number_length_zero));
			return false;
		}

		e164formatNumber = phoneNumberUtil.format(pn, PhoneNumberUtil.PhoneNumberFormat.E164);
		if (phoneNumber.length() == 10 && personName.length() > 0) {
			createDialog(getString(R.string.please_wait));
			Map<String, String> jsonParams = new HashMap<>();
			jsonParams.put("pno", e164formatNumber);
			JewelChatRequest request = new JewelChatRequest(Request.Method.POST,
					JewelChatURLS.REGISTRATION_URL, new JSONObject(jsonParams), this, this);
			if (addRequest(request)) {
				JewelChatApp.appLog(className + ":action:addRequest");
				try {
					Phonenumber.PhoneNumber referrer = phoneNumberUtil.parse(referredBy, "IN");
					String formatReferrer = phoneNumberUtil.format(referrer, PhoneNumberUtil.PhoneNumberFormat.E164);
					if (formatReferrer != null && formatReferrer.length() > 9) {
						SharedPreferences.Editor editor = JewelChatApp.getSharedPref().edit();
						editor.putLong(JewelChatPrefs.REFERRER, Long.parseLong(formatReferrer.substring(1), 10));
						editor.commit();
					}
				} catch (NumberParseException e) {
					Crashlytics.logException(e);
				}
			}
			return true;
		} else if (phoneNumber.length() == 0 && personName.length() == 0) {
			makeToast(getString(R.string.error_enter_number_name));
			return false;
		} else if (phoneNumber.length() == 0) {
			makeToast(getString(R.string.error_msg_number_length_zero));
			return false;
		} else if (phoneNumber.length() != 10) {
			makeToast(getString(R.string.error_msg_number_length_not_ten));
			return false;
		} else if (personName.length() == 0) {
			makeToast(getString(R.string.enter_name));
			return false;
		} else {
			makeToast(getString(R.string.error_default_entry_app));
			return false;
		}
	}


	@Override
	public void onResponse(JSONObject response) {

		JewelChatApp.appLog(className + ":onResponse");
		try {
			String id = response.getString("id");
			SharedPreferences.Editor editor = JewelChatApp.getSharedPref().edit();
			editor.putLong(JewelChatPrefs.MY_ID, Long.parseLong(id));
			editor.putString(JewelChatPrefs.MY_NAME, personName);
			Log.i("OMG",e164formatNumber.substring(1) );
			editor.putLong(JewelChatPrefs.MY_PHONE_NUMBER, Long.parseLong(e164formatNumber.substring(1), 10));
			editor.commit();
			Crashlytics.setUserIdentifier(e164formatNumber);
			Crashlytics.setUserName(personName);
			Intent intent = new Intent(getApplicationContext(), ActivityVerificationCode.class);
			hideKeyBoard();
			dismissDialog();
			startActivity(intent);
			finish();
		} catch (JSONException e) {
			dismissDialog();
			makeToast("Please try again");
			Crashlytics.logException(e);
		}

	}

	private void hideKeyBoard() {
		try {
			InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			manager.hideSoftInputFromWindow(enterNumber.getWindowToken(), 0);
			manager.hideSoftInputFromWindow(enterName.getWindowToken(), 0);
		} catch (Exception e) {
			JewelChatApp.appLog(getClass().getSimpleName() + ":" + e.toString());
		}
	}


}
