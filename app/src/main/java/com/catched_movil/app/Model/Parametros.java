package com.catched_movil.app.Model;

import android.database.Cursor;

import com.catched_movil.app.Control.Bd_origen;
import com.catched_movil.app.Control.GestionBD;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by hernandario on 07/11/2017.
 */

public class Parametros {

    public Parametros(){

    }

    public String fn_consulta_parametro(GestionBD BD,String parametro){

        ArrayList<HashMap<String, String>> datos = new  ArrayList<HashMap<String, String>>();
        String resultado = null;

        Cursor cursor = BD.fn_cursor(Bd_origen.PARAMETOS, Bd_origen.P_PARAMETOS, Bd_origen.ID_PARAMETRO+"="+parametro,null);

        while (cursor.moveToNext()) {
              resultado = cursor.getString(cursor.getColumnIndex(Bd_origen.VALOR_PARAMETRO));
              break;
        }

        return resultado;
    }


}
