package in.jewelchat.jewelchat.models;

/**
 * Created by mayukhchakraborty on 02/03/16.
 */
public class BasicJewelCountChangedEvent {

	public final int square;
	public final int circle;
	public final int triangle;
	public final int rectangle;
	public final int coin;
	public final int diamond;
	public final int level;
	public final int xp;
	public final boolean levelchange;


	public BasicJewelCountChangedEvent(int square, int circle, int triangle, int rectangle,
	                                   int coin, int diamond, int level, int xp, boolean levelchange){
		this.square = square;
		this.circle = circle;
		this.triangle = triangle;
		this.rectangle = rectangle;
		this.coin = coin;
		this.diamond = diamond;
		this.level = level;
		this.xp = xp;
		this.levelchange = levelchange;
	}

}
