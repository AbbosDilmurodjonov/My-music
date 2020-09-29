package uz.dilmurodjonov_a.mymusic;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
/**
 * Create by Abbos Dilmurodjonov (AyDee) on 29.09.2020
 */

public class MusicList extends Fragment {


    private TextView nameText;
    private TextView timeStartText;
    private TextView timeEndText;
    private SeekBar seekBar;
    private ImageView playImage;
    private ImageView nextImage;
    private ImageView prevImage;
    private List<Song> list = new ArrayList<>();
    private RelativeLayout relativeMusic;

    public static MusicList newInstance() {

        return new MusicList();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_music_list, container, false);

        nameText = v.findViewById(R.id.name_text);
        timeEndText = v.findViewById(R.id.music_time_end);
        timeStartText = v.findViewById(R.id.music_time_start);
        seekBar = v.findViewById(R.id.progress_horizontal);
        playImage = v.findViewById(R.id.play_icon);
        prevImage = v.findViewById(R.id.previous_icon);
        nextImage = v.findViewById(R.id.next_icon);
        relativeMusic = v.findViewById(R.id.relative_music);

        RecyclerView recyclerView = v.findViewById(R.id.recycler_view);


        list.add(new Song("Name 1", R.raw.audio_1, getActivity()));
        list.add(new Song("Name 2", R.raw.audio_2, getActivity()));
        list.add(new Song("Name 3", R.raw.audio_3, getActivity()));
        list.add(new Song("Name 4", R.raw.audio_4, getActivity()));
        list.add(new Song("Name 5", R.raw.audio_5, getActivity()));
        list.add(new Song("Name 6", R.raw.audio_6, getActivity()));
        list.add(new Song("Name 7", R.raw.audio_7, getActivity()));
        list.add(new Song("Name 8", R.raw.audio_8, getActivity()));
        list.add(new Song("Name 9", R.raw.audio_9, getActivity()));
        list.add(new Song("Name 10", R.raw.audio_10, getActivity()));


        MusicRecyclerAdapter adapter = new MusicRecyclerAdapter(getActivity(), list, this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }

    private MediaPlayer mMediaPlayer;

    public void play(final int position) {

        if (relativeMusic.getVisibility() == View.GONE) relativeMusic.setVisibility(View.VISIBLE);

        nextImage.setVisibility(View.VISIBLE);
        prevImage.setVisibility(View.VISIBLE);
        nameText.setText(list.get(position).getName());
        timeEndText.setText(list.get(position).getTime());
        timeStartText.setText("00:00");

        playImage.setImageResource(R.drawable.ic_baseline_pause_24);
        if (position + 1 == list.size()) nextImage.setVisibility(View.INVISIBLE);
        else if (position == 0) prevImage.setVisibility(View.INVISIBLE);

        stopAudio();
        mMediaPlayer = MediaPlayer.create(getActivity(), list.get(position).getRawId());
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopAudio();
                playImage.setImageResource(R.drawable.ic_baseline_play_arrow_24);
            }
        });
        seekBar.setMax(mMediaPlayer.getDuration() / 1000);
        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    if (mMediaPlayer != null) {
                        int mCurrentPosition = mMediaPlayer.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPosition);
                    }
                } catch (IllegalStateException e) {
                    timer.cancel();
                }
            }
        }, 0, 1000);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                if (mMediaPlayer != null && fromUser) {
                    mMediaPlayer.seekTo(i * 1000);
                }
                timeStartText.setText(getTime(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mMediaPlayer.start();

        nextImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play(position + 1);
            }
        });
        prevImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play(position - 1);
            }
        });

        playImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMediaPlayer != null) {
                    if (mMediaPlayer.isPlaying()) {
                        mMediaPlayer.pause();
                        playImage.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    } else {
                        mMediaPlayer.start();
                        playImage.setImageResource(R.drawable.ic_baseline_pause_24);
                    }
                }
            }
        });


    }

    private String getTime(int i) {
        int minute = i / 60;
        int second = i % 60;
        String time = "";
        if (minute / 10 == 0) time += "0";
        time += minute + ":";
        if (second / 10 == 0) time += "0";
        time += second;
        return time;
    }


    private void stopAudio() {
        try {
            if (mMediaPlayer != null)
                mMediaPlayer.release();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    @Override
    public void onPause() {
        super.onPause();
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            playImage.setImageResource(R.drawable.ic_baseline_play_arrow_24);
            mMediaPlayer.pause();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopAudio();
    }
}