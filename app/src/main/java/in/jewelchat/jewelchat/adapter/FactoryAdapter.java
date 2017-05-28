package in.jewelchat.jewelchat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import in.jewelchat.jewelchat.R;
import in.jewelchat.jewelchat.models.Factory;

/**
 * Created by mayukhchakraborty on 16/04/16.
 */
public class FactoryAdapter extends BaseAdapter {

	private List<Factory> factoryList;
	private LayoutInflater mInflater = null;
	private Context mContext;

	public FactoryAdapter(Context context, List<Factory> factoryList) {

		this.mContext = context;
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.factoryList = factoryList;
	}

	@Override
	public int getCount() {

		return this.factoryList.size();

	}

	@Override
	public Object getItem(int position) {

		return this.factoryList.get(position);

	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;

		if(convertView == null){

			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.activity_factory_element, null);
			holder.jewel = (ImageView) convertView.findViewById(R.id.jewel);
			holder.ring = (ImageView) convertView.findViewById(R.id.ring);
			holder.start = (Button) convertView.findViewById(R.id.start);
			holder.inprocess = (LinearLayout) convertView.findViewById(R.id.inprocess);
			holder.countdown = (TextView) convertView.findViewById(R.id.countdown);
			holder.stop = (Button) convertView.findViewById(R.id.stop);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}

		holder.jewel.setImageResource( ((Factory) this.getItem(position)).jewel);
		if(((Factory) this.getItem(position)).inprocess){
			holder.start.setVisibility(View.GONE);
			holder.inprocess.setVisibility(View.VISIBLE);
			holder.ring.setVisibility(View.VISIBLE);
			Animation animation = AnimationUtils.loadAnimation(this.mContext, R.anim.ring_rotate);
			holder.ring.startAnimation(animation);
		}else{
			holder.start.setVisibility(View.VISIBLE);
			holder.inprocess.setVisibility(View.GONE);
			holder.ring.setVisibility(View.GONE);
			//Animation animation = AnimationUtils.loadAnimation(this.mContext, R.anim.ring_rotate);
			holder.ring.clearAnimation();
		}

		holder.start.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				View parentrow = (View)v.getParent().getParent();
				ListView listView = (ListView) parentrow.getParent();
				listView.performItemClick(v, listView.getPositionForView(parentrow), 0);

			}
		});


		return convertView;
	}

	public static class ViewHolder {

		ImageView jewel;
		ImageView ring;
		Button start;
		LinearLayout inprocess;
		TextView countdown;
		Button stop;

	}
}

