package com.project.muhammedbas.tarifdefterim;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.muhammedbas.tarifdefterim.LoginRegister.LoginScreen;
import com.project.muhammedbas.tarifdefterim.SettingPage.ContactScreen;
import com.project.muhammedbas.tarifdefterim.SettingPage.FavoriteScreen;
import com.project.muhammedbas.tarifdefterim.SettingPage.HelpScreen;
import com.project.muhammedbas.tarifdefterim.SettingPage.SettingPage;
import com.project.muhammedbas.tarifdefterim.Utils.HomeList;

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
                usernameText.setText(username+"'"+R.string.home_own);

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
                        Intent intent_fav = new Intent(getApplicationContext(),FavoriteScreen.class);
                        startActivity(intent_fav);
                        return true;

                    case R.id.nav_cal:
                        Toast.makeText(getApplicationContext(),"Yakında Sizlerle",Toast.LENGTH_LONG).show();
                        return true;

                    case R.id.nav_seb:
                        Toast.makeText(getApplicationContext(),"Yakında Sizlerle",Toast.LENGTH_LONG).show();
                        return true;

                    case R.id.nav_mev:
                        Toast.makeText(getApplicationContext(),"Yakında Sizlerle",Toast.LENGTH_LONG).show();
                        return true;

                    case R.id.nav_fish:
                        Toast.makeText(getApplicationContext(),"Yakında Sizlerle",Toast.LENGTH_LONG).show();
                        return true;

                    case R.id.nav_setting:
                        Intent intent_setting = new Intent(getApplicationContext(),SettingPage.class);
                        startActivity(intent_setting);
                        return true;

                    case R.id.nav_contact:
                        Intent intent_contact = new Intent(getApplicationContext(),ContactScreen.class);
                        startActivity(intent_contact);
                        return true;

                    case R.id.nav_help:
                        Intent intent_help= new Intent(getApplicationContext(),HelpScreen.class);
                        startActivity(intent_help);
                        return true;

                    case R.id.nav_logout:
                        logout();
                        return true;

                    default:
                }
                return true;
            }
        });
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

    public void logout(){
        // Alert builder

        AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(HomeScreen.this);

        CharSequence options[] = new CharSequence[]{String.valueOf(R.string.setting_logout),String.valueOf(R.string.setting_cancel)};

        builder.setTitle(R.string.setting_log);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

                if(i==0){

                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(getApplicationContext(),LoginScreen.class);
                    startActivity(intent);

                }

                if(i==1){




                }

            }
        });

        ////////////////////////////////////////////////////////

        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }


    @Override
    protected void onStop() {
        super.onStop();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


        Intent intent  = new Intent(getApplicationContext(),HomeScreen.class);
        startActivity(intent);

    }
}
