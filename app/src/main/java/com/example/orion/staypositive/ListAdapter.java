package com.example.orion.staypositive;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Orion on 21/4/2016.
 */
public class ListAdapter extends CursorAdapter {


    private LayoutInflater inflater;


    public ListAdapter(Context context, Cursor c) {
        super(context, c);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View view = null;
        view = inflater.inflate(R.layout.one_day_element_list, parent, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        TextView date = (TextView) view.findViewById(R.id.date);

        date.setText(cursor.getString(1));
        String color = cursor.getString(2);
        if(color.equals("0")){
            imageView.setImageResource(R.drawable.red);
        }
        if(color.equals("1")){
            imageView.setImageResource(R.drawable.orange);
        }
        if(color.equals("2")){
            imageView.setImageResource(R.drawable.green);
        }
        if(color.equals("3")){
            imageView.setImageResource(R.drawable.blue);
        }
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        TextView date = (TextView) view.findViewById(R.id.date);

        date.setText(cursor.getString(1));
        String color = cursor.getString(2);
        if(color.equals("1")){
            imageView.setBackgroundResource(R.drawable.red);
        }
        if(color.equals("2")){
            imageView.setBackgroundResource(R.drawable.orange);
        }
        if(color.equals("3")){
            imageView.setBackgroundResource(R.drawable.green);
        }
    }

}
