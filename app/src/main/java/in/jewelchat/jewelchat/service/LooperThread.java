package in.jewelchat.jewelchat.service;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * Created by mayukhchakraborty on 18/03/16.
 */
public class LooperThread extends Thread {
	private Handler mHandler;
	private Looper mLooper;
	private String msgchannel;
	private String groupchannel;

	private static final String TAG = "LooperThread";



	public Looper getLooper(){
		return mLooper;
	}

	public Handler getHandler(){
		synchronized(this){
			while (mHandler == null){
				try{
					wait();
				}catch(InterruptedException e){}
			}
		}
		return mHandler;
	}

	static class MyHandler extends Handler{
		public void handleMessage(Message msg){

				//CityTalkServiceHelper.interPersonalMessage(message, CityTalk.getInstance().getContentResolver());

		}
	}


	public void run(){
		Looper.prepare();
		synchronized(this){
			mHandler = new LooperThread.MyHandler();
			mLooper = Looper.myLooper();
			notifyAll();
		}
		Looper.loop();

	}
}
