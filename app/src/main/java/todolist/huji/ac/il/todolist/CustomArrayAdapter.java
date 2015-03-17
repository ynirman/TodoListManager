package todolist.huji.ac.il.todolist;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomArrayAdapter extends ArrayAdapter<String> {

    private int[] colors = new int[] {Color.RED, Color.BLUE};

    public CustomArrayAdapter(Context context, int textViewResourceId, List<String> objects) {
        super(context, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = super.getView(position, convertView, parent);
        int colorPos = position % colors.length;
        TextView tv = (TextView) view.findViewById(android.R.id.text1);
        tv.setTextColor(colors[colorPos]);
        return view;
    }
}