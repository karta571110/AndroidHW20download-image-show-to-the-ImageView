package com.example.androidhw20;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

ImageView imageView;
private ProgressDialog progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);

        final Button inputbtn = (Button) findViewById(R.id.inputWeb);

        inputbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyTask().execute("https://i.imgur.com/AD3MbBi.jpg");
            }
        });

    }

    class MyTask extends AsyncTask<String, Integer, Bitmap> { //AsyncTask<傳入值型態, 更新進度型態, 結果型態>
        @Override
        protected void onPreExecute() {
            //執行前 設定可以在這邊設定
            super.onPreExecute();
            Log.d("Tag onPreExecute", String.valueOf(Thread.currentThread().getId()));
            progressBar = new ProgressDialog(MainActivity.this);
            progressBar.setMessage("Loading…");
            progressBar.setCancelable(false);
            progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressBar.show();
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            //執行中 在背景做事情
            Log.d("Tag doInBackground", String.valueOf(Thread.currentThread().getId()));
            int progress = 0;


            String urlStr = params[0];
            try {
                URL url = new URL(urlStr);
                return BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
            publishProgress(progress+=100);
            publishProgress(100);
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            //執行中 可以在這邊告知使用者進度
            super.onProgressUpdate(values);
            Log.d("Tag onProgressUpdate", String.valueOf(Thread.currentThread().getId()));
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            //執行後 完成背景任務
            super.onPostExecute(bitmap);
            Log.d("Tag onPostExecute", String.valueOf(Thread.currentThread().getId()));
            imageView.setImageBitmap(bitmap);
            progressBar.dismiss();
        }
    }


}
