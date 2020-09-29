package uz.dilmurodjonov_a.mymusic;

import android.content.Context;
import android.media.MediaPlayer;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
/**
 * Create by Abbos Dilmurodjonov (AyDee) on 29.09.2020
 */

public class Song {
    private String name;
    private String time;
    private String size;
    private int rawId;

    public Song(String name, int rawId, Context context) {
        this.name = name;
        this.rawId = rawId;
        MediaPlayer mMediaPlayer = MediaPlayer.create(context, rawId);
        this.time = getTime(mMediaPlayer);

        InputStream file = context.getResources().openRawResource(rawId);
        long length = 0;
        try {
            length = file.available();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DecimalFormat df = new DecimalFormat("#.##");
        length = length / 1024;
        size = df.format((float)length/1024) + " MB";
    }

    private String getTime(MediaPlayer mMediaPlayer) {
        int i = mMediaPlayer.getDuration() / 1000;
        mMediaPlayer.release();
        int minute = i / 60;
        int second = i % 60;
        String time = "";
        if (minute / 10 == 0) time += "0";
        time += minute + ":";
        if (second / 10 == 0) time += "0";
        time += second;
        return time;
    }

    public int getRawId() {
        return rawId;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getSize() {
        return size;
    }
}
