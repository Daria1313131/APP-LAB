package com.example.dashenka.mytestapp;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dashenka on 19.11.15.
 */
public class PersonalData extends BaseActivity {

    String[] StringDiet = {"No food", "Breakfast", "Banana diet", "Meat diet"};

    private Long mRowId;
    private TextView meditName;
    private TextView meditAge;
    private TextView meditWeight;
    private TextView meditHight;
    private TextView mDiete;
    private Spinner mCategory;

    private Cursor cursor;
    private DataBase mDbHelper;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        Button saveButton = (Button) findViewById(R.id.buttonEdit);
        //TextView
        meditName = (TextView) findViewById(R.id.Name);
        meditAge = (TextView) findViewById(R.id.Age);
        meditWeight = (TextView) findViewById(R.id.Weight);
        meditHight = (TextView) findViewById(R.id.Hight);
        mDiete = (TextView) findViewById(R.id.editDeite);
        // заголовок
        //mDiet.setPrompt("Diet");

        mDbHelper = new DataBase(this);

        mDbHelper.createNewTodo("","","","","");
        //mDbHelper.createNewTodo("mTitleText","mBodyText","meditWeight");

        mDb = mDbHelper.getWritableDatabase();

        fillData();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PersonalData.this, "Button Clicked", Toast.LENGTH_SHORT).show();
                createNewTask();
            }
        });
    }

    protected void onResume(){
        super.onResume();
        fillData();
    }


    private void createNewTask() {
        Intent intent = new Intent(this, PersonalDataEdit.class);
        startActivityForResult(intent, 1);
    }

    /*private void fillData() {
        mDb = mDbHelper.getWritableDatabase();
        Cursor cursor = mDb.rawQuery("SELECT count(*) FROM itemtable", null);
        cursor.moveToFirst();
        if (cursor.getInt(0) > 0) {
            mDbHelper.fetchAllReminders();
            Cursor remindersCursor = mDbHelper.fetchAllReminders();
            if (remindersCursor != null) {
                String[] from = new String[]{DataBase.COLUMN_SUMMARY};
                int[] to = new int[]{1};
                SimpleCursorAdapter reminders = new SimpleCursorAdapter(this, R.layout.activity_personal, remindersCursor, from, to, 0);
                //setListAdapter(reminders);
            }
        }
        else
        {
        }
    }*/

    private void fillData() {
        Log.d("filldata","filldata");
        //mDbHelper.onCreate(mDb);
        /*Cursor cursor = mDb.rawQuery("SELECT count(*) FROM itemtable", null);
        cursor.moveToFirst();
        if (cursor.getInt(0) > 0)
        {mTitleText.setText("LALAALAL");}
            else
        {mTitleText.setText("APAPAP");}
        */
            List<String> name = new ArrayList<String>();
            List<String> age = new ArrayList<String>();
            List<String> weight = new ArrayList<String>();
            List<String> heiht = new ArrayList<String>();
            List<String> diete = new ArrayList<String>();
            cursor = mDbHelper.getAllTodos();
            //startManagingCursor(cursor);
            //String[] from = new String[]{ DataBase.COLUMN_SUMMARY };
            //String[] description = new String[]{ DataBase.COLUMN_DESCRIPTION};
            //String[] id = new String[]{ DataBase.COLUMN_ID};

            //int i = 1;
            //String text = cursor.getString(from[i]);

        //int[] to = new int[]{2};
        //SimpleCursorAdapter reminders = new SimpleCursorAdapter(this, R.layout.activity_personal, cursor, from, to, 0);
        //mBodyText.setText(reminders.convertToString(cursor));

        if (cursor.moveToFirst())
        {
            do
            {
                name.add(cursor.getString(1));
                meditName.setText(name.toString().replace("[", "").replace("]", "").replace(",", ""));
                age.add(cursor.getString(2));
                meditAge.setText(age.toString().replace("[", "").replace("]", "").replace(",", ""));
                weight.add(cursor.getString(3));
                meditWeight.setText(weight.toString().replace("[", "").replace("]", "").replace(",", ""));
                heiht.add(cursor.getString(4));
                meditHight.setText(heiht.toString().replace("[", "").replace("]", "").replace(",", ""));
                diete.add(cursor.getString(5));
                mDiete.setText(diete.toString().replace("[", "").replace("]", "").replace(",", ""));
            }
            while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

    }

}

