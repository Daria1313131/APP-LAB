package com.example.dashenka.mytestapp;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Dashenka on 19.11.15.
 */
public class PersonalData extends BaseActivity {

    String[] StringDiet = {"No food", "Breakfast", "Banana diet", "Meat diet"};

    private DataBase mDbHelper;
    private Long mRowId;
    private EditText mTitleText;
    private EditText mBodyText;
    private EditText meditWeight;
    private EditText meditHight;
    private Spinner mCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        //Button
        Button saveButton = (Button)findViewById(R.id.buttonSave);

        //EditText
        mTitleText = (EditText)findViewById(R.id.editName);
        mBodyText = (EditText)findViewById(R.id.editAge);
        meditWeight = (EditText)findViewById(R.id.editWeight);
        meditHight = (EditText)findViewById(R.id.editHight);

        mCategory = (Spinner) findViewById(R.id.Diete);
        // заголовок
        //mDiet.setPrompt("Diet");

        //DataBase
        mDbHelper = new DataBase(this);
        mRowId = null;
        Bundle extras = getIntent().getExtras();

        mRowId = (savedInstanceState == null) ? null
                : (Long) savedInstanceState
                .getSerializable(DataBase.COLUMN_ID);
        if (extras != null) {
            mRowId = extras.getLong(DataBase.COLUMN_ID);
        }

        populateFields();

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                /*if (TextUtils.isEmpty(meditName.getText().toString())) {
                    Toast.makeText(PersonalData.this, "Данные не введены",
                            Toast.LENGTH_LONG).show();
                } else {*/
                    saveState();
                    setResult(RESULT_OK);
                    finish();
                //}
            }
        });
    }

    private void populateFields() {
        if (mRowId != null) {
            Cursor todo = mDbHelper.getTodo(mRowId);
            startManagingCursor(todo);
            String category = todo.getString(todo
                    .getColumnIndexOrThrow(DataBase.COLUMN_CATEGORY));

            for (int i = 0; i < mCategory.getCount(); i++) {

                String s = (String) mCategory.getItemAtPosition(i);
                Log.e(null, s + " " + category);
                if (s.equalsIgnoreCase(category)) {
                    mCategory.setSelection(i);
                }
            }

            mTitleText.setText(todo.getString(todo
                    .getColumnIndexOrThrow(DataBase.COLUMN_SUMMARY)));
            mBodyText.setText(todo.getString(todo
                    .getColumnIndexOrThrow(DataBase.COLUMN_DESCRIPTION)));
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

    private void saveState() {
        String category = (String) mCategory.getSelectedItem();
        String summary = mTitleText.getText().toString();
        String description = mBodyText.getText().toString();

        if (description.length() == 0 && summary.length() == 0) {
            return;
        }

        if (mRowId == null) {
            long id = mDbHelper.createNewTodo(category, summary, description);
            if (id > 0) {
                mRowId = id;
            }
        } else {
            mDbHelper.updateTodo(mRowId, category, summary, description);
        }
    }
}
