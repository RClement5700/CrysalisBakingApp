package crysalis.example.crysalisbakingapp;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class StepFragment extends Fragment {

    @BindView(R.id.img_btn_close_step) ImageButton img_btn_close_step;
    @BindView(R.id.tv_step_instruction) TextView tv_step_instruction;
    @BindView(R.id.previous_btn) Button previous_btn;
    @BindView(R.id.next_btn) Button next_btn;


    public StepFragment() {
        //required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this, v);
        container.bringToFront();
        ArrayList<Step> steps;
        int position;
        String description = null;
        String thumbnailUrl;
        String videoUrl;
        StepFragment nextFragment;
        StepFragment previousFragment;
        final int nextPosition;
        final int previousPosition;

        if (getFragmentManager() != null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.vid_view_container, new VideoViewFragment())
                    .commit();
        }
        if (getArguments() != null) {
            steps = (ArrayList<Step>) getArguments().getSerializable("steps");
            position = getArguments().getInt("position");
            System.err.println("position: " + position);
            description = steps.get(position).description;
//            thumbnailUrl = steps.get(position).thumbnailUrl;
//            videoUrl = steps.get(position).videoUrl;

            if (position == 0) {
                previousPosition = steps.size() - 1;
                nextPosition = 1;
            }
            else if (position == steps.size() - 1) {
                previousPosition = steps.size() - 2;
                nextPosition = 0;
            }

            else {
                previousPosition = position - 1;
                nextPosition = position + 1;
            }
            tv_step_instruction.setText(description);
            next_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = getArguments();
                    bundle.remove("position");
                    bundle.putInt("position", nextPosition);
                    System.err.println("nextPosition: " + nextPosition);
                    StepFragment stepFragment = new StepFragment();
                    stepFragment.setArguments(bundle);
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.step_container, stepFragment)
                            .commit();
                }
            });
            previous_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = getArguments();
                    bundle.remove("position");
                    bundle.putInt("position", previousPosition);
                    System.err.println("previousPosition: " + previousPosition);
                    StepFragment stepFragment = new StepFragment();
                    stepFragment.setArguments(bundle);
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.step_container, stepFragment)
                            .commit();
                }
            });
        }
        img_btn_close_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager()
                        .beginTransaction()
                        .remove(StepFragment.this)
                        .commit();
            }
        });
        return v;
    }

}
