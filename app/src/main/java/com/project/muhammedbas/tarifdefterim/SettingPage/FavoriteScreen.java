package com.project.muhammedbas.tarifdefterim.SettingPage;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.muhammedbas.tarifdefterim.R;
import com.project.muhammedbas.tarifdefterim.RecipeFullScreen;
import com.project.muhammedbas.tarifdefterim.Utils.FavList;
import com.project.muhammedbas.tarifdefterim.Utils.RecipeList;

public class FavoriteScreen extends AppCompatActivity {

    private RecyclerView favRecyler;
    private DatabaseReference favDatabase;
    private String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_favorite_screen);

        init();
    }

    private void init() {

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Favori Tariflerim");

        currentUser=FirebaseAuth.getInstance().getCurrentUser().getUid();
        favDatabase=FirebaseDatabase.getInstance().getReference().child("favorite").child(currentUser);

        favRecyler=findViewById(R.id.favrecyler);
        favRecyler.setHasFixedSize(true);
        favRecyler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
                this.finish();
                return true;

        }
        return super.onOptionsItemSelected(item);

    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<FavList> options = new FirebaseRecyclerOptions.Builder<FavList>()
                .setQuery(favDatabase,FavList.class).build();


        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<FavList,FavRecipeViewHolder>(options){

            @Override
            protected void onBindViewHolder(final FavRecipeViewHolder holder, int position, final FavList model) {

                final String id=getRef(position).getKey();
                String category =model.getCategory();

                FirebaseDatabase.getInstance().getReference().child("recipes").child(currentUser).child(category).child(id)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                final RecipeList recipeList = dataSnapshot.getValue(RecipeList.class);

                                holder.setRecipeCookTime(recipeList.getCookingTime());
                                holder.setRecipename(recipeList.getRecipeName());
                                holder.setRecipePerson(recipeList.getPersonCount());
                                holder.setRecipePreTime(recipeList.getPreparationTime());



                                holder.mView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Intent intent = new Intent(getApplicationContext(),RecipeFullScreen.class);
                                        intent.putExtra("personCount",recipeList.getPersonCount());
                                        intent.putExtra("preparationTime",recipeList.getPreparationTime());
                                        intent.putExtra("cookingTime",recipeList.getCookingTime());
                                        intent.putExtra("category",recipeList.getCategory());
                                        intent.putExtra("recipeName",recipeList.getRecipeName());
                                        intent.putExtra("materials",recipeList.getMaterials());
                                        intent.putExtra("preparation",recipeList.getPreparation());
                                        intent.putExtra("id",id);
                                        startActivity(intent);
                                    }
                                });

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });





            }

            @NonNull
            @Override
            public FavRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View recipe =LayoutInflater.from(getApplicationContext())
                        .inflate(R.layout.recipescreen_view,viewGroup,false);

                return  new FavRecipeViewHolder(recipe);

            }

        };

        favRecyler.setAdapter(adapter);
        adapter.startListening();


    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private class FavRecipeViewHolder extends  RecyclerView.ViewHolder{

        private View mView;
        private TextView recipename,recipecooktime,recipepretime,recipeperson;


        public FavRecipeViewHolder(View itemView) {
            super(itemView);
            mView=itemView;

            recipename = mView.findViewById(R.id.recipename);
            recipecooktime=mView.findViewById(R.id.cookingTime);
            recipepretime=mView.findViewById(R.id.preparationTime);
            recipeperson=mView.findViewById(R.id.personCount);


        }

        public void setRecipename(String recipenamestring){
            recipename.setText(recipenamestring);
        }

        public void setRecipeCookTime(String recipeCookTime){
            recipecooktime.setText(recipeCookTime);
        }

        public void setRecipePreTime(String recipePreTime){

            recipepretime.setText(recipePreTime);
        }

        public void setRecipePerson(String recipePerson){

            recipeperson.setText(recipePerson);
        }

    }
}
