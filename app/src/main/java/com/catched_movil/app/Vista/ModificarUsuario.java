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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class ModificarUsuario extends android.app.Fragment implements View.OnClickListener{

    private View myview;
    private Context context;
    private ImageView myImage;
    private EditText edtxt_usuario;
    private EditText edtxt_nombre;
    private EditText edtxt_password;
    private EditText edtxt_password_c;
    private String mensaje;
    private String usuario;
    private String nombre;
    private String password;
    private String password_c;
    private Funciones obj_funciones;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        myview= inflater.inflate(R.layout.fragment_gestor_informacion,container,false);
        context = myview.getContext();
        obj_funciones              = new Funciones();
        edtxt_usuario              = (EditText)myview.findViewById(R.id.modifi_us_edtxt_usuario);
        edtxt_nombre               = (EditText)myview.findViewById(R.id.modifi_us_edtxt_nombre);
        edtxt_password             = (EditText)myview.findViewById(R.id.modifi_us_edtxt_password);
        edtxt_password_c           = (EditText)myview.findViewById(R.id.modifi_us_edtxt_password_confirmar);

        Button registrar = (Button) myview.findViewById(R.id.modifi_us_registrar);
        registrar.setOnClickListener(this);
        Button cancelar = (Button) myview.findViewById(R.id.modifi_us_cancelar);
        cancelar.setOnClickListener(this);
        limpiarCampos();
        return myview;
    }

    @Override
    public void onClick(View view) {
        Button boton = (Button) view;

        if(boton.getId() == R.id.modifi_us_registrar){
            RegistroUsuario();
        }else if(boton.getId() == R.id.modifi_us_cancelar){
            limpiarCampos();
        }
    }

    public void RegistroUsuario() {
        try {

            usuario      = edtxt_usuario.getText().toString().trim();
            nombre       = edtxt_nombre.getText().toString();
            password     = obj_funciones.encodeBase64(edtxt_password.getText().toString()).trim();
            password_c   = obj_funciones.encodeBase64(edtxt_password_c.getText().toString()).trim();
            new ModificarUsuario.Servicio_validar().execute();
        }catch (Exception e){
            e.printStackTrace();
            obj_funciones.MensajeToast(context,"Ocurrio un error ");
        }
    }

    public void limpiarCampos(){
        edtxt_usuario.setText(Constantes.o_usuario.getUsuario());
        edtxt_nombre.setText(Constantes.o_usuario.getNombres());
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
                String jsonStr = servicio.makeServiceCall(Constantes.HOST,Constantes.WS_MODIFICAR_US,Constantes.MODO_SERVICIO,parametros);
                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);
                        AjaxResultado resultado = servicio.fn_json_to_has_map(jsonObj,null,2);
                        if(resultado.getErrores() != null){
                            mensaje = resultado.getErrores();
                        }else if(resultado.getResultado() != null){
                            mensaje_rs = resultado.getMensajes();

                            String[] array = Constantes.AUTENTIFICACION.split("::", -1);

                            String usuario_temp  = obj_funciones.generarCaracteres(5)+obj_funciones.encodeBase64(usuario).trim();

                            if(password.length() > 0){
                                password  = obj_funciones.generarCaracteres(4)+password;
                            }else{
                                password  =  array[1];
                            }

                            Constantes.o_usuario.setUsuario(usuario);
                            Constantes.o_usuario.setNombres(nombre);

                            Constantes.AUTENTIFICACION = (usuario_temp+Constantes.CONCATENACION+password).trim();

                            Log.e("ModificarUsuario ",Constantes.AUTENTIFICACION);
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
            }else{
                obj_funciones.MensajeToast(context,mensaje);
            }
        }

        @Override
        protected void onPostExecute(Void result) {

        }
    }


}
