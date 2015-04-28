package todolist.huji.ac.il.todolist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Date;


public class TodoListManagerActivity extends ActionBarActivity {

    private ToDoCursorAdapter toDoCursorAdapter;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);

        ListView list = (ListView)findViewById(R.id.lstTodoItems);
        ToDoDbHelper toDoDbHelper = new ToDoDbHelper(this);

        final SQLiteDatabase toDoDb = toDoDbHelper.getWritableDatabase();
        Cursor cursor = toDoDb.query(ToDoDbHelper.DATABASE_NAME,null,null,null,null,null,null);
        toDoCursorAdapter = new ToDoCursorAdapter(this, cursor, 0);
        list.setAdapter(toDoCursorAdapter);

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int pos, long id) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                Cursor c = toDoDb.query(ToDoDbHelper.DATABASE_NAME,null,null,null,null,null,null);
                c.moveToPosition(pos);
                final String title = c.getString(c.getColumnIndex("title"));
                final int idToRemove = c.getInt(c.getColumnIndex("_id"));


                alertDialogBuilder.setTitle(title);
                alertDialogBuilder.setNegativeButton(R.string.menuItemDelete,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ToDoDbHelper toDoDbHelper = new ToDoDbHelper(context);
                                SQLiteDatabase toDoDb = toDoDbHelper.getWritableDatabase();
                                toDoDb.delete(ToDoDbHelper.DATABASE_NAME, "_id = " + idToRemove, null);
                                toDoCursorAdapter.changeCursor(toDoDb.query(ToDoDbHelper.DATABASE_NAME,
                                        null, null, null, null, null, null));
                                toDoCursorAdapter.notifyDataSetChanged();
                            }
                        });

                if (title.startsWith(getResources().getString(R.string.menuItemCall))) {
                    alertDialogBuilder.setNeutralButton(title,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    String phoneNumber = "tel:" + title.substring(5);
                                    Intent dial = new Intent(Intent.ACTION_DIAL, Uri.parse(phoneNumber));
                                    startActivity(dial);
                                }
                            });
                }


                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_todo_list_manager,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemAdd:
                Intent intent = new Intent(this, AddNewTodoItemActivity.class);
                startActivityForResult(intent, 1);
                return true;
            default:
                return false;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            String title = data.getStringExtra("title");
            Date dueDate = (Date)data.getSerializableExtra("dueDate");
            ToDoDbHelper toDoDbHelper = new ToDoDbHelper(this);
            SQLiteDatabase toDoDb = toDoDbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("title",title);
            contentValues.put("due", dueDate.getTime());
            toDoDb.insert(ToDoDbHelper.DATABASE_NAME, null, contentValues);
            toDoCursorAdapter.changeCursor(toDoDb.query(ToDoDbHelper.DATABASE_NAME,
                    null, null, null, null, null, null));
            toDoCursorAdapter.notifyDataSetChanged();
        }
    }
}
