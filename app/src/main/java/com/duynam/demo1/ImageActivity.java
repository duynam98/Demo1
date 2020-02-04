package com.duynam.demo1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duynam.demo1.adapter.ImageAdapter;
import com.duynam.demo1.adapter.SearchImageAdapter;
import com.duynam.demo1.fragment.FragmentImage;
import com.duynam.demo1.fragment.FragmentListImageDownload;
import com.duynam.demo1.model.Image;
import com.duynam.demo1.utils.Constains;
import com.duynam.demo1.utils.FileCache;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class ImageActivity extends AppCompatActivity implements SearchImageAdapter.OnItemClickListener {

    private RecyclerView rvList;
    private List<Image> imageList;
    private ImageAdapter adapter;
    private static final String client_id = "c9a6c8ae8c5818ef6d710bf66a2ed1add973a00a796ab9d559a0b47a935909be";
    private int page = 1;
    private int per_page = 20;
    private GridLayoutManager gridLayoutManager;
    private FrameLayout container;
    private BottomNavigationView navBottom;
    private FragmentTransaction transaction;
    private SearchView searchView;
    private FragmentImage fragmentImage;
    private FragmentListImageDownload fragmentListImageDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        initView();

        fragmentImage = new FragmentImage();
        fragmentListImageDownload = new FragmentListImageDownload();

        navBottom.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, fragmentImage);
        transaction.commit();

        gridLayoutManager = new GridLayoutManager(this, 2);


    }


    private void initView() {
        container = findViewById(R.id.container);
        navBottom = findViewById(R.id.nav_bottom);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fragmentImage.imageList.clear();
                fragmentImage.search = true;
                if (fragmentImage != null) {
                    fragmentImage.search(query);
                    searchView.clearFocus();
                    FileCache.create(ImageActivity.this, query, Constains.cacheName);
                    searchView.clearFocus();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        return true;
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.menu_background:
                            transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.container, fragmentImage);
                            transaction.commit();
                            break;
                        case R.id.menu_mine:
                            transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.container, fragmentListImageDownload);
                            transaction.commit();
                            break;
                    }
                    return true;
                }
            };


    @Override
    public void OnItemClick(String displayurl, String downloadurl) {
        Intent intent = new Intent(this, DetailsImageActivity.class);
        intent.putExtra("show", displayurl);
        intent.putExtra("down", downloadurl);
        startActivity(intent);
    }


}
