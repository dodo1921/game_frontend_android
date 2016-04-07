package in.jewelchat.jewelchat.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import in.jewelchat.jewelchat.JewelChat;
import in.jewelchat.jewelchat.JewelChatApp;
import in.jewelchat.jewelchat.JewelChatPrefs;
import in.jewelchat.jewelchat.R;

/**
 * Created by mayukhchakraborty on 02/03/16.
 */
public class FragmentGame extends Fragment {

	private Button factory;
	private Button market;

	private static FragmentGame uniqueInstance;

	/*

	public static FragmentGame getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new FragmentGame();
		return uniqueInstance;
	}

	*/

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {


		View view = ((JewelChat)getActivity()).getFragmentGameView( inflater, container, savedInstanceState);

		GridView gridView = (GridView) view.findViewById(R.id.gamegrid);

		gridView.setAdapter(((JewelChat)getActivity()).getGameGridAdapter()); // uses the view to get the context instead of getActivity().

		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				String board = JewelChatApp.getSharedPref().getString(JewelChatPrefs.BOARD, "");
				String board_state = JewelChatApp.getSharedPref().getString(JewelChatPrefs.BOARD_STATE, "000000000000000000000000000000000000000000000000");
				if (!board.equals("")) {

					char element = board.charAt(position);
					char state = board_state.charAt(position);

					if (element == 'A' && state == '0')
						((ImageView) view).setImageResource(R.drawable.ic_ac);
					else if (element == 'B' && state == '0')
						((ImageView) view).setImageResource(R.drawable.ic_bc);

				}

			}
		});

		factory = (Button)view.findViewById(R.id.factory);
		factory.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent i = new Intent(getActivity(), ActivityFactory.class);
				startActivity(i);


			}
		});

		market = (Button)view.findViewById(R.id.market);
		market.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent i = new Intent(getActivity(), ActivityMarket.class);
				startActivity(i);

			}
		});

		return view;

	}

	@Override
	public void onDestroy(){
		super.onDestroy();
		Log.i("Fragment>>>","GAME Destroyed");

	}


}
