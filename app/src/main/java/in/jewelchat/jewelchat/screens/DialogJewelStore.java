package in.jewelchat.jewelchat.screens;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import in.jewelchat.jewelchat.JewelChatApp;
import in.jewelchat.jewelchat.JewelChatPrefs;
import in.jewelchat.jewelchat.R;

/**
 * Created by mayukhchakraborty on 30/03/16.
 */
public class DialogJewelStore extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// Get the layout inflater
		LayoutInflater inflater = getActivity().getLayoutInflater();

		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout

		View view = inflater.inflate(R.layout.dialog_jewel_store, null);

		initialise(view);

		builder.setView(view);

		return builder.create();
	}

	private void initialise(View view) {

		int Ac = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.A, 0);
		((TextView)view.findViewById(R.id.A)).setText(Ac+"");

		int Bc = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.B, 0);
		((TextView)view.findViewById(R.id.B)).setText(Bc+"");

		int Cc = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.C, 0);
		((TextView)view.findViewById(R.id.C)).setText(Cc+"");

		int Dc = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.D, 0);
		((TextView)view.findViewById(R.id.D)).setText(Dc+"");

		int Ec = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.E, 0);
		((TextView)view.findViewById(R.id.E)).setText(Ec+"");

		int Fc = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.F, 0);
		((TextView)view.findViewById(R.id.F)).setText(Fc+"");

		int Gc = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.G, 0);
		((TextView)view.findViewById(R.id.G)).setText(Gc+"");

		int Hc = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.H, 0);
		((TextView)view.findViewById(R.id.H)).setText(Hc+"");

		int Ic = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.I, 0);
		((TextView)view.findViewById(R.id.I)).setText(Ic+"");

		int Jc = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.J, 0);
		((TextView)view.findViewById(R.id.J)).setText(Jc+"");

		int Kc = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.K, 0);
		((TextView)view.findViewById(R.id.K)).setText(Kc+"");

		int Lc = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.L, 0);
		((TextView)view.findViewById(R.id.L)).setText(Lc+"");

		int Mc = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.M, 0);
		((TextView)view.findViewById(R.id.M)).setText(Mc+"");

		int Nc = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.N, 0);
		((TextView)view.findViewById(R.id.N)).setText(Nc+"");

		int Oc = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.O, 0);
		((TextView)view.findViewById(R.id.O)).setText(Oc+"");

		int Pc = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.P, 0);
		((TextView)view.findViewById(R.id.P)).setText(Pc+"");

		int Qc = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.Q, 0);
		((TextView)view.findViewById(R.id.Q)).setText(Qc+"");

		int Rc = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.R, 0);
		((TextView)view.findViewById(R.id.R)).setText(Rc+"");

		int Sc = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.S, 0);
		((TextView)view.findViewById(R.id.S)).setText(Sc+"");

		int Tc = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.T, 0);
		((TextView)view.findViewById(R.id.T)).setText(Tc+"");

		int Uc = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.U, 0);
		((TextView)view.findViewById(R.id.U)).setText(Uc+"");

		int Vc = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.V, 0);
		((TextView)view.findViewById(R.id.V)).setText(Vc+"");

		int Wc = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.W, 0);
		((TextView)view.findViewById(R.id.W)).setText(Wc+"");

		int Xc = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.X, 0);
		((TextView)view.findViewById(R.id.X)).setText(Xc+"");

		int ac = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.a, 0);
		((TextView)view.findViewById(R.id.a)).setText(ac+"");

		int bc = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.b, 0);
		((TextView)view.findViewById(R.id.b)).setText(bc+"");

		int cc = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.c, 0);
		((TextView)view.findViewById(R.id.c)).setText(cc+"");

		int dc = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.d, 0);
		((TextView)view.findViewById(R.id.d)).setText(dc+"");

		int ec = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.e, 0);
		((TextView)view.findViewById(R.id.e)).setText(ec+"");

		int fc = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.f, 0);
		((TextView)view.findViewById(R.id.f)).setText(fc+"");

		int gc = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.g, 0);
		((TextView)view.findViewById(R.id.g)).setText(gc+"");

		int hc = JewelChatApp.getSharedPref().getInt(JewelChatPrefs.h, 0);
		((TextView)view.findViewById(R.id.h)).setText(hc+"");

		int sum = Ac+Bc+Cc+Dc+Ec+Fc+Gc+Hc+Ic+Jc+Kc+Lc+Mc+Nc+Oc+Pc+Qc+Rc+Sc+Tc+Uc+Vc+Wc+Xc+ac+bc+cc+dc+ec+fc+gc+hc;

		((TextView)view.findViewById(R.id.store_max)).setText(JewelChatApp.getSharedPref().getInt(JewelChatPrefs.STORE_MAX,25)+"");

		com.daasuu.ahp.AnimateHorizontalProgressBar pb = (com.daasuu.ahp.AnimateHorizontalProgressBar)view.findViewById(R.id.animate_progress_bar);

		pb.setMax(JewelChatApp.getSharedPref().getInt(JewelChatPrefs.STORE_MAX, 25));
		pb.setProgress(sum);

	}


}
