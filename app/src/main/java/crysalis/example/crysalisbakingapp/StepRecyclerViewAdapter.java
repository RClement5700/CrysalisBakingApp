package crysalis.example.crysalisbakingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepRecyclerViewAdapter
        extends RecyclerView.Adapter<StepRecyclerViewAdapter.StepRecyclerViewHolder> {

    ArrayList<Step> steps;
    Bundle bundle;
    FragmentManager fragmentManager;

    public StepRecyclerViewAdapter(ArrayList<Step> steps, Bundle bundle,
                                   FragmentManager fragmentManager) {
        this.steps = steps;
        this.bundle = bundle;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public StepRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.step_item_view, parent,false);
        return new StepRecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StepRecyclerViewHolder holder, final int position) {
        holder.tv_step_description.setText(steps.get(position).shortDescription);
        holder.setPosition(position);
//        bundle.putString("videoUrl", steps.get(position).videoUrl);
//        bundle.putString("thumbnalUrl", steps.get(position).thumbnailUrl);
//        bundle.putString("description", steps.get(position).description);
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    class StepRecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_step_description) TextView tv_step_description;
        int position;

        public StepRecyclerViewHolder(@NonNull final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                StepFragment stepFragment = new StepFragment();
                    bundle.putInt("position", position);
                    stepFragment.setArguments(bundle);
                    fragmentManager.beginTransaction()
                    .add(R.id.step_container, stepFragment)
                    .commit();
                }
            });
        }
        void setPosition(int position) {
            this.position = position;
        }
    }
}
