package in.jewelchat.jewelchat.util;

import android.content.SharedPreferences;

import com.squareup.otto.Produce;

import in.jewelchat.jewelchat.JewelChatApp;
import in.jewelchat.jewelchat.models.BasicJewelCountChangedEvent;

/**
 * Created by mayukhchakraborty on 04/03/16.
 */
public class JewelStore {

	public static final int A = 1;
	public static final int B = 2;
	public static final int C = 3;
	public static final int D = 4;
	public static final int E = 5;
	public static final int F = 6;
	public static final int G = 7;
	public static final int H = 8;
	public static final int I = 9;
	public static final int J = 10;
	public static final int K = 11;
	public static final int L = 12;
	public static final int M = 13;
	public static final int N = 14;
	public static final int O = 15;
	public static final int P = 16;
	public static final int Q = 17;
	public static final int R = 18;
	public static final int S = 19;
	public static final int T = 20;
	public static final int U = 21;
	public static final int V = 22;
	public static final int W = 23;
	public static final int X = 24;
	public static final int a = 25;
	public static final int b = 26;
	public static final int c = 27;
	public static final int d = 28;
	public static final int e = 29;
	public static final int f = 30;
	public static final int g = 31;
	public static final int h = 32;
	public static final int Y = 33;
	public static final int Z = 34;
	public static final int LEVEL = 35;
	public static final int XP = 36;


	public static void updateJewelCount( int[][] jewels){

		SharedPreferences.Editor e = JewelChatApp.getSharedPref().edit();

		for(int i=0; i< jewels.length; i++){

				switch(jewels[i][0]){

					case A :{
						e.putInt("A",jewels[i][1]);
						break;
					}
					case B :{
						e.putInt("B",jewels[i][1]);
						break;
					}
					case C :{
						e.putInt("C",jewels[i][1]);
						break;
					}
					case D :{
						e.putInt("D",jewels[i][1]);
						break;
					}
					case Y :{
						e.putInt("Y",jewels[i][1]);
						break;
					}
					case Z :{
						e.putInt("Z",jewels[i][1]);
						break;
					}
					case LEVEL :{
						e.putInt("LEVEL",jewels[i][1]);
						break;
					}
					case XP :{
						e.putInt("XP",jewels[i][1]);
						break;
					}

				}

		}

		e.commit();

		int square = JewelChatApp.getSharedPref().getInt("A",0);
		int triangle = JewelChatApp.getSharedPref().getInt("B",0);
		int circle = JewelChatApp.getSharedPref().getInt("C",0);
		int rectangle = JewelChatApp.getSharedPref().getInt("D",0);
		int coin = JewelChatApp.getSharedPref().getInt("Y",0);
		int diamond = JewelChatApp.getSharedPref().getInt("Z",0);
		int level = JewelChatApp.getSharedPref().getInt("LEVEL",0);
		int xp = JewelChatApp.getSharedPref().getInt("XP",0);



		JewelChatApp.getBusInstance().post(new BasicJewelCountChangedEvent(square,triangle,
				circle, rectangle, coin, diamond, level, xp));

	}

	@Produce
	public static BasicJewelCountChangedEvent produceBasicJewelCountChangedEvent() {

		int square = JewelChatApp.getSharedPref().getInt("A",0);
		int triangle = JewelChatApp.getSharedPref().getInt("B",0);
		int circle = JewelChatApp.getSharedPref().getInt("C",0);
		int rectangle = JewelChatApp.getSharedPref().getInt("D",0);
		int coin = JewelChatApp.getSharedPref().getInt("Y",0);
		int diamond = JewelChatApp.getSharedPref().getInt("Z",0);
		int level = JewelChatApp.getSharedPref().getInt("LEVEL",0);
		int xp = JewelChatApp.getSharedPref().getInt("XP",0);

		return new BasicJewelCountChangedEvent(square,triangle,
				circle, rectangle, coin, diamond, level, xp);
	}

	public static int[] getStore(){

		int[] store = new int[36];

		return store;
	}






}
