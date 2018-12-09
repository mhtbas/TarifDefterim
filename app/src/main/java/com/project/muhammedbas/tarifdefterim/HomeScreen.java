package com.project.muhammedbas.tarifdefterim;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.muhammedbas.tarifdefterim.Utils.HomeList;

import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;

    private TextView usernameText;
    private DatabaseReference mUserDatabase;
    private DatabaseReference mRecipes;
    private String currentUser;
    private RecyclerView listTarifList;


    private FloatingActionButton addRecipes;

    private TextView addText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        init();

        //////////////////////// button click add //////////////////////////////////////

        addRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),AddRecipes.class);
                startActivity(intent);

            }
        });

        /////////////////////////// check user have recipes =  ? /////////////////////////////////////////

        mRecipes.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild("")){

                    addText.setVisibility(View.INVISIBLE);
                    addText.setEnabled(false);

                }else{

                    addText.setVisibility(View.VISIBLE);
                    addText.setEnabled(true);
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        ////////////////////////////////////// user name upload ///////////////////////////////////////////

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String username=dataSnapshot.child("name").getValue().toString();
                usernameText.setText(username+"'in tarifleri");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        ////////////////////////////////////// addText click//////////////////////////////////////////////////

        addText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),AddRecipes.class);
                startActivity(intent);
            }
        });

        ////////////////////////// navigation click event///////////////////
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.nav_fav:
                        Intent intent_fav = new Intent(getApplicationContext(),SettingPage.class);
                        startActivity(intent_fav);
                        return true;

                    case R.id.nav_cal:
                        Intent intent_cal = new Intent(getApplicationContext(),SettingPage.class);
                        startActivity(intent_cal);
                        return true;

                    case R.id.nav_seb:
                        Intent intent_seb = new Intent(getApplicationContext(),SettingPage.class);
                        startActivity(intent_seb);
                        return true;

                    case R.id.nav_mev:
                        Intent intent_mev = new Intent(getApplicationContext(),SettingPage.class);
                        startActivity(intent_mev);
                        return true;

                    case R.id.nav_fish:
                        Intent intent_fish= new Intent(getApplicationContext(),SettingPage.class);
                        startActivity(intent_fish);
                        return true;

                    case R.id.nav_setting:
                        Intent intent_setting = new Intent(getApplicationContext(),SettingPage.class);
                        startActivity(intent_setting);
                        return true;

                    case R.id.nav_contact:
                        Intent intent_contact = new Intent(getApplicationContext(),SettingPage.class);
                        startActivity(intent_contact);
                        return true;

                    case R.id.nav_help:
                        Intent intent_help= new Intent(getApplicationContext(),SettingPage.class);
                        startActivity(intent_help);
                        return true;

                    case R.id.nav_logout:
                        Intent intent_logout = new Intent(getApplicationContext(),SettingPage.class);
                        startActivity(intent_logout);
                        return true;

                    default:
                }
                return true;
            }
        });
    }

    public class ListAdapter extends BaseAdapter {

        public  ArrayList<HomeList> arrayList;

        public ListAdapter(ArrayList<HomeList> listItems){
            this.arrayList=listItems;
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return arrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return arrayList.get(position).hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = getLayoutInflater();
            View view=layoutInflater.inflate(R.layout.home_listview_single,null);

            TextView recipename= view.findViewById(R.id.recipename);

            final HomeList listItem =arrayList.get(position);

            recipename.setText(listItem.getRecipename());

            return view;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_three, menu);


        /////// divider with menu group //////////////////////////////////////////////////
        MenuCompat.setGroupDividerEnabled(menu, true);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //////////////////////////////Search Click/////////////////////////////////////////////////
         switch (item.getItemId()){
             case R.id.search:
                 Intent intent = new Intent(getApplicationContext(),SearchScreen.class);
                 startActivity(intent);
                 return true;

             default:


         }

         /////////////////////////////////////Toggle Click/////////////////////////////////////////
        if(mToggle.onOptionsItemSelected(item)){

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void init(){

        mDrawerLayout=findViewById(R.id.drawerlayout);
        mToggle= new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        navigationView=findViewById(R.id.navigationView);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        usernameText=findViewById(R.id.username);
        addText=findViewById(R.id.addtext);
        addRecipes=findViewById(R.id.addTarifButton);

        listTarifList=findViewById(R.id.listTarifList);
        listTarifList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        listTarifList.setHasFixedSize(true);

        currentUser=FirebaseAuth.getInstance().getCurrentUser().getUid();
        mUserDatabase=FirebaseDatabase.getInstance().getReference().child("users").child(currentUser);
        mRecipes=FirebaseDatabase.getInstance().getReference().child("recipes").child(currentUser);
    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<HomeList> options = new FirebaseRecyclerOptions.Builder<HomeList>()
                .setQuery(mRecipes,HomeList.class).build();


        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<HomeList, RecipeViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull RecipeViewHolder holder, int position, @NonNull HomeList model) {

                final String recipeID=getRef(position).getKey();
                Log.d("recipeıd",recipeID);

                holder.setRecipename(recipeID);

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getApplicationContext(),RecipeScreen.class);
                        intent.putExtra("recipecategory",recipeID);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

                View searchView =LayoutInflater.from(getApplicationContext())
                        .inflate(R.layout.home_listview_single,parent,false);

                return new RecipeViewHolder(searchView);
            }

        };

        listTarifList.setAdapter(adapter);
        adapter.startListening();

    }

    private class RecipeViewHolder extends  RecyclerView.ViewHolder{

        private View mView;
        private TextView recipename;


        public RecipeViewHolder(View itemView) {
            super(itemView);
            mView=itemView;

            recipename = mView.findViewById(R.id.recipename);

        }

        public void setRecipename(String recipenamestring){
            recipename.setText(recipenamestring);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();


    }
}
