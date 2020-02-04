package com.duynam.demo1;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

public class DetailsImageActivity extends AppCompatActivity {

    private ImageView imgDetails;
    String showImage, downImage;
    private RelativeLayout downloadImage;
    private TextView tvPhantram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_image);
        initView();
        getLink();
        Glide.with(this).load(showImage).into(imgDetails);

        downloadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dowloadImage();
            }
        });
    }

    private void getLink() {
        showImage = getIntent().getStringExtra("show");
        downImage = getIntent().getStringExtra("down");
    }

    private void dowloadImage(){
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(showImage);
    }

    private void initView() {
        imgDetails = findViewById(R.id.img_details);
        downloadImage = findViewById(R.id.downloadImage);
        tvPhantram = findViewById(R.id.tv_phantram);
    }

    class DownloadTask extends AsyncTask<String, Integer, String> {
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(DetailsImageActivity.this);
            dialog.setMessage("Downloading ... ");
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setMax(100);
            dialog.setProgress(0);
            //dialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String path = strings[0];
            int file_length = 0;
            try {
                URL url = new URL(path);
                URLConnection urlConnection = url.openConnection();
                urlConnection.connect();
                file_length = urlConnection.getContentLength();
                File new_folder = new File("sdcard/demo1");
                if (!new_folder.exists()) {
                    new_folder.mkdir();
                }

                File input_file = new File(new_folder, "Image_download" + UUID.randomUUID().toString() + ".jpg");
                InputStream inputStream = new BufferedInputStream(url.openStream(), 8129);
                byte[] data = new byte[1024];
                int total = 0;
                int count = 0;
                OutputStream outputStream = new FileOutputStream(input_file);
                while ((count = inputStream.read(data)) != -1) {
                    total += count;
                    outputStream.write(data, 0, count);
                    int progress = total * 100 / file_length;
                    publishProgress(progress);
                }
                inputStream.close();
                outputStream.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Download complete";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            dialog.setProgress(values[0]);
            tvPhantram.setText(values[0]+"%");
        }

        @Override
        protected void onPostExecute(String aVoid) {
            dialog.hide();
            tvPhantram.setText("");
        }
    }

}
