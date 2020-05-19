package crysalis.example.crysalisbakingapp;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class IngredientsFragment extends Fragment {

    @BindView(R.id.tv_ingredients_view) TextView tv_ingredients_view;
    @BindView(R.id.rv_steps) RecyclerView rv_steps;
    @BindView(R.id.img_btn_close_ingredients) ImageButton img_btn_close_ingredients;

    public IngredientsFragment() {
        //empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ingredients, container, false);
        ButterKnife.bind(this, v);
        ArrayList<Step> steps = new ArrayList<>();
        Bundle bundle = getArguments();
        if (getArguments() != null) {
            String ingredients = getArguments().getString("ingredients");
            tv_ingredients_view.setText(ingredients);
            steps = (ArrayList<Step>) getArguments().getSerializable("steps");
        }
        LinearLayoutManager llm = new LinearLayoutManager(this.getActivity());
        rv_steps = v.findViewById(R.id.rv_steps);
        rv_steps.setLayoutManager(llm);
        rv_steps.setAdapter(new StepRecyclerViewAdapter(steps, bundle,
                getFragmentManager()));
        img_btn_close_ingredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFragmentManager() != null) {
                    getFragmentManager()
                            .beginTransaction()
                            .remove(IngredientsFragment.this)
                            .commit();
                }
            }
        });
        return v;
    }
}
