package crysalis.example.crysalisbakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.res.Configuration;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_recipes) RecyclerView rv_recipes;
    private final ArrayList<Recipe> recipes = new ArrayList<Recipe>();
    private static final String RECIPE_URL =
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        loadRecipes();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
    }

    public void loadRecipes() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, RECIPE_URL,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray recipesArray = new JSONArray(response);
                        for (int i = 0; i < recipesArray.length(); i++) {
                            JSONObject recipeObject = recipesArray.getJSONObject(i);
                            String name = recipeObject.getString("name");
                            int id = recipeObject.getInt("id");
                            ArrayList<Ingredient> ingredients = new ArrayList<>();
                            JSONArray ingredientsArray =
                                    recipeObject.getJSONArray("ingredients");
                            for (int j = 0; j < ingredientsArray.length(); j++) {
                                JSONObject currentIngredient = ingredientsArray.getJSONObject(j);
                                String ingredient =
                                        currentIngredient.getString("ingredient");
                                String measure = currentIngredient.getString("measure");;
                                int quantity = currentIngredient.getInt("quantity");
                                Ingredient newIngredient =
                                        new Ingredient(ingredient, measure, quantity);
                                ingredients.add(newIngredient);
                            }

                            ArrayList<Step> steps = new ArrayList<>();
                            JSONArray stepsArray =
                                    recipeObject.getJSONArray("steps");
                            for (int x = 0; x < stepsArray.length(); x++) {
                                JSONObject currentStep = stepsArray.getJSONObject(x);
                                int stepId = currentStep.getInt("id");
                                String shortDescription =
                                        currentStep.getString("shortDescription");
                                String description =
                                        currentStep.getString("description");
                                String videoUrl =
                                        currentStep.getString("videoURL");
                                String thumbnailUrl =
                                        currentStep.getString("thumbnailURL");
                                Step newStep =
                                        new Step(stepId, shortDescription, description,
                                                videoUrl, thumbnailUrl);
                                steps.add(newStep);
                            }
                            Recipe recipe = new Recipe(name, id, ingredients, steps);
                            recipes.add(recipe);
                        }
                        RecipeRecyclerViewAdapter adapter = new RecipeRecyclerViewAdapter(
                                recipes, new Bundle(), getSupportFragmentManager());
                        Configuration newConfig = getResources().getConfiguration();

                        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            rv_recipes.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
                        }
                        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                            rv_recipes.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        }
                        rv_recipes.setAdapter(adapter);
                    }
                    catch(JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);
    }
}
