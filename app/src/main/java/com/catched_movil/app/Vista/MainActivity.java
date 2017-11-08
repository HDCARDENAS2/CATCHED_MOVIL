package com.catched_movil.app.Vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.catched_movil.R;
import com.catched_movil.app.Control.Constantes;
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

        if( url != null ){
            Constantes.HOST = url;
            String session        = obj_parametros.fn_consulta_parametro(BD,"3");

            if( session != null && session.equals("1") && !Constantes.AUTENTIFICACION.equals("")){
                Intent intent = new Intent(this, Menu.class);
                startActivity(intent);
                Log.e("MainActivity","MENU");
            }else{
                Intent intent = new Intent(this, Login.class);
                startActivity(intent);
                Log.e("MainActivity","LOGIN");
            }
        }else{
            if(GNBD.fn_insertParametro(BD,"3","0")){
                Intent intent = new Intent(this, RegistroServicio.class);
                startActivity(intent);
                Log.e("MainActivity","REGISTRO APP");
            }
        }

    }
}

