package in.jewelchat.jewelchat.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import in.jewelchat.jewelchat.JewelChatApp;
import in.jewelchat.jewelchat.service.GameStateLoadService;
import in.jewelchat.jewelchat.util.NetworkConnectivityStatus;

/**
 * Created by mayukhchakraborty on 24/02/16.
 */
public class NetworkReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {


		if ( NetworkConnectivityStatus.getConnectivityStatus()>0 &&
				!JewelChatApp.getJCSocket().getSocket().connected() &&
				JewelChatApp.JewelChat_Active) {

			JewelChatApp.getJCSocket().getSocket().connect();

			Intent service = new Intent(JewelChatApp.getInstance().getApplicationContext(), GameStateLoadService.class);
			JewelChatApp.getInstance().startService(service);

		}else if(NetworkConnectivityStatus.getConnectivityStatus() == NetworkConnectivityStatus.NOT_CONNECTED){


		}

	}
}
