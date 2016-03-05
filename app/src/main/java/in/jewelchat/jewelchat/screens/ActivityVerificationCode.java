package in.jewelchat.jewelchat.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import in.jewelchat.jewelchat.R;

/**
 * Created by mayukhchakraborty on 29/02/16.
 */
public class ActivityVerificationCode extends Activity implements View.OnClickListener {

	private Button btn_continue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_verification);

		btn_continue = (Button)findViewById(R.id.verify_button);
		btn_continue.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		Intent i = new Intent(ActivityVerificationCode.this, ActivityTutorial.class);
		startActivity(i);
		finish();

	}
}
