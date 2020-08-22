package com.example.mobileviolet;

import android.content.Context;
import android.content.DialogInterface;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import static android.content.ContentValues.TAG;

/**
 * Created by phoenixnguyen on 5/12/17.
 */

public class PropertySheet {
    private Context context;
    private View graphView;

    public PropertySheet(View graphView, Context context)
    {
        this.context = context;
        this.graphView = graphView;
    }

    public void getEditor(Object o, Graph g)
    {
        final Graph graph = g;
        if (o instanceof ImplicitParameterNode && !(o instanceof NoteNode))
        {
            final ImplicitParameterNode edited = (ImplicitParameterNode)o;
            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setTitle("Properties");
            alert.setMessage("Enter Name:");
            final EditText input = new EditText(context);
            alert.setView(input);
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String name = input.getText().toString();
                    MultiLineString nameString = new MultiLineString();
                    nameString.setText(name);
                    ((ImplicitParameterNode) edited).setName(nameString);
                    Log.d(TAG, "Name  : " + name);
                    graph.layout();
                    graphView.invalidate();
                    return;
                }
            });
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
// TODO Auto-generated method stub
                    return;
                }
            });
            alert.show();
        }
        else if (o instanceof NoteNode)
        {
            final NoteNode noteNode = (NoteNode)o;
            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setTitle("Properties");
            alert.setMessage("Enter Name:");


            LayoutInflater li = LayoutInflater.from(context);
            final View dialogView = li.inflate(R.layout.activity_editnote, null);

            ColorList colorList = new ColorList();
            final ColorList.ColorIcon[] colors = colorList.getColorList();
            final ArrayAdapter<ColorList.ColorIcon> adapter = new ColorAdapter(context, android.R.layout.simple_spinner_item, colors);

            // Set an EditText view to get user input
            final EditText input = (EditText)dialogView.findViewById(R.id.editNote);
            final Spinner spinner = (Spinner)dialogView.findViewById(R.id.spinnerNote);

            spinner.setAdapter(adapter);

            alert.setView(dialogView);


            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String name = input.getText().toString();
                    int position = spinner.getSelectedItemPosition();
                    MultiLineString nameString = new MultiLineString();
                    nameString.setText(name);
                    noteNode.setName(nameString);
                    noteNode.setColor(colors[position].getColor());
                    graph.layout();
                    graphView.invalidate();
                    Log.d(TAG, "Name  : " + name);
                    return;
                }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
// TODO Auto-generated method stub
                    return;
                }
            });
            alert.show();
        }
        else if (o instanceof CallEdge){
            final CallEdge callEdge = (CallEdge)o;
            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setTitle("Properties");
            alert.setMessage("Enter Name:");


            LayoutInflater li = LayoutInflater.from(context);
            final View dialogView = li.inflate(R.layout.activity_editcall, null);

            // Set an EditText view to get user input
            final EditText input = (EditText)dialogView.findViewById(R.id.editCall);
            final TextView textSignal = (TextView)dialogView.findViewById(R.id.setSignalView);
            final ToggleButton toggleSignal = (ToggleButton)dialogView.findViewById((R.id.toggleSignal));
            textSignal.setText("Set signal:");
            toggleSignal.setChecked(callEdge.isSignal());
            alert.setView(dialogView);

            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String name = input.getText().toString();
                    callEdge.setMiddleLabel(input.getText().toString());
                    if (toggleSignal.isChecked())
                    {
                        callEdge.setSignal(true);
                    }
                    else
                    {
                       callEdge.setSignal(false);
                    }
                    graph.layout();
                    graphView.invalidate();
                    Log.d(TAG, "Name  : " + name);
                    return;
                }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
// TODO Auto-generated method stub
                    return;
                }
            });
            alert.show();

        }
        else if (o instanceof CallNode)
        {
            final CallNode callNode = (CallNode)o;
            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setTitle("Properties");
            alert.setMessage("Open Bottom:");

            final ToggleButton toggleButton = new ToggleButton(context);
            toggleButton.setChecked(callNode.isOpenBottom());
            alert.setView(toggleButton);
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    if (toggleButton.isChecked())
                    {
                        callNode.setOpenBottom(true);
                    }
                    else
                    {
                        callNode.setOpenBottom(false);
                    }
                    graph.layout();
                    graphView.invalidate();

                    return;
                }
            });
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
// TODO Auto-generated method stub
                    return;
                }
            });
            alert.show();
        }

        }
    }