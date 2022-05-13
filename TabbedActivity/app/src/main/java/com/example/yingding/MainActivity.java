package com.example.yingding;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;

import com.example.yingding.ui.main.NoSwipViewPager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

// import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.yingding.ui.main.SectionsPagerAdapter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private String file;
    private String path;
    private String imgName = "dumpfile.bin";
    private final String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private final int REQUEST_PERMISSION = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        NoSwipViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);
        //下面这句加上会自动创建/mnt/media_rw/FAE7-F901/Android/data/com.example.yingding/files文件夹
        this.path = getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString();
//        this.file = this.path + "/" + this.imgName;
        //TODO: change hard-code path
//        this.file = "/mnt/media_rw/FAE7-F901/Android/data/com.example.yingding/files";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            this.file = getExternalCacheDirs()[1].getAbsolutePath() + "/" + imgName;
        }
        checkPermission();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                saveFile(file);
                bufferWriteTest();
            }
        });
    }

    private void checkPermission(){
        if (isGranted()){

        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(PERMISSIONS, REQUEST_PERMISSION);
            }
        }
    }


    private boolean isGranted(){
        for (int i = 0; i < PERMISSIONS.length; i++){
            //The first time is PERMISSION_DENIED returns
            if (checkSelfPermission(PERMISSIONS[i]) != PackageManager.PERMISSION_GRANTED) {
                //Returns true if the request is rejected once. Returns false for the first time or when "Do not show again" is selected.
                if (shouldShowRequestPermissionRationale(PERMISSIONS[i])) {
                    Toast.makeText(this, "Permission required to run the app", Toast.LENGTH_LONG).show();
                }
                return false;
            }
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION){
            checkPermission();
        }
    }

    private void bufferWriteTest() {
        char[] chars = new char[20*1024*1024];
        Arrays.fill(chars, 'A');
        String text = new String(chars);
        long start = System.nanoTime();
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bw.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long time = System.nanoTime() - start;
//        System.out.println("Wrote " + chars.length*1000L/time+" MB/s.");
        Log.d("dmzhu", "bufferWriteTest: " + chars.length*1000L/time+" MB/s.");
    }

    private void saveFile(String file){
        try {
            AssetManager assetManager = getResources().getAssets();
            //added by dmzhu for test
//            String[] fileNames =getAssets().list("myFolder");
//            for(String name:fileNames){
//                System.out.println(name);
//            }
            String assertFile = "myFolder/" + "systrace.pdf";
            InputStream inputStream = assetManager.open(assertFile);

            //dmzhu, calc time escape
            long start = System.currentTimeMillis();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024*4];
            while (true){
                int len = inputStream.read(buffer);
                if (len < 0){
                    break;
                }
                fileOutputStream.write(buffer, 0, len);
            }
            fileOutputStream.flush();
            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;
            Log.d("dmzhu", "saveFile: cost:" + timeElapsed + "ms");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}