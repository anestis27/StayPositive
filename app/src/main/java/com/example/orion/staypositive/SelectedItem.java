package com.example.orion.staypositive;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by Orion on 4/5/2016.
 */
public class SelectedItem extends AppCompatActivity {

    private TextView date, firstnoteTextView, secondnoteTextView, thirtnoteTextView;
    private AlertDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_item);
        init();
        Bundle b = getIntent().getExtras();
        String date = b.getString("Date");
        DataBase db = new DataBase(SelectedItem.this);
        Cursor c = db.getAnItem(date);
        if (c.moveToFirst()) {
            this.date.setText(c.getString(c.getColumnIndex("date")));
            firstnoteTextView.setText(c.getString(c.getColumnIndex("note1")));
            secondnoteTextView.setText(c.getString(c.getColumnIndex("note2")));
            thirtnoteTextView.setText(c.getString(c.getColumnIndex("note3")));
        }
        //Log.i("dad",c.getString(c.getColumnIndex("date")) );
        c.close();
    }

    private void init() {
        date = (TextView) findViewById(R.id.dateTextView);
        firstnoteTextView = (TextView) findViewById(R.id.firstnoteTextView);
        secondnoteTextView = (TextView) findViewById(R.id.secondnoteTextView);
        thirtnoteTextView = (TextView) findViewById(R.id.thirtnoteTextView);
    }

    @Override
    public void onBackPressed() {
        //Intent intentAddElement = new Intent(SelectedItem.this, MainActivity.class);
        //startActivity(intentAddElement);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (dialog != null)
            if (dialog.isShowing())
                dialog.dismiss();
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
        menu.findItem(R.id.action_clear_history).setVisible(false);
        menu.findItem(R.id.action_settings).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit) {
            Intent intentNewGame = new Intent(SelectedItem.this, AddElementActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("Date", date.getText().toString());
            bundle.putString("Note1", firstnoteTextView.getText().toString());
            bundle.putString("Note2", secondnoteTextView.getText().toString());
            bundle.putString("Note3", thirtnoteTextView.getText().toString());
            intentNewGame.putExtras(bundle);
            startActivity(intentNewGame);
            finish();
            return true;
        }
        if (id == R.id.action_delete) {
           dialog = new AlertDialog.Builder(SelectedItem.this)
                    .setTitle(getString(R.string.delete_entry))
                    .setMessage(getString(R.string.you_want_to_delete_this_entry))
                    .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            DataBase db = new DataBase(SelectedItem.this);
                            db.deleteAnItem(date.getText().toString());
                            onBackPressed();
                        }
                    })
                    .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("date", date.getText().toString());
        outState.putString("note1", firstnoteTextView.getText().toString());
        outState.putString("note2", secondnoteTextView.getText().toString());
        outState.putString("note3", thirtnoteTextView.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        date.setText(savedInstanceState.getString("date"));
        firstnoteTextView.setText(savedInstanceState.getString("note1"));
        secondnoteTextView.setText(savedInstanceState.getString("note2"));
        thirtnoteTextView.setText(savedInstanceState.getString("note3"));
    }

}
