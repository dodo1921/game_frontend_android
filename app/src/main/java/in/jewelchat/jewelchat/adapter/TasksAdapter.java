package in.jewelchat.jewelchat.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import in.jewelchat.jewelchat.JewelChatApp;
import in.jewelchat.jewelchat.JewelChatPrefs;
import in.jewelchat.jewelchat.R;
import in.jewelchat.jewelchat.database.TasksContract;

/**
 * Created by mayukhchakraborty on 21/03/16.
 */
public class TasksAdapter extends CursorAdapter {

	private LayoutInflater cursorInflater;

	public TasksAdapter(Context context) {
		super(context, null, false);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {

		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View retView = inflater.inflate(R.layout.fragment_tasks_element, parent, false);

		return retView;

	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {

		TextView diamond_count = (TextView)view.findViewById(R.id.diamond_count);
		TextView task = (TextView)view.findViewById(R.id.task);
		TextView tnote = (TextView)view.findViewById(R.id.note);
		TextView next_level = (TextView)view.findViewById(R.id.next_level);
		Button claim = (Button)view.findViewById(R.id.claim);

		com.daasuu.ahp.AnimateHorizontalProgressBar pb = (com.daasuu.ahp.AnimateHorizontalProgressBar)view.findViewById(R.id.animate_progress_bar);

		diamond_count.setText(cursor.getInt(cursor.getColumnIndex(TasksContract.DIAMOND_COUNT))+"");

		int type = cursor.getInt(cursor.getColumnIndex(TasksContract.TYPE));
		int value = cursor.getInt(cursor.getColumnIndex(TasksContract.VALUE));

		String code = cursor.getString(cursor.getColumnIndex(TasksContract.TASK_TEXT));

		String note = cursor.getString(cursor.getColumnIndex(TasksContract.TASK_NOTE));

		int level = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.LEVEL, 1);

		if(value>level){
			//disable button   enable levelIndicator
			claim.setEnabled(false);
			claim.setTextColor(ContextCompat.getColor(JewelChatApp.getInstance().getApplicationContext(), R.color.primary_dark_light));
			next_level.setVisibility(View.VISIBLE);
			next_level.setText("Level " + value);
		}else{
			claim.setEnabled(true);
			claim.setTextColor(ContextCompat.getColor(JewelChatApp.getInstance().getApplicationContext(), R.color.white));
			next_level.setVisibility(View.INVISIBLE);
		}


		if(value==1)
			value = 2;
		if (type==1)
			value = value*10;

		String newcode = code.replace("(x)",""+value);

		Spanned spanned = Html.fromHtml(newcode, JewelChatApp.getImageGetter(), null);
		task.setText(spanned);

		if(note.equals(""))
			tnote.setVisibility(View.GONE);
		else {
			tnote.setVisibility(View.VISIBLE);
			tnote.setText(note);
		}

	}
}
