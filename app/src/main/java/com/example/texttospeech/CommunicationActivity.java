package com.example.texttospeech;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class CommunicationActivity extends AppCompatActivity {


    String address=null;
    private ProgressDialog progressDialog;
    BluetoothAdapter mbt=null;

    BluetoothSocket socket=null;
    BluetoothDevice remoteDevice;
    BluetoothServerSocket mmServer;

    Button bon,boff;

    private boolean isBTConnected=false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication);
        Intent i =getIntent();
        address=i.getStringExtra(MainActivity.EXTRA_ADDRESS);

        bon=(Button)findViewById(R.id.bon);
        boff=(Button)findViewById(R.id.boff);

        bon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(socket!=null){
                    try{
                        socket.getOutputStream().write("1".toString().getBytes());
                    }catch (IOException e){
                        //
                    }
                }
            }
        });

        boff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(socket!=null){
                    try{
                        socket.getOutputStream().write("2".toString().getBytes());
                    }catch (IOException e){
                        //
                    }
                }
            }
        });






        new BTBaglan().execute();
    }

    public void Disconnect(){
        if (socket!=null){
            try{
                socket.close();
            }catch (IOException e){
                //
            }
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Disconnect();
    }

    private class BTBaglan extends AsyncTask<Void, Void, Void>{
        private boolean ConnectSuccess=true;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(CommunicationActivity.this,"Baglaniyor","Lütfen Bekleyiniz");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                if(socket ==null || !isBTConnected){
                    mbt = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice cihaz= mbt.getRemoteDevice(address);
                    socket=cihaz.createInsecureRfcommSocketToServiceRecord(myUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    socket.connect();
                }
            } catch (IOException e) {
                e.printStackTrace();
                ConnectSuccess=false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if(!ConnectSuccess){
                Toast.makeText(getApplicationContext(),"Bağlantı Hatası",Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(getApplicationContext(),"Bağlantı Başarılı",Toast.LENGTH_SHORT).show();
            }
            progressDialog.dismiss();
        }
    }
}
