package com.catched_movil.app.Vista;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.catched_movil.R;
import com.catched_movil.app.Control.Bd_origen;
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

public class RegistroServicio extends AppCompatActivity {

    private Context context;
    private Funciones obj_funciones;
    private EditText edtxt_url_host;
    private EditText edtxt_password;
    private String mensaje;
    private GestionBD BD;
    private String url_host;
    private String password;
    private GenerarlBD GNBD;
    Parametros obj_parametros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_servicio);
        context           = this;
        obj_funciones     = new Funciones();
        BD                = new GestionBD(context);
        GNBD              = new GenerarlBD();
        edtxt_url_host    = (EditText)findViewById(R.id.registro_servicio_edtxt_host);
        edtxt_password    = (EditText)findViewById(R.id.registro_servicio_edtxt_password);
        obj_parametros    = new Parametros();
    }

    public void ConnectarServicio(View v) {

        try {

            url_host = edtxt_url_host.getText().toString();
            password = obj_funciones.encodeBase64(edtxt_password.getText().toString()).trim();
            new Servicio_validar().execute();

        }catch (Exception e){
            e.printStackTrace();
            obj_funciones.MensajeToast(context,"Ocurrio un error ");
        }
    }

    public void CancelarServicio(View v) {
        limpiarCampos();
        obj_funciones.Actividad(context, MainActivity.class);
    }

    public void limpiarCampos(){
        edtxt_url_host.setText("");
        edtxt_password.setText("");
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
            parametros.add(new BasicNameValuePair(Constantes.KEY,password));

            mensaje = "Error Desconocido.";

            try {

                String jsonStr = servicio.makeServiceCall(url_host,Constantes.WS_REGISTRO_APP,Constantes.MODO_SERVICIO,parametros);
                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);
                        AjaxResultado resultado = servicio.fn_json_to_has_map(jsonObj,null,2);
                        if(resultado.getErrores() != null){
                            mensaje = resultado.getErrores();
                        }else if(resultado.getResultado() != null){

                            String host = obj_parametros.fn_consulta_parametro(BD,"1");
                            if(host == null){
                                GNBD.fn_insertParametro(BD,"1",url_host);
                            }else{
                                GNBD.fn_updateParametro(BD,"1",url_host);
                            }

                            String password_c = obj_parametros.fn_consulta_parametro(BD,"2");
                            if(password_c == null){
                                GNBD.fn_insertParametro(BD,"2",password);
                            }else{
                                GNBD.fn_updateParametro(BD,"2",password);
                            }

                            Constantes.HOST = url_host;
                            mensaje_rs = resultado.getMensajes();
                            ok = true;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
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
                limpiarCampos();
                obj_funciones.MensajeToast(context,mensaje_rs);
                obj_funciones.Actividad(context, Login.class);
            }else{
                obj_funciones.MensajeToast(context,mensaje);
            }
        }

        @Override
        protected void onPostExecute(Void result) {

        }
    }

}
