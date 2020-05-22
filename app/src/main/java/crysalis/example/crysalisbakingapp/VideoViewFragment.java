package crysalis.example.crysalisbakingapp;


import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoViewFragment extends Fragment {

    @BindView(R.id.iv_placeholder_image) ImageView placeholder_image;
    @BindView(R.id.video_view) PlayerView videoView;

    public VideoViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_video_view, container, false);
        ButterKnife.bind(this, v);
        if (getArguments() != null) {
            String videoPath = getArguments().getString("videoUrl");
            if (!videoPath.isEmpty()) {
                placeholder_image.setVisibility(View.INVISIBLE);
                videoView.setVisibility(View.VISIBLE);
                videoView.bringToFront();
                if (getActivity() != null) {
                    SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(getActivity());
                    String userAgent = Util.getUserAgent(getActivity(),
                            getActivity().getResources().getString(R.string.app_name));
//                DefaultDataSourceFactory defdataSourceFactory =
//                        new DefaultDataSourceFactory(getActivity(), userAgent);
                    DataSource.Factory dataSourceFactory =
                            new DefaultDataSourceFactory(getActivity(), userAgent);
                    Uri uriOfContentUrl = Uri.parse(videoPath);
                    System.err.println("videoPath: " + videoPath);
                    MediaSource mediaSource = new ProgressiveMediaSource
                            .Factory(dataSourceFactory)
                            .createMediaSource(uriOfContentUrl);
                    player.prepare(mediaSource);
                    player.setPlayWhenReady(true);
                    videoView.setPlayer(player);
                }
            }

            else {
                placeholder_image.setVisibility(View.VISIBLE);
                videoView.setVisibility(View.INVISIBLE);
                placeholder_image.bringToFront();
            }
        }
        return v;
    }

}
