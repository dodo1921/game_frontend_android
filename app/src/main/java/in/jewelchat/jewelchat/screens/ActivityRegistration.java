package in.jewelchat.jewelchat.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import in.jewelchat.jewelchat.R;

/**
 * Created by mayukhchakraborty on 28/02/16.
 */
public class ActivityRegistration extends Activity implements View.OnClickListener {

	private Button btn_continue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);

		btn_continue = (Button)findViewById(R.id.btn_continue);
		btn_continue.setOnClickListener(this);



	}

	@Override
	public void onClick(View v) {

		Intent i = new Intent(ActivityRegistration.this, ActivityVerificationCode.class);
		startActivity(i);
		finish();

	}
}
