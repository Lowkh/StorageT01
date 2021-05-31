package sg.edu.np.week7;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHandler extends SQLiteOpenHelper {

    public static int DATABASE_VERSION = 1;
    public static String DATABASE_NAME = "accountDB.db";
    public static String ACCOUNTS = "Accounts";
    public static String COLUMN_USERNAME = "UserName";
    public static String COLUMN_PASSWORD = "Password";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_TABLE = "CREATE TABLE "+ ACCOUNTS + "(" + COLUMN_USERNAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT"+")";

        //CREATE TABLE Accounts(UserName TEXT, Password TEXT)
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS "+ ACCOUNTS);
        onCreate(db);
    }

    public void addUser(UserData userData)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, userData.getUsername());
        values.put(COLUMN_PASSWORD, userData.getPassword());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(ACCOUNTS, null, values);
        db.close();
    }

    public UserData findUser(String username)
    {
        String query = "SELECT * FROM " + ACCOUNTS + " WHERE " +COLUMN_USERNAME +"=\"" + username + "\"";

        //SELECT * FROM Accounts WHERE UserName = "username"

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        UserData queryData = new UserData();

        if(cursor.moveToFirst()) //find the first occurrance
        {
            queryData.setUsername(cursor.getString(0));
            queryData.setPassword(cursor.getString(1));
            cursor.close();
        }
        else
        {
            queryData = null;
        }
        db.close();
        return queryData;

    }

}
