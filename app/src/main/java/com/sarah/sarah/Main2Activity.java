package com.sarah.sarah;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main2Activity extends AppCompatActivity {

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Button btnVentilador, btnLuz, btnTv, btnCerrojo;
    boolean ventilador, luz, tv, cerrojo;



    String ip;
    int port = 5555;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        prefs =  getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        editor = prefs.edit();

        ventilador = prefs.getBoolean("ventilador", false);
        luz = prefs.getBoolean("luz", false);
        tv = prefs.getBoolean("tv", false);
        cerrojo = prefs.getBoolean("cerrojo", false);

        Intent intent = getIntent();
        ip = intent.getStringExtra(MainActivity.IP);

        btnVentilador = (Button) findViewById(R.id.btnVentilador);
        btnLuz = (Button) findViewById(R.id.btnLuz);
        btnTv = (Button) findViewById(R.id.btnTv);
        btnCerrojo = (Button) findViewById(R.id.btnCerrojo);

        if(!luz){
            btnLuz.setBackgroundResource(R.drawable.btn_luz_off);
        } else {
            btnLuz.setBackgroundResource(R.drawable.btn_luz_on);
        }

        if(!ventilador){
            btnVentilador.setBackgroundResource(R.drawable.btn_ventilador_off);
        } else {
            btnVentilador.setBackgroundResource(R.drawable.btn_ventilador_on);
        }

        if(!tv){
            btnTv.setBackgroundResource(R.drawable.btn_tv_off);
        } else {
            btnTv.setBackgroundResource(R.drawable.btn_tv_on);
        }

        if(!cerrojo){
            btnCerrojo.setBackgroundResource(R.drawable.btn_cerrojo_off);
        } else {
            btnCerrojo.setBackgroundResource(R.drawable.btn_cerrojo_on);
        }

    }

    public void luz(View V) {
        MyClientTask myClientTask = new MyClientTask(ip, port);

        if(luz){
            myClientTask.execute("lightOff");
            btnLuz.setBackgroundResource(R.drawable.btn_luz_off);
            luz = false;
        } else {
            myClientTask.execute("light");
            btnLuz.setBackgroundResource(R.drawable.btn_luz_on);
            luz = true;
        }

        editor.putBoolean("luz", luz);
        editor.commit();
    }

    public void ventilador(View V) {
        MyClientTask myClientTask = new MyClientTask(ip, port);

        if(ventilador){
            myClientTask.execute("fanOff");
            btnVentilador.setBackgroundResource(R.drawable.btn_ventilador_off);
            ventilador = false;
        } else {
            myClientTask.execute("fan");
            btnVentilador.setBackgroundResource(R.drawable.btn_ventilador_on);
            ventilador = true;
        }

        editor.putBoolean("ventilador", ventilador);
        editor.commit();
    }

    public void tv(View V) {
        MyClientTask myClientTask = new MyClientTask(ip, port);

        if(tv){
            myClientTask.execute("tvOff");
            btnTv.setBackgroundResource(R.drawable.btn_tv_off);
            tv = false;
        } else {
            myClientTask.execute("tv");
            btnTv.setBackgroundResource(R.drawable.btn_tv_on);
            tv = true;
        }

        editor.putBoolean("tv", tv);
        editor.commit();
    }

    public void cerrojo(View V) {
        MyClientTask myClientTask = new MyClientTask(ip, port);

        if(cerrojo){
            myClientTask.execute("lockOff");
            btnCerrojo.setBackgroundResource(R.drawable.btn_cerrojo_off);
            cerrojo = false;
        } else {
            myClientTask.execute("lock");
            btnCerrojo.setBackgroundResource(R.drawable.btn_cerrojo_on);
            cerrojo = true;
        }

        editor.putBoolean("cerrojo", cerrojo);
        editor.commit();
    }

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
                    Toast.makeText(Main2Activity.this, "Puede que el servidor se haya caido", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

    }
}
