package com.duynam.demo1.fragment;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duynam.demo1.R;
import com.duynam.demo1.adapter.LocalImageAdapter;

import java.io.File;
import java.util.ArrayList;

public class FragmentListImageDownload extends Fragment {

    ArrayList<String> f = new ArrayList<String>();
    File[] listFile;
    private RecyclerView rvListdownload;
    private LocalImageAdapter adapter;
    private ImageView imgImg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listimage_download, container, false);
        initView(view);
        getFromSdcard();

        adapter = new LocalImageAdapter(f, getContext());
        rvListdownload.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvListdownload.setAdapter(adapter);

        return view;
    }

    public void getFromSdcard() {
        f = new ArrayList<>();
        File file = new File(Environment.getExternalStorageDirectory(), "demo1");
        if (file.isDirectory()) {
            listFile = file.listFiles();
            Toast.makeText(getContext(), listFile.length + "", Toast.LENGTH_SHORT).show();
            for (int i = 0; i < listFile.length; i++) {
                f.add(listFile[i].getAbsolutePath());
            }
        }
    }

    private void initView(View view) {
        rvListdownload = view.findViewById(R.id.rv_listdownload);
    }
}
