package com.catched_movil.app.Vista;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.catched_movil.R;
import com.catched_movil.app.Control.Constantes;
import com.catched_movil.app.Control.Funciones;
import com.catched_movil.app.Control.GenerarlBD;
import com.catched_movil.app.Control.GestionBD;
import com.catched_movil.app.Control.ServiceHandler;
import com.catched_movil.app.Model.AjaxResultado;
import com.catched_movil.app.Model.Parametros;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by hernandario on 09/11/2017.
 */

public class Notificacion extends Service {

    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;
    private Funciones obj_funciones;
    private GestionBD BD;
    private GenerarlBD GNBD;
    private Parametros obj_parametros;

    private class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            try {

                int notificacion = 1;
                while (true) {
                    Thread.sleep(5000);
                    if(Constantes.AUTENTIFICACION.equals("") || Constantes.AUTENTIFICACION == null ){
                         break;
                    }
                    if(!Constantes.AUTENTIFICACION.equals("") || Constantes.AUTENTIFICACION != null ){

                        String fecha = obj_parametros.fn_consulta_parametro(BD,"4");

                        com.catched_movil.app.Control.ServiceHandler servicio = new com.catched_movil.app.Control.ServiceHandler();
                        ArrayList<NameValuePair> parametros = new ArrayList<NameValuePair>();

                        Log.e("Notificacion - DATOS",Constantes.AUTENTIFICACION+" "+fecha);

                        parametros.add(new BasicNameValuePair(Constantes.KEY,Constantes.AUTENTIFICACION));
                        parametros.add(new BasicNameValuePair(Constantes.FECHA,"'"+fecha+"'"));

                        try {
                            String jsonStr = servicio.makeServiceCall(Constantes.HOST,Constantes.WS_NOTIFICACION,Constantes.MODO_SERVICIO,parametros);

                            Log.e("Notificacion - json",jsonStr);

                            if (jsonStr != null) {
                                try {
                                    JSONObject jsonObj = new JSONObject(jsonStr);
                                    AjaxResultado resultado = servicio.fn_json_to_has_map(jsonObj,Constantes.CT_EVENTO,3);
                                    if(resultado.getErrores() != null){
                                       Log.e("Notificacion",resultado.getErrores()) ;
                                    }else if(resultado.getResultado_array() != null){

                                        for(HashMap<String, String> dato : resultado.getResultado_array()){

                                            Log.e("Notificacion"," fecha "+dato.get(Constantes.CT_EVENTO_FECHA));

                                            GNBD.fn_updateParametro(BD,"4",dato.get(Constantes.CT_EVENTO_FECHA));
                                        }

                                        notificacion++;
                                        NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx)
                                                .setSmallIcon(R.drawable.notification)
                                                .setContentTitle(ctx.getResources().getString(R.string.app_name))
                                                .setContentText("Se capturo un evento...")
                                                .setWhen(System.currentTimeMillis());
                                        Intent notificacionIntent = new Intent(ctx.getApplicationContext(), MenuPr.class);
                                        notificacionIntent.putExtra("frame", "1");

                                        int FLAG_NONE = 0;
                                        PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 1, notificacionIntent, FLAG_NONE);
                                        builder.setContentIntent(pendingIntent);
                                        builder.setAutoCancel(true);
                                        nManager.notify(notificacion, builder.build());

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.e("Notificacion - json",jsonStr);
                                }
                            }else{
                                Log.e("Notificacion ","El servidor no retorno la respuesta.");
                            }
                        }  catch (Exception e) {
                            e.printStackTrace();
                            Log.e("Notificacion ","Error.");
                        }
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            stopSelf(msg.arg1);
        }
    }

    Context ctx;

    @Override
    public void onCreate() {
        HandlerThread thread = new HandlerThread("ServiceStartArguments", 1);
        thread.start();
        ctx = this;
        obj_funciones     = new Funciones();
        BD                = new GestionBD(ctx);
        GNBD              = new GenerarlBD();
        obj_parametros    = new Parametros();
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {

    }

}