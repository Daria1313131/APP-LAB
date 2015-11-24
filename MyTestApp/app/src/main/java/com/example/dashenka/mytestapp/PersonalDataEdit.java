package com.example.dashenka.mytestapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dashenka on 19.11.15.
 */
public class PersonalDataEdit extends BaseActivity {

    String[] StringDiet = {"No food", "Breakfast", "Banana diet", "Meat diet"};

    private DataBase mDbHelper;
    //private SQLiteDatabase mDb;
    private Long mRowId;
    private EditText meditName;
    private EditText meditAge;
    private EditText meditWeight;
    private EditText meditHight;
    private Spinner mDiet;
    private Cursor cursor;

    public String Diete_date_in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_edit);

        //Button
        Button saveButton = (Button)findViewById(R.id.buttonSave);

        //EditText
        meditName = (EditText)findViewById(R.id.editName);
        meditAge = (EditText)findViewById(R.id.editAge);
        meditWeight = (EditText)findViewById(R.id.editWeight);
        meditHight = (EditText)findViewById(R.id.editHight);
        mDiet = (Spinner) findViewById(R.id.Diete);

/*************************************************************************************************************************/
        ArrayAdapter<String> StringDiet = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new
                String[]{"No food", "Breakfast", "Banana diet", "Meat diet"});
        mDiet.setAdapter(StringDiet);
        mDiet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Log.d("item selected", "item selected");
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
/*************************************************************************************************************************/



        //DataBase for writing
        mDbHelper = new DataBase(this);

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (TextUtils.isEmpty(meditName.getText().toString())||TextUtils.isEmpty(meditAge.getText().toString())||TextUtils.isEmpty(meditWeight.getText().toString())||TextUtils.isEmpty(meditHight.getText().toString()))
                {
                    Toast.makeText(PersonalDataEdit.this, "Данные не введены", Toast.LENGTH_LONG).show();
                }
                else
                {
                saveState();
                setResult(RESULT_OK);
                finish();
                }
            }
        });
    }

    private void populateFields() {
        if (mRowId != null) {
            Cursor todo = mDbHelper.getTodo(mRowId);
            startManagingCursor(todo);
            String category = todo.getString(todo.getColumnIndexOrThrow(DataBase.COLUMN_NAME));

            for (int i = 0; i < mDiet.getCount(); i++) {

                String s = (String) mDiet.getItemAtPosition(i);
                Log.e(null, s + " " + category);
                if (s.equalsIgnoreCase(category)) {
                    mDiet.setSelection(i);
                }
            }

            meditName.setText(todo.getString(todo.getColumnIndexOrThrow(DataBase.COLUMN_NAME)));
            meditAge.setText(todo.getString(todo.getColumnIndexOrThrow(DataBase.COLUMN_AGE)));
            todo.close();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //saveState();
        //outState.putSerializable(ToDoDatabase.COLUMN_ID, mRowId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //saveState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateFields();
    }

    private void saveState()
    {
        String name = meditName.getText().toString();//(String) mCategory.getSelectedItem();
        String age = meditAge.getText().toString();
        String weight = meditWeight.getText().toString();
        String heiht = meditHight.getText().toString();
        String diete = mDiet.getSelectedItem().toString();
        mDbHelper.updateTodo(1, name, age, weight, heiht, diete);

        /*
        if (description.length() == 0 && summary.length() == 0) {
            return;
        }
        if (mRowId == null) {
            long id = mDbHelper.createNewTodo(name, age, weight, heiht);
            if (id > 0) {
                mRowId = id;
            }
        } else {
            mDbHelper.updateTodo(1, name, age, weight, heiht);
        }*/
    }
}
