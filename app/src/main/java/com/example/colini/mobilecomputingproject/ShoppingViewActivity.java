package com.example.colini.mobilecomputingproject;

/**
 * Created by JoeyFarrell on 2016-03-06.
 */

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class ShoppingViewActivity extends AppCompatActivity implements SurfaceHolder.Callback{
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    Camera camera;
    DrawFrame frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoppingview_layout); //choose XML file
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); //add the toolbar to the top
        setSupportActionBar(toolbar);

        surfaceView = (SurfaceView) findViewById(R.id.mySurface); //view for the camera
        surfaceHolder = surfaceView.getHolder();

        surfaceHolder.addCallback(this); //to display the camera

        frame = (DrawFrame) findViewById(R.id.myView); //draws a rectangle on the screen. frame to find the barcode in

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        try{
            camera = Camera.open(); //start the camera
        }

        catch(RuntimeException e){
            System.err.println(e); //hopefully not!
            return;
        }

        try{
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview(); //display whats through the camera
        }

        catch(Exception e){
            System.err.println(e);
            return;
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        camera.stopPreview(); //stop the preview
        camera.release(); //release the camera
        camera = null; //set the object null
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){
        refreshCamera();
    }

    public void refreshCamera(){
        if(surfaceHolder.getSurface() == null){ //if there's no surface view...
            return;
        }

        try{
            camera.stopPreview(); //stop the preview
        }

        catch(Exception e){
            System.err.println(e);
            return;
        }

        try{
            camera.setPreviewDisplay(surfaceHolder); //set surfaceHolder
            camera.startPreview(); //start preview
        }
        catch (Exception e){
            System.err.println(e);
            return;
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        camera.release();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}