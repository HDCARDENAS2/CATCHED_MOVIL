package com.catched_movil.app.Vista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.catched_movil.R;
import com.catched_movil.app.Control.Constantes;
import com.catched_movil.app.Control.Funciones;
import com.catched_movil.app.Control.GenerarlBD;
import com.catched_movil.app.Control.GestionBD;
import com.catched_movil.app.Model.Parametros;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GestionBD  BD             = new GestionBD(this);
        GenerarlBD GNBD           = new GenerarlBD();
        Parametros obj_parametros = new Parametros();
        String url                = obj_parametros.fn_consulta_parametro(BD,"1");
        Funciones obj_funciones   = new Funciones();

        if( url != null ){
            Constantes.HOST = url;
            String session        = obj_parametros.fn_consulta_parametro(BD,"3");

            if( session != null && session.equals("1") && !Constantes.AUTENTIFICACION.equals("")){
                obj_funciones.Actividad(this, MenuPr.class);
                Log.e("MainActivity","MENU");
            }else{
                obj_funciones.Actividad(this, Login.class);
                Log.e("MainActivity","LOGIN");
            }
        }else{
            if(GNBD.fn_insertParametro(BD,"3","0")){
                obj_funciones.Actividad(this, RegistroServicio.class);
                Log.e("MainActivity","REGISTRO APP");
            }
        }

    }
}

