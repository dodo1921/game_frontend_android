package in.jewelchat.jewelchat.models;

/**
 * Created by mayukhchakraborty on 02/03/16.
 */
public class BasicJewelCountChangedEvent {

	public final int A;
	public final int C;
	public final int B;
	public final int D;
	public final int Y;
	public final int Z;
	public final int LEVEL;
	public final int LEVEL_XP;
	public final int XP;
	public final boolean levelchange;


	public BasicJewelCountChangedEvent(int A, int C, int B, int D,
	                                   int Y, int Z, int LEVEL, int LEVEL_XP, int XP, boolean levelchange){
		this.A = A;
		this.C = C;
		this.B = B;
		this.D = D;
		this.Y = Y;
		this.Z = Z;
		this.LEVEL = LEVEL;
		this.LEVEL_XP = LEVEL_XP;
		this.XP = XP;
		this.levelchange = levelchange;
	}

}
