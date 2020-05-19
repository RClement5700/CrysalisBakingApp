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
    private StepFragment nextFragment;
    private StepFragment previousFragment;
    private int position;

    public StepFragment() {
        //required empty constructor
    }

    public StepFragment(StepFragment nextFragment, StepFragment previousFragment) {
        this.nextFragment = nextFragment;
        this.previousFragment = previousFragment;
    }

    public StepFragment(int position) {
        this.position = position;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this, v);
        ArrayList<Step> steps = new ArrayList<>();
        int position = 0;
        String description = null;
        String thumbnailUrl;
        String videoUrl;
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
//            description = getArguments().getString("description");
//            thumbnailUrl = getArguments().getString("thumbnailUrl");
//            videoUrl = getArguments().getString("videoUrl");
        }

        tv_step_instruction.setText(description);
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
