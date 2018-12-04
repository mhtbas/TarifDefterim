package com.project.muhammedbas.tarifdefterim;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ToggleButton;

public class HomeScreen extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_three, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

         switch (item.getItemId()){
             case R.id.setting:
                 Intent intent = new Intent(getApplicationContext(),SettingPage.class);
                 startActivity(intent);
                 return true;


             default:

         }

        if(mToggle.onOptionsItemSelected(item)){

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void init(){

        mDrawerLayout=findViewById(R.id.drawerlayout);
        mToggle= new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
