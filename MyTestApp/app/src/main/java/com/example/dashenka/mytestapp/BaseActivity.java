package com.example.dashenka.mytestapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by Dashenka on 19.11.15.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.Today:
                Intent intent1 = new Intent(this, MainActivity.class);
                startActivity(intent1);
                return true;
            case R.id.PersonalData:
                Intent intent2 = new Intent(this, PersonalData.class);
                startActivity(intent2);
                return true;
            case R.id.ShareResult:
                Intent intent3 = new Intent(this, ShareResults.class);
                startActivity(intent3);
                return true;
            case R.id.About:
                Intent intent5 = new Intent(this, About.class);
                startActivity(intent5);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
