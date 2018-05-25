package eim.systems.cs.pub.ro.practicaltest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;



public class PracticalTest02MainActivity extends AppCompatActivity {
        private TextView publishTextView;

        /*** Client ***/
        private Button startButton;
        private Button goButton;
        private EditText clientporttext;
        private EditText clienturltext;

        private MyServer server;


        /*** Server ***/
        private EditText resulttext;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_practical_test02_main);

            /*** Client ***/
            startButton = findViewById(R.id.strt_button);
            goButton = findViewById(R.id.go_button);
            clientporttext = findViewById(R.id.clientporttext);
            clienturltext = findViewById(R.id.clienturltext);


            /*** Server ***/
//            resulttext = findViewById(R.id.resulttext);


            /*** Button listeners ***/
//            startButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String address = "localhost";
//                    String port = clientporttext.getText().toString();
//
//                    MyClient myClient = new MyClient(publishTextView);
//                    myClient.execute(address, port, "GET", getKey);
//                }
//            });

            goButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String address = "localhost";
                    String port = clientporttext.getText().toString();
                    String addr = clienturltext.getText().toString();

                    MyClient myClient = new MyClient(publishTextView);
                    myClient.execute(address, port, "GET", port, addr);
                }
            });

        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            Log.i(Constants.TAG, "[MAIN ACTIVITY] onDestroy() callback method has been invoked");
            if (this.server != null) {
                this.server.stopThread();
            }
        }
    }

