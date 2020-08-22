/**
 * The Main Activity of MobileViolet
 */

package com.example.mobileviolet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static android.provider.AlarmClock.EXTRA_MESSAGE;


/**
 * Run the Main Activity in MobileViolet
 */
public class MainActivity extends AppCompatActivity {
    Button b1;
    Button b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = (Button) findViewById(R.id.button);
        //b2.findViewById(R.id.button2);


        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                Intent myIntent = new Intent(MainActivity.this,
                        WorkingActivity.class);
                startActivity(myIntent);
            }

//    public void sendMessage(View view)
//    {
//        Intent intent = new Intent(this, DisplayMessageActivity.class);
//        TextView editText = (TextView) findViewById(R.id.textView);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);
//
//    }

//        });
//
//    }
        });
    }

    public void createNew(View view) {
        Intent intent = new Intent(this, WorkingActivity.class);
        startActivity(intent);
    }

    public void startTutorial(View view) {
        Intent intent = new Intent(this, WorkingActivity.class);
        startActivity(intent);
    }
}