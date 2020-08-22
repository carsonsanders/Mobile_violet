package com.example.mobileviolet;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



public class TutorialActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PixelGridView pixelGrid = new PixelGridView(this);
        pixelGrid.setNumColumns(8);
        pixelGrid.setNumRows(15);

        setContentView(pixelGrid);
    }
}

