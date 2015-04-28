package todolist.huji.ac.il.todolist;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ToDoDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "todo_db";

    public ToDoDbHelper(Context context) {super(context, DATABASE_NAME, null, 1);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ DATABASE_NAME +
                " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT," +
                "due NUMERIC);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(db);
    }
}
