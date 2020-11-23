package com.example.servicebackground;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.Manifest.permission.FOREGROUND_SERVICE;

public class MainActivity extends AppCompatActivity {
    private static Context context;

    EditText mEdit;
    Button   mButton;
    TextView label;


    //creamos el metodo que escucha datos emitidos por el servicio
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Integer segundoService = intent.getIntExtra("segundoService",0);
            Log.i("Segundo recibido:", segundoService.toString());
            label.setText(segundoService.toString());

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = MainActivity.this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this,new String[]{FOREGROUND_SERVICE}, PackageManager.PERMISSION_GRANTED);

        mEdit   =(EditText)findViewById(R.id.tiempo);

        mButton = (Button)findViewById(R.id.button);

        label = (TextView)findViewById(R.id.textView);





        mButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        Log.v("EditText", mEdit.getText().toString());
                        ejecutar();
                    }
                });

    }

    public  void ejecutar(){
        Log.v("Click","Inica la funcion");
        Intent i = new Intent(context, CronometroService.class);
        Integer numero = Integer.parseInt(mEdit.getText().toString());
        i.putExtra("segundo",numero);
        startService(i);

    }


    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //registramos el reciver
        registerReceiver(broadcastReceiver,new IntentFilter("contador"));
        Toast.makeText(context, "On Resume.", Toast.LENGTH_LONG).show();
    }
}
