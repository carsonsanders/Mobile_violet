package com.example.mobileviolet;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.provider.ContactsContract;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ScrollingView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.ContentFrameLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;
import static android.view.MotionEvent.INVALID_POINTER_ID;
import static java.lang.Math.*;
/**
 * Created by phoenixnguyen on 4/17/17.
 */

public class GraphView extends View {

    //HorizontalScrollView hor = (HorizontalScrollView) findViewById(R.id.hor);
    ScrollView scroll = (ScrollView) findViewById(R.id.scroll);
    private Node implicitNode;
    private ArrayList<ImplicitParameterNode> implicitNodes = new ArrayList<>();
    private int mActivePointerId = INVALID_POINTER_ID;
    Grid2 grid = new Grid2();
    SequenceDiagramGraph graph = new SequenceDiagramGraph();

    private Object lastSelected;
    private Set selectedItems;
    private Point2D lastMousePoint;
    private Point2D mouseDownPoint;
    private ArrayList tools;
    private String currentSave;
    long start = 0;

    int selected;
    private int dragMode;

    private static final int DRAG_NONE = 0;
    private static final int DRAG_MOVE = 1;
    private static final int DRAG_RUBBERBAND = 2;
    private static final int DRAG_LASSO = 3;
    private static final int CONNECT_THRESHOLD = 8;

    Context context;


//Tool_bar bar= new Tool_bar();

    public GraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        selected = 0;
        tools = new ArrayList<>();
        tools.add(null);
        tools.add(new ImplicitParameterNode());
        tools.add(new CallNode());
        tools.add(new NoteNode());
        tools.add(new CallEdge());
        tools.add(new ReturnEdge());
        tools.add(new NoteEdge());
        tools.add(new Rectangle2D());
        selectedItems = new HashSet();
        currentSave = "New";


    }

    private void setSelectedItem(Object obj) {
        selectedItems.clear();
        lastSelected = obj;
        if (obj != null) selectedItems.add(obj);
    }

    private void clearSelection() {
        selectedItems.clear();
        lastSelected = null;
    }

    public String getSave(){
        return currentSave;
    }

    public void setSelected(int newValue) {
        selected = newValue;
    }

    public Object getSelectedTool() {
        for (int i = 0; i < tools.size(); i++) {
            if (getSelected() > 0) ;
            return tools.get(getSelected());
        }
        return null;
    }

    public int getSelected() {
        return selected;
    }

    public void saveGraph(String fileName){
        try
        {
            FileOutputStream fos = this.getContext().openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos); //Select where you wish to save the file...
            oos.writeObject(graph); // write the class as an 'object'
            oos.flush(); // flush the stream to insure all of the information was written to 'save_object.bin'
            oos.close();// close the stream
        }
        catch(Exception ex)
        {
            //Log.v("Serialization Save Error : ",ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void loadGraph(File f)
    {
        currentSave = f.getName();
        try
        {
            FileInputStream fis = this.getContext().openFileInput(f.getName());
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object o = ois.readObject();
            graph = (SequenceDiagramGraph) o;
            invalidate();
        }
        catch(Exception ex)
        {
            //Log.v("Serialization Read Error : ",ex.getMessage());
            ex.printStackTrace();
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {

        int width = getWidth();
        int height = getHeight();

//bar.add(new ImplicitParameterNode(80,90));
        grid.draw(canvas, new Rect(0, 0, width, height));


//bar.draw(canvas);
//for (ImplicitParameterNode o : implicitNodes)
//o.draw(canvas);
//}
        if (graph != null) {
            graph.draw(canvas, grid);
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
// Let the ScaleGestureDetector inspect all events.

        final int MAX_DURATION = 200;

        final int action = MotionEventCompat.getActionMasked(ev);
        float mLastTouchX = 0;
        float mLastTouchY = 0;
        float mPosX = 0;
        float mPosY = 0;
        boolean added = false;

        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN: {
                Point2D mousePoint= new Point2D(ev.getX(), ev.getY());
                Node n = graph.findNode(mousePoint);
                Edge e = graph.findEdge(mousePoint);
                Object tool = getSelectedTool();
                if (System.currentTimeMillis() - start <= MAX_DURATION && tool == null) {
                    // DOUBLE TAP DETECTED
                    if (e != null)
                    {
                        setSelectedItem(e);
                        editSelected();
                    }
                    else if  (n == null) {
                        //DO NOTHING
                    }
                    else if (!(n instanceof CallNode)) {
                        setSelectedItem(n);
//editSelected(); //not entirely sure what it does yet
                        editSelected();
                        invalidate();
                    }
                    else if (n instanceof CallNode)
                    {
                        editSelected();
                        invalidate();
                    }


                }
                else if (tool == null) {
                    if (e != null)
                    {
                        setSelectedItem(e);
                    }
                    else if (n != null) {
                        setSelectedItem(n);
                        dragMode = DRAG_MOVE;
                    } else {
                        clearSelection();
                    }
                } else if (tool instanceof Node) {

                    if (tool instanceof NoteNode) {
                        if (n != null)
                        {
                            if (!selectedItems.contains(n))
                                setSelectedItem(n);

                        }
                        else {

                            NoteNode newNode = new NoteNode();
                            added = graph.add(newNode, mousePoint);
                            if (added) {
                                setSelectedItem(newNode);

                            }
                        }

                    } else if (tool instanceof ImplicitParameterNode) {
                        if (n != null)
                        {
                            if (!selectedItems.contains(n))
                                setSelectedItem(n);

                        }
                        else {
                            ImplicitParameterNode newNode = new ImplicitParameterNode();
                            added = graph.add(newNode, mousePoint);
                            if (added) {
                                setSelectedItem(newNode);

                            }
                        }
                    } else if (tool instanceof CallNode) {
                        if (n instanceof CallNode)
                        {
                            if (!selectedItems.contains(n))
                                setSelectedItem(n);

                        }
                        else {
                            CallNode newNode = new CallNode();
                            added = graph.add(newNode, mousePoint);
                            if (added) {
                                setSelectedItem(newNode);

                            }
                        }
                    } else if (tool instanceof CallEdge){
                        // TODO
                        if (n != null) dragMode = DRAG_RUBBERBAND;
                    }
                    else if (tool instanceof ReturnEdge){
                        // TODO
                        if (n != null) dragMode = DRAG_RUBBERBAND;
                    }
                    else if (tool instanceof NoteEdge){
                        // TODO
                        if (n != null) dragMode = DRAG_RUBBERBAND;
                    }
                }
                else if (tool instanceof Rectangle2D){
                    if(n != null) {
                        graph.removeNode(n);
                        Toast.makeText(this.getContext(), "remove", Toast.LENGTH_SHORT).show();
                    }
                }
                lastMousePoint = mousePoint;
                mouseDownPoint = mousePoint;

                invalidate();
                break;
            }


            case MotionEvent.ACTION_MOVE: {
//                hor.requestDisallowInterceptTouchEvent(true);
              //  graph.get
//scroll.requestDisallowInterceptTouchEvent(true);
//                  scroll.setOnTouchListener( new OnTouchListener(){
//                      @Override
//                      public boolean onTouch(View v, MotionEvent event)
//                      {
//
//                          return true;
//                      }
//                  });
//
//
//// Enable Scrolling by removing the OnTouchListner
               //scroll.setOnTouchListener(null);
//                scroll.setEnabled(false);
//                scroll.setFocusable(false);
//                scroll.setHorizontalScrollBarEnabled(false);
//                scroll.setVerticalScrollBarEnabled(false);




                Object tool = getSelectedTool();
                // Find the index of the active pointer and fetch its position
                Point2D mousePoint= new Point2D(ev.getX(), ev.getY());
                Node n = graph.findNode(mousePoint);

                if (n != null && dragMode == DRAG_MOVE) {

                    // Find the index of the active pointer and fetch its position
                    final float x = ev.getX();
                    final float y = ev.getY();


                    // Calculate the distance moved

                    Node lastNode = (Node) n;
                    Rectangle2D bounds = lastNode.getBounds();


                    float dx = x;
                    float dy = y;

                    dx = Math.max(dx, (float) -bounds.getX());
                    dy = Math.max(dy, (float) -bounds.getY());

                    lastNode.translate((float) (dx - bounds.getX() - bounds.getWidth() / 2), (float) (dy - bounds.getY() - bounds.getHeight()/2));
                    invalidate();
                }
                lastMousePoint = mousePoint;
                break;
            }

            case MotionEvent.ACTION_UP: {
                start = System.currentTimeMillis();
                Point2D mousePoint= new Point2D(ev.getX(), ev.getY());
                Object tool = getSelectedTool();

                /*
                if (dragMode == DRAG_RUBBERBAND)
                {
                    CallEdge newEdge = new CallEdge();
                    if (mousePoint.distance(mouseDownPoint) > CONNECT_THRESHOLD
                            && graph.connect(newEdge, mouseDownPoint, mousePoint))
                    {
                        setSelectedItem(newEdge);
                        //editSelected();
                    }
                }
                */

                if (dragMode == DRAG_MOVE)
                {
                    graph.layout();
                }
                dragMode = DRAG_NONE;

                if(tool instanceof CallEdge) {
                    //TODO
                    CallEdge newEdge = new CallEdge();
                    graph.connect(newEdge, mouseDownPoint, mousePoint);
                    setSelectedItem(newEdge);
                    //editSelected();
                }

                if(tool instanceof ReturnEdge) {
                    //TODO
                    ReturnEdge newEdge = new ReturnEdge();
                    graph.connect(newEdge, mouseDownPoint, mousePoint);
                    setSelectedItem(newEdge);
                    //editSelected();
                }
                if(tool instanceof NoteEdge) {
                    //TODO
                    NoteEdge newEdge = new NoteEdge();
                    graph.connect(newEdge, mouseDownPoint, mousePoint);
                    setSelectedItem(newEdge);
                }
                invalidate();
                break;
            }

            case MotionEvent.ACTION_CANCEL: {
                mActivePointerId = INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_POINTER_UP: {

                final int pointerIndex = MotionEventCompat.getActionIndex(ev);
                final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);

                if (pointerId == mActivePointerId) {
// This was our active pointer going up. Choose a new
// active pointer and adjust accordingly.
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    mLastTouchX = ev.getX();
                    mLastTouchY = ev.getY();
                    mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
                }
                break;
            }
        }
        return true;
    }



    public void editSelected() {
        Object edited = lastSelected;
        if (lastSelected == null) {
            if (selectedItems.size() == 1)
                edited = selectedItems.iterator().next();
            else
                return;
        }


        PropertySheet editor = new PropertySheet(this, this.getContext());
        editor.getEditor(edited, graph);
    }
}