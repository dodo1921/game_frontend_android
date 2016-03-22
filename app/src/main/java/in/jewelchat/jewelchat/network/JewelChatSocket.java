package in.jewelchat.jewelchat.network;

import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import in.jewelchat.jewelchat.JewelChatApp;
import in.jewelchat.jewelchat.JewelChatPrefs;
import in.jewelchat.jewelchat.service.LooperThread;
import io.socket.client.Ack;
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

	private Object msg;
	private LooperThread mLooper;
	private Messenger mMessenger;

	public JewelChatSocket(){

		IO.Options opts = new IO.Options();
		opts.forceNew = false;
		opts.reconnection = true;
		opts.reconnectionAttempts = 5;

		try {

			this.socket = IO.socket("http://192.168.0.103:8081", opts);

		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		mLooper = new LooperThread();
		mLooper.start();
		mMessenger = new Messenger(mLooper.getHandler());

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
						Log.i("SOCKET COOKIE SET", "Cookie");
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

				Log.i("Socket","Connect");

			}

		}).on( Socket.EVENT_CONNECTING, new Emitter.Listener() {

			@Override
			public void call(Object... args) {

			}

		}).on( JewelChatApp.getSharedPref().getString(JewelChatPrefs.CHANNEL,"" ), new Emitter.Listener() {

			@Override
			public void call(Object... args) {

				Message msg = Message.obtain(null, 0, args[0]);
				try {
					mMessenger.send(msg);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


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

		}).on(Socket.EVENT_ERROR, new Emitter.Listener() {

			@Override
			public void call(Object... args) {

			}

		});

	}


	public void emitEventOneToOneMessage( String eventName, Object obj ){

		this.socket.emit(eventName, obj, new Ack() {
			@Override
			public void call(Object... args) {

				//Process aargs and then send

				Message msg = Message.obtain(null, 0, args[0]);
				try {
					mMessenger.send(msg);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//send the returned object to loopback thread for processing

			}

		});

	}

	public void emitEventGroupMessage( String eventName, Object obj ){

		this.socket.emit(eventName, obj, new Ack() {
			@Override
			public void call(Object... args) {

				//send the returned object to loopback thread for processing

			}

		});

	}

	public void emitEventDeliveredMsg( String eventName, Object obj ){

		this.socket.emit(eventName, obj, new Ack() {
			@Override
			public void call(Object... args) {

				//send the returned object to loopback thread for processing

			}

		});

	}

	public void emitEventReadMsg( String eventName, Object obj ){

		this.socket.emit(eventName, obj, new Ack() {
			@Override
			public void call(Object... args) {

				//send the returned object to loopback thread for processing

			}

		});

	}

	public void emitEventPresenceMsg( String eventName, Object obj ){

		this.socket.emit(eventName, obj, new Ack() {
			@Override
			public void call(Object... args) {

				//send the returned object to loopback thread for processing

			}

		});

	}

	public void emitEventTypingMsg( String eventName, Object obj ){

		this.socket.emit(eventName, obj, new Ack() {
			@Override
			public void call(Object... args) {

				//send the returned object to loopback thread for processing

			}

		});

	}


	public void emitEventHistoryMsg( String eventName, Object obj ){

		this.socket.emit(eventName, obj, new Ack() {
			@Override
			public void call(Object... args) {

				//send the returned object to loopback thread for processing

			}

		});

	}



	public Socket getSocket(){

		return this.socket;

	}





}
