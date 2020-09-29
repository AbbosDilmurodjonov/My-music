package uz.dilmurodjonov_a.mymusic;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
/**
 * Create by Abbos Dilmurodjonov (AyDee) on 29.09.2020
 */

public class SplashScreen extends Fragment {

    public static SplashScreen newInstance() {
        return new SplashScreen();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int SPLASH_DISPLAY_LENGTH = 2000;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                assert getFragmentManager() != null;
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .replace(R.id.fragment_container, MusicList.newInstance())
                        .commit();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}