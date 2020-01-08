package com.example.texttospeech;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    BluetoothAdapter mbt;
    private Set<BluetoothDevice> pairedDevices;
    Button toggle,pair;
    ListView list;
    public static String EXTRA_ADDRESS = "device address";
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mbt=BluetoothAdapter.getDefaultAdapter();
        toggle=(Button)findViewById(R.id.b1);
        pair=(Button)findViewById(R.id.b2);
        list=(ListView)findViewById(R.id.dl);

        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleBt();
            }
        });

        pair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listDevice();
            }
        });
    }

    private void listDevice() {

        pairedDevices=mbt.getBondedDevices();
        ArrayList list2=new ArrayList();
        if(pairedDevices.size()>0){
            for(BluetoothDevice bt: pairedDevices){
                list2.add(bt.getName()+"\n"+bt.getAddress());
            }
        }else {
            Toast.makeText(getApplicationContext(),"Cihaz Yok",Toast.LENGTH_SHORT).show();
        }

        final ArrayAdapter adapter= new ArrayAdapter(this,android.R.layout.simple_list_item_1,list2);
        list.setAdapter(adapter);
        list.setOnItemClickListener(selectDevice);



    }


    public AdapterView.OnItemClickListener selectDevice = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String info= ((TextView) view).getText().toString();
            String address= info.substring(info.length()-17);

            Intent comintent=new Intent(MainActivity.this, CommunicationActivity.class);
            comintent.putExtra(EXTRA_ADDRESS,address);
            startActivity(comintent);
        }
    };

    private void toggleBt() {

        if(mbt==null){
            Toast.makeText(getApplicationContext(),"Bluetooth Cihazı Yok",Toast.LENGTH_SHORT).show();
        }

        if (!mbt.isEnabled()){
            Intent ebt=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(ebt);
        }
        if (mbt.isEnabled()){
            mbt.disable();
        }



    }
}
