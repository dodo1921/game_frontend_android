package in.jewelchat.jewelchat.screens;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import in.jewelchat.jewelchat.R;
import in.jewelchat.jewelchat.adapter.GameGridAdapter;

/**
 * Created by mayukhchakraborty on 02/03/16.
 */
public class FragmentGame extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment

		View view = inflater.inflate(R.layout.fragment_game, container, false);
		GridView gridView = (GridView) view.findViewById(R.id.gamegrid);
		gridView.setAdapter(new GameGridAdapter(view.getContext())); // uses the view to get the context instead of getActivity().
		return view;
	}
}
