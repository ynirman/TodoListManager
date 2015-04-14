package todolist.huji.ac.il.todolist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;


public class TodoListManagerActivity extends ActionBarActivity {

    private ArrayList<ToDoItem> items;
    private CustomArrayAdapter itemsAdapter;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);
        ListView list = (ListView)findViewById(R.id.lstTodoItems);
        items = new ArrayList<>();
        itemsAdapter = new CustomArrayAdapter(this, android.R.layout.simple_list_item_1, items);
        list.setAdapter(itemsAdapter);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int pos, long id) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                final String title = items.get(pos).title;
                alertDialogBuilder.setTitle(title);
                alertDialogBuilder.setNegativeButton(R.string.menuItemDelete,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                items.remove(pos);
                                itemsAdapter.notifyDataSetChanged();
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
            items.add(new ToDoItem(data.getStringExtra("title"), (Date)data.getSerializableExtra("dueDate")));
            itemsAdapter.notifyDataSetChanged();
        }
    }
}
