package com.catched_movil.app.Model;

/**
 * Created by hernandario on 09/11/2017.
 */

public class Usuario {


    public String cod_usuario;
    public String nombres;
    public String usuario;
    public String fecha_creacion;
    public String cod_roll;
    public String ind_estado;

    public Usuario(String cod_usuario, String nombres, String usuario, String fecha_creacion, String cod_roll, String ind_estado) {
        this.cod_usuario = cod_usuario;
        this.nombres = nombres;
        this.usuario = usuario;
        this.fecha_creacion = fecha_creacion;
        this.cod_roll = cod_roll;
        this.ind_estado = ind_estado;
    }

    public String getCod_usuario() {
        return cod_usuario;
    }

    public void setCod_usuario(String cod_usuario) {
        this.cod_usuario = cod_usuario;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(String fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public String getCod_roll() {
        return cod_roll;
    }

    public void setCod_roll(String cod_roll) {
        this.cod_roll = cod_roll;
    }

    public String getInd_estado() {
        return ind_estado;
    }

    public void setInd_estado(String ind_estado) {
        this.ind_estado = ind_estado;
    }
}
