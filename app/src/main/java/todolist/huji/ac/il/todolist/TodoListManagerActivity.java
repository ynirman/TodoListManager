package todolist.huji.ac.il.todolist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class TodoListManagerActivity extends ActionBarActivity {

    private ArrayList<String> items;
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
                String title = items.get(pos);
                alertDialogBuilder.setTitle(title);
                alertDialogBuilder.setNegativeButton(R.string.menuItemDelete,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                items.remove(pos);
                                itemsAdapter.notifyDataSetChanged();
                            }
                        });
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
                EditText edtNewItem = (EditText) findViewById(R.id.edtNewItem);
                String itemText = edtNewItem.getText().toString();
                itemsAdapter.add(itemText);
                edtNewItem.setText("");
                return true;

            default:
                return false;
        }
    }
}
