package in.jewelchat.jewelchat.screens;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.List;

import in.jewelchat.jewelchat.JewelChat;
import in.jewelchat.jewelchat.adapter.WalletAdapter;
import in.jewelchat.jewelchat.models.Product;

/**
 * Created by mayukhchakraborty on 02/03/16.
 */
public class FragmentWallet extends Fragment {


	private GridView gv;
	private List<Product> productList;
	private WalletAdapter mListAdapter;

	private static FragmentWallet uniqueInstance;

	/*

	public static FragmentWallet getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new FragmentWallet();
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


		View view = ((JewelChat)getActivity()).getFragmentWalletView( inflater, container, savedInstanceState);
		return view;
		//return null;

	}


	@Override
	public void onDestroy(){
		super.onDestroy();
		Log.i("Fragment>>>", "WALLET Destroyed");

	}



	
}
