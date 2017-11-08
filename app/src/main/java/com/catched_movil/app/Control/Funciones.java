package com.catched_movil.app.Control;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Base64;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * Created by hernandario on 08/11/2017.
 */

public class Funciones {

    public Funciones() {
    }

    public void MensajeToast(Context context, String texto){
        Toast toast = Toast.makeText(context, texto, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void MensajeDialogo(Context context,String titulo,String texto){
         AlertDialog.Builder msn = new AlertDialog.Builder(context);
         msn.setTitle(titulo);
         msn.setMessage(texto);
         msn.setPositiveButton("Aceptar", null);
         msn.create();
         msn.show();
    }

    public String encodeBase64(String texto) throws UnsupportedEncodingException {
        byte[] data = texto.getBytes("ISO-8859-1");
        String base64 = Base64.encodeToString(data, Base64.DEFAULT);
        return base64;
    }

    public String generarCaracteres(int numero){

        String resultado = "";

        char[] caracteres = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        try {
            for (int i = 0; i < numero; i++) {
                resultado += caracteres[new Random().nextInt(62)];
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return resultado;
    }



}
