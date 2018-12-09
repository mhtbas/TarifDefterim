package com.project.muhammedbas.tarifdefterim;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.muhammedbas.tarifdefterim.Utils.RecipeList;

public class RecipeScreen extends AppCompatActivity {

    private String categoryname;
    private RecyclerView recipeRecylerView;

    private String currentUser;
    private DatabaseReference mRecipeDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_screen);

        init();


    }

    private void init() {

        categoryname= getIntent().getStringExtra("recipecategory");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(categoryname);


        recipeRecylerView=findViewById(R.id.recipescreenrcView);
        recipeRecylerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recipeRecylerView.setHasFixedSize(true);

        currentUser=FirebaseAuth.getInstance().getCurrentUser().getUid();
        mRecipeDatabase=FirebaseDatabase.getInstance().getReference().child("recipes").child(currentUser).child(categoryname);


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

        FirebaseRecyclerOptions<RecipeList> options = new FirebaseRecyclerOptions.Builder<RecipeList>()
                .setQuery(mRecipeDatabase,RecipeList.class).build();


        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<RecipeList,RecipeViewHolder>(options){

            @Override
            protected void onBindViewHolder(@NonNull RecipeViewHolder holder, int position, @NonNull final RecipeList model) {

                String id=getRef(position).getKey();

                holder.setRecipeCookTime(model.getCookingTime());
                holder.setRecipename(model.getRecipeName());
                holder.setRecipePerson(model.getPersonCount());
                holder.setRecipePreTime(model.getPreparationTime());



                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getApplicationContext(),RecipeFullScreen.class);
                        intent.putExtra("personCount",model.getPersonCount());
                        intent.putExtra("preparationTime",model.getPreparationTime());
                        intent.putExtra("cookingTime",model.getCookingTime());
                        intent.putExtra("category",model.getCategory());
                        intent.putExtra("recipeName",model.getRecipeName());
                        intent.putExtra("materials",model.getMaterials());
                        intent.putExtra("preparation",model.getPreparation());
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View recipe =LayoutInflater.from(getApplicationContext())
                        .inflate(R.layout.recipescreen_view,viewGroup,false);

                return  new RecipeViewHolder(recipe);
            }

        };

        recipeRecylerView.setAdapter(adapter);
        adapter.startListening();
    }

    private class RecipeViewHolder extends  RecyclerView.ViewHolder{

        private View mView;
        private TextView recipename,recipecooktime,recipepretime,recipeperson;


        public RecipeViewHolder(View itemView) {
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


    @Override
    protected void onStop() {
        super.onStop();
    }
}
