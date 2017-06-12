package com.sarah.sarah;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    public final static String IP = "IP";
    int port = 5555;
    String address;
    EditText editTextAddress;
    Button buttonConnect;
    CheckBox checkbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextAddress = (EditText) findViewById(R.id.address);
        buttonConnect = (Button) findViewById(R.id.connect);
        checkbox = (CheckBox) findViewById(R.id.checkBox);

        buttonConnect.setOnClickListener(buttonConnectOnClickListener);

        SharedPreferences myPrefs = MainActivity.this.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
        boolean isRemember = myPrefs.getBoolean("REMEMBER", false);
        if(isRemember){
            String id = myPrefs.getString("MY_IP", "");

            editTextAddress.setText(id);
            checkbox.setChecked(true);
        }
    }

    View.OnClickListener buttonConnectOnClickListener =
            new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    address = editTextAddress.getText().toString();

                    SharedPreferences myPrefs = MainActivity.this.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
                    SharedPreferences.Editor prefsEditor = myPrefs.edit();
                    prefsEditor.putString("MY_IP", editTextAddress.getText().toString());
                    prefsEditor.putBoolean("REMEMBER", checkbox.isChecked());
                    prefsEditor.commit();

                    MyClientTask myClientTask = new MyClientTask(
                            address,
                            port);

                    myClientTask.execute("connect");

                }
            };

    public class MyClientTask extends AsyncTask<String, Void, Void> {

        String dstAddress;
        int dstPort;

        MyClientTask(String addr, int port) {
            dstAddress = addr;
            dstPort = port;
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                Socket socket = new Socket(dstAddress, dstPort);

                OutputStream outputStream = socket.getOutputStream();
                PrintStream printStream = new PrintStream(outputStream);
                printStream.print(params[0]);

                socket.close();

                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtra(IP, address);
                startActivity(intent);

            } catch (UnknownHostException e) {
                showToast();
                e.printStackTrace();
            } catch (IOException e) {
                showToast();
                e.printStackTrace();
            }
            return null;
        }

        public void showToast(){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "check your ip or server", Toast.LENGTH_LONG).show();
                }
            });
        }

    }
}