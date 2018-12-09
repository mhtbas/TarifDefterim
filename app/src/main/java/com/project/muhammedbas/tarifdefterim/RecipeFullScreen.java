package com.project.muhammedbas.tarifdefterim;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class RecipeFullScreen extends AppCompatActivity {

    private String personCount;
    private String preparationTime;
    private String cookingTime;
    private String category;
    private String recipeName;
    private String materials;
    private String preparation;

    private TextView recipename,recipeperson,recipecook
            ,recipetime,recipematerials,recipeprepa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_full_screen);

        init();

        recipename.setText(recipeName);
        recipeperson.setText(personCount);
        recipecook.setText(cookingTime);
        recipetime.setText(preparationTime);
        recipematerials.setText(materials);
        recipeprepa.setText(preparation);

    }

    private void init() {

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        personCount=intent.getStringExtra("personCount");
        preparationTime=intent.getStringExtra("preparationTime");
        cookingTime=intent.getStringExtra("cookingTime");
        category=intent.getStringExtra("category");
        recipeName=intent.getStringExtra("recipeName");
        materials=intent.getStringExtra("materials");
        preparation=intent.getStringExtra("preparation");

        recipename=findViewById(R.id.recipename);
        recipeperson=findViewById(R.id.recipeperson);
        recipecook=findViewById(R.id.recipecooktime);
        recipetime=findViewById(R.id.recipetime);
        recipematerials=findViewById(R.id.recipemate);
        recipeprepa=findViewById(R.id.recipeprepa);



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
}
