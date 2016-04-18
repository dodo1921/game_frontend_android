package in.jewelchat.jewelchat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

import in.jewelchat.jewelchat.JewelChatApp;
import in.jewelchat.jewelchat.JewelChatPrefs;
import in.jewelchat.jewelchat.R;
import in.jewelchat.jewelchat.models.BasicJewelCountChangedEvent;
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
		final char type = ((GameGridCell)this.getItem(position)).type;
		boolean state = ((GameGridCell)this.getItem(position)).state;

		if(convertView == null){

			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.fragment_game_cell, null);
			holder.imagepost = (ImageView) convertView.findViewById(R.id.game_cell);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}

		holder.imagepost.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				View parentrow = (View)v.getParent();
				GridView gridView = (GridView) parentrow.getParent();
				gridView.performItemClick(v, gridView.getPositionForView(parentrow), 0);


				int A = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.A,0);
				int B = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.B,0);
				int C = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.C,0);
				int D = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.D,0);
				int Y = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.Y,0);
				int Z = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.Z,0);
				int LEVEL = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.LEVEL,0);
				int LEVEL_XP = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.XP_MAX,0);
				int XP = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.XP,0);



				if(type == 'A') {
					//JewelChatApp.getSharedPref().edit().putInt(JewelChatPrefs.A, ++A).commit();
					JewelChatApp.getSharedPref().edit().putInt(JewelChatPrefs.XP, XP+=2).commit();
				}else if(type == 'B') {
					//JewelChatApp.getSharedPref().edit().putInt(JewelChatPrefs.B, ++B).commit();
					JewelChatApp.getSharedPref().edit().putInt(JewelChatPrefs.XP, XP+=2).commit();
				}else if(type == 'C') {
					//JewelChatApp.getSharedPref().edit().putInt(JewelChatPrefs.C, ++C).commit();
					JewelChatApp.getSharedPref().edit().putInt(JewelChatPrefs.XP, XP+=2).commit();
				}else if(type == 'D') {
					//JewelChatApp.getSharedPref().edit().putInt(JewelChatPrefs.D, ++D).commit();
					JewelChatApp.getSharedPref().edit().putInt(JewelChatPrefs.XP, XP+=2).commit();
				}

				JewelChatApp.getBusInstance().post(new BasicJewelCountChangedEvent(A,C,B,D,Y,Z,LEVEL,LEVEL_XP,XP,false));

			}
		});

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
