package com.example.orion.staypositive;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Created by Orion on 21/4/2016.
 */
public class AddElementActivity extends AppCompatActivity {

    private Button dateButton,recordButton;
    private EditText editText1, editText2, editText3;
    private int day, month, year;
    private AlertDialog dialog;
    static final int DATE_PICKER_ID = 1111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_element_activity);
        init();
        Listeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.action_edit).setVisible(false);
        menu.findItem(R.id.action_delete).setVisible(false);
        menu.findItem(R.id.action_settings).setVisible(false);
        menu.findItem(R.id.action_clear_history).setVisible(false);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (dialog != null)
            if (dialog.isShowing())
                dialog.dismiss();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save: {
                DataBase db = new DataBase(AddElementActivity.this);
                Cursor c = db.getAnItem(dateButton.getText().toString());
                if (c.getCount() == 0) {
                    onSave();
                    Intent intentAddElement = new Intent(AddElementActivity.this, MainActivity.class);
                    startActivity(intentAddElement);
                    finish();
                } else {
                    Bundle b = getIntent().getExtras();
                    if (b != null) {
                        int i = 0;
                        if (!editText1.getText().toString().equals("")) {
                            i += 1;
                        }
                        if (!editText2.getText().toString().equals("")) {
                            i += 1;
                        }
                        if (!editText3.getText().toString().equals("")) {
                            i += 1;
                        }
                        db.editAnItem(dateButton.getText().toString(), i + "", editText1.getText().toString(), editText2.getText().toString(), editText3.getText().toString());
                        //onBackPressed();
                    }
                    //se periptosi overwright
                    dialog = new AlertDialog.Builder(AddElementActivity.this)
                            .setTitle(getString(R.string.overwrite_entry))
                            .setMessage(getString(R.string.you_want_to_overwrite_this_entry))
                            .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    DataBase db = new DataBase(AddElementActivity.this);
                                    int i = 0;
                                    if (!editText1.getText().toString().equals("")) {
                                        i += 1;
                                    }
                                    if (!editText2.getText().toString().equals("")) {
                                        i += 1;
                                    }
                                    if (!editText3.getText().toString().equals("")) {
                                        i += 1;
                                    }
                                    db.editAnItem(dateButton.getText().toString(), i + "", editText1.getText().toString(), editText2.getText().toString(), editText3.getText().toString());
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
                    break;
                }

            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //Intent intentAddElement = new Intent(AddElementActivity.this, MainActivity.class);
       // startActivity(intentAddElement);
        finish();
    }

    private void onSave() {
        DataBase db = new DataBase(AddElementActivity.this);//instance of current database
        int i = 0;
        if (!editText1.getText().toString().equals("")) {
            i += 1;
        }
        if (!editText2.getText().toString().equals("")) {
            i += 1;
        }
        if (!editText3.getText().toString().equals("")) {
            i += 1;
        }

        Day item = new Day(dateButton.getText().toString(), i, editText1.getText().toString(), editText2.getText().toString(), editText3.getText().toString());
        db.InsertDay(item);
        db.close();
    }

    private void init() {
        dateButton = (Button) findViewById(R.id.dateButton);
        recordButton = (Button) findViewById(R.id.record);
        editText1 = (EditText) findViewById(R.id.editText1);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        Bundle b = getIntent().getExtras();
        if (b == null) {
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String formattedDate = df.format(c.getTime());
            dateButton.setText(formattedDate);
        } else {
            dateButton.setEnabled(false);
            dateButton.setText(b.getString("Date"));
            editText1.setText(b.getString("Note1"));
            editText2.setText(b.getString("Note2"));
            editText3.setText(b.getString("Note3"));
        }
    }

    private void Listeners() {
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_PICKER_ID);
            }
        });
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final MediaRecorder recorder = new MediaRecorder();
                ContentValues values = new ContentValues(3);
                values.put(MediaStore.MediaColumns.TITLE, "dd");
                recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                recorder.setOutputFile("/sdcard/sound/" + "dd");
                try {
                    recorder.prepare();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                final ProgressDialog mProgressDialog = new ProgressDialog(AddElementActivity.this);
                mProgressDialog.setTitle("AAA");
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setButton("Stop recording", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        mProgressDialog.dismiss();
                        recorder.stop();
                        recorder.release();
                    }
                });

                mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface p1) {
                        recorder.stop();
                        recorder.release();
                    }
                });
                recorder.start();
                mProgressDialog.show();

            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_ID:

                // open datepicker dialog.
                // set date picker for current date
                // add pickerListener listner to date picker
                return new DatePickerDialog(this, pickerListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // Show selected date
            dateButton.setText(new StringBuilder().append(day).append("-").append(month + 1)
                    .append("-").append(year)
                    .append(" "));

        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("date", dateButton.getText().toString());
        outState.putString("note1", editText1.getText().toString());
        outState.putString("note2", editText2.getText().toString());
        outState.putString("note3", editText3.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        dateButton.setText(savedInstanceState.getString("date"));
        editText1.setText(savedInstanceState.getString("note1"));
        editText2.setText(savedInstanceState.getString("note2"));
        editText3.setText(savedInstanceState.getString("note3"));
    }

}
