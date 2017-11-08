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

public class RegistroUsuario extends AppCompatActivity {

    private Context context;
    private Funciones obj_funciones;
    private EditText edtxt_usuario;
    private EditText edtxt_nombre;
    private EditText edtxt_password;
    private EditText edtxt_password_c;
    private String mensaje;
    private GestionBD BD;
    private String usuario;
    private String nombre;
    private String password;
    private String password_c;
    private GenerarlBD GNBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        context                    = this;
        obj_funciones              = new Funciones();
        BD                         = new GestionBD(context);
        GNBD                       = new GenerarlBD();
        edtxt_usuario              = (EditText)findViewById(R.id.registro_us_edtxt_usuario);
        edtxt_nombre               = (EditText)findViewById(R.id.registro_us_edtxt_nombre);
        edtxt_password             = (EditText)findViewById(R.id.registro_us_edtxt_password);
        edtxt_password_c           = (EditText)findViewById(R.id.registro_us_edtxt_password_confirmar);
        Parametros obj_parametros  = new Parametros();
        Constantes.AUTENTIFICACION = obj_parametros.fn_consulta_parametro(BD,"2");

        Log.e("xx",Constantes.AUTENTIFICACION+"-"+Constantes.HOST );

    }

    public void RegistroUsuario(View v) {

        try {

            usuario      = edtxt_usuario.getText().toString().trim();
            nombre       = edtxt_nombre.getText().toString();
            password     = obj_funciones.encodeBase64(edtxt_password.getText().toString()).trim();
            password_c   = obj_funciones.encodeBase64(edtxt_password_c.getText().toString()).trim();

            new RegistroUsuario.Servicio_validar().execute();

        }catch (Exception e){
            e.printStackTrace();
            obj_funciones.MensajeToast(context,"Ocurrio un error ");
        }
    }

    public void CancelarUsuario(View v) {
        limpiarCampos();
        obj_funciones.Actividad(context, Login.class);
    }

    public void limpiarCampos(){
        edtxt_usuario.setText("");
        edtxt_nombre.setText("");
        edtxt_password.setText("");
        edtxt_password_c.setText("");
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
            parametros.add(new BasicNameValuePair(Constantes.CT_USUARIO_USUARIO,usuario));
            parametros.add(new BasicNameValuePair(Constantes.CT_USUARIO_NOMBRES,nombre));
            parametros.add(new BasicNameValuePair(Constantes.PASSWORD,password));
            parametros.add(new BasicNameValuePair(Constantes.PASSWORD_C,password_c));

            mensaje = "Error Desconocido.";

            try {
                String jsonStr = servicio.makeServiceCall(Constantes.HOST,Constantes.WS_REGISTRO_US,Constantes.MODO_SERVICIO,parametros);
                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);
                        AjaxResultado resultado = servicio.fn_json_to_has_map(jsonObj,null,2);
                        if(resultado.getErrores() != null){
                            mensaje = resultado.getErrores();
                        }else if(resultado.getResultado() != null){
                            mensaje_rs = resultado.getMensajes();
                            ok = true;
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
