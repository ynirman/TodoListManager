package todolist.huji.ac.il.todolist;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ToDoCursorAdapter extends CursorAdapter {

    private LayoutInflater mInflater;

    public ToDoCursorAdapter (Context context, Cursor c, int flags){
        super(context, c, flags);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return mInflater.inflate(R.layout.row, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView title = (TextView) view.findViewById(R.id.txtTodoTitle);
        title.setText(cursor.getString(cursor.getColumnIndex("title")));

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        TextView dueDate = (TextView) view.findViewById(R.id.txtTodoDueDate);
        Date date = new Date(cursor.getLong(cursor.getColumnIndex("due")));
        dueDate.setText(format.format(date.getTime()));

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        Date today = c.getTime();

        if (date.before(today)) {
            title.setTextColor(Color.RED);
            dueDate.setTextColor(Color.RED);
        }

    }
}
