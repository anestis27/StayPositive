package com.example.orion.staypositive;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ListAdapter listAdapter;
    private TextView emptyListText;
    private DataBase db;
    private Cursor cursor;

    /*
           Ton kodika gia to datepicker ton brika etoimo.
           Den bgazei taksinomimena ta apotelesmata sto listview (den ksero giati an mporeite steilte mou ton sosto tropo..eyxaristo :) )
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        emptyListText = (TextView) findViewById(R.id.empty);
        listView = (ListView) findViewById(R.id.listView);

        db = new DataBase(MainActivity.this);
        try {
            cursor = db.getAllDaysItems();
        }catch (SQLiteException e){
            e.printStackTrace();
        }

        listAdapter = new ListAdapter(MainActivity.this,cursor);
        listView.setAdapter(listAdapter);
        listView.setEmptyView(emptyListText);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3) {
                TextView c = (TextView) v.findViewById(R.id.date);
                String date = c.getText().toString();
                Intent intentNewGame = new Intent(MainActivity.this, SelectedItem.class);
                Bundle bundle = new Bundle();
                bundle.putString("Date", date);
                intentNewGame.putExtras(bundle);
                startActivity(intentNewGame);
                //finish();
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAddElement = new Intent(MainActivity.this, AddElementActivity.class);
                startActivity(intentAddElement);
                //finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.action_save).setVisible(false);
        menu.findItem(R.id.action_edit).setVisible(false);
        menu.findItem(R.id.action_delete).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_clear_history) {
            clearHistory();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void clearHistory(){
        db.deleteDataBase();
        listAdapter.notifyDataSetChanged(); // den mou leitourgei i entoli
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}
