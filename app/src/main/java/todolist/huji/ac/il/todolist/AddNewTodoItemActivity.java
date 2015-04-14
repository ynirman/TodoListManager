package todolist.huji.ac.il.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;


public class AddNewTodoItemActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_todo);

        Button btnOk = (Button) findViewById(R.id.btnOK);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText newToDo = (EditText) findViewById(R.id.edtNewItem);
                Intent resultIntent = new Intent();
                resultIntent.putExtra("title", newToDo.getText().toString());
                DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
                Calendar calendar = Calendar.getInstance();
                calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                resultIntent.putExtra("dueDate", calendar.getTime());
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });


        Button btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
