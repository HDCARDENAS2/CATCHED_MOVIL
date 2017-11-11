package com.catched_movil.app.Vista;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.catched_movil.R;
import com.catched_movil.app.Control.Constantes;
import com.catched_movil.app.Control.Funciones;
import com.catched_movil.app.Control.GenerarlBD;
import com.catched_movil.app.Control.GestionBD;
import com.catched_movil.app.Control.ServiceHandler;
import com.catched_movil.app.Model.AjaxResultado;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ConsultaEventos extends android.app.Fragment implements View.OnClickListener{

    private View myview;
    private Context context;
    private String mensaje;
    private Funciones obj_funciones;
    private ListAdapter adapter;
    private ListView v_lista;
    private String cod_evento;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        myview= inflater.inflate(R.layout.fragment_consulta_eventos,container,false);
        context = myview.getContext();
        obj_funciones              = new Funciones();
        v_lista = (ListView)       myview.findViewById(R.id.listViewEventos);

        new ConsultaEventos.Servicio_validar().execute();

        v_lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                cod_evento= ((TextView) view
                              .findViewById(R.id.list_v1)).getText()
                              .toString();

                new ConsultaEventos.Servicio_imagen().execute();
            }
        });

        return myview;
    }

    @Override
    public void onClick(View view) {
        Button boton = (Button) view;

    }

    private class Servicio_validar extends AsyncTask<Void, Void, Void> {

        private boolean ok;
        private String  mensaje_rs;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            ok = false;
            ServiceHandler servicio = new ServiceHandler();
            ArrayList<NameValuePair> parametros = new ArrayList<NameValuePair>();
            parametros.add(new BasicNameValuePair(Constantes.KEY,Constantes.AUTENTIFICACION));

            mensaje = "Error Desconocido.";

            try {
                String jsonStr = servicio.makeServiceCall(Constantes.HOST,Constantes.WS_CS_ENVENTOS,Constantes.MODO_SERVICIO,parametros);
                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);
                        AjaxResultado resultado = servicio.fn_json_to_has_map(jsonObj,Constantes.CT_EVENTO,1);
                        if(resultado.getErrores() != null){
                            mensaje = resultado.getErrores();
                        }else if(resultado.getResultado_array() != null){
                            mensaje_rs = resultado.getMensajes();

                            adapter = new SimpleAdapter(context, resultado.getResultado_array() ,
                                    R.layout.sub_desing_lista_2, Constantes.CT_EVENTO_VISTA,
                                    new int[] {
                                    R.id.list_v1, R.id.list_v2 }
                             );

                             ok = true;

                        }else{
                            mensaje = null;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("JSON ",jsonStr);
                        mensaje = "Ocurrio un error en los datos JSON.";
                    }
                }else{
                    mensaje = "El servidor no retorno la respuesta.";
                }
            }  catch (Exception e) {
                e.printStackTrace();
                mensaje = "Error.";
            }
            publishProgress();
            return null;
        }

        protected void onProgressUpdate (Void... valores) {
            if (ok) {
                v_lista.setAdapter(adapter);
                obj_funciones.MensajeToast(context,mensaje_rs);
            }else{
                obj_funciones.MensajeToast(context,mensaje);
            }
        }

        @Override
        protected void onPostExecute(Void result) {

        }
    }

    private class Servicio_imagen extends AsyncTask<Void, Void, Void> {

        private boolean ok;
        private String  mensaje_rs;
        private String  imagen;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            ok = false;
            ServiceHandler servicio = new ServiceHandler();
            ArrayList<NameValuePair> parametros = new ArrayList<NameValuePair>();
            parametros.add(new BasicNameValuePair(Constantes.KEY,Constantes.AUTENTIFICACION));
            parametros.add(new BasicNameValuePair(Constantes.CT_EVENTO_COD_EVENTO,cod_evento));

            mensaje = "Error Desconocido.";

            try {
                String jsonStr = servicio.makeServiceCall(Constantes.HOST,Constantes.WS_CS_IMAGEN,Constantes.MODO_SERVICIO,parametros);
                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);
                        AjaxResultado resultado = servicio.fn_json_to_has_map(jsonObj,null,2);
                        if(resultado.getErrores() != null){
                            mensaje = resultado.getErrores();
                        }else if(resultado.getResultado() != null){
                            mensaje_rs = resultado.getMensajes();
                            imagen = resultado.getResultado();
                            ok = true;
                        }else{
                            mensaje = null;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("JSON ",jsonStr);
                        mensaje = "Ocurrio un error en los datos JSON.";
                    }
                }else{
                    mensaje = "El servidor no retorno la respuesta.";
                }
            }  catch (Exception e) {
                e.printStackTrace();
                mensaje = "Error.";
            }
            publishProgress();
            return null;
        }

        protected void onProgressUpdate (Void... valores) {
            if (ok) {

            }else{
                obj_funciones.MensajeToast(context,mensaje);
            }
        }

        @Override
        protected void onPostExecute(Void result) {

        }
    }


}
