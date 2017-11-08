package com.catched_movil.app.Control;

import android.content.ContentValues;

/**
 * Created by hernandario on 08/11/2017.
 */

public class GenerarlBD {


    public boolean fn_insertParametro(GestionBD BD,String codigo,String valor){
        ContentValues valores = new ContentValues();
        valores.put(Bd_origen.ID_PARAMETRO,codigo);
        valores.put(Bd_origen.VALOR_PARAMETRO,valor);
        return BD.fn_Insert(Bd_origen.PARAMETOS,valores);

    }

    public boolean fn_updateParametro(GestionBD BD,String codigo,String valor){
        ContentValues valores = new ContentValues();
        valores.put(Bd_origen.VALOR_PARAMETRO,valor);
        return BD.fn_Update(Bd_origen.PARAMETOS,valores,Bd_origen.ID_PARAMETRO+'='+codigo);
    }


}
