package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        try {

            JSONObject sandwichJSON = new JSONObject(json);
            JSONObject sandwichNameObj = sandwichJSON.getJSONObject("name");
            JSONArray alsoKnownAsJSONArray = sandwichNameObj.getJSONArray("alsoKnownAs");
            JSONArray ingredientsJSONArray = sandwichJSON.getJSONArray("ingredients");
            List<String> alsoKnownAs = jsonArrayToStringList(alsoKnownAsJSONArray);
            List<String> ingredients = jsonArrayToStringList(ingredientsJSONArray);
            Sandwich sandwichObj = new Sandwich(sandwichNameObj.getString("mainName"), alsoKnownAs, sandwichJSON.getString("placeOfOrigin"), sandwichJSON.getString("description"), sandwichJSON.getString("image"), ingredients);
            return sandwichObj;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<String> jsonArrayToStringList(JSONArray jsonArray) {
        List<String> stringList = new ArrayList<String>();
        try {
            for(int i=0; i<jsonArray.length(); i++) {
                stringList.add(jsonArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stringList;
    }
}
