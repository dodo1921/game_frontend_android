package in.jewelchat.jewelchat.network;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import in.jewelchat.jewelchat.JewelChatApp;
import in.jewelchat.jewelchat.JewelChatPrefs;
import io.socket.client.IO;
import io.socket.client.Manager;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.Transport;

/**
 * Created by mayukhchakraborty on 28/02/16.
 */
public class JewelChatSocket {

	private Socket socket;

	public JewelChatSocket(){

		IO.Options opts = new IO.Options();
		opts.forceNew = false;
		opts.reconnection = true;
		opts.reconnectionAttempts = 5;

		try {

			this.socket = IO.socket("", opts);

		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		initialize();

	}

	private void initialize() {

		socket.io().on(Manager.EVENT_TRANSPORT, new Emitter.Listener() {

			public void call(Object... args) {
				Transport transport = (Transport)args[0];

				transport.on(Transport.EVENT_REQUEST_HEADERS, new Emitter.Listener() {
					@Override
					public void call(Object... args) {
						@SuppressWarnings("unchecked")
						Map<String, List<String>> headers = (Map<String, List<String>>)args[0];
						// modify request headers
						headers.put("Cookie", Arrays.asList(JewelChatApp.getCookie()));
					}
				});

				/*transport.on(Transport.EVENT_RESPONSE_HEADERS, new Emitter.Listener() {
					@Override
					public void call(Object... args) {
						@SuppressWarnings("unchecked")
						Map<String, List<String>> headers = (Map<String, List<String>>)args[0];
						// access response headers
						String cookie = headers.get("Set-Cookie").get(0);
					}
				});*/
			}
		});

		socket.on( Socket.EVENT_CONNECT, new Emitter.Listener() {

			@Override
			public void call(Object... args) {

			}

		}).on( Socket.EVENT_CONNECTING, new Emitter.Listener() {

			@Override
			public void call(Object... args) {

			}

		}).on( JewelChatApp.getSharedPref().getString(JewelChatPrefs.CHANNEL,"" ), new Emitter.Listener() {

			@Override
			public void call(Object... args) {

			}

		}).on( Socket.EVENT_DISCONNECT, new Emitter.Listener() {

			@Override
			public void call(Object... args) {

			}

		}).on(Socket.EVENT_RECONNECT, new Emitter.Listener() {

			@Override
			public void call(Object... args) {

			}

		}).on( Socket.EVENT_RECONNECTING, new Emitter.Listener() {

			@Override
			public void call(Object... args) {

			}

		}).on( Socket.EVENT_ERROR, new Emitter.Listener() {

			@Override
			public void call(Object... args) {

			}

		});

	}


	public Socket getSocket(){

		return this.socket;

	}





}
