package in.jewelchat.jewelchat.models;

import java.util.HashMap;

/**
 * Created by mayukhchakraborty on 06/03/16.
 */
public class ContactPresenceChangedEvent {

	public HashMap<Integer, UserPresence> contactPresence;

	public ContactPresenceChangedEvent(HashMap cp){
		this.contactPresence = cp;
	}



}
