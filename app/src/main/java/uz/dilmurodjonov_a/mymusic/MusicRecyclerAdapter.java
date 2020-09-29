package uz.dilmurodjonov_a.mymusic;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
/**
 * Create by Abbos Dilmurodjonov (AyDee) on 29.09.2020
 */

public class MusicRecyclerAdapter extends RecyclerView.Adapter<MusicRecyclerAdapter.MyViewHolder> {
    private Context context;
    private List<Song> myList;
    private MusicList fragment;

    public MusicRecyclerAdapter(Context context, List<Song> myList, MusicList fragment) {
        this.context = context;
        this.myList = myList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_music, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.name.setText(myList.get(position).getName());
        holder.size.setText(myList.get(position).getSize());
        holder.time.setText(myList.get(position).getTime());

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.play(position);
            }
        });

        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (isStoragePermissionGranted()) copyMusic(myList.get(position));
                    else
                        Toast.makeText(context, "Permission is revoked", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(Objects.requireNonNull(fragment.getActivity()), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else {
            return true;
        }
    }

    private void copyMusic(Song song) throws IOException {
        Toast.makeText(context, "File Downloading...", Toast.LENGTH_SHORT).show();

        InputStream in = context.getResources().openRawResource(song.getRawId());

        String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();

        FileOutputStream out = new FileOutputStream(dir + File.separator + song.getName() + ".mp3");

        byte[] buff = new byte[1024];

        int read;

        try {

            while ((read = in.read(buff)) > 0) {

                out.write(buff, 0, read);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            in.close();
            out.close();
            Toast.makeText(context, "Complete!", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public int getItemCount() {
        return myList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView time;
        public TextView size;
        public RelativeLayout parent;
        public ImageView download;

        public MyViewHolder(@NonNull View v) {
            super(v);

            name = v.findViewById(R.id.name_text);
            time = v.findViewById(R.id.music_time);
            size = v.findViewById(R.id.music_size);
            download = v.findViewById(R.id.download_icon);
            parent = v.findViewById(R.id.relative);
        }
    }
}
