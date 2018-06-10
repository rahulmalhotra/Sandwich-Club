package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private Sandwich sandwich;
    private TextView also_known_tv, description_tv, ingredients_tv, origin_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        also_known_tv = findViewById(R.id.also_known_tv);
        ingredients_tv = findViewById(R.id.ingredients_tv);
        description_tv = findViewById(R.id.description_tv);
        origin_tv = findViewById(R.id.origin_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        List<String> getAlsoKnownAs = sandwich.getAlsoKnownAs();
        if(getAlsoKnownAs.size()>0 && getAlsoKnownAs!=null) {
            also_known_tv.setText(listToString(sandwich.getAlsoKnownAs()));
        } else {
            also_known_tv.setText(R.string.no_info_available);
        }
        if(sandwich.getDescription()!=null && !sandwich.getDescription().equals("")) {
            description_tv.setText(sandwich.getDescription());
        } else {
            description_tv.setText(R.string.no_info_available);
        }
        if(sandwich.getPlaceOfOrigin()!=null && !sandwich.getPlaceOfOrigin().equals("")) {
            origin_tv.setText(sandwich.getPlaceOfOrigin());
        } else {
            origin_tv.setText(R.string.no_info_available);
        }
        if(sandwich.getIngredients()!=null && sandwich.getIngredients().size()>0) {
            ingredients_tv.setText(listToString(sandwich.getIngredients()));
        } else {
            ingredients_tv.setText(R.string.no_info_available);
        }
    }

    private String listToString(List<String> stringList) {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0; i<stringList.size(); i++) {
            stringBuilder.append(stringList.get(i)).append("\n");
        }
        stringBuilder.replace(stringBuilder.lastIndexOf("\n"),stringBuilder.length(),"");
        return stringBuilder.toString();
    }
}
