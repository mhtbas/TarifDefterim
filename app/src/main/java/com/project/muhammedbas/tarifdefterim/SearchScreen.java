package com.project.muhammedbas.tarifdefterim;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.project.muhammedbas.tarifdefterim.Utils.SearchList;

public class SearchScreen extends AppCompatActivity {

    private EditText searchEdit;
    private ImageView searchbutton;
    private String searchString;
    private String currentUser;
    private RecyclerView searchRecyclerView;
    private DatabaseReference mRecipeDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_screen);
        
        init();

        //////////////////// Search Click/////////////////////////////////

        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchString=searchEdit.getText().toString();
                search(searchString);
            }
        });


    }

    private void init() {

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Tarif Ara");

        searchEdit=findViewById(R.id.search_field);
        searchbutton=findViewById(R.id.search_btn);

        searchRecyclerView=findViewById(R.id.searchlist);
        searchRecyclerView.setHasFixedSize(true);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        currentUser=FirebaseAuth.getInstance().getCurrentUser().getUid();
        mRecipeDatabase=FirebaseDatabase.getInstance().getReference().child("recipes").child(currentUser);

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
    public void onStart() {
        super.onStart();

        search("");
    }

    public void search(String searchString) {

        Query querysearch =mRecipeDatabase.orderByChild("recipeName").startAt(searchString).endAt(searchString+"\uf8ff");

        FirebaseRecyclerOptions<SearchList> options = new FirebaseRecyclerOptions.Builder<SearchList>()
                .setQuery(querysearch,SearchList.class).build();


        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<SearchList,SearchRecipeViewHolder>(options){

            @Override
            protected void onBindViewHolder(SearchRecipeViewHolder holder, int position,final SearchList model) {


                String id=getRef(position).getKey();

                holder.setRecipeCookTime(model.getCookingTime());
                Log.d("searchsss",model.getRecipeName());
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

            @Override
            public SearchRecipeViewHolder onCreateViewHolder(ViewGroup parent, int i) {

                View recipe =LayoutInflater.from(getApplicationContext())
                        .inflate(R.layout.recipescreen_view,parent,false);

                return  new SearchRecipeViewHolder(recipe);

            }

        };

        searchRecyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    private class SearchRecipeViewHolder extends  RecyclerView.ViewHolder{

        private View mView;
        private TextView recipename,recipecooktime,recipepretime,recipeperson;


        public SearchRecipeViewHolder(View itemView) {
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
    public void onStop() {
        super.onStop();
    }


}
