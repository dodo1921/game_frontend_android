package in.jewelchat.jewelchat.network;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.jewelchat.jewelchat.JewelChatApp;

/**
 * Created by mayukhchakraborty on 24/02/16.
 */
public class JewelChatRequest extends JsonObjectRequest {

	public JewelChatRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener,
	                       Response.ErrorListener errorListener) {
		super(method, url, jsonRequest, listener, errorListener);
		this.setTag("JewelChat");
		RetryPolicy policy = new RetryPolicy() {
			@Override
			public int getCurrentTimeout() {
				return JewelChatApp.CONNECTION_TIMEOUT;
			}

			@Override
			public int getCurrentRetryCount() {
				return 0;
			}

			@Override
			public void retry(VolleyError error) throws VolleyError {
				throw error;
			}
		};
		this.setRetryPolicy(policy);
	}

	@Override
	protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
		try {
			Map<String, String> headers = response.headers;
			String cookie = headers.get("Set-Cookie");
			JewelChatApp.saveCookie(cookie);
		}catch (OutOfMemoryError e){
			//JewelChatApp.appLog(getClass().getSimpleName() + ":" + e.toString());
			//Crashlytics.logException(e);
		}
		return super.parseNetworkResponse(response);
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		Map<String, String> headers = new HashMap<>();
		try {
			if (!JewelChatApp.getCookie().equals("")) {
				headers.put("Cookie", JewelChatApp.getCookie());
			}
		} catch (Exception e) {
			//JewelChatApp.appLog(getClass().getSimpleName() + ":" + e.toString());
		}
		return headers;
	}
}
