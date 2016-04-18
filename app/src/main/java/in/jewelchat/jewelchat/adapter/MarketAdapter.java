package in.jewelchat.jewelchat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import in.jewelchat.jewelchat.R;
import in.jewelchat.jewelchat.models.Factory;
import in.jewelchat.jewelchat.models.MarketItem;

/**
 * Created by mayukhchakraborty on 16/04/16.
 */
public class MarketAdapter extends BaseAdapter {

	private List<MarketItem> marketList;
	private LayoutInflater mInflater = null;
	private Context mContext;

	public MarketAdapter(Context context, List<MarketItem> marketList) {

		this.mContext = context;
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.marketList = marketList;
	}

	@Override
	public int getCount() {

		return this.marketList.size();

	}

	@Override
	public Object getItem(int position) {

		return this.marketList.get(position);

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
			convertView = mInflater.inflate(R.layout.activity_market_element, null);
			holder.jewel = (ImageView) convertView.findViewById(R.id.jewel);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}

		holder.jewel.setImageResource( ((MarketItem) this.getItem(position)).jewel);

		return convertView;
	}

	public static class ViewHolder {

		ImageView jewel;

	}
}

