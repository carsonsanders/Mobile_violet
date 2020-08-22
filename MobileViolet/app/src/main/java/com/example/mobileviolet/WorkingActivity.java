package com.example.mobileviolet;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.ToggleButton;
import android.view.View;
import android.widget.ImageButton;
import com.example.mobileviolet.GraphView;

import android.widget.Toast;

import java.io.File;


public class WorkingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static Context context;
    //    ImageButton imageButton11;
//    ImageButton imageButton12;
//    Canvas can;
    private GraphView graphView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_working);

        final GraphView graphview = (GraphView) findViewById(R.id.graphview);
        graphView = graphview;

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        Button select = (Button) findViewById(R.id.button0);
        select.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                graphview.setSelected(0);
            }
        });

        Button implicit = (Button) findViewById(R.id.button1);
        implicit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                graphview.setSelected(1);
            }
        });

        Button call = (Button) findViewById(R.id.button2);
        call.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                graphview.setSelected(2);
            }
        });

        Button note = (Button) findViewById(R.id.button3);
        note.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                graphview.setSelected(3);
            }
        });

        Button callEdge = (Button) findViewById(R.id.button7);
        callEdge.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                graphview.setSelected(4);
                Toast.makeText(WorkingActivity.this,
                        "Call Edge Selected!", Toast.LENGTH_SHORT).show();

            }
        });

        Button returnEdge = (Button) findViewById(R.id.button8);
        returnEdge.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                graphview.setSelected(5);
                Toast.makeText(WorkingActivity.this,
                        "Return Edge Selected!", Toast.LENGTH_SHORT).show();

            }
        });

        Button noteEdge = (Button) findViewById(R.id.buttonNoteEdge);
        noteEdge.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                graphview.setSelected(6);
                Toast.makeText(WorkingActivity.this,
                        "Note Edge Selected!", Toast.LENGTH_SHORT).show();

            }
        });

        Button save = (Button) findViewById(R.id.button10);
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(graphview.getSave().equals("New")){
                    graphview.saveGraph("Save2");
                    Toast.makeText(WorkingActivity.this, "Saved to Save2", Toast.LENGTH_SHORT).show();
                }
                else {
                    graphview.saveGraph(graphview.getSave());
                    Toast.makeText(WorkingActivity.this, "Saved to " + graphview.getSave(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button delete = (Button) findViewById(R.id.buttonDelete);
        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                graphview.setSelected(7);
                Toast.makeText(WorkingActivity.this,
                        "Delete Selected!", Toast.LENGTH_SHORT).show();

            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        spinner.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.save_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        String selected = (String)parent.getItemAtPosition(pos);
        if(selected.equals("New")){
            //chill
        }
        else {
            graphView.loadGraph(new File(selected));
            Toast.makeText(WorkingActivity.this, "Loaded: " + selected, Toast.LENGTH_SHORT).show();
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

}