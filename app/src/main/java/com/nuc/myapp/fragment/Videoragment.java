package com.nuc.myapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nuc.myapp.R;

public class Videoragment extends Fragment {

    private String title;

    public Videoragment(){
    }
    public static Videoragment newInstance(String title){

        Videoragment videoragment = new Videoragment();
        videoragment.title = title;
        return  videoragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_videoragment , container , false);
        TextView vt = v.findViewById(R.id.title);
        vt.setText(title);
        return v;
    }
}
