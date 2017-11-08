package com.catched_movil.app.Vista;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.catched_movil.R;
import com.catched_movil.app.Control.Constantes;
import com.catched_movil.app.Control.Funciones;

import java.util.ArrayList;


public class MenuPr extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mdrawer;
    private ActionBarDrawerToggle mtoogle;
    private android.app.FragmentManager fragmentManager ;
    private LinearLayout ly;
    private Context context;
    private Funciones obj_funciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        context           = this;
        obj_funciones     = new Funciones();

        Intent intent = getIntent();

        String valor = intent.getStringExtra("frame");

        NavigationView nview = (NavigationView) findViewById(R.id.navegacion);
        nview.setNavigationItemSelectedListener(this);

        mdrawer = (DrawerLayout)findViewById(R.id.drawerLayout);
        mtoogle = new ActionBarDrawerToggle(this,mdrawer,R.string.abrir,R.string.cerrar);
        mdrawer.addDrawerListener(mtoogle);
        mtoogle.syncState();
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        fragmentManager = getFragmentManager();
        ly = (LinearLayout) findViewById(R.id.content_layout);

        if( valor == null){
            Intent service = new Intent(this, Notificacion.class);
            startService(service);
        }else if(valor.equals("1")){
            generarFramet(new ConsultaEventos());
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(mtoogle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        fragmentManager = getFragmentManager();

        switch (item.getItemId()){
            case R.id.datos_personales:
                return crear_fragment(item,new ModificarUsuario());
            case R.id.consulta_eventos:
                return crear_fragment(item,new ConsultaEventos());
            case R.id.cerrarsession:
                Constantes.AUTENTIFICACION = "";
                obj_funciones.Actividad(this,Login.class);
                return false;
        }
        return false;
    }


    private boolean crear_fragment(MenuItem item, Fragment fragment){
        try {

            if(item != null){
                item.setChecked(true);
            }
            generarFramet(fragment);
            return true;

        }catch (Exception e) {
            Toast toast = Toast.makeText(this, "Error" + e.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
    }


    private void generarFramet(Fragment fragment){
        ly.removeAllViews();
        fragmentManager.beginTransaction().replace(R.id.content_layout,fragment).commit();
        mdrawer.closeDrawer(GravityCompat.START);
    }





}