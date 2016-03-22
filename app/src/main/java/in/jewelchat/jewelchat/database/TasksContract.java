package in.jewelchat.jewelchat.database;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by mayukhchakraborty on 20/03/16.
 */
public class TasksContract implements BaseColumns {

	public static final String KEY_ROWID = BaseColumns._ID;
	public static final String TASK_ID = "taskId";// foreign key of the group/contact
	public static final String TYPE = "type";
	public static final String VALUE = "value";
	public static final String TASK_TEXT = "taskText";
	public static final String TASK_NOTE = "taskNote";
	public static final String DIAMOND_COUNT = "diamondCount";



	public static final String SQLITE_TABLE_NAME = "JewelChatTasks";

	private static final String DATABASE_CREATE =
			"CREATE TABLE if not exists " + SQLITE_TABLE_NAME + " ( " +
					KEY_ROWID + " integer PRIMARY KEY autoincrement," +
					TASK_ID + "  INTEGER" + ", " +
					TYPE + "  INTEGER" + ", " +
					VALUE + "  INTEGER" + ", " +
					DIAMOND_COUNT + "  INTEGER" + ", " +
					TASK_TEXT + "  TEXT" + ", " +
					TASK_NOTE + "  TEXT" + " )";


	public static void onCreate(SQLiteDatabase db) {
		Log.i("JewelChatTask", "OnCreate");
		db.execSQL(DATABASE_CREATE);
		/*
		String insertquery1 =  "INSERT INTO Group (groupId,contactnumber,contactname,groupMessageChannel)"+
				" values('4','+919005835705','Santanu','shantanuGroupChannel')";
		db.execSQL(insertquery1);

		String insertquery2 =  "INSERT INTO Group (groupId,contactnumber,contactname,groupMessageChannel)"+
				" values('4','+919005835706','Veeru','veeruGroupChannel')";
		db.execSQL(insertquery2);
		*/
	}

	public static void onUpgrade(SQLiteDatabase db, int oldVersion,
	                             int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE_NAME);
		onCreate(db);
	}

}
