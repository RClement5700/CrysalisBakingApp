package crysalis.example.crysalisbakingapp;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeRecyclerViewAdapter
        extends RecyclerView.Adapter<RecipeRecyclerViewAdapter.RecipeRecyclerViewHolder> {

    ArrayList<Recipe> recipes;
    Bundle bundle;
    FragmentManager fragmentManager;

    public RecipeRecyclerViewAdapter(ArrayList<Recipe> recipes, Bundle bundle,
                                     FragmentManager fragmentManager) {
        this.recipes = recipes;
        this.bundle = bundle;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public RecipeRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.recipe_item_view, parent,false);
        return new RecipeRecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeRecyclerViewHolder holder, int position) {
        ArrayList<Ingredient> currentIngredients =
                recipes.get(position).ingredients;
        StringBuilder listedIngredientsBuilder = new StringBuilder();
        for (int j = 0; j < currentIngredients.size(); j++) {
            listedIngredientsBuilder.append(currentIngredients.get(j).ingredient);
            listedIngredientsBuilder.append(", ");
        }
        String listedIngredients = listedIngredientsBuilder.toString();
        holder.tv_recipe_ingredients.setText(listedIngredients);
        holder.tv_recipe_name.setText(recipes.get(position).name);
        holder.setSteps(recipes.get(position).steps);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    class RecipeRecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_recipe_name) TextView tv_recipe_name;
        @BindView(R.id.tv_recipe_ingredients) TextView tv_recipe_ingredients;
        private ArrayList<Step> steps;

        public RecipeRecyclerViewHolder(@NonNull final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bundle.putSerializable("steps", steps);
                    bundle.putString("ingredients", tv_recipe_ingredients.getText().toString());
                    bundle.putInt("position", 0);
                    IngredientsFragment ingredientsFragment =
                            new IngredientsFragment();
                    StepFragment stepFragment = new StepFragment();
                    ingredientsFragment.setArguments(bundle);
                    stepFragment.setArguments(bundle);
                    fragmentManager.beginTransaction()
                            .add(R.id.ingredients_container, ingredientsFragment)
                            .commit();
                    if(isTablet(itemView.getContext())) {
                        fragmentManager.beginTransaction()
                                .add(R.id.step_container_tablet, stepFragment)
                                .commit();
                    }
                }
            });
        }

        private void setSteps(ArrayList<Step> steps) {
            this.steps = steps;
        }

        private boolean isTablet(Context context) {
            boolean xlarge = ((context.getResources().getConfiguration().screenLayout &
                    Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
            boolean large = ((context.getResources().getConfiguration().screenLayout &
                    Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
            return (xlarge || large);
        }
    }
}
