package com.example.servicebackground;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

public class CronometroService extends Service {


    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {

        //obtenemos el parametro enviado desde el activity
        Integer segundos[] = {intent.getIntExtra("segundo", 0)};
        Integer valor = segundos[0];
        Log.i("MyTestService", "el paremetro recibido es " + segundos);

        //creamos un hilo para la tarea
        final AsyncTaskRunner hilo = new AsyncTaskRunner();
        hilo.execute(valor);

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    class AsyncTaskRunner extends AsyncTask<Integer, String, Integer> {

        private int resp;


        @Override
        protected Integer doInBackground(Integer... params) {
            //recibimos el parametro
            Integer segundos = params[0];
            Log.i("Segundo", "Inicamos el hilo + " + segundos);

            //ejecutamos un contador y dormimos la app por cada vuelta del ciclo
            for (int i = 1; i <= segundos; i++) {

                try {
                    Intent intentLocal = new Intent();
                    intentLocal.setAction("contador");
                    Thread.sleep(1000);
                    Log.i("Segundo", "" + i);

                    //agregamos la variable al intent y emitimos
                    intentLocal.putExtra("segundoService", i);
                    CronometroService.this.getBaseContext().sendBroadcast(intentLocal);

                } catch (Exception e) {

                    e.printStackTrace();

                }

            }

            return resp;
        }
    }


}




