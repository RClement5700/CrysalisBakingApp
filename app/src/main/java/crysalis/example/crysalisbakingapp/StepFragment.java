package crysalis.example.crysalisbakingapp;


import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepFragment extends Fragment {
    @BindView(R.id.img_btn_close_step) ImageButton img_btn_close_step;
    @Nullable
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
        String description;
        final String thumbnailUrl;
        final String videoUrl;
        final String nextThumbnailUrl;
        final String nextVideoUrl;
        final String previousThumbnailUrl;
        final String previousVideoUrl;
        final int nextPosition;
        final int previousPosition;

        if (getArguments() != null) {
            steps = (ArrayList<Step>) getArguments().getSerializable("steps");
            position = getArguments().getInt("position");
            description = steps.get(position).description;
            thumbnailUrl = steps.get(position).thumbnailUrl;
            videoUrl = steps.get(position).videoUrl;
            if (getFragmentManager() != null) {
                Bundle bundle = getArguments();
                bundle.putString("thumbnailUrl", thumbnailUrl);
                bundle.putString("videoUrl", videoUrl);
                VideoViewFragment vvFragment = new VideoViewFragment();
                vvFragment.setArguments(bundle);
                getFragmentManager()
                        .beginTransaction()
                        .add(R.id.vid_view_container, vvFragment)
                        .commit();
            }

            if (position == 0) {
                previousPosition = steps.size() - 1;
                nextPosition = 1;
                nextVideoUrl = steps.get(nextPosition).videoUrl;
                nextThumbnailUrl = steps.get(nextPosition).thumbnailUrl;
                previousVideoUrl = steps.get(previousPosition).videoUrl;
                previousThumbnailUrl = steps.get(previousPosition).thumbnailUrl;

            }
            else if (position == steps.size() - 1) {
                previousPosition = steps.size() - 2;
                nextPosition = 0;
                nextVideoUrl = steps.get(nextPosition).videoUrl;
                nextThumbnailUrl = steps.get(nextPosition).thumbnailUrl;
                previousVideoUrl = steps.get(previousPosition).videoUrl;
                previousThumbnailUrl = steps.get(previousPosition).thumbnailUrl;
            }

            else {
                previousPosition = position - 1;
                nextPosition = position + 1;
                nextVideoUrl = steps.get(nextPosition).videoUrl;
                nextThumbnailUrl = steps.get(nextPosition).thumbnailUrl;
                previousVideoUrl = steps.get(previousPosition).videoUrl;
                previousThumbnailUrl = steps.get(previousPosition).thumbnailUrl;
            }
            if (tv_step_instruction != null) {
                tv_step_instruction.setText(description);
            }
            next_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    directionButtonPressed(nextVideoUrl, nextThumbnailUrl, nextPosition);
                }
            });
            previous_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    directionButtonPressed(previousVideoUrl, previousThumbnailUrl, previousPosition);
                }
            });
        }
        img_btn_close_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeStep(view);
            }
        });
        return v;
    }

    private void closeStep(View v) {
        if (getFragmentManager() != null) {
            getFragmentManager()
                    .beginTransaction()
                    .remove(StepFragment.this)
                    .commit();
        }
    }

    private void directionButtonPressed(String videoUrl, String thumbnailUrl, int position) {

        Bundle bundle = getArguments();
        bundle.remove("position");
        bundle.remove("thumbnailUrl");
        bundle.remove("videoUrl");
        bundle.putInt("position", position);
        if (thumbnailUrl.length() > 0) {
            bundle.putString("thumbnailUrl", thumbnailUrl);
        }
        else {
            bundle.putString("thumbnailUrl", null);
        }
        if (videoUrl.length() > 0) {
            bundle.putString("videoUrl", videoUrl);
            System.err.println("videoUrl: " + videoUrl);
        }
        else {
            bundle.putString("videoUrl", null);
        }
        VideoViewFragment vvFragment = new VideoViewFragment();
        StepFragment stepFragment = new StepFragment();
        vvFragment.setArguments(bundle);
        stepFragment.setArguments(bundle);
        if (isTablet(getActivity())) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.step_container_tablet, stepFragment)
                    .replace(R.id.vid_view_container, vvFragment)
                    .commit();
        }
        else {
            stepFragment.setArguments(bundle);
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.step_container, stepFragment)
                    .replace(R.id.vid_view_container, vvFragment)
                    .commit();
        }
    }

    private boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

}
