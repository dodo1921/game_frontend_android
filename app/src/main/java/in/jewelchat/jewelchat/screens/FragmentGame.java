package in.jewelchat.jewelchat.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.List;

import in.jewelchat.jewelchat.JewelChatApp;
import in.jewelchat.jewelchat.JewelChatPrefs;
import in.jewelchat.jewelchat.R;
import in.jewelchat.jewelchat.adapter.GameGridAdapter;
import in.jewelchat.jewelchat.models.GameGridCell;

/**
 * Created by mayukhchakraborty on 02/03/16.
 */
public class FragmentGame extends Fragment {

	private List<GameGridCell> gameGridCellList;

	private Button factory;
	private Button market;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_game, container, false);
		GridView gridView = (GridView) view.findViewById(R.id.gamegrid);


		String board = JewelChatApp.getSharedPref().getString(JewelChatPrefs.BOARD,"");
		String board_state = JewelChatApp.getSharedPref().getString(JewelChatPrefs.BOARD_STATE,"");

		gameGridCellList = new ArrayList<GameGridCell>();

		for(int i=0; i<48; i++){

			GameGridCell t = new GameGridCell();
			t.type = board.charAt(i);
			if(board_state.charAt(i)== '0')
				t.state = false;
			else if(board_state.charAt(i)== '1')
				t.state = true;

			gameGridCellList.add(t);


		}

		gridView.setAdapter(new GameGridAdapter(view.getContext(), gameGridCellList)); // uses the view to get the context instead of getActivity().

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
}
