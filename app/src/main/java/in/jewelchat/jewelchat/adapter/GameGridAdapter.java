package in.jewelchat.jewelchat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import in.jewelchat.jewelchat.R;
import in.jewelchat.jewelchat.models.GameGridCell;

/**
 * Created by mayukhchakraborty on 03/03/16.
 */
public class GameGridAdapter extends BaseAdapter {

	private List<GameGridCell> gameGridCellList;
	private LayoutInflater mInflater = null;
	private Context mContext;

	public GameGridAdapter(Context context, List<GameGridCell> gameGridCellList) {

		this.mContext = context;
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.gameGridCellList = gameGridCellList;
	}
	
	@Override
	public int getCount() {

		return this.gameGridCellList.size();

	}

	@Override
	public Object getItem(int position) {

		return this.gameGridCellList.get(position);

	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		char type = ((GameGridCell)this.getItem(position)).type;
		boolean state = ((GameGridCell)this.getItem(position)).state;

		if(convertView == null){

			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.fragment_game_cell, null);
			holder.imagepost = (ImageView) convertView.findViewById(R.id.game_cell);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}

		if(type == 'A'){
			if(state)
				holder.imagepost.setImageResource(R.drawable.ic_ac);
			else
				holder.imagepost.setImageResource(R.drawable.ic_ab);

		}else if(type == 'B'){
			if(state)
				holder.imagepost.setImageResource(R.drawable.ic_bc);
			else
				holder.imagepost.setImageResource(R.drawable.ic_bb);

		}else if(type == 'C'){
			if(state)
				holder.imagepost.setImageResource(R.drawable.ic_cc);
			else
				holder.imagepost.setImageResource(R.drawable.ic_cb);

		}else if(type == 'D'){
			if(state)
				holder.imagepost.setImageResource(R.drawable.ic_dc);
			else
				holder.imagepost.setImageResource(R.drawable.ic_db);

		}




		return convertView;
	}

	public static class ViewHolder {
		public ImageView imagepost;
	}
}
