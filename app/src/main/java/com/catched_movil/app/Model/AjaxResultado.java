package com.catched_movil.app.Model;

/**
 * Created by hernandario on 05/11/2017.
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class AjaxResultado implements Serializable {

    private String mensajes;
    private String errores;
    private ArrayList<HashMap<String, String>> resultado_array;
    private String resultado;

    public AjaxResultado(){
    }

    public String getMensajes() {
        return mensajes;
    }

    public void setMensajes(String mensajes) {
        this.mensajes = mensajes;
    }

    public String getErrores() {
        return errores;
    }

    public void setErrores(String errores) {
        this.errores = errores;
    }

    public ArrayList<HashMap<String, String>> getResultado_array() {
        return resultado_array;
    }

    public void setResultado_array(ArrayList<HashMap<String, String>> resultado_array) {
        this.resultado_array = resultado_array;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
}
