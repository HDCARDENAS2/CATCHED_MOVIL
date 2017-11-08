package com.catched_movil.app.Vista;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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

public class Login extends AppCompatActivity {

    private Context context;
    private Funciones obj_funciones;
    private EditText edtxt_usuario;
    private EditText edtxt_password;
    private String mensaje;
    private GestionBD BD;
    private GenerarlBD GNBD;
    private Parametros obj_parametros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context           = this;
        obj_funciones     = new Funciones();
        BD                = new GestionBD(context);
        GNBD              = new GenerarlBD();
        obj_parametros    = new Parametros();
        edtxt_usuario     = (EditText)findViewById(R.id.login_edtxt_usuario);
        edtxt_password    = (EditText)findViewById(R.id.login_edtxt_password);

        String url                = obj_parametros.fn_consulta_parametro(BD,"1");
        if( url != null ){
            Constantes.HOST = url;
        }

    }

    public void IniciarSession(View v) {

        try {

            String usuario    = obj_funciones.generarCaracteres(5)+obj_funciones.encodeBase64(edtxt_usuario.getText().toString()).trim();
            String password   = obj_funciones.generarCaracteres(4)+obj_funciones.encodeBase64(edtxt_password.getText().toString()).trim();
            Constantes.AUTENTIFICACION = (usuario+Constantes.CONCATENACION+password).trim();
            Log.e("contrasena",Constantes.AUTENTIFICACION);
            new Servicio_validar().execute();

        }catch (Exception e){
            e.printStackTrace();
            obj_funciones.MensajeToast(context,"Ocurrio un error ");
        }
    }

    public void RegistraUsuario(View v) {
        limpiarCampos();
        obj_funciones.Actividad(context, RegistroUsuario.class);
    }

    public void GestionarConexion(View v) {
        limpiarCampos();
        obj_funciones.Actividad(context, RegistroServicio.class);
    }

    public void limpiarCampos(){
        edtxt_usuario.setText("");
        edtxt_password.setText("");
    }

    private class Servicio_validar extends AsyncTask<Void, Void, Void> {

        private boolean ok;

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
                String jsonStr = servicio.makeServiceCall(Constantes.HOST,Constantes.WS_LONGIN,Constantes.MODO_SERVICIO,parametros);
                Log.e("json",jsonStr);
                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);
                        AjaxResultado resultado = servicio.fn_json_to_has_map(jsonObj,Constantes.CT_USUARIO,3);
                        if(resultado.getErrores() != null){
                            mensaje = resultado.getErrores();
                        }else if(resultado.getResultado_array() != null){

                            String session   = obj_parametros.fn_consulta_parametro(BD,"3");
                            if( session != null ){
                                GNBD.fn_updateParametro(BD,"3","0");
                            }else{
                                GNBD.fn_insertParametro(BD,"3","1");
                            }

                            String fecha_evento = jsonObj.getString(Constantes.FECHA).toString().trim();
                            Log.e("login","fehca"+fecha_evento);
                            String fecha = obj_parametros.fn_consulta_parametro(BD,"4");

                            if(fecha != null){
                                GNBD.fn_updateParametro(BD,"4",fecha_evento);
                            }else{
                                GNBD.fn_insertParametro(BD,"4",fecha_evento);
                            }

                            ok = true;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("json",jsonStr);
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
                obj_funciones.Actividad(context, MenuPr.class);

            }else{
                obj_funciones.MensajeToast(context,mensaje);
            }
        }

        @Override
        protected void onPostExecute(Void result) {

        }
    }


}
