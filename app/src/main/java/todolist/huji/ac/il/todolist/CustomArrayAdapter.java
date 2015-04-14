package todolist.huji.ac.il.todolist;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CustomArrayAdapter extends ArrayAdapter<ToDoItem> {

    private final Context context;
    private List<ToDoItem> objects;

    public CustomArrayAdapter(Context context, int textViewResourceId, List<ToDoItem> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.row, parent, false);

        TextView title = (TextView) view.findViewById(R.id.txtTodoTitle);
        title.setText(objects.get(position).title);

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        TextView dueDate = (TextView) view.findViewById(R.id.txtTodoDueDate);
        Date date = objects.get(position).dueDate;
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

        return view;
    }
}