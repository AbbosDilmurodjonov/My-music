package uz.dilmurodjonov_a.mymusic;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * Create by Abbos Dilmurodjonov (AyDee) on 29.09.2020
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = SplashScreen.newInstance();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

    }
}